package openplatform.snmp;

import java.io.*;
import java.util.*;
import net.percederberg.mibble.*;
import net.percederberg.mibble.value.*;
import net.percederberg.mibble.snmp.*;
import openplatform.tools.*;
import openplatform.database.dbean.*;
import java.util.zip.*;

/**
 * MibManager.java
 *
 *
 * Created: Wed Jun 22 17:14:28 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class MibManager 
{
    //public final static String DIR = System.getProperty("user.home") + "/.openplatform/_MIB";
    public final static String DIR = Configuration.getROOT() + "/_MIB";
    public static MibLoader loader = new MibLoader();

    static
    {

        File file = new File(DIR);
        if(!file.exists())
            file.mkdirs();
        loader.addDir(new File(DIR));

    }


    public static void upload(String name, InputStream input)
        throws Exception
    {
        if(name == null || name.trim().equals("")) return;

        String filepath = DIR+"/"+name;
        File file = new File(filepath);
        if(!file.exists()) file.createNewFile();

        StreamConverter.writeToDisk(file,input);
        
        //FileOutputStream fos = new FileOutputStream(file);
        //fos.write(data);
        //fos.close();

        DBSnmpMibFile mf = new DBSnmpMibFile();
        mf.setName(name);
        mf.store();

        FileReader fr = new FileReader(filepath);
        Mib mib = loader.load(fr);
        Iterator iter = mib.getAllSymbols().iterator();


        String mibname = className(mib.getName());
        String mibFileId = mf.getId();

        
        DBSnmpOID snmpOID = new DBSnmpOID();
        while(iter.hasNext())
        {
            boolean objectType = false;
            String oid = null;
            String captorname = null;
            String description = null;
            String parentId = null;

            MibSymbol symbol = (MibSymbol)iter.next();

            captorname = filterNameUpperCase(symbol.getName());

            if(symbol instanceof MibValueSymbol)
            {
                MibValueSymbol sym = (MibValueSymbol)symbol;
                MibValue value = sym.getValue();
                MibType type = sym.getType();

                if(value instanceof ObjectIdentifierValue)
                {
                    ObjectIdentifierValue oiv = (ObjectIdentifierValue)value;
                    oid = oiv.toObject().toString();
                }

                if(type instanceof SnmpObjectType)
                {
                    objectType = true;
                    SnmpObjectType id = (SnmpObjectType)type;
                    description = id.getDescription();
                }
            }

            

            
            if(oid != null && captorname != null && objectType)
            {
                // search a parentId
                int idx = oid.lastIndexOf(".");
                String prevOID = oid.substring(0,idx);
                
//                 Debug.println(null, Debug.DEBUG, "PREVOID="+prevOID);

                snmpOID.clear();                
                snmpOID.setOid(prevOID);
                snmpOID.setMibName(mibname);
                snmpOID.setMibFileId(mibFileId);
                DBSnmpOID parentOID = DBSnmpOID.loadByKey(snmpOID);
                if(parentOID != null) parentId = parentOID.getId();
                
                

                // store
                snmpOID.clear();
                snmpOID.setOid(oid);
                snmpOID.setMibName(mibname);
                snmpOID.setCaptorName(captorname);
                snmpOID.setDescription(description);
                snmpOID.setParentId(parentId);
                snmpOID.setMibFileId(mibFileId);

                if(DBSnmpOID.count(snmpOID, null) == 0)
                    snmpOID.store();
            }
            
        }

    }

    private static String filterNameUpperCase(String string)
    {
        return (filterName(string)).toUpperCase();
    }


    private static String filterName(String string)
    {
        StringBuffer result = new StringBuffer();

        char[] charname = string.toCharArray();
        for(int i=0;i<charname.length;i++)
        {
            if(i==0)
                charname[0] = Character.toUpperCase(charname[0]);
            
            if(charname[i] == '-')
            {
                if(i+1 < charname.length)
                    charname[i+1] = Character.toUpperCase(charname[i+1]);

                continue;
            }
            else if(charname[i] =='.')
            {
                break;
            }

            result.append(charname[i]);
        }

        return result.toString();
    }

    private static String className(String mibfile)
    {
        StringBuffer result = new StringBuffer();
        char[] charname = mibfile.toLowerCase().toCharArray();

        for(int i=0;i<charname.length;i++)
        {
            if(i==0)
                charname[0] = Character.toUpperCase(charname[0]);

            if(charname[i] == '-')
            {
                if(i+1 < charname.length)
                    charname[i+1] = Character.toUpperCase(charname[i+1]);

                continue;
            }
            else if(charname[i] =='.')
            {
                break;
            }

            result.append(charname[i]);
        }

        return result.toString();
    }




    /**
     * ARG1 : the zip file
     */
    
    
    public static void main(String[] args)
        throws Exception
    {
        File file = new File(args[0]);
        ZipFile zf = new ZipFile(file, ZipFile.OPEN_READ);
        Enumeration enum0 = zf.entries();
        while(enum0.hasMoreElements())
        {
            ZipEntry ze = (ZipEntry)enum0.nextElement();
            if(ze == null) continue;
            InputStream is = zf.getInputStream(ze);
            //byte[] content = StreamConverter.inputStreamToBytes(is);
            MibManager.upload(ze.getName(), is);
        }
    }
    

//     public static void main(String[] args)
//     throws Exception
//     {
//         File file = new File("/home/thai/dev/MODULES/OpenPlatform/RFC1213-MIB");
//         FileInputStream fis = new FileInputStream(file);
//         byte[] content = StreamConverter.inputStreamToBytes(fis);
//         MibManager.upload("RFC1213-MIB", content);
//     }
}

