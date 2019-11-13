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

If you are not redirected, click <sr:a href="../../index.jsp">here</sr:a>

<c:redirect url="../../index.jsp"/>