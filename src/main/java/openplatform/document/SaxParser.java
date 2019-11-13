package openplatform.document;

import javax.xml.parsers.*;
import java.io.*;
import openplatform.tools.*;


/**
 * Class SaxParser
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class SaxParser
{
    private SAXParser parser = null;

    public SaxParser()
    {
        try
        {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            parser = factory.newSAXParser();
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }
    }

    public boolean parse(SaxHandler handler, String buffXml)
    {
        if(parser == null) return false;

        try
        {
            ByteArrayInputStream bais = new ByteArrayInputStream(buffXml.getBytes("UTF-8"));
            boolean res = parse(handler, bais);
            bais.close();
            return res;
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            return false;
        }
    }


    public boolean parse(SaxHandler handler, InputStream stream)
    {
        if(parser == null) return false;

        try
        {
            parser.parse(stream, handler);
            return true;
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            return false;
        }
    }


    public static void main(String[] args)
    {
        String st = "<?xml version=\"1.0\"?>"
            +"<root>"
            +"<voiture>"
            +"<siege position=\"avant\">thai</siege><siege position='arrière'>bébé</siege><siege test='true' version='12'>personne</siege>"
            +"un truc qui roule"
            +"</voiture>"
            +"<camion><![CDATA[ <&éèô ]]></camion>"
            +"<camion><![CDATA[hello\nworld]]></camion>"
            +"<camion>bon\r\tjour</camion>"
            +"<camping-car/>"
            +"</root>";

        SaxParser sp = new SaxParser();
        SaxHandler sh = new SaxHandler();
        System.out.println("PARSE OK ? " + sp.parse(sh, st));
    }
}
