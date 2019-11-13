<%@ include file="include.jspf" %>
<%@ include file="importmoduleid.jspf" %>


<%@ include file="../../part1.jspf" %>

<div id="file">
<c:choose>
	<c:when test="${param.view eq 'expired'}">
		<table class="sortable" id="todo">
			<caption><fmt:message key='ged.expired.files'/></caption>
			<tr><th><fmt:message key='filename'/></th></tr>
			<c:forEach var="ef" items="${utodo.expiredGedFiles}">
			<tr>
				<td><sr:a href="browser.jsp?action=modifyFile&fileId=${ef.id}"><c:out value="${ef.pathName}"/></sr:a></td>
			</tr>
			</c:forEach>
		</table>
	</c:when>
	<c:when test="${param.view eq 'workflow'}">
		<table class="sortable" id="todo">
			<caption><fmt:message key='ged.workflow.files'/></caption>
			<tr><th><fmt:message key='filename'/></th><th><fmt:message key='timeleft'/></th><th><fmt:message key='workflow.step'/></th></tr>
			<c:forEach var="wf" items="${utodo.workflowGedFiles}">
			<tr>
				<td><sr:a href="browser.jsp?action=seeFile&fileId=${wf.id}"><c:out value="${wf.pathName}"/></sr:a></td>
				<td><c:out value="${wf.workflowTimeleft}"/></td>
				<td><c:out value="${wf.workflowStepExt.workflowName}"/></td>
			</tr>
			</c:forEach>
		</table>
	</c:when>
</c:choose>
</div>

<%@ include file="../../part2.jspf" %>