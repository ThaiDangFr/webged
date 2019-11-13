package openplatform.chart;

import org.jfree.data.time.*;
import java.util.*;

/**
 * Class CustomPeriod
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class CustomPeriod extends Second
{
    private int period;

    /**
     * Means every period second
     **/
    public CustomPeriod(Date reference, int period)
    {
        super(reference);
        this.period = period;
    }


    public CustomPeriod(final int second, final Minute minute, int period)
    {
        super(second, minute);
        this.period = period;
    }


    public RegularTimePeriod next()
    {
        CustomPeriod result = null;
        if (this.getSecond() != LAST_SECOND_IN_MINUTE)
        {
            result = new CustomPeriod(this.getSecond() + period, this.getMinute(), period);
        }
        else
        {
            final Minute next = (Minute) this.getMinute().next();
            if (next != null)
            {
                result = new CustomPeriod(FIRST_SECOND_IN_MINUTE, next, period);
            }
        }
        return result;
    }


    public RegularTimePeriod previous()
    {
        CustomPeriod result = null;
        if (this.getSecond() != FIRST_SECOND_IN_MINUTE)
        {
            result = new CustomPeriod(this.getSecond() - period, this.getMinute(), period);
        }
        else
        {
            final Minute previous = (Minute) this.getMinute().previous();
            if (previous != null)
            {
                result = new CustomPeriod(LAST_SECOND_IN_MINUTE, previous, period);
            }
        }
        return result;       

    }


    public int compareTo(final Object o1)
    {

        int result;

        // CASE 1 : Comparing to another Second object
        // -------------------------------------------
        if (o1 instanceof CustomPeriod)
        {
            final CustomPeriod s = (CustomPeriod) o1;
            result = this.getMinute().compareTo(s.getMinute());
            if (result == 0)
            {
                result = this.getSecond() - s.getSecond();
            }
        }

        // CASE 2 : Comparing to another TimePeriod object
        // -----------------------------------------------
        else if (o1 instanceof RegularTimePeriod)
        {
            // more difficult case - evaluate later...
            result = 0;
        }

        // CASE 3 : Comparing to a non-TimePeriod object
        // ---------------------------------------------
        else
        {
            // consider time periods to be ordered after general objects
            result = 1;
        }

        return result;

    }


    public boolean equals(final Object object)
    {
        if (object instanceof CustomPeriod)
        {
            final CustomPeriod s = (CustomPeriod) object;
            return ((this.getSecond() == s.getSecond()) && (this.getMinute().equals(s.getMinute())));
        }
        else
        {
            return false;
        }
    }
}
