package openplatform.file;

import java.io.*;

public class OpFile extends File
{

    public OpFile(String pathname)
    {
        super(pathname);
    }

    public OpFile(File parent, String child)
    {
        super(parent, child);
    }


    // Deletes all files and subdirectories under dir.
    // Returns true if all deletions were successful.
    // If a deletion fails, the method stops attempting to delete and returns false.
    public boolean deleteDirectory()
    {
        if(this.isDirectory())
        {
            String[] children = this.list();
            for (int i=0; i<children.length; i++)
            {
                boolean success = (new OpFile(this, children[i])).deleteDirectory();
                if (!success)
                {
                    return false;
                }
            }
        }
        
        // The directory is now empty so delete it
        return this.delete();
    }


    public static void main(String[] args)
    {
        OpFile opf = new OpFile("/home/thai/tmp2");
        opf.deleteDirectory();
    }
}
