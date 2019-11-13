<%@ include file="include.jspf" %>
<%@ include file="importmoduleid.jspf" %>


<%-- ACTION --%>
<c:if test="${param.action eq 'save'}">
    <sr:set bean="${ufile}" property="directoryId"/>
    <sr:cmstrace moduleId="${MODULEID}" object="${ufile.pathName}" action="move" statusMsg="ok" statusUserId="${usersession.userId}" ipAddress="${pageContext.request.remoteAddr}"/>
    <sr:do bean="${ufile}" property="moveFile" redirectOk="browser.jsp"/>
</c:if>


<%@ include file="../../part1.jspf" %>

<form method="post">
<input type="hidden" name="action" value="save">

<table>
<caption><fmt:message key='move.to'/></caption>
<tr>
<td>
<sr:select name="directoryId" multiple="false"
options="${ubrowser.writableDirectoryOption}"
defaultOption="${ufile.directoryId}"/>
</td>
<td>
<fmt:message key='save' var="i18n_save"/>
<fmt:message key='back' var="i18n_back"/>
<input type="submit" value="${i18n_save}">
<input type="button" value="${i18n_back}" onClick="window.location='browser.jsp'">
</td>
</tr>
</table>

</form>

<%@ include file="../../part2.jspf" %>