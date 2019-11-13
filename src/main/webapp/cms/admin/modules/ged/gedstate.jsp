<jsp:useBean id="module" scope="session" class="dang.cms.AdminModule" />
<jsp:useBean id="agedstate" scope="session" class="dang.ged.AdminGedState"/>


<%-- I18N --%>


<%-- ACTION --%>
<c:choose>
	<c:when test="${param.action eq 'del'}">
		<sr:set bean="${agedstate}" property="id"/>
		<sr:do bean="${agedstate}" property="deleteItem" redirectOk="gedstate.jsp"/>
	</c:when>
	<c:when test="${param.action eq 'up'}">
		<sr:set bean="${agedstate}" property="id"/>
		<sr:do bean="${agedstate}" property="itemUp" redirectOk="gedstate.jsp"/>
	</c:when>
	<c:when test="${param.action eq 'down'}">
		<sr:set bean="${agedstate}" property="id"/>
		<sr:do bean="${agedstate}" property="itemDown" redirectOk="gedstate.jsp"/>
	</c:when>	
	<c:when test="${param.action eq 'add' and param.type eq '0'}">
		<sr:set bean="${agedstate}" property="generalColumn|type"/>
		<sr:do bean="${agedstate}" property="addItem"/>
	</c:when>
	<c:when test="${param.action eq 'add' and param.type eq '1'}">
		<sr:set bean="${agedstate}" property="gedTemplateItem|type"/>
		<sr:do bean="${agedstate}" property="addItem"/>
	</c:when>	
</c:choose>



<%@ include file="../../part1.jspf" %>
<div id="main-copy">
<%@ include file="../../part2.jspf" %>

<a href="${PREFIX}/modules.jsp?page=modules">Modules</a> > <a href="${PREFIX}/modules.jsp?page=modules&moduleId=${module.id}"><c:out value="${module.name}"/></a> > <fmt:message key='settings'/> 

<ul>
<li><a href="${PREFIX}/modules/ged/template.jsp"><fmt:message key='ged.template'/></a></li>
<li><a href="${PREFIX}/modules/ged/workflow.jsp">Workflow</a></li>
<li><a href="${PREFIX}/modules/ged/pref.jsp"><fmt:message key='preferences'/></a></li>
<li><fmt:message key='ged.state'/></li>
</ul>

<br/><br/>

<table class="horizontal">
	<c:forEach var="hm" items="${agedstate.gedStateList}">
		<tr>
			<th>
				<c:choose>
					<c:when test="${hm.gsType eq '0'}">
						<fmt:message key="${hm.gsName}"/>
					</c:when>
					<c:otherwise>
						<c:out value="${hm.gsName}"/>
					</c:otherwise>
				</c:choose>
			</th>
			<td>
				<a href="gedstate.jsp?action=del&amp;id=${hm.gsId}"><fmt:message key='delete'/></a>|
				<a href="gedstate.jsp?action=up&amp;id=${hm.gsId}"><fmt:message key='up'/></a>|
				<a href="gedstate.jsp?action=down&amp;id=${hm.gsId}"><fmt:message key='down'/></a>
			</td>
		</tr>
	</c:forEach>
</table>

<form method="post">
<input type="hidden" name="action" value="add"/>
<table>
	<caption><fmt:message key='add'/></caption>
	<tr>
		<td>
			<input type="radio" name="type" value="0" checked> General
			<sr:select name="generalColumn" options="${agedstate.generalOptions}"/>
		</td>
	</tr>
	<tr>
		<td>
			<input type="radio" name="type" value="1"> <fmt:message key='ged.template'/>
			<sr:select name="gedTemplateItem" options="${agedstate.templateItemOptions}"/>
		</td>
	</tr>
	<tr>
		<td>
			<input type="submit" value="<fmt:message key='add'/>"/>
		</td>
	</tr>
</table>


</div>
<%@ include file="../../part3.jspf" %>
