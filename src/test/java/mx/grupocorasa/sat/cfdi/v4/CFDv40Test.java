package mx.grupocorasa.sat.cfdi.v4;

import mx.grupocorasa.sat.cfd._40.Comprobante;
import mx.grupocorasa.sat.common.Pagos20.Pagos;
import mx.grupocorasa.sat.common.TimbreFiscalDigital11.TimbreFiscalDigital;
import mx.grupocorasa.sat.common.ValidationErrorHandler;
import mx.grupocorasa.sat.factory.cfd.ExampleCFDv40Factory;
import mx.grupocorasa.sat.factory.common.ExampleComplementoPago20Factory;
import mx.grupocorasa.sat.factory.common.ExampleTimbreFiscal11Factory;
import mx.grupocorasa.sat.security.KeyLoaderEnumeration;
import mx.grupocorasa.sat.security.factory.KeyLoaderFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Objects;

public final class CFDv40Test {

    private static PrivateKey key;
    private static X509Certificate cert;

    @BeforeClass
    public static void loadKeys() {
        key = KeyLoaderFactory.createInstance(
                KeyLoaderEnumeration.PRIVATE_KEY_LOADER,
                "resources/certs/Personas Morales/FIEL_H&E951128469_20190620115342/CSD_H&E951128469_20190620154707/CSD_HERRERIA_&_ELECTRICOS_SA_DE_CV_H&E951128469_20190620_154653.key",
                "12345678a"
        ).getKey();
        cert = KeyLoaderFactory.createInstance(
                KeyLoaderEnumeration.PUBLIC_KEY_LOADER,
                "resources/certs/Personas Morales/FIEL_H&E951128469_20190620115342/CSD_H&E951128469_20190620154707/CSD_HERRERIA_&_ELECTRICOS_SA_DE_CV_H&E951128469_20190620_154653s.cer"
        ).getKey();
    }

    @Test
    public void testCreateCleanComprobante() throws Exception {
        CFDv40 cfd = new CFDv40(ExampleCFDv40Factory.getInstance().createComprobante());
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            cfd.guardar(byteArrayOutputStream, false);
            String comprobante = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><cfdi:Comprobante Version=\"4.0\" Serie=\"FFF\" Folio=\"12345\" Fecha=\"2021-01-01T14:54:56\" FormaPago=\"12\" CondicionesDePago=\"Crédito a 20 días\" SubTotal=\"2200.00\" Descuento=\"200.00\" Moneda=\"MXN\" TipoCambio=\"1\" Total=\"2000\" TipoDeComprobante=\"I\" Exportacion=\"02\" MetodoPago=\"PUE\" LugarExpedicion=\"83000\" Confirmacion=\"aB1cD\" xsi:schemaLocation=\"http://www.sat.gob.mx/cfd/4 http://www.sat.gob.mx/sitio_internet/cfd/4/cfdv40.xsd\" xmlns:cfdi=\"http://www.sat.gob.mx/cfd/4\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><cfdi:InformacionGlobal Periodicidad=\"03\" Meses=\"09\" Año=\"2022\"/><cfdi:CfdiRelacionados TipoRelacion=\"06\"><cfdi:CfdiRelacionado UUID=\"248C01A6-A08C-4821-BFAF-F1D04C0A0DC5\"/><cfdi:CfdiRelacionado UUID=\"4A3CC52B-4F7A-4FAF-9375-9905D535CB07\"/></cfdi:CfdiRelacionados><cfdi:CfdiRelacionados TipoRelacion=\"06\"><cfdi:CfdiRelacionado UUID=\"580A7432-900F-11EC-B909-0242AC120002\"/><cfdi:CfdiRelacionado UUID=\"223E7412-4F7A-4821-9375-F1D04C0A0DC5\"/></cfdi:CfdiRelacionados><cfdi:Emisor Rfc=\"PPL961114GZ1\" Nombre=\"PHARMA PLUS SA DE CV\" RegimenFiscal=\"606\" FacAtrAdquirente=\"0123456789\"/><cfdi:Receptor Rfc=\"PEPJ8001019Q8\" Nombre=\"JUAN PEREZ PEREZ\" DomicilioFiscalReceptor=\"83000\" ResidenciaFiscal=\"MEX\" NumRegIdTrib=\"ResidenteExtranjero1\" RegimenFiscalReceptor=\"607\" UsoCFDI=\"D08\"/><cfdi:Conceptos><cfdi:Concepto ClaveProdServ=\"10101501\" NoIdentificacion=\"PRU01\" Cantidad=\"1.0\" ClaveUnidad=\"C51\" Unidad=\"Servicio\" Descripcion=\"Concepto de Prueba 01\" ValorUnitario=\"1100.00\" Importe=\"1100.00\" Descuento=\"100.00\" ObjetoImp=\"02\"><cfdi:Impuestos><cfdi:Traslados><cfdi:Traslado Base=\"1000.00\" Impuesto=\"002\" TipoFactor=\"Tasa\" TasaOCuota=\"0.160000\" Importe=\"160.00\"/></cfdi:Traslados><cfdi:Retenciones><cfdi:Retencion Base=\"1000.00\" Impuesto=\"002\" TipoFactor=\"Tasa\" TasaOCuota=\"0.160000\" Importe=\"160.00\"/></cfdi:Retenciones></cfdi:Impuestos><cfdi:InformacionAduanera NumeroPedimento=\"67  52  3924  8060097\"/><cfdi:CuentaPredial Numero=\"123456\"/></cfdi:Concepto><cfdi:Concepto ClaveProdServ=\"10101502\" NoIdentificacion=\"PRU02\" Cantidad=\"1.0\" ClaveUnidad=\"C51\" Unidad=\"Servicio\" Descripcion=\"Concepto de Prueba 02\" ValorUnitario=\"1100.00\" Importe=\"1100.00\" Descuento=\"100.00\" ObjetoImp=\"02\"><cfdi:Impuestos><cfdi:Traslados><cfdi:Traslado Base=\"1000.00\" Impuesto=\"002\" TipoFactor=\"Tasa\" TasaOCuota=\"0.160000\" Importe=\"160.00\"/></cfdi:Traslados><cfdi:Retenciones><cfdi:Retencion Base=\"1000.00\" Impuesto=\"002\" TipoFactor=\"Tasa\" TasaOCuota=\"0.160000\" Importe=\"160.00\"/></cfdi:Retenciones></cfdi:Impuestos><cfdi:InformacionAduanera NumeroPedimento=\"67  52  3924  8060097\"/><cfdi:CuentaPredial Numero=\"123456\"/></cfdi:Concepto></cfdi:Conceptos><cfdi:Impuestos TotalImpuestosRetenidos=\"160.00\" TotalImpuestosTrasladados=\"160.00\"><cfdi:Retenciones><cfdi:Retencion Impuesto=\"002\" Importe=\"160.00\"/></cfdi:Retenciones><cfdi:Traslados><cfdi:Traslado Base=\"1000\" Impuesto=\"002\" TipoFactor=\"Tasa\" TasaOCuota=\"0.16\" Importe=\"160.00\"/></cfdi:Traslados></cfdi:Impuestos></cfdi:Comprobante>";
            Assert.assertEquals(comprobante, byteArrayOutputStream.toString("UTF-8"));
        }
    }

    @Test
    public void testCleanCadenaOriginal() throws Exception {
        CFDv40 cfd = new CFDv40(ExampleCFDv40Factory.getInstance().createComprobante());
        String cadena = "||4.0|FFF|12345|2021-01-01T14:54:56|12||Crédito a 20 días|2200.00|200.00|MXN|1|2000|I|02|PUE|83000|aB1cD|03|09|2022|06|248C01A6-A08C-4821-BFAF-F1D04C0A0DC5|4A3CC52B-4F7A-4FAF-9375-9905D535CB07|06|580A7432-900F-11EC-B909-0242AC120002|223E7412-4F7A-4821-9375-F1D04C0A0DC5|PPL961114GZ1|PHARMA PLUS SA DE CV|606|0123456789|PEPJ8001019Q8|JUAN PEREZ PEREZ|83000|MEX|ResidenteExtranjero1|607|D08|10101501|PRU01|1.0|C51|Servicio|Concepto de Prueba 01|1100.00|1100.00|100.00|02|1000.00|002|Tasa|0.160000|160.00|1000.00|002|Tasa|0.160000|160.00|67 52 3924 8060097|123456|10101502|PRU02|1.0|C51|Servicio|Concepto de Prueba 02|1100.00|1100.00|100.00|02|1000.00|002|Tasa|0.160000|160.00|1000.00|002|Tasa|0.160000|160.00|67 52 3924 8060097|123456|002|160.00|160.00|1000|002|Tasa|0.16|160.00|160.00||";
        Assert.assertEquals(cadena, cfd.getCadenaOriginal());
    }

    @Test
    public void testCleanSign() throws Exception {
        CFDv40 cfd = new CFDv40(ExampleCFDv40Factory.getInstance().createComprobante());
        Comprobante sellado = cfd.sellarComprobante(key, cert);
        String signature = "3Zj5p0nQTe6/uz+CwpTX0ADFKehgaBTzr2qQYaOyg0AooHT89tX8dWdQTgC7FfynD46x0mAj/tNFch2YhxQwk/8XwYgTLGc9X2hBG5LEiZ4zsqmdfnsa5nRYxWJFVAXSV6GMf5P3FCNAIHFMiuK97gYCKhBGFhbGy2fYfdOQzaPBfKz5HZGkzJJMDzgA89HVizIub+p9yqW72yR1/nh3sbT1DtLB0Qs79OGTo+TSYi3mrOHF+oiBCcblPar0/FtZMZnE11XZuFzCgcz/01iPUwReMPqbHjVGqTWcspnSzBk92RAUquzgGUwwyDRC3sIQ5frXEME7qG9kPh1MyGMhqQ==";
        Assert.assertEquals(signature, sellado.getSello());
        String certificate = "MIIFwTCCA6mgAwIBAgIUMzAwMDEwMDAwMDA0MDAwMDI0NjMwDQYJKoZIhvcNAQELBQAwggErMQ8wDQYDVQQDDAZBQyBVQVQxLjAsBgNVBAoMJVNFUlZJQ0lPIERFIEFETUlOSVNUUkFDSU9OIFRSSUJVVEFSSUExGjAYBgNVBAsMEVNBVC1JRVMgQXV0aG9yaXR5MSgwJgYJKoZIhvcNAQkBFhlvc2Nhci5tYXJ0aW5lekBzYXQuZ29iLm14MR0wGwYDVQQJDBQzcmEgY2VycmFkYSBkZSBjYWRpejEOMAwGA1UEEQwFMDYzNzAxCzAJBgNVBAYTAk1YMRkwFwYDVQQIDBBDSVVEQUQgREUgTUVYSUNPMREwDwYDVQQHDAhDT1lPQUNBTjERMA8GA1UELRMIMi41LjQuNDUxJTAjBgkqhkiG9w0BCQITFnJlc3BvbnNhYmxlOiBBQ0RNQS1TQVQwHhcNMTkwNjIwMjA1NTA4WhcNMjMwNjIwMjA1NTA4WjCB6DEmMCQGA1UEAxQdSEVSUkVSSUEgJiBFTEVDVFJJQ09TIFMgREUgQ1YxJjAkBgNVBCkUHUhFUlJFUklBICYgRUxFQ1RSSUNPUyBTIERFIENWMSYwJAYDVQQKFB1IRVJSRVJJQSAmIEVMRUNUUklDT1MgUyBERSBDVjElMCMGA1UELRQcSCZFOTUxMTI4NDY5IC8gWE9KSTc0MDkxOVU0ODEeMBwGA1UEBRMVIC8gWE9KSTc0MDkxOU1RVERNTjAyMScwJQYDVQQLFB5IRVJSRVJJQSAmIEVMRUNUUklDT1MgU0EgREUgQ1YwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDoNWRICH7Ja/IR0sFzGAoveKzFT94BJ4Wlh7MsePe50azvre4j6CYEItQ5jCkxZyYHT23dfONpUWDN7+Bx7j9QjKPwqr+zJjFrla/hD4fj63LeUmofVR7TjeDLLEwCSsn1k3vLae4WBgsV1jNc3M87ZYOHpe0XXLVw/25nSyqQS5l4HTJrw7wQ3NZAXghG38NIOUYJZsE/6K3OAslVoF/YregQ0UqodFLyA6BBMcfll8rjMIWtCy2DxZ0T5QCPX3OccWzB9hHvP5PAjUiOf+FpKHW8WPGEywoC1vM3nvsHXTp2MkUn78U5PK/hmc/7chqaneZHA066AIRs3zGHkHTjAgMBAAGjHTAbMAwGA1UdEwEB/wQCMAAwCwYDVR0PBAQDAgbAMA0GCSqGSIb3DQEBCwUAA4ICAQCsaJpin0RAOsyxBMQRpfTM47k+a1QlHHsP2vEubuINZE+3Hp4b/C1w1gGe8+l8EJcLxQfQ0ovlvKDdYFJLleBCkt9eoFY92keaL77Teq6YxzQ8jArI1WSz75KkljzHYbtm4Oy+hsmpla4L51VnhRfyqehfAgZEDRHytpOPVzxIQnnDx/GI2KVlUBvInOS50dBoCnq5sxzQ+Ha1nsPMvrdTaHBRjto4kOL9SkHvZDVZHWnJ1R7w+1n0zXmDV27BUNsHQNaDG8e+5uGex7d/Bfolf3dU2y4YVFN9DOP8ge0J7ryIuRB7eESwT3aEM/0gec41vMOfVmR2mzNeX0q8czU0xjMH+NKSdgkNFAvoMk2uIsGePkbQDdk3XW1h6f3HGRQ85mkPh/Zt4kqbc0CzFsQMGdj8eixm8jRazSoCdndSDXrDwSTkVaqTuYqRxy89NWNk4dbXXqw4a52DKR1oYR0r9AVjHTfK936tRvNhxQ4KHGjPUi0ttxoCw1fogKwf2MRkTZMO0EZrEnODKl3CMoi4IXuWpNYY6MhUCjLRWJF/mOclkpCzNpyNbwJiGTshTEDvfqS1PsIuXgjIzbCa3seRzP6yxsd0CfEI7lUxMvYT6r0wqRXsymO6mOywWQKRFnrZmzD1zSMRap7vhED+plAxUgouQFbfv1NDkqAfF57FFA==";
        Assert.assertEquals(certificate, sellado.getCertificado());
        String certificateNum = "30001000000400002463";
        Assert.assertEquals(certificateNum, sellado.getNoCertificado());
        cfd.validar(null);
        cfd.verificar();
    }

    @Test
    public void testCleanLoad() throws Exception {
        try (FileInputStream fis = new FileInputStream("resources/xmls/cfdi/v40/CFDv40.xml")) {
            Comprobante c = CFDv40.newComprobante(fis);
            CFDv40 cfd = new CFDv40(c);
            cfd.validar(null);
            cfd.verificar();
        }
    }

    @Test
    public void testSellarComprobante() throws Exception {
        try (FileInputStream fis_before = new FileInputStream("resources/xmls/cfdi/v40/CFDv40_before.xml");
             FileInputStream fis_sellado = new FileInputStream("resources/xmls/cfdi/v40/CFDv40.xml")) {
            Comprobante c_before = CFDv40.newComprobante(fis_before);
            CFDv40 cfd_before = new CFDv40(c_before);
            Comprobante sellado_before = cfd_before.sellarComprobante(key, cert);
            Assert.assertNotNull(sellado_before.getSello());
            Assert.assertNotNull(sellado_before.getNoCertificado());
            Assert.assertNotNull(sellado_before.getCertificado());
            Comprobante c_sellado = CFDv40.newComprobante(fis_sellado);
            CFDv40 cfd_sellado = new CFDv40(c_sellado);
            try (OutputStream os_before = new ByteArrayOutputStream(); OutputStream os_sellado = new ByteArrayOutputStream()) {
                cfd_before.validar(null);
                cfd_before.verificar();
                cfd_before.guardar(os_before, false);
                cfd_sellado.validar(null);
                cfd_sellado.verificar();
                cfd_sellado.guardar(os_sellado, false);
                Assert.assertEquals(String.valueOf(os_sellado), String.valueOf(os_before));
            }
        }
    }

    @Test
    public void testComprobanteTFD() throws Exception {
        try (FileInputStream fis_before = new FileInputStream("resources/xmls/cfdi/v40/CFDv40.xml")) {
            Comprobante c_before = CFDv40.newComprobante(fis_before);
            Comprobante.Complemento complemento = ExampleCFDv40Factory.getInstance().createComplemento();
            TimbreFiscalDigital tfd = ExampleTimbreFiscal11Factory.getInstance().createTimbreFiscal();
            complemento.getAny().add(tfd);
            c_before.setComplemento(complemento);
            CFDv40 cfd_before = new CFDv40(c_before);
            try (OutputStream os_before = new ByteArrayOutputStream(); OutputStream os_tfd = new ByteArrayOutputStream(); FileInputStream fis_tfd = new FileInputStream("resources/xmls/cfdi/v40/CFDv40_tfd.xml")) {
                cfd_before.validar(null);
                cfd_before.verificar();
                cfd_before.guardar(os_before, false);

                Comprobante comprobante2 = CFDv40.newComprobante(fis_tfd);
                CFDv40 cfd_tfd = new CFDv40(comprobante2);
                cfd_tfd.validar(null);
                cfd_tfd.verificar();
                cfd_tfd.guardar(os_tfd, false);
                Assert.assertEquals(String.valueOf(os_before), String.valueOf(os_tfd));
            }
        }
    }

    @Test
    public void testValidateVerifyOk() throws Exception {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            CFDv4 cfd = CFDv4Factory.load(new File("resources/xmls/cfdi/v40/CFDv40_before.xml"));
            cfd.sellar(key, cert);
            cfd.guardar(baos, false);
            try (ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray())) {
                CFDv40 cfd3 = new CFDv40(bais);
                cfd3.validar(null);
                cfd3.verificar();
                Assert.assertNotNull(bais);
            }
        }
    }

    @Test
    public void testValidateVerifyFail() throws Exception {
        CFDv4 cfd = CFDv4Factory.load(new File("resources/xmls/cfdi/v40/CFDv40_before.xml"));
        ValidationErrorHandler handler = new ValidationErrorHandler();
        cfd.validar(handler);
        Assert.assertFalse(handler.getErrors().isEmpty());
    }

    @Test
    public void testCreateCPagoComprobante() throws Exception {
        Pagos c_pago = ExampleComplementoPago20Factory.getInstance().createComplementoPago();
        Comprobante comprobante = ExampleCFDv40Factory.getInstance().createComprobantePago();
        Comprobante.Complemento complemento = ExampleCFDv40Factory.getInstance().createComplemento();
        complemento.getAny().add(c_pago);
        comprobante.setComplemento(complemento);
        CFDv40 cfd = new CFDv40(comprobante);
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            cfd.guardar(byteArrayOutputStream, false);
            String comp = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><cfdi:Comprobante Version=\"4.0\" Serie=\"PPP\" Folio=\"54321\" Fecha=\"2022-02-02T15:55:57\" SubTotal=\"0\" Moneda=\"XXX\" Total=\"0\" TipoDeComprobante=\"P\" Exportacion=\"01\" LugarExpedicion=\"83000\" xsi:schemaLocation=\"http://www.sat.gob.mx/cfd/4 http://www.sat.gob.mx/sitio_internet/cfd/4/cfdv40.xsd http://www.sat.gob.mx/Pagos20 http://www.sat.gob.mx/sitio_internet/cfd/Pagos/Pagos20.xsd\" xmlns:pago20=\"http://www.sat.gob.mx/Pagos20\" xmlns:cfdi=\"http://www.sat.gob.mx/cfd/4\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><cfdi:Emisor Rfc=\"PPL961114GZ1\" Nombre=\"PHARMA PLUS SA DE CV\" RegimenFiscal=\"606\" FacAtrAdquirente=\"0123456789\"/><cfdi:Receptor Rfc=\"PEPJ8001019Q8\" Nombre=\"JUAN PEREZ PEREZ\" DomicilioFiscalReceptor=\"83000\" ResidenciaFiscal=\"MEX\" NumRegIdTrib=\"ResidenteExtranjero1\" RegimenFiscalReceptor=\"607\" UsoCFDI=\"D08\"/><cfdi:Conceptos><cfdi:Concepto ClaveProdServ=\"84111506\" Cantidad=\"1\" ClaveUnidad=\"ACT\" Descripcion=\"Pago\" ValorUnitario=\"0\" Importe=\"0\" ObjetoImp=\"01\"/></cfdi:Conceptos><cfdi:Complemento><pago20:Pagos Version=\"2.0\"><pago20:Totales TotalRetencionesIVA=\"480.00\" TotalTrasladosBaseIVA16=\"3000\" TotalTrasladosImpuestoIVA16=\"480.00\" MontoTotalPagos=\"3000\"/><pago20:Pago FechaPago=\"2022-02-02T14:43:57\" FormaDePagoP=\"03\" MonedaP=\"MXN\" TipoCambioP=\"1\" Monto=\"3000\" NumOperacion=\"ABC123\" RfcEmisorCtaOrd=\"ABC010101000\" NomBancoOrdExt=\"Razón Social Banco Ordenante\" CtaOrdenante=\"123456789012345678\" RfcEmisorCtaBen=\"CBA010101000\" CtaBeneficiario=\"876543210987654321\"><pago20:DoctoRelacionado IdDocumento=\"7c5edff2-f8d1-5298-9685-5e288bac9841\" Serie=\"BBB\" Folio=\"898\" MonedaDR=\"MXN\" EquivalenciaDR=\"1\" NumParcialidad=\"2\" ImpSaldoAnt=\"1500\" ImpPagado=\"1500\" ImpSaldoInsoluto=\"0\" ObjetoImpDR=\"02\"><pago20:ImpuestosDR><pago20:RetencionesDR><pago20:RetencionDR BaseDR=\"1500\" ImpuestoDR=\"002\" TipoFactorDR=\"Tasa\" TasaOCuotaDR=\"0.160000\" ImporteDR=\"240.00\"/></pago20:RetencionesDR><pago20:TrasladosDR><pago20:TrasladoDR BaseDR=\"1500\" ImpuestoDR=\"002\" TipoFactorDR=\"Tasa\" TasaOCuotaDR=\"0.160000\" ImporteDR=\"240.00\"/></pago20:TrasladosDR></pago20:ImpuestosDR></pago20:DoctoRelacionado><pago20:DoctoRelacionado IdDocumento=\"153e2c1a-9901-4fc2-aa92-54bf6add80c4\" Serie=\"BBB\" Folio=\"899\" MonedaDR=\"MXN\" EquivalenciaDR=\"1\" NumParcialidad=\"3\" ImpSaldoAnt=\"1000\" ImpPagado=\"1000\" ImpSaldoInsoluto=\"0\" ObjetoImpDR=\"02\"><pago20:ImpuestosDR><pago20:RetencionesDR><pago20:RetencionDR BaseDR=\"1000\" ImpuestoDR=\"002\" TipoFactorDR=\"Tasa\" TasaOCuotaDR=\"0.160000\" ImporteDR=\"160.00\"/></pago20:RetencionesDR><pago20:TrasladosDR><pago20:TrasladoDR BaseDR=\"1000\" ImpuestoDR=\"002\" TipoFactorDR=\"Tasa\" TasaOCuotaDR=\"0.160000\" ImporteDR=\"160.00\"/></pago20:TrasladosDR></pago20:ImpuestosDR></pago20:DoctoRelacionado><pago20:DoctoRelacionado IdDocumento=\"47bdcbab-97be-4195-a674-043e33970121\" Serie=\"BBB\" Folio=\"999\" MonedaDR=\"MXN\" EquivalenciaDR=\"1\" NumParcialidad=\"4\" ImpSaldoAnt=\"500\" ImpPagado=\"500\" ImpSaldoInsoluto=\"0\" ObjetoImpDR=\"02\"><pago20:ImpuestosDR><pago20:RetencionesDR><pago20:RetencionDR BaseDR=\"500\" ImpuestoDR=\"002\" TipoFactorDR=\"Tasa\" TasaOCuotaDR=\"0.160000\" ImporteDR=\"80.00\"/></pago20:RetencionesDR><pago20:TrasladosDR><pago20:TrasladoDR BaseDR=\"500\" ImpuestoDR=\"002\" TipoFactorDR=\"Tasa\" TasaOCuotaDR=\"0.160000\" ImporteDR=\"80.00\"/></pago20:TrasladosDR></pago20:ImpuestosDR></pago20:DoctoRelacionado><pago20:ImpuestosP><pago20:RetencionesP><pago20:RetencionP ImpuestoP=\"002\" ImporteP=\"480.00\"/></pago20:RetencionesP><pago20:TrasladosP><pago20:TrasladoP BaseP=\"3000\" ImpuestoP=\"002\" TipoFactorP=\"Tasa\" TasaOCuotaP=\"0.160000\" ImporteP=\"480.00\"/></pago20:TrasladosP></pago20:ImpuestosP></pago20:Pago></pago20:Pagos></cfdi:Complemento></cfdi:Comprobante>";
            Assert.assertEquals(comp, byteArrayOutputStream.toString("UTF-8"));
        }
    }

    @Test
    public void testCPagoCadenaOriginal() throws Exception {
        Pagos c_pago = ExampleComplementoPago20Factory.getInstance().createComplementoPago();
        Comprobante comprobante = ExampleCFDv40Factory.getInstance().createComprobantePago();
        Comprobante.Complemento complemento = ExampleCFDv40Factory.getInstance().createComplemento();
        complemento.getAny().add(c_pago);
        comprobante.setComplemento(complemento);
        CFDv40 cfd = new CFDv40(comprobante);
        String cadena = "||4.0|PPP|54321|2022-02-02T15:55:57||0|XXX|0|P|01|83000|PPL961114GZ1|PHARMA PLUS SA DE CV|606|0123456789|PEPJ8001019Q8|JUAN PEREZ PEREZ|83000|MEX|ResidenteExtranjero1|607|D08|84111506|1|ACT|Pago|0|0|01|2.0|480.00|3000|480.00|3000|2022-02-02T14:43:57|03|MXN|1|3000|ABC123|ABC010101000|Razón Social Banco Ordenante|123456789012345678|CBA010101000|876543210987654321|7c5edff2-f8d1-5298-9685-5e288bac9841|BBB|898|MXN|1|2|1500|1500|0|02|1500|002|Tasa|0.160000|240.00|1500|002|Tasa|0.160000|240.00|153e2c1a-9901-4fc2-aa92-54bf6add80c4|BBB|899|MXN|1|3|1000|1000|0|02|1000|002|Tasa|0.160000|160.00|1000|002|Tasa|0.160000|160.00|47bdcbab-97be-4195-a674-043e33970121|BBB|999|MXN|1|4|500|500|0|02|500|002|Tasa|0.160000|80.00|500|002|Tasa|0.160000|80.00|002|480.00|3000|002|Tasa|0.160000|480.00||";
        Assert.assertEquals(cadena, cfd.getCadenaOriginal());
    }

    @Test
    public void testCPagoSign() throws Exception {
        Pagos c_pago = ExampleComplementoPago20Factory.getInstance().createComplementoPago();
        Comprobante comprobante = ExampleCFDv40Factory.getInstance().createComprobantePago();
        Comprobante.Complemento complemento = ExampleCFDv40Factory.getInstance().createComplemento();
        complemento.getAny().add(c_pago);
        comprobante.setComplemento(complemento);
        CFDv40 cfd = new CFDv40(comprobante);
        Comprobante sellado = cfd.sellarComprobante(key, cert);
        String signature = "kh6hegpeRD1vW4n7lskx3N181Wu+Lpewv5Woy6kuCjMarmmCh0obTozD/sXwngxeOvWRoRx80VkimQncy7PD6G69azhyj15qpsOSB1I465ezkGOmgF2fEW4Yx7RykTlJYKQRDhJlvoAOWAwAVmt+OAYkulzA6pnhynMiHUxFLoakxMT5BsZ+3KJ+7LgFRhyJj+acj1oS/XZTOgQxNwLVa+Nbp4eYbJO9owrB0bh+bzwT/RftHnfAJDuX0cIaHPYlpuuttvnnYGl+zOD939YCS2Ueb4g8Y/SPSYHeMRvy694dNkUmOafLzO38FH71GdyZMp7eCwCdgtlfvF8Gnsh5OQ==";
        Assert.assertEquals(signature, sellado.getSello());
        String certificate = "MIIFwTCCA6mgAwIBAgIUMzAwMDEwMDAwMDA0MDAwMDI0NjMwDQYJKoZIhvcNAQELBQAwggErMQ8wDQYDVQQDDAZBQyBVQVQxLjAsBgNVBAoMJVNFUlZJQ0lPIERFIEFETUlOSVNUUkFDSU9OIFRSSUJVVEFSSUExGjAYBgNVBAsMEVNBVC1JRVMgQXV0aG9yaXR5MSgwJgYJKoZIhvcNAQkBFhlvc2Nhci5tYXJ0aW5lekBzYXQuZ29iLm14MR0wGwYDVQQJDBQzcmEgY2VycmFkYSBkZSBjYWRpejEOMAwGA1UEEQwFMDYzNzAxCzAJBgNVBAYTAk1YMRkwFwYDVQQIDBBDSVVEQUQgREUgTUVYSUNPMREwDwYDVQQHDAhDT1lPQUNBTjERMA8GA1UELRMIMi41LjQuNDUxJTAjBgkqhkiG9w0BCQITFnJlc3BvbnNhYmxlOiBBQ0RNQS1TQVQwHhcNMTkwNjIwMjA1NTA4WhcNMjMwNjIwMjA1NTA4WjCB6DEmMCQGA1UEAxQdSEVSUkVSSUEgJiBFTEVDVFJJQ09TIFMgREUgQ1YxJjAkBgNVBCkUHUhFUlJFUklBICYgRUxFQ1RSSUNPUyBTIERFIENWMSYwJAYDVQQKFB1IRVJSRVJJQSAmIEVMRUNUUklDT1MgUyBERSBDVjElMCMGA1UELRQcSCZFOTUxMTI4NDY5IC8gWE9KSTc0MDkxOVU0ODEeMBwGA1UEBRMVIC8gWE9KSTc0MDkxOU1RVERNTjAyMScwJQYDVQQLFB5IRVJSRVJJQSAmIEVMRUNUUklDT1MgU0EgREUgQ1YwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDoNWRICH7Ja/IR0sFzGAoveKzFT94BJ4Wlh7MsePe50azvre4j6CYEItQ5jCkxZyYHT23dfONpUWDN7+Bx7j9QjKPwqr+zJjFrla/hD4fj63LeUmofVR7TjeDLLEwCSsn1k3vLae4WBgsV1jNc3M87ZYOHpe0XXLVw/25nSyqQS5l4HTJrw7wQ3NZAXghG38NIOUYJZsE/6K3OAslVoF/YregQ0UqodFLyA6BBMcfll8rjMIWtCy2DxZ0T5QCPX3OccWzB9hHvP5PAjUiOf+FpKHW8WPGEywoC1vM3nvsHXTp2MkUn78U5PK/hmc/7chqaneZHA066AIRs3zGHkHTjAgMBAAGjHTAbMAwGA1UdEwEB/wQCMAAwCwYDVR0PBAQDAgbAMA0GCSqGSIb3DQEBCwUAA4ICAQCsaJpin0RAOsyxBMQRpfTM47k+a1QlHHsP2vEubuINZE+3Hp4b/C1w1gGe8+l8EJcLxQfQ0ovlvKDdYFJLleBCkt9eoFY92keaL77Teq6YxzQ8jArI1WSz75KkljzHYbtm4Oy+hsmpla4L51VnhRfyqehfAgZEDRHytpOPVzxIQnnDx/GI2KVlUBvInOS50dBoCnq5sxzQ+Ha1nsPMvrdTaHBRjto4kOL9SkHvZDVZHWnJ1R7w+1n0zXmDV27BUNsHQNaDG8e+5uGex7d/Bfolf3dU2y4YVFN9DOP8ge0J7ryIuRB7eESwT3aEM/0gec41vMOfVmR2mzNeX0q8czU0xjMH+NKSdgkNFAvoMk2uIsGePkbQDdk3XW1h6f3HGRQ85mkPh/Zt4kqbc0CzFsQMGdj8eixm8jRazSoCdndSDXrDwSTkVaqTuYqRxy89NWNk4dbXXqw4a52DKR1oYR0r9AVjHTfK936tRvNhxQ4KHGjPUi0ttxoCw1fogKwf2MRkTZMO0EZrEnODKl3CMoi4IXuWpNYY6MhUCjLRWJF/mOclkpCzNpyNbwJiGTshTEDvfqS1PsIuXgjIzbCa3seRzP6yxsd0CfEI7lUxMvYT6r0wqRXsymO6mOywWQKRFnrZmzD1zSMRap7vhED+plAxUgouQFbfv1NDkqAfF57FFA==";
        Assert.assertEquals(certificate, sellado.getCertificado());
        String certificateNum = "30001000000400002463";
        Assert.assertEquals(certificateNum, sellado.getNoCertificado());
        cfd.validar(null);
        cfd.verificar();
    }

    @Test
    public void testCPagoLoad() throws Exception {
        Comprobante c = CFDv40.newComprobante(new FileInputStream("resources/xmls/cfdi/v40/CFDv40_cpago.xml"));
        CFDv40 cfd = new CFDv40(c);
        cfd.validar(null);
        cfd.verificar();
    }

    @Test
    public void testSellarComprobanteCPago() throws Exception {
        Comprobante c = CFDv40.newComprobante(new FileInputStream("resources/xmls/cfdi/v40/CFDv40_cpago_before.xml"));
        CFDv40 cfd = new CFDv40(c);
        Comprobante sellado = cfd.sellarComprobante(key, cert);
        Assert.assertNotNull(sellado.getSello());
        Assert.assertNotNull(sellado.getNoCertificado());
        Assert.assertNotNull(sellado.getCertificado());
        Comprobante c2 = CFDv40.newComprobante(new FileInputStream("resources/xmls/cfdi/v40/CFDv40_cpago.xml"));
        CFDv40 cfd2 = new CFDv40(c2);
        try (OutputStream outputStream = new ByteArrayOutputStream(); OutputStream outputStream2 = new ByteArrayOutputStream()) {
            cfd.validar(null);
            cfd.verificar();
            cfd.guardar(outputStream, false);
            cfd2.validar(null);
            cfd2.verificar();
            cfd2.guardar(outputStream2, false);
            Assert.assertEquals(String.valueOf(outputStream2), String.valueOf(outputStream));
        }
    }

    @Test
    public void testValidateVerifyCPagoOk() throws Exception {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            CFDv4 cfd = CFDv4Factory.load(new File("resources/xmls/cfdi/v40/CFDv40_cpago_before.xml"));
            cfd.sellar(key, cert);
            cfd.guardar(baos, false);
            try (ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray())) {
                CFDv40 cfd2 = new CFDv40(bais);
                cfd2.validar(null);
                cfd2.verificar();
            }
        }
    }

    @Test
    public void testValidateVerifyCPagoFail() throws Exception {
        CFDv4 cfd = CFDv4Factory.load(new File("resources/xmls/cfdi/v40/CFDv40_cpago_before.xml"));
        ValidationErrorHandler handler = new ValidationErrorHandler();
        cfd.validar(handler);
        Assert.assertFalse(handler.getErrors().isEmpty());
    }

    @Test
    public void testingPACtests() throws Exception {
        File dir = new File("./resources/xmls/cfdi/v40/pac/");
        if (!dir.isDirectory() || !dir.exists()) Assert.fail(dir.getAbsolutePath() + " no es un directorio válido");
        for (File f : Objects.requireNonNull(dir.listFiles())) {
            CFDv4 cfd = CFDv4Factory.load(f);
            cfd.validar(null);
            if (cfd.getSelloString() != null && !cfd.getSelloString().isEmpty()) {
                try (InputStream fis = new FileInputStream(f)) {
                    cfd.verificar(fis);
                }
            }
        }
    }
}