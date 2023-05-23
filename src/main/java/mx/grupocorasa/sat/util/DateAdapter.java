package mx.grupocorasa.sat.util;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateAdapter extends XmlAdapter<String, LocalDate> {

    DateTimeFormatter CUSTOM_FORMAT_STRING = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public String marshal(LocalDate v) {
        if (v == null) return null;
        return v.format(CUSTOM_FORMAT_STRING);
    }

    @Override
    public LocalDate unmarshal(String v) {
        if (v == null || v.isEmpty()) return null;
        return LocalDate.parse(v, CUSTOM_FORMAT_STRING);
    }

}