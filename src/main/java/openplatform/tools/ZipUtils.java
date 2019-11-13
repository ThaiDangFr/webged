package openplatform.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * ZipUtils.java
 *
 *
 * Created: Tue Oct 11 16:26:54 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class ZipUtils 
{

    public static byte[] zipOne(String name, byte[] data)
    {
        ByteArrayOutputStream baos = null;
        ZipOutputStream zos = null;

        try
        {
        	long t1 = System.currentTimeMillis();
            baos = new ByteArrayOutputStream();
            zos = new ZipOutputStream(baos);
            zos.putNextEntry(new ZipEntry(name));
            zos.write(data, 0, data.length);
            zos.closeEntry();
            zos.close();
            baos.close();
            long t2 = System.currentTimeMillis();
            
            long t3 = System.currentTimeMillis();
            byte[] res = baos.toByteArray();
            long t4 = System.currentTimeMillis();
            
            System.out.println("ZIP1:"+(t2-t1));
            System.out.println("ZIP2:"+(t4-t3));
            
            return res;
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
            return null;
        }
    }
 

	
	
    public static byte[] extractFirst(byte[] data)
    {
        ByteArrayInputStream bais = null;
        ZipInputStream zis = null;

        try
        {
            bais = new ByteArrayInputStream(data);
            zis = new ZipInputStream(bais);
            ZipEntry ze = zis.getNextEntry();
            return StreamConverter.inputStreamToBytes(zis);
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
            return null;
        }
        finally
        {
            try
            {
                if(bais != null) bais.close();
                if(zis != null) zis.close();
            }
            catch(Exception e) {}
        }        
    }

    
    public static byte[] extract(String fileName, byte[] data)
    {
        ByteArrayInputStream bais = null;
        ZipInputStream zis = null;

        try
        {
            bais = new ByteArrayInputStream(data);
            zis = new ZipInputStream(bais);

            while(true)
            {
                ZipEntry ze = zis.getNextEntry();
                if(ze == null) return null;
                if(fileName.equals(ze.getName())) break;         
            }

            return StreamConverter.inputStreamToBytes(zis);
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
            return null;
        }
        finally
        {
            try
            {
                if(bais != null) bais.close();
                if(zis != null) zis.close();
            }
            catch(Exception e) {}
        }         
    }


    

    
    /*
    public static void main(String[] args)
    {
        //byte[] b = ZipUtils.zipOne("snmpwalk.log", StreamConverter.fileToBytes(new File("/home/thai/snmpwalk.log")));
        //StreamConverter.writeToDisk(new File("/home/thai/snmpwalk.zip"), b);

        //byte[] ori = ZipUtils.extractFirst(b);
        //StreamConverter.writeToDisk(new File("/home/thai/coco.txt"), ori);

        //         byte[] b = ZipUtils.extract("meta.xml", StreamConverter.fileToBytes(new File("/home/thai/archivage_mail.sxw")));
        //         if(b!=null)
        //             System.out.println(new String(b));
    }
    */
}
