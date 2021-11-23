package mx.grupocorasa.sat.factory.cfd;

import mx.grupocorasa.sat.cfd._33.*;
import mx.grupocorasa.sat.common.nomina12.Nomina;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import java.math.BigDecimal;
import java.util.List;

public class ExampleCFDv33Factory {

    private static ExampleCFDv33Factory instance;

    private ObjectFactory of;
    private DatatypeFactory datatypeFactory;

    private ExampleCFDv33Factory() {
        try {
            of = new ObjectFactory();
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static ExampleCFDv33Factory getInstance() {
        if (instance == null) {
            instance = new ExampleCFDv33Factory();
        }
        return instance;
    }

    public Comprobante createComprobante() {
        Comprobante comp = of.createComprobante();
        comp.setVersion("3.3");
        comp.setSerie("FFF");
        comp.setFolio("12345");
        comp.setFecha(datatypeFactory.newXMLGregorianCalendar(2021, 1, 1, 14, 54, 56, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED));
//        comp.setSello();
        comp.setFormaPago(CFormaPago.VALUE_8);
        comp.setNoCertificado("30001000000400002463");
//        comp.setCertificado();
        comp.setCondicionesDePago("Crédito a 20 días");
        comp.setSubTotal(new BigDecimal("2200.00"));
        comp.setDescuento(new BigDecimal("200.00"));
        comp.setMoneda(CMoneda.MXN);
        comp.setTipoCambio(new BigDecimal("1"));
        comp.setTotal(new BigDecimal("2000"));
        comp.setTipoDeComprobante(CTipoDeComprobante.I);
        comp.setMetodoPago(CMetodoPago.PUE);
        comp.setLugarExpedicion("83000");
        comp.setConfirmacion("aB1cD");
        comp.setCfdiRelacionados(createCfdiRelacionados());
        comp.setEmisor(createEmisor());
        comp.setReceptor(createReceptor());
        comp.setConceptos(createConceptos());
        comp.setImpuestos(createImpuestos());
        return comp;
    }

    public Comprobante createComprobanteNomina(Nomina nomina) {
        Comprobante comp = of.createComprobante();
        comp.setVersion("3.3");
        comp.setSerie("NNN");
        comp.setFolio("54321");
        comp.setFecha(datatypeFactory.newXMLGregorianCalendar(2021, 1, 1, 14, 54, 56, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED));
//        comp.setSello();
        comp.setFormaPago(CFormaPago.VALUE_22);
//        comp.setNoCertificado();
//        comp.setCertificado();
        comp.setSubTotal(nomina.getPercepciones().getPercepcion().stream().map(per -> per.getImporteGravado().add(per.getImporteExento())).reduce(BigDecimal.ZERO, BigDecimal::add));
        comp.setDescuento(nomina.getDeducciones().getDeduccion().stream().map(Nomina.Deducciones.Deduccion::getImporte).reduce(BigDecimal.ZERO, BigDecimal::add));
        comp.setMoneda(CMoneda.MXN);
        comp.setTotal(comp.getSubTotal().subtract(comp.getDescuento()));
        comp.setTipoDeComprobante(CTipoDeComprobante.N);
        comp.setMetodoPago(CMetodoPago.PUE);
        comp.setLugarExpedicion("83000");
        comp.setConfirmacion("ECVH1");
        comp.setCfdiRelacionados(createCfdiRelacionados());
        comp.setEmisor(createEmisor());
        comp.setReceptor(createReceptorNomina());
        comp.setConceptos(createConceptoNomina(nomina));
        return comp;
    }

    public Comprobante createComplementoPago() {
        Comprobante comp = of.createComprobante();
        comp.setVersion("3.3");
        comp.setSerie("PPP");
        comp.setFolio("34152");
        comp.setFecha(datatypeFactory.newXMLGregorianCalendar(2021, 3, 11, 14, 54, 56, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED));
//        comp.setSello();
//        comp.setNoCertificado();
//        comp.setCertificado();
        comp.setSubTotal(BigDecimal.ZERO);
        comp.setMoneda(CMoneda.XXX);
        comp.setTotal(BigDecimal.ZERO);
        comp.setTipoDeComprobante(CTipoDeComprobante.P);
        comp.setLugarExpedicion("83000");
        comp.setConfirmacion("ECVH1");
        comp.setCfdiRelacionados(createCfdiRelacionados());
        comp.setEmisor(createEmisor());
        comp.setReceptor(createReceptorNomina());
        comp.setConceptos(createConceptoComplementoPago());
        return comp;
    }

    private Comprobante.CfdiRelacionados createCfdiRelacionados() {
        Comprobante.CfdiRelacionados cfdiRelacionados = of.createComprobanteCfdiRelacionados();
        cfdiRelacionados.setTipoRelacion(CTipoRelacion.VALUE_6);
        Comprobante.CfdiRelacionados.CfdiRelacionado cfdiRelacionado1 = of.createComprobanteCfdiRelacionadosCfdiRelacionado();
        cfdiRelacionado1.setUUID("248C01A6-A08C-4821-BFAF-F1D04C0A0DC5");
        cfdiRelacionados.getCfdiRelacionado().add(cfdiRelacionado1);
        Comprobante.CfdiRelacionados.CfdiRelacionado cfdiRelacionado2 = of.createComprobanteCfdiRelacionadosCfdiRelacionado();
        cfdiRelacionado2.setUUID("4A3CC52B-4F7A-4FAF-9375-9905D535CB07");
        cfdiRelacionados.getCfdiRelacionado().add(cfdiRelacionado2);
        return cfdiRelacionados;
    }

    private Comprobante.Emisor createEmisor() {
        Comprobante.Emisor emisor = of.createComprobanteEmisor();
        emisor.setNombre("PHARMA PLUS SA DE CV");
        emisor.setRfc("PPL961114GZ1");
        emisor.setRegimenFiscal(CRegimenFiscal.VALUE_4);
        return emisor;
    }

    private Comprobante.Receptor createReceptor() {
        Comprobante.Receptor receptor = of.createComprobanteReceptor();
        receptor.setRfc("PEPJ8001019Q8");
        receptor.setNombre("JUAN PEREZ PEREZ");
        receptor.setResidenciaFiscal(CPais.MEX);
        receptor.setNumRegIdTrib("ResidenteExtranjero1");
        receptor.setUsoCFDI(CUsoCFDI.D_08);
        return receptor;
    }

    private Comprobante.Receptor createReceptorNomina() {
        Comprobante.Receptor receptor = of.createComprobanteReceptor();
        receptor.setRfc("OEGH910609BZ6");
        receptor.setNombre("Heriberto Ortega Gonzalez");
        receptor.setUsoCFDI(CUsoCFDI.P_01);
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
        c1.setImpuestos(createImpuestosConceptos());
        c1.getInformacionAduanera().add(createInformacionAduanera());
        c1.setCuentaPredial(createCuentaPredial());
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
        c2.setImpuestos(createImpuestosConceptos());
        c2.getInformacionAduanera().add(createInformacionAduanera());
        c2.setCuentaPredial(createCuentaPredial());
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

    private Comprobante.Conceptos.Concepto.InformacionAduanera createInformacionAduanera() {
        Comprobante.Conceptos.Concepto.InformacionAduanera ia = of.createComprobanteConceptosConceptoInformacionAduanera();
        ia.setNumeroPedimento("67  52  3924  8060097");
        return ia;
    }

    private Comprobante.Conceptos.Concepto.CuentaPredial createCuentaPredial() {
        Comprobante.Conceptos.Concepto.CuentaPredial cup = of.createComprobanteConceptosConceptoCuentaPredial();
        cup.setNumero("123456");
        return cup;
    }

    private Comprobante.Conceptos createConceptoNomina(Nomina nomina) {
        BigDecimal value = BigDecimal.ZERO;
        if (nomina.getTotalPercepciones() != null) value = value.add(nomina.getTotalPercepciones());
        if (nomina.getTotalOtrosPagos() != null) value = value.add(nomina.getTotalOtrosPagos());

        Comprobante.Conceptos conceptos = of.createComprobanteConceptos();
        Comprobante.Conceptos.Concepto concepto = of.createComprobanteConceptosConcepto();
        concepto.setClaveProdServ("84111505");
        concepto.setCantidad(BigDecimal.ONE);
        concepto.setClaveUnidad(CClaveUnidad.VALUE_241);
        concepto.setDescripcion("Pago de nómina");
        concepto.setValorUnitario(value);
        concepto.setImporte(concepto.getValorUnitario());
        concepto.setDescuento(nomina.getTotalDeducciones());
        conceptos.getConcepto().add(concepto);
        return conceptos;
    }

    private Comprobante.Conceptos createConceptoComplementoPago() {
        Comprobante.Conceptos conceptos = of.createComprobanteConceptos();
        Comprobante.Conceptos.Concepto concepto = of.createComprobanteConceptosConcepto();
        concepto.setClaveProdServ("84111506");
        concepto.setCantidad(BigDecimal.ONE);
        concepto.setClaveUnidad(CClaveUnidad.VALUE_241);
        concepto.setDescripcion("Pago");
        concepto.setValorUnitario(BigDecimal.ZERO);
        concepto.setImporte(BigDecimal.ZERO);
        conceptos.getConcepto().add(concepto);
        return conceptos;
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
