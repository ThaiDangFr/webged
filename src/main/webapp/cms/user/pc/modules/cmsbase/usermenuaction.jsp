<%@ include file="import.jspf" %>

<c:set var="URLBASE" value="/cms/user/pc/modules" scope="page"/>


<%-- ACTION --%>
<c:if test="${param.action eq 'authent'}">
	<jsp:setProperty name="usersession" property="login" value="${param.login}"/>
	<jsp:setProperty name="usersession" property="password" value="${param.password}"/>
	<jsp:setProperty name="adminsession" property="login" value="${param.login}"/>
	<jsp:setProperty name="adminsession" property="password" value="${param.password}"/>
	<c:if test="${usersession.loginUser}"/>
	<c:if test="${adminsession.loginUser}"/>
	<jsp:setProperty name="usermenu" property="userId" value="${usersession.userId}"/>
	<jsp:setProperty name="inbox" property="userId" value="${usersession.userId}"/>
</c:if>


<c:choose>
	<c:when test="${not empty param.autoredirect}">
		<c:redirect url="${param.autoredirect}"/>
	</c:when>
	<c:otherwise>
		<c:redirect url="${URLBASE}/${usernavig.firstPageURL}"/>
	</c:otherwise>
</c:choose>

<%-- 
If you are not redirected, click <a href="${URLBASE}/${usernavig.firstPageURL}">here</a>

<c:redirect url="${URLBASE}/${usernavig.firstPageURL}"/>

--%>