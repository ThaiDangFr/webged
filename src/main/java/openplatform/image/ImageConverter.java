package openplatform.image;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import openplatform.tools.Debug;
import openplatform.tools.StreamConverter;

public class ImageConverter {

	public static String BMP = "BMP";
	public static String GIF = "GIF";
	public static String JPEG = "JPG";
	public static String PNG = "PNG";
	public static String PNM = "PNM";
	public static String TIFF = "TIFF";
	
	
    public static ImageDimension getDimension(byte[] data)
    {
    	ByteArrayInputStream bais = null;
    	BufferedImage bi = null;
    	
        try
        {
        	bais = new ByteArrayInputStream(data);
			bi = ImageIO.read(bais);
        
        	ImageDimension id = new ImageDimension();
        	id.setHeight(bi.getHeight());
        	id.setWidth(bi.getWidth());
        	return id;
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
            return null;
        }
        finally
        {
        	if(bais != null) try { bais.close(); } catch(Exception e) {}
        	bi = null;
        }
    }
	
	public static byte[] convertResize(byte[] data, String formatOut, int width, int height)
	{
    	ByteArrayInputStream bais = null;
    	BufferedImage bi = null;
    	ByteArrayOutputStream baos = null;
    	
		try
		{
			bais = new ByteArrayInputStream(data);
			bi = ImageIO.read(bais);
			
			Image newImg = bi.getScaledInstance(width,height,Image.SCALE_SMOOTH);
			BufferedImage bim = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
			bim.createGraphics().drawImage(newImg, 0, 0, null);
			
			
			baos = new ByteArrayOutputStream();
			ImageIO.write(bim, formatOut, baos);
			return baos.toByteArray();
		}
		catch(Exception e)
		{
			Debug.println(null, Debug.ERROR, e);
			return null;
		}
        finally
        {
        	if(bais != null) try { bais.close(); } catch(Exception e) {}
        	if(baos != null) try { baos.close(); } catch(Exception e) {}
        	bi = null;
        }
	}
	
	
	public static byte[] convert(byte[] data, String formatOut)
	{
    	ByteArrayInputStream bais = null;
    	BufferedImage bi = null;
    	ByteArrayOutputStream baos = null;
    	
		try
		{
			bais = new ByteArrayInputStream(data);
			bi = ImageIO.read(bais);
			
			baos = new ByteArrayOutputStream();
			ImageIO.write(bi, formatOut, baos);
			return baos.toByteArray();
		}
		catch(Exception e)
		{
			Debug.println(null, Debug.ERROR, e);
			return null;
		}
        finally
        {
        	if(bais != null) try { bais.close(); } catch(Exception e) {}
        	if(baos != null) try { baos.close(); } catch(Exception e) {}
        	bi = null;
        }
	}
	
	
	public static byte[] resize(byte[] data, int width, int height)
	{
		return convertResize(data,getFormat(data),width,height);
	}
	
	
	public static String getFormat(byte[] data)
	{
		ByteArrayInputStream bais = null;
		ImageInputStream iis = null;
		
		try
		{
			// Create an image input stream on the image
			bais = new ByteArrayInputStream(data);
			iis = ImageIO.createImageInputStream(bais);
			
			// Find all image readers that recognize the image format
			Iterator iter = ImageIO.getImageReaders(iis);
			if (!iter.hasNext()) {
				// No readers found
				return null;
			}
			
			// Use the first reader
			ImageReader reader = (ImageReader)iter.next();

			// Return the format name
			return reader.getFormatName();
		}
		catch (IOException e)
		{
			Debug.println(null, Debug.ERROR, e);
			return null;
		}
		finally
		{
        	if(bais != null) try { bais.close(); } catch(Exception e) {}
        	if(iis != null) try { iis.close(); } catch(Exception e) {}
		}
	}
	
	
	
	
	/*
	public static boolean canRead(String format){
		   boolean type = ImageIO.getImageReadersByFormatName(format).hasNext();
		   boolean mime = ImageIO.getImageReadersByMIMEType(format).hasNext();
		   boolean suffixe = ImageIO.getImageReadersBySuffix(format).hasNext();
		   String infos = "Informations lecture "+format+" : ";
		   infos+="Format image = "+type+", ";
		   infos+="MIME = "+mime+", ";
		   infos+="Suffixe fichier = "+suffixe;
		   System.out.println(infos);
		   return type||mime||suffixe;
		}
		public static boolean canWrite(String format){
		   boolean type = ImageIO.getImageWritersByFormatName(format).hasNext();
		   boolean mime = ImageIO.getImageWritersByMIMEType(format).hasNext();
		   boolean suffixe = ImageIO.getImageWritersBySuffix(format).hasNext();
		   String infos = "Informations écriture "+format+" : ";
		   infos+="Format image = "+type+", ";
		   infos+="MIME = "+mime+", ";
		   infos+="Suffixe fichier = "+suffixe;
		   System.out.println(infos);
		   return type||mime||suffixe;
		}
		*/
		
	public static void main(String[] args)
	{
		/*
		canRead("gif");
		canRead("jpg");
		canRead("bmp");
		canRead("wbmp");
		canRead("png");
		canRead("image/jpeg");

		canWrite("gif");
		canWrite("jpg");
		canWrite("bmp");
		canWrite("wbmp");
		canWrite("png");
		canWrite("image/jpeg");
*/
		byte[] b = StreamConverter.fileToBytes(new File("D:/Priv/TRCmitch300_N5.gif"));
		//byte[] out = convert(b, TIFF);
		byte[] out = resize(b, 100,100);
		StreamConverter.bytesToFile(out, new File("D:/Priv/out"));
		
	}
}
