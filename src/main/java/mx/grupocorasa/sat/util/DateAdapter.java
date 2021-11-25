package mx.grupocorasa.sat.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateAdapter extends XmlAdapter<String, LocalDate> {

    DateTimeFormatter CUSTOM_FORMAT_STRING = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public String marshal(LocalDate v) {
        return v.format(CUSTOM_FORMAT_STRING);
    }

    @Override
    public LocalDate unmarshal(String v) {
        return LocalDate.parse(v, CUSTOM_FORMAT_STRING);
    }

}