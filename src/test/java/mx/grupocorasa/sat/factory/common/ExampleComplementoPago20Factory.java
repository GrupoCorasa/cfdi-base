package mx.grupocorasa.sat.factory.common;

import mx.grupocorasa.sat.common.Pagos20.ObjectFactory;
import mx.grupocorasa.sat.common.Pagos20.Pagos;
import mx.grupocorasa.sat.common.catalogos.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ExampleComplementoPago20Factory {

    private static ExampleComplementoPago20Factory instance;

    private final ObjectFactory of;

    private ExampleComplementoPago20Factory() {
        of = new ObjectFactory();
    }

    public static ExampleComplementoPago20Factory getInstance() {
        if (instance == null) {
            instance = new ExampleComplementoPago20Factory();
        }
        return instance;
    }

    public Pagos createComplementoPago() {
        Pagos pagos = of.createPagos();
        pagos.setVersion("2.0");
        pagos.getPago().add(createPago1());
        pagos.setTotales(createTotales(pagos));
        return pagos;
    }

    private Pagos.Pago createPago1() {
        Pagos.Pago pago = of.createPagosPago();
        pago.setFechaPago(LocalDateTime.of(2022, 2, 2, 14, 43, 57));
        pago.setFormaDePagoP(CFormaPago.VALUE_3);
        pago.setMonedaP(CMoneda.MXN);
        pago.setTipoCambioP(BigDecimal.ONE);
        pago.setMonto(new BigDecimal("3000"));
        pago.setNumOperacion("ABC123");
        pago.setRfcEmisorCtaOrd("ABC010101000");
        pago.setNomBancoOrdExt("Razón Social Banco Ordenante");
        pago.setCtaOrdenante("123456789012345678");
        pago.setRfcEmisorCtaBen("CBA010101000");
        pago.setCtaBeneficiario("876543210987654321");
//        pago.setTipoCadPago(CTipoCadenaPago.VALUE_1);
//        pago.setCertPago();
//        pago.setCadPago();
//        pago.setSelloPago();
        Pagos.Pago.DoctoRelacionado doc1 = of.createPagosPagoDoctoRelacionado();
        doc1.setIdDocumento("7c5edff2-f8d1-5298-9685-5e288bac9841");
        doc1.setSerie("BBB");
        doc1.setFolio("898");
        doc1.setMonedaDR(CMoneda.MXN);
        doc1.setEquivalenciaDR(new BigDecimal("1"));
        doc1.setNumParcialidad(new BigInteger("2"));
        doc1.setImpSaldoAnt(new BigDecimal("1500"));
        doc1.setImpPagado(new BigDecimal("1500"));
        doc1.setImpSaldoInsoluto(BigDecimal.ZERO);
        doc1.setObjetoImpDR(CObjetoImp.VALUE_2);
        doc1.setImpuestosDR(createImpuestosDR(doc1));
        pago.getDoctoRelacionado().add(doc1);
        Pagos.Pago.DoctoRelacionado doc2 = of.createPagosPagoDoctoRelacionado();
        doc2.setIdDocumento("153e2c1a-9901-4fc2-aa92-54bf6add80c4");
        doc2.setSerie("BBB");
        doc2.setFolio("899");
        doc2.setMonedaDR(CMoneda.MXN);
        doc2.setEquivalenciaDR(new BigDecimal("1"));
        doc2.setNumParcialidad(new BigInteger("3"));
        doc2.setImpSaldoAnt(new BigDecimal("1000"));
        doc2.setImpPagado(new BigDecimal("1000"));
        doc2.setImpSaldoInsoluto(BigDecimal.ZERO);
        doc2.setObjetoImpDR(CObjetoImp.VALUE_2);
        doc2.setImpuestosDR(createImpuestosDR(doc2));
        pago.getDoctoRelacionado().add(doc2);
        Pagos.Pago.DoctoRelacionado doc3 = of.createPagosPagoDoctoRelacionado();
        doc3.setIdDocumento("47bdcbab-97be-4195-a674-043e33970121");
        doc3.setSerie("BBB");
        doc3.setFolio("999");
        doc3.setMonedaDR(CMoneda.MXN);
        doc3.setEquivalenciaDR(new BigDecimal("1"));
        doc3.setNumParcialidad(new BigInteger("4"));
        doc3.setImpSaldoAnt(new BigDecimal("500"));
        doc3.setImpPagado(new BigDecimal("500"));
        doc3.setImpSaldoInsoluto(BigDecimal.ZERO);
        doc3.setObjetoImpDR(CObjetoImp.VALUE_2);
        doc3.setImpuestosDR(createImpuestosDR(doc3));
        pago.getDoctoRelacionado().add(doc3);

        pago.setImpuestosP(createImpuestosP(pago));
        return pago;
    }

    private Pagos.Pago.ImpuestosP createImpuestosP(Pagos.Pago pago) {
        Pagos.Pago.ImpuestosP impuestosP = of.createPagosPagoImpuestosP();

        Pagos.Pago.ImpuestosP.RetencionesP retencionesP = of.createPagosPagoImpuestosPRetencionesP();
        pago.getDoctoRelacionado().stream()
                .map(Pagos.Pago.DoctoRelacionado::getImpuestosDR)
                .map(Pagos.Pago.DoctoRelacionado.ImpuestosDR::getRetencionesDR)
                .map(Pagos.Pago.DoctoRelacionado.ImpuestosDR.RetencionesDR::getRetencionDR)
                .flatMap(List::stream)
                .map(Pagos.Pago.DoctoRelacionado.ImpuestosDR.RetencionesDR.RetencionDR::getImpuestoDR)
                .distinct().forEach(cImpuesto -> {
                    Pagos.Pago.ImpuestosP.RetencionesP.RetencionP retencionP = of.createPagosPagoImpuestosPRetencionesPRetencionP();
                    retencionP.setImpuestoP(cImpuesto);
                    retencionP.setImporteP(pago.getDoctoRelacionado().stream()
                            .map(Pagos.Pago.DoctoRelacionado::getImpuestosDR)
                            .map(Pagos.Pago.DoctoRelacionado.ImpuestosDR::getRetencionesDR)
                            .map(Pagos.Pago.DoctoRelacionado.ImpuestosDR.RetencionesDR::getRetencionDR)
                            .flatMap(List::stream)
                            .map(Pagos.Pago.DoctoRelacionado.ImpuestosDR.RetencionesDR.RetencionDR::getImporteDR)
                            .reduce(BigDecimal.ZERO, BigDecimal::add));
                    retencionesP.getRetencionP().add(retencionP);
                });
        impuestosP.setRetencionesP(retencionesP);

        Pagos.Pago.ImpuestosP.TrasladosP trasladosP = of.createPagosPagoImpuestosPTrasladosP();
        pago.getDoctoRelacionado().stream()
                .map(Pagos.Pago.DoctoRelacionado::getImpuestosDR)
                .map(Pagos.Pago.DoctoRelacionado.ImpuestosDR::getTrasladosDR)
                .map(Pagos.Pago.DoctoRelacionado.ImpuestosDR.TrasladosDR::getTrasladoDR)
                .flatMap(List::stream)
                .map(Pagos.Pago.DoctoRelacionado.ImpuestosDR.TrasladosDR.TrasladoDR::getImpuestoDR)
                .distinct().forEach(cImpuesto -> {
                    List<Pagos.Pago.DoctoRelacionado.ImpuestosDR.TrasladosDR.TrasladoDR> list = pago.getDoctoRelacionado().stream()
                            .map(Pagos.Pago.DoctoRelacionado::getImpuestosDR)
                            .map(Pagos.Pago.DoctoRelacionado.ImpuestosDR::getTrasladosDR)
                            .map(Pagos.Pago.DoctoRelacionado.ImpuestosDR.TrasladosDR::getTrasladoDR)
                            .flatMap(List::stream).collect(Collectors.toList());
                    Pagos.Pago.ImpuestosP.TrasladosP.TrasladoP trasladoP = of.createPagosPagoImpuestosPTrasladosPTrasladoP();
                    trasladoP.setBaseP(list.stream().map(Pagos.Pago.DoctoRelacionado.ImpuestosDR.TrasladosDR.TrasladoDR::getBaseDR).reduce(BigDecimal.ZERO, BigDecimal::add));
                    trasladoP.setImpuestoP(cImpuesto);
                    trasladoP.setTipoFactorP(list.stream().findAny().get().getTipoFactorDR());
                    trasladoP.setTasaOCuotaP(list.stream().findAny().get().getTasaOCuotaDR());
                    trasladoP.setImporteP(list.stream().map(Pagos.Pago.DoctoRelacionado.ImpuestosDR.TrasladosDR.TrasladoDR::getImporteDR).reduce(BigDecimal.ZERO, BigDecimal::add));
                    trasladosP.getTrasladoP().add(trasladoP);
                });
        impuestosP.setTrasladosP(trasladosP);

        return impuestosP;
    }

    private Pagos.Pago.DoctoRelacionado.ImpuestosDR createImpuestosDR(Pagos.Pago.DoctoRelacionado doc1) {
        Pagos.Pago.DoctoRelacionado.ImpuestosDR impuestosDR = of.createPagosPagoDoctoRelacionadoImpuestosDR();

        Pagos.Pago.DoctoRelacionado.ImpuestosDR.RetencionesDR retencionesDR = of.createPagosPagoDoctoRelacionadoImpuestosDRRetencionesDR();
        Pagos.Pago.DoctoRelacionado.ImpuestosDR.RetencionesDR.RetencionDR retencionDR = of.createPagosPagoDoctoRelacionadoImpuestosDRRetencionesDRRetencionDR();
        retencionDR.setBaseDR(doc1.getImpPagado());
        retencionDR.setImpuestoDR(CImpuesto.VALUE_2);
        retencionDR.setTipoFactorDR(CTipoFactor.TASA);
        retencionDR.setTasaOCuotaDR(new BigDecimal("0.160000"));
        retencionDR.setImporteDR(doc1.getImpPagado().multiply(retencionDR.getTasaOCuotaDR()).setScale(2, RoundingMode.HALF_UP));
        retencionesDR.getRetencionDR().add(retencionDR);
        impuestosDR.setRetencionesDR(retencionesDR);

        Pagos.Pago.DoctoRelacionado.ImpuestosDR.TrasladosDR trasladosDR = of.createPagosPagoDoctoRelacionadoImpuestosDRTrasladosDR();
        Pagos.Pago.DoctoRelacionado.ImpuestosDR.TrasladosDR.TrasladoDR trasladoDR = of.createPagosPagoDoctoRelacionadoImpuestosDRTrasladosDRTrasladoDR();
        trasladoDR.setBaseDR(doc1.getImpPagado());
        trasladoDR.setImpuestoDR(CImpuesto.VALUE_2);
        trasladoDR.setTipoFactorDR(CTipoFactor.TASA);
        trasladoDR.setTasaOCuotaDR(new BigDecimal("0.160000"));
        trasladoDR.setImporteDR(doc1.getImpPagado().multiply(trasladoDR.getTasaOCuotaDR()).setScale(2, RoundingMode.HALF_UP));
        trasladosDR.getTrasladoDR().add(trasladoDR);
        impuestosDR.setTrasladosDR(trasladosDR);

        return impuestosDR;
    }

    private Pagos.Totales createTotales(Pagos pagos) {
        Pagos.Totales totales = of.createPagosTotales();
        totales.setTotalRetencionesIVA(pagos.getPago().stream()
                .map(Pagos.Pago::getImpuestosP)
                .map(Pagos.Pago.ImpuestosP::getRetencionesP)
                .map(Pagos.Pago.ImpuestosP.RetencionesP::getRetencionP)
                .flatMap(List::stream)
                .map(Pagos.Pago.ImpuestosP.RetencionesP.RetencionP::getImporteP)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        totales.setTotalTrasladosBaseIVA16(pagos.getPago().stream()
                .map(Pagos.Pago::getImpuestosP)
                .map(Pagos.Pago.ImpuestosP::getTrasladosP)
                .map(Pagos.Pago.ImpuestosP.TrasladosP::getTrasladoP)
                .flatMap(List::stream)
                .map(Pagos.Pago.ImpuestosP.TrasladosP.TrasladoP::getBaseP)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        totales.setTotalTrasladosImpuestoIVA16(pagos.getPago().stream()
                .map(Pagos.Pago::getImpuestosP)
                .map(Pagos.Pago.ImpuestosP::getTrasladosP)
                .map(Pagos.Pago.ImpuestosP.TrasladosP::getTrasladoP)
                .flatMap(List::stream)
                .map(Pagos.Pago.ImpuestosP.TrasladosP.TrasladoP::getImporteP)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        totales.setMontoTotalPagos(pagos.getPago().stream().map(Pagos.Pago::getMonto).reduce(BigDecimal.ZERO, BigDecimal::add));
        return totales;
    }
}
