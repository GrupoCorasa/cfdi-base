package mx.grupocorasa.sat.cfdi.v4;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.util.JAXBSource;
import mx.grupocorasa.sat.cfd._40.Comprobante;
import org.w3c.dom.Document;

import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class CFDv40 extends CFDv4 {

    private final String XSLT = "/xslt/cfd/4/cadenaoriginal_4_0/cadenaoriginal_4_0.xslt";
    private final String BASE_CONTEXT = "mx.grupocorasa.sat.cfd._40";
    private final List<String> schemaLocations = Stream.of("http://www.sat.gob.mx/cfd/4 http://www.sat.gob.mx/sitio_internet/cfd/4/cfdv40.xsd").collect(Collectors.toList());
    private final String[] XSD = new String[]{
            "/xsd/common/catalogos/CartaPorte/catCartaPorte.xsd",
            "/xsd/common/catalogos/ComExt/catComExt.xsd",
            "/xsd/common/catalogos/Combustible/catCombustible.xsd",
            "/xsd/common/catalogos/Nomina/catNomina.xsd",
            "/xsd/common/catalogos/Pagos/catPagos.xsd",
            "/xsd/common/catalogos/catCFDI.xsd",
            "/xsd/common/catalogos/hidrocarburos/catHidrocarburos.xsd",
            "/xsd/common/tipoDatos/tdCFDI/tdCFDI.xsd",
            "/xsd/cfd/4/cfdv40.xsd",
            "/xsd/common/CartaPorte/CartaPorte20.xsd",
            "/xsd/common/CartaPorte/CartaPorte30.xsd",
            "/xsd/common/CartaPorte/CartaPorte31.xsd",
            "/xsd/common/ComercioExterior11/ComercioExterior11.xsd",
            "/xsd/common/ComercioExterior20/ComercioExterior20.xsd",
            "/xsd/common/EstadoDeCuentaCombustible/ecc12.xsd",
            "/xsd/common/GastosHidrocarburos10/GastosHidrocarburos10.xsd",
            "/xsd/common/IngresosHidrocarburos10/IngresosHidrocarburos.xsd",
            "/xsd/common/Pagos/Pagos20.xsd",
            "/xsd/common/TimbreFiscalDigital/TimbreFiscalDigitalv11.xsd",
            "/xsd/common/TuristaPasajeroExtranjero/TuristaPasajeroExtranjero.xsd",
            "/xsd/common/aerolineas/aerolineas.xsd",
            "/xsd/common/arteantiguedades/obrasarteantiguedades.xsd",
            "/xsd/common/certificadodestruccion/certificadodedestruccion.xsd",
            "/xsd/common/cfdiregistrofiscal/cfdiregistrofiscal.xsd",
            "/xsd/common/consumodecombustibles/consumodeCombustibles11.xsd",
            "/xsd/common/detallista/detallista.xsd",
            "/xsd/common/divisas/divisas.xsd",
            "/xsd/common/donat/donat11.xsd",
            "/xsd/common/iedu/iedu.xsd",
            "/xsd/common/implocal/implocal.xsd",
            "/xsd/common/ine/ine11.xsd",
            "/xsd/common/leyendasFiscales/leyendasFisc.xsd",
            "/xsd/common/nomina/nomina12.xsd",
            "/xsd/common/notariospublicos/notariospublicos.xsd",
            "/xsd/common/pagoenespecie/pagoenespecie.xsd",
            "/xsd/common/pfic/pfic.xsd",
            "/xsd/common/renovacionysustitucionvehiculos/renovacionysustitucionvehiculos.xsd",
            "/xsd/common/servicioparcialconstruccion/servicioparcialconstruccion.xsd",
            "/xsd/common/valesdedespensa/valesdedespensa.xsd",
            "/xsd/common/vehiculousado/vehiculousado.xsd",
            "/xsd/common/ventavehiculos/ventavehiculos11.xsd"
    };
    private final Comprobante document;
    private final JAXBContext context;

    public CFDv40(InputStream in, String... contexts) throws Exception {
        this.document = (Comprobante) load(in, contexts);
        this.context = getContext(document, contexts);
    }

    public CFDv40(Comprobante comprobante, String... contexts) throws Exception {
        this.context = getContext(comprobante, contexts);
        this.document = copy(comprobante);
    }

    public static Comprobante newComprobante(InputStream in) throws Exception {
        return new CFDv40(in).document;
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
        document.setNoCertificado(new String(cert.getSerialNumber().toByteArray()));
        document.setCertificado(Base64.getEncoder().encodeToString(cert.getEncoded()));
        document.setSello(getSignature(key));
    }

    private List<String> getComprobanteContexts(Comprobante comprobante) throws IOException {
        final List<String> contexts = new ArrayList<>();
        if (comprobante != null) {
            if (comprobante.getComplemento() != null && comprobante.getComplemento().getAny() != null && !comprobante.getComplemento().getAny().isEmpty()) {
                for (Object c : comprobante.getComplemento().getAny()) {
                    defineComprobanteContext(c, contexts);
                }
            }
            if (comprobante.getConceptos() != null && comprobante.getConceptos().getConcepto() != null) {
                for (Object c : comprobante.getConceptos().getConcepto().stream()
                        .filter(Objects::nonNull)
                        .filter(c -> c.getComplementoConcepto() != null)
                        .filter(c -> c.getComplementoConcepto().getAny() != null)
                        .map(c -> c.getComplementoConcepto().getAny())
                        .flatMap(List::stream).collect(Collectors.toList())
                ) {
                    defineComprobanteContext(c, contexts);
                }
            }
        }
        return contexts;
    }

    private JAXBContext getContext(Comprobante comprobante, String[] addendas) throws Exception {
        final List<String> contexts = getComprobanteContexts(comprobante);
        if (addendas != null && addendas.length > 0)
            contexts.addAll(Arrays.asList(addendas));
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

}
