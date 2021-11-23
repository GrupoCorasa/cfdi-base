package mx.grupocorasa.sat.cfd;

import com.google.common.io.ByteStreams;
import mx.grupocorasa.sat.common.CFDFactory;
import mx.grupocorasa.sat.exceptions.UnsupportedVersionException;

import java.io.*;

public final class CFDv2Factory extends CFDFactory {

    public static CFDv2 load(File file) throws Exception {
        try (InputStream in = new FileInputStream(file)) {
            return load(in);
        }
    }

    public static CFDv2 load(InputStream in) throws Exception {
        return getCFD2(in);
    }

    private static CFDv2 getCFD2(InputStream in) throws Exception {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ByteStreams.copy(in, baos);
            byte[] data = baos.toByteArray();
            if (getVersion(data).equals("2.0")) {
                try (ByteArrayInputStream bais = new ByteArrayInputStream(data)) {
                    return new CFDv20(bais);
                }
            } else if (getVersion(data).equals("2.2")) {
                try (ByteArrayInputStream bais = new ByteArrayInputStream(data)) {
                    return new CFDv22(bais);
                }
            } else {
                throw new UnsupportedVersionException("La versión " + getVersion(data) + " no es soportada en esta librería");
            }
        }
    }

}
