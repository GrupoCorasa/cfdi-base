package mx.grupocorasa.sat.cfdi;

import mx.grupocorasa.sat.cfd._30.Comprobante;
import mx.grupocorasa.sat.common.TimbreFiscalDigital10.TimbreFiscalDigital;
import mx.grupocorasa.sat.common.ValidationErrorHandler;
import mx.grupocorasa.sat.common.donat10.Donatarias;
import mx.grupocorasa.sat.factory.cfd.ExampleCFDv3Factory;
import mx.grupocorasa.sat.factory.common.ExampleDonatFactory;
import mx.grupocorasa.sat.factory.common.ExampleTimbreFiscal10Factory;
import mx.grupocorasa.sat.security.KeyLoaderEnumeration;
import mx.grupocorasa.sat.security.factory.KeyLoaderFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public final class CFDv30Test {

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
        CFDv30 cfd = new CFDv30(ExampleCFDv3Factory.getInstance().createComprobante());
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            cfd.guardar(byteArrayOutputStream, false);
            String comprobante = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><cfdi:Comprobante version=\"3.0\" serie=\"ABCD\" folio=\"12\" fecha=\"2011-04-03T14:11:36\" formaDePago=\"PAGO EN UNA SOLA EXHIBICIÓN\" condicionesDePago=\"Factura de Contado\" subTotal=\"2050.00\" descuento=\"50.00\" motivoDescuento=\"Por pronto pago\" total=\"2320.00\" metodoDePago=\"Tarjeta de Débito\" tipoDeComprobante=\"ingreso\" xsi:schemaLocation=\"http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv3.xsd\" xmlns:cfdi=\"http://www.sat.gob.mx/cfd/3\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><cfdi:Emisor rfc=\"COA0106136Z4\" nombre=\"Comercializadora Ortega y Accionistas, S.A. de C.V.\"><cfdi:DomicilioFiscal calle=\"Leandro P. Gaxiola\" noExterior=\"6\" noInterior=\"6\" colonia=\"Olivares\" localidad=\"Hermosillo\" referencia=\"Casa de dos pisos\" municipio=\"Hermosillo\" estado=\"Sonora\" pais=\"México\" codigoPostal=\"83180\"/><cfdi:ExpedidoEn calle=\"Leandro P. Gaxiola\" noExterior=\"6\" noInterior=\"6\" colonia=\"Olivares\" localidad=\"Hermosillo\" referencia=\"Casa de dos pisos\" municipio=\"Hermosillo\" estado=\"Sonora\" pais=\"México\" codigoPostal=\"83180\"/></cfdi:Emisor><cfdi:Receptor rfc=\"H&amp;E951128469\" nombre=\"Herreria &amp; Electricos S.A. de C.V.\"><cfdi:Domicilio calle=\"Calle conocida\" noExterior=\"1\" noInterior=\"2\" colonia=\"Colonia Centro\" localidad=\"Hermosillo\" referencia=\"Carpa verde\" municipio=\"Hermosillo\" estado=\"Sonora\" pais=\"México\" codigoPostal=\"83000\"/></cfdi:Receptor><cfdi:Conceptos><cfdi:Concepto cantidad=\"1.00\" unidad=\"Servicio\" noIdentificacion=\"01\" descripcion=\"Asesoria Fiscal y administrativa\" valorUnitario=\"1000.00\" importe=\"1000.00\"/><cfdi:Concepto cantidad=\"2.00\" unidad=\"Servicio\" noIdentificacion=\"02\" descripcion=\"Asesoria Fiscal y administrativa 2\" valorUnitario=\"500.00\" importe=\"1000.00\"/></cfdi:Conceptos><cfdi:Impuestos totalImpuestosTrasladados=\"320.00\"><cfdi:Traslados><cfdi:Traslado impuesto=\"IVA\" tasa=\"16.00\" importe=\"320.00\"/></cfdi:Traslados></cfdi:Impuestos></cfdi:Comprobante>";
            Assert.assertEquals(comprobante, byteArrayOutputStream.toString());
        }
    }

    @Test
    public void testCreateDonatComprobante() throws Exception {
        Comprobante comprobante = ExampleCFDv3Factory.getInstance().createComprobante();
        comprobante.setComplemento(ExampleCFDv3Factory.getInstance().createComplemento());
        Donatarias donat = ExampleDonatFactory.getInstance().createDonatarias();
        comprobante.getComplemento().getAny().add(donat);
        CFDv30 cfd = new CFDv30(comprobante);
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            cfd.guardar(byteArrayOutputStream, false);
            String comp = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><cfdi:Comprobante version=\"3.0\" serie=\"ABCD\" folio=\"12\" fecha=\"2011-04-03T14:11:36\" formaDePago=\"PAGO EN UNA SOLA EXHIBICIÓN\" condicionesDePago=\"Factura de Contado\" subTotal=\"2050.00\" descuento=\"50.00\" motivoDescuento=\"Por pronto pago\" total=\"2320.00\" metodoDePago=\"Tarjeta de Débito\" tipoDeComprobante=\"ingreso\" xsi:schemaLocation=\"http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv3.xsd http://www.sat.gob.mx/donat http://www.sat.gob.mx/sitio_internet/cfd/donat/donat.xsd\" xmlns:donat=\"http://www.sat.gob.mx/donat\" xmlns:cfdi=\"http://www.sat.gob.mx/cfd/3\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><cfdi:Emisor rfc=\"COA0106136Z4\" nombre=\"Comercializadora Ortega y Accionistas, S.A. de C.V.\"><cfdi:DomicilioFiscal calle=\"Leandro P. Gaxiola\" noExterior=\"6\" noInterior=\"6\" colonia=\"Olivares\" localidad=\"Hermosillo\" referencia=\"Casa de dos pisos\" municipio=\"Hermosillo\" estado=\"Sonora\" pais=\"México\" codigoPostal=\"83180\"/><cfdi:ExpedidoEn calle=\"Leandro P. Gaxiola\" noExterior=\"6\" noInterior=\"6\" colonia=\"Olivares\" localidad=\"Hermosillo\" referencia=\"Casa de dos pisos\" municipio=\"Hermosillo\" estado=\"Sonora\" pais=\"México\" codigoPostal=\"83180\"/></cfdi:Emisor><cfdi:Receptor rfc=\"H&amp;E951128469\" nombre=\"Herreria &amp; Electricos S.A. de C.V.\"><cfdi:Domicilio calle=\"Calle conocida\" noExterior=\"1\" noInterior=\"2\" colonia=\"Colonia Centro\" localidad=\"Hermosillo\" referencia=\"Carpa verde\" municipio=\"Hermosillo\" estado=\"Sonora\" pais=\"México\" codigoPostal=\"83000\"/></cfdi:Receptor><cfdi:Conceptos><cfdi:Concepto cantidad=\"1.00\" unidad=\"Servicio\" noIdentificacion=\"01\" descripcion=\"Asesoria Fiscal y administrativa\" valorUnitario=\"1000.00\" importe=\"1000.00\"/><cfdi:Concepto cantidad=\"2.00\" unidad=\"Servicio\" noIdentificacion=\"02\" descripcion=\"Asesoria Fiscal y administrativa 2\" valorUnitario=\"500.00\" importe=\"1000.00\"/></cfdi:Conceptos><cfdi:Impuestos totalImpuestosTrasladados=\"320.00\"><cfdi:Traslados><cfdi:Traslado impuesto=\"IVA\" tasa=\"16.00\" importe=\"320.00\"/></cfdi:Traslados></cfdi:Impuestos><cfdi:Complemento><donat:Donatarias version=\"1.0\" noAutorizacion=\"prueba de donatarias 01\" fechaAutorizacion=\"2011-04-03\" leyenda=\"Este comprobante ampara un donativo, el cual será destinado por la donataria a los fines propios de su objeto social. En el caso de que los bienes donados hayan sido deducidos previamente para los efectos del impuesto sobre la renta, este donativo no es deducible. La reproducción no autorizada de este comprobante constituye un delito en los términos de las disposiciones fiscales.\"/></cfdi:Complemento></cfdi:Comprobante>";
            Assert.assertEquals(comp, byteArrayOutputStream.toString());
        }
    }

    @Test
    public void testCleanCadenaOriginal() throws Exception {
        CFDv30 cfd = new CFDv30(ExampleCFDv3Factory.getInstance().createComprobante());
        String cadena = "||3.0|2011-04-03T14:11:36|ingreso|PAGO EN UNA SOLA EXHIBICIÓN|Factura de Contado|2050.00|50.00|2320.00|COA0106136Z4|Comercializadora Ortega y Accionistas, S.A. de C.V.|Leandro P. Gaxiola|6|6|Olivares|Hermosillo|Casa de dos pisos|Hermosillo|Sonora|México|83180|Leandro P. Gaxiola|6|6|Olivares|Hermosillo|Casa de dos pisos|Hermosillo|Sonora|México|83180|H&E951128469|Herreria & Electricos S.A. de C.V.|Calle conocida|1|2|Colonia Centro|Hermosillo|Carpa verde|Hermosillo|Sonora|México|83000|1.00|Servicio|01|Asesoria Fiscal y administrativa|1000.00|1000.00|2.00|Servicio|02|Asesoria Fiscal y administrativa 2|500.00|1000.00|IVA|16.00|320.00|320.00||";
        Assert.assertEquals(cadena, cfd.getCadenaOriginal());
    }

    @Test
    public void testDonatCadenaOriginal() throws Exception {
        Comprobante comprobante = ExampleCFDv3Factory.getInstance().createComprobante();
        comprobante.setComplemento(ExampleCFDv3Factory.getInstance().createComplemento());
        Donatarias donat = ExampleDonatFactory.getInstance().createDonatarias();
        comprobante.getComplemento().getAny().add(donat);
        CFDv30 cfd = new CFDv30(comprobante);
        String cadena = "||3.0|2011-04-03T14:11:36|ingreso|PAGO EN UNA SOLA EXHIBICIÓN|Factura de Contado|2050.00|50.00|2320.00|COA0106136Z4|Comercializadora Ortega y Accionistas, S.A. de C.V.|Leandro P. Gaxiola|6|6|Olivares|Hermosillo|Casa de dos pisos|Hermosillo|Sonora|México|83180|Leandro P. Gaxiola|6|6|Olivares|Hermosillo|Casa de dos pisos|Hermosillo|Sonora|México|83180|H&E951128469|Herreria & Electricos S.A. de C.V.|Calle conocida|1|2|Colonia Centro|Hermosillo|Carpa verde|Hermosillo|Sonora|México|83000|1.00|Servicio|01|Asesoria Fiscal y administrativa|1000.00|1000.00|2.00|Servicio|02|Asesoria Fiscal y administrativa 2|500.00|1000.00|IVA|16.00|320.00|320.00|1.0|prueba de donatarias 01|2011-04-03||";
        Assert.assertEquals(cadena, cfd.getCadenaOriginal());
    }

    @Test
    public void testCleanSign() throws Exception {
        CFDv30 cfd = new CFDv30(ExampleCFDv3Factory.getInstance().createComprobante());
        Comprobante sellado = cfd.sellarComprobante(key, cert);
        String signature = "J9GLrodnREBBIeuaLUsd82B39wOUy/4/kaOHuQkPYPIGZ6bNgZfMvxoHxEMzr9U9b+1Hg1n9Wh3vK4mxgb+egt7u9y9vzhuiTKN4HIBD6NsLwHPDK3Z2+Lw2FmyJxDT7m9xDWbbz5msM5jncYcG4Ls3SLyaDhPbVJaljc6o48WiVcXAF5t8kuFKSdNTx9YPCovoZMF97SK2ac8yoaH7WjvntZXlzHdT47caAqLE1I8MRZD7L6KQ3NOzpIl9u4t5LGScjSMp2XjFSt/7qHxe4VuyJa9RsMZXLAnOItWZm8J7Vmv1g0Id6XggnJ7QUupt28aHBBZ3x6lj2lKn2hJ5klA==";
        Assert.assertEquals(signature, sellado.getSello());
        String certificate = "MIIFwTCCA6mgAwIBAgIUMzAwMDEwMDAwMDA0MDAwMDI0NjMwDQYJKoZIhvcNAQELBQAwggErMQ8wDQYDVQQDDAZBQyBVQVQxLjAsBgNVBAoMJVNFUlZJQ0lPIERFIEFETUlOSVNUUkFDSU9OIFRSSUJVVEFSSUExGjAYBgNVBAsMEVNBVC1JRVMgQXV0aG9yaXR5MSgwJgYJKoZIhvcNAQkBFhlvc2Nhci5tYXJ0aW5lekBzYXQuZ29iLm14MR0wGwYDVQQJDBQzcmEgY2VycmFkYSBkZSBjYWRpejEOMAwGA1UEEQwFMDYzNzAxCzAJBgNVBAYTAk1YMRkwFwYDVQQIDBBDSVVEQUQgREUgTUVYSUNPMREwDwYDVQQHDAhDT1lPQUNBTjERMA8GA1UELRMIMi41LjQuNDUxJTAjBgkqhkiG9w0BCQITFnJlc3BvbnNhYmxlOiBBQ0RNQS1TQVQwHhcNMTkwNjIwMjA1NTA4WhcNMjMwNjIwMjA1NTA4WjCB6DEmMCQGA1UEAxQdSEVSUkVSSUEgJiBFTEVDVFJJQ09TIFMgREUgQ1YxJjAkBgNVBCkUHUhFUlJFUklBICYgRUxFQ1RSSUNPUyBTIERFIENWMSYwJAYDVQQKFB1IRVJSRVJJQSAmIEVMRUNUUklDT1MgUyBERSBDVjElMCMGA1UELRQcSCZFOTUxMTI4NDY5IC8gWE9KSTc0MDkxOVU0ODEeMBwGA1UEBRMVIC8gWE9KSTc0MDkxOU1RVERNTjAyMScwJQYDVQQLFB5IRVJSRVJJQSAmIEVMRUNUUklDT1MgU0EgREUgQ1YwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDoNWRICH7Ja/IR0sFzGAoveKzFT94BJ4Wlh7MsePe50azvre4j6CYEItQ5jCkxZyYHT23dfONpUWDN7+Bx7j9QjKPwqr+zJjFrla/hD4fj63LeUmofVR7TjeDLLEwCSsn1k3vLae4WBgsV1jNc3M87ZYOHpe0XXLVw/25nSyqQS5l4HTJrw7wQ3NZAXghG38NIOUYJZsE/6K3OAslVoF/YregQ0UqodFLyA6BBMcfll8rjMIWtCy2DxZ0T5QCPX3OccWzB9hHvP5PAjUiOf+FpKHW8WPGEywoC1vM3nvsHXTp2MkUn78U5PK/hmc/7chqaneZHA066AIRs3zGHkHTjAgMBAAGjHTAbMAwGA1UdEwEB/wQCMAAwCwYDVR0PBAQDAgbAMA0GCSqGSIb3DQEBCwUAA4ICAQCsaJpin0RAOsyxBMQRpfTM47k+a1QlHHsP2vEubuINZE+3Hp4b/C1w1gGe8+l8EJcLxQfQ0ovlvKDdYFJLleBCkt9eoFY92keaL77Teq6YxzQ8jArI1WSz75KkljzHYbtm4Oy+hsmpla4L51VnhRfyqehfAgZEDRHytpOPVzxIQnnDx/GI2KVlUBvInOS50dBoCnq5sxzQ+Ha1nsPMvrdTaHBRjto4kOL9SkHvZDVZHWnJ1R7w+1n0zXmDV27BUNsHQNaDG8e+5uGex7d/Bfolf3dU2y4YVFN9DOP8ge0J7ryIuRB7eESwT3aEM/0gec41vMOfVmR2mzNeX0q8czU0xjMH+NKSdgkNFAvoMk2uIsGePkbQDdk3XW1h6f3HGRQ85mkPh/Zt4kqbc0CzFsQMGdj8eixm8jRazSoCdndSDXrDwSTkVaqTuYqRxy89NWNk4dbXXqw4a52DKR1oYR0r9AVjHTfK936tRvNhxQ4KHGjPUi0ttxoCw1fogKwf2MRkTZMO0EZrEnODKl3CMoi4IXuWpNYY6MhUCjLRWJF/mOclkpCzNpyNbwJiGTshTEDvfqS1PsIuXgjIzbCa3seRzP6yxsd0CfEI7lUxMvYT6r0wqRXsymO6mOywWQKRFnrZmzD1zSMRap7vhED+plAxUgouQFbfv1NDkqAfF57FFA==";
        Assert.assertEquals(certificate, sellado.getCertificado());
        String certificateNum = "30001000000400002463";
        Assert.assertEquals(certificateNum, sellado.getNoCertificado());
        cfd.validar(null);
        cfd.verificar();
    }

    @Test
    public void testDonatSign() throws Exception {
        Comprobante comprobante = ExampleCFDv3Factory.getInstance().createComprobante();
        comprobante.setComplemento(ExampleCFDv3Factory.getInstance().createComplemento());
        Donatarias donat = ExampleDonatFactory.getInstance().createDonatarias();
        comprobante.getComplemento().getAny().add(donat);
        CFDv30 cfd = new CFDv30(comprobante);
        Comprobante sellado = cfd.sellarComprobante(key, cert);
        String signature = "mIoDB8cKzA+gEYfWATfNZFNCn7zkmsxX5mMRDCjNL/lks7O/JQHe/RitWE3L23YjdKxHcqrx0WRzU6K9RjwWecD4MyaQK3hIEbtafM5l1WL8a+/I+9lkrd2GXCYY2Yw0tO/mZ132PLy7wcK5Srwqeb25hylM3IPNCOE4vIHtU+Ta94etTiYPBIgvNiIx8nEvzmC8w7wtmTV7/S3IvbCE2SQlhjsdFYXhzSIbn4TMTGUnusRL0vNlraIHPc81y+DdBB0NK48F4pumJKKOF6/qI/thWsGaNsi408ZeuLziT53SHOv22pS/geBK8hAocDPISEfFIxPY4rqkKaEnfEA58g==";
        Assert.assertEquals(signature, sellado.getSello());
        String certificate = "MIIFwTCCA6mgAwIBAgIUMzAwMDEwMDAwMDA0MDAwMDI0NjMwDQYJKoZIhvcNAQELBQAwggErMQ8wDQYDVQQDDAZBQyBVQVQxLjAsBgNVBAoMJVNFUlZJQ0lPIERFIEFETUlOSVNUUkFDSU9OIFRSSUJVVEFSSUExGjAYBgNVBAsMEVNBVC1JRVMgQXV0aG9yaXR5MSgwJgYJKoZIhvcNAQkBFhlvc2Nhci5tYXJ0aW5lekBzYXQuZ29iLm14MR0wGwYDVQQJDBQzcmEgY2VycmFkYSBkZSBjYWRpejEOMAwGA1UEEQwFMDYzNzAxCzAJBgNVBAYTAk1YMRkwFwYDVQQIDBBDSVVEQUQgREUgTUVYSUNPMREwDwYDVQQHDAhDT1lPQUNBTjERMA8GA1UELRMIMi41LjQuNDUxJTAjBgkqhkiG9w0BCQITFnJlc3BvbnNhYmxlOiBBQ0RNQS1TQVQwHhcNMTkwNjIwMjA1NTA4WhcNMjMwNjIwMjA1NTA4WjCB6DEmMCQGA1UEAxQdSEVSUkVSSUEgJiBFTEVDVFJJQ09TIFMgREUgQ1YxJjAkBgNVBCkUHUhFUlJFUklBICYgRUxFQ1RSSUNPUyBTIERFIENWMSYwJAYDVQQKFB1IRVJSRVJJQSAmIEVMRUNUUklDT1MgUyBERSBDVjElMCMGA1UELRQcSCZFOTUxMTI4NDY5IC8gWE9KSTc0MDkxOVU0ODEeMBwGA1UEBRMVIC8gWE9KSTc0MDkxOU1RVERNTjAyMScwJQYDVQQLFB5IRVJSRVJJQSAmIEVMRUNUUklDT1MgU0EgREUgQ1YwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDoNWRICH7Ja/IR0sFzGAoveKzFT94BJ4Wlh7MsePe50azvre4j6CYEItQ5jCkxZyYHT23dfONpUWDN7+Bx7j9QjKPwqr+zJjFrla/hD4fj63LeUmofVR7TjeDLLEwCSsn1k3vLae4WBgsV1jNc3M87ZYOHpe0XXLVw/25nSyqQS5l4HTJrw7wQ3NZAXghG38NIOUYJZsE/6K3OAslVoF/YregQ0UqodFLyA6BBMcfll8rjMIWtCy2DxZ0T5QCPX3OccWzB9hHvP5PAjUiOf+FpKHW8WPGEywoC1vM3nvsHXTp2MkUn78U5PK/hmc/7chqaneZHA066AIRs3zGHkHTjAgMBAAGjHTAbMAwGA1UdEwEB/wQCMAAwCwYDVR0PBAQDAgbAMA0GCSqGSIb3DQEBCwUAA4ICAQCsaJpin0RAOsyxBMQRpfTM47k+a1QlHHsP2vEubuINZE+3Hp4b/C1w1gGe8+l8EJcLxQfQ0ovlvKDdYFJLleBCkt9eoFY92keaL77Teq6YxzQ8jArI1WSz75KkljzHYbtm4Oy+hsmpla4L51VnhRfyqehfAgZEDRHytpOPVzxIQnnDx/GI2KVlUBvInOS50dBoCnq5sxzQ+Ha1nsPMvrdTaHBRjto4kOL9SkHvZDVZHWnJ1R7w+1n0zXmDV27BUNsHQNaDG8e+5uGex7d/Bfolf3dU2y4YVFN9DOP8ge0J7ryIuRB7eESwT3aEM/0gec41vMOfVmR2mzNeX0q8czU0xjMH+NKSdgkNFAvoMk2uIsGePkbQDdk3XW1h6f3HGRQ85mkPh/Zt4kqbc0CzFsQMGdj8eixm8jRazSoCdndSDXrDwSTkVaqTuYqRxy89NWNk4dbXXqw4a52DKR1oYR0r9AVjHTfK936tRvNhxQ4KHGjPUi0ttxoCw1fogKwf2MRkTZMO0EZrEnODKl3CMoi4IXuWpNYY6MhUCjLRWJF/mOclkpCzNpyNbwJiGTshTEDvfqS1PsIuXgjIzbCa3seRzP6yxsd0CfEI7lUxMvYT6r0wqRXsymO6mOywWQKRFnrZmzD1zSMRap7vhED+plAxUgouQFbfv1NDkqAfF57FFA==";
        Assert.assertEquals(certificate, sellado.getCertificado());
        String certificateNum = "30001000000400002463";
        Assert.assertEquals(certificateNum, sellado.getNoCertificado());
        cfd.validar(null);
        cfd.verificar();
    }

    @Test
    public void testValidateVerifyFail() throws Exception {
        CFDv3 cfd = CFDv3Factory.load(new File("resources/xmls/cfdi/v30/CFDv30_before.xml"));
        ValidationErrorHandler handler = new ValidationErrorHandler();
        cfd.validar(handler);
        Assert.assertFalse(handler.getErrors().isEmpty());
    }

    @Test
    public void testValidateVerifyDonatFail() throws Exception {
        CFDv3 cfd = CFDv3Factory.load(new File("resources/xmls/cfdi/v30/CFDv30_donat_before.xml"));
        ValidationErrorHandler handler = new ValidationErrorHandler();
        cfd.validar(handler);
        Assert.assertFalse(handler.getErrors().isEmpty());
    }

    @Test
    public void testValidateVerifyOk() throws Exception {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            CFDv3 cfd = CFDv3Factory.load(new File("resources/xmls/cfdi/v30/CFDv30_before.xml"));
            cfd.sellar(key, cert);
            cfd.guardar(baos, false);
            try (ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray())) {
                CFDv3 cfd3 = new CFDv30(bais);
                cfd3.validar(null);
                cfd3.verificar();
            }
        }
    }

    @Test
    public void testValidateVerifyDonatOk() throws Exception {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            CFDv3 cfd = CFDv3Factory.load(new File("resources/xmls/cfdi/v30/CFDv30_donat_before.xml"));
            cfd.sellar(key, cert);
            cfd.guardar(baos, false);
            try (ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray())) {
                CFDv3 cfd2 = new CFDv30(bais);
                cfd2.validar(null);
                cfd2.verificar();
            }
        }
    }

    @Test
    public void testCleanLoad() throws Exception {
        Comprobante c = CFDv30.newComprobante(new FileInputStream("resources/xmls/cfdi/v30/CFDv30.xml"));
        CFDv30 cfd = new CFDv30(c);
        cfd.validar(null);
        cfd.verificar();
    }

    @Test
    public void testDonatLoad() throws Exception {
        Comprobante c = CFDv30.newComprobante(new FileInputStream("resources/xmls/cfdi/v30/CFDv30_donat.xml"));
        CFDv30 cfd = new CFDv30(c);
        cfd.validar(null);
        cfd.verificar();
    }

    @Test
    public void testSellarComprobante() throws Exception {
        Comprobante c = CFDv30.newComprobante(new FileInputStream("resources/xmls/cfdi/v30/CFDv30_before.xml"));
        CFDv30 cfd = new CFDv30(c);
        Comprobante sellado = cfd.sellarComprobante(key, cert);
        Assert.assertNotNull(sellado.getSello());
        Assert.assertNotNull(sellado.getNoCertificado());
        Assert.assertNotNull(sellado.getCertificado());
        Comprobante c2 = CFDv30.newComprobante(new FileInputStream("resources/xmls/cfdi/v30/CFDv30.xml"));
        CFDv30 cfd2 = new CFDv30(c2);
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
    public void testSellarComprobanteDonat() throws Exception {
        Comprobante c = CFDv30.newComprobante(new FileInputStream("resources/xmls/cfdi/v30/CFDv30_donat_before.xml"));
        c.setFormaDePago("PAGO EN UNA SOLA EXHIBICIÓN");
        c.setCondicionesDePago("Factura de Contado");
        c.setMetodoDePago("Tarjeta de Débito");
        CFDv30 cfd = new CFDv30(c);
        Comprobante sellado = cfd.sellarComprobante(key, cert);
        Assert.assertNotNull(sellado.getSello());
        Assert.assertNotNull(sellado.getNoCertificado());
        Assert.assertNotNull(sellado.getCertificado());
        Comprobante c2 = CFDv30.newComprobante(new FileInputStream("resources/xmls/cfdi/v30/CFDv30_donat.xml"));
        CFDv30 cfd2 = new CFDv30(c2);
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
    public void testComprobanteTFD() throws Exception {
        Comprobante c = CFDv30.newComprobante(new FileInputStream("resources/xmls/cfdi/v30/CFDv30.xml"));
        c.setComplemento(ExampleCFDv3Factory.getInstance().createComplemento());
        TimbreFiscalDigital tfd = ExampleTimbreFiscal10Factory.getInstance().createTimbreFiscal();
        c.getComplemento().getAny().add(tfd);
        CFDv30 cfd = new CFDv30(c);
        String cfdi_tfd = Files.readString(Path.of("resources/xmls/cfdi/v30/CFDv30_tfd.xml")).replaceAll("(\\r\\n|\\r|\\n|\\t)", "");
        try (OutputStream outputStream = new ByteArrayOutputStream()) {
            cfd.validar(null);
            cfd.verificar();
            cfd.guardar(outputStream, false);
            Assert.assertEquals(cfdi_tfd, String.valueOf(outputStream));
        }
    }

}
