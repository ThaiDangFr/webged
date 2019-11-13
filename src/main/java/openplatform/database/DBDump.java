package openplatform.database;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import openplatform.tools.*;
import java.text.SimpleDateFormat;
import java.text.FieldPosition;
import javax.servlet.ServletOutputStream;


/**
 * Class DBDump
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class DBDump
{

    private Database db = Database.getInstance();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd'_'MM'_'yyyy'_'HH'_'mm");    
    private static final FieldPosition fieldpos0 = new FieldPosition(0);


    public boolean downloadDump(HttpServletResponse resp)
    {
        try
        {
            if(resp==null) return false;

            String st = dump();
            if(st == null) return false;

            byte[] b = st.getBytes("UTF-8");
            if(b == null) return false;
        
            

            Date time = new Date(System.currentTimeMillis());

            StringBuffer sbbdate = dateFormat.format(time, new StringBuffer(), fieldpos0);


            resp.setContentType("text/txt");
            resp.setHeader("Content-disposition","attachment; filename=backup_"+sbbdate+".sql");
            resp.setContentLength(st.length());



            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(b, 0, b.length);
            ServletOutputStream sos = resp.getOutputStream();
            baos.writeTo(sos);
            sos.flush();

            return true;
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            return false;
        }
    }


    public String dump()
    {
        StringBuffer res = new StringBuffer();
        res.append("SET FOREIGN_KEY_CHECKS=0;\n");

        try
        {
            String[] tables = db.listTables();
            int len = tables.length;

            for(int i=0; i<len; i++)
            {
                String table = tables[i];
                String[] fields = db.listField(table);
                StringBuffer sbtable = new StringBuffer();

                sbtable.append("INSERT INTO ").append(table).append(" (");
                int flen = fields.length;
                for(int j=0; j<flen; j++)
                {
                    sbtable.append(fields[j]);

                    if(j!=(flen-1)) sbtable.append(",");
                    else sbtable.append(")");
                }
                sbtable.append(" VALUES (");
                

                ParamValuePair[] pvp = db.select(table, null, fields, null);

                if(pvp==null) continue;

                int pvplen = pvp.length;
                for(int j=0; j<pvplen; j++)
                {
                    for(int k=0; k<flen; k++)
                    {
                        String value = pvp[j].get(fields[k]);
                        if(value==null) sbtable.append("null");
                        else sbtable.append("'").append(filterSQL(value)).append("'");

                        if(k!=(flen-1)) sbtable.append(",");
                        else sbtable.append(")");
                    }

                    if(j!=(pvplen-1)) sbtable.append(",(");
                    else sbtable.append(";");
                }


                res.append(sbtable).append("\n");
            }

            return res.toString();
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            return null;
        }
    }



    private String filterSQL(String comment)
    {
        String res = Database.cleanChars(comment);
        return res;
    }


    
    public static void main(String[] args)
    {
        DBDump d = new DBDump();
        System.out.println(d.dump());
    }
    
}
