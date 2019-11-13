package openplatform.document;

import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import openplatform.tools.*;

/**
 * Class XMLDocError
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class XMLDocError implements ErrorHandler
{
    public void warning(SAXParseException e)
        throws SAXException
    {
        Debug.println(this, Debug.WARNING, e.getMessage());
    }

    public void error(SAXParseException e)
        throws SAXException
    {
        Debug.println(this, Debug.ERROR, e.getMessage());
    }

    public void fatalError(SAXParseException e)
        throws SAXException
    {
        Debug.println(this, Debug.ERROR, e.getMessage());
        throw e;
    }
}
