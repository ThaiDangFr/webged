package dang.websnmp;

import java.util.*;

import dang.cms.CmsLanguage;
import openplatform.tools.*;
import openplatform.snmp.*;
import openplatform.database.dbean.*;


/**
 * AdminSnmpDiscover.java
 *
 *
 * Created: Thu Jun 23 14:32:55 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class AdminSnmpDiscover implements Runnable
{
	private CmsLanguage cmsLang = new CmsLanguage();
	
    public static boolean running = false;

    private String beginIP; // jsp
    private String endIP; // jsp

    private String currentBeginIP;
    private String currentEndIP;
    private String currentIP;

    public String doDiscover()
        throws Exception
    {
        if(running == true)
        		throw new Exception(cmsLang.translate("process.running"));

        if(!checkIP(beginIP) || !checkIP(endIP))
            throw new Exception(cmsLang.translate("bad.value")+" IP");

        if(!lowerIP(beginIP,endIP))
        	throw new Exception(cmsLang.translate("bad.value")+" IP");

        currentBeginIP = beginIP;
        currentEndIP = endIP;

        Thread t = new Thread(this);
        t.setDaemon(true);
        t.start();

        return null;
    }


    public String doStop()
        throws Exception
    {
        running = false;
        return null;
    }



    public void run()
    {
        try
        {
            currentIP = null;
            running = true;
            DBSnmpHost host = new DBSnmpHost();
            String ni;
            while((ni=nextIP())!=null && running)
            {
                Snmp snmp = new Snmp(Snmp.SNMP_V1, ni, "public",null,null,null);
                SnmpNodeList nl = snmp.get("1.3.6.1.2.1.1.1");
                if(nl != null)
                {
                    int len = nl.size();
                    for(int i=0; i<len; i++)
                    {
                        SnmpNode node = nl.getSnmpNode(i);
                        String sysdescr = node.getValue();
                        host.clear();
                        host.setIpAddress(ni);

                        if(DBSnmpHost.count(host, null) != 0) continue;

                        host.setDisplayName(sysdescr);
                        host.setCommunity("public");
                        host.setSnmpVersion(DBSnmpHost.SNMPVERSION_0);
                        host.store();
                    }
                }
            }
        }
        catch(Exception e)
        {
        }
        finally
        {
            running = false;
        }
    }



    public void setBeginIP(String ip)
    {
        this.beginIP = ip;
    }

    public void setEndIP(String ip)
    {
        this.endIP = ip;
    }


    public boolean lowerIP(String beg, String end)
    {
        try
        {
            StringBuffer a = new StringBuffer();
            StringBuffer b = new StringBuffer();

            StringTokenizer sta = new StringTokenizer(beg, ".");
            while(sta.hasMoreTokens())
            {
                a.append(sta.nextToken());
            }

            StringTokenizer stb = new StringTokenizer(end, ".");
            while(stb.hasMoreTokens())
            {
                b.append(stb.nextToken());
            }

            int ia = Integer.parseInt(a.toString());
            int ib = Integer.parseInt(b.toString());

            if(ia<ib) return true;
            else return false;
        }
        catch(Exception e)
        {
            return false;
        }
    }


    private boolean checkIP(String ip)
    {
        try
        {
            if(ip == null) return false;
            StringTokenizer st = new StringTokenizer(ip, ".");
            int i=0;
            while(st.hasMoreTokens())
            {
                int n = Integer.parseInt(st.nextToken());
                if(n<0 || n>255) return false;
                i++;
            }
            
            if(i!=4) return false;
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            return false;
        }

        return true;
    }

    private int[] split(String addr)
        throws Exception
    {
        int[] ip = new int[4];

        StringTokenizer st = new StringTokenizer(addr, ".");
        int i=0;
        while(st.hasMoreTokens())
        {
            ip[i] = Integer.parseInt(st.nextToken().trim());
            i++;
        }

        return ip;
    }
    
    
    private String nextIP()
        throws Exception
    {
        if(currentIP == null)
        {
            currentIP = currentBeginIP;
            return currentIP;
        }

        int[] ip = split(currentIP);
        int aa = ip[0];
        int bb = ip[1];
        int cc = ip[2];
        int dd = ip[3];

        dd++;
        if(dd == 255)
        {
            dd = 1;
            cc++;
        }

        if(cc == 255)
        {
            cc = 1;
            bb++;
        }

        if(bb == 255)
        {
            bb = 1;
            aa++;
        }

        if(aa == 255) return null;

        String nextIP = Integer.toString(aa) +"."+ Integer.toString(bb) +"."+ Integer.toString(cc) +"."+ Integer.toString(dd);
        currentIP = nextIP;
        if(nextIP.equals(currentEndIP)) return null;
        else return nextIP;
    }


    public static void main(String[] args)
        throws Exception
    {
        AdminSnmpDiscover a = new AdminSnmpDiscover();
        a.setBeginIP("192.168.0.1");
        a.setEndIP("192.168.0.5");
        a.doDiscover();
       
    }

    // GET / SET

    public final boolean getRunning()
    {
        return this.running;
    }

    /**
     * Gets the value of beginIP
     *
     * @return the value of beginIP
     */
    public final String getBeginIP()
    {
        return this.beginIP;
    }

    /**
     * Gets the value of endIP
     *
     * @return the value of endIP
     */
    public final String getEndIP()
    {
        return this.endIP;
    }

    /**
     * Gets the value of currentIP
     *
     * @return the value of currentIP
     */
    public final String getCurrentIP()
    {
        return this.currentIP;
    }

    /**
     * Sets the value of currentIP
     *
     * @param argCurrentIP Value to assign to this.currentIP
     */
    public final void setCurrentIP(final String argCurrentIP)
    {
        this.currentIP = argCurrentIP;
    }

}
