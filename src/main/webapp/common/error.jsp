<%@ page import="java.util.Enumeration" isErrorPage="true" %>
<%@ page import="java.io.*" %>

<%
if(exception!=null)
{
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    exception.printStackTrace(pw);
    session.setAttribute("error", sw.toString());
    pw.close();
    sw.close();
}
%>

<sr:xhtml>
<sr:head title="Error page"/>
<sr:body title="Error" attributesText="bgcolor=\"#6ca3ee\"">

<p><c:out value="An exception occured with the following message ${error}"/></p>
<p>Please contact your administrator and submit the bug.</p>

<sr:br/>
<sr:br/>
<b>Application attribute</b>
<%
Enumeration appAttribEnum = application.getAttributeNames();
while(appAttribEnum.hasMoreElements())
{
    String name = (String)appAttribEnum.nextElement();
    out.print("<p>"+name+" | "+application.getAttribute(name)+"</p>");
}
%>

<sr:br/>
<sr:br/>
<b>Request attribute</b>
<%
Enumeration reqAttribEnum = request.getAttributeNames();
while(reqAttribEnum.hasMoreElements())
{
    String name = (String)reqAttribEnum.nextElement();
    out.print("<p>"+name+" | "+request.getAttribute(name)+"</p>");
}
%>

<sr:br/>
<sr:br/>
<b>Parameters</b>
<%
Enumeration paramEnum = request.getParameterNames();
while(paramEnum.hasMoreElements())
{
    String name = (String)paramEnum.nextElement();
    out.print("<p>"+name+" | "+request.getAttribute(name)+"</p>");
}
%>
</sr:body>
</sr:xhtml>
