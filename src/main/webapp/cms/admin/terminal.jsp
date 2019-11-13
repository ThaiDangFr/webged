<jsp:useBean id="adminterm" scope="session" class="dang.applimgt.AdminTerminal" />

<%-- ACTION --%>  
<fmt:message key='update' var="i18n_update"/>
<fmt:message key='delete' var="i18n_delete"/>
<fmt:message key='add' var="i18n_add"/>

<c:choose>
    <c:when test="${param.action eq i18n_update}">
        <sr:set bean="${adminterm}" property="terminalId|userAgent|imageId|navType"/>
	<sr:do bean="${adminterm}" property="update"/>
    </c:when>

    <c:when test="${param.action eq i18n_delete}">
        <sr:set bean="${adminterm}" property="terminalId"/>
	<sr:do bean="${adminterm}" property="delete"/>
    </c:when>

    <c:when test="${param.action eq i18n_add}">
        <sr:set bean="${adminterm}" property="userAgent|imageId|navType"/>
	<sr:do bean="${adminterm}" property="add"/>
    </c:when>
</c:choose>



<%@ include file="part1.jspf" %>
<%@ include file="part2.jspf" %>
<div id="main-copy">


<table>
    <caption><fmt:message key='terminals'/></caption>

    <c:forEach var="t" items="${adminterm.terminals}" varStatus="vs">
        <form method="post" name="form${vs.count}">
	<input type="hidden" name="terminalId" value="${t.terminalId}"/>
	<input type="hidden" name="action" value=""/>
        <tr>
	    <td><sr:input type="text" name="userAgent" value="${t.userAgent}"/></td>
	    <td><sr:select name="imageId" options="${adminterm.imageOptions}" defaultOption="${t.imageId}"/></td>
	    <td><sr:select name="navType" options="${adminterm.navTypeOptions}" defaultOption="${t.navType}"/></td>
	    <td>
	        <input type="button" value="${i18n_update}" onClick="document.form${vs.count}.action.value='${i18n_update}';document.form${vs.count}.submit();"/>
		<input type="button" value="${i18n_delete}" onClick="if(confirmDelete()) {document.form${vs.count}.action.value='${i18n_delete}';document.form${vs.count}.submit();}"/>
	    </td>
	</tr>
	</form>
    </c:forEach>
</table>


<form method="post">
<table class="horizontal">
    <tr>
        <th>User Agent</th>
	<td><sr:input type="text" name="userAgent"/></td>
    </tr>
    <tr>
        <th>Image</th>
	<td><sr:select name="imageId" options="${adminterm.imageOptions}"/></td>
    </tr>
    <tr>
        <th>Type</th>
	<td><sr:select name="navType" options="${adminterm.navTypeOptions}"/></td>
    </tr>
</table>

<input type="submit" name="action" value="${i18n_add}"/>
</form>

</div>
<%@ include file="part3.jspf" %>