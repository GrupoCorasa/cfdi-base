package mx.grupocorasa.sat.factory.cfd;

import mx.grupocorasa.sat.cfd._40.Comprobante;
import mx.grupocorasa.sat.cfd._40.ObjectFactory;
import mx.grupocorasa.sat.common.catalogos.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExampleCFDv40Factory {

    private static ExampleCFDv40Factory instance;

    private final ObjectFactory of;

    private ExampleCFDv40Factory() {
        of = new ObjectFactory();
    }

    public static ExampleCFDv40Factory getInstance() {
        if (instance == null) {
            instance = new ExampleCFDv40Factory();
        }
        return instance;
    }

    public Comprobante createComprobante() {
        Comprobante comp = of.createComprobante();
        comp.setVersion("4.0");
        comp.setSerie("FFF");
        comp.setFolio("12345");
        comp.setFecha(LocalDateTime.of(2021, 1, 1, 14, 54, 56));
//        comp.setSello();
        comp.setFormaPago(CFormaPago.VALUE_8);
//        comp.setNoCertificado("30001000000400002463");
//        comp.setCertificado();
        comp.setCondicionesDePago("Crédito a 20 días");
        comp.setSubTotal(new BigDecimal("2200.00"));
        comp.setDescuento(new BigDecimal("200.00"));
        comp.setMoneda(CMoneda.MXN);
        comp.setTipoCambio(new BigDecimal("1"));
        comp.setTotal(new BigDecimal("2000"));
        comp.setTipoDeComprobante(CTipoDeComprobante.I);
        comp.setExportacion(CExportacion.VALUE_2);
        comp.setMetodoPago(CMetodoPago.PUE);
        comp.setLugarExpedicion("83000");
        comp.setConfirmacion("aB1cD");
        comp.setInformacionGlobal(createCfdiInformacionGlobal());
        comp.getCfdiRelacionados().addAll(createCfdiRelacionados());
        comp.setEmisor(createEmisor());
        comp.setReceptor(createReceptor());
        comp.setConceptos(createConceptos());
        comp.setImpuestos(createImpuestos());
        return comp;
    }

    public Comprobante createComprobantePago() {
        Comprobante comp = of.createComprobante();
        comp.setVersion("4.0");
        comp.setSerie("PPP");
        comp.setFolio("54321");
        comp.setFecha(LocalDateTime.of(2022, 2, 2, 15, 55, 57));
//        comp.setSello();
//        comp.setFormaPago();
//        comp.setNoCertificado("30001000000400002463");
//        comp.setCertificado();
//        comp.setCondicionesDePago();
        comp.setSubTotal(BigDecimal.ZERO);
//        comp.setDescuento();
        comp.setMoneda(CMoneda.XXX);
//        comp.setTipoCambio();
        comp.setTotal(BigDecimal.ZERO);
        comp.setTipoDeComprobante(CTipoDeComprobante.P);
        comp.setExportacion(CExportacion.VALUE_1);
//        comp.setMetodoPago();
        comp.setLugarExpedicion("83000");
//        comp.setConfirmacion();
//        comp.setInformacionGlobal(createCfdiInformacionGlobal());
//        comp.getCfdiRelacionados().addAll(createCfdiRelacionados());
        comp.setEmisor(createEmisor());
        comp.setReceptor(createReceptor());

        Comprobante.Conceptos conceptos = of.createComprobanteConceptos();
        Comprobante.Conceptos.Concepto c1 = of.createComprobanteConceptosConcepto();
        c1.setClaveProdServ("84111506");
//        c1.setNoIdentificacion();
        c1.setCantidad(BigDecimal.ONE);
        c1.setClaveUnidad(CClaveUnidad.VALUE_241);
//        c1.setUnidad();
        c1.setDescripcion("Pago");
        c1.setValorUnitario(BigDecimal.ZERO);
        c1.setImporte(BigDecimal.ZERO);
//        c1.setDescuento();
        c1.setObjetoImp(CObjetoImp.VALUE_1);
//        c1.setImpuestos(createImpuestosConceptos());
//        c1.getInformacionAduanera().addAll(createInformacionAduanera());
//        c1.getCuentaPredial().addAll(createCuentaPredial());
        conceptos.getConcepto().add(c1);
        comp.setConceptos(conceptos);

//        comp.setImpuestos(createImpuestos());
        return comp;
    }

    private Comprobante.InformacionGlobal createCfdiInformacionGlobal() {
        Comprobante.InformacionGlobal cfdiInformacionGlobal = of.createComprobanteInformacionGlobal();
        cfdiInformacionGlobal.setPeriodicidad(CPeriodicidad.VALUE_3);
        cfdiInformacionGlobal.setMeses(CMeses.VALUE_9);
        cfdiInformacionGlobal.setAño(Short.parseShort("2022"));
        return cfdiInformacionGlobal;
    }

    private List<Comprobante.CfdiRelacionados> createCfdiRelacionados() {
        List<Comprobante.CfdiRelacionados> cfdiRelacionadosList = new ArrayList<>();
        Comprobante.CfdiRelacionados cfdiRelacionados = of.createComprobanteCfdiRelacionados();
        cfdiRelacionados.setTipoRelacion(CTipoRelacion.VALUE_6);
        Comprobante.CfdiRelacionados.CfdiRelacionado cfdiRelacionado1 = of.createComprobanteCfdiRelacionadosCfdiRelacionado();
        cfdiRelacionado1.setUUID("248C01A6-A08C-4821-BFAF-F1D04C0A0DC5");
        cfdiRelacionados.getCfdiRelacionado().add(cfdiRelacionado1);
        Comprobante.CfdiRelacionados.CfdiRelacionado cfdiRelacionado2 = of.createComprobanteCfdiRelacionadosCfdiRelacionado();
        cfdiRelacionado2.setUUID("4A3CC52B-4F7A-4FAF-9375-9905D535CB07");
        cfdiRelacionados.getCfdiRelacionado().add(cfdiRelacionado2);
        cfdiRelacionadosList.add(cfdiRelacionados);

        Comprobante.CfdiRelacionados cfdiRelacionados2 = of.createComprobanteCfdiRelacionados();
        cfdiRelacionados2.setTipoRelacion(CTipoRelacion.VALUE_6);
        Comprobante.CfdiRelacionados.CfdiRelacionado cfdiRelacionado3 = of.createComprobanteCfdiRelacionadosCfdiRelacionado();
        cfdiRelacionado3.setUUID("580A7432-900F-11EC-B909-0242AC120002");
        cfdiRelacionados2.getCfdiRelacionado().add(cfdiRelacionado3);
        Comprobante.CfdiRelacionados.CfdiRelacionado cfdiRelacionado4 = of.createComprobanteCfdiRelacionadosCfdiRelacionado();
        cfdiRelacionado4.setUUID("223E7412-4F7A-4821-9375-F1D04C0A0DC5");
        cfdiRelacionados2.getCfdiRelacionado().add(cfdiRelacionado4);
        cfdiRelacionadosList.add(cfdiRelacionados2);

        return cfdiRelacionadosList;
    }

    private Comprobante.Emisor createEmisor() {
        Comprobante.Emisor emisor = of.createComprobanteEmisor();
        emisor.setNombre("PHARMA PLUS SA DE CV");
        emisor.setRfc("PPL961114GZ1");
        emisor.setRegimenFiscal(CRegimenFiscal.VALUE_4);
        emisor.setFacAtrAdquirente("0123456789");
        return emisor;
    }

    private Comprobante.Receptor createReceptor() {
        Comprobante.Receptor receptor = of.createComprobanteReceptor();
        receptor.setRfc("PEPJ8001019Q8");
        receptor.setNombre("JUAN PEREZ PEREZ");
        receptor.setDomicilioFiscalReceptor("83000");
        receptor.setResidenciaFiscal(CPais.MEX);
        receptor.setNumRegIdTrib("ResidenteExtranjero1");
        receptor.setRegimenFiscalReceptor(CRegimenFiscal.VALUE_5);
        receptor.setUsoCFDI(CUsoCFDI.D_08);
        return receptor;
    }

    private Comprobante.Conceptos createConceptos() {
        Comprobante.Conceptos conceptos = of.createComprobanteConceptos();
        List<Comprobante.Conceptos.Concepto> list = conceptos.getConcepto();
        Comprobante.Conceptos.Concepto c1 = of.createComprobanteConceptosConcepto();
        c1.setClaveProdServ("10101501");
        c1.setNoIdentificacion("PRU01");
        c1.setCantidad(new BigDecimal("1.0"));
        c1.setClaveUnidad(CClaveUnidad.VALUE_429);
        c1.setUnidad("Servicio");
        c1.setDescripcion("Concepto de Prueba 01");
        c1.setValorUnitario(new BigDecimal("1100.00"));
        c1.setImporte(new BigDecimal("1100.00"));
        c1.setDescuento(new BigDecimal("100.00"));
        c1.setObjetoImp(CObjetoImp.VALUE_2);
        c1.setImpuestos(createImpuestosConceptos());
        c1.getInformacionAduanera().addAll(createInformacionAduanera());
        c1.getCuentaPredial().addAll(createCuentaPredial());
        list.add(c1);
        Comprobante.Conceptos.Concepto c2 = of.createComprobanteConceptosConcepto();
        c2.setClaveProdServ("10101502");
        c2.setNoIdentificacion("PRU02");
        c2.setCantidad(new BigDecimal("1.0"));
        c2.setClaveUnidad(CClaveUnidad.VALUE_429);
        c2.setUnidad("Servicio");
        c2.setDescripcion("Concepto de Prueba 02");
        c2.setValorUnitario(new BigDecimal("1100.00"));
        c2.setImporte(new BigDecimal("1100.00"));
        c2.setDescuento(new BigDecimal("100.00"));
        c2.setObjetoImp(CObjetoImp.VALUE_2);
        c2.setImpuestos(createImpuestosConceptos());
        c2.getInformacionAduanera().addAll(createInformacionAduanera());
        c2.getCuentaPredial().addAll(createCuentaPredial());
        list.add(c2);
        return conceptos;
    }

    private Comprobante.Conceptos.Concepto.Impuestos createImpuestosConceptos() {
        Comprobante.Conceptos.Concepto.Impuestos impuestos = of.createComprobanteConceptosConceptoImpuestos();
        Comprobante.Conceptos.Concepto.Impuestos.Traslados traslados = of.createComprobanteConceptosConceptoImpuestosTraslados();
        Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado traslado = of.createComprobanteConceptosConceptoImpuestosTrasladosTraslado();
        traslado.setBase(new BigDecimal("1000.00"));
        traslado.setImpuesto(CImpuesto.VALUE_2);
        traslado.setTipoFactor(CTipoFactor.TASA);
        traslado.setTasaOCuota(new BigDecimal("0.160000"));
        traslado.setImporte(new BigDecimal("160.00"));
        traslados.getTraslado().add(traslado);
        impuestos.setTraslados(traslados);
        Comprobante.Conceptos.Concepto.Impuestos.Retenciones retenciones = of.createComprobanteConceptosConceptoImpuestosRetenciones();
        Comprobante.Conceptos.Concepto.Impuestos.Retenciones.Retencion retencion = of.createComprobanteConceptosConceptoImpuestosRetencionesRetencion();
        retencion.setBase(new BigDecimal("1000.00"));
        retencion.setImpuesto(CImpuesto.VALUE_2);
        retencion.setTipoFactor(CTipoFactor.TASA);
        retencion.setTasaOCuota(new BigDecimal("0.160000"));
        retencion.setImporte(new BigDecimal("160.00"));
        retenciones.getRetencion().add(retencion);
        impuestos.setRetenciones(retenciones);
        return impuestos;
    }

    private List<Comprobante.Conceptos.Concepto.InformacionAduanera> createInformacionAduanera() {
        List<Comprobante.Conceptos.Concepto.InformacionAduanera> informacionAduaneraList = new ArrayList<>();
        Comprobante.Conceptos.Concepto.InformacionAduanera ia = of.createComprobanteConceptosConceptoInformacionAduanera();
        ia.setNumeroPedimento("67  52  3924  8060097");
        informacionAduaneraList.add(ia);
        return informacionAduaneraList;
    }

    private List<Comprobante.Conceptos.Concepto.CuentaPredial> createCuentaPredial() {
        List<Comprobante.Conceptos.Concepto.CuentaPredial> cuentaPredialList = new ArrayList<>();
        Comprobante.Conceptos.Concepto.CuentaPredial cup = of.createComprobanteConceptosConceptoCuentaPredial();
        cup.setNumero("123456");
        cuentaPredialList.add(cup);
        return cuentaPredialList;
    }

    private Comprobante.Impuestos createImpuestos() {
        Comprobante.Impuestos impuestos = of.createComprobanteImpuestos();
        impuestos.setTotalImpuestosTrasladados(new BigDecimal("160.00"));
        impuestos.setTotalImpuestosRetenidos(new BigDecimal("160.00"));

        Comprobante.Impuestos.Retenciones retenciones = of.createComprobanteImpuestosRetenciones();
        List<Comprobante.Impuestos.Retenciones.Retencion> retencionList = retenciones.getRetencion();
        Comprobante.Impuestos.Retenciones.Retencion retencion = of.createComprobanteImpuestosRetencionesRetencion();
        retencion.setImpuesto(CImpuesto.VALUE_2);
        retencion.setImporte(new BigDecimal("160.00"));
        retencionList.add(retencion);
        impuestos.setRetenciones(retenciones);

        Comprobante.Impuestos.Traslados traslados = of.createComprobanteImpuestosTraslados();
        List<Comprobante.Impuestos.Traslados.Traslado> trasladosList = traslados.getTraslado();
        Comprobante.Impuestos.Traslados.Traslado traslado = of.createComprobanteImpuestosTrasladosTraslado();
        traslado.setBase(new BigDecimal("1000"));
        traslado.setImpuesto(CImpuesto.VALUE_2);
        traslado.setTipoFactor(CTipoFactor.TASA);
        traslado.setTasaOCuota(new BigDecimal("0.16"));
        traslado.setImporte(new BigDecimal("160.00"));
        trasladosList.add(traslado);
        impuestos.setTraslados(traslados);
        return impuestos;
    }

    public Comprobante.Complemento createComplemento() {
        return of.createComprobanteComplemento();
    }

}
