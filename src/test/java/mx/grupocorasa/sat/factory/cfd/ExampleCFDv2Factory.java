package mx.grupocorasa.sat.factory.cfd;

import mx.grupocorasa.sat.cfd._20.Comprobante;
import mx.grupocorasa.sat.cfd._20.ObjectFactory;
import mx.grupocorasa.sat.cfd._20.TUbicacion;
import mx.grupocorasa.sat.cfd._20.TUbicacionFiscal;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class ExampleCFDv2Factory {

    private static ExampleCFDv2Factory instance;

    private ObjectFactory of;
    private DatatypeFactory datatypeFactory;

    private ExampleCFDv2Factory() {
        try {
            of = new ObjectFactory();
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static ExampleCFDv2Factory getInstance() {
        if (instance == null) {
            instance = new ExampleCFDv2Factory();
        }
        return instance;
    }

    public Comprobante createComprobante() {
        Comprobante comp = of.createComprobante();
        comp.setVersion("2.0");
        comp.setSerie("ABCD");
        comp.setFolio("12");
        comp.setFecha(datatypeFactory.newXMLGregorianCalendar(2011, 4, 3, 14, 11, 36, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED));
//        comp.setSello();
        comp.setNoAprobacion(new BigInteger("49"));
        comp.setAnoAprobacion(new BigInteger("2008"));
        comp.setFormaDePago("UNA SOLA EXHIBICIÓN");
//        comp.setNoCertificado();
//        comp.setCertificado();
        comp.setCondicionesDePago("Factura a Crédito");
        comp.setSubTotal(new BigDecimal("2050.00"));
        comp.setDescuento(new BigDecimal("50.00"));
        comp.setMotivoDescuento("Por pronto pago");
        comp.setTotal(new BigDecimal("2320.00"));
        comp.setMetodoDePago("Tarjeta de Crédito");
        comp.setTipoDeComprobante("ingreso");
        comp.setEmisor(createEmisor());
        comp.setReceptor(createReceptor());
        comp.setConceptos(createConceptos());
        comp.setImpuestos(createImpuestos());
        return comp;
    }

    private Comprobante.Emisor createEmisor() {
        Comprobante.Emisor emisor = of.createComprobanteEmisor();
        emisor.setRfc("COA0106136Z4");
        emisor.setNombre("Comercializadora Ortega y Accionistas, S.A. de C.V.");
        TUbicacionFiscal uf = of.createTUbicacionFiscal();
        uf.setCalle("Leandro P. Gaxiola");
        uf.setNoExterior("6");
        uf.setNoInterior("6");
        uf.setColonia("Olivares");
        uf.setLocalidad("Hermosillo");
        uf.setReferencia("Casa de dos pisos");
        uf.setMunicipio("Hermosillo");
        uf.setEstado("Sonora");
        uf.setPais("México");
        uf.setCodigoPostal("83180");
        emisor.setDomicilioFiscal(uf);
        TUbicacion ee = of.createTUbicacion();
        ee.setCalle("Leandro P. Gaxiola");
        ee.setNoExterior("6");
        ee.setNoInterior("6");
        ee.setColonia("Olivares");
        ee.setLocalidad("Hermosillo");
        ee.setReferencia("Casa de dos pisos");
        ee.setMunicipio("Hermosillo");
        ee.setEstado("Sonora");
        ee.setPais("México");
        ee.setCodigoPostal("83180");
        emisor.setExpedidoEn(ee);
        return emisor;
    }

    private Comprobante.Receptor createReceptor() {
        Comprobante.Receptor receptor = of.createComprobanteReceptor();
        receptor.setRfc("H&E951128469");
        receptor.setNombre("Herreria & Electricos S.A. de C.V.");
        TUbicacion uf = of.createTUbicacion();
        uf.setCalle("Calle conocida");
        uf.setNoExterior("1");
        uf.setNoInterior("2");
        uf.setColonia("Colonia Centro");
        uf.setLocalidad("Hermosillo");
        uf.setReferencia("Carpa verde");
        uf.setMunicipio("Hermosillo");
        uf.setEstado("Sonora");
        uf.setPais("México");
        uf.setCodigoPostal("83000");
        receptor.setDomicilio(uf);
        return receptor;
    }

    private Comprobante.Conceptos createConceptos() {
        Comprobante.Conceptos cps = of.createComprobanteConceptos();
        Comprobante.Conceptos.Concepto c1 = of.createComprobanteConceptosConcepto();
        c1.setUnidad("Servicio");
        c1.setNoIdentificacion("01");
        c1.setImporte(new BigDecimal("1000.00"));
        c1.setCantidad(new BigDecimal("1.00"));
        c1.setDescripcion("Asesoria Fiscal y administrativa");
        c1.setValorUnitario(new BigDecimal("1000.00"));
        cps.getConcepto().add(c1);
        Comprobante.Conceptos.Concepto c2 = of.createComprobanteConceptosConcepto();
        c2.setUnidad("Servicio");
        c2.setNoIdentificacion("02");
        c2.setImporte(new BigDecimal("1000.00"));
        c2.setCantidad(new BigDecimal("2.00"));
        c2.setDescripcion("Asesoria Fiscal y administrativa 2");
        c2.setValorUnitario(new BigDecimal("500.00"));
        cps.getConcepto().add(c2);
        return cps;
    }

    private Comprobante.Impuestos createImpuestos() {
        Comprobante.Impuestos imps = of.createComprobanteImpuestos();
        imps.setTotalImpuestosTrasladados(new BigDecimal("320.00"));
        Comprobante.Impuestos.Traslados trs = of.createComprobanteImpuestosTraslados();
        List<Comprobante.Impuestos.Traslados.Traslado> list = trs.getTraslado();
        Comprobante.Impuestos.Traslados.Traslado t1 = of.createComprobanteImpuestosTrasladosTraslado();
        t1.setImporte(new BigDecimal("320.00"));
        t1.setImpuesto("IVA");
        t1.setTasa(new BigDecimal("16.00"));
        list.add(t1);
        imps.setTraslados(trs);
        return imps;
    }

    public Comprobante.Complemento createComplemento() {
        return of.createComprobanteComplemento();
    }

}
