package mx.grupocorasa.sat.util;

import com.google.common.io.ByteStreams;
import org.apache.commons.io.input.BOMInputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class StreamUtils {
    public static List<InputStream> copyStream(InputStream input, int copies) throws IOException {
        try (BOMInputStream bom = new BOMInputStream(input); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ByteStreams.copy(bom, baos);
            ByteStreams.copy(input, baos);
            List<InputStream> list = new ArrayList<>();
            for (int i = 0; i < copies; i++) {
                list.add(new ByteArrayInputStream(baos.toByteArray()));
            }
            return list;
        }
    }
}