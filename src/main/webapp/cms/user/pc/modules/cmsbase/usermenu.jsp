<%@ include file="import.jspf" %>


<%-- PAGE --%>
<div class="sideBarText">
<c:choose>

    <c:when test="${usersession.connected}">
        <c:set var="newmsg" scope="page" value="${inbox.newMessages}"/>
		<ul>
		    <li><a href="${PREFIX}/cmsbase/useredit.jsp"><fmt:message key='edit'/></a></li>
		    <li><a href="${PREFIX}/cmsbase/inbox.jsp"><fmt:message key='inbox'/><c:if test="${newmsg ne '0'}"><span class="error">(<c:out value="${newmsg}"/>)</span></c:if></a></li>
		    <c:if test="${usermenu.admin}">
		        <li><a href="/openplatform/cms/admin/index.jsp">Administration</a></li>
		    </c:if>
	
		    <li><a class="logout" href="${PREFIX}/cmsbase/logout.jsp"><fmt:message key='logout'/>&nbsp;<c:out value="${usersession.login}"/></a></li>
		</ul>
    </c:when>

    <c:when test="${not usersession.loginOpened}">
        <a href="/openplatform/cms/admin/license.jsp">Please update your license</a>
    </c:when>

    <c:otherwise>
		<form method="post" name="usermenuform" action="${PREFIX}/cmsbase/usermenuaction.jsp">
		    <input type="hidden" name="action" value="authent"/>
		    <input type="hidden" name="autoredirect" value=""/>
		    <ul>
		        <li><fmt:message key='authent.login'/></li>
				<li><input type="text" name="login" size="5"/></li>
				<li><fmt:message key='authent.password'/></li>
				<li><input type="password" name="password" size="5"/></li>
				<li><input type="submit" value="OK"/></li>
		    </ul>
		</form>
	
	    <SCRIPT LANGUAGE="JavaScript">
	    	document.forms['usermenuform'].autoredirect.value=location.href;
		</SCRIPT>
	
    </c:otherwise>

</c:choose>
</div>