<jsp:useBean id="module" scope="session" class="dang.cms.AdminModule" />
<jsp:useBean id="adminReport" scope="session" class="dang.websnmp.AdminReportBean" />

<c:if test="${param.action=='delete'}">
<c:set target="${adminReport}" property="reportIds" value="${paramValues.reportid}"/>
<sr:do bean="${adminReport}" property="deleteReport"/>
</c:if>

<%@ include file="../../part1.jspf" %>
<div id="main-copy">
<%@ include file="../../part2.jspf" %>


<a href="${PREFIX}/modules.jsp?page=modules">Modules</a> > <a href="${PREFIX}/modules.jsp?page=modules&moduleId=${module.id}"><c:out value="${module.name}"/></a> > <a href="${PREFIX}/modules/websnmp/index.jsp?page=modules"><fmt:message key='settings'/></a> > <fmt:message key='report'/>

<br/>
<br/>


<form action="reportgen.jsp" method="post" name="gen" onSubmit="return confirmDelete()">
<input type="hidden" name="action" value="delete">

<table class="vertical">
<caption><fmt:message key='report'/></caption>
<tr>
    <th></th>
    <th><fmt:message key='report.name'/></th>
    <th><fmt:message key='host.name'/></th>
</tr>


<c:set var="dbreports" value="${adminReport.reportList}" scope="page" />
<c:choose>

<c:when test="${dbreports==null}">
<tr><td colspan="4"><span class="info"><fmt:message key='no.report'/></span></td></tr>
</c:when>

<c:when test="${dbreports!=null}">
<c:forEach items="${dbreports}" var="r">
<c:set target="${adminReport}" property="hostId" value="${r.hostId}"/>
<tr>
<td width="5%">
<input type="checkbox" name="reportid" value="${r.reportId}">
</td>
<td>
<a href="reportedit.jsp?init=true&reportid=${r.reportId}">
<c:out value="${r.displayName}"/>
</a>
</td>
<td><c:out value="${adminReport.hostName}"/></td>
</tr>
</c:forEach>
</c:when>
</c:choose>

</table>


<input type="button" value="<fmt:message key='add'/>" onClick="window.location='reportedit.jsp?init=true'" class="button">
<input type="submit" value="<fmt:message key='delete'/>" class="button">

</form>


</div>
<%@ include file="../../part3.jspf" %>