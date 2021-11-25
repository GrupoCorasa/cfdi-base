package mx.grupocorasa.sat.factory.cfd;

import mx.grupocorasa.sat.cfd._22.Comprobante;
import mx.grupocorasa.sat.cfd._22.ObjectFactory;
import mx.grupocorasa.sat.cfd._22.TUbicacion;
import mx.grupocorasa.sat.cfd._22.TUbicacionFiscal;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

public class ExampleCFDv22Factory {

    private static ExampleCFDv22Factory instance;

    private final ObjectFactory of;

    private ExampleCFDv22Factory() {
        of = new ObjectFactory();
    }

    public static ExampleCFDv22Factory getInstance() {
        if (instance == null) {
            instance = new ExampleCFDv22Factory();
        }
        return instance;
    }

    public Comprobante createComprobante() {
        Comprobante comp = of.createComprobante();
        comp.setVersion("2.2");
        comp.setSerie("DCBA");
        comp.setFolio("81");
        comp.setFecha(LocalDateTime.of(2012, 3, 11, 17, 8, 22));
//        comp.setSello();
        comp.setNoAprobacion(new BigInteger("448911"));
        comp.setAnoAprobacion(new BigInteger("2011"));
        comp.setFormaDePago("PAGO EN PARCIALIDADES");
//        comp.setNoCertificado();
//        comp.setCertificado();
        comp.setCondicionesDePago("Factura a Crédito");
        comp.setSubTotal(new BigDecimal("250.00"));
        comp.setDescuento(new BigDecimal("150.00"));
        comp.setMotivoDescuento("Cliente de confianza");
        comp.setTipoCambio("1.00");
        comp.setTotal(new BigDecimal("124.00"));
        comp.setMetodoDePago("Transferencia Electrónica");
        comp.setTipoDeComprobante("ingreso");
        comp.setLugarExpedicion("Hermosillo, Sonora, México");
        comp.setNumCtaPago("1587");
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

    private Comprobante.Conceptos createConceptos() {
        Comprobante.Conceptos cps = of.createComprobanteConceptos();
        Comprobante.Conceptos.Concepto c1 = of.createComprobanteConceptosConcepto();
        c1.setUnidad("Servicio");
        c1.setNoIdentificacion("01");
        c1.setImporte(new BigDecimal("150.00"));
        c1.setCantidad(new BigDecimal("1.00"));
        c1.setDescripcion("Asesoria Fiscal y administrativa");
        c1.setValorUnitario(new BigDecimal("150.00"));
        cps.getConcepto().add(c1);
        Comprobante.Conceptos.Concepto c2 = of.createComprobanteConceptosConcepto();
        c2.setUnidad("Servicio");
        c2.setNoIdentificacion("02");
        c2.setImporte(new BigDecimal("100.00"));
        c2.setCantidad(new BigDecimal("2.00"));
        c2.setDescripcion("Asesoria Fiscal y administrativa 2");
        c2.setValorUnitario(new BigDecimal("50.00"));
        cps.getConcepto().add(c2);
        return cps;
    }

    private Comprobante.Impuestos createImpuestos() {
        Comprobante.Impuestos imps = of.createComprobanteImpuestos();
        imps.setTotalImpuestosTrasladados(new BigDecimal("16.00"));
        Comprobante.Impuestos.Traslados trs = of.createComprobanteImpuestosTraslados();
        List<Comprobante.Impuestos.Traslados.Traslado> list = trs.getTraslado();
        Comprobante.Impuestos.Traslados.Traslado t1 = of.createComprobanteImpuestosTrasladosTraslado();
        t1.setImporte(new BigDecimal("16.00"));
        t1.setImpuesto("IVA");
        t1.setTasa(new BigDecimal("16.00"));
        list.add(t1);
        imps.setTraslados(trs);
        imps.setTotalImpuestosTrasladados(new BigDecimal("16.00"));
        Comprobante.Impuestos.Retenciones ret = of.createComprobanteImpuestosRetenciones();
        List<Comprobante.Impuestos.Retenciones.Retencion> listRet = ret.getRetencion();
        Comprobante.Impuestos.Retenciones.Retencion r1 = of.createComprobanteImpuestosRetencionesRetencion();
        r1.setImporte(new BigDecimal("4.00"));
        r1.setImpuesto("IVA");
        listRet.add(r1);
        imps.setRetenciones(ret);
        return imps;
    }

    public Comprobante.Complemento createComplemento() {
        return of.createComprobanteComplemento();
    }

}
