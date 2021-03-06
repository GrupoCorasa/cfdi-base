package mx.grupocorasa.sat.factory.common;

import mx.grupocorasa.sat.common.Pagos10.ObjectFactory;
import mx.grupocorasa.sat.common.Pagos10.Pagos;
import mx.grupocorasa.sat.common.catalogos.*;
import mx.grupocorasa.sat.common.catalogos.Pagos.CTipoCadenaPago;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

public class ExampleComplementoPagoFactory {

    private static ExampleComplementoPagoFactory instance;

    private final ObjectFactory of;

    private ExampleComplementoPagoFactory() {
        of = new ObjectFactory();
    }

    public static ExampleComplementoPagoFactory getInstance() {
        if (instance == null) {
            instance = new ExampleComplementoPagoFactory();
        }
        return instance;
    }

    public Pagos createComplementoPago() {
        Pagos pagos = of.createPagos();
        pagos.setVersion("1.0");
        pagos.getPago().add(createPago1());
        pagos.getPago().add(createPago2());
        return pagos;
    }

    private Pagos.Pago createPago1() {
        Pagos.Pago pago = of.createPagosPago();
        pago.setFechaPago(LocalDateTime.of(2020, 1, 1, 13, 42, 56));
        pago.setFormaDePagoP(CFormaPago.VALUE_4);
        pago.setMonedaP(CMoneda.MXN);
        pago.setTipoCambioP(BigDecimal.ONE);
        pago.setMonto(new BigDecimal("3000"));
        pago.setNumOperacion("ABC123");
        pago.setRfcEmisorCtaOrd("ABC010101000");
        pago.setNomBancoOrdExt("Razón Social Banco Ordenante");
        pago.setCtaOrdenante("123456789012345678");
        pago.setRfcEmisorCtaBen("CBA010101000");
        pago.setCtaBeneficiario("876543210987654321");
        pago.setTipoCadPago(CTipoCadenaPago.VALUE_1);
//        pago.setCertPago();
//        pago.setCadPago();
//        pago.setSelloPago();
        Pagos.Pago.DoctoRelacionado doc1 = of.createPagosPagoDoctoRelacionado();
        doc1.setIdDocumento("8c6edff3-f9d0-4187-8574-4e199bac8730");
        doc1.setSerie("AAA");
        doc1.setFolio("987");
        doc1.setMonedaDR(CMoneda.MXN);
        doc1.setTipoCambioDR(BigDecimal.ONE);
        doc1.setMetodoDePagoDR(CMetodoPago.PPD);
        doc1.setNumParcialidad(new BigInteger("1"));
        doc1.setImpSaldoAnt(new BigDecimal("1500"));
        doc1.setImpPagado(new BigDecimal("1500"));
        doc1.setImpSaldoInsoluto(BigDecimal.ZERO);
        pago.getDoctoRelacionado().add(doc1);
        Pagos.Pago.DoctoRelacionado doc2 = of.createPagosPagoDoctoRelacionado();
        doc2.setIdDocumento("153e2c1a-9901-4fc2-aa92-54bf6add80c4");
        doc2.setSerie("AAA");
        doc2.setFolio("988");
        doc2.setMonedaDR(CMoneda.MXN);
        doc2.setTipoCambioDR(BigDecimal.ONE);
        doc2.setMetodoDePagoDR(CMetodoPago.PPD);
        doc2.setNumParcialidad(new BigInteger("1"));
        doc2.setImpSaldoAnt(new BigDecimal("1000"));
        doc2.setImpPagado(new BigDecimal("1000"));
        doc2.setImpSaldoInsoluto(BigDecimal.ZERO);
        pago.getDoctoRelacionado().add(doc2);
        Pagos.Pago.DoctoRelacionado doc3 = of.createPagosPagoDoctoRelacionado();
        doc3.setIdDocumento("47bdcbab-97be-4195-a674-043e33970121");
        doc3.setSerie("AAA");
        doc3.setFolio("989");
        doc3.setMonedaDR(CMoneda.MXN);
        doc3.setTipoCambioDR(BigDecimal.ONE);
        doc3.setMetodoDePagoDR(CMetodoPago.PPD);
        doc3.setNumParcialidad(new BigInteger("1"));
        doc3.setImpSaldoAnt(new BigDecimal("500"));
        doc3.setImpPagado(new BigDecimal("500"));
        doc3.setImpSaldoInsoluto(BigDecimal.ZERO);
        pago.getDoctoRelacionado().add(doc3);
        Pagos.Pago.Impuestos impuesto = of.createPagosPagoImpuestos();
        impuesto.setTotalImpuestosRetenidos(new BigDecimal("123"));
        Pagos.Pago.Impuestos.Retenciones retenciones = of.createPagosPagoImpuestosRetenciones();
        Pagos.Pago.Impuestos.Retenciones.Retencion retencion = of.createPagosPagoImpuestosRetencionesRetencion();
        retencion.setImporte(new BigDecimal("123"));
        retencion.setImpuesto(CImpuesto.VALUE_2);
        retenciones.getRetencion().add(retencion);
        impuesto.setRetenciones(retenciones);
        impuesto.setTotalImpuestosTrasladados(new BigDecimal("123"));
        Pagos.Pago.Impuestos.Traslados trasladados = of.createPagosPagoImpuestosTraslados();
        Pagos.Pago.Impuestos.Traslados.Traslado traslado = of.createPagosPagoImpuestosTrasladosTraslado();
        traslado.setImpuesto(CImpuesto.VALUE_2);
        traslado.setTipoFactor(CTipoFactor.TASA);
        traslado.setTasaOCuota(new BigDecimal("0.16"));
        traslado.setImporte(new BigDecimal("123"));
        trasladados.getTraslado().add(traslado);
        impuesto.setTraslados(trasladados);
        pago.getImpuestos().add(impuesto);
        return pago;
    }

    private Pagos.Pago createPago2() {
        Pagos.Pago pago = of.createPagosPago();
        pago.setFechaPago(LocalDateTime.of(2020, 2, 1, 13, 42, 56));
        pago.setFormaDePagoP(CFormaPago.VALUE_3);
        pago.setMonedaP(CMoneda.MXN);
        pago.setTipoCambioP(BigDecimal.ONE);
        pago.setMonto(new BigDecimal("2000"));
        pago.setNumOperacion("ABC321");
        pago.setRfcEmisorCtaOrd("AAA010101000");
        pago.setNomBancoOrdExt("Razón Social Banco Ordenante");
        pago.setCtaOrdenante("123456789012345679");
        pago.setRfcEmisorCtaBen("BBB010101000");
        pago.setCtaBeneficiario("976543210987654321");
        pago.setTipoCadPago(CTipoCadenaPago.VALUE_1);
//        pago.setCertPago();
//        pago.setCadPago();
//        pago.setSelloPago();
        Pagos.Pago.DoctoRelacionado doc1 = of.createPagosPagoDoctoRelacionado();
        doc1.setIdDocumento("0121a674-97be-4195-cbab-043e339747bd");
        doc1.setSerie("BBB");
        doc1.setFolio("987");
        doc1.setMonedaDR(CMoneda.MXN);
        doc1.setTipoCambioDR(BigDecimal.ONE);
        doc1.setMetodoDePagoDR(CMetodoPago.PPD);
        doc1.setNumParcialidad(new BigInteger("1"));
        doc1.setImpSaldoAnt(new BigDecimal("1500"));
        doc1.setImpPagado(new BigDecimal("1500"));
        doc1.setImpSaldoInsoluto(BigDecimal.ZERO);
        pago.getDoctoRelacionado().add(doc1);
        Pagos.Pago.DoctoRelacionado doc2 = of.createPagosPagoDoctoRelacionado();
        doc2.setIdDocumento("80c42c1a-9901-4fc2-aa92-54bf6add153e");
        doc2.setSerie("BBB");
        doc2.setFolio("989");
        doc2.setMonedaDR(CMoneda.MXN);
        doc2.setTipoCambioDR(BigDecimal.ONE);
        doc2.setMetodoDePagoDR(CMetodoPago.PPD);
        doc2.setNumParcialidad(new BigInteger("1"));
        doc2.setImpSaldoAnt(new BigDecimal("500"));
        doc2.setImpPagado(new BigDecimal("500"));
        doc2.setImpSaldoInsoluto(BigDecimal.ZERO);
        pago.getDoctoRelacionado().add(doc2);
        return pago;
    }

}
