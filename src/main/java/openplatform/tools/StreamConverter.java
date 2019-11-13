package openplatform.tools;

import java.io.*;



public abstract class StreamConverter
{
    public static final int DOWNLOAD_BUFFER_SIZE = 8*1024;
    
    
    public static boolean writeToDisk(File file, byte[] buff)
    {
        try
        {
            int readb;
            FileOutputStream output = new FileOutputStream(file, false);
            output.write(buff, 0, buff.length);
            output.close();
            return true;
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }
        
        return false;
    }


    public static boolean writeToDisk(File file, InputStream input)
    {
        try
        {
            int readb;
            byte[] buff = new byte[DOWNLOAD_BUFFER_SIZE];
            FileOutputStream output = new FileOutputStream(file, false);

            while((readb=input.read(buff)) != -1)
            {
                output.write(buff, 0, readb);
            }

            output.close();
            input.close();
            return true;
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }
        
        return false;
    }


    public static void bytesToFile(byte[] bytes, File file)
    {
        try
        {
            File parent = file.getParentFile();
            if(parent!=null && !parent.exists())
                parent.mkdirs();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.close();
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }
    }


    public static byte[] fileToBytes(File file)
    {
        try
        {
            FileInputStream fis = new FileInputStream(file);
            return inputStreamToBytes(fis);
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
            return new byte[0];
            //return null;
        }
    }



    public static byte[] inputStreamToBytes(InputStream input)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try
        {
            
            byte[] buffer = new byte[DOWNLOAD_BUFFER_SIZE];
            
            int readlen;
            
            while((readlen = input.read(buffer)) != -1)
            {
                baos.write(buffer, 0, readlen);
            }

            baos.close();
            input.close();

            return baos.toByteArray();
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
            //return null;
            return new byte[0];
        }
        finally
        {            
            baos=null;
        }
    }
    
    
    /**
     *  Convert any stream (idealy in buffer) 
     * @param input
     * @return
     */
    public static InputStream toDiskStream(InputStream input)
    {
    	FileOutputStream fos = null;
    	
        try
        {
        	File temp = File.createTempFile("streamconverter_",".tmp");
        	fos = new FileOutputStream(temp);
            
            byte[] buffer = new byte[DOWNLOAD_BUFFER_SIZE];
            
            int readlen;
            
            while((readlen = input.read(buffer)) != -1)
            {
                fos.write(buffer, 0, readlen);
            }

            FileInputStream fis = new FileInputStream(temp);
            return fis;

        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
            return input;

        }
        finally
        {            
        	try
        	{
        		if(fos != null) fos.close();
        		if(input != null) input.close();
        	}
        	catch(Exception e) {}
        }
    }
}








