package mx.grupocorasa.sat.common;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

public final class ValidationErrorHandler extends DefaultHandler {

    private final List<SAXParseException> errors = Lists.newArrayList();

    public void error(SAXParseException e) {
        errors.add(e);
    }

    public List<SAXParseException> getErrors() {
        return ImmutableList.copyOf(errors);
    }

}
