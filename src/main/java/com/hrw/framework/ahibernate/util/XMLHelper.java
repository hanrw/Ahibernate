
package com.hrw.framework.ahibernate.util;

import java.util.List;

import org.dom4j.io.SAXReader;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

import android.util.Log;

public class XMLHelper {
    private SAXReader saxReader;

    public SAXReader createSAXReader(String resourceName, List errorsList) {
        SAXReader saxReader = resolveSAXReader();
        saxReader.setErrorHandler(new ErrorLogger(errorsList));
        return saxReader;
    }

    private SAXReader resolveSAXReader() {
        if (saxReader == null) {
            saxReader = new SAXReader();
            saxReader.setMergeAdjacentText(true);
            saxReader.setValidation(false);
        }
        return saxReader;
    }

    public static class ErrorLogger implements ErrorHandler {

        private List<SAXParseException> errors;

        private ErrorLogger(List errors) {
            this.errors = errors;
        }

        public void error(SAXParseException error) {
            errors.add(error);
        }

        public void fatalError(SAXParseException error) {
            error(error);
        }

        public void warning(SAXParseException warn) {
            Log.w(XMLHelper.class.getName(), warn.getLineNumber() + " " + warn.getMessage());
        }
    }

}
