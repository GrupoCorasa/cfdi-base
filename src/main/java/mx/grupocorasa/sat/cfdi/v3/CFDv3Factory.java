package mx.grupocorasa.sat.cfdi.v3;

import com.google.common.io.ByteStreams;
import mx.grupocorasa.sat.common.CFDFactory;
import mx.grupocorasa.sat.exceptions.UnsupportedVersionException;

import java.io.*;

public final class CFDv3Factory extends CFDFactory {

    public static CFDv3 load(File file) throws Exception {
        try (InputStream in = new FileInputStream(file)) {
            return load(in);
        }
    }

    public static CFDv3 load(InputStream in) throws Exception {
        return getCFDI3(in);
    }

    private static CFDv3 getCFDI3(InputStream in) throws Exception {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ByteStreams.copy(in, baos);
            byte[] data = baos.toByteArray();
            switch (getVersion(data)) {
                case "3.0":
                    try (ByteArrayInputStream bais = new ByteArrayInputStream(data)) {
                        return new CFDv30(bais);
                    }
                case "3.2":
                    try (ByteArrayInputStream bais = new ByteArrayInputStream(data)) {
                        return new CFDv32(bais);
                    }
                case "3.3":
                    try (ByteArrayInputStream bais = new ByteArrayInputStream(data)) {
                        return new CFDv33(bais);
                    }
                default:
                    throw new UnsupportedVersionException("La versión " + getVersion(data) + " no es soportada en esta librería");
            }
        }
    }

}
