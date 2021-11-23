package mx.grupocorasa.sat.factory.common;

import mx.grupocorasa.sat.common.nomina12.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public class ExampleNomina12Factory {

    private static ExampleNomina12Factory instance;

    private ObjectFactory of;
    private DatatypeFactory datatypeFactory;

    private ExampleNomina12Factory() {
        try {
            of = new ObjectFactory();
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static ExampleNomina12Factory getInstance() {
        if (instance == null) {
            instance = new ExampleNomina12Factory();
        }
        return instance;
    }

    public Nomina createNomina() {
        Nomina nomina = of.createNomina();
        nomina.setVersion("1.2");
        nomina.setTipoNomina(CTipoNomina.O);
        nomina.setFechaPago(datatypeFactory.newXMLGregorianCalendar(2020, 2, 15, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED));
        nomina.setFechaInicialPago(datatypeFactory.newXMLGregorianCalendar(2020, 2, 1, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED));
        nomina.setFechaFinalPago(datatypeFactory.newXMLGregorianCalendar(2020, 2, 15, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED));
        nomina.setNumDiasPagados(new BigDecimal("15"));
        nomina.setPercepciones(createPercepciones());
        if (nomina.getPercepciones().getTotalSueldos() != null || nomina.getPercepciones().getTotalSeparacionIndemnizacion() != null || nomina.getPercepciones().getTotalJubilacionPensionRetiro() != null) {
            BigDecimal totalP = BigDecimal.ZERO;
            if (nomina.getPercepciones().getTotalSueldos() != null)
                totalP = totalP.add(nomina.getPercepciones().getTotalSueldos());
            if (nomina.getPercepciones().getTotalSeparacionIndemnizacion() != null)
                totalP = totalP.add(nomina.getPercepciones().getTotalSeparacionIndemnizacion());
            if (nomina.getPercepciones().getTotalJubilacionPensionRetiro() != null)
                totalP = totalP.add(nomina.getPercepciones().getTotalJubilacionPensionRetiro());
            nomina.setTotalPercepciones(totalP);
        }
        nomina.setDeducciones(createDeducciones());
        if (nomina.getDeducciones().getTotalOtrasDeducciones() != null || nomina.getDeducciones().getTotalImpuestosRetenidos() != null) {
            BigDecimal totalD = BigDecimal.ZERO;
            if (nomina.getDeducciones().getTotalOtrasDeducciones() != null)
                totalD = totalD.add(nomina.getDeducciones().getTotalOtrasDeducciones());
            if (nomina.getDeducciones().getTotalImpuestosRetenidos() != null)
                totalD = totalD.add(nomina.getDeducciones().getTotalImpuestosRetenidos());
            nomina.setTotalDeducciones(totalD);
        }
        nomina.setOtrosPagos(createOtrosPagos());
        if (nomina.getOtrosPagos() != null && nomina.getOtrosPagos().getOtroPago() != null && !nomina.getOtrosPagos().getOtroPago().isEmpty()) {
            nomina.setTotalOtrosPagos(nomina.getOtrosPagos().getOtroPago().stream().map(Nomina.OtrosPagos.OtroPago::getImporte).reduce(BigDecimal.ZERO, BigDecimal::add));
        }
        nomina.setEmisor(createEmisor());
        nomina.setReceptor(createReceptor());
        nomina.setIncapacidades(createIncapacidades());
        return nomina;
    }

    private Nomina.Percepciones createPercepciones() {
        Nomina.Percepciones percepciones = of.createNominaPercepciones();
        Nomina.Percepciones.Percepcion per1 = of.createNominaPercepcionesPercepcion();
        per1.setTipoPercepcion(CTipoPercepcion.VALUE_1);
        per1.setClave("001");
        per1.setConcepto("Sueldo Quincenal de Prueba");
        per1.setImporteGravado(new BigDecimal("4005.35"));
        per1.setImporteExento(BigDecimal.ZERO);
        percepciones.getPercepcion().add(per1);
        Nomina.Percepciones.Percepcion per2 = of.createNominaPercepcionesPercepcion();
        per2.setTipoPercepcion(CTipoPercepcion.VALUE_8);
        per2.setClave("006");
        per2.setConcepto("Premio por Asistencia");
        per2.setImporteGravado(new BigDecimal("160.21"));
        per2.setImporteExento(BigDecimal.ZERO);
        percepciones.getPercepcion().add(per2);
        Nomina.Percepciones.Percepcion per3 = of.createNominaPercepcionesPercepcion();
        per3.setTipoPercepcion(CTipoPercepcion.VALUE_5);
        per3.setClave("007");
        per3.setConcepto("Fondo de Ahorro");
        per3.setImporteGravado(BigDecimal.ZERO);
        per3.setImporteExento(new BigDecimal("160.21"));
        percepciones.getPercepcion().add(per3);
        Nomina.Percepciones.Percepcion per4 = of.createNominaPercepcionesPercepcion();
        per4.setTipoPercepcion(CTipoPercepcion.VALUE_44);
        per4.setClave("054");
        per4.setConcepto("Prueba de nodos hijos");
        per4.setImporteGravado(new BigDecimal("50.00"));
        per4.setImporteExento(BigDecimal.ZERO);
        Nomina.Percepciones.Percepcion.AccionesOTitulos accionesOTitulos = of.createNominaPercepcionesPercepcionAccionesOTitulos();
        accionesOTitulos.setValorMercado(BigDecimal.ONE);
        accionesOTitulos.setPrecioAlOtorgarse(new BigDecimal("0.99"));
        per4.setAccionesOTitulos(accionesOTitulos);
        Nomina.Percepciones.Percepcion.HorasExtra horasExtra = of.createNominaPercepcionesPercepcionHorasExtra();
        horasExtra.setDias(1);
        horasExtra.setTipoHoras(CTipoHoras.VALUE_2);
        horasExtra.setHorasExtra(4);
        horasExtra.setImportePagado(BigDecimal.ZERO);
        per4.getHorasExtra().add(horasExtra);
        percepciones.getPercepcion().add(per4);
        Nomina.Percepciones.JubilacionPensionRetiro jubilacionPensionRetiro = of.createNominaPercepcionesJubilacionPensionRetiro();
        jubilacionPensionRetiro.setTotalUnaExhibicion(new BigDecimal("0.01"));
        jubilacionPensionRetiro.setTotalParcialidad(new BigDecimal("0.02"));
        jubilacionPensionRetiro.setMontoDiario(new BigDecimal("0.03"));
        jubilacionPensionRetiro.setIngresoAcumulable(new BigDecimal("0.04"));
        jubilacionPensionRetiro.setIngresoNoAcumulable(new BigDecimal("0.05"));
        percepciones.setJubilacionPensionRetiro(jubilacionPensionRetiro);
        Nomina.Percepciones.SeparacionIndemnizacion separacionIndemnizacion = of.createNominaPercepcionesSeparacionIndemnizacion();
        separacionIndemnizacion.setTotalPagado(new BigDecimal("0.01"));
        separacionIndemnizacion.setNumAñosServicio(4);
        separacionIndemnizacion.setUltimoSueldoMensOrd(new BigDecimal("0.02"));
        separacionIndemnizacion.setIngresoAcumulable(new BigDecimal("0.03"));
        separacionIndemnizacion.setIngresoNoAcumulable(new BigDecimal("0.04"));
        percepciones.setSeparacionIndemnizacion(separacionIndemnizacion);

        if (percepciones.getPercepcion() != null && !percepciones.getPercepcion().isEmpty()
                && percepciones.getPercepcion().stream().anyMatch(totalSueldosMatch())) {
            percepciones.setTotalSueldos(percepciones.getPercepcion().stream().filter(totalSueldosMatch()).map(p -> {
                BigDecimal sum = BigDecimal.ZERO;
                if (p.getImporteExento() != null) sum = sum.add(p.getImporteExento());
                if (p.getImporteGravado() != null) sum = sum.add(p.getImporteGravado());
                return sum;
            }).reduce(BigDecimal.ZERO, BigDecimal::add));
        }
        percepciones.setTotalGravado(percepciones.getPercepcion().stream().map(Nomina.Percepciones.Percepcion::getImporteGravado).reduce(BigDecimal.ZERO, BigDecimal::add));
        percepciones.setTotalExento(percepciones.getPercepcion().stream().map(Nomina.Percepciones.Percepcion::getImporteExento).reduce(BigDecimal.ZERO, BigDecimal::add));
        return percepciones;
    }

    Predicate<Nomina.Percepciones.Percepcion> totalSueldosMatch() {
        return p -> !p.getTipoPercepcion().equals(CTipoPercepcion.VALUE_17)
                && !p.getTipoPercepcion().equals(CTipoPercepcion.VALUE_18) && !p.getTipoPercepcion().equals(CTipoPercepcion.VALUE_20)
                && !p.getTipoPercepcion().equals(CTipoPercepcion.VALUE_34) && !p.getTipoPercepcion().equals(CTipoPercepcion.VALUE_35);
    }

    private Nomina.OtrosPagos createOtrosPagos() {
        Nomina.OtrosPagos otrosPagos = of.createNominaOtrosPagos();
        Nomina.OtrosPagos.OtroPago otp1 = of.createNominaOtrosPagosOtroPago();
        otp1.setTipoOtroPago(CTipoOtroPago.VALUE_2);
        otp1.setClave("002");
        otp1.setConcepto("Subsidio para el empleo");
        otp1.setImporte(BigDecimal.ZERO);
        Nomina.OtrosPagos.OtroPago.SubsidioAlEmpleo subsidioAlEmpleo = of.createNominaOtrosPagosOtroPagoSubsidioAlEmpleo();
        subsidioAlEmpleo.setSubsidioCausado(new BigDecimal("1488.92"));
        otp1.setSubsidioAlEmpleo(subsidioAlEmpleo);
        otrosPagos.getOtroPago().add(otp1);
        Nomina.OtrosPagos.OtroPago otp2 = of.createNominaOtrosPagosOtroPago();
        otp2.setTipoOtroPago(CTipoOtroPago.VALUE_10);
        otp2.setClave("999");
        otp2.setConcepto("Prueba de nodos hijos");
        otp2.setImporte(BigDecimal.ZERO);
        Nomina.OtrosPagos.OtroPago.CompensacionSaldosAFavor compensacionSaldosAFavor = of.createNominaOtrosPagosOtroPagoCompensacionSaldosAFavor();
        compensacionSaldosAFavor.setSaldoAFavor(new BigDecimal("0.01"));
        compensacionSaldosAFavor.setAño((short) 2020);
        compensacionSaldosAFavor.setRemanenteSalFav(BigDecimal.ZERO);
        otp2.setCompensacionSaldosAFavor(compensacionSaldosAFavor);
        otrosPagos.getOtroPago().add(otp2);
        return otrosPagos;
    }

    private Nomina.Deducciones createDeducciones() {
        Nomina.Deducciones deducciones = of.createNominaDeducciones();
        deducciones.setTotalOtrasDeducciones(new BigDecimal("436.61"));
        deducciones.setTotalImpuestosRetenidos(new BigDecimal("375.58"));
        Nomina.Deducciones.Deduccion ded1 = of.createNominaDeduccionesDeduccion();
        ded1.setTipoDeduccion(CTipoDeduccion.VALUE_2);
        ded1.setClave("011");
        ded1.setConcepto("Retencion ISR");
        ded1.setImporte(new BigDecimal("375.58"));
        deducciones.getDeduccion().add(ded1);
        Nomina.Deducciones.Deduccion ded2 = of.createNominaDeduccionesDeduccion();
        ded2.setTipoDeduccion(CTipoDeduccion.VALUE_1);
        ded2.setClave("012");
        ded2.setConcepto("Descuento IMSS");
        ded2.setImporte(new BigDecimal("116.18"));
        deducciones.getDeduccion().add(ded2);
        Nomina.Deducciones.Deduccion ded3 = of.createNominaDeduccionesDeduccion();
        ded3.setTipoDeduccion(CTipoDeduccion.VALUE_4);
        ded3.setClave("016");
        ded3.setConcepto("Aportación Fondo de Ahorro");
        ded3.setImporte(new BigDecimal("320.43"));
        deducciones.getDeduccion().add(ded3);
        return deducciones;
    }

    private Nomina.Emisor createEmisor() {
        Nomina.Emisor emisor = of.createNominaEmisor();
        emisor.setCurp("XEXX010101HNEXXXA4");
        emisor.setRegistroPatronal("E6479648108");
        emisor.setRfcPatronOrigen("XAXX010101000");
        emisor.setEntidadSNCF(createEntidadSNCF());
        return emisor;
    }

    private Nomina.Emisor.EntidadSNCF createEntidadSNCF() {
        Nomina.Emisor.EntidadSNCF entidad = of.createNominaEmisorEntidadSNCF();
        entidad.setOrigenRecurso(COrigenRecurso.IF);
        entidad.setMontoRecursoPropio(BigDecimal.TEN);
        return entidad;
    }

    private Nomina.Receptor createReceptor() {
        Nomina.Receptor receptor = of.createNominaReceptor();
        receptor.setCurp("OEGH910609HNLRNR14");
        receptor.setNumSeguridadSocial("24109129148");
        receptor.setFechaInicioRelLaboral(datatypeFactory.newXMLGregorianCalendar(2017, 1, 1, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED));
        receptor.setAntigüedad("P11Y7M14D");
        receptor.setTipoContrato(CTipoContrato.VALUE_1);
        receptor.setSindicalizado("No");
        receptor.setTipoJornada(CTipoJornada.VALUE_1);
        receptor.setTipoRegimen(CTipoRegimen.VALUE_1);
        receptor.setNumEmpleado("001");
        receptor.setDepartamento("Sistemas");
        receptor.setPuesto("Programador");
        receptor.setRiesgoPuesto(CRiesgoPuesto.VALUE_2);
        receptor.setPeriodicidadPago(CPeriodicidadPago.VALUE_4);
        receptor.setBanco(CBanco.VALUE_1);
        receptor.setCuentaBancaria("002760700569076003");
        receptor.setSalarioBaseCotApor(new BigDecimal("293.73"));
        receptor.setSalarioDiarioIntegrado(new BigDecimal("293.73"));
        receptor.setClaveEntFed(CEstado.SON);
        receptor.getSubContratacion().addAll(createSubContrataciones());
        return receptor;
    }

    private Collection<? extends Nomina.Receptor.SubContratacion> createSubContrataciones() {
        List<Nomina.Receptor.SubContratacion> subContratacionList = new ArrayList<>();
        Nomina.Receptor.SubContratacion subContratacion = of.createNominaReceptorSubContratacion();
        subContratacion.setRfcLabora("XAXX010101000");
        subContratacion.setPorcentajeTiempo(BigDecimal.valueOf(30));
        subContratacionList.add(subContratacion);
        return subContratacionList;
    }

    private Nomina.Incapacidades createIncapacidades() {
        Nomina.Incapacidades incapacidades = of.createNominaIncapacidades();
        Nomina.Incapacidades.Incapacidad inc1 = of.createNominaIncapacidadesIncapacidad();
        inc1.setDiasIncapacidad(11);
        inc1.setTipoIncapacidad(CTipoIncapacidad.VALUE_4);
        inc1.setImporteMonetario(new BigDecimal("0.01"));
        incapacidades.getIncapacidad().add(inc1);
        Nomina.Incapacidades.Incapacidad inc2 = of.createNominaIncapacidadesIncapacidad();
        inc2.setDiasIncapacidad(20);
        inc2.setTipoIncapacidad(CTipoIncapacidad.VALUE_1);
        inc2.setImporteMonetario(new BigDecimal("0.02"));
        incapacidades.getIncapacidad().add(inc2);
        return incapacidades;
    }

}
