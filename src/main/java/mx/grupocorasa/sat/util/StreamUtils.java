package mx.grupocorasa.sat.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class StreamUtils {
    public static List<InputStream> copyStream(InputStream input, int copies) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            input.transferTo(baos);
            List<InputStream> list = new ArrayList<>();
            for (int i = 0; i < copies; i++) {
                list.add(new ByteArrayInputStream(baos.toByteArray()));
            }
            return list;
        }
    }
}