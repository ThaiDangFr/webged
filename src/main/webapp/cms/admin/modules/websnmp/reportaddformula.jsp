<jsp:useBean id="module" scope="session" class="dang.cms.AdminModule" />
<jsp:useBean id="adminReport" scope="session" class="dang.websnmp.AdminReportBean"/>


<c:url var="fw" scope="page" value="reportedit.jsp">
<c:param name="action" value="view"/>

<c:if test="${param.reportid != null}">
<c:param name="reportid" value="${param.reportid}"/>
</c:if>
</c:url>

<c:if test="${param.action=='save'}">
<c:set target="${adminReport}" property="addToReport" value="${param.formulaid}"/>
<jsp:forward page="${fw}"/>
</c:if>


<%@ include file="../../part1.jspf" %>
<div id="main-copy">
<%@ include file="../../part2.jspf" %>


<a href="${PREFIX}/modules.jsp?page=modules">Modules</a> > <a href="${PREFIX}/modules.jsp?page=modules&moduleId=${module.id}"><c:out value="${module.name}"/></a> > <a href="${PREFIX}/modules/websnmp/index.jsp?page=modules"><fmt:message key='settings'/></a> > <fmt:message key='report'/>

<br/>
<br/>


<form name="formulaire" action="reportaddformula.jsp" method="post">

<c:if test="${param.reportid != null}">
<input type="hidden" name="reportid" value="${param.reportid}">
</c:if>

<input type="hidden" name="action" value="save">

<table class="vertical">
<caption><fmt:message key='formula'/></caption>
<tr>
<td>
<input:select name="formulaid" options="${adminReport.availableFormula}" attributesText='onChange="document.formulaire.action.value=\'view\';document.formulaire.submit();"'/>
</td>

<td>
<c:set target="${adminReport}" property="formulaId" value="${param.formulaid}"/>
<c:out value="${fn:substring(adminReport.formulaDesc,0,45)}"/>
</td>
</tr>
</table>

<input type="submit" class="button" value="<fmt:message key='add'/>">
<input type="button" Onclick="window.location='${fw}'" class="button" value="<fmt:message key='back'/>">

</form>


</div>
<%@ include file="../../part3.jspf" %>