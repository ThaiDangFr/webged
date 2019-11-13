<%@ include file="include.jspf" %>
<%@ include file="importmoduleid.jspf" %>




<%-- ACTION --%>
<c:choose>

<c:when test="${param.action eq 'addReadGroup'}">
<c:set target="${udir}" property="groupId" value="${param.groupId}"/>
<sr:do bean="${udir}" property="addReadGroup"/>
</c:when>

<c:when test="${param.action eq 'addWriteGroup'}">
<c:set target="${udir}" property="groupId" value="${param.groupId}"/>
<sr:do bean="${udir}" property="addWriteGroup"/>
</c:when>


<c:when test="${param.action eq 'deleteReadGroup'}">
<c:set target="${udir}" property="index" value="${param.index}"/>
<sr:do bean="${udir}" property="deleteReadGroup" redirectOk="diredit2.jsp"/>
</c:when>

<c:when test="${param.action eq 'deleteWriteGroup'}">
<c:set target="${udir}" property="index" value="${param.index}"/>
<sr:do bean="${udir}" property="deleteWriteGroup" redirectOk="diredit2.jsp"/>
</c:when>

<c:when test="${param.action eq 'finish'}">
<sr:do bean="${udir}" property="saveDir" redirectOk="browser.jsp"/>
</c:when>

<c:when test="${param.action eq 'prev'}">
<sr:do bean="${udir}" property="saveDir" redirectOk="diredit.jsp.jsp"/>
</c:when>

<c:when test="${param.action eq 'next'}">
    <c:redirect url="diredit3.jsp"/>
</c:when>
</c:choose>




<%@ include file="../../part1.jspf" %>

<ul id="step">
    <li class="inactive"><span>General</span></li>
    <li class="active"><span><fmt:message key='rights'/></span></li>
    <li class="inactive"><span>Notification</span></li>
</ul>


<fmt:message key='add' var="i18n_add"/>

<table class="horizontal">
<caption><fmt:message key='rights'/></caption>
<tr>
<th><fmt:message key='ged.read'/></th>
<td>
<c:forEach items="${udir.readRights}" var="g" varStatus="vs">
<c:out value="${g.name}"/><a href="${PREFIX}/ged/diredit2.jsp?action=deleteReadGroup&index=${vs.count-1}">[X]</a>
</c:forEach>
</td>
<td>
<form method="post"><input type="hidden" name="action" value="addReadGroup">
<sr:select name="groupId" multiple="false" options="${udir.groupOption}"/>
<input type="submit" value="${i18n_add}">
</form>
</td>
</tr>

<tr>
<th><fmt:message key='ged.write'/></th>
<td>
<c:forEach items="${udir.writeRights}" var="g" varStatus="vs">
<c:out value="${g.name}"/><a href="${PREFIX}/ged/diredit2.jsp?action=deleteWriteGroup&index=${vs.count-1}">[X]</a>
</c:forEach>
</td>
<td>
<form method="post"><input type="hidden" name="action" value="addWriteGroup">
<sr:select name="groupId" multiple="false" options="${udir.groupOption}"/>
<input type="submit" value="${i18n_add}">
</form>
</td>
</tr>
</table>


<form method="post" name="dirForm">
<input type="hidden" name="action" value="next">
</form>


<p>
<input type="button" value="<fmt:message key='previous'/>" onClick="window.location='${PREFIX}/ged/diredit.jsp'">
<input type="button" value="<fmt:message key='next'/>" onClick="document.dirForm.submit();">
</p>

<p>
<input type="button" value="<fmt:message key='save'/>" onClick="document.dirForm.action.value='finish';document.dirForm.submit();">
<input type="button" value="<fmt:message key='cancel'/>" onClick="window.location='${PREFIX}/ged/browser.jsp'">
</p>

<%@ include file="../../part2.jspf" %>