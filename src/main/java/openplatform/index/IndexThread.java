package openplatform.index;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.text.rtf.RTFEditorKit;

import openplatform.database.dbean.DBFileBase;
import openplatform.database.dbean.DBTrace;
import openplatform.file.Repository;
import openplatform.tools.Debug;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;
import org.textmining.text.extraction.WordExtractor;

public class IndexThread extends Thread {

	private String fileBaseId;
	private String application;
	
	public IndexThread(String fileBaseId, String application)
	{
		this.fileBaseId = fileBaseId;
		this.application = application;
	}
	
	
	public void run()
	{
		long indextime1=System.currentTimeMillis();
		long indextime2=0;
		Debug.println(this, Debug.DEBUG, "Starting to index fileBaseId:"
				+fileBaseId+" application:"+application);
		
		IndexWriter writer = null;
		Reader reader = null;
		
        try
        {
            if(IndexReader.isLocked(Engine.INDEXPATH)) IndexReader.unlock(FSDirectory.getDirectory(Engine.INDEXPATH, false)); // recovery code

            writer = new IndexWriter(Engine.INDEXPATH, new OPAnalyzer(), false);
            writer.setUseCompoundFile(true);
            
            reader = reader(fileBaseId);
                
            if(reader != null)
            {
                Document doc = new Document();
                
                if(application != null)
                	doc.add(new Field("application", application, Field.Store.YES, Field.Index.UN_TOKENIZED)); // store the field as a searchable item 
                
                doc.add(new Field("fileBaseId", fileBaseId, Field.Store.YES, Field.Index.UN_TOKENIZED)); // store the field as a searchable item
                doc.add(new Field("contents", reader)); // store the field as a searchable item by being tokenized
                
                writer.addDocument(doc);
                writer.optimize();
                
                Debug.println(null, Debug.DEBUG, "FileBaseId="+fileBaseId+" indexed ; Application="+application+" ; INDEXPATH="+Engine.INDEXPATH);
                writeTrace(true, fileBaseId);
            }
            else
            {  
                writeTrace(false, fileBaseId);
                Debug.println(null, Debug.WARNING, "FileBaseId="+fileBaseId+" not indexed because the file type is not recognized");
            }
            
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }
        catch(OutOfMemoryError oe)
    	{
    		Debug.println(null, Debug.ERROR, oe);
    	}
        finally
        {
        	if(writer != null)
        		try { writer.close(); } catch(Exception e) {} 		
        		
            if(reader != null)
            	try { reader.close(); } catch(Exception e) {} 	
        }

        indextime2=System.currentTimeMillis();
        long diffindextime = indextime2-indextime1;
       
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss:SSS");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        
        String diffindex = format.format(new Date(diffindextime));
        
		Debug.println(this, Debug.DEBUG, "Finished indexation of fileBaseId:"
				+fileBaseId+" application:"+application+" in "+diffindex);
	}
	
	
    private void writeTrace(boolean result, String fileBaseId)
    throws Exception
{
    if(fileBaseId == null) return;


    DBFileBase fmask = new DBFileBase();
    fmask.setFileBaseId(fileBaseId);
    DBFileBase f = DBFileBase.loadByKey(fmask);
    if(f == null) return;

    DBTrace trace = new DBTrace();
    trace.setDate((new Timestamp(System.currentTimeMillis())).toString());
    trace.setObject(f.getFilename());
    trace.setAction("Index");

    if(result)
        trace.setStatusMsg(DBTrace.STATUSMSG_OK);
    else
        trace.setStatusMsg(DBTrace.STATUSMSG_FAIL);

    trace.store();
}
	
	
    /**
     * @return reader according to the file extension
     */
    private Reader reader(String fileBaseId)
    {
        DBFileBase fbmask = new DBFileBase();
        fbmask.setFileBaseId(fileBaseId);
        DBFileBase fb = DBFileBase.loadByKey(fbmask);
        String filename = fb.getFilename();

        String ext = null;
        int index = filename.lastIndexOf(".");
        if(index != -1)
        {
            ext = filename.substring(index+1);
        }

        if(ext == null) return null;

        try
        {
            if(ext.equalsIgnoreCase("txt"))
            {      
            	java.io.InputStream input = Repository.getStream(fileBaseId);
                Reader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
                return reader;
            }
            else if(ext.equalsIgnoreCase("rtf"))
            {
            	java.io.InputStream input = Repository.getStream(fileBaseId);
                RTFEditorKit aKit = new RTFEditorKit();
                javax.swing.text.Document aDocument = aKit.createDefaultDocument();
                aKit.read(input, aDocument, 0 );
                String txt = aDocument.getText(0, aDocument.getLength());
                input.close();
                return new StringReader(txt);
            }
            else if(ext.equalsIgnoreCase("doc"))
            {
            	java.io.InputStream input = Repository.getStream(fileBaseId);
                WordExtractor we = new WordExtractor();
                String txt = we.extractText(input);
                input.close();
                return new StringReader(txt);
            }
            else if(ext.equalsIgnoreCase("pdf"))
            {
            	java.io.InputStream input = Repository.getStream(fileBaseId);
                PDDocument document = PDDocument.load(input);

                try
                {
                    if(document.isEncrypted())
                        Debug.println(null, Debug.WARNING, "Can't index because the pdf is encrypted");
                    else
                    {
                        PDFTextStripper stripper = new PDFTextStripper();
                        String txt = stripper.getText(document);
                        return new StringReader(txt);
                    }
                }
                catch(Exception e)
                {
                    Debug.println(null, Debug.ERROR, e);
                }
                finally
                {
                    document.close();
                    input.close();
                }
            }
            else if(ext.equalsIgnoreCase("ppt"))
            {
            	java.io.InputStream input = Repository.getStream(fileBaseId);
                String txt = PptExtractor.extractText(input);
                input.close();
                return new StringReader(txt);
            }
            else if(ext.equalsIgnoreCase("xls"))
            {
            	java.io.InputStream input = Repository.getStream(fileBaseId);
                String txt = ExcelExtractor.extractText(input);
                input.close();
                return new StringReader(txt);
            }
            else if(ext.equalsIgnoreCase("xml"))
            {
            	java.io.InputStream input = Repository.getStream(fileBaseId);
                String txt = XMLExtractor.extractText(input);
                input.close();
                return new StringReader(txt);
            }
            else if(ext.equalsIgnoreCase("stw")
                    || ext.equalsIgnoreCase("sxw")
                    || ext.equalsIgnoreCase("sxc")
                    || ext.equalsIgnoreCase("stc")
                    || ext.equalsIgnoreCase("sxi")
                    || ext.equalsIgnoreCase("sti")
                    || ext.equalsIgnoreCase("sxm")
                    || ext.equalsIgnoreCase("sxg")
                    || ext.equalsIgnoreCase("sxd")
                    || ext.equalsIgnoreCase("std")

                    || ext.equalsIgnoreCase("odt")
                    || ext.equalsIgnoreCase("ods")
                    || ext.equalsIgnoreCase("odp")
                    || ext.equalsIgnoreCase("odg")
                    || ext.equalsIgnoreCase("odc")
                    || ext.equalsIgnoreCase("odf")
                    || ext.equalsIgnoreCase("odb")
                    || ext.equalsIgnoreCase("odi")
                    || ext.equalsIgnoreCase("odm")
                    || ext.equalsIgnoreCase("ott")
                    || ext.equalsIgnoreCase("ots")
                    || ext.equalsIgnoreCase("otp")
                    || ext.equalsIgnoreCase("otg")
                    || ext.equalsIgnoreCase("otc")
                    || ext.equalsIgnoreCase("otf")
                    || ext.equalsIgnoreCase("oti")
                    || ext.equalsIgnoreCase("oth")
                )
            {
            	java.io.InputStream input = Repository.getStream(fileBaseId);
                String txt = OOExtractor.extractText(input);
                input.close();
                return new StringReader(txt);
            }

        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }

        Debug.println(null, Debug.WARNING, "extension:"+ext+" not supported for indexing or an error occured");
        return null;
    }
}
