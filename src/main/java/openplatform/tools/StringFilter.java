package openplatform.tools;


/**
 * Class StringFilter
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class StringFilter
{
    private StringFilter() {}


    /**
     * usefull for HTML forms
     * see http://www.bbsinc.com/symbol.html
     **/
    public static String replaceSpecialChars(String string)
    {
        if(string==null)
            return null;

        if(string.indexOf("&")!=-1)
        {
            Debug.println(null, Debug.DEBUG, string+" -> & replaced");
            string=string.replaceAll("&","&amp;");
        }

        if(string.indexOf("'")!=-1)
        {
            Debug.println(null, Debug.DEBUG, string+" -> ' replaced");
            // string=string.replaceAll("'","&apos;");
            string=string.replaceAll("'","&#39;");
        }

        if(string.indexOf("\"")!=-1)
        {
            Debug.println(null, Debug.DEBUG, string+" -> \" replaced");
            // string=string.replaceAll("\"","&quot;");
            string=string.replaceAll("\"","&#34;");
        }
        
        if(string.indexOf(">")!=-1)
        {
            Debug.println(null, Debug.DEBUG, string+" -> > replaced");
            string=string.replaceAll(">","&gt;");
        }

        if(string.indexOf("<")!=-1)
        {
            Debug.println(null, Debug.DEBUG, string+" -> < replaced");
            string=string.replaceAll("<","&lt;");
        }

        if(string.indexOf("\\")!=-1)
        {
            Debug.println(null, Debug.DEBUG, string+" -> \\ replaced");
            string=string.replaceAll("\\\\","&#92;");
        }

        return string;
    }
}
