<jsp:useBean id="groupsb" scope="session" class="dang.applimgt.AdminGroupsBean" />

<%-- ACTION --%>
<fmt:message key="edit" var="i18n_edit"/>
<fmt:message key="delete" var="i18n_delete"/>
<fmt:message key="new" var="i18n_new"/>
<fmt:message key="save" var="i18n_save"/>


<c:choose>
    <c:when test="${param.action eq i18n_edit}">
        <sr:set bean="${groupsb}" property="groupId"/>
	<sr:do bean="${groupsb}" property="edit"/>
    </c:when>
    <c:when test="${param.action eq i18n_delete}">
        <sr:set bean="${groupsb}" property="groupId"/>
	<sr:do bean="${groupsb}" property="delete"/>
    </c:when>
    <c:when test="${param.action eq i18n_new}">
        <sr:do bean="${groupsb}" property="new"/>
    </c:when>
    <c:when test="${param.action eq i18n_save}">
        <sr:set bean="${groupsb}" property="groupId|name|comment"/>
        <sr:do bean="${groupsb}" property="save"/>
    </c:when>
</c:choose>



<%@ include file="part1.jspf" %>
<%@ include file="part2.jspf" %>
<div id="main-copy">


<form method="post">
<table class="horizontal">
    <tr>
        <th><fmt:message key='groups'/></th>
	<td><sr:select name="groupId" multiple="false" options="${groupsb.groupsOption}" defaultOption="${groupsb.groupId}"/></td>
	<td>
	    <input type="submit" name="action" value="${i18n_edit}"/>
	    <input type="submit" name="action" value="${i18n_delete}"/>
	    <input type="submit" name="action" value="${i18n_new}"/>
	</td>
    </tr>
</table>
</form>



<form method="post">

<c:if test="${not empty groupsb.groupId}">
    <input type="hidden" name="groupId" value="${groupsb.groupId}"/>
</c:if>

<table class="horizontal">
    <tr>
        <th><fmt:message key='name'/></th>
	<td><sr:input type="text" name="name" value="${groupsb.name}"/></td>
    </tr>
    <tr>
        <th><fmt:message key='comments'/></th>
	<td><sr:input type="text" name="comment" value="${groupsb.comment}"/></td>
    </tr>
</table>

<input type="submit" name="action" value="${i18n_save}"/>
</form>

</div>
<%@ include file="part3.jspf" %>