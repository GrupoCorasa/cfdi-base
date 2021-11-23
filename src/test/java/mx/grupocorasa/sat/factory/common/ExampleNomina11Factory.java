package mx.grupocorasa.sat.factory.common;

import mx.grupocorasa.sat.common.nomina11.Nomina;
import mx.grupocorasa.sat.common.nomina11.ObjectFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import java.math.BigDecimal;

public class ExampleNomina11Factory {

    private static ExampleNomina11Factory instance;

    private ObjectFactory of;
    private DatatypeFactory datatypeFactory;

    private ExampleNomina11Factory() {
        try {
            of = new ObjectFactory();
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static ExampleNomina11Factory getInstance() {
        if (instance == null) {
            instance = new ExampleNomina11Factory();
        }
        return instance;
    }

    public Nomina createNomina() {
        Nomina nomina = of.createNomina();
        nomina.setVersion("1.1");
        nomina.setRegistroPatronal("E6479648108");
        nomina.setNumEmpleado("001");
        nomina.setCURP("OEGH910609HNLRNR14");
        nomina.setTipoRegimen("02");
        nomina.setNumSeguridadSocial("24109129148");
        nomina.setFechaPago(datatypeFactory.newXMLGregorianCalendar(2017, 2, 15, 16, 0, 0, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED));
        nomina.setFechaInicialPago(datatypeFactory.newXMLGregorianCalendar(2017, 2, 1, 8, 0, 0, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED));
        nomina.setFechaFinalPago(datatypeFactory.newXMLGregorianCalendar(2017, 2, 15, 16, 0, 0, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED));
        nomina.setNumDiasPagados(new BigDecimal("15"));
        nomina.setDepartamento("Pruebas");
        nomina.setCLABE("002760700569076003");
        nomina.setBanco("002");
        nomina.setFechaInicioRelLaboral(datatypeFactory.newXMLGregorianCalendar(2017, 1, 1, 8, 0, 0, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED));
        nomina.setAntiguedad(7);
        nomina.setPuesto("Developer");
        nomina.setTipoContrato("01");
        nomina.setTipoJornada("01");
        nomina.setPeriodicidadPago("04");
        nomina.setSalarioBaseCotApor(new BigDecimal("293.73"));
        nomina.setRiesgoPuesto(2);
        nomina.setSalarioDiarioIntegrado(new BigDecimal("293.73"));
        nomina.setPercepciones(createPercepciones());
        nomina.setDeducciones(createDeducciones());
        return nomina;
    }

    private Nomina.Percepciones createPercepciones() {
        Nomina.Percepciones percepciones = of.createNominaPercepciones();
        percepciones.setTotalGravado(new BigDecimal("4165.56"));
        percepciones.setTotalExento(new BigDecimal("160.21"));
        Nomina.Percepciones.Percepcion per1 = of.createNominaPercepcionesPercepcion();
        per1.setTipoPercepcion("001");
        per1.setClave("001");
        per1.setConcepto("Sueldo Quincenal de Prueba");
        per1.setImporteGravado(new BigDecimal("4005.35"));
        per1.setImporteExento(BigDecimal.ZERO);
        percepciones.getPercepcion().add(per1);
        Nomina.Percepciones.Percepcion per2 = of.createNominaPercepcionesPercepcion();
        per2.setTipoPercepcion("010");
        per2.setClave("006");
        per2.setConcepto("Premio por Asistencia");
        per2.setImporteGravado(new BigDecimal("160.21"));
        per2.setImporteExento(BigDecimal.ZERO);
        percepciones.getPercepcion().add(per2);
        Nomina.Percepciones.Percepcion per3 = of.createNominaPercepcionesPercepcion();
        per3.setTipoPercepcion("005");
        per3.setClave("007");
        per3.setConcepto("Fondo de Ahorro");
        per3.setImporteGravado(BigDecimal.ZERO);
        per3.setImporteExento(new BigDecimal("160.21"));
        percepciones.getPercepcion().add(per3);
        return percepciones;
    }

    private Nomina.Deducciones createDeducciones() {
        Nomina.Deducciones deducciones = of.createNominaDeducciones();
        deducciones.setTotalGravado(new BigDecimal("812.19"));
        deducciones.setTotalExento(BigDecimal.ZERO);
        Nomina.Deducciones.Deduccion ded1 = of.createNominaDeduccionesDeduccion();
        ded1.setTipoDeduccion("002");
        ded1.setClave("011");
        ded1.setConcepto("Retencion ISR");
        ded1.setImporteGravado(new BigDecimal("375.58"));
        ded1.setImporteExento(BigDecimal.ZERO);
        deducciones.getDeduccion().add(ded1);
        Nomina.Deducciones.Deduccion ded2 = of.createNominaDeduccionesDeduccion();
        ded2.setTipoDeduccion("001");
        ded2.setClave("012");
        ded2.setConcepto("Descuento IMSS");
        ded2.setImporteGravado(new BigDecimal("116.18"));
        ded2.setImporteExento(BigDecimal.ZERO);
        deducciones.getDeduccion().add(ded2);
        Nomina.Deducciones.Deduccion ded3 = of.createNominaDeduccionesDeduccion();
        ded3.setTipoDeduccion("004");
        ded3.setClave("016");
        ded3.setConcepto("Aportación Fondo de Ahorro");
        ded3.setImporteGravado(new BigDecimal("320.43"));
        ded3.setImporteExento(BigDecimal.ZERO);
        deducciones.getDeduccion().add(ded3);
        return deducciones;
    }

}
