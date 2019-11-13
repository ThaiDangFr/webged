<%@ include file="import.jspf" %>
<%@ include file="importmoduleid.jspf" %>


<%@ include file="../../part1.jspf" %>

<c:choose>
<c:when test="${param.type eq 'all'}">
<h1 style="border-top: none; padding-top: 0;"><fmt:message key='friends.list'/></h1>
<fmt:message key='all'/> | <a href="${PREFIX}/cmsbase/inboxcontact.jsp?type=friends"><fmt:message key='friends'/></a><br/>

<table class="vertical">
<caption><fmt:message key='friends.list'/></caption>
<tr><th><fmt:message key='authent.login'/></th><th><fmt:message key='firstname'/></th><th><fmt:message key='lastname'/></th></tr>
<c:forEach var="u" items="${inboxcontact.allUsers}">
<tr><td><a href="${PREFIX}/cmsbase/inboxcompose.jsp?toUserId=${u.userId}"><c:out value="${u.login}"/></a></td><td><c:out value="${u.firstName}"/></td><td><c:out value="${u.lastName}"/></td></tr>
</c:forEach>
</table>

</c:when>



<c:when test="${param.type eq 'friends' or empty param.type}">

<c:choose>
<c:when test="${param.action eq 'remove'}">
<jsp:setProperty name="inboxcontact" property="removeFriend" value="${param.friendId}"/>
</c:when>
<c:when test="${param.action eq 'add'}">
<jsp:setProperty name="inboxcontact" property="addFriends" value="${paramValues.friendId}"/>
</c:when>
</c:choose>

<h1 style="border-top: none; padding-top: 0;"><fmt:message key='friends.list'/></h1>
<a href="${PREFIX}/cmsbase/inboxcontact.jsp?type=all"><fmt:message key='all'/></a> | <fmt:message key='friends'/><br/>

<table class="contact">
<tr><td>
<form method="post" action="${PREFIX}/cmsbase/inboxcontact.jsp">
<input type="hidden" name="type" value="friends">
<input type="hidden" name="action" value="add">
<sr:select name="friendId" multiple="true" options="${inboxcontact.allUsersOption}" size="5"/>

</td>
<td>
<fmt:message key='friends.add' var="i18n_friendsadd"/>
<input type="submit" value="${i18n_friendsadd}">
</td>
</form>
<td>
<table class="vertical">
<caption><fmt:message key='friends.list'/></caption>
<tr><th></th><th><fmt:message key='authent.login'/></th><th><fmt:message key='firstname'/></th><th><fmt:message key='lastname'/></th></tr>
<jsp:setProperty name="inboxcontact" property="userId" value="${usersession.userId}"/>
<c:forEach var="u" items="${inboxcontact.friends}">
<tr><td><a class="noline" href="${PREFIX}/cmsbase/inboxcontact.jsp?type=friends&action=remove&friendId=${u.userId}"> [X]</a></td><td><a href="${PREFIX}/cmsbase/inboxcompose.jsp?toUserId=${u.userId}"><c:out value="${u.login}"/></a></td><td><c:out value="${u.firstName}"/></td><td><c:out value="${u.lastName}"/></td></tr>
</c:forEach>
</table>
</td>
</tr>
</table>

</c:when>
</c:choose>

<%@ include file="../../part2.jspf" %>