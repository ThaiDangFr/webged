<%@ include file="import.jspf" %>
<c:set var="PREFIX"  value="/openplatform/cms/user/mobile/modules" scope="page"/>

<c:choose>
    <c:when test="${usersession.connected}">
        <sr:a href="${PREFIX}/cmsbase/logout.jsp">
        	<c:out value="Logout ${usersession.login}"/>
        </sr:a>
    </c:when>


    <c:when test="${usersession.loginOpened}">
        <sr:form method="post" action="${PREFIX}/cmsbase/usermenuaction.jsp" submitstring="Ok">
	    <sr:input type="hidden" name="action" value="authent"/>
	    <sr:table columns="2">
	        <tr><td><fmt:message key='authent.login'/></td><td><sr:input type="text" name="login" size="5"/></td></tr>
		<tr><td><fmt:message key='authent.password'/></td><td><sr:input type="password" name="password" size="5"/></td></tr>
	    </sr:table>
	</sr:form>
    </c:when>
</c:choose>