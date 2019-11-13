package dang.cms;

import java.util.*;
import openplatform.database.dbean.*;
import openplatform.database.*;
import openplatform.tools.*;

/**
 * Class AdminTrace
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class AdminTrace
{
    private static HashMap dayOpt = new OrdHashMap();
    private static HashMap monthOpt = new OrdHashMap();
    private static HashMap yearOpt = new OrdHashMap();


    private String bDay;
    private String bMonth;
    private String bYear;

    private String eDay;
    private String eMonth;
    private String eYear;
    
    private SQLConstraint constr = new SQLConstraint();

    static
    {
        for(int i=1; i<=31; i++) dayOpt.put(Integer.toString(i), Integer.toString(i));
        for(int i=1; i<=12; i++) monthOpt.put(Integer.toString(i), Integer.toString(i));
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int myear = year - 5;
        for(int i=myear; i<=year; i++) yearOpt.put(Integer.toString(i), Integer.toString(i));
    }

    // GET BEG
    public boolean getDoInit()
    {
        bDay = Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        bMonth = Integer.toString(Calendar.getInstance().get(Calendar.MONTH)+1);
        bYear = Integer.toString(Calendar.getInstance().get(Calendar.YEAR)-1);        

        eDay = Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        eMonth = Integer.toString(Calendar.getInstance().get(Calendar.MONTH)+1);
        eYear = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));

        Debug.println(this, Debug.WARNING, bDay+" "+bMonth+" "+bYear);
        Debug.println(this, Debug.WARNING, eDay+" "+eMonth+" "+eYear);

        return true;
    }

    public HashMap getDayOption() { return dayOpt; }
    public HashMap getMonthOption() { return monthOpt; }
    public HashMap getYearOption() { return yearOpt; }

    public String getBeginDay() { return bDay; }
    public String getBeginMonth() { return bMonth; }
    public String getBeginYear() { return bYear; }
    public String getEndDay() { return eDay; }
    public String getEndMonth() { return eMonth; }
    public String getEndYear() { return eYear; }

    public ArrayList getTrace()
    {
        constr.clear();
        StringBuffer sb = new StringBuffer();
        
        sb.append(DBTrace.DATE).append(SQLConstraint.GREATER_EQ_THAN).append("'").append(bYear).append("-").append(bMonth).append("-").append(bDay).append(" 00:00:00").append("'");
        sb.append(SQLConstraint.AND);
        sb.append(DBTrace.DATE).append(SQLConstraint.LOWER_EQ_THAN).append("'").append(eYear).append("-").append(eMonth).append("-").append(eDay).append(" 23:59:59").append("'");
        
        constr.setCustomWhere(sb.toString());
        constr.setOrderBy(DBTrace.DATE);
        constr.setOrderByBiggestFirst(true);

        DBTrace[] t = DBTrace.load(constr, null);

        if(t == null) return null;
        else
        {
            ArrayList array = new ArrayList();
            int len = t.length;
            for(int i=0; i<len; i++)
            {
                array.add(new Trace(t[i]));
            }
            return array;
        }
    }
    // GET END


    // SET BEG
    public void setBeginDay(String bDay) { this.bDay = bDay; }
    public void setBeginMonth(String bMonth) { this.bMonth = bMonth; }
    public void setBeginYear(String bYear) { this.bYear = bYear;; }
    public void setEndDay(String eDay) { this.eDay = eDay; }
    public void setEndMonth(String eMonth) { this.eMonth = eMonth; }
    public void setEndYear(String eYear) { this.eYear = eYear; }
    // SET END
}
