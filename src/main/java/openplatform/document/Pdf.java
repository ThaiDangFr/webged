package openplatform.document;

import java.io.InputStream;

import openplatform.tools.Debug;
import openplatform.tools.StreamConverter;

public class Pdf {

	public static PdfImage convert2Image(InputStream input, String extension)
	{		
		try
		{
			byte[] b = StreamConverter.inputStreamToBytes(input);
			return new PdfImage(b,extension);
		}
		catch(Exception e)
		{
			Debug.println(null, Debug.ERROR, e);
			return null;
		}
	}
}
