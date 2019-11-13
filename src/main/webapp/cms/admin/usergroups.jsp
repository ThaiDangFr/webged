<jsp:useBean id="usergroup" scope="session" class="dang.applimgt.AdminUserGroupBean" />


<%-- ACTION BEGIN --%>
<c:if test="${not empty param.groupId}"><jsp:setProperty name="usergroup" property="group" value="${param.groupId}"/></c:if>

<c:choose>
    <c:when test="${param.action == 'add'}">
        <jsp:setProperty name="usergroup" property="selectedUsers" value="${paramValues.userId}"/>
	<sr:do bean="${usergroup}" property="add"/>
    </c:when>
    <c:when test="${param.action == 'remove'}">
        <jsp:setProperty name="usergroup" property="selectedUsers" value="${paramValues.userId}"/>
	<sr:do bean="${usergroup}" property="remove"/>
    </c:when>
</c:choose>
<%-- ACTION END --%>


<%@ include file="part1.jspf" %>
<%@ include file="part2.jspf" %>
<div id="main-copy">


<table>
<caption><fmt:message key='groups'/></caption>
<tr>
    <th><fmt:message key='name'/></th>
    <th><fmt:message key='description'/></th>
    <th/>
</tr>
<c:forEach var="group" items="${usergroup.listGroups}">
    
<tr>
    <td><c:out value="${group.name}"/></td>
    <td><c:out value="${group.comment}"/></td>
    <td><a href="usergroups.jsp?groupId=${group.groupId}"><fmt:message key='view'/></a></td>
</tr>


</c:forEach>
</table>


<c:if test="${not empty param.groupId}">

<table>
<caption><fmt:message key='usergroups'/></caption>
<tr>
    <th style="width:20%;"><fmt:message key='user.list'/></th>
    <th style="width:20%;" />
    <th><c:out value="${usergroup.groupName}"/></th>
</tr>


<tr>
    <td>
    <form name="add" method="post">
    <input type="hidden" name="action" value="add">
    <sr:select name="userId" options="${usergroup.usersNotInGroup}" multiple="true" size="5"/>
    </td>
    
    <td>
    <input type="submit" value="-->">
    </form>
    <form name="remove" method="post">
    <input type="hidden" name="action" value="remove">
    <input type="submit" value="<--">
    </td>
    
    <td>
    <sr:select name="userId" options="${usergroup.usersInGroup}" multiple="true" size="5"/>
    </form>
    </td>
</tr>



</table>
</c:if>


</div>
<%@ include file="part3.jspf" %>