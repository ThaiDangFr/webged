package openplatform.logger;

public interface LogFormat
{
    public String decode();
    public void encode(String logline);
    public void clear();
}
