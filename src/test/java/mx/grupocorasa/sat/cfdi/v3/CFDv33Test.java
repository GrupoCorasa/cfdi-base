package mx.grupocorasa.sat.cfdi.v3;

import mx.grupocorasa.sat.cfd._33.Comprobante;
import mx.grupocorasa.sat.common.Pagos10.Pagos;
import mx.grupocorasa.sat.common.TimbreFiscalDigital11.TimbreFiscalDigital;
import mx.grupocorasa.sat.common.ValidationErrorHandler;
import mx.grupocorasa.sat.common.nomina12.Nomina;
import mx.grupocorasa.sat.factory.cfd.ExampleCFDv33Factory;
import mx.grupocorasa.sat.factory.common.ExampleComplementoPagoFactory;
import mx.grupocorasa.sat.factory.common.ExampleNomina12Factory;
import mx.grupocorasa.sat.factory.common.ExampleTimbreFiscal11Factory;
import mx.grupocorasa.sat.security.KeyLoaderEnumeration;
import mx.grupocorasa.sat.security.factory.KeyLoaderFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public final class CFDv33Test {

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
        CFDv33 cfd = new CFDv33(ExampleCFDv33Factory.getInstance().createComprobante());
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            cfd.guardar(byteArrayOutputStream, false);
            String comprobante = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><cfdi:Comprobante Version=\"3.3\" Serie=\"FFF\" Folio=\"12345\" Fecha=\"2021-01-01T14:54:56\" FormaPago=\"12\" NoCertificado=\"30001000000400002463\" CondicionesDePago=\"Crédito a 20 días\" SubTotal=\"2200.00\" Descuento=\"200.00\" Moneda=\"MXN\" TipoCambio=\"1\" Total=\"2000\" TipoDeComprobante=\"I\" MetodoPago=\"PUE\" LugarExpedicion=\"83000\" Confirmacion=\"aB1cD\" xsi:schemaLocation=\"http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd\" xmlns:cfdi=\"http://www.sat.gob.mx/cfd/3\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><cfdi:CfdiRelacionados TipoRelacion=\"06\"><cfdi:CfdiRelacionado UUID=\"248C01A6-A08C-4821-BFAF-F1D04C0A0DC5\"/><cfdi:CfdiRelacionado UUID=\"4A3CC52B-4F7A-4FAF-9375-9905D535CB07\"/></cfdi:CfdiRelacionados><cfdi:Emisor Rfc=\"PPL961114GZ1\" Nombre=\"PHARMA PLUS SA DE CV\" RegimenFiscal=\"606\"/><cfdi:Receptor Rfc=\"PEPJ8001019Q8\" Nombre=\"JUAN PEREZ PEREZ\" ResidenciaFiscal=\"MEX\" NumRegIdTrib=\"ResidenteExtranjero1\" UsoCFDI=\"D08\"/><cfdi:Conceptos><cfdi:Concepto ClaveProdServ=\"10101501\" NoIdentificacion=\"PRU01\" Cantidad=\"1.0\" ClaveUnidad=\"C51\" Unidad=\"Servicio\" Descripcion=\"Concepto de Prueba 01\" ValorUnitario=\"1100.00\" Importe=\"1100.00\" Descuento=\"100.00\"><cfdi:Impuestos><cfdi:Traslados><cfdi:Traslado Base=\"1000.00\" Impuesto=\"002\" TipoFactor=\"Tasa\" TasaOCuota=\"0.160000\" Importe=\"160.00\"/></cfdi:Traslados><cfdi:Retenciones><cfdi:Retencion Base=\"1000.00\" Impuesto=\"002\" TipoFactor=\"Tasa\" TasaOCuota=\"0.160000\" Importe=\"160.00\"/></cfdi:Retenciones></cfdi:Impuestos><cfdi:InformacionAduanera NumeroPedimento=\"67  52  3924  8060097\"/><cfdi:CuentaPredial Numero=\"123456\"/></cfdi:Concepto><cfdi:Concepto ClaveProdServ=\"10101502\" NoIdentificacion=\"PRU02\" Cantidad=\"1.0\" ClaveUnidad=\"C51\" Unidad=\"Servicio\" Descripcion=\"Concepto de Prueba 02\" ValorUnitario=\"1100.00\" Importe=\"1100.00\" Descuento=\"100.00\"><cfdi:Impuestos><cfdi:Traslados><cfdi:Traslado Base=\"1000.00\" Impuesto=\"002\" TipoFactor=\"Tasa\" TasaOCuota=\"0.160000\" Importe=\"160.00\"/></cfdi:Traslados><cfdi:Retenciones><cfdi:Retencion Base=\"1000.00\" Impuesto=\"002\" TipoFactor=\"Tasa\" TasaOCuota=\"0.160000\" Importe=\"160.00\"/></cfdi:Retenciones></cfdi:Impuestos><cfdi:InformacionAduanera NumeroPedimento=\"67  52  3924  8060097\"/><cfdi:CuentaPredial Numero=\"123456\"/></cfdi:Concepto></cfdi:Conceptos><cfdi:Impuestos TotalImpuestosRetenidos=\"160.00\" TotalImpuestosTrasladados=\"160.00\"><cfdi:Retenciones><cfdi:Retencion Impuesto=\"002\" Importe=\"160.00\"/></cfdi:Retenciones><cfdi:Traslados><cfdi:Traslado Impuesto=\"002\" TipoFactor=\"Tasa\" TasaOCuota=\"0.16\" Importe=\"160.00\"/></cfdi:Traslados></cfdi:Impuestos></cfdi:Comprobante>";
            Assert.assertEquals(comprobante, byteArrayOutputStream.toString("UTF-8"));
        }
    }

    @Test
    public void testCleanCadenaOriginal() throws Exception {
        CFDv33 cfd = new CFDv33(ExampleCFDv33Factory.getInstance().createComprobante());
        String cadena = "||3.3|FFF|12345|2021-01-01T14:54:56|12|30001000000400002463|Crédito a 20 días|2200.00|200.00|MXN|1|2000|I|PUE|83000|aB1cD|06|248C01A6-A08C-4821-BFAF-F1D04C0A0DC5|4A3CC52B-4F7A-4FAF-9375-9905D535CB07|PPL961114GZ1|PHARMA PLUS SA DE CV|606|PEPJ8001019Q8|JUAN PEREZ PEREZ|MEX|ResidenteExtranjero1|D08|10101501|PRU01|1.0|C51|Servicio|Concepto de Prueba 01|1100.00|1100.00|100.00|1000.00|002|Tasa|0.160000|160.00|1000.00|002|Tasa|0.160000|160.00|67 52 3924 8060097|123456|10101502|PRU02|1.0|C51|Servicio|Concepto de Prueba 02|1100.00|1100.00|100.00|1000.00|002|Tasa|0.160000|160.00|1000.00|002|Tasa|0.160000|160.00|67 52 3924 8060097|123456|002|160.00|160.00|002|Tasa|0.16|160.00|160.00||";
        Assert.assertEquals(cadena, cfd.getCadenaOriginal());
    }

    @Test
    public void testCleanSign() throws Exception {
        CFDv33 cfd = new CFDv33(ExampleCFDv33Factory.getInstance().createComprobante());
        Comprobante sellado = cfd.sellarComprobante(key, cert);
        String signature = "kaSjqmIGfHhQs53MUxXygPzUXaACNpE2/184m3rOkfwTgizvL301OiI2SqYZL2MhMSI8janfX5zLq2OpRE5iPtvUHMKGWRowECYk446smqssNUy0IF+l/0qr783DkQUw7IIhh/sDgyvbmmEgt89b5QhMSTcPaIDkNppU6t1yb4jq6f3hYly6QL3b3B25KEHaf5sCYcgU1FyCxQKdiNusn0VQp2pHQ8xfF1iSmcqQfAW29NHuEn4UkNOvx5uYlijDG6BWvYo9guzQqVEEU5aKCSbjiPSTr6fSVvSWAd7AlznvPwNaC5kyrJNOmIrarWK0dH8PVQcUuxnzUCxLJDqkbQ==";
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
        try (FileInputStream fis = new FileInputStream("resources/xmls/cfdi/v33/CFDv33.xml")) {
            Comprobante c = CFDv33.newComprobante(fis);
            CFDv33 cfd = new CFDv33(c);
            cfd.validar(null);
            cfd.verificar();
        }
    }

    @Test
    public void testSellarComprobante() throws Exception {
        try (FileInputStream fis_before = new FileInputStream("resources/xmls/cfdi/v33/CFDv33_before.xml");
             FileInputStream fis_sellado = new FileInputStream("resources/xmls/cfdi/v33/CFDv33.xml")) {
            Comprobante c_before = CFDv33.newComprobante(fis_before);
            CFDv33 cfd_before = new CFDv33(c_before);
            Comprobante sellado_before = cfd_before.sellarComprobante(key, cert);
            Assert.assertNotNull(sellado_before.getSello());
            Assert.assertNotNull(sellado_before.getNoCertificado());
            Assert.assertNotNull(sellado_before.getCertificado());
            Comprobante c_sellado = CFDv33.newComprobante(fis_sellado);
            c_sellado.getComplemento().clear();
            CFDv33 cfd_sellado = new CFDv33(c_sellado);
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
        try (FileInputStream fis_before = new FileInputStream("resources/xmls/cfdi/v33/CFDv33.xml")) {
            Comprobante c_before = CFDv33.newComprobante(fis_before);
            Comprobante.Complemento complemento = ExampleCFDv33Factory.getInstance().createComplemento();
            TimbreFiscalDigital tfd = ExampleTimbreFiscal11Factory.getInstance().createTimbreFiscal();
            complemento.getAny().add(tfd);
            c_before.getComplemento().add(complemento);
            CFDv33 cfd_before = new CFDv33(c_before);
            try (OutputStream os_before = new ByteArrayOutputStream(); OutputStream os_tfd = new ByteArrayOutputStream(); FileInputStream fis_tfd = new FileInputStream("resources/xmls/cfdi/v33/CFDv33_tfd.xml")) {
                cfd_before.validar(null);
                cfd_before.verificar();
                cfd_before.guardar(os_before, false);

                Comprobante comprobante2 = CFDv33.newComprobante(fis_tfd);
                CFDv33 cfd_tfd = new CFDv33(comprobante2);
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
            CFDv3 cfd = CFDv3Factory.load(new File("resources/xmls/cfdi/v33/CFDv33_before.xml"));
            cfd.sellar(key, cert);
            cfd.guardar(baos, false);
            try (ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray())) {
                CFDv33 cfd3 = new CFDv33(bais);
                cfd3.validar(null);
                cfd3.verificar();
            }
        }
    }

    @Test
    public void testValidateVerifyFail() throws Exception {
        CFDv3 cfd = CFDv3Factory.load(new File("resources/xmls/cfdi/v33/CFDv33_before.xml"));
        ValidationErrorHandler handler = new ValidationErrorHandler();
        cfd.validar(handler);
        Assert.assertFalse(handler.getErrors().isEmpty());
    }

    @Test
    public void testCreateNominaComprobante() throws Exception {
        Nomina nomina = ExampleNomina12Factory.getInstance().createNomina();
        Comprobante comprobante = ExampleCFDv33Factory.getInstance().createComprobanteNomina(nomina);
        Comprobante.Complemento complemento = ExampleCFDv33Factory.getInstance().createComplemento();
        complemento.getAny().add(nomina);
        comprobante.getComplemento().add(complemento);
        CFDv33 cfd = new CFDv33(comprobante);
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            cfd.guardar(byteArrayOutputStream, false);
            String comp = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><cfdi:Comprobante Version=\"3.3\" Serie=\"NNN\" Folio=\"54321\" Fecha=\"2021-01-01T14:54:56\" FormaPago=\"99\" SubTotal=\"4375.77\" Descuento=\"812.19\" Moneda=\"MXN\" Total=\"3563.58\" TipoDeComprobante=\"N\" MetodoPago=\"PUE\" LugarExpedicion=\"83000\" Confirmacion=\"ECVH1\" xsi:schemaLocation=\"http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd http://www.sat.gob.mx/nomina12 http://www.sat.gob.mx/sitio_internet/cfd/nomina/nomina12.xsd\" xmlns:nomina12=\"http://www.sat.gob.mx/nomina12\" xmlns:cfdi=\"http://www.sat.gob.mx/cfd/3\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><cfdi:CfdiRelacionados TipoRelacion=\"06\"><cfdi:CfdiRelacionado UUID=\"248C01A6-A08C-4821-BFAF-F1D04C0A0DC5\"/><cfdi:CfdiRelacionado UUID=\"4A3CC52B-4F7A-4FAF-9375-9905D535CB07\"/></cfdi:CfdiRelacionados><cfdi:Emisor Rfc=\"PPL961114GZ1\" Nombre=\"PHARMA PLUS SA DE CV\" RegimenFiscal=\"606\"/><cfdi:Receptor Rfc=\"OEGH910609BZ6\" Nombre=\"Heriberto Ortega Gonzalez\" UsoCFDI=\"P01\"/><cfdi:Conceptos><cfdi:Concepto ClaveProdServ=\"84111505\" Cantidad=\"1\" ClaveUnidad=\"ACT\" Descripcion=\"Pago de nómina\" ValorUnitario=\"4375.77\" Importe=\"4375.77\" Descuento=\"812.19\"/></cfdi:Conceptos><cfdi:Complemento><nomina12:Nomina Version=\"1.2\" TipoNomina=\"O\" FechaPago=\"2020-02-15\" FechaInicialPago=\"2020-02-01\" FechaFinalPago=\"2020-02-15\" NumDiasPagados=\"15\" TotalPercepciones=\"4375.77\" TotalDeducciones=\"812.19\" TotalOtrosPagos=\"0\"><nomina12:Emisor Curp=\"XEXX010101HNEXXXA4\" RegistroPatronal=\"E6479648108\" RfcPatronOrigen=\"XAXX010101000\"><nomina12:EntidadSNCF OrigenRecurso=\"IF\" MontoRecursoPropio=\"10\"/></nomina12:Emisor><nomina12:Receptor Curp=\"OEGH910609HNLRNR14\" NumSeguridadSocial=\"24109129148\" FechaInicioRelLaboral=\"2017-01-01\" Antigüedad=\"P11Y7M14D\" TipoContrato=\"01\" Sindicalizado=\"No\" TipoJornada=\"01\" TipoRegimen=\"02\" NumEmpleado=\"001\" Departamento=\"Sistemas\" Puesto=\"Programador\" RiesgoPuesto=\"2\" PeriodicidadPago=\"04\" Banco=\"002\" CuentaBancaria=\"002760700569076003\" SalarioBaseCotApor=\"293.73\" SalarioDiarioIntegrado=\"293.73\" ClaveEntFed=\"SON\"><nomina12:SubContratacion RfcLabora=\"XAXX010101000\" PorcentajeTiempo=\"30\"/></nomina12:Receptor><nomina12:Percepciones TotalSueldos=\"4375.77\" TotalGravado=\"4215.56\" TotalExento=\"160.21\"><nomina12:Percepcion TipoPercepcion=\"001\" Clave=\"001\" Concepto=\"Sueldo Quincenal de Prueba\" ImporteGravado=\"4005.35\" ImporteExento=\"0\"/><nomina12:Percepcion TipoPercepcion=\"010\" Clave=\"006\" Concepto=\"Premio por Asistencia\" ImporteGravado=\"160.21\" ImporteExento=\"0\"/><nomina12:Percepcion TipoPercepcion=\"005\" Clave=\"007\" Concepto=\"Fondo de Ahorro\" ImporteGravado=\"0\" ImporteExento=\"160.21\"/><nomina12:Percepcion TipoPercepcion=\"053\" Clave=\"054\" Concepto=\"Prueba de nodos hijos\" ImporteGravado=\"50.00\" ImporteExento=\"0\"><nomina12:AccionesOTitulos ValorMercado=\"1\" PrecioAlOtorgarse=\"0.99\"/><nomina12:HorasExtra Dias=\"1\" TipoHoras=\"02\" HorasExtra=\"4\" ImportePagado=\"0\"/></nomina12:Percepcion><nomina12:JubilacionPensionRetiro TotalUnaExhibicion=\"0.01\" TotalParcialidad=\"0.02\" MontoDiario=\"0.03\" IngresoAcumulable=\"0.04\" IngresoNoAcumulable=\"0.05\"/><nomina12:SeparacionIndemnizacion TotalPagado=\"0.01\" NumAñosServicio=\"4\" UltimoSueldoMensOrd=\"0.02\" IngresoAcumulable=\"0.03\" IngresoNoAcumulable=\"0.04\"/></nomina12:Percepciones><nomina12:Deducciones TotalOtrasDeducciones=\"436.61\" TotalImpuestosRetenidos=\"375.58\"><nomina12:Deduccion TipoDeduccion=\"002\" Clave=\"011\" Concepto=\"Retencion ISR\" Importe=\"375.58\"/><nomina12:Deduccion TipoDeduccion=\"001\" Clave=\"012\" Concepto=\"Descuento IMSS\" Importe=\"116.18\"/><nomina12:Deduccion TipoDeduccion=\"004\" Clave=\"016\" Concepto=\"Aportación Fondo de Ahorro\" Importe=\"320.43\"/></nomina12:Deducciones><nomina12:OtrosPagos><nomina12:OtroPago TipoOtroPago=\"002\" Clave=\"002\" Concepto=\"Subsidio para el empleo\" Importe=\"0\"><nomina12:SubsidioAlEmpleo SubsidioCausado=\"1488.92\"/></nomina12:OtroPago><nomina12:OtroPago TipoOtroPago=\"999\" Clave=\"999\" Concepto=\"Prueba de nodos hijos\" Importe=\"0\"><nomina12:CompensacionSaldosAFavor SaldoAFavor=\"0.01\" Año=\"2020\" RemanenteSalFav=\"0\"/></nomina12:OtroPago></nomina12:OtrosPagos><nomina12:Incapacidades><nomina12:Incapacidad DiasIncapacidad=\"11\" TipoIncapacidad=\"04\" ImporteMonetario=\"0.01\"/><nomina12:Incapacidad DiasIncapacidad=\"20\" TipoIncapacidad=\"01\" ImporteMonetario=\"0.02\"/></nomina12:Incapacidades></nomina12:Nomina></cfdi:Complemento></cfdi:Comprobante>";
            Assert.assertEquals(comp, byteArrayOutputStream.toString("UTF-8"));
        }
    }

    @Test
    public void testNominaCadenaOriginal() throws Exception {
        Nomina nomina = ExampleNomina12Factory.getInstance().createNomina();
        Comprobante comprobante = ExampleCFDv33Factory.getInstance().createComprobanteNomina(nomina);
        Comprobante.Complemento complemento = ExampleCFDv33Factory.getInstance().createComplemento();
        complemento.getAny().add(nomina);
        comprobante.getComplemento().add(complemento);
        CFDv33 cfd = new CFDv33(comprobante);
        String cadena = "||3.3|NNN|54321|2021-01-01T14:54:56|99||4375.77|812.19|MXN|3563.58|N|PUE|83000|ECVH1|06|248C01A6-A08C-4821-BFAF-F1D04C0A0DC5|4A3CC52B-4F7A-4FAF-9375-9905D535CB07|PPL961114GZ1|PHARMA PLUS SA DE CV|606|OEGH910609BZ6|Heriberto Ortega Gonzalez|P01|84111505|1|ACT|Pago de nómina|4375.77|4375.77|812.19|1.2|O|2020-02-15|2020-02-01|2020-02-15|15|4375.77|812.19|0|XEXX010101HNEXXXA4|E6479648108|XAXX010101000|IF|10|OEGH910609HNLRNR14|24109129148|2017-01-01|P11Y7M14D|01|No|01|02|001|Sistemas|Programador|2|04|002|002760700569076003|293.73|293.73|SON|XAXX010101000|30|4375.77|4215.56|160.21|001|001|Sueldo Quincenal de Prueba|4005.35|0|010|006|Premio por Asistencia|160.21|0|005|007|Fondo de Ahorro|0|160.21|053|054|Prueba de nodos hijos|50.00|0|1|0.99|1|02|4|0|0.01|0.02|0.03|0.04|0.05|0.01|4|0.02|0.03|0.04|436.61|375.58|002|011|Retencion ISR|375.58|001|012|Descuento IMSS|116.18|004|016|Aportación Fondo de Ahorro|320.43|002|002|Subsidio para el empleo|0|1488.92|999|999|Prueba de nodos hijos|0|0.01|2020|0|11|04|0.01|20|01|0.02||";
        Assert.assertEquals(cadena, cfd.getCadenaOriginal());
    }

    @Test
    public void testNominaSign() throws Exception {
        Nomina nomina = ExampleNomina12Factory.getInstance().createNomina();
        Comprobante comprobante = ExampleCFDv33Factory.getInstance().createComprobanteNomina(nomina);
        Comprobante.Complemento complemento = ExampleCFDv33Factory.getInstance().createComplemento();
        complemento.getAny().add(nomina);
        comprobante.getComplemento().add(complemento);
        CFDv33 cfd = new CFDv33(comprobante);
        Comprobante sellado = cfd.sellarComprobante(key, cert);
        String signature = "haNyxEY6iqK8RG8B0QlVH+Fvq+Y1AaoeCZWyoNP2CFloMIFG7NKcPpjHcQSc9m9DzjSoCvprilBshpCeuvjsUgRDx8mB/9QW0jHzZUA+mZTrXJAlQjygvpKeLh5/Liggyoz3onmhifjxFobq/5yG1XcIJBtErBFSRiJ/oNcaGiHUW91m1D5YCp8W3h1lIZpkWf5Y3MLtY8gax9ah6rDjSNGAUZCUgCzpH+EJ7WeEm9lZz13jUYy7VNRNNiM1g2BDIscbqYcZjGhNSRodAqTM+1BAcN9exaE6hQAyXonrMM8GUVnKocIXOVexBmT9YRw5+Hqtonz0Zb0wqvvNEjAfqw==";
        Assert.assertEquals(signature, sellado.getSello());
        String certificate = "MIIFwTCCA6mgAwIBAgIUMzAwMDEwMDAwMDA0MDAwMDI0NjMwDQYJKoZIhvcNAQELBQAwggErMQ8wDQYDVQQDDAZBQyBVQVQxLjAsBgNVBAoMJVNFUlZJQ0lPIERFIEFETUlOSVNUUkFDSU9OIFRSSUJVVEFSSUExGjAYBgNVBAsMEVNBVC1JRVMgQXV0aG9yaXR5MSgwJgYJKoZIhvcNAQkBFhlvc2Nhci5tYXJ0aW5lekBzYXQuZ29iLm14MR0wGwYDVQQJDBQzcmEgY2VycmFkYSBkZSBjYWRpejEOMAwGA1UEEQwFMDYzNzAxCzAJBgNVBAYTAk1YMRkwFwYDVQQIDBBDSVVEQUQgREUgTUVYSUNPMREwDwYDVQQHDAhDT1lPQUNBTjERMA8GA1UELRMIMi41LjQuNDUxJTAjBgkqhkiG9w0BCQITFnJlc3BvbnNhYmxlOiBBQ0RNQS1TQVQwHhcNMTkwNjIwMjA1NTA4WhcNMjMwNjIwMjA1NTA4WjCB6DEmMCQGA1UEAxQdSEVSUkVSSUEgJiBFTEVDVFJJQ09TIFMgREUgQ1YxJjAkBgNVBCkUHUhFUlJFUklBICYgRUxFQ1RSSUNPUyBTIERFIENWMSYwJAYDVQQKFB1IRVJSRVJJQSAmIEVMRUNUUklDT1MgUyBERSBDVjElMCMGA1UELRQcSCZFOTUxMTI4NDY5IC8gWE9KSTc0MDkxOVU0ODEeMBwGA1UEBRMVIC8gWE9KSTc0MDkxOU1RVERNTjAyMScwJQYDVQQLFB5IRVJSRVJJQSAmIEVMRUNUUklDT1MgU0EgREUgQ1YwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDoNWRICH7Ja/IR0sFzGAoveKzFT94BJ4Wlh7MsePe50azvre4j6CYEItQ5jCkxZyYHT23dfONpUWDN7+Bx7j9QjKPwqr+zJjFrla/hD4fj63LeUmofVR7TjeDLLEwCSsn1k3vLae4WBgsV1jNc3M87ZYOHpe0XXLVw/25nSyqQS5l4HTJrw7wQ3NZAXghG38NIOUYJZsE/6K3OAslVoF/YregQ0UqodFLyA6BBMcfll8rjMIWtCy2DxZ0T5QCPX3OccWzB9hHvP5PAjUiOf+FpKHW8WPGEywoC1vM3nvsHXTp2MkUn78U5PK/hmc/7chqaneZHA066AIRs3zGHkHTjAgMBAAGjHTAbMAwGA1UdEwEB/wQCMAAwCwYDVR0PBAQDAgbAMA0GCSqGSIb3DQEBCwUAA4ICAQCsaJpin0RAOsyxBMQRpfTM47k+a1QlHHsP2vEubuINZE+3Hp4b/C1w1gGe8+l8EJcLxQfQ0ovlvKDdYFJLleBCkt9eoFY92keaL77Teq6YxzQ8jArI1WSz75KkljzHYbtm4Oy+hsmpla4L51VnhRfyqehfAgZEDRHytpOPVzxIQnnDx/GI2KVlUBvInOS50dBoCnq5sxzQ+Ha1nsPMvrdTaHBRjto4kOL9SkHvZDVZHWnJ1R7w+1n0zXmDV27BUNsHQNaDG8e+5uGex7d/Bfolf3dU2y4YVFN9DOP8ge0J7ryIuRB7eESwT3aEM/0gec41vMOfVmR2mzNeX0q8czU0xjMH+NKSdgkNFAvoMk2uIsGePkbQDdk3XW1h6f3HGRQ85mkPh/Zt4kqbc0CzFsQMGdj8eixm8jRazSoCdndSDXrDwSTkVaqTuYqRxy89NWNk4dbXXqw4a52DKR1oYR0r9AVjHTfK936tRvNhxQ4KHGjPUi0ttxoCw1fogKwf2MRkTZMO0EZrEnODKl3CMoi4IXuWpNYY6MhUCjLRWJF/mOclkpCzNpyNbwJiGTshTEDvfqS1PsIuXgjIzbCa3seRzP6yxsd0CfEI7lUxMvYT6r0wqRXsymO6mOywWQKRFnrZmzD1zSMRap7vhED+plAxUgouQFbfv1NDkqAfF57FFA==";
        Assert.assertEquals(certificate, sellado.getCertificado());
        String certificateNum = "30001000000400002463";
        Assert.assertEquals(certificateNum, sellado.getNoCertificado());
        cfd.validar(null);
        cfd.verificar();
    }

    @Test
    public void testNominaLoad() throws Exception {
        Comprobante c = CFDv33.newComprobante(new FileInputStream("resources/xmls/cfdi/v33/CFDv33_nomina.xml"));
        CFDv33 cfd = new CFDv33(c);
        cfd.validar(null);
        cfd.verificar();
    }

    @Test
    public void testSellarComprobanteNomina() throws Exception {
        Comprobante c = CFDv33.newComprobante(new FileInputStream("resources/xmls/cfdi/v33/CFDv33_nomina_before.xml"));
        CFDv33 cfd = new CFDv33(c);
        Comprobante sellado = cfd.sellarComprobante(key, cert);
        Assert.assertNotNull(sellado.getSello());
        Assert.assertNotNull(sellado.getNoCertificado());
        Assert.assertNotNull(sellado.getCertificado());
        Comprobante c2 = CFDv33.newComprobante(new FileInputStream("resources/xmls/cfdi/v33/CFDv33_nomina.xml"));
        CFDv33 cfd2 = new CFDv33(c2);
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
    public void testValidateVerifyNominaOk() throws Exception {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            CFDv3 cfd = CFDv3Factory.load(new File("resources/xmls/cfdi/v33/CFDv33_nomina_before.xml"));
            cfd.sellar(key, cert);
            cfd.guardar(baos, false);
            try (ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray())) {
                CFDv33 cfd2 = new CFDv33(bais);
                cfd2.validar(null);
                cfd2.verificar();
            }
        }
    }

    @Test
    public void testValidateVerifyNominaFail() throws Exception {
        CFDv3 cfd = CFDv3Factory.load(new File("resources/xmls/cfdi/v33/CFDv33_nomina_before.xml"));
        ValidationErrorHandler handler = new ValidationErrorHandler();
        cfd.validar(handler);
        Assert.assertFalse(handler.getErrors().isEmpty());
    }

    @Test
    public void testCreateCPagoComprobante() throws Exception {
        Pagos c_pago = ExampleComplementoPagoFactory.getInstance().createComplementoPago();
        Comprobante comprobante = ExampleCFDv33Factory.getInstance().createComplementoPago();
        Comprobante.Complemento complemento = ExampleCFDv33Factory.getInstance().createComplemento();
        complemento.getAny().add(c_pago);
        comprobante.getComplemento().add(complemento);
        CFDv33 cfd = new CFDv33(comprobante);
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            cfd.guardar(byteArrayOutputStream, false);
            String comp = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><cfdi:Comprobante Version=\"3.3\" Serie=\"PPP\" Folio=\"34152\" Fecha=\"2021-03-11T14:54:56\" SubTotal=\"0\" Moneda=\"XXX\" Total=\"0\" TipoDeComprobante=\"P\" LugarExpedicion=\"83000\" Confirmacion=\"ECVH1\" xsi:schemaLocation=\"http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd http://www.sat.gob.mx/Pagos http://www.sat.gob.mx/sitio_internet/cfd/Pagos/Pagos10.xsd\" xmlns:cfdi=\"http://www.sat.gob.mx/cfd/3\" xmlns:pago10=\"http://www.sat.gob.mx/Pagos\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><cfdi:CfdiRelacionados TipoRelacion=\"06\"><cfdi:CfdiRelacionado UUID=\"248C01A6-A08C-4821-BFAF-F1D04C0A0DC5\"/><cfdi:CfdiRelacionado UUID=\"4A3CC52B-4F7A-4FAF-9375-9905D535CB07\"/></cfdi:CfdiRelacionados><cfdi:Emisor Rfc=\"PPL961114GZ1\" Nombre=\"PHARMA PLUS SA DE CV\" RegimenFiscal=\"606\"/><cfdi:Receptor Rfc=\"OEGH910609BZ6\" Nombre=\"Heriberto Ortega Gonzalez\" UsoCFDI=\"P01\"/><cfdi:Conceptos><cfdi:Concepto ClaveProdServ=\"84111506\" Cantidad=\"1\" ClaveUnidad=\"ACT\" Descripcion=\"Pago\" ValorUnitario=\"0\" Importe=\"0\"/></cfdi:Conceptos><cfdi:Complemento><pago10:Pagos Version=\"1.0\"><pago10:Pago FechaPago=\"2020-01-01T13:42:56\" FormaDePagoP=\"04\" MonedaP=\"MXN\" TipoCambioP=\"1\" Monto=\"3000\" NumOperacion=\"ABC123\" RfcEmisorCtaOrd=\"ABC010101000\" NomBancoOrdExt=\"Razón Social Banco Ordenante\" CtaOrdenante=\"123456789012345678\" RfcEmisorCtaBen=\"CBA010101000\" CtaBeneficiario=\"876543210987654321\" TipoCadPago=\"01\"><pago10:DoctoRelacionado IdDocumento=\"8c6edff3-f9d0-4187-8574-4e199bac8730\" Serie=\"AAA\" Folio=\"987\" MonedaDR=\"MXN\" TipoCambioDR=\"1\" MetodoDePagoDR=\"PPD\" NumParcialidad=\"1\" ImpSaldoAnt=\"1500\" ImpPagado=\"1500\" ImpSaldoInsoluto=\"0\"/><pago10:DoctoRelacionado IdDocumento=\"153e2c1a-9901-4fc2-aa92-54bf6add80c4\" Serie=\"AAA\" Folio=\"988\" MonedaDR=\"MXN\" TipoCambioDR=\"1\" MetodoDePagoDR=\"PPD\" NumParcialidad=\"1\" ImpSaldoAnt=\"1000\" ImpPagado=\"1000\" ImpSaldoInsoluto=\"0\"/><pago10:DoctoRelacionado IdDocumento=\"47bdcbab-97be-4195-a674-043e33970121\" Serie=\"AAA\" Folio=\"989\" MonedaDR=\"MXN\" TipoCambioDR=\"1\" MetodoDePagoDR=\"PPD\" NumParcialidad=\"1\" ImpSaldoAnt=\"500\" ImpPagado=\"500\" ImpSaldoInsoluto=\"0\"/><pago10:Impuestos TotalImpuestosRetenidos=\"123\" TotalImpuestosTrasladados=\"123\"><pago10:Retenciones><pago10:Retencion Impuesto=\"002\" Importe=\"123\"/></pago10:Retenciones><pago10:Traslados><pago10:Traslado Impuesto=\"002\" TipoFactor=\"Tasa\" TasaOCuota=\"0.16\" Importe=\"123\"/></pago10:Traslados></pago10:Impuestos></pago10:Pago><pago10:Pago FechaPago=\"2020-02-01T13:42:56\" FormaDePagoP=\"03\" MonedaP=\"MXN\" TipoCambioP=\"1\" Monto=\"2000\" NumOperacion=\"ABC321\" RfcEmisorCtaOrd=\"AAA010101000\" NomBancoOrdExt=\"Razón Social Banco Ordenante\" CtaOrdenante=\"123456789012345679\" RfcEmisorCtaBen=\"BBB010101000\" CtaBeneficiario=\"976543210987654321\" TipoCadPago=\"01\"><pago10:DoctoRelacionado IdDocumento=\"0121a674-97be-4195-cbab-043e339747bd\" Serie=\"BBB\" Folio=\"987\" MonedaDR=\"MXN\" TipoCambioDR=\"1\" MetodoDePagoDR=\"PPD\" NumParcialidad=\"1\" ImpSaldoAnt=\"1500\" ImpPagado=\"1500\" ImpSaldoInsoluto=\"0\"/><pago10:DoctoRelacionado IdDocumento=\"80c42c1a-9901-4fc2-aa92-54bf6add153e\" Serie=\"BBB\" Folio=\"989\" MonedaDR=\"MXN\" TipoCambioDR=\"1\" MetodoDePagoDR=\"PPD\" NumParcialidad=\"1\" ImpSaldoAnt=\"500\" ImpPagado=\"500\" ImpSaldoInsoluto=\"0\"/></pago10:Pago></pago10:Pagos></cfdi:Complemento></cfdi:Comprobante>";
            Assert.assertEquals(comp, byteArrayOutputStream.toString("UTF-8"));
        }
    }

    @Test
    public void testCPagoCadenaOriginal() throws Exception {
        Pagos c_pago = ExampleComplementoPagoFactory.getInstance().createComplementoPago();
        Comprobante comprobante = ExampleCFDv33Factory.getInstance().createComplementoPago();
        Comprobante.Complemento complemento = ExampleCFDv33Factory.getInstance().createComplemento();
        complemento.getAny().add(c_pago);
        comprobante.getComplemento().add(complemento);
        CFDv33 cfd = new CFDv33(comprobante);
        String cadena = "||3.3|PPP|34152|2021-03-11T14:54:56||0|XXX|0|P|83000|ECVH1|06|248C01A6-A08C-4821-BFAF-F1D04C0A0DC5|4A3CC52B-4F7A-4FAF-9375-9905D535CB07|PPL961114GZ1|PHARMA PLUS SA DE CV|606|OEGH910609BZ6|Heriberto Ortega Gonzalez|P01|84111506|1|ACT|Pago|0|0|1.0|2020-01-01T13:42:56|04|MXN|1|3000|ABC123|ABC010101000|Razón Social Banco Ordenante|123456789012345678|CBA010101000|876543210987654321|01|8c6edff3-f9d0-4187-8574-4e199bac8730|AAA|987|MXN|1|PPD|1|1500|1500|0|153e2c1a-9901-4fc2-aa92-54bf6add80c4|AAA|988|MXN|1|PPD|1|1000|1000|0|47bdcbab-97be-4195-a674-043e33970121|AAA|989|MXN|1|PPD|1|500|500|0|123|123|002|123|002|Tasa|0.16|123|2020-02-01T13:42:56|03|MXN|1|2000|ABC321|AAA010101000|Razón Social Banco Ordenante|123456789012345679|BBB010101000|976543210987654321|01|0121a674-97be-4195-cbab-043e339747bd|BBB|987|MXN|1|PPD|1|1500|1500|0|80c42c1a-9901-4fc2-aa92-54bf6add153e|BBB|989|MXN|1|PPD|1|500|500|0||";
        Assert.assertEquals(cadena, cfd.getCadenaOriginal());
    }

    @Test
    public void testCPagoSign() throws Exception {
        Pagos c_pago = ExampleComplementoPagoFactory.getInstance().createComplementoPago();
        Comprobante comprobante = ExampleCFDv33Factory.getInstance().createComplementoPago();
        Comprobante.Complemento complemento = ExampleCFDv33Factory.getInstance().createComplemento();
        complemento.getAny().add(c_pago);
        comprobante.getComplemento().add(complemento);
        CFDv33 cfd = new CFDv33(comprobante);
        Comprobante sellado = cfd.sellarComprobante(key, cert);
        String signature = "pCFD1vqDAHq+fowhMLuYZ+5s9+fGnab22mGkdamLCVyez6FOqP3cj+By8OmZIPypwsdJaSkQM3pG46rUtAzHVbS6sXKwsFyrE5JZSmXVTjZrwJG4hO3471H4vGJ8EvHH0yWk5btxIM3DX+aeBZtyJovvHEzn/MeW2TdJe0luQmBRKbM3WOGNoat4VyYIGGLUrVLo6YSIM85HWIcgOS9grF0Y6TIoAXDzSL2U8BrexvwKCQmS9oXGObQ/n4M4DoYBcSa0yttqmOY9DFbLg3lBYkWiU5sQfbZqmMZkRMFCx1kLgRBlEmAUxlzrrZZRsDoQ+rSDf7G/JcvGoMk+q7R3lA==";
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
        Comprobante c = CFDv33.newComprobante(new FileInputStream("resources/xmls/cfdi/v33/CFDv33_cpago.xml"));
        CFDv33 cfd = new CFDv33(c);
        cfd.validar(null);
        cfd.verificar();
    }

    @Test
    public void testSellarComprobanteCPago() throws Exception {
        Comprobante c = CFDv33.newComprobante(new FileInputStream("resources/xmls/cfdi/v33/CFDv33_cpago_before.xml"));
        CFDv33 cfd = new CFDv33(c);
        Comprobante sellado = cfd.sellarComprobante(key, cert);
        Assert.assertNotNull(sellado.getSello());
        Assert.assertNotNull(sellado.getNoCertificado());
        Assert.assertNotNull(sellado.getCertificado());
        Comprobante c2 = CFDv33.newComprobante(new FileInputStream("resources/xmls/cfdi/v33/CFDv33_cpago.xml"));
        CFDv33 cfd2 = new CFDv33(c2);
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
            CFDv3 cfd = CFDv3Factory.load(new File("resources/xmls/cfdi/v33/CFDv33_cpago_before.xml"));
            cfd.sellar(key, cert);
            cfd.guardar(baos, false);
            try (ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray())) {
                CFDv33 cfd2 = new CFDv33(bais);
                cfd2.validar(null);
                cfd2.verificar();
            }
        }
    }

    @Test
    public void testValidateVerifyCPagoFail() throws Exception {
        CFDv3 cfd = CFDv3Factory.load(new File("resources/xmls/cfdi/v33/CFDv33_cpago_before.xml"));
        ValidationErrorHandler handler = new ValidationErrorHandler();
        cfd.validar(handler);
        Assert.assertFalse(handler.getErrors().isEmpty());
    }

}