package openplatform.document;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import openplatform.tools.Debug;

import org.jpedal.PdfDecoder;

public class PdfImage {

	private PdfDecoder decode_pdf;
	private String extension;
	
	public PdfImage(byte[] b, String extension)
		throws Exception
	{
		this.extension = extension;
		decode_pdf = new PdfDecoder(true);
		decode_pdf.openPdfArray(b);
	}
	
	public byte[] get(int i)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = null;
		try
		{
			int idx = i+1;
			BufferedImage image_to_save = decode_pdf.getPageAsImage(idx);
			ImageIO.write(image_to_save, extension, baos);
			b = baos.toByteArray();
		}
		catch(Exception e)
		{
			Debug.println(this, Debug.ERROR, e);
		}
		finally
		{
			try { baos.flush(); baos.close(); } catch(Exception e) { }
		}
		
		return b;
	}
	
	public int size() { return decode_pdf.getPageCount(); }
	public void close() { decode_pdf.closePdfFile(); }
}
