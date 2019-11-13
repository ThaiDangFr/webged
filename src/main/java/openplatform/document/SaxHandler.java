package openplatform.document;

import org.xml.sax.helpers.*;
import org.xml.sax.*;
import openplatform.tools.*;
import java.util.*;

/**
 * Class SaxHandler
 *
 * Extends that class to fit your need
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class SaxHandler extends DefaultHandler 
{
    private StringBuffer currentText = new StringBuffer();
    private ArrayList parentTags = new ArrayList();

    /**
     * No need to redefine
     */
    public void characters(char ch[],int start,int length)
    {
        StringBuffer text = new StringBuffer();

        for (int i = start; i < start + length; i++)
        {
            switch (ch[i])
            {
            case '\\':
                Debug.println(this,Debug.WARNING, "characters:\\\\");
                break;

            case '"':
                Debug.println(this,Debug.WARNING, "characters:\\\"");
                break;

                /*case '\n':
                  Debug.println(Debug.WARNING+ "characters:" + "\\n");
                  break;*/

            case '\r':
                Debug.println(this,Debug.WARNING, "characters:\\r");
                break;

            case '\t':
                Debug.println(this,Debug.WARNING, "characters:\\t");
                break;

            default:
                text.append(ch[i]);
            }
        }

        currentText.append(text);
    }


    /**
     * The handling text
     */
    public void arrivingText(String text)
    {
        Debug.println(this, Debug.DEBUG, parentTags+"/"+text);
    }


    /**
     * No need to redefine
     */
    public void startElement(String uri, String localName, String qName, Attributes attributes)
    {
        parentTags.add(qName);
    }


    /**
     * No need to redefine
     */
    public void endElement(String uri,String localName,String qName)
    {
        arrivingText(currentText.toString().trim());
        currentText = new StringBuffer();

        parentTags.remove(parentTags.size()-1);
    }


    /**
     * if the specified tag is a parent
     */
    public boolean isParentTag(String tag)
    {
        return parentTags.contains(tag);
    }


    /**
     * The current tag
     */
    public String currentTag()
    {
        Object obj = parentTags.get(parentTags.size()-1);
        if(obj == null) return null;
        else return (String)obj;
    }
}
