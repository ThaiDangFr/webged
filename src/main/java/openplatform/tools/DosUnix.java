package openplatform.tools;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class DosUnix {
	
	
    
    public static InputStream dos2Unix(InputStream input)
    	throws Exception
    {
    	PipedOutputStream out = new PipedOutputStream();
    	PipedInputStream in = new PipedInputStream(out);
    	
    	
    	Thread t = new Thread(new Dos2UnixThread(out, input));
		t.start();
		
    	return in;
    }
    

    
    
    public static InputStream unix2Dos(InputStream input)
    	throws Exception
    {
    	PipedOutputStream out = new PipedOutputStream();
    	PipedInputStream in = new PipedInputStream(out);
    	
    	Thread t = new Thread(new Unix2DosThread(out, input));
		t.start();

    	return in;
    }
    

    
    
    /*
    public static void main(String[] args)
    	throws Exception
    {
    	
    	System.out.println("63292WDF.txt");
    	FileInputStream fis = new FileInputStream("F:/63292WDF.txt");
    	int b;
    	while((b=fis.read())!=-1)
    	{
    		System.out.print(b); System.out.print(" ");
    	}
    	System.out.println("");
    	
    	
    	
    	System.out.println("63292WDF-dos.txt");
    	FileInputStream fis3 = new FileInputStream("F:/63292WDF-dos.txt");
    	while((b=fis3.read())!=-1)
    	{
    		System.out.print(b); System.out.print(" ");
    	}
    	System.out.println("");
    	
    	
    	System.out.println("my unix2dos");
    	InputStream unix2dos = unix2Dos(new FileInputStream("F:/63292WDF.txt"));
    	while((b=unix2dos.read())!=-1)
    	{
    		System.out.print(b); System.out.print(" ");
    	}
    	System.out.println("");
    }
    */
    
}






/**
 * 
 * La conversion unix2dos est dans un thead séparé
 * car l'écriture dans un pipedoutputstream doit être séparé
 * de la lecture du pipedinputstream correspondant
 * @author thai
 *
 */
class Unix2DosThread implements Runnable
{
	private OutputStream output;
	private InputStream input;
	
	public Unix2DosThread(OutputStream output, InputStream input)
	{
		this.output = output;
		this.input = input;
	}
	
	public void run()
	{
		try
		{
	    	int b;
	    	int last = -1;
	    	while((b=input.read()) != -1)
	    	{
	    		if(last != 13 && b == 10)
	    		{
	    			output.write(13);
	    			output.write(10);
	    			last = 10;
	    			continue;
	    		}
	    		
	    		output.write(b);
	    		last = b;

	    	}
		}
		catch(Exception e)
		{
			Debug.println(this, Debug.ERROR, e);
		}
		finally
		{
			try
			{
				if(output!=null) output.close();
				if(input!=null) input.close();
			}catch(Exception e) {}
		}
	}
}









class Dos2UnixThread implements Runnable
{
	private OutputStream output;
	private InputStream input;
	
	public Dos2UnixThread(OutputStream output, InputStream input)
	{
		this.output = output;
		this.input = input;
	}
	
	public void run()
	{
		try
		{
	    	
	    	int b;
	    	int last = -1;
	    	while((b=input.read()) != -1)
	    	{
	    		if(last == 13 && b == 13)
	    		{
	    		}
	    		else
	    			output.write(b);
	    		
	    		last = b;
	    	}
		}
		catch(Exception e)
		{
			Debug.println(this, Debug.ERROR, e);
		}
		finally
		{
			try
			{
				if(output!=null) output.close();
				if(input!=null) input.close();
			}catch(Exception e) {}
		}
	}
}  

