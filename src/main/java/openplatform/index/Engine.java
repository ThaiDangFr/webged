package openplatform.index;

import java.io.File;
import java.util.ArrayList;

import openplatform.database.Database;
import openplatform.database.ParamValuePair;
import openplatform.database.SQLConstraint;
import openplatform.database.dbean.DBFileBase;
import openplatform.tools.Configuration;
import openplatform.tools.Debug;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.store.FSDirectory;

import dang.cms.CmsLanguage;
// import org.apache.slide.extractor.MSExcelExtractor;



/**
 * Engine.java
 *
 * poi-2.0-RC2-20040102.jar => take it to make XLS maccro support
 *
 * http://www.getopt.org/luke/ -> for the language analyzer
 * source : http://svn.apache.org/repos/asf/lucene/java/trunk/contrib/analyzers/src/java/org/apache/lucene/analysis/
 * http://www.textmining.org/ -> word extractor
 * http://cvs.apache.org/viewcvs.cgi/jakarta-slide/src/share/org/apache/slide/extractor/ (extractor)
 * http://mail-archives.apache.org/mod_mbox/jakarta-poi-user/ (POI mailing list)
 * http://mail-archives.apache.org/mod_mbox/jakarta-poi-user/200408.mbox/%3c20040801204503.25670.qmail@web41214.mail.yahoo.com%3e (PPT)
 * Created: Fri May  6 16:48:10 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class Engine implements Constants
{
    //public static final String INDEXPATH = System.getProperty("user.home") + "/.openplatform/_INDEX";
	public static final String INDEXPATH = Configuration.getROOT() + "/_INDEX";

    public final static int BR = 0;
    public final static int CJK = 1;
    public final static int CN = 2;
    public final static int CZ = 3;
    public final static int DE = 4;
    public final static int FR = 5;
    public final static int NL = 6;
    public final static int RU = 7;
    public final static int EN = 8;

    
    private static boolean runningReIndex = false;

    static
    {
        File file = new File(INDEXPATH);
        if(!file.isDirectory())
        {
            file.mkdirs();
            try
            {
                IndexWriter writer = new IndexWriter(INDEXPATH, new OPAnalyzer(), true);
                writer.close();
            }
            catch(Exception e)
            {
                Debug.println(null, Debug.ERROR, e);
            }
        }
    }


    /**
     * Reindex the entire database
     */
    public static void reIndexAll()
        throws Exception
    {
        if(runningReIndex)
        {
        	CmsLanguage cmsLang = new CmsLanguage();
            throw new Exception(cmsLang.translate("process.running"));
        }

        try
        {
            runningReIndex = true;

            if(IndexReader.isLocked(Engine.INDEXPATH)) IndexReader.unlock(FSDirectory.getDirectory(Engine.INDEXPATH, false)); // recovery code
            
            File file = new File(INDEXPATH);
            file.delete();
            file.mkdirs();
            IndexWriter writer = new IndexWriter(INDEXPATH, new OPAnalyzer(), true);
            writer.close();
            
            
            // 1. REINDEX GED APPLICATION
            StringBuffer query = new StringBuffer("SELECT * FROM GedFiles gf INNER JOIN (select * from GedFilesVersion gfv order by gfv.version desc) gfv ON gf.id=gfv.fileId group by gf.id");
            Database db = Database.getInstance();
            
            ArrayList array = db.loadSQL(query);
            if(array == null) return;
            int len = array.size();
            for(int i=0; i<len; i++)
            {
            	ParamValuePair pvp = (ParamValuePair)array.get(i);
            	String fbId = pvp.get(DBFileBase.FILEBASEID);
            	IndexThread it = new IndexThread(fbId, APPLI_GED);
            	it.start();
            	it.join();
            }
            
            
            // 2. REINDEX OTHER APPLICATIONS
        	SQLConstraint sqlc = new SQLConstraint();
        	sqlc.setCustomWhere(DBFileBase.APPLICATION+SQLConstraint.DIFFERENT_OF+"'"+APPLI_GED+"'");
        	DBFileBase[] fb = DBFileBase.load(sqlc,null);
        	if(fb != null)
        	{
        		len = fb.length;
        		for(int i=0; i<len; i++)
        		{
        			IndexThread it = new IndexThread(fb[i].getFileBaseId(), fb[i].getApplication());
        			it.start();
        			it.join();
        		}
        	}
        }
        catch(Exception e)
        {
        	Debug.println(null, Debug.ERROR, e);
            throw e;
        }
        finally
        {
            runningReIndex = false;
        }
    }

    
    

    /**
     * Index the file
     */
    public static void index(String fileBaseId, String application)
    {
    	IndexThread it = new IndexThread(fileBaseId, application);
    	it.start();
    }


    /**
     * @return ArrayList of fileBaseId
     */
    public static final ArrayList search(String queryString, int maxHits, String application)
    {
        ArrayList array = new ArrayList();
        
        Searcher searcher = null;
        
        try
        {
        	if(IndexReader.isLocked(Engine.INDEXPATH)) IndexReader.unlock(FSDirectory.getDirectory(Engine.INDEXPATH, false)); // recovery code
        	
            searcher = new IndexSearcher(INDEXPATH);
            Analyzer analyzer = new OPAnalyzer();
   
            if(application != null) queryString = "application:\""+application+"\" AND "+queryString;

            //Query query = QueryParser.parse(queryString,"contents",analyzer);
            QueryParser queryparser = new QueryParser("contents", analyzer);
            Query query = queryparser.parse(queryString); 
            
            Hits hits = searcher.search(query);

            int hitCount = hits.length();

            int len = hitCount>maxHits?maxHits:hitCount;
            for(int i=0; i<len; i++)
            {
                Document doc = hits.doc(i);
                String fileBaseId = doc.get("fileBaseId");

                //Debug.println(null, Debug.DEBUG, "filebaseid="+fileBaseId);
                
                if(fileBaseId != null && !array.contains(fileBaseId))
                    array.add(fileBaseId);
            }
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }
        finally
        {
        	if(searcher != null)
        		try { searcher.close(); } catch(Exception e) {}
        }

        return array;
    }


    /**
     * Delete the index
     */
    public static boolean delete(String fileBaseId)
    {
        if(fileBaseId == null) return false;

        IndexReader ireader = null;
        
        try
        {
        	if(IndexReader.isLocked(Engine.INDEXPATH)) IndexReader.unlock(FSDirectory.getDirectory(Engine.INDEXPATH, false)); // recovery code
        	
            Term term = new Term("fileBaseId", fileBaseId);
            
            ireader = IndexReader.open(INDEXPATH);
            ireader.deleteDocuments(term);
            Debug.println(null, Debug.WARNING,"Desindex fileBaseId:"+fileBaseId);
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
            return false;
        }
        finally
        {
        	if(ireader != null) try { ireader.close(); } catch(Exception e) {}
        }

        return true;
    }


    public static void deferencePreviousGedVersion(String fileBaseId)
    	throws Exception
    {
        // Dereference previous version
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append("select *, gfv.fileBaseId fbi from GedFilesVersion gfv inner join (select * from GedFilesVersion gfv where gfv.fileBaseId=" +
    			fileBaseId +
    			") gfv2 on gfv2.fileId=gfv.fileId order by gfv.version desc limit 2");
    	
    	Database db = Database.getInstance();
    	ArrayList res = db.loadSQL(sb);
    	int index = 1;
    	if(res.size() == 1) index = 0;
    	
    	ParamValuePair pvp = (ParamValuePair)res.get(index);
    	String fbi = pvp.get("fbi");
    	Engine.delete(fbi);
    }
    







    public static void main(String[] args)
    	throws Exception
    {
    	reIndexAll();
    	
    	/*
    	SQLConstraint sqlc = new SQLConstraint();
    	sqlc.setCustomWhere(DBFileBase.APPLICATION+SQLConstraint.DIFFERENT_OF+"'"+APPLI_GED+"'");
    	DBFileBase[] fb = DBFileBase.load(sqlc,null);
    	int len = fb.length;
    	for(int i=0; i<len; i++) System.out.println(fb[i].getFilename());
    	*/
    }
    
}
