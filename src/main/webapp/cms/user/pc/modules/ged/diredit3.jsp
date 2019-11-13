<%@ include file="include.jspf" %>
<%@ include file="importmoduleid.jspf" %>




<%-- ACTION --%>

<c:choose>
    <c:when test="${param.action eq 'finish'}">
        <sr:do bean="${udir}" property="saveDir" redirectOk="browser.jsp"/>
    </c:when>

    <c:when test="${param.action eq 'deleteGroup'}">
        <c:set target="${udir}" property="index" value="${param.index}"/>
	<sr:do bean="${udir}" property="deleteNotifGroup" redirectOk="diredit3.jsp"/>
    </c:when>

    <c:when test="${param.action eq 'addGroup'}">
        <c:set target="${udir}" property="groupId" value="${param.groupId}"/>
	<sr:do bean="${udir}" property="addNotifGroup"/>
    </c:when>
</c:choose>




<%@ include file="../../part1.jspf" %>

<ul id="step">
    <li class="inactive"><span>General</span></li>
    <li class="inactive"><span><fmt:message key='rights'/></span></li>
    <li class="active"><span>Notification</span></li>
</ul>




<table class="horizontal">
    <caption>Notification</caption>
    <tr>
        <th><fmt:message key='groups'/></th>
	<td>
	    <c:forEach items="${udir.notifGroups}" var="g" varStatus="vs">
	        <c:out value="${g.name}"/><a href="${PREFIX}/ged/diredit3.jsp?action=deleteGroup&index=${vs.count-1}">[X]</a>
	    </c:forEach>
	</td>
	<td>
	    <form method="post">
	        <input type="hidden" name="action" value="addGroup">
		<sr:select name="groupId" multiple="false" options="${udir.groupOption}"/>
		<input type="submit" value="<fmt:message key='add'/>">
	    </form>
	</td>
    </tr>
</table>











<form method="post" name="dirForm">
<input type="hidden" name="action" value="finish">
</form>


<p>
<input type="button" value="<fmt:message key='previous'/>" onClick="window.location='${PREFIX}/ged/diredit2.jsp'">
</p>

<p>
<input type="button" value="<fmt:message key='save'/>" onClick="document.dirForm.submit();">
<input type="button" value="<fmt:message key='cancel'/>" onClick="window.location='${PREFIX}/ged/browser.jsp'">
</p>


<%@ include file="../../part2.jspf" %>