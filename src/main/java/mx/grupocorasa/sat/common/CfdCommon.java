package mx.grupocorasa.sat.common;

import com.google.common.io.ByteStreams;
import com.google.common.reflect.ClassPath;
import mx.grupocorasa.sat.security.KeyLoaderEnumeration;
import mx.grupocorasa.sat.security.factory.KeyLoaderFactory;
import mx.grupocorasa.sat.util.StreamUtils;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.bind.util.JAXBSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.lang.annotation.Annotation;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class CfdCommon implements CfdInterface {

    protected final Map<List<String>, JAXBContext> contextMap = new HashMap<>();

    private TransformerFactory tf;

    protected abstract String getDigestAlgorithm();

    public void setTransformerFactory(TransformerFactory tf) {
        this.tf = tf;
        tf.setURIResolver(new URIResolverImpl());
    }

    protected Object load(InputStream in, String[] addendas) throws Exception {
        List<InputStream> copies = StreamUtils.copyStream(in, 2);
        JAXBContext context = getFileContext(copies.get(0), addendas);
        Unmarshaller u = context.createUnmarshaller();
        try (Reader reader = new InputStreamReader(copies.get(1), StandardCharsets.UTF_8)) {
            return u.unmarshal(reader);
        }
    }

    public void validar(ErrorHandler handler) throws Exception {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source[] schemas = new Source[getXSD().length];
        for (int i = 0; i < getXSD().length; i++) {
            schemas[i] = new StreamSource(getClass().getResourceAsStream(getXSD()[i]));
        }
        Schema schema = sf.newSchema(schemas);
        Validator validator = schema.newValidator();
        if (handler != null) {
            validator.setErrorHandler(handler);
        }
        validator.validate(getJAXBSource());
    }

    public void verificar() throws Exception {
        byte[] cbs = Base64.getDecoder().decode(getCertificadoString());
        try (InputStream is = new ByteArrayInputStream(cbs)) {
            X509Certificate cert = KeyLoaderFactory.createInstance(
                    KeyLoaderEnumeration.PUBLIC_KEY_LOADER,
                    is
            ).getKey();
            verificar(cert);
        }
    }

    public void guardar(File out, Boolean formatted) throws Exception {
        try (OutputStream outputStream = new FileOutputStream(out)) {
            guardar(outputStream, formatted);
        }
    }

    public void guardar(OutputStream out, Boolean formatted) throws Exception {
        if (formatted == null) formatted = true;
        Marshaller m = createMarshaller();
        m.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapperImpl(getLocalPrefixes()));
        m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatted);
        m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, String.join(" ", getSchemaLocation()));
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); Writer writer = new OutputStreamWriter(baos, StandardCharsets.UTF_8)) {
            writer.write(XML_HEADER);
            m.marshal(getComprobanteDocument(), writer);
            String xml = baos.toString("UTF-8").replace("xmlns:tfd=\"http://www.sat.gob.mx/TimbreFiscalDigital\" ", "");
            if (xml.contains("http://www.sat.gob.mx/sitio_internet/cfd/TimbreFiscalDigital/TimbreFiscalDigital.xsd")) {
                xml = xml
                        .replace(" http://www.sat.gob.mx/TimbreFiscalDigital http://www.sat.gob.mx/sitio_internet/cfd/TimbreFiscalDigital/TimbreFiscalDigital.xsd", "")
                        .replace("<tfd:TimbreFiscalDigital", "<tfd:TimbreFiscalDigital xsi:schemaLocation=\"http://www.sat.gob.mx/TimbreFiscalDigital http://www.sat.gob.mx/sitio_internet/cfd/TimbreFiscalDigital/TimbreFiscalDigital.xsd\" xmlns:tfd=\"http://www.sat.gob.mx/TimbreFiscalDigital\"");
            } else if (xml.contains("http://www.sat.gob.mx/sitio_internet/cfd/TimbreFiscalDigital/TimbreFiscalDigitalv11.xsd")) {
                xml = xml
                        .replace(" http://www.sat.gob.mx/TimbreFiscalDigital http://www.sat.gob.mx/sitio_internet/cfd/TimbreFiscalDigital/TimbreFiscalDigitalv11.xsd", "")
                        .replace("<tfd:TimbreFiscalDigital", "<tfd:TimbreFiscalDigital xsi:schemaLocation=\"http://www.sat.gob.mx/TimbreFiscalDigital http://www.sat.gob.mx/sitio_internet/cfd/TimbreFiscalDigital/TimbreFiscalDigitalv11.xsd\" xmlns:tfd=\"http://www.sat.gob.mx/TimbreFiscalDigital\"");
            }
            out.write(xml.getBytes(StandardCharsets.UTF_8));
        }
    }

    public String getCadenaOriginal() throws Exception {
        byte[] bytes = getOriginalBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private void defineContexts(List<String> contexts, ClassPath.ClassInfo info) {
        final Class<?> clazz = info.load();
        for (Annotation annotation : clazz.getPackage().getAnnotations()) {
            if (annotation.annotationType() == XmlSchema.class) {
                String nsURI = ((XmlSchema) annotation).namespace();
                final String[] nsUrl = new String[1];
                Arrays.stream(getXSD())
                        .filter(xsd -> xsd.contains(nsURI.substring(nsURI.lastIndexOf("/"))))
                        .filter(xsd -> !xsd.contains("/catalogo/") && !xsd.contains("/catalogos/"))
                        .findAny().ifPresent(value -> nsUrl[0] = value.replace("/xsd/common", "http://www.sat.gob.mx/sitio_internet/cfd"));
                if (!getLocalPrefixes().containsKey(nsURI)) {
                    getFileNamespaceMap().entrySet().stream()
                            .filter(entry -> entry.getValue().equalsIgnoreCase(nsURI))
                            .findFirst()
                            .ifPresent(entry -> addNamespace(nsURI, entry.getKey().split(":")[0]));
                }
                if (!contexts.contains(info.getName().substring(0, info.getName().lastIndexOf(".")))) {
                    contexts.add(info.getName().substring(0, info.getName().lastIndexOf(".")));
                }
                if (!getSchemaLocation().contains(nsURI + " " + nsUrl[0])) {
                    addSchemaLocation(nsURI + " " + nsUrl[0]);
                }
                break;
            }
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    protected void defineComprobanteContext(Object c, List<String> contexts) throws IOException {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        for (final ClassPath.ClassInfo info : ClassPath.from(loader).getTopLevelClasses()) {
            if (info.getName().startsWith("mx.grupocorasa.sat.common.") && info.getName().equalsIgnoreCase(c.getClass().getName())) {
                defineContexts(contexts, info);
            }
        }
    }

    protected void addNamespace(String uri, String prefix) {
        getLocalPrefixes().put(uri, prefix);
    }

    protected Document getDocument() throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        return db.newDocument();
    }

    //Verifica textualmente el XML con el XSD (Funciona cuando queremos validar un XML que NO fue creado con esta librería
    public void verificar(InputStream in) throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] cbs = decoder.decode(getCertificadoString());

        X509Certificate cert = KeyLoaderFactory.createInstance(
                KeyLoaderEnumeration.PUBLIC_KEY_LOADER,
                new ByteArrayInputStream(cbs)
        ).getKey();

        byte[] signature = decoder.decode(getSelloString());
        byte[] bytes = getOriginalBytes(in);
        Signature sig = Signature.getInstance(getDigestAlgorithm());
        sig.initVerify(cert);
        sig.update(bytes);
        boolean bool = sig.verify(signature);
        if (!bool) {
            throw new Exception("Invalid signature.");
        }
    }

    private void verificar(X509Certificate cert) throws Exception {
        String sigStr = getSelloString();
        byte[] signature = Base64.getDecoder().decode(sigStr);
        byte[] bytes = getOriginalBytes();
        Signature sig = Signature.getInstance(getDigestAlgorithm());
        sig.initVerify(cert);
        sig.update(bytes);
        boolean bool = sig.verify(signature);
        if (!bool) {
            throw new Exception("Invalid signature");
        }
    }

    //Funciona en conjunto con: verificar(InputStream in)
    byte[] getOriginalBytes(InputStream in) throws IOException, TransformerException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Source source = new StreamSource(in);
            Result out = new StreamResult(baos);
            TransformerFactory factory = tf;
            if (factory == null) {
                factory = TransformerFactory.newInstance();
                factory.setURIResolver(new URIResolverImpl());
            }
            Transformer transformer = factory.newTransformer(new StreamSource(getClass().getResourceAsStream(getXSLT())));
            transformer.transform(source, out);
            return baos.toByteArray();
        }
    }

    private byte[] getOriginalBytes() throws Exception {
        JAXBSource in = getJAXBSource();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); Writer writer = new OutputStreamWriter(baos, StandardCharsets.UTF_8)) {
            Result out = new StreamResult(writer);
            TransformerFactory factory = tf;
            if (factory == null) {
                factory = TransformerFactory.newInstance();
                factory.setURIResolver(new URIResolverImpl());
            }
            Transformer transformer = factory.newTransformer(new StreamSource(getClass().getResourceAsStream(getXSLT())));
            transformer.transform(in, out);
            return baos.toByteArray();
        }
    }

    protected String getSignature(PrivateKey key) throws Exception {
        byte[] bytes = getOriginalBytes();
        byte[] signed;
        Signature sig = Signature.getInstance(getDigestAlgorithm());
        sig.initSign(key);
        sig.update(bytes);
        signed = sig.sign();
        Base64.Encoder b64 = Base64.getEncoder();
        return b64.encodeToString(signed);
    }

    //***DO NOT EDIT*** FROM HERE
    private Map<String, String> getFileNamespaceMap() {
        Map<String, String> namespaceMap = new HashMap<>();
        namespaceMap.put("ecc:mx.grupocorasa.sat.common.ecc", "http://www.sat.gob.mx/ecc");
        namespaceMap.put("vehiculousado:mx.grupocorasa.sat.common.vehiculousado10", "http://www.sat.gob.mx/vehiculousado");
        namespaceMap.put("psgecfd:mx.grupocorasa.sat.common.psgecfd", "http://www.sat.gob.mx/psgecfd");
        namespaceMap.put("catCFDI:mx.grupocorasa.sat.common.catalogos", "http://www.sat.gob.mx/sitio_internet/cfd/catalogos");
        namespaceMap.put("spei:mx.grupocorasa.sat.common.spei", "http://www.sat.gob.mx/spei");
        namespaceMap.put("cartaporte20:mx.grupocorasa.sat.common.CartaPorte20", "http://www.sat.gob.mx/CartaPorte20");
        namespaceMap.put("ecb:mx.grupocorasa.sat.common.ecb10", "http://www.sat.gob.mx/ecb");
        namespaceMap.put("obrasarte:mx.grupocorasa.sat.common.arteantiguedades10", "http://www.sat.gob.mx/arteantiguedades");
        namespaceMap.put("catNomina:mx.grupocorasa.sat.common.catalogos.Nomina", "http://www.sat.gob.mx/sitio_internet/cfd/catalogos/Nomina");
        namespaceMap.put("catCEH:mx.grupocorasa.sat.common.catalogos.hidrocarburos", "http://www.sat.gob.mx/sitio_internet/cfd/catalogos/hidrocarburos");
        namespaceMap.put("implocal:mx.grupocorasa.sat.common.implocal10", "http://www.sat.gob.mx/implocal");
        namespaceMap.put("decreto:mx.grupocorasa.sat.common.renovacionysustitucionvehiculos10", "http://www.sat.gob.mx/renovacionysustitucionvehiculos");
        namespaceMap.put("tpe:mx.grupocorasa.sat.common.TuristaPasajeroExtranjero10", "http://www.sat.gob.mx/TuristaPasajeroExtranjero");
        namespaceMap.put("catPagos:mx.grupocorasa.sat.common.catalogos.Pagos", "http://www.sat.gob.mx/sitio_internet/cfd/catalogos/Pagos");
        namespaceMap.put("aerolineas:mx.grupocorasa.sat.common.aerolineas10", "http://www.sat.gob.mx/aerolineas");
        namespaceMap.put("iedu:mx.grupocorasa.sat.common.iedu10", "http://www.sat.gob.mx/iedu");
        namespaceMap.put("pago20:mx.grupocorasa.sat.common.Pagos20", "http://www.sat.gob.mx/Pagos20");
        namespaceMap.put("notariospublicos:mx.grupocorasa.sat.common.notariospublicos10", "http://www.sat.gob.mx/notariospublicos");
        namespaceMap.put("donat:mx.grupocorasa.sat.common.donat10", "http://www.sat.gob.mx/donat");
        namespaceMap.put("divisas:mx.grupocorasa.sat.common.divisas10", "http://www.sat.gob.mx/divisas");
        namespaceMap.put("pagoenespecie:mx.grupocorasa.sat.common.pagoenespecie10", "http://www.sat.gob.mx/pagoenespecie");
        namespaceMap.put("donat:mx.grupocorasa.sat.common.donat11", "http://www.sat.gob.mx/donat");
        namespaceMap.put("tdCFDI:mx.grupocorasa.sat.common.tipoDatos.tdCFDI", "http://www.sat.gob.mx/sitio_internet/cfd/tipoDatos/tdCFDI");
        namespaceMap.put("gceh:mx.grupocorasa.sat.common.GastosHidrocarburos10", "http://www.sat.gob.mx/GastosHidrocarburos10");
        namespaceMap.put("destruccion:mx.grupocorasa.sat.common.certificadodestruccion10", "http://www.sat.gob.mx/certificadodestruccion");
        namespaceMap.put("catComExt:mx.grupocorasa.sat.common.catalogos.ComExt", "http://www.sat.gob.mx/sitio_internet/cfd/catalogos/ComExt");
        namespaceMap.put("ieeh:mx.grupocorasa.sat.common.IngresosHidrocarburos10", "http://www.sat.gob.mx/IngresosHidrocarburos10");
        namespaceMap.put("pago10:mx.grupocorasa.sat.common.Pagos10", "http://www.sat.gob.mx/Pagos");
        namespaceMap.put("tfd:mx.grupocorasa.sat.common.TimbreFiscalDigital11", "http://www.sat.gob.mx/TimbreFiscalDigital");
        namespaceMap.put("tfd:mx.grupocorasa.sat.common.TimbreFiscalDigital10", "http://www.sat.gob.mx/TimbreFiscalDigital");
        namespaceMap.put("catCartaPorte:mx.grupocorasa.sat.common.catalogos.CartaPorte", "http://www.sat.gob.mx/sitio_internet/cfd/catalogos/CartaPorte");
        namespaceMap.put("terceros:mx.grupocorasa.sat.common.terceros11", "http://www.sat.gob.mx/terceros");
        namespaceMap.put("ventavehiculos:mx.grupocorasa.sat.common.ventavehiculos11", "http://www.sat.gob.mx/ventavehiculos");
        namespaceMap.put("cce:mx.grupocorasa.sat.common.ComercioExterior10", "http://www.sat.gob.mx/ComercioExterior");
        namespaceMap.put("cce11:mx.grupocorasa.sat.common.ComercioExterior11", "http://www.sat.gob.mx/ComercioExterior11");
        namespaceMap.put("cartaporte:mx.grupocorasa.sat.common.CartaPorte10", "http://www.sat.gob.mx/CartaPorte");
        namespaceMap.put("ine:mx.grupocorasa.sat.common.ine10", "http://www.sat.gob.mx/ine");
        namespaceMap.put("registrofiscal:mx.grupocorasa.sat.common.cfdiregistrofiscal10", "http://www.sat.gob.mx/registrofiscal");
        namespaceMap.put("ine:mx.grupocorasa.sat.common.ine11", "http://www.sat.gob.mx/ine");
        namespaceMap.put("ecc12:mx.grupocorasa.sat.common.EstadoDeCuentaCombustible12", "http://www.sat.gob.mx/EstadoDeCuentaCombustible12");
        namespaceMap.put("ecc11:mx.grupocorasa.sat.common.EstadoDeCuentaCombustible11", "http://www.sat.gob.mx/EstadoDeCuentaCombustible");
        namespaceMap.put("nomina:mx.grupocorasa.sat.common.nomina11", "http://www.sat.gob.mx/nomina");
        namespaceMap.put("nomina12:mx.grupocorasa.sat.common.nomina12", "http://www.sat.gob.mx/nomina12");
        namespaceMap.put("aieps:mx.grupocorasa.sat.common.acreditamiento10", "http://www.sat.gob.mx/acreditamiento");
        namespaceMap.put("consumodecombustibles11:mx.grupocorasa.sat.common.consumodecombustibles11", "http://www.sat.gob.mx/ConsumoDeCombustibles11");
        return namespaceMap;
    }
    //***DO NOT EDIT*** TO HERE

    @SuppressWarnings("UnstableApiUsage")
    protected JAXBContext getFileContext(InputStream in, String[] addendas) throws IOException, JAXBException {
        final List<String> contexts = new ArrayList<>();
        contexts.add(getBaseContext());
        contexts.addAll(Arrays.asList(addendas));
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final String xml = new String(ByteStreams.toByteArray(in), StandardCharsets.UTF_8);
        Map<String, String> namespaceMap = getFileNamespaceMap();
        for (final ClassPath.ClassInfo info : ClassPath.from(loader).getTopLevelClasses()) {
            if (info.getName().startsWith("mx.grupocorasa.sat.common.")
                    && namespaceMap.keySet().stream().map(k -> k.split(":")[1]).anyMatch(k -> k.equalsIgnoreCase(info.getPackageName()))
                    && xml.contains(Objects.requireNonNull(namespaceMap.entrySet().stream().filter(entry -> entry.getKey().split(":")[1].equalsIgnoreCase(info.getPackageName())).map(Map.Entry::getValue).findAny().orElse(null)))
            ) {
                List<String> preList = namespaceMap.keySet().stream().filter(k ->
                        namespaceMap.get(k.split(":")[0] + ":" + info.getPackageName()) != null
                ).collect(Collectors.toList());
                if (preList.stream().map(p -> p.split(":")[0]).distinct().noneMatch(pre -> xml.contains("<" + pre + ":")))
                    continue;
                if (preList.size() > 1) {
                    String pre = preList.get(0).split(":")[0];
                    int startIndex = xml.indexOf("<" + pre);
                    Matcher versionMatcher = Pattern.compile("(?<=[V|v]ersion=\")((.)*?)(?=\")", Pattern.CASE_INSENSITIVE).matcher(xml.substring(startIndex));
                    if (versionMatcher.find()) {
                        String version = versionMatcher.group().replace(".", "");
                        if (!info.getName().contains(version)) continue;
                    }
                }
                defineContexts(contexts, info);
            }
        }
        return JAXBContext.newInstance(JOINER.join(contexts));
    }
}