<jsp:useBean id="module" scope="session" class="dang.cms.AdminModule" />
<jsp:useBean id="adminMib" scope="session" class="dang.websnmp.AdminMib" />


<%@ include file="../../part1.jspf" %>
<div id="main-copy">
<%@ include file="../../part2.jspf" %>


<a href="${PREFIX}/modules.jsp?page=modules">Modules</a> > <a href="${PREFIX}/modules.jsp?page=modules&moduleId=${module.id}"><c:out value="${module.name}"/></a> > <a href="${PREFIX}/modules/websnmp/index.jsp?page=modules"><fmt:message key='settings'/></a> > <fmt:message key='mib.manager'/>

<br/>
<br/>

<table class="horizontal">
<tr><form method="post" action="mibmanaction.jsp?action=upload" enctype="multipart/form-data"><th><fmt:message key='upload'/> MIB</th><td><input type="file" name="myfile"><input type="submit" value="<fmt:message key='upload'/>"/></td></form></tr>

<tr><form method="post" action="mibmanaction.jsp?action=delete" onSubmit="return confirmDelete()"><th><fmt:message key='delete'/> MIB</th><td><sr:select name="mibId" options="${adminMib.mibFileOptions}"/><input type="submit" value="<fmt:message key='delete'/>"/></td></form></tr>
</table>


</div>
<%@ include file="../../part3.jspf" %>