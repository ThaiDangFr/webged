package openplatform.tools;

public class OsName
{
	public static boolean isLinux()
	{
		if(System.getProperty("os.name").startsWith("Linux")) return true;
		else return false;
	}
	
	public static boolean isMacOS()
	{
		if(System.getProperty("os.name").startsWith("Mac OS")) return true;
		else return false;		
	}
	
	public static boolean isWindows()
	{
		if(System.getProperty("os.name").startsWith("Windows")) return true;
		else return false;				
	}
	
	public static boolean isOS2()
	{
		if(System.getProperty("os.name").startsWith("OS/2")) return true;
		else return false;			
	}
	
	public static boolean isSolaris()
	{
		if(System.getProperty("os.name").startsWith("Solaris")) return true;
		else return false;	
	}
	
	public static boolean isSunOS()
	{
		if(System.getProperty("os.name").startsWith("SunOS")) return true;
		else return false;			
	}
	
	public static boolean isMPEiX()
	{
		if(System.getProperty("os.name").startsWith("MPE/iX")) return true;
		else return false;	
	}
	
	public static boolean isHPUX()
	{
		if(System.getProperty("os.name").startsWith("HP-UX")) return true;
		else return false;	
	}
	
	public static boolean isAIX()
	{
		if(System.getProperty("os.name").startsWith("AIX")) return true;
		else return false;	
	}
	
	public static boolean isOS390()
	{
		if(System.getProperty("os.name").startsWith("OS/390")) return true;
		else return false;	
	}
	
	public static boolean isFreeBSD()
	{
		if(System.getProperty("os.name").startsWith("FreeBSD")) return true;
		else return false;	
	}
	
	public static boolean isIrix()
	{
		if(System.getProperty("os.name").startsWith("Irix")) return true;
		else return false;	
	}
	
	public static boolean isDigitalUnix()
	{
		if(System.getProperty("os.name").startsWith("Digital Unix")) return true;
		else return false;	
	}
	
	public static boolean isNetWare()
	{
		if(System.getProperty("os.name").startsWith("NetWare")) return true;
		else return false;	
	}
	
	public static boolean isOSF1()
	{
		if(System.getProperty("os.name").startsWith("OSF1")) return true;
		else return false;	
	}
	
	public static boolean isOpenVMS()
	{
		if(System.getProperty("os.name").startsWith("OpenVMS")) return true;
		else return false;	
	}
}
