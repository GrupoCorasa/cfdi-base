package mx.grupocorasa.sat.factory.common;

import mx.grupocorasa.sat.common.implocal10.ImpuestosLocales;
import mx.grupocorasa.sat.common.implocal10.ObjectFactory;

import java.math.BigDecimal;

public class ExampleImpLocalFactory {

    private static ExampleImpLocalFactory instance;

    private final ObjectFactory of;

    private ExampleImpLocalFactory() {
        of = new ObjectFactory();
    }

    public static ExampleImpLocalFactory getInstance() {
        if (instance == null) {
            instance = new ExampleImpLocalFactory();
        }
        return instance;
    }

    public ImpuestosLocales createImpuestosLocales() {
        ImpuestosLocales impuestosLocales = of.createImpuestosLocales();
        impuestosLocales.setVersion("1.0");
        impuestosLocales.setTotaldeRetenciones(new BigDecimal("4.00"));
        impuestosLocales.setTotaldeTraslados(new BigDecimal("16.00"));

        ImpuestosLocales.RetencionesLocales ret = of.createImpuestosLocalesRetencionesLocales();
        ret.setImpLocRetenido("IVA RET 4%");
        ret.setTasadeRetencion(new BigDecimal("4"));
        ret.setImporte(new BigDecimal("4.000000"));
        impuestosLocales.getRetencionesLocalesAndTrasladosLocales().add(ret);

        ImpuestosLocales.TrasladosLocales tras = of.createImpuestosLocalesTrasladosLocales();
        tras.setImpLocTrasladado("IVA TRAS 16%");
        tras.setTasadeTraslado(new BigDecimal("16"));
        tras.setImporte(new BigDecimal("16"));
        impuestosLocales.getRetencionesLocalesAndTrasladosLocales().add(tras);

        return impuestosLocales;
    }

}
