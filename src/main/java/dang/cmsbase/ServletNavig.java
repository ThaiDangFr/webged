package dang.cmsbase;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import openplatform.http.NavigCodec;
import openplatform.tools.Debug;

public class ServletNavig extends HttpServlet
{

	
	public void init()
	{
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		try
		{
			StringBuffer navig = new StringBuffer();
			navig.append(request.getRequestURI())
				.append("?").append(request.getQueryString());

			//Debug.println(this, Debug.DEBUG, "Navig:"+ navig);
			
			String fwd = NavigCodec.decode(navig.toString());
			
			// remove /openplatform
			int idx = fwd.indexOf("/");
			if(idx != -1)
			{
				int idx2 = fwd.indexOf("/",idx+1);
				if(idx2 != -1)
					fwd = fwd.substring(idx2);
			}
		
			
			//Debug.println(this, Debug.DEBUG, "Dispatch to:"+fwd);
			
			RequestDispatcher dispatcher = 
				getServletContext().getRequestDispatcher(fwd);
			dispatcher.forward(request, response);
		}
		catch(Exception e)
		{
			Debug.println(this, Debug.ERROR, e);
		}
	}
	
	
	

}
