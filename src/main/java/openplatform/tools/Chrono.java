package openplatform.tools;


public class Chrono
{
    private long startTime, endTime;
    
    public void start()   { startTime = System.currentTimeMillis(); }
    public void stop()    { endTime   = System.currentTimeMillis(); }
    public long getTime() { return endTime - startTime;             }
}
