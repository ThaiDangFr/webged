<jsp:useBean id="mimetypes" scope="session" class="dang.applimgt.AdminMimeTypes" />

<%-- ACTION BEGIN--%>
<c:choose>
    <c:when test="${param.action eq 'add'}">
        <sr:set bean="${mimetypes}" property="extension|primaryType|secondaryType"/>
	<sr:do bean="${mimetypes}" property="add"/>
    </c:when>

    <c:when test="${param.action eq 'delete'}">
        <sr:set bean="${mimetypes}" property="mimetypesId"/>
	<sr:do bean="${mimetypes}" property="delete"/>
    </c:when>
</c:choose>



<%@ include file="part1.jspf" %>
<%@ include file="part2.jspf" %>
<div id="main-copy">


<form method="post">
<input type="hidden" name="action" value="add"/>
<table class="horizontal">
    <caption><fmt:message key='mimetypes'/></caption>
    <tr>
        <th>Extension</th>
	<td><input type="text" name="extension"/></td>
    </tr>

    <tr>
        <th><fmt:message key='primary.type'/></th>
	<td><input type="text" name="primaryType"/></td>
    </tr>

    <tr>
        <th><fmt:message key='secondary.type'/></th>
	<td><input type="text" name="secondaryType"/></td>
    </tr>
</table>

<input type="submit" value="<fmt:message key='add'/>"/>
</form>

<br/>

<form method="post">
<input type="hidden" name="action" value="delete"/>
<sr:select name="mimetypesId" options="${mimetypes.mimeTypesOption}"/>
<input type="submit" value="<fmt:message key='delete'/>"/>
</form>



</div>
<%@ include file="part3.jspf" %>