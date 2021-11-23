package mx.grupocorasa.sat.cfdi;

import mx.grupocorasa.sat.cfd._32.Comprobante;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;
import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class CFDv32 extends CFDv3 {

    private final String XSLT = "/xslt/cfd/3/cadenaoriginal_3_2/cadenaoriginal_3_2.xslt";
    private final String BASE_CONTEXT = "mx.grupocorasa.sat.cfd._32";
    private final List<String> schemaLocations = Stream.of("http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv32.xsd").collect(Collectors.toList());
    private final String[] XSD = new String[]{
            "/xsd/common/catalogos/ComExt/catComExt.xsd",
            "/xsd/common/catalogos/Nomina/catNomina.xsd",
            "/xsd/common/tipoDatos/tdCFDI/tdCFDI.xsd",
            "/xsd/common/catalogos/catCFDI.xsd",
            "/xsd/common/ecc/ecc.xsd",
            "/xsd/common/psgecfd/psgecfd.xsd",
            "/xsd/common/donat/donat11.xsd",
            "/xsd/common/divisas/divisas.xsd",
            "/xsd/common/ecb/ecb.xsd",
            "/xsd/common/detallista/detallista.xsd",
            "/xsd/common/implocal/implocal.xsd",
            "/xsd/common/terceros/terceros11.xsd",
            "/xsd/common/iedu/iedu.xsd",
            "/xsd/common/ventavehiculos/ventavehiculos11.xsd",
            "/xsd/common/pfic/pfic.xsd",
            "/xsd/common/TuristaPasajeroExtranjero/TuristaPasajeroExtranjero.xsd",
            "/xsd/common/leyendasFiscales/leyendasFisc.xsd",
            "/xsd/common/spei/spei.xsd",
            "/xsd/common/nomina/nomina11.xsd",
            "/xsd/common/cfdiregistrofiscal/cfdiregistrofiscal.xsd",
            "/xsd/common/pagoenespecie/pagoenespecie.xsd",
            "/xsd/common/consumodecombustibles/consumodecombustibles.xsd",
            "/xsd/common/valesdedespensa/valesdedespensa.xsd",
            "/xsd/common/aerolineas/aerolineas.xsd",
            "/xsd/common/notariospublicos/notariospublicos.xsd",
            "/xsd/common/vehiculousado/vehiculousado.xsd",
            "/xsd/common/servicioparcialconstruccion/servicioparcialconstruccion.xsd",
            "/xsd/common/certificadodestruccion/certificadodedestruccion.xsd",
            "/xsd/common/renovacionysustitucionvehiculos/renovacionysustitucionvehiculos.xsd",
            "/xsd/common/arteantiguedades/obrasarteantiguedades.xsd",
            "/xsd/common/acreditamiento/AcreditamientoIEPS10.xsd",
            "/xsd/common/EstadoDeCuentaCombustible/ecc11.xsd",
            "/xsd/common/ComercioExterior/ComercioExterior10.xsd",
            "/xsd/common/ine/ine10.xsd",
            "/xsd/common/ine/ine11.xsd",
            "/xsd/common/ComercioExterior11/ComercioExterior11.xsd",
            "/xsd/cfd/3/cfdv32.xsd",
            "/xsd/common/TimbreFiscalDigital/TimbreFiscalDigital.xsd",
            "/xsd/common/nomina/nomina12.xsd"
    };
    private final Comprobante document;
    private final JAXBContext context;

    public CFDv32(InputStream in) throws Exception {
        this.document = (Comprobante) load(in);
        this.context = getContext(document);
    }

    public CFDv32(Comprobante comprobante) throws Exception {
        this.context = getContext(comprobante);
        this.document = copy(comprobante);
    }

    @Override
    public String getBaseContext() {
        return BASE_CONTEXT;
    }

    @Override
    public String[] getXSD() {
        return XSD;
    }

    @Override
    public String getXSLT() {
        return XSLT;
    }

    @Override
    public void addSchemaLocation(String uri) {
        schemaLocations.add(uri);
    }

    @Override
    public JAXBSource getJAXBSource() throws JAXBException {
        return new JAXBSource(context, document);
    }

    @Override
    public int getYear() {
        return document.getFecha().getYear();
    }

    @Override
    public String getCertificadoString() {
        return document.getCertificado();
    }

    @Override
    public String getSelloString() {
        return document.getSello();
    }

    @Override
    public Marshaller createMarshaller() throws JAXBException {
        return context.createMarshaller();
    }

    @Override
    public List<String> getSchemaLocation() {
        return schemaLocations;
    }

    @Override
    public Object getComprobanteDocument() {
        return document;
    }

    @Override
    public void sellar(PrivateKey key, X509Certificate cert) throws Exception {
        document.setSello(getSignature(key));
        document.setCertificado(Base64.getEncoder().encodeToString(cert.getEncoded()));
        document.setNoCertificado(new String(cert.getSerialNumber().toByteArray()));
    }

    private List<String> getComprobanteContexts(Comprobante comprobante) throws IOException {
        final List<String> contexts = new ArrayList<>();
        if (comprobante != null && comprobante.getComplemento() != null && comprobante.getComplemento().getAny() != null && !comprobante.getComplemento().getAny().isEmpty()) {
            for (Object c : comprobante.getComplemento().getAny()) {
                defineComprobanteContext(c, contexts);
            }
        }
        return contexts;
    }

    private JAXBContext getContext(Comprobante comprobante) throws Exception {
        final List<String> contexts = getComprobanteContexts(comprobante);
        contexts.add(0, BASE_CONTEXT);
        if (!contextMap.containsKey(contexts)) {
            JAXBContext context = JAXBContext.newInstance(JOINER.join(contexts));
            contextMap.put(contexts, context);
        }
        return contextMap.get(contexts);
    }

    public Comprobante sellarComprobante(PrivateKey key, X509Certificate cert) throws Exception {
        sellar(key, cert);
        return getComprobante();
    }

    private Comprobante getComprobante() throws Exception {
        return copy(document);
    }

    private Comprobante copy(Comprobante comprobante) throws Exception {
        Document doc = getDocument();
        Marshaller m = createMarshaller();
        m.marshal(comprobante, doc);
        Unmarshaller u = context.createUnmarshaller();
        return (Comprobante) u.unmarshal(doc);
    }

    public static Comprobante newComprobante(InputStream in) throws Exception {
        return new CFDv32(in).document;
    }

}
