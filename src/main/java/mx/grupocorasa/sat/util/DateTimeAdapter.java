package mx.grupocorasa.sat.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    DateTimeFormatter CUSTOM_FORMAT_STRING = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public String marshal(LocalDateTime v) {
        if (v == null) return null;
        return v.format(CUSTOM_FORMAT_STRING);
    }

    @Override
    public LocalDateTime unmarshal(String v) {
        if (v == null || v.isEmpty() || v.isBlank()) return null;
        return LocalDateTime.parse(v, CUSTOM_FORMAT_STRING);
    }

}