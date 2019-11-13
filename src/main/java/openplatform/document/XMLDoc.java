package openplatform.document;

import openplatform.tools.*;

import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import java.util.*;
import openplatform.database.*;
import org.w3c.dom.traversal.*;
import org.apache.xpath.*;


/**
 * Class XMLDoc
 *
 * <pre>
 * Performs search in XML document using XPATH language
 * See tutorial for XPATH syntax : http://www.w3schools.com/xpath/
 * </pre>
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class XMLDoc
{
    private XMLDocError error = new XMLDocError();
    private Document document;
    private ArrayList array = new ArrayList();

    /**
     * If cached is true then the data are cached in memory
     */
    public XMLDoc(InputStream inputXML)
        throws Exception
    {
        init(inputXML);
    }


    /**
     * If cached is true then the data are cached in memory
     */
    public XMLDoc(String xml)
        throws Exception
    {
        init(xml);
    }


    /**
     * Init with a stream
     */
    public void init(InputStream inputXML)
        throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setErrorHandler(error);
        document = builder.parse(new InputSource(inputXML));
        inputXML.close();
    }

    
    /**
     * Init with the buffered xml string
     */
    public void init(String xml)
        throws Exception
    {
        ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes("UTF-8"));
        init(bais);        
    }

    /**
     * Return an arraylist of XMLResult
     */
    public ArrayList getNodeValue(String xpath)
    {
        array.clear();

        try
        {
            NodeIterator nl = XPathAPI.selectNodeIterator(document, xpath);
            Node n;
        
            while((n=nl.nextNode()) != null)
            {                
                Node child = n.getFirstChild();
                
                if(isTextNode(child))
                {
                    XMLResult xmlr = new XMLResult();
                    xmlr.setValue(child.getNodeValue());

                    if(n.hasAttributes())
                    {
                        NamedNodeMap m = n.getAttributes();
                        for(int i=0; i<m.getLength(); i++)
                        {
                            xmlr.addAttribute(m.item(i).getNodeName(), m.item(i).getNodeValue());
                        }
                    }

                    array.add(xmlr);
                }
            }
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }

        Debug.println(this, Debug.DEBUG, "XPATH="+xpath+" FOUND:"+array);

        return array;
    }



    private boolean isTextNode(Node n)
    {
        if (n == null)
            return false;

        short nodeType = n.getNodeType();
        return nodeType == Node.CDATA_SECTION_NODE || nodeType == Node.TEXT_NODE;
    }
        
    /*
    public static void main(String[] args)
        throws Exception
    {
        String st = "<?xml version=\"1.0\"?>"
            +"<root>"
            +"<voiture>"
            +"<siege position=\"avant\">thai</siege><siege position='arrière'>bébé</siege><siege test='true' version='12'>personne</siege>"
            +"un truc qui roule"
            +"</voiture>"
            +"<camion><![CDATA[ <&éèô ]]></camion>"
            +"</root>";

        XMLDoc d = new XMLDoc(st);
        ArrayList res = d.getNodeValue("/root/voiture/siege[@position='avant']");
        res = d.getNodeValue("/root/voiture/siege");
        res = d.getNodeValue("/root/camion");
        res = d.getNodeValue("/root/voiture");
        res = d.getNodeValue("//siege[@position]");
    }
    */     
}
