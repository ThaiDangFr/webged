package openplatform.database;


/**
 * Class SQLConstraint
 *
 * Constraint for SQL select
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class SQLConstraint
{
    private String orderBy;
    private boolean desc; // order by biggest first or not
    private int limit;
    private int limitOffset;
    private String customWhere;
    private String separator;
    private boolean caseSensitive;

    public final static String AND = " AND ";
    public final static String OR = " OR ";
    public final static String LOWER_THAN = " < ";
    public final static String LOWER_EQ_THAN = " <= ";
    public final static String GREATER_THAN = " > ";
    public final static String GREATER_EQ_THAN = " >= ";
    public final static String DIFFERENT_OF = " != ";
    public final static String LIKE = " LIKE ";
    public final static String IS_NULL = " IS NULL ";
    public final static String IS_NOT_NULL = " IS NOT NULL ";


    public SQLConstraint()
    {
        clear();
    }

    
    public void setOR()
    {
        separator = OR;
    }


    public void setAND()
    {
        separator = AND;
    }


    public String getSeparator()
    {
        return separator;
    }

    public void clear()
    {
        orderBy=null;
        desc=false;
        limit=-1;
        limitOffset=0;
        customWhere=null;
        separator = AND;
        caseSensitive = false;
    }


    public String getCustomWhere()
    {
        return customWhere;
    }

    public void setCustomWhere(String customWhere)
    {
        this.customWhere = customWhere;
    }


    public int getLimitOffset()
    {
        return limitOffset;
    }

    public void setLimitOffset(int offset)
    {
        limitOffset=offset;
    }

    public int getLimit()
    {
        return limit;
    }

    public void setLimit(int alimit)
    {
        limit=alimit;
    }

    public boolean getOrderByBiggestFirst()
    {
        return desc;
    }

    public void setOrderByBiggestFirst(boolean desc)
    {
        this.desc = desc;
    }



    public String getOrderBy()
    {
        return orderBy;
    }

    public void setOrderBy(String aorderBy)
    {
        orderBy=aorderBy;
    }


    public boolean isCaseSensitive()
    {
        return caseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive)
    {
        this.caseSensitive = caseSensitive;
    }

    /**
     * compile all constraint an generate a string to append to the end of the SQL select
     **/
    public String compile()
    {
        StringBuffer sb = new StringBuffer();

        if(customWhere!=null)
        {
            sb.append(" ( ");
            sb.append(customWhere);
            sb.append(" ) ");
        }

        if(orderBy!=null)
            sb.append(" ORDER BY "+orderBy);

        if(desc)
            sb.append(" DESC");

        if(limit!=-1)
            sb.append(" LIMIT "+limitOffset+","+limit);

        return sb.toString();
    }

}
