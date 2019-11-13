package dang.applimgt;

/**
 * Class Maintenance
 *
 * Used to perform maintenance jobs. The scope is application.
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class AdminMaintenance // extends NotifPushlet
{

	
	
    public String getJVMStats()
    {
        Runtime runtime = Runtime.getRuntime();
        int max = (int)runtime.totalMemory();
        int freemem = (int)(runtime.freeMemory());
        int usedmem = max - freemem;
        
        float f_usedmem = (float)usedmem/(float)1048576;
        float f_max = (float)max/(float)1048576;
        
        return "Memory usage " + Math.round(f_usedmem) + " Mb/" + Math.round(f_max)
            + "Mb ";
    }
}


