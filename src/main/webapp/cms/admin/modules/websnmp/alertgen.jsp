<jsp:useBean id="module" scope="session" class="dang.cms.AdminModule" />
<jsp:useBean id="adminalert" scope="session" class="dang.websnmp.AdminAlert" />



<%@ include file="../../part1.jspf" %>
<div id="main-copy">
<%@ include file="../../part2.jspf" %>


<a href="${PREFIX}/modules.jsp?page=modules">Modules</a> > <a href="${PREFIX}/modules.jsp?page=modules&moduleId=${module.id}"><c:out value="${module.name}"/></a> > <a href="${PREFIX}/modules/websnmp/index.jsp?page=modules"><fmt:message key='settings'/></a> > <fmt:message key='alert'/>

<br/>
<br/>

<a href="alertuser.jsp"><fmt:message key='configure.alert.mailing'/></a><br/><br/>


<form method="post">
<table>
<caption><fmt:message key='alert'/></caption>
<tr><th><fmt:message key='report.name'/></th></tr>

<c:set var="report" value="${adminalert.reportList}"/>
<c:choose>
<c:when test="${not empty report}">
<c:forEach items="${report}" var="r">
<tr>
<td><a href="alertedit.jsp?reportId=${r.reportId}"><c:out value="${r.displayName}"/></a></td>
</tr>
</c:forEach>
</c:when>

<c:otherwise><tr><td colspan="2"><span class="info"><fmt:message key='no.report'/></span></td></tr></c:otherwise>
</c:choose>

</table>
</form>


</div>
<%@ include file="../../part3.jspf" %>