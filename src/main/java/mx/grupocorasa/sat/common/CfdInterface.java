package mx.grupocorasa.sat.common;

import com.google.common.base.Joiner;
import org.xml.sax.ErrorHandler;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.TransformerFactory;
import java.io.OutputStream;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

public interface CfdInterface {

    String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    Joiner JOINER = Joiner.on(':');

    String getBaseContext();

    String[] getXSD();

    String getXSLT();

    Map<String, String> getLocalPrefixes();

    void addSchemaLocation(String uri);

    JAXBSource getJAXBSource() throws JAXBException;

    int getYear();

    String getCertificadoString();

    String getSelloString();

    Marshaller createMarshaller() throws JAXBException;

    List<String> getSchemaLocation();

    Object getComprobanteDocument();

    void setTransformerFactory(TransformerFactory tf);

    void sellar(PrivateKey key, X509Certificate cert) throws Exception;

    void validar(ErrorHandler handler) throws Exception;

    void verificar() throws Exception;

    void guardar(OutputStream out, Boolean formatted) throws Exception;

    String getCadenaOriginal() throws Exception;

}
