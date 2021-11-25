package mx.grupocorasa.sat.factory.common;

import mx.grupocorasa.sat.common.TimbreFiscalDigital10.ObjectFactory;
import mx.grupocorasa.sat.common.TimbreFiscalDigital10.TimbreFiscalDigital;

import java.time.LocalDateTime;

public class ExampleTimbreFiscal10Factory {

    private static ExampleTimbreFiscal10Factory instance;
    private final ObjectFactory of;

    private ExampleTimbreFiscal10Factory() {
        of = new ObjectFactory();
    }

    public static ExampleTimbreFiscal10Factory getInstance() {
        if (instance == null) {
            instance = new ExampleTimbreFiscal10Factory();
        }
        return instance;
    }

    public TimbreFiscalDigital createTimbreFiscal() {
        TimbreFiscalDigital tfd = of.createTimbreFiscalDigital();
        tfd.setVersion("1.0");
        tfd.setFechaTimbrado(LocalDateTime.of(2017, 11, 28, 14, 14, 20));
        tfd.setNoCertificadoSAT("00001000000403498740");
        tfd.setUUID("073B945D-6C2E-4BAD-97A3-8480A5EA5BB5");
        tfd.setSelloSAT("MagGid/SIWef19OP2kbi2tUj/ge63szT/nwqMR/PYiMXv2HHBe4BxH0gTvQS1LK7L7goot2qMpkgvqH3p3ICC6CzNcFvYgTA1MaZ0bnYQuEeCLXqQ6qZbKl+wuTLdp09R1Qg8dAkOUZP+WzhjP+eI588qdZhrzHEAmF++He/B/IFSSB9VdX23inyKQ9bdRE0QKPZEFgQykoaVkXZE/k/JnGs3MQKh0tVfTM+Dge5pJbt12AeYTXoQURP3O/vkxm9u4MsMyYuBYM2tglC1R/K69RKcN8R7NlXIDF/52xI1f0WMIe/Al9HdTechjpbuEK0XrqIXi73vH/9oWH3KoDmAw==");
        tfd.setSelloCFD("XnxPA11RsNodVN9UMXVDF+rh/ZoFqfIFMVdjgIlCX/zvuplvxcaPMyfgySIsARc6fKNfn0mSiEa7biGA+5ekho7NVmQwwyQZqJbVIGO3hgWXlZphdfoEgvzB34keDRGTBJJz8OoVBicOE8zWl7xPltwihsCMIptWATPaZfF11t5Zy5Hy/pRCBSinNIoN93uxbMOMtU2nXYYZKNlQ913nRIaEDnSlJPQG5T1kobf2XTeKr0UrVGFqDLDCT0N6W0GeJzay8IkYLTSDomKL1rKI1M0UnDXjk7ptXu5H4Zp6bM2+NYgwfynIaMEsvjApdCh0eDzfRhXyH9xL/0aGRbpNUQ==");
        return tfd;
    }

}
