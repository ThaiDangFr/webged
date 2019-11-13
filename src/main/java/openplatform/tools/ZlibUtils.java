package openplatform.tools;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.ZInputStream;
import com.jcraft.jzlib.ZOutputStream;

public class ZlibUtils {

	
	
	public static void main(String[] args)
	throws Exception
	{
		java.io.FileInputStream fis = new java.io.FileInputStream("F:/SVN-MODULES/trunk/Testing/cleartext1.txt");
		
		long t1 = 0;
		long t2 = 0;
		
		t1 = System.currentTimeMillis();
		InputStream in = compress(fis);
		
		/*
		byte[] b = new byte[1024];
		while(in.read(b) != -1)
		{
			;
		}
		*/
		
		System.out.println(Digest.MD5(in));
		
		t2 = System.currentTimeMillis();
		System.out.println("COMPRESSING:"+(t2-t1)+"ms");

		
		/*
		System.out.println("compressing");
		InputStream in = compress(new ByteArrayInputStream("coucou ceci est un test".getBytes()));
		System.out.println("decompressing");
		InputStream out = decompress(in);
		byte[] b = StreamConverter.inputStreamToBytes(out);
		System.out.println("plain text="+new String(b));
		System.out.println("done.");
		*/
	}

	

	
	
	public static InputStream compress(InputStream input)
	{		
		ZOutputStream zos = null;
		
		PipedOutputStream out = null;
		PipedInputStream in = new PipedInputStream();
		
		
		try
		{
			out = new PipedOutputStream(in);
			zos = new ZOutputStream(out, JZlib.Z_BEST_SPEED);		
			
			Thread t = new Thread(new Zlibthread(zos, input));
			t.start();
		}
		catch(Exception e)
		{
            Debug.println(null, Debug.ERROR, e);
		}
		
		return in;
	}
	
	
	/**
	 * Decompress
	 * @param input
	 * @return
	 */
	public static InputStream decompress(InputStream input)
	{
		try
		{
			ZInputStream zis = new ZInputStream(input);
			return zis;
		}
		catch(Exception e)
		{
            Debug.println(null, Debug.ERROR, e);
            return null;
		}
	}
}



/**
 * 
 * Use that thread because :
 * The PipedInputStream classes use an internal buffer of 1024 bytes.
 * This buffer is used to store characters written to it, before they are read from the input stream.
 * So as soon as it contains more than 1024 characters awaiting to be read it will block.
 * This cannot be changed and is a limitation of these classes.
 *
 */
class Zlibthread implements Runnable
{
	private static final int BUFFER_SIZE = StreamConverter.DOWNLOAD_BUFFER_SIZE;
	private ZOutputStream zout;
	private InputStream input;
	
	public Zlibthread(ZOutputStream zout, InputStream input)
	{
		this.zout = zout;
		this.input = input;
	}
	
	public void run()
	{
		try
		{
			BufferedInputStream binput = new BufferedInputStream(input);
			int readb;
			byte[] buff = new byte[BUFFER_SIZE];
			while((readb=binput.read(buff)) != -1)
				zout.write(buff, 0, readb);
		}
		catch(Exception e)
		{
			Debug.println(this, Debug.ERROR, e);
		}
		finally
		{
			try
			{
				if(zout!=null) zout.close();
				if(input!=null) input.close();
			}catch(Exception e) {}
		}
	}
}
