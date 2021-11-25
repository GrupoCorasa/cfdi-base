package mx.grupocorasa.sat.factory.cfd;

import mx.grupocorasa.sat.cfd._32.Comprobante;
import mx.grupocorasa.sat.cfd._32.ObjectFactory;
import mx.grupocorasa.sat.cfd._32.TUbicacion;
import mx.grupocorasa.sat.cfd._32.TUbicacionFiscal;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ExampleCFDv32Factory {

    private static ExampleCFDv32Factory instance;

    private final ObjectFactory of;

    private ExampleCFDv32Factory() {
        of = new ObjectFactory();
    }

    public static ExampleCFDv32Factory getInstance() {
        if (instance == null) {
            instance = new ExampleCFDv32Factory();
        }
        return instance;
    }

    public Comprobante createComprobante() {
        Comprobante comp = of.createComprobante();
        comp.setVersion("3.2");
        comp.setSerie("DCBA");
        comp.setFolio("81");
        comp.setFecha(LocalDateTime.of(2015, 3, 11, 17, 8, 22));
//        comp.setSello();
        comp.setFormaDePago("Pago en una sola exhibición");
//        comp.setNoCertificado();
//        comp.setCertificado();
        comp.setCondicionesDePago("Factura a Crédito");
        comp.setSubTotal(new BigDecimal("1681.04"));
        comp.setDescuento(new BigDecimal("323.28"));
        comp.setMotivoDescuento("Por acuerdo mutuo");
        comp.setTipoCambio("1.00");
        comp.setMoneda("MXN");
        comp.setTotal(new BigDecimal("1575.00"));
        comp.setTipoDeComprobante("ingreso");
        comp.setMetodoDePago("Transferencia Electrónica");
        comp.setLugarExpedicion("Hermosillo, Sonora, México");
        comp.setNumCtaPago("1587");
        comp.setEmisor(createEmisor());
        comp.setReceptor(createReceptor());
        comp.setConceptos(createConceptos());
        comp.setImpuestos(createImpuestos());
        return comp;
    }

    public Comprobante createComprobanteNomina() {
        Comprobante comp = of.createComprobante();
        comp.setVersion("3.2");
        comp.setSerie("NOM");
        comp.setFolio("312");
        comp.setFecha(LocalDateTime.of(2016, 10, 13, 14, 52, 36));
//        comp.setSello();
        comp.setFormaDePago("Pago en una sola exhibición");
//        comp.setNoCertificado();
//        comp.setCertificado();
        comp.setSubTotal(new BigDecimal("4325.77"));
        comp.setDescuento(new BigDecimal("436.61"));
        comp.setMotivoDescuento("Deducciones nómina");
        comp.setTipoCambio("1.00");
        comp.setMoneda("MXN");
        comp.setTotal(new BigDecimal("3513.58"));
        comp.setTipoDeComprobante("egreso");
        comp.setMetodoDePago("03");
        comp.setLugarExpedicion("Hermosillo, Sonora, México");
        comp.setEmisor(createEmisor());
        comp.setReceptor(createReceptorNomina());
        comp.setConceptos(createConceptoNomina());
        comp.setImpuestos(createImpuestosNomina());
        return comp;
    }

    private Comprobante.Emisor createEmisor() {
        Comprobante.Emisor emisor = of.createComprobanteEmisor();
        emisor.setRfc("COA0106136Z4");
        emisor.setNombre("Comercializadora Ortega y Accionistas, S.A. de C.V.");
        Comprobante.Emisor.RegimenFiscal regimenFiscal = of.createComprobanteEmisorRegimenFiscal();
        regimenFiscal.setRegimen("Régimen General de Ley Persona Moral");
        emisor.getRegimenFiscal().add(regimenFiscal);
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

    private Comprobante.Receptor createReceptorNomina() {
        Comprobante.Receptor receptor = of.createComprobanteReceptor();
        receptor.setRfc("OEGH910609BZ6");
        receptor.setNombre("Heriberto Ortega Gonzalez");
        TUbicacion uf = of.createTUbicacion();
        uf.setEstado("Sonora");
        uf.setPais("México");
        receptor.setDomicilio(uf);
        return receptor;
    }

    private Comprobante.Conceptos createConceptos() {
        Comprobante.Conceptos cps = of.createComprobanteConceptos();
        Comprobante.Conceptos.Concepto c1 = of.createComprobanteConceptosConcepto();
        c1.setCantidad(new BigDecimal("1.00"));
        c1.setUnidad("Servicio");
        c1.setNoIdentificacion("01");
        c1.setDescripcion("Asesoria Fiscal y administrativa");
        c1.setValorUnitario(new BigDecimal("1681.04"));
        c1.setImporte(new BigDecimal("1681.04"));
        cps.getConcepto().add(c1);
        return cps;
    }

    private Comprobante.Conceptos createConceptoNomina() {
        Comprobante.Conceptos cps = of.createComprobanteConceptos();
        Comprobante.Conceptos.Concepto c1 = of.createComprobanteConceptosConcepto();
        c1.setCantidad(new BigDecimal("1.00"));
        c1.setUnidad("Servicio");
        c1.setDescripcion("Pago de nómina");
        c1.setValorUnitario(new BigDecimal("4325."));
        c1.setImporte(new BigDecimal("4325."));
        cps.getConcepto().add(c1);
        return cps;
    }

    private Comprobante.Impuestos createImpuestos() {
        Comprobante.Impuestos imps = of.createComprobanteImpuestos();
        imps.setTotalImpuestosTrasladados(new BigDecimal("217.24"));
        Comprobante.Impuestos.Traslados trs = of.createComprobanteImpuestosTraslados();
        List<Comprobante.Impuestos.Traslados.Traslado> list = trs.getTraslado();
        Comprobante.Impuestos.Traslados.Traslado t1 = of.createComprobanteImpuestosTrasladosTraslado();
        t1.setImpuesto("IVA");
        t1.setTasa(new BigDecimal("16.00"));
        t1.setImporte(new BigDecimal("217.24"));
        list.add(t1);
        imps.setTraslados(trs);
        return imps;
    }

    private Comprobante.Impuestos createImpuestosNomina() {
        Comprobante.Impuestos imps = of.createComprobanteImpuestos();
        imps.setTotalImpuestosRetenidos(new BigDecimal("375.58"));
        Comprobante.Impuestos.Retenciones trs = of.createComprobanteImpuestosRetenciones();
        List<Comprobante.Impuestos.Retenciones.Retencion> list = trs.getRetencion();
        Comprobante.Impuestos.Retenciones.Retencion ret = of.createComprobanteImpuestosRetencionesRetencion();
        ret.setImpuesto("ISR");
        ret.setImporte(new BigDecimal("375.58"));
        list.add(ret);
        imps.setRetenciones(trs);
        return imps;
    }

    public Comprobante.Complemento createComplemento() {
        return of.createComprobanteComplemento();
    }

}
