package openplatform.index;

// import org.apache.slide.extractor.SimpleXmlExtractor;
import java.util.*;
import java.io.*;
import openplatform.tools.*;

import org.jdom.*;
import org.jdom.input.SAXBuilder;

/**
 * XMLExtractor.java
 *
 * Don't extract attribute
 * JDOM => http://www.cafeconleche.org/books/xmljava/chapters/ch14.html
 * www.jdom.org
 *
 *
 * to skip dtd parsing if using XERCES (but for crimson it does not work ) :
 * builder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
 *
 * Created: Thu May 12 16:44:19 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class XMLExtractor 
{
    public static String extractText(InputStream in) throws Exception
    {
        SAXBuilder builder = new SAXBuilder();

        builder.setEntityResolver(new NoOpEntityResolver()); // SKIP DTD PARSING (can throw error)

        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        StringBuffer sb = listChildren(root);
        return sb.toString();
    }

    public static StringBuffer listChildren(Element current)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(" ").append(current.getName());

        List list = current.getContent();
        int len = list.size();
        for(int i=0; i<len; i++)
        {
            Object obj = list.get(i);
            if(obj instanceof Text)
            {
                Text t = (Text)obj;
                String txt = t.getTextTrim();
                if(txt!=null && !txt.trim().equals(""))
                    sb.append(" ").append(txt);
            }
            else if(obj instanceof Comment)
            {
                Comment c = (Comment)obj;
                String txt = c.getText();
                if(txt!=null && !txt.trim().equals(""))
                    sb.append(" ").append(txt);                
            }
        }

        List children = current.getChildren();
        Iterator iterator = children.iterator();

        while(iterator.hasNext())
        {
            Element child = (Element)iterator.next();
            StringBuffer sbc = listChildren(child);
            sb.append(sbc);
        }
        
        return sb;
    }

    public static void main(String[] args)
        throws Exception
    {
        FileInputStream fis = new FileInputStream("bookmarks.xml");
        System.out.println(XMLExtractor.extractText(fis));
    }
}
