<jsp:useBean id="module" scope="session" class="dang.cms.AdminModule" />
<jsp:useBean id="aworkflow" scope="session" class="dang.ged.AdminWorkflow"/>

<%-- INIT --%>
<sr:do bean="${aworkflow}" property="newWorkflow"/>

<%-- ACTION --%>
<c:choose>
<c:when test="${param.action eq 'deleteWorkflow'}">
<c:forEach items="${paramValues.workflowId}" var="id">
<sr:set bean="${aworkflow}" property="workflowId"/>
<sr:do bean="${aworkflow}" property="deleteWorkflow"/>
</c:forEach>
</c:when>

<c:when test="${param.action eq 'editWorkflow'}">
<c:set target="${aworkflow}" property="workflowId" value="${param.id}"/>
<sr:do bean="${aworkflow}" property="editWorkflow" redirectOk="editworkflow.jsp"/>
</c:when>

<c:when test="${param.action eq 'newWorkflow'}">
<sr:do bean="${aworkflow}" property="newWorkflow" redirectOk="editworkflow.jsp"/>
</c:when>
</c:choose>






<%@ include file="../../part1.jspf" %>
<div id="main-copy">
<%@ include file="../../part2.jspf" %>

<a href="${PREFIX}/modules.jsp?page=modules">Modules</a> > <a href="${PREFIX}/modules.jsp?page=modules&moduleId=${module.id}"><c:out value="${module.name}"/></a> > <fmt:message key='settings'/>

<ul>
<li><a href="${PREFIX}/modules/ged/template.jsp"><fmt:message key='ged.template'/></a></li>
<li>Workflow</li>
<li><a href="${PREFIX}/modules/ged/pref.jsp"><fmt:message key='preferences'/></a></li>
<li><a href="${PREFIX}/modules/ged/gedstate.jsp"><fmt:message key='ged.state'/></a></li>
</ul>

<br><br>



<c:set var="tpllist" value="${aworkflow.workflowList}"/>
<form method="post" onSubmit="return confirmDelete()">
<input type="hidden" name="action" value="deleteWorkflow">
<table>
<caption>Workflow</caption>
<tr><th colspan="100%">
<input type="button" value="<fmt:message key='new'/>" onClick="window.location='${PREFIX}/modules/ged/workflow.jsp?action=newWorkflow'">
<c:if test="${not empty tpllist}"><input type="submit" value="<fmt:message key='delete'/>"></c:if>
</th></tr>
<c:forEach var="l" items="${tpllist}">
<tr>
<td><input type="checkbox" name="workflowId" value="${l.id}"></td>
<td><c:out value="${l.name}"/></td>
<td><a href="workflow.jsp?action=editWorkflow&id=${l.id}"><fmt:message key='edit'/></a></td>
</tr>
</c:forEach>
</table>
</form>

</div>
<%@ include file="../../part3.jspf" %>