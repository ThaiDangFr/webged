package dang.cmsbase;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import openplatform.session.UserSession;
import openplatform.tools.Debug;


public class JspControl {

	private static ArrayList pagesToControl = new ArrayList();
	static
	{
		pagesToControl.add("/cms/user/pc/modules/ged/browser.jsp");
		pagesToControl.add("/cms/user/pc/modules/ged/seefile.jsp");
		pagesToControl.add("/cms/user/pc/modules/ged/seeversion.jsp");
	}
	
	
	
	/**
	 * Check the rights, just control that anonymous cant browse certain page for the moment
	 * @param req
	 * @return
	 */
	public boolean isAuthorized(HttpServletRequest req)
	{
		String path = req.getServletPath();
		Debug.println(this, Debug.DEBUG,"Checking path:"+path);
		
		if(path == null) return false;
		
		if(!pagesToControl.contains(path))
		{
			return true;
		}
		else
		{
			UserSession user = null;
			
			Object obj = req.getSession().getAttribute("usersession");
			if(obj != null) user = (UserSession)obj;
			
			if(user != null && user.getUserId() != null)
				return true;
		}
		
		Debug.println(this, Debug.WARNING, "Access denied !");
		return false;
	}
	
}
