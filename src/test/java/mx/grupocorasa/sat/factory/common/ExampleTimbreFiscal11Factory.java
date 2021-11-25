package mx.grupocorasa.sat.factory.common;

import mx.grupocorasa.sat.common.TimbreFiscalDigital11.ObjectFactory;
import mx.grupocorasa.sat.common.TimbreFiscalDigital11.TimbreFiscalDigital;

import java.time.LocalDateTime;

public class ExampleTimbreFiscal11Factory {

    private static ExampleTimbreFiscal11Factory instance;
    private final ObjectFactory of;

    private ExampleTimbreFiscal11Factory() {
        of = new ObjectFactory();
    }

    public static ExampleTimbreFiscal11Factory getInstance() {
        if (instance == null) {
            instance = new ExampleTimbreFiscal11Factory();
        }
        return instance;
    }

    public TimbreFiscalDigital createTimbreFiscal() {
        TimbreFiscalDigital tfd = of.createTimbreFiscalDigital();
        tfd.setVersion("1.1");
        tfd.setFechaTimbrado(LocalDateTime.of(2017, 10, 31, 17, 39, 42));
        tfd.setNoCertificadoSAT("20001000000300022779");
        tfd.setUUID("5CB8D806-7BDF-4D24-AC4C-4C469EB4F57A");
        tfd.setSelloSAT("tUH6OL8H4V/Pcsjjjhvscme19OU1aRx03RKXRVsGUbtiZCQAxkWwzVKOjrXxJR0rVHHChhDpG6Yg/fIZaVwwVJXy9xLE2O6WUdeY+iEUJGrVp4Kv4PyfSz/KCqJp/dnpAGvdl2BpY1ZvpRi4a2/MJ7UvokEU2malSiGoB0mPrPeYo/nXkFUDfrisQ9pZDKgpowkw4mi4sYZOPl5JCPaF8X5LuSLDNcO3FPeslDvjqtM0Jlmu3tk5/O2opjhKDv7L+327JFU+efbExqifTR43Anthnu0mXv+zSviDlLbxJcRF+bXXvPHlYi3gENazzOxHlnlXa+qfCU3eNRd+uih3gA==");
        tfd.setSelloCFD("kqFJelLV1B99l8DunR98pn5k8uCwi94k+PhZJ2crw5Et2+qSkoROBlOWsoXrv93j5UUPBkxTmlStwTGBHSHWiQr1ewA4/fzseNIaWokVbIr8iFwrZETgZWp6Q+dErqLntDPISWYHmXfnvXi/Om7hCtklZC/msv3ZmmojcZEkjJb0Rk+sVDh+qm3sRbx40k1xKX1xtgLnX4/P/DwZlv+mj31YE4MEpy48xMjBA7dPNE4dF9UL3mTAQhwMV40MCrLpjTn95ov8mnL0ftaxuqzGXhqqNcDk1YF5OtBXuGFKwWAfY53bNxz1GeVY1+/8xZsmUmuukwA5uvIa7ghGt/4twA==");
        tfd.setRfcProvCertif("SFE0807172W7");
        return tfd;
    }

}
