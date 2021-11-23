package mx.grupocorasa.sat.cfd;

import mx.grupocorasa.sat.cfd._22.Comprobante;
import mx.grupocorasa.sat.common.ValidationErrorHandler;
import mx.grupocorasa.sat.common.implocal10.ImpuestosLocales;
import mx.grupocorasa.sat.factory.cfd.ExampleCFDv22Factory;
import mx.grupocorasa.sat.factory.common.ExampleImpLocalFactory;
import mx.grupocorasa.sat.security.KeyLoaderEnumeration;
import mx.grupocorasa.sat.security.factory.KeyLoaderFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public final class CFDv22Test {

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
        CFDv22 cfd = new CFDv22(ExampleCFDv22Factory.getInstance().createComprobante());
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            cfd.guardar(byteArrayOutputStream, false);
            String comprobante = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Comprobante version=\"2.2\" serie=\"DCBA\" folio=\"81\" fecha=\"2012-03-11T17:08:22\" noAprobacion=\"448911\" anoAprobacion=\"2011\" formaDePago=\"PAGO EN PARCIALIDADES\" condicionesDePago=\"Factura a Crédito\" subTotal=\"250.00\" descuento=\"150.00\" motivoDescuento=\"Cliente de confianza\" TipoCambio=\"1.00\" total=\"124.00\" tipoDeComprobante=\"ingreso\" metodoDePago=\"Transferencia Electrónica\" LugarExpedicion=\"Hermosillo, Sonora, México\" NumCtaPago=\"1587\" xsi:schemaLocation=\"http://www.sat.gob.mx/cfd/2 http://www.sat.gob.mx/sitio_internet/cfd/2/cfdv22.xsd\" xmlns=\"http://www.sat.gob.mx/cfd/2\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><Emisor rfc=\"COA0106136Z4\" nombre=\"Comercializadora Ortega y Accionistas, S.A. de C.V.\"><DomicilioFiscal calle=\"Leandro P. Gaxiola\" noExterior=\"6\" noInterior=\"6\" colonia=\"Olivares\" localidad=\"Hermosillo\" referencia=\"Casa de dos pisos\" municipio=\"Hermosillo\" estado=\"Sonora\" pais=\"México\" codigoPostal=\"83180\"/><ExpedidoEn calle=\"Leandro P. Gaxiola\" noExterior=\"6\" noInterior=\"6\" colonia=\"Olivares\" localidad=\"Hermosillo\" referencia=\"Casa de dos pisos\" municipio=\"Hermosillo\" estado=\"Sonora\" pais=\"México\" codigoPostal=\"83180\"/><RegimenFiscal Regimen=\"Régimen General de Ley Persona Moral\"/></Emisor><Receptor rfc=\"H&amp;E951128469\" nombre=\"Herreria &amp; Electricos S.A. de C.V.\"><Domicilio calle=\"Calle conocida\" noExterior=\"1\" noInterior=\"2\" colonia=\"Colonia Centro\" localidad=\"Hermosillo\" referencia=\"Carpa verde\" municipio=\"Hermosillo\" estado=\"Sonora\" pais=\"México\" codigoPostal=\"83000\"/></Receptor><Conceptos><Concepto cantidad=\"1.00\" unidad=\"Servicio\" noIdentificacion=\"01\" descripcion=\"Asesoria Fiscal y administrativa\" valorUnitario=\"150.00\" importe=\"150.00\"/><Concepto cantidad=\"2.00\" unidad=\"Servicio\" noIdentificacion=\"02\" descripcion=\"Asesoria Fiscal y administrativa 2\" valorUnitario=\"50.00\" importe=\"100.00\"/></Conceptos><Impuestos totalImpuestosTrasladados=\"16.00\"><Retenciones><Retencion impuesto=\"IVA\" importe=\"4.00\"/></Retenciones><Traslados><Traslado impuesto=\"IVA\" tasa=\"16.00\" importe=\"16.00\"/></Traslados></Impuestos></Comprobante>";
            Assert.assertEquals(comprobante, new String(byteArrayOutputStream.toByteArray()));
        }
    }

    @Test
    public void testCreateImpLocalComprobante() throws Exception {
        Comprobante comprobante = ExampleCFDv22Factory.getInstance().createComprobante();
        comprobante.setComplemento(ExampleCFDv22Factory.getInstance().createComplemento());
        ImpuestosLocales impuestosLocales = ExampleImpLocalFactory.getInstance().createImpuestosLocales();
        comprobante.getComplemento().getAny().add(impuestosLocales);
        CFDv22 cfd = new CFDv22(comprobante);
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            cfd.guardar(byteArrayOutputStream, false);
            String comp = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Comprobante version=\"2.2\" serie=\"DCBA\" folio=\"81\" fecha=\"2012-03-11T17:08:22\" noAprobacion=\"448911\" anoAprobacion=\"2011\" formaDePago=\"PAGO EN PARCIALIDADES\" condicionesDePago=\"Factura a Crédito\" subTotal=\"250.00\" descuento=\"150.00\" motivoDescuento=\"Cliente de confianza\" TipoCambio=\"1.00\" total=\"124.00\" tipoDeComprobante=\"ingreso\" metodoDePago=\"Transferencia Electrónica\" LugarExpedicion=\"Hermosillo, Sonora, México\" NumCtaPago=\"1587\" xsi:schemaLocation=\"http://www.sat.gob.mx/cfd/2 http://www.sat.gob.mx/sitio_internet/cfd/2/cfdv22.xsd http://www.sat.gob.mx/implocal http://www.sat.gob.mx/sitio_internet/cfd/implocal/implocal.xsd\" xmlns=\"http://www.sat.gob.mx/cfd/2\" xmlns:implocal=\"http://www.sat.gob.mx/implocal\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><Emisor rfc=\"COA0106136Z4\" nombre=\"Comercializadora Ortega y Accionistas, S.A. de C.V.\"><DomicilioFiscal calle=\"Leandro P. Gaxiola\" noExterior=\"6\" noInterior=\"6\" colonia=\"Olivares\" localidad=\"Hermosillo\" referencia=\"Casa de dos pisos\" municipio=\"Hermosillo\" estado=\"Sonora\" pais=\"México\" codigoPostal=\"83180\"/><ExpedidoEn calle=\"Leandro P. Gaxiola\" noExterior=\"6\" noInterior=\"6\" colonia=\"Olivares\" localidad=\"Hermosillo\" referencia=\"Casa de dos pisos\" municipio=\"Hermosillo\" estado=\"Sonora\" pais=\"México\" codigoPostal=\"83180\"/><RegimenFiscal Regimen=\"Régimen General de Ley Persona Moral\"/></Emisor><Receptor rfc=\"H&amp;E951128469\" nombre=\"Herreria &amp; Electricos S.A. de C.V.\"><Domicilio calle=\"Calle conocida\" noExterior=\"1\" noInterior=\"2\" colonia=\"Colonia Centro\" localidad=\"Hermosillo\" referencia=\"Carpa verde\" municipio=\"Hermosillo\" estado=\"Sonora\" pais=\"México\" codigoPostal=\"83000\"/></Receptor><Conceptos><Concepto cantidad=\"1.00\" unidad=\"Servicio\" noIdentificacion=\"01\" descripcion=\"Asesoria Fiscal y administrativa\" valorUnitario=\"150.00\" importe=\"150.00\"/><Concepto cantidad=\"2.00\" unidad=\"Servicio\" noIdentificacion=\"02\" descripcion=\"Asesoria Fiscal y administrativa 2\" valorUnitario=\"50.00\" importe=\"100.00\"/></Conceptos><Impuestos totalImpuestosTrasladados=\"16.00\"><Retenciones><Retencion impuesto=\"IVA\" importe=\"4.00\"/></Retenciones><Traslados><Traslado impuesto=\"IVA\" tasa=\"16.00\" importe=\"16.00\"/></Traslados></Impuestos><Complemento><implocal:ImpuestosLocales version=\"1.0\" TotaldeRetenciones=\"4.00\" TotaldeTraslados=\"16.00\"><implocal:RetencionesLocales ImpLocRetenido=\"IVA RET 4%\" TasadeRetencion=\"4\" Importe=\"4.000000\"/><implocal:TrasladosLocales ImpLocTrasladado=\"IVA TRAS 16%\" TasadeTraslado=\"16\" Importe=\"16\"/></implocal:ImpuestosLocales></Complemento></Comprobante>";
            Assert.assertEquals(comp, new String(byteArrayOutputStream.toByteArray()));
        }
    }

    @Test
    public void testCleanCadenaOriginal() throws Exception {
        CFDv22 cfd = new CFDv22(ExampleCFDv22Factory.getInstance().createComprobante());
        String cadena = "||2.2|DCBA|81|2012-03-11T17:08:22|448911|2011|ingreso|PAGO EN PARCIALIDADES|Factura a Crédito|250.00|150.00|124.00|Transferencia Electrónica|Hermosillo, Sonora, México|1587|1.00|COA0106136Z4|Comercializadora Ortega y Accionistas, S.A. de C.V.|Leandro P. Gaxiola|6|6|Olivares|Hermosillo|Casa de dos pisos|Hermosillo|Sonora|México|83180|Leandro P. Gaxiola|6|6|Olivares|Hermosillo|Casa de dos pisos|Hermosillo|Sonora|México|83180|Régimen General de Ley Persona Moral|H&E951128469|Herreria & Electricos S.A. de C.V.|Calle conocida|1|2|Colonia Centro|Hermosillo|Carpa verde|Hermosillo|Sonora|México|83000|1.00|Servicio|01|Asesoria Fiscal y administrativa|150.00|150.00|2.00|Servicio|02|Asesoria Fiscal y administrativa 2|50.00|100.00|IVA|4.00|IVA|16.00|16.00|16.00||";
        Assert.assertEquals(cadena, cfd.getCadenaOriginal());
    }

    @Test
    public void testImpLocalCadenaOriginal() throws Exception {
        Comprobante comprobante = ExampleCFDv22Factory.getInstance().createComprobante();
        comprobante.setComplemento(ExampleCFDv22Factory.getInstance().createComplemento());
        ImpuestosLocales impuestosLocales = ExampleImpLocalFactory.getInstance().createImpuestosLocales();
        comprobante.getComplemento().getAny().add(impuestosLocales);
        CFDv22 cfd = new CFDv22(comprobante);
        String cadena = "||2.2|DCBA|81|2012-03-11T17:08:22|448911|2011|ingreso|PAGO EN PARCIALIDADES|Factura a Crédito|250.00|150.00|124.00|Transferencia Electrónica|Hermosillo, Sonora, México|1587|1.00|COA0106136Z4|Comercializadora Ortega y Accionistas, S.A. de C.V.|Leandro P. Gaxiola|6|6|Olivares|Hermosillo|Casa de dos pisos|Hermosillo|Sonora|México|83180|Leandro P. Gaxiola|6|6|Olivares|Hermosillo|Casa de dos pisos|Hermosillo|Sonora|México|83180|Régimen General de Ley Persona Moral|H&E951128469|Herreria & Electricos S.A. de C.V.|Calle conocida|1|2|Colonia Centro|Hermosillo|Carpa verde|Hermosillo|Sonora|México|83000|1.00|Servicio|01|Asesoria Fiscal y administrativa|150.00|150.00|2.00|Servicio|02|Asesoria Fiscal y administrativa 2|50.00|100.00|IVA|4.00|IVA|16.00|16.00|16.00|1.0|4.00|16.00|IVA RET 4%|4|4.000000|IVA TRAS 16%|16|16||";
        Assert.assertEquals(cadena, cfd.getCadenaOriginal());
    }

    @Test
    public void testCleanSign() throws Exception {
        CFDv22 cfd = new CFDv22(ExampleCFDv22Factory.getInstance().createComprobante());
        Comprobante sellado = cfd.sellarComprobante(key, cert);
        String signature = "h630R6FBG8dvYNLDCUAtchQ8f9quURQ0hIfzW2IVsK5RxQb5nvaJcGdzNhC4p2gT1zynTFgEjHNsvDTmsGPbcznVGmyXayHHm8c4yeoKbucqHrkDKFOQBcC+UAPfZMDTmBWHU+d4pA9z83p2+Gj08WNDbLD8WazP7aHuDdi6O2RIcGCGBiV7L4ZZakWqpMktDtQoG45de2dgJTOl03F4iZM57mEd0vaLSOfPRUf6tf2UVKMUjypV/B012iY/zaemAI1KVMXD0kNuEYogCSaEYTkgHBWDMP3p1Ao3fVq1nJjUm86E05pMhD103NqwV6FOgs8EO/f/V2Rb1LRwRnVLdQ==";
        Assert.assertEquals(signature, sellado.getSello());
        String certificate = "MIIFwTCCA6mgAwIBAgIUMzAwMDEwMDAwMDA0MDAwMDI0NjMwDQYJKoZIhvcNAQELBQAwggErMQ8wDQYDVQQDDAZBQyBVQVQxLjAsBgNVBAoMJVNFUlZJQ0lPIERFIEFETUlOSVNUUkFDSU9OIFRSSUJVVEFSSUExGjAYBgNVBAsMEVNBVC1JRVMgQXV0aG9yaXR5MSgwJgYJKoZIhvcNAQkBFhlvc2Nhci5tYXJ0aW5lekBzYXQuZ29iLm14MR0wGwYDVQQJDBQzcmEgY2VycmFkYSBkZSBjYWRpejEOMAwGA1UEEQwFMDYzNzAxCzAJBgNVBAYTAk1YMRkwFwYDVQQIDBBDSVVEQUQgREUgTUVYSUNPMREwDwYDVQQHDAhDT1lPQUNBTjERMA8GA1UELRMIMi41LjQuNDUxJTAjBgkqhkiG9w0BCQITFnJlc3BvbnNhYmxlOiBBQ0RNQS1TQVQwHhcNMTkwNjIwMjA1NTA4WhcNMjMwNjIwMjA1NTA4WjCB6DEmMCQGA1UEAxQdSEVSUkVSSUEgJiBFTEVDVFJJQ09TIFMgREUgQ1YxJjAkBgNVBCkUHUhFUlJFUklBICYgRUxFQ1RSSUNPUyBTIERFIENWMSYwJAYDVQQKFB1IRVJSRVJJQSAmIEVMRUNUUklDT1MgUyBERSBDVjElMCMGA1UELRQcSCZFOTUxMTI4NDY5IC8gWE9KSTc0MDkxOVU0ODEeMBwGA1UEBRMVIC8gWE9KSTc0MDkxOU1RVERNTjAyMScwJQYDVQQLFB5IRVJSRVJJQSAmIEVMRUNUUklDT1MgU0EgREUgQ1YwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDoNWRICH7Ja/IR0sFzGAoveKzFT94BJ4Wlh7MsePe50azvre4j6CYEItQ5jCkxZyYHT23dfONpUWDN7+Bx7j9QjKPwqr+zJjFrla/hD4fj63LeUmofVR7TjeDLLEwCSsn1k3vLae4WBgsV1jNc3M87ZYOHpe0XXLVw/25nSyqQS5l4HTJrw7wQ3NZAXghG38NIOUYJZsE/6K3OAslVoF/YregQ0UqodFLyA6BBMcfll8rjMIWtCy2DxZ0T5QCPX3OccWzB9hHvP5PAjUiOf+FpKHW8WPGEywoC1vM3nvsHXTp2MkUn78U5PK/hmc/7chqaneZHA066AIRs3zGHkHTjAgMBAAGjHTAbMAwGA1UdEwEB/wQCMAAwCwYDVR0PBAQDAgbAMA0GCSqGSIb3DQEBCwUAA4ICAQCsaJpin0RAOsyxBMQRpfTM47k+a1QlHHsP2vEubuINZE+3Hp4b/C1w1gGe8+l8EJcLxQfQ0ovlvKDdYFJLleBCkt9eoFY92keaL77Teq6YxzQ8jArI1WSz75KkljzHYbtm4Oy+hsmpla4L51VnhRfyqehfAgZEDRHytpOPVzxIQnnDx/GI2KVlUBvInOS50dBoCnq5sxzQ+Ha1nsPMvrdTaHBRjto4kOL9SkHvZDVZHWnJ1R7w+1n0zXmDV27BUNsHQNaDG8e+5uGex7d/Bfolf3dU2y4YVFN9DOP8ge0J7ryIuRB7eESwT3aEM/0gec41vMOfVmR2mzNeX0q8czU0xjMH+NKSdgkNFAvoMk2uIsGePkbQDdk3XW1h6f3HGRQ85mkPh/Zt4kqbc0CzFsQMGdj8eixm8jRazSoCdndSDXrDwSTkVaqTuYqRxy89NWNk4dbXXqw4a52DKR1oYR0r9AVjHTfK936tRvNhxQ4KHGjPUi0ttxoCw1fogKwf2MRkTZMO0EZrEnODKl3CMoi4IXuWpNYY6MhUCjLRWJF/mOclkpCzNpyNbwJiGTshTEDvfqS1PsIuXgjIzbCa3seRzP6yxsd0CfEI7lUxMvYT6r0wqRXsymO6mOywWQKRFnrZmzD1zSMRap7vhED+plAxUgouQFbfv1NDkqAfF57FFA==";
        Assert.assertEquals(certificate, sellado.getCertificado());
        String certificateNum = "30001000000400002463";
        Assert.assertEquals(certificateNum, sellado.getNoCertificado());
        ValidationErrorHandler handler = new ValidationErrorHandler();
        cfd.validar(handler);
        handler.getErrors().forEach(System.out::println);
        cfd.verificar();
    }

    @Test
    public void testImpLocalSign() throws Exception {
        Comprobante comprobante = ExampleCFDv22Factory.getInstance().createComprobante();
        comprobante.setComplemento(ExampleCFDv22Factory.getInstance().createComplemento());
        ImpuestosLocales impuestosLocales = ExampleImpLocalFactory.getInstance().createImpuestosLocales();
        comprobante.getComplemento().getAny().add(impuestosLocales);
        CFDv22 cfd = new CFDv22(comprobante);
        Comprobante sellado = cfd.sellarComprobante(key, cert);
        String signature = "BVSv9G3vQFUcLzRZ0fxDu6XlZt7VHfkpSf440lQk4iYabRGjrEiJ46FxeBWbGlx2xQDNEuJF8OeSmu2vQynLUIHJE2XA/kklRQMnPTiPz4X2JV2kkFwF8pevvwttTThfbIX0NTsCZvbdErbq00X+f9ySOk+Xq2uGdytTv8xSsmERSdtmlABjsRsfi0YTz6tq4HRux9ofYRaJMqUSx97Pv+K04GP74SQmODnxMXchOPO2fEzJ/PpjkXvoUZsFL6YPPFnftV9CjJ8XxO5AxwnMhJJAUM7tuZTcrfn3KlgkaFJKV2JOj6LtUAU/xIUQKDnZvsV6GoiwOvZwAraBsFP3WA==";
        Assert.assertEquals(signature, sellado.getSello());
        String certificate = "MIIFwTCCA6mgAwIBAgIUMzAwMDEwMDAwMDA0MDAwMDI0NjMwDQYJKoZIhvcNAQELBQAwggErMQ8wDQYDVQQDDAZBQyBVQVQxLjAsBgNVBAoMJVNFUlZJQ0lPIERFIEFETUlOSVNUUkFDSU9OIFRSSUJVVEFSSUExGjAYBgNVBAsMEVNBVC1JRVMgQXV0aG9yaXR5MSgwJgYJKoZIhvcNAQkBFhlvc2Nhci5tYXJ0aW5lekBzYXQuZ29iLm14MR0wGwYDVQQJDBQzcmEgY2VycmFkYSBkZSBjYWRpejEOMAwGA1UEEQwFMDYzNzAxCzAJBgNVBAYTAk1YMRkwFwYDVQQIDBBDSVVEQUQgREUgTUVYSUNPMREwDwYDVQQHDAhDT1lPQUNBTjERMA8GA1UELRMIMi41LjQuNDUxJTAjBgkqhkiG9w0BCQITFnJlc3BvbnNhYmxlOiBBQ0RNQS1TQVQwHhcNMTkwNjIwMjA1NTA4WhcNMjMwNjIwMjA1NTA4WjCB6DEmMCQGA1UEAxQdSEVSUkVSSUEgJiBFTEVDVFJJQ09TIFMgREUgQ1YxJjAkBgNVBCkUHUhFUlJFUklBICYgRUxFQ1RSSUNPUyBTIERFIENWMSYwJAYDVQQKFB1IRVJSRVJJQSAmIEVMRUNUUklDT1MgUyBERSBDVjElMCMGA1UELRQcSCZFOTUxMTI4NDY5IC8gWE9KSTc0MDkxOVU0ODEeMBwGA1UEBRMVIC8gWE9KSTc0MDkxOU1RVERNTjAyMScwJQYDVQQLFB5IRVJSRVJJQSAmIEVMRUNUUklDT1MgU0EgREUgQ1YwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDoNWRICH7Ja/IR0sFzGAoveKzFT94BJ4Wlh7MsePe50azvre4j6CYEItQ5jCkxZyYHT23dfONpUWDN7+Bx7j9QjKPwqr+zJjFrla/hD4fj63LeUmofVR7TjeDLLEwCSsn1k3vLae4WBgsV1jNc3M87ZYOHpe0XXLVw/25nSyqQS5l4HTJrw7wQ3NZAXghG38NIOUYJZsE/6K3OAslVoF/YregQ0UqodFLyA6BBMcfll8rjMIWtCy2DxZ0T5QCPX3OccWzB9hHvP5PAjUiOf+FpKHW8WPGEywoC1vM3nvsHXTp2MkUn78U5PK/hmc/7chqaneZHA066AIRs3zGHkHTjAgMBAAGjHTAbMAwGA1UdEwEB/wQCMAAwCwYDVR0PBAQDAgbAMA0GCSqGSIb3DQEBCwUAA4ICAQCsaJpin0RAOsyxBMQRpfTM47k+a1QlHHsP2vEubuINZE+3Hp4b/C1w1gGe8+l8EJcLxQfQ0ovlvKDdYFJLleBCkt9eoFY92keaL77Teq6YxzQ8jArI1WSz75KkljzHYbtm4Oy+hsmpla4L51VnhRfyqehfAgZEDRHytpOPVzxIQnnDx/GI2KVlUBvInOS50dBoCnq5sxzQ+Ha1nsPMvrdTaHBRjto4kOL9SkHvZDVZHWnJ1R7w+1n0zXmDV27BUNsHQNaDG8e+5uGex7d/Bfolf3dU2y4YVFN9DOP8ge0J7ryIuRB7eESwT3aEM/0gec41vMOfVmR2mzNeX0q8czU0xjMH+NKSdgkNFAvoMk2uIsGePkbQDdk3XW1h6f3HGRQ85mkPh/Zt4kqbc0CzFsQMGdj8eixm8jRazSoCdndSDXrDwSTkVaqTuYqRxy89NWNk4dbXXqw4a52DKR1oYR0r9AVjHTfK936tRvNhxQ4KHGjPUi0ttxoCw1fogKwf2MRkTZMO0EZrEnODKl3CMoi4IXuWpNYY6MhUCjLRWJF/mOclkpCzNpyNbwJiGTshTEDvfqS1PsIuXgjIzbCa3seRzP6yxsd0CfEI7lUxMvYT6r0wqRXsymO6mOywWQKRFnrZmzD1zSMRap7vhED+plAxUgouQFbfv1NDkqAfF57FFA==";
        Assert.assertEquals(certificate, sellado.getCertificado());
        String certificateNum = "30001000000400002463";
        Assert.assertEquals(certificateNum, sellado.getNoCertificado());
        ValidationErrorHandler handler = new ValidationErrorHandler();
        cfd.validar(handler);
        handler.getErrors().forEach(System.out::println);
        cfd.verificar();
    }

    @Test
    public void testValidateVerifyFail() throws Exception {
        CFDv2 cfd = CFDv2Factory.load(new File("resources/xmls/cfd/v22/CFDv22_before.xml"));
        ValidationErrorHandler handler = new ValidationErrorHandler();
        cfd.validar(handler);
        Assert.assertFalse(handler.getErrors().isEmpty());
    }

    @Test
    public void testValidateVerifyDonatFail() throws Exception {
        CFDv2 cfd = CFDv2Factory.load(new File("resources/xmls/cfd/v22/CFDv22_imploc_before.xml"));
        ValidationErrorHandler handler = new ValidationErrorHandler();
        cfd.validar(handler);
        Assert.assertFalse(handler.getErrors().isEmpty());
    }

    @Test
    public void testValidateVerifyOk() throws Exception {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            CFDv2 cfd = CFDv2Factory.load(new File("resources/xmls/cfd/v22/CFDv22_before.xml"));
            cfd.sellar(key, cert);
            cfd.guardar(baos, false);
            try (ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray())) {
                CFDv2 cfd2 = new CFDv22(bais);
                cfd2.validar(null);
                cfd2.verificar();
            }
        }
    }

    @Test
    public void testValidateVerifyDonatOk() throws Exception {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            CFDv2 cfd = CFDv2Factory.load(new File("resources/xmls/cfd/v22/CFDv22_imploc_before.xml"));
            cfd.sellar(key, cert);
            cfd.guardar(baos, false);
            try (ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray())) {
                CFDv2 cfd2 = new CFDv22(bais);
                cfd2.validar(null);
                cfd2.verificar();
            }
        }
    }

    @Test
    public void testCleanLoad() throws Exception {
        Comprobante c = CFDv22.newComprobante(new FileInputStream("resources/xmls/cfd/v22/CFDv22.xml"));
        CFDv22 cfd = new CFDv22(c);
        cfd.validar(null);
        cfd.verificar();
    }

    @Test
    public void testDonatLoad() throws Exception {
        Comprobante c = CFDv22.newComprobante(new FileInputStream("resources/xmls/cfd/v22/CFDv22_imploc.xml"));
        CFDv22 cfd = new CFDv22(c);
        cfd.validar(null);
        cfd.verificar();
    }

    @Test
    public void testSellarComprobante() throws Exception {
        Comprobante c = CFDv22.newComprobante(new FileInputStream("resources/xmls/cfd/v22/CFDv22_before.xml"));
        CFDv22 cfd = new CFDv22(c);
        Comprobante sellado = cfd.sellarComprobante(key, cert);
        Assert.assertNotNull(sellado.getSello());
        Assert.assertNotNull(sellado.getNoCertificado());
        Assert.assertNotNull(sellado.getCertificado());
        Comprobante c2 = CFDv22.newComprobante(new FileInputStream("resources/xmls/cfd/v22/CFDv22.xml"));
        CFDv22 cfd2 = new CFDv22(c2);
        try (OutputStream outputStream = new ByteArrayOutputStream(); OutputStream outputStream2 = new ByteArrayOutputStream()) {
            cfd.guardar(outputStream, false);
            cfd2.guardar(outputStream2, false);
            Assert.assertEquals(String.valueOf(outputStream2), String.valueOf(outputStream));
        }
    }

    @Test
    public void testSellarComprobanteDonat() throws Exception {
        Comprobante c = CFDv22.newComprobante(new FileInputStream("resources/xmls/cfd/v22/CFDv22_imploc_before.xml"));
        CFDv22 cfd = new CFDv22(c);
        Comprobante sellado = cfd.sellarComprobante(key, cert);
        Assert.assertNotNull(sellado.getSello());
        Assert.assertNotNull(sellado.getNoCertificado());
        Assert.assertNotNull(sellado.getCertificado());
        Comprobante c2 = CFDv22.newComprobante(new FileInputStream("resources/xmls/cfd/v22/CFDv22_imploc.xml"));
        CFDv22 cfd2 = new CFDv22(c2);
        try (OutputStream outputStream = new ByteArrayOutputStream(); OutputStream outputStream2 = new ByteArrayOutputStream()) {
            cfd.guardar(outputStream, false);
            cfd2.guardar(outputStream2, false);
            Assert.assertEquals(String.valueOf(outputStream2), String.valueOf(outputStream));
        }
    }

}
