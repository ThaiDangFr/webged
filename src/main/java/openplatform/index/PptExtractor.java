package openplatform.index;

import org.apache.poi.poifs.filesystem.*;
import org.apache.poi.util.LittleEndian;
import org.apache.poi.hwpf.model.*;

import java.util.*;
import java.io.*;

/**
 * This class extracts the text from a powerpoint doc
 *
 */
public class PptExtractor
{
//     private ByteArrayOutputStream writer = new ByteArrayOutputStream();

    /**
     * Gets the text from a Word document.
     *
     * @param in The InputStream representing the Word file.
     */
    public static String extractText(InputStream in) throws Exception
    {
//         ArrayList text = new ArrayList();
        StringBuffer sb = new StringBuffer();
        POIFSFileSystem fsys = new POIFSFileSystem(in);

        // load our POIFS document streams.
        DocumentEntry headerProps =
            (DocumentEntry)fsys.getRoot().getEntry("PowerPoint Document");
        DocumentInputStream input = fsys.createDocumentInputStream("PowerPoint Document");
        byte[] header = new byte[headerProps.getSize()];

        byte[] buffer = new byte[input.available()];

        input.read(buffer, 0, input.available());

        int len = buffer.length;

        for(int i=0; i<len; i++)
        {
            int ascii = (int)buffer[i];
            
            if((ascii>=65 && ascii <=90) || (ascii>=97 && ascii<=122)
               || ascii==32 || (ascii>=128 && ascii<=165))
            {
                sb.append((char)ascii);
            }
        }

        return sb.toString();

//         for(int i=0; i<buffer.length-20; i++) {

//             long type = LittleEndian.getUShort(buffer,i+2);

//             long size = LittleEndian.getUInt(buffer,i+4);

//             if(type==4008) {

//                 writer.write(buffer, i + 4 + 1, (int) size +3);

//                 i = i + 4 + 1 + (int) size - 1;
//             }

//         }

//         return writer.toString();
    }
}
