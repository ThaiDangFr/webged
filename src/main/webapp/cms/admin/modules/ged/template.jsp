<jsp:useBean id="module" scope="session" class="dang.cms.AdminModule" />
<jsp:useBean id="atemplate" scope="session" class="dang.ged.AdminTemplate"/>

<%-- INIT --%>
<sr:do bean="${atemplate}" property="newTemplate"/>

<%-- ACTION --%>
<c:choose>
<c:when test="${param.action eq 'deleteTemplate'}">
<c:forEach items="${paramValues.templateId}" var="id">
<sr:set bean="${atemplate}" property="templateId"/>
<sr:do bean="${atemplate}" property="deleteTemplate"/>
</c:forEach>
</c:when>

<c:when test="${param.action eq 'editTemplate'}">
<c:set target="${atemplate}" property="templateId" value="${param.id}"/>
<sr:do bean="${atemplate}" property="editTemplate" redirectOk="edittemplate.jsp"/>
</c:when>

<c:when test="${param.action eq 'newTemplate'}">
<sr:do bean="${atemplate}" property="newTemplate" redirectOk="edittemplate.jsp"/>
</c:when>
</c:choose>






<%@ include file="../../part1.jspf" %>
<div id="main-copy">
<%@ include file="../../part2.jspf" %>

<a href="${PREFIX}/modules.jsp?page=modules">Modules</a> > <a href="${PREFIX}/modules.jsp?page=modules&moduleId=${module.id}"><c:out value="${module.name}"/></a> > <fmt:message key='settings'/>

<ul>
<li><fmt:message key='ged.template'/></li>
<li><a href="${PREFIX}/modules/ged/workflow.jsp">Workflow</a></li>
<li><a href="${PREFIX}/modules/ged/pref.jsp"><fmt:message key='preferences'/></a></li>
<li><a href="${PREFIX}/modules/ged/gedstate.jsp"><fmt:message key='ged.state'/></a></li>
</ul>

<br><br>



<c:set var="tpllist" value="${atemplate.templateList}"/>
<form method="post" onSubmit="return confirmDelete()">
<input type="hidden" name="action" value="deleteTemplate">
<table>
<caption><fmt:message key='ged.template'/></caption>
<tr><th colspan="100%">
<input type="button" value="<fmt:message key='new'/>" onClick="window.location='${PREFIX}/modules/ged/template.jsp?action=newTemplate'">
<c:if test="${not empty tpllist}"><input type="submit" value="<fmt:message key='delete'/>"></c:if>
</th></tr>
<c:forEach var="l" items="${tpllist}">
<tr>
<td><input type="checkbox" name="templateId" value="${l.id}"></td>
<td><c:out value="${l.name}"/></td>
<td><a href="template.jsp?action=editTemplate&id=${l.id}"><fmt:message key='edit'/></a></td>
</tr>
</c:forEach>
</table>
</form>

</div>
<%@ include file="../../part3.jspf" %>