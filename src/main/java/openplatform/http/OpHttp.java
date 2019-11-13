package openplatform.http;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import openplatform.database.dbeanext.MimeTypes;
import openplatform.index.OPAnalyzer;
import openplatform.tools.Configuration;
import openplatform.tools.Debug;
import openplatform.tools.StreamConverter;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.lucene.index.IndexWriter;

// see http://jakarta.apache.org/commons/httpclient/tutorial.html


/**
 * Class OpHttp
 *
<pre>
        Sample :

        OpHttp http = new OpHttp();
        HttpRequest req = new HttpRequest();
        req.setURL("http://acacias/openplatform/user/pc/index.jsp");
        req.addParamValuePair("username","thai");
        req.addParamValuePair("password","test");
        HttpResponse resp = http.POST(req);

        System.out.println(resp.getDataAsString("ISO-8859-1"));

        req.clearParamValues();
        req.setURL("http://acacias/openplatform/user/pc/bugreport.jsp");
        resp = http.GET(req); // the session is maintained
        System.out.println(resp.getDataAsString("ISO-8859-1"));

        Enumeration enum = resp.getHeaderNames();
        while(enum.hasMoreElements())
        {
            String name = (String)enum.nextElement();
            System.out.println(name + "=" + resp.getHeader(name));
        }
</pre>

 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class OpHttp
{
    private static final String USER_AGENT=
    //"Mozilla/5.0 (X11; U; Linux i686; fr-FR; rv:1.7.5) Gecko/20041108 Firefox/1.0";

    "Mozilla//4.0 (compatible; MSIE 6.0; X11; Linux i686) OpenPlatform";

    //public final static String tempDir = System.getProperty("user.home") + "/.openplatform/_TMP";
    public final static String tempDir = Configuration.getROOT() + "/_TMP";
    public final static int SIZETHRESHOLD = 1000000;
    
    private HttpClient httpclient;

    static
    {
        File file = new File(tempDir);
        if(!file.isDirectory())
        {
            file.mkdirs();
        }
    }

    
    
    
    public OpHttp()
    {
        httpclient = new HttpClient();
        httpclient.getState().setCookiePolicy(CookiePolicy.COMPATIBILITY);
    }



    /**
     * Simply upload a file somewhere with an extension control
     **/
    public static boolean uploadFileHtmlForm(HttpServletRequest request, String targetdir, String extensionFilter)
    {
        //boolean isMultipart = FileUpload.isMultipartContent(request);
    	boolean isMultipart  = ServletFileUpload.isMultipartContent(request);
        
        if(isMultipart)
        {
            //DiskFileUpload upload = new DiskFileUpload();
            //upload.setSizeThreshold(1000000);
            //upload.setRepositoryPath(tempDir);

        	DiskFileItemFactory dfif = new DiskFileItemFactory(SIZETHRESHOLD, new File(tempDir));
        	ServletFileUpload upload = new ServletFileUpload(dfif);
        	
            try
            {
                List items = upload.parseRequest(request);
                Iterator iter = items.iterator();
                while(iter.hasNext())
                {
                    FileItem item = (FileItem)iter.next();
                    
                    if(!item.isFormField()) // there are no parameters
                    {
                        String fieldName = item.getFieldName();
                        String fileName = item.getName();
                        String contentType = item.getContentType();
                        boolean isInMemory = item.isInMemory();
                        long sizeInBytes = item.getSize();

                        if(fileName==null)
                        {
                            Debug.println(null, Debug.ERROR, "Filename is null. Skipping.");
                            continue;
                            // return false;
                        }

                        // BUGGY IE (return a full pathname instead of a simple filename)
                        int idx = fileName.lastIndexOf("\\");
                        if(idx!=-1)
                        {
                            fileName = fileName.substring(idx+1);
                        }
                        
                        
                        if(extensionFilter != null)
                        {
                            String extension = MimeTypes.getInstance().getExtension(fileName);
                            if(extension==null || !extension.equals(extensionFilter))
                            {
                                Debug.println(null, Debug.WARNING, "The file is not a "+extensionFilter+" file ! Skipping.");
                                continue;
                            }
                        }


                        Debug.println(null, Debug.DEBUG, "fieldName="+fieldName+" fileName="+fileName+" contentType="
                                      +contentType+" isInMemory="+isInMemory+" sizeInBytes="+sizeInBytes);


                        String starget = targetdir + File.separator + fileName;
                        File storedFile = new File(starget);
                        item.write(storedFile);
                        Debug.println(null, Debug.DEBUG, "File stored to "+storedFile);
                    }
                }
                return true;

            }
            catch(Exception e)
            {
                Debug.println(null, Debug.ERROR, e);
                return false;
            }
        }
        else
            return false;
    }



    /**
     * return the content of the http upload file
     **/
    public static UploadedFile uploadFileHtmlForm(HttpServletRequest request, String extensionFilter)
    {
        //boolean isMultipart = FileUpload.isMultipartContent(request);
    	boolean isMultipart  = ServletFileUpload.isMultipartContent(request);
    	
        if(isMultipart)
        {
            //DiskFileUpload upload = new DiskFileUpload();
            //upload.setSizeThreshold(1000000);
            //upload.setRepositoryPath(tempDir);
        	
        	DiskFileItemFactory dfif = new DiskFileItemFactory(SIZETHRESHOLD, new File(tempDir));
        	ServletFileUpload upload = new ServletFileUpload(dfif);

            try
            {
                List items = upload.parseRequest(request);
                Iterator iter = items.iterator();
                while(iter.hasNext())
                {
                    FileItem item = (FileItem)iter.next();
                    
                    if(!item.isFormField()) // there are no parameters
                    {
                        String fieldName = item.getFieldName();
                        String fileName = item.getName();
                        String contentType = item.getContentType();
                        boolean isInMemory = item.isInMemory();
                        long sizeInBytes = item.getSize();

                        if(fileName==null)
                        {
                            Debug.println(null, Debug.ERROR, "Filename is null. Skipping.");
                            continue;
                            // return false;
                        }

                        // BUGGY IE (return a full pathname instead of a simple filename)
                        int idx = fileName.lastIndexOf("\\");
                        if(idx!=-1)
                        {
                            fileName = fileName.substring(idx+1);
                        }
                        
                        if(extensionFilter!=null) // Check the extension
                        {
                            String extension = MimeTypes.getInstance().getExtension(fileName);
                            if(extension==null || !extension.equals(extensionFilter))
                            {
                                Debug.println(null, Debug.WARNING, "The file is not a "+extensionFilter+" file ! Skipping.");
                                continue;
                            }
                        }                       

                        Debug.println(null, Debug.DEBUG, "fieldName="+fieldName+" fileName="+fileName+" contentType="
                                      +contentType+" isInMemory="+isInMemory+" sizeInBytes="+sizeInBytes);

                        UploadedFile u = new UploadedFile();
                        u.setFieldName(fieldName);
                        u.setFileName(fileName);
                        u.setContentType(contentType);
                        u.setSize(sizeInBytes);
                        u.setInput(item.getInputStream());
                        return u;
                    }
                }
                return null;

            }
            catch(Exception e)
            {
                Debug.println(null, Debug.ERROR, e);
                return null;
            }
        }
        else
            return null;
    }


    /**
     * Send a GET request
     * @return the Http response or null
     **/
    public HttpResponse GET(HttpRequest request)
    {
        return commonMethod(request, new GetMethod(request.getURL()));
    }


    /**
     * Send a POST request
     * @return the Http response or null
     **/
    public HttpResponse POST(HttpRequest request)
    {
        return commonMethod(request, new PostMethod(request.getURL()));
    }


    /**
     * Common method used by GET and POST
     **/
    private HttpResponse commonMethod(HttpRequest request, HttpMethod method)
    {
        try
        {
            method.setFollowRedirects(true);

            //             method.setURI(new URI(request.getURL())); // httpclient 3.0

            Enumeration headers = request.getHeaderNames();
            while(headers.hasMoreElements())
            {
                String name = (String)headers.nextElement();
                String value = request.getHeader(name);
                method.addRequestHeader(name, value);
            }

            // NEED TO BE FIRST BECAUSE PostMethod inherited from GetMethod
            if(method instanceof PostMethod)
            {
                Debug.println(this, Debug.DEBUG, "POST");

                PostMethod postmethod = (PostMethod)method;
                postmethod.setRequestHeader("User-Agent",USER_AGENT);
                                
                ArrayList list = new ArrayList();
                Enumeration paramvalue = request.getParamValueNames();
                while(paramvalue.hasMoreElements())
                {
                    String name = (String)paramvalue.nextElement();
                    ArrayList valuelist = request.getParamValue(name);
                    
                    if(valuelist != null)
                    {
                        int len = valuelist.size();
                        
                        for(int i=0; i<len; i++)
                        {
                            String value = (String)valuelist.get(i);
                            list.add(new NameValuePair(name, value));
                        }
                    }
                }

                Object[] obj = list.toArray();
                int len = obj.length;
                NameValuePair[] nvp = new NameValuePair[len];
                
                for(int i=0; i<len; i++)
                    nvp[i] = (NameValuePair)obj[i];

                postmethod.setRequestBody(nvp);
            }        
            else if(method instanceof GetMethod)
            {
                Debug.println(this, Debug.DEBUG, "GET");

                GetMethod getmethod = (GetMethod)method;
                getmethod.setRequestHeader("User-Agent",USER_AGENT);

                ArrayList list = new ArrayList();
                Enumeration paramvalue = request.getParamValueNames();
                while(paramvalue.hasMoreElements())
                {
                    String name = (String)paramvalue.nextElement();
                    ArrayList valuelist = request.getParamValue(name);
                    
                    if(valuelist != null)
                    {
                        int len = valuelist.size();
                        
                        for(int i=0; i<len; i++)
                        {
                            String value = (String)valuelist.get(i);
                            list.add(new NameValuePair(name, value));
                        }
                    }
                }

                Object[] obj = list.toArray();
                int len = obj.length;
                NameValuePair[] nvp = new NameValuePair[len];
                
                for(int i=0; i<len; i++)
                    nvp[i] = (NameValuePair)obj[i];
                getmethod.setQueryString(nvp);

            }


            Debug.println(this, Debug.DEBUG, "uri="+method.getURI().toString());
            int status = httpclient.executeMethod(method);
            Debug.println(this, Debug.DEBUG, "Status="+method.getStatusText());


            InputStream is = method.getResponseBodyAsStream();
            byte[] data = StreamConverter.inputStreamToBytes(is);
            Header[] headerTab = method.getResponseHeaders();

            HttpResponse response = new HttpResponse();
            response.setStatusCode(status);
            response.setData(data);

            int len = headerTab.length;
            for(int i=0;i<len;i++)
            {
                response.setHeader(headerTab[i].getName(), headerTab[i].getValue());
            }

            

            return response;
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            return null;
        }
        finally
        {
            method.releaseConnection();
            //             method.recycle(); // deprecated
        }
    }
}
