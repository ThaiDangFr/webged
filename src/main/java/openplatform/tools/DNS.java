package openplatform.tools;

import java.net.*;
//import org.apache.oro.text.regex.*;

import openplatform.regexp.Regexp;

/**
 * Class class
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class DNS
{
    //private static PatternCompiler compiler = new Perl5Compiler();
    //private static PatternMatcher matcher = new Perl5Matcher();

    //private static Pattern numberPattern;

	/*
    static
    {
        try
        {
            numberPattern = compiler.compile("\\d+");
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }
    }
*/
	
	
    /**
     * name can be ip address or a dns name
     */
    public static String getIPAddress(String hostname)
    {
        if(hostname == null || hostname.trim().equals("")) return null;

        Regexp regexp = new Regexp("\\d+");
        
        // if hostname is a number, refuse it
        //if(matcher.matches(hostname, numberPattern)) return null;
        if(regexp.matches(hostname)) return null;

        try
        {
            InetAddress inetadd = InetAddress.getByName(hostname);        
            return inetadd.getHostAddress();
        }
        catch(Exception e)
        {
//             Debug.println(null, Debug.ERROR, e);
            return null;
        }      
    }


    public static void main(String[] args)
    {
        System.out.println("bora "+DNS.getIPAddress("bora"));
        System.out.println("192.168.0.1 "+DNS.getIPAddress("192.168.0.1"));
        System.out.println("194.146.224.102 "+DNS.getIPAddress("194.146.224.102"));
        System.out.println("www.google.fr "+DNS.getIPAddress("www.google.fr"));
        System.out.println("1234 "+DNS.getIPAddress("1234"));
        System.out.println("12dq.s34 "+DNS.getIPAddress("12dq.s34"));
    }
}
