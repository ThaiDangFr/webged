package openplatform.database;

import openplatform.tools.*;
import java.io.*;
import java.util.*;

/**
 * Class DBeanGen
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class DBeanGen
{
    private static final String DIRPATH = "src/openplatform/database/dbean/";
    private static final String HEADER = 
    "package openplatform.database.dbean;\n" +
    "import openplatform.tools.*;\n" +
    "import openplatform.database.*;\n" +
    "import java.util.*;\n";
    private static final String NEWLINE = "\n";
    private static final String INDENT = "    ";
    private static final String INDENT2 = INDENT+INDENT;
    private static final String INDENT3 = INDENT2 + INDENT;
    private static final String INDENT4 = INDENT3 + INDENT;
    private static final String INDENT5 = INDENT4 + INDENT;

    private Database db = Database.getInstance();
    


    public String[] getTables()
    {
        try
        {     
            return db.listTables();
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            return null;
        }
    }


    private String getClassName(String table)
    {
        return "DB"+upperFirstCase(table);
    }
    

    private String getDBean(String table)
        throws Exception
    {
        if(table == null) return null;

        String className = getClassName(table);
        String[] fields = db.listField(table);
        ArrayList dbfield = db.descTable(table);

        if(fields == null) return null;

        int flen = fields.length;

        StringBuffer sb = new StringBuffer();
        sb.append(HEADER).append(NEWLINE);
        sb.append("/**").append(NEWLINE);
        sb.append(" * Class ").append(className).append(NEWLINE);
        sb.append(" *").append(NEWLINE);
        sb.append(" *").append("@author Thai DANG<BR>").append(NEWLINE);
        sb.append(" * Source code is copyright").append(NEWLINE);
        sb.append(" **/").append(NEWLINE);

        sb.append("public class ").append(className).append(NEWLINE);
        sb.append("{").append(NEWLINE);
        sb.append(INDENT).append("protected boolean inError = false;").append(NEWLINE);

        for(int i=0; i<flen; i++)
        {
            sb.append(INDENT).append("protected String ").append(fields[i]).append(";").append(NEWLINE);
        }

        sb.append(NEWLINE);

        for(int i=0; i<flen; i++)
        {
            sb.append(INDENT).append("public static String ")
                .append(fields[i].toUpperCase()).append(" = \"")
                .append(fields[i]).append("\";").append(NEWLINE);

            DatabaseField field = (DatabaseField)dbfield.get(i);
            if(field.isEnumeration())
            {
                StringArrayList sal = field.getEnumeration();
                int salen = sal.size();
                for(int j=0; j<salen; j++)
                {
                    String enum0 = sal.getString(j);
                    String varname = (field.getField()+"_"+enum0.replace(' ','_')).toUpperCase();
                    sb.append(INDENT).append("/** ").append(enum0).append(" **/").append(NEWLINE);
                    sb.append(INDENT).append("public static final String ").append(varname)
                        .append(" = \"").append(enum0).append("\";").append(NEWLINE);
                }


                // _LIST for enumeration types B
                String arrayVarName = "_" + field.getField().toUpperCase() + "_LIST";
                sb.append(INDENT).append("/** Contains the SQL enumeration for ").append(field.getField().toUpperCase())
                    .append(" **/").append(NEWLINE);
                sb.append(INDENT).append("public static final ArrayList ")
                    .append(arrayVarName).append(" = new ArrayList();")
                    .append(NEWLINE);
                sb.append(INDENT).append("static {");
                for(int j=0; j<salen; j++)
                {
                    String enum0 = sal.getString(j);
                    String varname = (field.getField()+"_"+enum0.replace(' ','_')).toUpperCase();
                    sb.append(arrayVarName).append(".add(").append(varname).append(");");
                }
                sb.append("}").append(NEWLINE);
                // _LIST for enumeration types E


                // _MAP for enumeration types B
                arrayVarName = "_" + field.getField().toUpperCase() + "_MAP";
                sb.append(INDENT).append("/** Contains the SQL HASHMAP for ").append(field.getField().toUpperCase())
                    .append(" **/").append(NEWLINE);
                sb.append(INDENT).append("public static final OrdHashMap ")
                    .append(arrayVarName).append(" = new OrdHashMap();")
                    .append(NEWLINE);
                sb.append(INDENT).append("static {");
                for(int j=0; j<salen; j++)
                {
                    String enum0 = sal.getString(j);
                    String varname = (field.getField()+"_"+enum0.replace(' ','_')).toUpperCase();
                    sb.append(arrayVarName).append(".put(").append(varname).append(",").append(varname).append(");");
                }
                sb.append("}").append(NEWLINE);
                // _MAP for enumeration types E
            }
        }
        


        sb.append(INDENT).append("public static String TABLE = \"").append(table).append("\";").append(NEWLINE);
        sb.append(NEWLINE);
        sb.append(INDENT).append("private static Database db = Database.getInstance();").append(NEWLINE);
        sb.append(NEWLINE);
        sb.append(NEWLINE);


        // <CLEAR>
        sb.append(INDENT).append("public void clear()").append(NEWLINE);
        sb.append(INDENT).append("{").append(NEWLINE);

        for(int i=0; i<flen; i++)
        {
            sb.append(INDENT).append(INDENT).append(fields[i]).append(" = ").append("null;").append(NEWLINE);
        }

        sb.append(INDENT).append("}").append(NEWLINE);
        // </CLEAR>

        sb.append(NEWLINE);

        // <ISINERROR>
        sb.append(INDENT).append("public boolean isInError()").append(NEWLINE);
        sb.append(INDENT).append("{").append(NEWLINE);
        sb.append(INDENT2).append("return inError;").append(NEWLINE);
        sb.append(INDENT).append("}").append(NEWLINE);
        // </ISINERROR>

        sb.append(NEWLINE);

        // <EQUALS>
        sb.append(INDENT).append("public boolean equals(Object obj)").append(NEWLINE);
        sb.append(INDENT).append("{").append(NEWLINE);
        sb.append(INDENT2).append("if(this == obj) return true;").append(NEWLINE);
        sb.append(INDENT2).append("if((obj == null) || (obj.getClass() != this.getClass())) return false;").append(NEWLINE);
        sb.append(INDENT2).append(className).append(" a = (").append(className).append(")obj;").append(NEWLINE);


        sb.append(INDENT2).append("return ");
        for(int i=0; i<flen; i++)
        {
            sb.append("(").append(fields[i]).append("==null?a.").append(getMethodName(fields[i])).append("()==null:").append(fields[i]).append(".equals(a.").append(getMethodName(fields[i])).append("()))");
            if(i<(flen-1)) sb.append(" && ");
        }
        sb.append(";");


        sb.append(INDENT).append("}").append(NEWLINE);
        // </EQUALS>

        sb.append(NEWLINE);

        // <HASHCODE>
        sb.append(INDENT).append("public int hashCode()").append(NEWLINE);
        sb.append(INDENT).append("{").append(NEWLINE);
        sb.append(INDENT2).append("return ");

        for(int i=0; i<flen; i++)
        {
            sb.append("(").append(fields[i]).append("!=null?").append(fields[i]).append(".hashCode():0)");
            if(i<(flen-1)) sb.append(" + ");
        }

        sb.append(";").append(NEWLINE);
        sb.append(INDENT).append("}").append(NEWLINE);
        // </HASHCODE>

        sb.append(NEWLINE);

        // <TOSTRING>
        sb.append(INDENT).append("public String toString()").append(NEWLINE);
        sb.append(INDENT).append("{").append(NEWLINE);
        sb.append(INDENT2).append("return ");

        for(int i=0; i<flen; i++)
        {
            sb.append("\"").append(fields[i]).append("=\"+").append(fields[i]);
            if(i<(flen-1)) sb.append("+\"|\"+");
        }
        
        sb.append(";").append(NEWLINE);
        sb.append(INDENT).append("}").append(NEWLINE);
        // </TOSTRING>

        sb.append(NEWLINE);

        // <REFRESH>
        sb.append(INDENT).append("public void refresh()").append(NEWLINE);
        sb.append(INDENT).append("{").append(NEWLINE);
        sb.append(INDENT2).append("if(").append(fields[0]).append(" != null)").append(NEWLINE);
        sb.append(INDENT2).append("{").append(NEWLINE);
        sb.append(INDENT3).append(className).append(" mask = new ").append(className).append("();").append(NEWLINE);
        sb.append(INDENT3).append("mask.").append(setMethodName(fields[0])).append("(").append(fields[0]).append(");").append(NEWLINE);
        sb.append(INDENT3).append(className).append(" var = ").append(className).append(".loadByKey(mask);").append(NEWLINE);
        sb.append(INDENT3).append("if(var != null)").append(NEWLINE);
        sb.append(INDENT3).append("{").append(NEWLINE);

        for(int i=0; i<flen; i++)
        {
            sb.append(INDENT4).append(fields[i]).append(" = var.").append(getMethodName(fields[i])).append("();").append(NEWLINE);
        }
        
        sb.append(INDENT3).append("}").append(NEWLINE);

        sb.append(INDENT2).append("}").append(NEWLINE);
        sb.append(INDENT).append("}").append(NEWLINE);
        // </REFRESH>

        sb.append(NEWLINE);


        // <INITBEAN>
        sb.append(INDENT).append("public void initBean(").append(className).append(" db)").append(NEWLINE);
        sb.append(INDENT).append("{").append(NEWLINE);
        
        for(int i=0; i<flen; i++)
        {
            sb.append(INDENT2).append("this.").append(fields[i]).append(" = db.").append(getMethodName(fields[i])).append("();").append(NEWLINE);
        }

        sb.append(INDENT).append("}").append(NEWLINE);
        // </INITBEAN>
        sb.append(NEWLINE);



        // <GET-SET>
        for(int i=0; i<flen; i++)
        {
            sb.append(INDENT).append("public String ").append(getMethodName(fields[i])).append("()").append(NEWLINE);
            sb.append(INDENT).append("{").append(NEWLINE);
            sb.append(INDENT2).append("return ").append(fields[i]).append(";").append(NEWLINE);
            sb.append(INDENT).append("}").append(NEWLINE);

            sb.append(NEWLINE);

            sb.append(INDENT).append("public void ").append(setMethodName(fields[i])).append("(String ").append(fields[i]).append(")").append(NEWLINE);
            sb.append(INDENT).append("{").append(NEWLINE);
            sb.append(INDENT2).append("this.").append(fields[i]).append(" = ").append(fields[i]).append(";").append(NEWLINE);
            sb.append(INDENT).append("}").append(NEWLINE);

            if(i<(flen-1)) sb.append(NEWLINE);
        }
        // </GET-SET>

        sb.append(NEWLINE);

        // <LOAD>
        sb.append(INDENT).append("/**").append(NEWLINE);
        sb.append(INDENT).append(" * @return a ").append(className).append(" table or null if nothing found or if an error occured").append(NEWLINE);
        sb.append(INDENT).append(" **/").append(NEWLINE);
        sb.append(INDENT).append("public static ").append(className).append("[] load(").append(className).append(" mask)").append(NEWLINE);
        sb.append(INDENT).append("{").append(NEWLINE);
        sb.append(INDENT2).append("return load(null, mask);").append(NEWLINE);
        sb.append(INDENT).append("}").append(NEWLINE);
        // </LOAD>
        
        sb.append(NEWLINE);

        // <LOAD>
        sb.append(INDENT).append("/**").append(NEWLINE);
        sb.append(INDENT).append(" * @return a ").append(className).append(" table or null if nothing found or if an error occured").append(NEWLINE);
        sb.append(INDENT).append(" **/").append(NEWLINE);
        sb.append(INDENT).append("public static ").append(className).append("[] load(SQLConstraint sqlconstraint, ").append(className).append(" mask)").append(NEWLINE);
        sb.append(INDENT).append("{").append(NEWLINE);
        sb.append(INDENT2).append("ParamValuePair filter = new ParamValuePair();").append(NEWLINE);
        sb.append(NEWLINE);
        sb.append(INDENT2).append("if(mask != null)").append(NEWLINE);
        sb.append(INDENT2).append("{").append(NEWLINE);
        
        for(int i=0; i<flen; i++)
        {
            sb.append(INDENT3).append("if(mask.").append(getMethodName(fields[i])).append("() != null) filter.set(").append(fields[i].toUpperCase()).append(", mask.").append(getMethodName(fields[i])).append("());").append(NEWLINE);
        }

        sb.append(INDENT2).append("}").append(NEWLINE);
        
        sb.append(NEWLINE);

        sb.append(INDENT2).append("try").append(NEWLINE);
        sb.append(INDENT2).append("{").append(NEWLINE);
        sb.append(INDENT3).append("ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{");

        for(int i=0; i<flen; i++)
        {
            sb.append(fields[i].toUpperCase());;
            if(i<(flen-1)) sb.append(", ");
        }
        
        sb.append("}, filter);").append(NEWLINE);
        sb.append(INDENT3).append("if(pvp == null) return null;").append(NEWLINE);
        sb.append(INDENT3).append("else").append(NEWLINE);
        sb.append(INDENT3).append("{").append(NEWLINE);
        sb.append(INDENT4).append(className).append("[] result = new ").append(className).append("[pvp.length];").append(NEWLINE);
        sb.append(INDENT4).append("for(int i=0; i<result.length; i++)").append(NEWLINE);
        sb.append(INDENT4).append("{").append(NEWLINE);
        sb.append(INDENT5).append("result[i] = new ").append(className).append("();").append(NEWLINE);
        
        for(int i=0; i<flen; i++)
        {
            sb.append(INDENT5).append("result[i].").append(setMethodName(fields[i])).append("(pvp[i].get(").append(fields[i].toUpperCase()).append("));").append(NEWLINE);
        }

        sb.append(INDENT4).append("}").append(NEWLINE);
        sb.append(INDENT4).append("return result;").append(NEWLINE);
        sb.append(INDENT3).append("}").append(NEWLINE);
        sb.append(INDENT2).append("}").append(NEWLINE);

        sb.append(INDENT2).append("catch(Exception e)").append(NEWLINE);
        sb.append(INDENT2).append("{").append(NEWLINE);
        sb.append(INDENT3).append("Debug.println(null, Debug.ERROR, e);").append(NEWLINE);
        sb.append(INDENT3).append("return null;").append(NEWLINE);
        sb.append(INDENT2).append("}").append(NEWLINE);
        sb.append(INDENT).append("}").append(NEWLINE);
        // </LOAD>
        
        sb.append(NEWLINE);

        // <LOADBYKEY>
        sb.append(INDENT).append("/**").append(NEWLINE);
        sb.append(INDENT).append(" * @return a ").append(className).append(" object or null if nothing found or if an error occured").append(NEWLINE);
        sb.append(INDENT).append(" **/").append(NEWLINE);
        sb.append(INDENT).append("public static ").append(className).append(" loadByKey(").append(className).append(" mask)").append(NEWLINE);
        sb.append(INDENT).append("{").append(NEWLINE);
        sb.append(INDENT2).append(className).append("[] res = ").append(className).append(".load(mask);").append(NEWLINE);
        sb.append(INDENT2).append("if(res != null && res.length == 1) return res[0];").append(NEWLINE);
        sb.append(INDENT2).append("else return null;").append(NEWLINE);
        sb.append(INDENT).append("}").append(NEWLINE);
        // </LOADBYKEY>

        sb.append(NEWLINE);

        // <LOADBYKEY>
        sb.append(INDENT).append("/**").append(NEWLINE);
        sb.append(INDENT).append(" * @return a ").append(className).append(" object or null if nothing found or if an error occured").append(NEWLINE);
        sb.append(INDENT).append(" **/").append(NEWLINE);
        sb.append(INDENT).append("public static ").append(className).append(" loadByKey(").append(className).append(" mask, SQLConstraint sqlconstraint)").append(NEWLINE);
        sb.append(INDENT).append("{").append(NEWLINE);
        sb.append(INDENT2).append(className).append("[] res = ").append(className).append(".load(sqlconstraint, mask);").append(NEWLINE);
        sb.append(INDENT2).append("if(res != null && res.length == 1) return res[0];").append(NEWLINE);
        sb.append(INDENT2).append("else return null;").append(NEWLINE);
        sb.append(INDENT).append("}").append(NEWLINE);
        // </LOADBYKEY>

        sb.append(NEWLINE);

        // <STORE>
        sb.append(INDENT).append("/**").append(NEWLINE);
        sb.append(INDENT).append(" * Store the object in database").append(NEWLINE);
        sb.append(INDENT).append(" **/").append(NEWLINE);
        sb.append(INDENT).append("public void store()").append(NEWLINE);
        sb.append(INDENT).append("{").append(NEWLINE);
        sb.append(INDENT2).append("inError = false;").append(NEWLINE);
        sb.append(INDENT2).append("ParamValuePair toinsert = new ParamValuePair();").append(NEWLINE);
        
        for(int i=0; i<flen; i++)
        {
            sb.append(INDENT2).append("toinsert.set(").append(fields[i].toUpperCase()).append(",").append(fields[i]).append(");").append(NEWLINE);
        }
        
        
        sb.append(NEWLINE);
        sb.append(INDENT2).append("// Store a new entry").append(NEWLINE);
        sb.append(INDENT2).append("if(").append(fields[0]).append(" == null)").append(NEWLINE);
        sb.append(INDENT2).append("{").append(NEWLINE);
        sb.append(INDENT3).append("try").append(NEWLINE);
        sb.append(INDENT3).append("{").append(NEWLINE);
        sb.append(INDENT4).append(fields[0]).append(" = db.insert(TABLE, toinsert);").append(NEWLINE);
        sb.append(INDENT3).append("}").append(NEWLINE);
        sb.append(INDENT3).append("catch(Exception e)").append(NEWLINE);
        sb.append(INDENT3).append("{").append(NEWLINE);
        sb.append(INDENT4).append("inError=true;").append(NEWLINE);
        sb.append(INDENT4).append("Debug.println(this, Debug.ERROR, e);").append(NEWLINE);
        sb.append(INDENT3).append("}").append(NEWLINE);
        sb.append(INDENT2).append("}").append(NEWLINE);
        sb.append(INDENT2).append("// update entry").append(NEWLINE);
        sb.append(INDENT2).append("else").append(NEWLINE);
        sb.append(INDENT2).append("{").append(NEWLINE);
        sb.append(INDENT3).append("ParamValuePair filter = new ParamValuePair();").append(NEWLINE);
        sb.append(INDENT3).append("filter.set(").append(fields[0].toUpperCase()).append(", ").append(fields[0]).append(");").append(NEWLINE);
        sb.append(NEWLINE);
        sb.append(INDENT3).append("try").append(NEWLINE);
        sb.append(INDENT3).append("{").append(NEWLINE);
        sb.append(INDENT4).append("db.update(TABLE, filter, toinsert);").append(NEWLINE);
        sb.append(INDENT3).append("}").append(NEWLINE);
        sb.append(INDENT3).append("catch(Exception e)").append(NEWLINE);
        sb.append(INDENT3).append("{").append(NEWLINE);
        sb.append(INDENT4).append("inError=true;").append(NEWLINE);
        sb.append(INDENT4).append("Debug.println(this, Debug.ERROR, e);").append(NEWLINE);
        sb.append(INDENT3).append("}").append(NEWLINE);


        sb.append(INDENT2).append("}").append(NEWLINE);
        sb.append(INDENT).append("}").append(NEWLINE);     
        // </STORE>
        
        sb.append(NEWLINE);

        // <DELETE>
        sb.append(INDENT).append("/**").append(NEWLINE);
        sb.append(INDENT).append(" * Delete the object in database (if primary key is not null)").append(NEWLINE);
        sb.append(INDENT).append(" **/").append(NEWLINE);
        sb.append(INDENT).append("public void delete()").append(NEWLINE);
        sb.append(INDENT).append("{").append(NEWLINE);
        sb.append(INDENT2).append("if(").append(fields[0]).append(" != null)").append(NEWLINE);
        sb.append(INDENT2).append("{").append(NEWLINE);
        sb.append(INDENT3).append("inError = false;").append(NEWLINE);
        sb.append(INDENT3).append("ParamValuePair filter = new ParamValuePair();").append(NEWLINE);
        sb.append(INDENT3).append("filter.set(").append(fields[0].toUpperCase()).append(", ").append(fields[0]).append(");").append(NEWLINE);
        sb.append(NEWLINE);
        sb.append(INDENT3).append("try").append(NEWLINE);
        sb.append(INDENT3).append("{").append(NEWLINE);
        sb.append(INDENT4).append("db.delete(TABLE, filter);").append(NEWLINE);
        sb.append(INDENT3).append("}").append(NEWLINE);
        sb.append(INDENT3).append("catch(Exception e)").append(NEWLINE);
        sb.append(INDENT3).append("{").append(NEWLINE);
        sb.append(INDENT4).append("inError=true;").append(NEWLINE);
        sb.append(INDENT4).append("Debug.println(this, Debug.ERROR, e);").append(NEWLINE);
        sb.append(INDENT3).append("}").append(NEWLINE);        
        sb.append(INDENT2).append("}").append(NEWLINE);
        sb.append(INDENT).append("}").append(NEWLINE);
        // </DELETE>

        sb.append(NEWLINE);

        // <DELETE>
        sb.append(INDENT).append("/**").append(NEWLINE);
        sb.append(INDENT).append(" * Delete many files").append(NEWLINE);
        sb.append(INDENT).append(" **/").append(NEWLINE);
        sb.append(INDENT).append("public static void delete(").append(className).append(" mask)").append(NEWLINE);
        sb.append(INDENT).append("{").append(NEWLINE);
        sb.append(INDENT2).append("ParamValuePair filter = new ParamValuePair();").append(NEWLINE);
        sb.append(NEWLINE);
        sb.append(INDENT2).append("if(mask != null)").append(NEWLINE);
        sb.append(INDENT2).append("{").append(NEWLINE);
        
        for(int i=0; i<flen; i++)
        {
            sb.append(INDENT3).append("if(mask.").append(getMethodName(fields[i])).append("() != null) filter.set(").append(fields[i].toUpperCase()).append(", mask.").append(getMethodName(fields[i])).append("());").append(NEWLINE);
        }

        sb.append(INDENT2).append("}").append(NEWLINE);

        sb.append(NEWLINE);

        sb.append(INDENT2).append("try").append(NEWLINE);
        sb.append(INDENT2).append("{").append(NEWLINE);
        sb.append(INDENT3).append("db.delete(TABLE, filter);").append(NEWLINE);
        sb.append(INDENT2).append("}").append(NEWLINE);
        sb.append(INDENT2).append("catch(Exception e)").append(NEWLINE);
        sb.append(INDENT2).append("{").append(NEWLINE);
        sb.append(INDENT3).append("Debug.println(null, Debug.ERROR, e);").append(NEWLINE);
        sb.append(INDENT2).append("}").append(NEWLINE);  

        sb.append(INDENT).append("}").append(NEWLINE);
        // </DELETE>

        sb.append(NEWLINE);

        // <COUNT>
        sb.append(INDENT).append("/**").append(NEWLINE);
        sb.append(INDENT).append(" * Delete many files").append(NEWLINE);
        sb.append(INDENT).append(" **/").append(NEWLINE);
        sb.append(INDENT).append("public static int count(").append(className).append(" mask, SQLConstraint sqlconst)").append(NEWLINE);
        sb.append(INDENT).append("{").append(NEWLINE);
        sb.append(INDENT2).append("ParamValuePair filter = new ParamValuePair();").append(NEWLINE);
        sb.append(NEWLINE);
        sb.append(INDENT2).append("if(mask != null)").append(NEWLINE);
        sb.append(INDENT2).append("{").append(NEWLINE);
        
        for(int i=0; i<flen; i++)
        {
            sb.append(INDENT3).append("if(mask.").append(getMethodName(fields[i])).append("() != null) filter.set(").append(fields[i].toUpperCase()).append(", mask.").append(getMethodName(fields[i])).append("());").append(NEWLINE);
        }
        sb.append(INDENT2).append("}").append(NEWLINE);

        sb.append(NEWLINE);

        sb.append(INDENT2).append("try").append(NEWLINE);
        sb.append(INDENT2).append("{").append(NEWLINE);
        sb.append(INDENT3).append("return db.count(TABLE, sqlconst, filter);").append(NEWLINE);
        sb.append(INDENT2).append("}").append(NEWLINE);
        sb.append(INDENT2).append("catch(Exception e)").append(NEWLINE);
        sb.append(INDENT2).append("{").append(NEWLINE);
        sb.append(INDENT3).append("Debug.println(null, Debug.ERROR, e);").append(NEWLINE);
        sb.append(INDENT2).append("}").append(NEWLINE);  

        sb.append(NEWLINE);
        sb.append(INDENT2).append("return 0;").append(NEWLINE);

        sb.append(INDENT).append("}").append(NEWLINE);
        // </COUNT>

        sb.append(NEWLINE);

        // <AVG>
        sb.append(INDENT).append("/**").append(NEWLINE);
        sb.append(INDENT).append(" * Return an average").append(NEWLINE);
        sb.append(INDENT).append(" **/").append(NEWLINE);
        sb.append(INDENT).append("public static double avg(String field, ").append(className).append(" mask, SQLConstraint sqlconst)").append(NEWLINE);
        sb.append(INDENT).append("{").append(NEWLINE);
        sb.append(INDENT2).append("ParamValuePair filter = new ParamValuePair();").append(NEWLINE);
        sb.append(NEWLINE);
        sb.append(INDENT2).append("if(mask != null)").append(NEWLINE);
        sb.append(INDENT2).append("{").append(NEWLINE);
        
        for(int i=0; i<flen; i++)
        {
            sb.append(INDENT3).append("if(mask.").append(getMethodName(fields[i])).append("() != null) filter.set(").append(fields[i].toUpperCase()).append(", mask.").append(getMethodName(fields[i])).append("());").append(NEWLINE);
        }

        sb.append(INDENT2).append("}").append(NEWLINE);

        sb.append(NEWLINE);

        sb.append(INDENT2).append("try").append(NEWLINE);
        sb.append(INDENT2).append("{").append(NEWLINE);
        sb.append(INDENT3).append("return db.average(TABLE, field, sqlconst, filter);").append(NEWLINE);
        sb.append(INDENT2).append("}").append(NEWLINE);
        sb.append(INDENT2).append("catch(Exception e)").append(NEWLINE);
        sb.append(INDENT2).append("{").append(NEWLINE);
        sb.append(INDENT3).append("Debug.println(null, Debug.ERROR, e);").append(NEWLINE);
        sb.append(INDENT2).append("}").append(NEWLINE);  

        sb.append(NEWLINE);
        sb.append(INDENT2).append("return 0;").append(NEWLINE);

        sb.append(INDENT).append("}").append(NEWLINE);
        // </AVG>

        sb.append(NEWLINE);

        // <MIN>
        sb.append(INDENT).append("/**").append(NEWLINE);
        sb.append(INDENT).append(" * Return a minimum").append(NEWLINE);
        sb.append(INDENT).append(" **/").append(NEWLINE);
        sb.append(INDENT).append("public static double min(String field, ").append(className).append(" mask, SQLConstraint sqlconst)").append(NEWLINE);
        sb.append(INDENT).append("{").append(NEWLINE);
        sb.append(INDENT2).append("ParamValuePair filter = new ParamValuePair();").append(NEWLINE);
        sb.append(NEWLINE);
        sb.append(INDENT2).append("if(mask != null)").append(NEWLINE);
        sb.append(INDENT2).append("{").append(NEWLINE);
        
        for(int i=0; i<flen; i++)
        {
            sb.append(INDENT3).append("if(mask.").append(getMethodName(fields[i])).append("() != null) filter.set(").append(fields[i].toUpperCase()).append(", mask.").append(getMethodName(fields[i])).append("());").append(NEWLINE);
        }

        sb.append(INDENT2).append("}").append(NEWLINE);

        sb.append(NEWLINE);

        sb.append(INDENT2).append("try").append(NEWLINE);
        sb.append(INDENT2).append("{").append(NEWLINE);
        sb.append(INDENT3).append("return db.min(TABLE, field, sqlconst, filter);").append(NEWLINE);
        sb.append(INDENT2).append("}").append(NEWLINE);
        sb.append(INDENT2).append("catch(Exception e)").append(NEWLINE);
        sb.append(INDENT2).append("{").append(NEWLINE);
        sb.append(INDENT3).append("Debug.println(null, Debug.ERROR, e);").append(NEWLINE);
        sb.append(INDENT2).append("}").append(NEWLINE);  

        sb.append(NEWLINE);
        sb.append(INDENT2).append("return 0;").append(NEWLINE);

        sb.append(INDENT).append("}").append(NEWLINE);
        // </MIN>

        sb.append(NEWLINE);

        // <MAX>
        sb.append(INDENT).append("/**").append(NEWLINE);
        sb.append(INDENT).append(" * Return a max").append(NEWLINE);
        sb.append(INDENT).append(" **/").append(NEWLINE);
        sb.append(INDENT).append("public static double max(String field, ").append(className).append(" mask, SQLConstraint sqlconst)").append(NEWLINE);
        sb.append(INDENT).append("{").append(NEWLINE);
        sb.append(INDENT2).append("ParamValuePair filter = new ParamValuePair();").append(NEWLINE);
        sb.append(NEWLINE);
        sb.append(INDENT2).append("if(mask != null)").append(NEWLINE);
        sb.append(INDENT2).append("{").append(NEWLINE);
        
        for(int i=0; i<flen; i++)
        {
            sb.append(INDENT3).append("if(mask.").append(getMethodName(fields[i])).append("() != null) filter.set(").append(fields[i].toUpperCase()).append(", mask.").append(getMethodName(fields[i])).append("());").append(NEWLINE);
        }

        sb.append(INDENT2).append("}").append(NEWLINE);

        sb.append(NEWLINE);

        sb.append(INDENT2).append("try").append(NEWLINE);
        sb.append(INDENT2).append("{").append(NEWLINE);
        sb.append(INDENT3).append("return db.max(TABLE, field, sqlconst, filter);").append(NEWLINE);
        sb.append(INDENT2).append("}").append(NEWLINE);
        sb.append(INDENT2).append("catch(Exception e)").append(NEWLINE);
        sb.append(INDENT2).append("{").append(NEWLINE);
        sb.append(INDENT3).append("Debug.println(null, Debug.ERROR, e);").append(NEWLINE);
        sb.append(INDENT2).append("}").append(NEWLINE);  

        sb.append(NEWLINE);
        sb.append(INDENT2).append("return 0;").append(NEWLINE);

        sb.append(INDENT).append("}").append(NEWLINE);
        // </MAX>

        sb.append(NEWLINE);

        // <STD>
        sb.append(INDENT).append("/**").append(NEWLINE);
        sb.append(INDENT).append(" * Return a standard deviation (french ecart type)").append(NEWLINE);
        sb.append(INDENT).append(" **/").append(NEWLINE);
        sb.append(INDENT).append("public static double std(String field, ").append(className).append(" mask, SQLConstraint sqlconst)").append(NEWLINE);
        sb.append(INDENT).append("{").append(NEWLINE);
        sb.append(INDENT2).append("ParamValuePair filter = new ParamValuePair();").append(NEWLINE);
        sb.append(NEWLINE);
        sb.append(INDENT2).append("if(mask != null)").append(NEWLINE);
        sb.append(INDENT2).append("{").append(NEWLINE);
        
        for(int i=0; i<flen; i++)
        {
            sb.append(INDENT3).append("if(mask.").append(getMethodName(fields[i])).append("() != null) filter.set(").append(fields[i].toUpperCase()).append(", mask.").append(getMethodName(fields[i])).append("());").append(NEWLINE);
        }

        sb.append(INDENT2).append("}").append(NEWLINE);

        sb.append(NEWLINE);

        sb.append(INDENT2).append("try").append(NEWLINE);
        sb.append(INDENT2).append("{").append(NEWLINE);
        sb.append(INDENT3).append("return db.std(TABLE, field, sqlconst, filter);").append(NEWLINE);
        sb.append(INDENT2).append("}").append(NEWLINE);
        sb.append(INDENT2).append("catch(Exception e)").append(NEWLINE);
        sb.append(INDENT2).append("{").append(NEWLINE);
        sb.append(INDENT3).append("Debug.println(null, Debug.ERROR, e);").append(NEWLINE);
        sb.append(INDENT2).append("}").append(NEWLINE);  

        sb.append(NEWLINE);
        sb.append(INDENT2).append("return 0;").append(NEWLINE);

        sb.append(INDENT).append("}").append(NEWLINE);
        // </STD>

        sb.append(NEWLINE);

        // <DISTINCT>
        sb.append(INDENT).append("/**").append(NEWLINE);
        sb.append(INDENT).append(" * Return distinct entries)").append(NEWLINE);
        sb.append(INDENT).append(" **/").append(NEWLINE);
        sb.append(INDENT).append("public static String[] distinct(String field, ").append(className).append(" mask, SQLConstraint sqlconst)").append(NEWLINE);
        sb.append(INDENT).append("{").append(NEWLINE);
        sb.append(INDENT2).append("ParamValuePair filter = new ParamValuePair();").append(NEWLINE);
        sb.append(NEWLINE);
        sb.append(INDENT2).append("if(mask != null)").append(NEWLINE);
        sb.append(INDENT2).append("{").append(NEWLINE);
        
        for(int i=0; i<flen; i++)
        {
            sb.append(INDENT3).append("if(mask.").append(getMethodName(fields[i])).append("() != null) filter.set(").append(fields[i].toUpperCase()).append(", mask.").append(getMethodName(fields[i])).append("());").append(NEWLINE);
        }

        sb.append(INDENT2).append("}").append(NEWLINE);

        sb.append(NEWLINE);

        sb.append(INDENT2).append("try").append(NEWLINE);
        sb.append(INDENT2).append("{").append(NEWLINE);
        sb.append(INDENT3).append("return db.selectDistinct(TABLE, sqlconst, field, filter);").append(NEWLINE);
        sb.append(INDENT2).append("}").append(NEWLINE);
        sb.append(INDENT2).append("catch(Exception e)").append(NEWLINE);
        sb.append(INDENT2).append("{").append(NEWLINE);
        sb.append(INDENT3).append("Debug.println(null, Debug.ERROR, e);").append(NEWLINE);
        sb.append(INDENT2).append("}").append(NEWLINE);  

        sb.append(NEWLINE);
        sb.append(INDENT2).append("return null;").append(NEWLINE);

        sb.append(INDENT).append("}").append(NEWLINE);
        // </DISTINCT>

        sb.append("}").append(NEWLINE);

        return sb.toString();
    }

    
    public void saveDBean(String table)
    {
        try
        {

            File file = new File(System.getProperty("user.dir")+"/"+DIRPATH+getClassName(table)+".java");
            
            if(!file.getParentFile().exists()) file.getParentFile().mkdirs();
            
            String jdbean = getDBean(table);

            if(file.exists()) Debug.println(this, Debug.DEBUG, "Overwriting "+file);
            else Debug.println(this, Debug.DEBUG, "Creating "+file);

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(jdbean.getBytes("UTF-8"));
            fos.close();
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }
    }


    private String upperFirstCase(String st)
    {
        if(st == null) return null;

        char[] ca = st.toCharArray();
        ca[0] = Character.toUpperCase(ca[0]);
        
        return new String(ca);
    }


    private String lowerFirstCase(String st)
    {
        if(st == null) return null;

        char[] ca = st.toCharArray();
        ca[0] = Character.toLowerCase(ca[0]);
        
        return new String(ca);
    }


    private String getMethodName(String field)
    {
        if(field == null) return null;

        return "get"+upperFirstCase(field);
    }

    private String setMethodName(String field)
    {
        if(field == null) return null;

        return "set"+upperFirstCase(field);
    }

    public static void main(String[] args)
    {
        DBeanGen dbg = new DBeanGen();
        String[] t = dbg.getTables();

        if(t == null) return;

        int len = t.length;

        for(int i=0; i<len; i++)
        {
        	System.out.println("Parsing : "+t[i]);
            dbg.saveDBean(t[i]);
        }
    }
}
