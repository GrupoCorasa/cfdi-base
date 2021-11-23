package mx.grupocorasa.sat.security.factory;

import mx.grupocorasa.sat.security.KeyLoader;
import mx.grupocorasa.sat.security.KeyLoaderEnumeration;
import mx.grupocorasa.sat.security.PrivateKeyLoader;
import mx.grupocorasa.sat.security.PublicKeyLoader;

import java.io.InputStream;

@SuppressWarnings("DuplicatedCode")
public class KeyLoaderFactory {

    public static KeyLoader createInstance(KeyLoaderEnumeration keyLoaderEnumeration, String keyLocation, String... keyPassword) {
        KeyLoader keyLoader;
        if (keyLoaderEnumeration == KeyLoaderEnumeration.PRIVATE_KEY_LOADER) {
            keyLoader = new PrivateKeyLoader(keyLocation, keyPassword == null ? null : keyPassword[0]);
        } else if (keyLoaderEnumeration == KeyLoaderEnumeration.PUBLIC_KEY_LOADER) {
            keyLoader = new PublicKeyLoader(keyLocation);
        } else {
            keyLoader = null;
        }
        return keyLoader;
    }

    public static KeyLoader createInstance(KeyLoaderEnumeration keyLoaderEnumeration, InputStream keyInputStream, String... keyPassword) {
        KeyLoader keyLoader;
        if (keyLoaderEnumeration == KeyLoaderEnumeration.PRIVATE_KEY_LOADER) {
            keyLoader = new PrivateKeyLoader(keyInputStream, keyPassword == null ? null : keyPassword[0]);
        } else if (keyLoaderEnumeration == KeyLoaderEnumeration.PUBLIC_KEY_LOADER) {
            keyLoader = new PublicKeyLoader(keyInputStream);
        } else {
            keyLoader = null;
        }
        return keyLoader;
    }

}
