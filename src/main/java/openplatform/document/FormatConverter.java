package openplatform.document;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;


import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.DocumentFormatRegistry;
import com.artofsolving.jodconverter.XmlDocumentFormatRegistry;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

import openplatform.tools.Debug;
import openplatform.tools.DosUnix;

public class FormatConverter {

	
	/**
	 * 
	 * Dont forget to launch : soffice -headless -accept="socket,port=8100;urp;"
	 * 
	 * Supported conversion : 
	   Microsoft Word to PDF
	   Microsoft Word to RTF
	   Microsoft Word to OpenOffice Text
	   OpenOffice Text to PDF
	   OpenOffice Text to RTF
	   OpenOffice Text to Microsoft Word
	   Microsoft Excel to PDF
	   Microsoft Excel to OpenOffice Spreadsheet
	   OpenOffice Spreadsheet to PDF
	   OpenOffice Spreadsheet to Microsoft Excel
	   Microsoft PowerPoint to PDF
	   Microsoft PowerPoint to Flash
	   Microsoft PowerPoint to OpenOffice Presentation
	   OpenOffice Presentation to PDF
	   OpenOffice Presentation to Flash
	   OpenOffice Presentation to Microsoft PowerPoint
	 * 
	 * @param input
	 * @param formatIn
	 * @param formatOut
	 * @return
	 */
	public static InputStream convert(InputStream input, String formatIn, String formatOut)
	{
		try
		{			
			PipedOutputStream out = new PipedOutputStream();
	    	PipedInputStream in = new PipedInputStream(out);
	    	
	    	Thread t = new Thread(new FormatConverterThread(out, input,formatIn,formatOut));
			t.start();
			return in;
		}
		catch(Exception e)
		{
			Debug.println(null, Debug.ERROR, e);
			return null;
		}
	}
	
	
	public static boolean isConvertible(String ori, String dest)
	{
		DocumentFormatRegistry registry = new XmlDocumentFormatRegistry();
		DocumentFormat oridf = registry.getFormatByFileExtension(ori); 
		DocumentFormat destdf = registry.getFormatByFileExtension(dest);
		
		if(oridf == null || destdf == null) return false;
		else return destdf.isExportableFrom(oridf.getFamily()); 
	}
}





class FormatConverterThread implements Runnable
{
	private OutputStream output;
	private InputStream input;
	private String formatIn;
	private String formatOut;
	
	public FormatConverterThread(OutputStream output, InputStream input, String formatIn, String formatOut)
	{
		this.output = output;
		this.input = input;
		this.formatIn = formatIn;
		this.formatOut = formatOut;
	}
	
	public void run()
	{
		try
		{
			Debug.println(this, Debug.DEBUG, "Starting conversion: "+formatIn+" to "+formatOut);
			
			DocumentFormatRegistry registry = new XmlDocumentFormatRegistry();
			DocumentFormat dfIn = registry.getFormatByFileExtension(formatIn); 
    		DocumentFormat dfOut = registry.getFormatByFileExtension(formatOut);
			
			OpenOfficeConnection connection = new SocketOpenOfficeConnection();
			
			
			
			try
			{
				connection.connect();
			}
			catch(Exception e)
			{
				Debug.println(null, Debug.ERROR, e);
			}
			
			try
			{
				DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
				
				if("txt".equalsIgnoreCase(formatIn))
					converter.convert(DosUnix.unix2Dos(input), dfIn, output, dfOut);
				else
					converter.convert(input, dfIn, output, dfOut);
			}
			catch(Exception e)
			{
				Debug.println(null, Debug.ERROR, e);
			}
			finally
			{
				connection.disconnect();
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