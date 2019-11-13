package openplatform.index;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import openplatform.tools.StreamConverter;
import openplatform.tools.ZipUtils;

/**
 * OOExtractor.java
 *
 *
 * Created: Thu May 12 18:20:13 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class OOExtractor 
{
    //public static String extractText(File file) throws Exception
    public static String extractText(InputStream input) throws Exception
    {
//         ZipFile zf = new ZipFile(file, ZipFile.OPEN_READ);
//         ZipEntry ze = zf.getEntry("content.xml");

//         if(ze == null) return null;

//         InputStream is = zf.getInputStream(ze);

//         byte[] content = StreamConverter.inputStreamToBytes(is);
//         ByteArrayInputStream bais = new ByteArrayInputStream(content);

        byte[] content = ZipUtils.extract("content.xml", StreamConverter.inputStreamToBytes(input));

        if(content == null) return null;

        ByteArrayInputStream bais = new ByteArrayInputStream(content);
        return XMLExtractor.extractText(bais);
    }
}
