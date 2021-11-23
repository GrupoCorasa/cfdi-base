package mx.grupocorasa.sat.factory.common;

import mx.grupocorasa.sat.common.donat10.Donatarias;
import mx.grupocorasa.sat.common.donat10.ObjectFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;

public class ExampleDonatFactory {

    private static ExampleDonatFactory instance;

    private ObjectFactory of;
    private DatatypeFactory datatypeFactory;

    private ExampleDonatFactory() {
        try {
            of = new ObjectFactory();
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static ExampleDonatFactory getInstance() {
        if (instance == null) {
            instance = new ExampleDonatFactory();
        }
        return instance;
    }

    public Donatarias createDonatarias() {
        Donatarias donat = of.createDonatarias();
        donat.setVersion("1.0");
        donat.setNoAutorizacion("prueba de donatarias 01");
        donat.setFechaAutorizacion(datatypeFactory.newXMLGregorianCalendar(2011, 4, 3, 14, 11, 36, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED));
        donat.setLeyenda("Este comprobante ampara un donativo, el cual será destinado por la " +
                "donataria a los fines propios de su objeto social. En el caso de que los " +
                "bienes donados hayan sido deducidos previamente para los efectos " +
                "del impuesto sobre la renta, este donativo no es deducible. La " +
                "reproducción no autorizada de este comprobante constituye un delito " +
                "en los términos de las disposiciones fiscales.");
        return donat;
    }

}
