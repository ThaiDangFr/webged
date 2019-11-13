<%@ include file="include.jspf" %>

<c:set var="nbexpiredgedfiles" value="${utodo.countExpiredGedFiles}"/>
<c:set var="nbworkflowgedfiles" value="${utodo.countWorkflowGedFiles}"/>

<div class="sideBarText">

<c:choose>
	<c:when test="${nbexpiredgedfiles==0 && nbworkflowgedfiles==0}">
		<fmt:message key='no.task'/>
	</c:when>

	<c:otherwise>
		<ul>
			<c:if test="${nbexpiredgedfiles != 0}">
		    	<li><sr:a href="${PREFIX}/ged/todo.jsp?view=expired"><fmt:message key='ged.expired.files'/> <span class="error">(<c:out value="${nbexpiredgedfiles}"/>)</span></sr:a></li>
		    </c:if>
		    <c:if test="${nbworkflowgedfiles != 0}">
				<li><sr:a href="${PREFIX}/ged/todo.jsp?view=workflow"><fmt:message key='ged.workflow.files'/> <span class="error">(<c:out value="${nbworkflowgedfiles}"/>)</span></sr:a></li>
			</c:if>
		</ul>
	</c:otherwise>
</c:choose>

</div>