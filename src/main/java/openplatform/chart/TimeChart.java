package openplatform.chart;

import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.data.time.TimePeriodValues;


/**
 * Class TimeChart <br>
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class TimeChart implements TimeConstants 
{
    private TimePeriodValues series;
    private String chartName;
    private long previousTime = -1;
    

    public TimeChart(String chartName, long beginTime)
    {
        this.chartName = chartName;
        this.previousTime = beginTime;
        series = new TimePeriodValues(chartName);
    }


    public void addValue(long time, double value)
    {
        SimpleTimePeriod stp = new SimpleTimePeriod(this.previousTime, time);
        
        if(Double.isNaN(value))
            series.add(stp, null);
        else
            series.add(stp, value);

        this.previousTime = time;
    }


    // GET / SET

    /**
     * Gets the value of series
     *
     * @return the value of series
     */
    public final TimePeriodValues getSeries()
    {
        return this.series;
    }

    /**
     * Sets the value of series
     *
     * @param argSeries Value to assign to this.series
     */
    public final void setSeries(final TimePeriodValues argSeries)
    {
        this.series = argSeries;
    }

    /**
     * Gets the value of chartName
     *
     * @return the value of chartName
     */
    public final String getChartName()
    {
        return this.chartName;
    }

    /**
     * Sets the value of chartName
     *
     * @param argChartName Value to assign to this.chartName
     */
    public final void setChartName(final String argChartName)
    {
        this.chartName = argChartName;
    }

    /**
     * Gets the value of previousTime
     *
     * @return the value of previousTime
     */
    public final long getPreviousTime()
    {
        return this.previousTime;
    }

    /**
     * Sets the value of previousTime
     *
     * @param argPreviousTime Value to assign to this.previousTime
     */
    public final void setPreviousTime(final long argPreviousTime)
    {
        this.previousTime = argPreviousTime;
    }

}
