package openplatform.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.StringTokenizer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import openplatform.database.dbean.DBConversionCache;
import openplatform.database.dbean.DBFileBase;
import openplatform.database.dbean.DBMimeTypes;
import openplatform.database.dbeanext.MimeTypes;
import openplatform.document.FormatConverter;
import openplatform.document.Pdf;
import openplatform.document.PdfImage;
import openplatform.file.Repository;
import openplatform.image.ImageConverter;
import openplatform.image.ImageDimension;
import openplatform.tools.Debug;
import openplatform.tools.StreamConverter;

public class DownloadConvert {

	private String fileBaseId;

	private String convert;
	public static final String CONVERT_TRUE				= "true";
	public static final String CONVERT_FALSE			= "false";

	private String disposition;
	public static final String DISPOSITION_INLINE 		= "inline";
	public static final String DISPOSITION_ATTACHMENT 	= "attachment";

	private String extension;
	private String param;

	private HttpServletResponse response;



	/**
	 * 
	 * Generate image from a pdf file
	 * 
	 * @param pdfinput
	 * @param fileBaseId
	 * @param extension
	 * @param param
	 * @param filename
	 * @param application
	 * @return
	 * @throws Exception
	 */
	private static String generateImageFromPdf(InputStream pdfinput, String fileBaseId, 
			String extension, String param, String filename, String application)
		throws Exception
	{
		PdfImage pdfi = Pdf.convert2Image(pdfinput, extension);
		String res = null;
		if(pdfi != null)
		{
			DBConversionCache cc = new DBConversionCache();
			int len = pdfi.size();
			for(int i=0; i<len; i++)
			{
				//byte[] b = (byte[])pdfi.get(i);
				byte[] b = pdfi.get(i);

				ByteArrayInputStream bais = new ByteArrayInputStream(b);

				String is = Integer.toString(i);
				cc.clear();
				cc.setExtension(extension);
				cc.setParam(is);
				cc.setOrifile(fileBaseId);

				if(DBConversionCache.count(cc, null) == 0) // check that there is no other occurence
				{
					String fid = Repository.storeFile(filename, bais, false, application);
					bais.close();
					bais = null;

					if(fid != null)
					{
						cc.setCachefile(fid);
						cc.store();
						if(is.equals(param)) res = fid;
					}
				}
			}
			
			pdfi.close();
		}
		
		return res;
	}
	
	
	
	/**
	 * Return the filebaseid of the cache file associated
	 * @return
	 */
	private static String getCache(String fileBaseId, String oriextension, 
			String extension, String param, String filename, String application)
	{
		InputStream input = null;

		try
		{
			DBConversionCache cmask = new DBConversionCache();
			cmask.setExtension(extension);
			cmask.setParam(param);
			cmask.setOrifile(fileBaseId);

			DBConversionCache[] cache = DBConversionCache.load(cmask);
			if(cache != null)
			{
				String res =  cache[0].getCachefile();

				// CLEAN OBSOLETE CACHE
				int len = cache.length;
				if(len > 1)
				{
					for(int i=1; i<len; i++)
						cache[i].delete();
				}

				return res;
			}
			else
			{
				DBMimeTypes mt = MimeTypes.getInstance().getMimetypes(extension);

				if(mt == null) return null;

				if("pdf".equalsIgnoreCase(extension))
				{
					input = Repository.getStream(fileBaseId);
					InputStream newinput = FormatConverter.convert(input, oriextension, extension);

					DBConversionCache cc = new DBConversionCache();
					cc.setExtension(extension);
					cc.setParam(param);
					cc.setOrifile(fileBaseId);

					if(DBConversionCache.count(cc,null) == 0)
					{
						String fid = Repository.storeFile(filename, newinput, false, application);
						if(fid != null)
						{
							cc.setCachefile(fid);
							cc.store();
							return fid;
						}
					}

				}
				else if("image".equalsIgnoreCase(mt.getPrimaryType()))
				{
					// PARAM IS NOT NULL
					if(param != null)
					{
						/**
						 * ORI IS SOMETHING CONVERTIBLE TO PDF
						 */
						if(FormatConverter.isConvertible(oriextension,"pdf"))
						{
							cmask.clear();
							cmask.setExtension(extension);
							cmask.setOrifile(fileBaseId);
							if(DBConversionCache.count(cmask, null) != 0) return null;
							
							
							String newFileName = null;
							int idx = filename.lastIndexOf(".");
							if(idx != -1)
								newFileName = filename.substring(0,idx)+".pdf";
							else
								newFileName  = filename + ".pdf";
							
							
							String pdfFileBaseId = getCache(fileBaseId, oriextension, "pdf", null, newFileName, application);
							
							input = Repository.getStream(pdfFileBaseId);
							String res = generateImageFromPdf(input, fileBaseId, extension, param, filename, application);
							return res;
							
							//input = Repository.getStream(fileBaseId);
							//InputStream pdfinput = FormatConverter.convert(input, oriextension, "pdf");
							//String res = generateImageFromPdf(pdfinput, filename, application);
							//if(pdfinput != null) try { pdfinput.close(); } catch(Exception e) {}
							//return res;
						}
						
						
						
						/**
						 * ORI IS PDF
						 */
						else if("pdf".equalsIgnoreCase(oriextension))
						{
							cmask.clear();
							cmask.setExtension(extension);
							cmask.setOrifile(fileBaseId);
							if(DBConversionCache.count(cmask, null) != 0) return null;
							input = Repository.getStream(fileBaseId);
							return generateImageFromPdf(input, fileBaseId, extension, param, filename, application);
						}







						/**
						 * ORI IS IMAGE
						 */
						else 
						{
							int w = -1;
							int h = -1;
							StringTokenizer st = new StringTokenizer(param, "x");
							if(st.hasMoreTokens()) w = Integer.parseInt(st.nextToken());
							if(st.hasMoreTokens()) h = Integer.parseInt(st.nextToken());

							if(w != -1 && h != -1)
							{							
								input = Repository.getStream(fileBaseId);
								byte[] b = StreamConverter.inputStreamToBytes(input);


								// recalculate dimension
								int newwidth = w;
								int newheight = h;
								ImageDimension dim = ImageConverter.getDimension(b);
								int oricol = dim.getWidth();
								int orirow = dim.getHeight();
								if(oricol <= w && orirow <= h)
								{
									newwidth = oricol;
									newheight = orirow;
								}
								else
								{
									float ratiocol = (float)oricol/(float)w;
									float ratiorow = (float)orirow/(float)h;

									float max = ratiocol>ratiorow?ratiocol:ratiorow;

									newwidth = (int)(oricol/max);
									newheight = (int)(orirow/max);
								}
								Debug.println(null, Debug.DEBUG, "Asked "+w+"x"+h+", proceed "+newwidth+"x"+newheight+ ", "+extension);
								//

								byte[] newb = ImageConverter.convertResize(b,extension,newwidth,newheight);
								ByteArrayInputStream bais = new ByteArrayInputStream(newb);



								DBConversionCache cc = new DBConversionCache();
								cc.setExtension(extension);
								cc.setParam(param);
								cc.setOrifile(fileBaseId);

								if(DBConversionCache.count(cc, null) == 0)
								{
									String fid = Repository.storeFile(filename, bais, false, application);
									bais.close();
									bais = null;

									if(fid != null)
									{
										cc.setCachefile(fid);
										cc.store();
										return fid;
									}
								}
							} // if(w != -1 && h != -1)
						}
						
						
					} // if(param != null)
					
					
					/*
					else // PARAM IS NULL
					{
						input = Repository.getStream(fileBaseId);
						byte[] b = StreamConverter.inputStreamToBytes(input);
						byte[] newb = ImageConverter.convert(b, extension);
						ByteArrayInputStream bais = new ByteArrayInputStream(newb);

						DBConversionCache cc = new DBConversionCache();
						cc.setExtension(extension);
						cc.setParam(param);
						cc.setOrifile(fileBaseId);

						if(DBConversionCache.count(cc, null) == 0)
						{
							String fid = Repository.storeFile(filename, bais, false, application);
							bais.close();
							bais = null;	

							cc.setCachefile(fid);
							cc.store();
							return fid;
						}
					}
					*/
				}
				else return null;
			}
		}
		catch(Exception e)
		{
			Debug.println(null, Debug.ERROR, e);
		}
		finally
		{
			if(input != null) try { input.close(); input = null; } catch(Exception e) {}
		}

		return null;
	}


	/**
	 * DOWNLOAD THE FILE
	 * @return
	 * @throws Exception
	 */
	public String doDownload() throws Exception
	{
		InputStream input = null;
		ServletOutputStream sout = null;

		try
		{
			/**
			 * 
			 * 1) PREPARE TO SEND
			 * 
			 */		
			if(fileBaseId == null) 
				throw new Exception("FileBaseId is null");

			if(convert == null)
				throw new Exception("Convert is null");

			if(disposition == null)
				throw new Exception("Disposition is null");


			DBFileBase fbmask = new DBFileBase();
			fbmask.setFileBaseId(fileBaseId);
			DBFileBase fb = DBFileBase.loadByKey(fbmask);

			if(fb == null) 
				throw new Exception("DBFileBase is null");

			String filename = null;
			String idToDownload = null;


			if(CONVERT_FALSE.equalsIgnoreCase(convert))
			{
				filename = fb.getFilename();
				idToDownload = fileBaseId;

				int idx = filename.lastIndexOf(".");
				if(idx != -1) extension = filename.substring(idx+1);
			}
			else if(CONVERT_TRUE.equalsIgnoreCase(convert))
			{
				if(extension == null)
					throw new Exception("Extension is null");

				String oriextension = null;
				String oriprefix = null;
				String tmp = fb.getFilename();
				int idx = tmp.lastIndexOf(".");
				if(idx != -1)
				{
					oriextension = tmp.substring(idx+1);
					oriprefix= tmp.substring(0, idx);
				}

				filename = oriprefix + "." + extension;

				idToDownload = getCache(fileBaseId, oriextension, extension, param, filename, fb.getApplication());
			}


			///////// MIMETYPES /////////
			DBMimeTypes mimetypes = null;
			if(extension == null)
				mimetypes = MimeTypes.getInstance().DEFAULT_MIME_TYPE_CLASS;
			else
				mimetypes = MimeTypes.getInstance().getMimetypes(extension);

			if(mimetypes != null)
				response.setContentType(mimetypes.getPrimaryType()+"/"+mimetypes.getSecondaryType());
			///////// MIMETYPES /////////



			///////// DISPOSITION /////////
			if(DISPOSITION_INLINE.equals(disposition))
			{
				response.setHeader("Content-Disposition","inline; filename=\""+filename+"\"");
			}
			else if(DISPOSITION_ATTACHMENT.equals(disposition))
			{
				response.setHeader("Content-Disposition","attachment; filename=\""+filename+"\"");
			}
			///////// DISPOSITION /////////







			/**
			 * 
			 * 2) SEND THE FILE
			 * 
			 */
			if(idToDownload == null)
				throw new Exception("IdToDownload is null");

			if(Debug.DEBUGON)
			{
				Debug.println(this, Debug.DEBUG, "FileBaseId:"+fileBaseId+
						" Convert:"+convert+" Disposition:"+disposition+
						" Extension:"+extension+" Param:"+param+
						" Filename:"+filename+" IdToDownload:"+idToDownload+ 
						" MimeTypes:"+mimetypes);
			}




			int  readlen;
			byte buffer[] = new byte[StreamConverter.DOWNLOAD_BUFFER_SIZE];
			input = Repository.getStream(idToDownload);
			sout = response.getOutputStream();

			while((readlen = input.read(buffer)) != -1)
			{
				// throws IOException: broken pipe when download is canceled.
				sout.write(buffer, 0, readlen);
			} 


		}
		catch(IOException se)
		{
			Debug.println(this, Debug.DEBUG, "Socket closed");
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		catch(Exception e)
		{
			Debug.println(this, Debug.ERROR, e);
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		finally
		{
			try
			{
				if(sout != null)
				{
					sout.flush();
					sout.close();
				}

				if(input != null) input.close();

				response.flushBuffer();
			}
			catch(Exception e) {}
		}

		return null;
	}




	////////////////////////////////////




	public String getConvert() {
		return convert;
	}
	public void setConvert(String convert) {
		this.convert = convert;
	}
	public String getDisposition() {
		return disposition;
	}
	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getFileBaseId() {
		return fileBaseId;
	}
	public void setFileBaseId(String fileBaseId) {
		this.fileBaseId = fileBaseId;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}


}
