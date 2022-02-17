package mx.grupocorasa.sat.cfdi.v4;

import com.google.common.io.ByteStreams;
import mx.grupocorasa.sat.common.CFDFactory;
import mx.grupocorasa.sat.exceptions.UnsupportedVersionException;

import java.io.*;

public final class CFDv4Factory extends CFDFactory {

    public static CFDv4 load(File file) throws Exception {
        try (InputStream in = new FileInputStream(file)) {
            return load(in);
        }
    }

    public static CFDv4 load(InputStream in) throws Exception {
        return getCFDI4(in);
    }

    private static CFDv4 getCFDI4(InputStream in) throws Exception {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ByteStreams.copy(in, baos);
            byte[] data = baos.toByteArray();
            switch (getVersion(data)) {
                case "4.0":
                    try (ByteArrayInputStream bais = new ByteArrayInputStream(data)) {
                        return new CFDv40(bais);
                    }
                default:
                    throw new UnsupportedVersionException("La versión " + getVersion(data) + " no es soportada en esta librería");
            }
        }
    }

}
