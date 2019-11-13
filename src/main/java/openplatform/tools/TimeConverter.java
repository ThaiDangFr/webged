package openplatform.tools;

import dang.cms.CmsLanguage;


/**
 * Class TimeConverter
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class TimeConverter
{

    /**
     * Get a human readable Time
     */
	
    public static String humanRead(String seconds)
    {
        try
        {
            long sec = Long.parseLong(seconds);
            return humanRead(sec);
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
            return "ERROR";
        }
    }


    public static String humanRead(long sec)
    {
    	CmsLanguage cmsLang = new CmsLanguage();
    	
        if(sec<60)
        {
            return sec + " "+ cmsLang.translate("seconds");
        }
        else if(sec>=60 && sec<3600)
        {
        	long l = sec/60;
            return Long.toString(l)+ " "+ cmsLang.translate("minutes") + " " + humanRead(sec-l*60);
        }
        else if(sec>=3600 && sec<86400)
        {
        	long l=sec/3600;
            return Long.toString(l) + " "+ cmsLang.translate("hours") + " " + humanRead(sec-l*3600);
        }
        else if(sec>=86400 && sec<604800)
        {
        	long l=sec/86400;
        	System.out.println(l);
            return Long.toString(l) + " "+ cmsLang.translate("days") + " " + humanRead(sec-l*86400);
        }
        else if(sec>=604800)
        {
        	long l=sec/604800;
            return Long.toString(l) + " "+ cmsLang.translate("weeks") + " " + humanRead(sec-l*604800);
        }
        else
            return sec + " "+ cmsLang.translate("seconds");
    }    
    
    /*
    public static void main(String[] args)
    {
    	System.out.println(humanRead(167800));
    }
    */
}
