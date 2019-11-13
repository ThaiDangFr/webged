<jsp:useBean id="module" scope="session" class="dang.cms.AdminModule" />
<jsp:useBean id="adminReport" scope="session" class="dang.websnmp.AdminReportBean"/>


<c:if test="${param.init=='true'}">
<sr:do bean="${adminReport}" property="clear"/>
<c:set target="${adminReport}" property="reportId" value="${param.reportid}"/>
</c:if>



<c:choose>
<c:when test="${param.action=='delete'}">
<c:set target="${adminReport}" property="formulaIds" value="${paramValues.formulaid}"/>
<sr:do bean="${adminReport}" property="deleteFormula"/>
</c:when>

<c:when test="${param.action=='save'}">

<c:set target="${adminReport}" property="reportName" value="${param.reportname}"/>
<c:set target="${adminReport}" property="hostId" value="${param.hostid}"/>

<sr:do bean="${adminReport}" property="save" redirectOk="reportgen.jsp"/>
</c:when>


<c:when test="${param.action=='addformula'}">
<c:set target="${adminReport}" property="reportName" value="${param.reportname}"/>
<c:set target="${adminReport}" property="hostId" value="${param.hostid}"/>

<c:url var="addfurl" value="reportaddformula.jsp">
<c:if test="${param.reportid!=null}">
<c:param name="reportid" value="${param.reportid}"/>
</c:if>
</c:url>

<jsp:forward page="${addfurl}"/>

</c:when>

</c:choose>


<%@ include file="../../part1.jspf" %>
<div id="main-copy">
<%@ include file="../../part2.jspf" %>


<a href="${PREFIX}/modules.jsp?page=modules">Modules</a> > <a href="${PREFIX}/modules.jsp?page=modules&moduleId=${module.id}"><c:out value="${module.name}"/></a> > <a href="${PREFIX}/modules/websnmp/index.jsp?page=modules"><fmt:message key='settings'/></a> > <fmt:message key='report'/>

<br/>
<br/>


<form name="formulaire" action="reportedit.jsp" method="post">
<input type="hidden" name="action" value="save">

<c:if test="${param.reportid!=null}">
<input type="hidden" name="reportid" value="${param.reportid}">
</c:if>



<table class="horizontal">
<tr>
<th><fmt:message key='report.name'/></th>
<td>
<input:text name="reportname" default="${adminReport.reportName}" attributesText='size="22" maxlength="22"'/>
</td>
</tr>

<tr>
<th><fmt:message key='host'/></th>
<td>
<input:select name="hostid" options="${adminReport.hostOptions}" default="${adminReport.selectedHost}"/>
</td>
</tr>


<tr>
<th valign="top"><fmt:message key='formula'/>(s)</th>
<td>
<div class="field">
<a href="javascript:document.formulaire.action.value='addformula';document.formulaire.submit();">[<fmt:message key='add'/>]</a>
<a href="javascript:document.formulaire.action.value='delete';document.formulaire.submit();">[<fmt:message key='delete'/>]</a>
<br/>


<c:forEach items="${adminReport.associatedFormula}" var="af">
<input type="checkbox" name="formulaid" value="${af.key}"> <c:out value="${af.value}"/>
<br/>
</c:forEach>
</div>
</td>
</tr>

</table>


<input type="submit" value="<fmt:message key='save'/>" class="button">
<input type="button" value="<fmt:message key='cancel'/>" onClick="window.location='reportgen.jsp'" class="button">


</form>



</div>
<%@ include file="../../part3.jspf" %>