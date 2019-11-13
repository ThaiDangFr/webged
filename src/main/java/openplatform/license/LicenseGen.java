package openplatform.license;

import java.io.File;

import openplatform.tools.StreamConverter;

/**
 * LicenseGen.java
 *
 *
 * Created: Fri Jun 24 15:33:29 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class LicenseGen implements Constants
{
	/*
    public static void main(String[] args)
        throws Exception
    {
        if(args==null || args.length!=2)
        {
            System.out.println("Syntax is [inputfile] [outputfile]");
            return;
        }

        File input = new File(args[0]);
        File output = new File(args[1]);

        byte[] b = StreamConverter.fileToBytes(input);
        String dec = new String(b, "UTF-8");
        DesEncrypter des = new DesEncrypter(PHRASE);
        String enc = des.encrypt(dec);
        StreamConverter.writeToDisk(output, enc.getBytes("UTF-8"));
    }
    */
	
	public static void main(String[] args)
	throws Exception
	{
		File ori = new File("ori");
		File key = new File("key");
		
		File[] subOri = ori.listFiles();
		if(subOri != null)
		{
			int len = subOri.length;
			for(int i=0; i<len; i++)
			{
				File output = new File(key,subOri[i].getName()+".key");
		        byte[] b = StreamConverter.fileToBytes(subOri[i]);
		        String dec = new String(b, "UTF-8");
		        DesEncrypter des = new DesEncrypter(PHRASE);
		        String enc = des.encrypt(dec);
		        StreamConverter.writeToDisk(output, enc.getBytes("UTF-8"));
			}
		}
	}
}
