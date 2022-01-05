package mx.grupocorasa.sat.cfdi.v3;

import mx.grupocorasa.sat.cfd._32.Comprobante;
import mx.grupocorasa.sat.common.TimbreFiscalDigital10.TimbreFiscalDigital;
import mx.grupocorasa.sat.common.ValidationErrorHandler;
import mx.grupocorasa.sat.common.nomina11.Nomina;
import mx.grupocorasa.sat.factory.cfd.ExampleCFDv32Factory;
import mx.grupocorasa.sat.factory.common.ExampleNomina11Factory;
import mx.grupocorasa.sat.factory.common.ExampleTimbreFiscal10Factory;
import mx.grupocorasa.sat.security.KeyLoaderEnumeration;
import mx.grupocorasa.sat.security.factory.KeyLoaderFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public final class CFDv32Test {

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
        CFDv32 cfd = new CFDv32(ExampleCFDv32Factory.getInstance().createComprobante());
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            cfd.guardar(byteArrayOutputStream, false);
            String comprobante = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><cfdi:Comprobante version=\"3.2\" serie=\"DCBA\" folio=\"81\" fecha=\"2015-03-11T17:08:22\" formaDePago=\"Pago en una sola exhibición\" condicionesDePago=\"Factura a Crédito\" subTotal=\"1681.04\" descuento=\"323.28\" motivoDescuento=\"Por acuerdo mutuo\" TipoCambio=\"1.00\" Moneda=\"MXN\" total=\"1575.00\" tipoDeComprobante=\"ingreso\" metodoDePago=\"Transferencia Electrónica\" LugarExpedicion=\"Hermosillo, Sonora, México\" NumCtaPago=\"1587\" xsi:schemaLocation=\"http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv32.xsd\" xmlns:cfdi=\"http://www.sat.gob.mx/cfd/3\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><cfdi:Emisor rfc=\"COA0106136Z4\" nombre=\"Comercializadora Ortega y Accionistas, S.A. de C.V.\"><cfdi:DomicilioFiscal calle=\"Leandro P. Gaxiola\" noExterior=\"6\" noInterior=\"6\" colonia=\"Olivares\" localidad=\"Hermosillo\" referencia=\"Casa de dos pisos\" municipio=\"Hermosillo\" estado=\"Sonora\" pais=\"México\" codigoPostal=\"83180\"/><cfdi:ExpedidoEn calle=\"Leandro P. Gaxiola\" noExterior=\"6\" noInterior=\"6\" colonia=\"Olivares\" localidad=\"Hermosillo\" referencia=\"Casa de dos pisos\" municipio=\"Hermosillo\" estado=\"Sonora\" pais=\"México\" codigoPostal=\"83180\"/><cfdi:RegimenFiscal Regimen=\"Régimen General de Ley Persona Moral\"/></cfdi:Emisor><cfdi:Receptor rfc=\"H&amp;E951128469\" nombre=\"Herreria &amp; Electricos S.A. de C.V.\"><cfdi:Domicilio calle=\"Calle conocida\" noExterior=\"1\" noInterior=\"2\" colonia=\"Colonia Centro\" localidad=\"Hermosillo\" referencia=\"Carpa verde\" municipio=\"Hermosillo\" estado=\"Sonora\" pais=\"México\" codigoPostal=\"83000\"/></cfdi:Receptor><cfdi:Conceptos><cfdi:Concepto cantidad=\"1.00\" unidad=\"Servicio\" noIdentificacion=\"01\" descripcion=\"Asesoria Fiscal y administrativa\" valorUnitario=\"1681.04\" importe=\"1681.04\"/></cfdi:Conceptos><cfdi:Impuestos totalImpuestosTrasladados=\"217.24\"><cfdi:Traslados><cfdi:Traslado impuesto=\"IVA\" tasa=\"16.00\" importe=\"217.24\"/></cfdi:Traslados></cfdi:Impuestos></cfdi:Comprobante>";
            Assert.assertEquals(comprobante, byteArrayOutputStream.toString());
        }
    }

    @Test
    public void testCreateNominaComprobante() throws Exception {
        Comprobante comprobante = ExampleCFDv32Factory.getInstance().createComprobanteNomina();
        comprobante.setComplemento(ExampleCFDv32Factory.getInstance().createComplemento());
        Nomina nomina = ExampleNomina11Factory.getInstance().createNomina();
        comprobante.getComplemento().getAny().add(nomina);
        CFDv32 cfd = new CFDv32(comprobante);
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            cfd.guardar(byteArrayOutputStream, false);
            String comp = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><cfdi:Comprobante version=\"3.2\" serie=\"NOM\" folio=\"312\" fecha=\"2016-10-13T14:52:36\" formaDePago=\"Pago en una sola exhibición\" subTotal=\"4325.77\" descuento=\"436.61\" motivoDescuento=\"Deducciones nómina\" TipoCambio=\"1.00\" Moneda=\"MXN\" total=\"3513.58\" tipoDeComprobante=\"egreso\" metodoDePago=\"03\" LugarExpedicion=\"Hermosillo, Sonora, México\" xsi:schemaLocation=\"http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv32.xsd http://www.sat.gob.mx/nomina http://www.sat.gob.mx/sitio_internet/cfd/nomina/nomina11.xsd\" xmlns:nomina=\"http://www.sat.gob.mx/nomina\" xmlns:cfdi=\"http://www.sat.gob.mx/cfd/3\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><cfdi:Emisor rfc=\"COA0106136Z4\" nombre=\"Comercializadora Ortega y Accionistas, S.A. de C.V.\"><cfdi:DomicilioFiscal calle=\"Leandro P. Gaxiola\" noExterior=\"6\" noInterior=\"6\" colonia=\"Olivares\" localidad=\"Hermosillo\" referencia=\"Casa de dos pisos\" municipio=\"Hermosillo\" estado=\"Sonora\" pais=\"México\" codigoPostal=\"83180\"/><cfdi:ExpedidoEn calle=\"Leandro P. Gaxiola\" noExterior=\"6\" noInterior=\"6\" colonia=\"Olivares\" localidad=\"Hermosillo\" referencia=\"Casa de dos pisos\" municipio=\"Hermosillo\" estado=\"Sonora\" pais=\"México\" codigoPostal=\"83180\"/><cfdi:RegimenFiscal Regimen=\"Régimen General de Ley Persona Moral\"/></cfdi:Emisor><cfdi:Receptor rfc=\"OEGH910609BZ6\" nombre=\"Heriberto Ortega Gonzalez\"><cfdi:Domicilio estado=\"Sonora\" pais=\"México\"/></cfdi:Receptor><cfdi:Conceptos><cfdi:Concepto cantidad=\"1.00\" unidad=\"Servicio\" descripcion=\"Pago de nómina\" valorUnitario=\"4325\" importe=\"4325\"/></cfdi:Conceptos><cfdi:Impuestos totalImpuestosRetenidos=\"375.58\"><cfdi:Retenciones><cfdi:Retencion impuesto=\"ISR\" importe=\"375.58\"/></cfdi:Retenciones></cfdi:Impuestos><cfdi:Complemento><nomina:Nomina Version=\"1.1\" RegistroPatronal=\"E6479648108\" NumEmpleado=\"001\" CURP=\"OEGH910609HNLRNR14\" TipoRegimen=\"02\" NumSeguridadSocial=\"24109129148\" FechaPago=\"2017-02-15\" FechaInicialPago=\"2017-02-01\" FechaFinalPago=\"2017-02-15\" NumDiasPagados=\"15\" Departamento=\"Pruebas\" CLABE=\"002760700569076003\" Banco=\"002\" FechaInicioRelLaboral=\"2017-01-01\" Antiguedad=\"7\" Puesto=\"Developer\" TipoContrato=\"01\" TipoJornada=\"01\" PeriodicidadPago=\"04\" SalarioBaseCotApor=\"293.73\" RiesgoPuesto=\"2\" SalarioDiarioIntegrado=\"293.73\"><nomina:Percepciones TotalGravado=\"4165.56\" TotalExento=\"160.21\"><nomina:Percepcion TipoPercepcion=\"001\" Clave=\"001\" Concepto=\"Sueldo Quincenal de Prueba\" ImporteGravado=\"4005.35\" ImporteExento=\"0\"/><nomina:Percepcion TipoPercepcion=\"010\" Clave=\"006\" Concepto=\"Premio por Asistencia\" ImporteGravado=\"160.21\" ImporteExento=\"0\"/><nomina:Percepcion TipoPercepcion=\"005\" Clave=\"007\" Concepto=\"Fondo de Ahorro\" ImporteGravado=\"0\" ImporteExento=\"160.21\"/></nomina:Percepciones><nomina:Deducciones TotalGravado=\"812.19\" TotalExento=\"0\"><nomina:Deduccion TipoDeduccion=\"002\" Clave=\"011\" Concepto=\"Retencion ISR\" ImporteGravado=\"375.58\" ImporteExento=\"0\"/><nomina:Deduccion TipoDeduccion=\"001\" Clave=\"012\" Concepto=\"Descuento IMSS\" ImporteGravado=\"116.18\" ImporteExento=\"0\"/><nomina:Deduccion TipoDeduccion=\"004\" Clave=\"016\" Concepto=\"Aportación Fondo de Ahorro\" ImporteGravado=\"320.43\" ImporteExento=\"0\"/></nomina:Deducciones></nomina:Nomina></cfdi:Complemento></cfdi:Comprobante>";
            Assert.assertEquals(comp, byteArrayOutputStream.toString());
        }
    }

    @Test
    public void testCleanCadenaOriginal() throws Exception {
        CFDv32 cfd = new CFDv32(ExampleCFDv32Factory.getInstance().createComprobante());
        String cadena = "||3.2|2015-03-11T17:08:22|ingreso|Pago en una sola exhibición|Factura a Crédito|1681.04|323.28|1.00|MXN|1575.00|Transferencia Electrónica|Hermosillo, Sonora, México|1587|COA0106136Z4|Comercializadora Ortega y Accionistas, S.A. de C.V.|Leandro P. Gaxiola|6|6|Olivares|Hermosillo|Casa de dos pisos|Hermosillo|Sonora|México|83180|Leandro P. Gaxiola|6|6|Olivares|Hermosillo|Casa de dos pisos|Hermosillo|Sonora|México|83180|Régimen General de Ley Persona Moral|H&E951128469|Herreria & Electricos S.A. de C.V.|Calle conocida|1|2|Colonia Centro|Hermosillo|Carpa verde|Hermosillo|Sonora|México|83000|1.00|Servicio|01|Asesoria Fiscal y administrativa|1681.04|1681.04|IVA|16.00|217.24|217.24||";
        Assert.assertEquals(cadena, cfd.getCadenaOriginal());
    }

    @Test
    public void testNominaCadenaOriginal() throws Exception {
        Comprobante comprobante = ExampleCFDv32Factory.getInstance().createComprobanteNomina();
        comprobante.setComplemento(ExampleCFDv32Factory.getInstance().createComplemento());
        Nomina nomina = ExampleNomina11Factory.getInstance().createNomina();
        comprobante.getComplemento().getAny().add(nomina);
        CFDv32 cfd = new CFDv32(comprobante);
        String cadena = "||3.2|2016-10-13T14:52:36|egreso|Pago en una sola exhibición|4325.77|436.61|1.00|MXN|3513.58|03|Hermosillo, Sonora, México|COA0106136Z4|Comercializadora Ortega y Accionistas, S.A. de C.V.|Leandro P. Gaxiola|6|6|Olivares|Hermosillo|Casa de dos pisos|Hermosillo|Sonora|México|83180|Leandro P. Gaxiola|6|6|Olivares|Hermosillo|Casa de dos pisos|Hermosillo|Sonora|México|83180|Régimen General de Ley Persona Moral|OEGH910609BZ6|Heriberto Ortega Gonzalez|Sonora|México|1.00|Servicio|Pago de nómina|4325|4325|ISR|375.58|375.58|1.1|E6479648108|001|OEGH910609HNLRNR14|02|24109129148|2017-02-15|2017-02-01|2017-02-15|15|Pruebas|002760700569076003|002|2017-01-01|7|Developer|01|01|04|293.73|2|293.73|4165.56|160.21|001|001|Sueldo Quincenal de Prueba|4005.35|0|010|006|Premio por Asistencia|160.21|0|005|007|Fondo de Ahorro|0|160.21|812.19|0|002|011|Retencion ISR|375.58|0|001|012|Descuento IMSS|116.18|0|004|016|Aportación Fondo de Ahorro|320.43|0||";
        Assert.assertEquals(cadena, cfd.getCadenaOriginal());
    }

    @Test
    public void testCleanSign() throws Exception {
        CFDv32 cfd = new CFDv32(ExampleCFDv32Factory.getInstance().createComprobante());
        Comprobante sellado = cfd.sellarComprobante(key, cert);
        String signature = "MwqkAMeohL4NstwJXPdvLCS6NT8y8qVK/BTcphk1TWdli25djG59KClqzYDU5pirVgWCFZtQrcD8tqhZA1wJAeK5/RS42e34+G3rtbiHiotaGYyV9SqVp3KJVnHPCmnbznTc5fUJxBCX8brVeIMJ9U50ALoNBrlFs2pCs+fnxNKZB/FMYlAOm67Az4UHHsXiRFvYPmWrt0RbApKHGqF8EuiRYBK7CsyeyNkci+ZuGx1DMj6GQ+O9ZyfQ4S+mETVf1gsUnHAMu1FgaDaOmW3V/qBs5Td26BW/+Swg5TxA02DV1jJ6WFPRP7VVuVIX2H69YPJ6blkJRRZcYgzAenrDVw==";
        Assert.assertEquals(signature, sellado.getSello());
        String certificate = "MIIFwTCCA6mgAwIBAgIUMzAwMDEwMDAwMDA0MDAwMDI0NjMwDQYJKoZIhvcNAQELBQAwggErMQ8wDQYDVQQDDAZBQyBVQVQxLjAsBgNVBAoMJVNFUlZJQ0lPIERFIEFETUlOSVNUUkFDSU9OIFRSSUJVVEFSSUExGjAYBgNVBAsMEVNBVC1JRVMgQXV0aG9yaXR5MSgwJgYJKoZIhvcNAQkBFhlvc2Nhci5tYXJ0aW5lekBzYXQuZ29iLm14MR0wGwYDVQQJDBQzcmEgY2VycmFkYSBkZSBjYWRpejEOMAwGA1UEEQwFMDYzNzAxCzAJBgNVBAYTAk1YMRkwFwYDVQQIDBBDSVVEQUQgREUgTUVYSUNPMREwDwYDVQQHDAhDT1lPQUNBTjERMA8GA1UELRMIMi41LjQuNDUxJTAjBgkqhkiG9w0BCQITFnJlc3BvbnNhYmxlOiBBQ0RNQS1TQVQwHhcNMTkwNjIwMjA1NTA4WhcNMjMwNjIwMjA1NTA4WjCB6DEmMCQGA1UEAxQdSEVSUkVSSUEgJiBFTEVDVFJJQ09TIFMgREUgQ1YxJjAkBgNVBCkUHUhFUlJFUklBICYgRUxFQ1RSSUNPUyBTIERFIENWMSYwJAYDVQQKFB1IRVJSRVJJQSAmIEVMRUNUUklDT1MgUyBERSBDVjElMCMGA1UELRQcSCZFOTUxMTI4NDY5IC8gWE9KSTc0MDkxOVU0ODEeMBwGA1UEBRMVIC8gWE9KSTc0MDkxOU1RVERNTjAyMScwJQYDVQQLFB5IRVJSRVJJQSAmIEVMRUNUUklDT1MgU0EgREUgQ1YwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDoNWRICH7Ja/IR0sFzGAoveKzFT94BJ4Wlh7MsePe50azvre4j6CYEItQ5jCkxZyYHT23dfONpUWDN7+Bx7j9QjKPwqr+zJjFrla/hD4fj63LeUmofVR7TjeDLLEwCSsn1k3vLae4WBgsV1jNc3M87ZYOHpe0XXLVw/25nSyqQS5l4HTJrw7wQ3NZAXghG38NIOUYJZsE/6K3OAslVoF/YregQ0UqodFLyA6BBMcfll8rjMIWtCy2DxZ0T5QCPX3OccWzB9hHvP5PAjUiOf+FpKHW8WPGEywoC1vM3nvsHXTp2MkUn78U5PK/hmc/7chqaneZHA066AIRs3zGHkHTjAgMBAAGjHTAbMAwGA1UdEwEB/wQCMAAwCwYDVR0PBAQDAgbAMA0GCSqGSIb3DQEBCwUAA4ICAQCsaJpin0RAOsyxBMQRpfTM47k+a1QlHHsP2vEubuINZE+3Hp4b/C1w1gGe8+l8EJcLxQfQ0ovlvKDdYFJLleBCkt9eoFY92keaL77Teq6YxzQ8jArI1WSz75KkljzHYbtm4Oy+hsmpla4L51VnhRfyqehfAgZEDRHytpOPVzxIQnnDx/GI2KVlUBvInOS50dBoCnq5sxzQ+Ha1nsPMvrdTaHBRjto4kOL9SkHvZDVZHWnJ1R7w+1n0zXmDV27BUNsHQNaDG8e+5uGex7d/Bfolf3dU2y4YVFN9DOP8ge0J7ryIuRB7eESwT3aEM/0gec41vMOfVmR2mzNeX0q8czU0xjMH+NKSdgkNFAvoMk2uIsGePkbQDdk3XW1h6f3HGRQ85mkPh/Zt4kqbc0CzFsQMGdj8eixm8jRazSoCdndSDXrDwSTkVaqTuYqRxy89NWNk4dbXXqw4a52DKR1oYR0r9AVjHTfK936tRvNhxQ4KHGjPUi0ttxoCw1fogKwf2MRkTZMO0EZrEnODKl3CMoi4IXuWpNYY6MhUCjLRWJF/mOclkpCzNpyNbwJiGTshTEDvfqS1PsIuXgjIzbCa3seRzP6yxsd0CfEI7lUxMvYT6r0wqRXsymO6mOywWQKRFnrZmzD1zSMRap7vhED+plAxUgouQFbfv1NDkqAfF57FFA==";
        Assert.assertEquals(certificate, sellado.getCertificado());
        String certificateNum = "30001000000400002463";
        Assert.assertEquals(certificateNum, sellado.getNoCertificado());
        cfd.validar(null);
        cfd.verificar();
    }

    @Test
    public void testNominaSign() throws Exception {
        Comprobante comprobante = ExampleCFDv32Factory.getInstance().createComprobanteNomina();
        comprobante.setComplemento(ExampleCFDv32Factory.getInstance().createComplemento());
        Nomina nomina = ExampleNomina11Factory.getInstance().createNomina();
        comprobante.getComplemento().getAny().add(nomina);
        CFDv32 cfd = new CFDv32(comprobante);
        Comprobante sellado = cfd.sellarComprobante(key, cert);
        String signature = "HjVl83EMqc/OWFOjfuLguUSz7xqAHwDciIsiYoOKwob0ly57fXnS+OQcbZ3IBLgqfkw8uNDBThQMCY5t7HMj4sGHE1BuzmzXH416oK+hVHowaE+ptWHdj7URAQZt6x7LePUI7BFnMdPcKlouMlXHZ+mNxrO4CZfPkYm5WBNBLeLD48UmQGzkSSNdJSrDC+ZoxJzewfVuPrPKd8E8hXUi3lAFJi8MhwCJELx6byVzQjXTChKqmqlzRDjcc8SXIE5DS44d7uvO57iBVa9xyEeI7BmdwnWfCC7KKqm8wwxTYqaQChTeXvKQxPamdALP9s0ppOweEvERQxYxVAxy8bOTwA==";
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
        CFDv3 cfd = CFDv3Factory.load(new File("resources/xmls/cfdi/v32/CFDv32_before.xml"));
        ValidationErrorHandler handler = new ValidationErrorHandler();
        cfd.validar(handler);
        Assert.assertFalse(handler.getErrors().isEmpty());
    }

    @Test
    public void testValidateVerifyNominaFail() throws Exception {
        CFDv3 cfd = CFDv3Factory.load(new File("resources/xmls/cfdi/v32/CFDv32_nomina_before.xml"));
        ValidationErrorHandler handler = new ValidationErrorHandler();
        cfd.validar(handler);
        Assert.assertFalse(handler.getErrors().isEmpty());
    }

    @Test
    public void testValidateVerifyOk() throws Exception {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            CFDv3 cfd = CFDv3Factory.load(new File("resources/xmls/cfdi/v32/CFDv32_before.xml"));
            cfd.sellar(key, cert);
            cfd.guardar(baos, false);
            try (ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray())) {
                CFDv3 cfd3 = new CFDv32(bais);
                cfd3.validar(null);
                cfd3.verificar();
            }
        }
    }

    @Test
    public void testValidateVerifyNominaOk() throws Exception {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            CFDv3 cfd = CFDv3Factory.load(new File("resources/xmls/cfdi/v32/CFDv32_nomina_before.xml"));
            cfd.sellar(key, cert);
            cfd.guardar(baos, false);
            try (ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray())) {
                CFDv3 cfd2 = new CFDv32(bais);
                cfd2.validar(null);
                cfd2.verificar();
            }
        }
    }

    @Test
    public void testCleanLoad() throws Exception {
        Comprobante c = CFDv32.newComprobante(new FileInputStream("resources/xmls/cfdi/v32/CFDv32.xml"));
        CFDv32 cfd = new CFDv32(c);
        cfd.validar(null);
        cfd.verificar();
    }

    @Test
    public void testNominaLoad() throws Exception {
        Comprobante c = CFDv32.newComprobante(new FileInputStream("resources/xmls/cfdi/v32/CFDv32_nomina.xml"));
        CFDv32 cfd = new CFDv32(c);
        cfd.validar(null);
        cfd.verificar();
    }

    @Test
    public void testSellarComprobante() throws Exception {
        Comprobante c = CFDv32.newComprobante(new FileInputStream("resources/xmls/cfdi/v32/CFDv32_before.xml"));
        CFDv32 cfd = new CFDv32(c);
        Comprobante sellado = cfd.sellarComprobante(key, cert);
        Assert.assertNotNull(sellado.getSello());
        Assert.assertNotNull(sellado.getNoCertificado());
        Assert.assertNotNull(sellado.getCertificado());
        Comprobante c2 = CFDv32.newComprobante(new FileInputStream("resources/xmls/cfdi/v32/CFDv32.xml"));
        CFDv32 cfd2 = new CFDv32(c2);
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
    public void testSellarComprobanteNomina() throws Exception {
        Comprobante c = CFDv32.newComprobante(new FileInputStream("resources/xmls/cfdi/v32/CFDv32_nomina_before.xml"));
        CFDv32 cfd = new CFDv32(c);
        Comprobante sellado = cfd.sellarComprobante(key, cert);
        Assert.assertNotNull(sellado.getSello());
        Assert.assertNotNull(sellado.getNoCertificado());
        Assert.assertNotNull(sellado.getCertificado());
        Comprobante c2 = CFDv32.newComprobante(new FileInputStream("resources/xmls/cfdi/v32/CFDv32_nomina.xml"));
        CFDv32 cfd2 = new CFDv32(c2);
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
        Comprobante c = CFDv32.newComprobante(new FileInputStream("resources/xmls/cfdi/v32/CFDv32.xml"));
        c.setComplemento(ExampleCFDv32Factory.getInstance().createComplemento());
        TimbreFiscalDigital tfd = ExampleTimbreFiscal10Factory.getInstance().createTimbreFiscal();
        c.getComplemento().getAny().add(tfd);
        CFDv32 cfd = new CFDv32(c);
        String cfdi_tfd = new String(Files.readAllBytes(Paths.get("resources/xmls/cfdi/v32/CFDv32_tfd.xml")), StandardCharsets.UTF_8).replaceAll("(\\r\\n|\\r|\\n)", "");
        try (OutputStream outputStream = new ByteArrayOutputStream()) {
            cfd.validar(null);
            cfd.verificar();
            cfd.guardar(outputStream, false);
            Assert.assertEquals(cfdi_tfd, String.valueOf(outputStream));
        }
    }

}
