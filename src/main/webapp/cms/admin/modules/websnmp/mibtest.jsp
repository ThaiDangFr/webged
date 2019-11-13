<jsp:useBean id="module" scope="session" class="dang.cms.AdminModule" />
<jsp:useBean id="adminMibTest" scope="session" class="dang.websnmp.AdminMibTestBean" />

<c:set target="${adminMibTest}" property="mibName" value="${param.mibName}"/>
<c:set target="${adminMibTest}" property="oidName" value="${param.oidName}"/>
<c:set target="${adminMibTest}" property="hostId" value="${param.hostId}"/>


<%@ include file="../../part1.jspf" %>
<div id="main-copy">
<%@ include file="../../part2.jspf" %>


<a href="${PREFIX}/modules.jsp?page=modules">Modules</a> > <a href="${PREFIX}/modules.jsp?page=modules&moduleId=${module.id}"><c:out value="${module.name}"/></a> > <a href="${PREFIX}/modules/websnmp/index.jsp?page=modules"><fmt:message key='settings'/></a> > <fmt:message key='mib.test'/>

<br/>
<br/>


<form action="mibtest.jsp" method="post" name="mibtest">
<input type="hidden" name="action" value="run">

<table class="horizontal">
<caption><fmt:message key='mib.test'/></caption>
<tr>
<th><fmt:message key='mib.name'/></th>
<td>
<input:select name="mibName" options="${adminMibTest.mibOptions}" default="${adminMibTest.mibName}" attributesText='onChange="document.mibtest.action.value=\'view\';document.mibtest.submit();"'/>
</td>
</tr>

<tr>
<th><fmt:message key='oid.name'/></th>
<td>
<input:select name="oidName" options="${adminMibTest.oidOptions}" default="${adminMibTest.oidName}"/>
</td>
</tr>

<tr>
<th><fmt:message key='host.name'/></th>
<td>
<input:select name="hostId" options="${adminMibTest.hostOptions}" default="${adminMibTest.hostOptions}"/>
</td>
</tr>

</table>

<input type="submit" value="<fmt:message key='launch'/>" class="button">

</form>



<c:if test="${param.action=='run'}">
<c:set var="runrep" value="${adminMibTest.run}" scope="page" />

<table id="mibtest">
<c:if test="${runrep==null}">
<tr><td><fmt:message key='no.response'/></td></tr>
</c:if>

<c:forEach items="${runrep}" var="r">
<tr><td><c:out value="${r}"/></td></tr>
</c:forEach>
</table>
</c:if>



</div>
<%@ include file="../../part3.jspf" %>