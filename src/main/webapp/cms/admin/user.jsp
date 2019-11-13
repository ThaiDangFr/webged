<jsp:useBean id="userb" scope="session" class="dang.applimgt.AdminUser" />

<%-- ACTION --%>
<c:choose>
<c:when test="${param.action eq 'save'}">
    <sr:set bean="${userb}" property="login|password|email|expiryTime"/>
    <sr:do bean="${userb}" property="store"/>
</c:when>

<c:when test="${param.action eq 'newuser'}">
    <sr:do bean="${userb}" property="newUser"/>
</c:when>

<c:when test="${param.action eq 'edit'}">
    <jsp:setProperty name="userb" property="userId" value="${param.userId}"/>
</c:when>

<c:when test="${param.action eq 'delete'}">
    <c:set target="${userb}" property="userId" value="${param.userId}"/>
    <sr:do bean="${userb}" property="delete"/>
</c:when>
</c:choose>




<%@ include file="part1.jspf" %>
<%@ include file="part2.jspf" %>
<div id="main-copy">



<form method="post">
<input type="hidden" name="action" value="newuser">
<input type="submit" value="<fmt:message key='new'/>">
</form>

<form method="post">
<input type="hidden" name="action" value="edit">
<sr:select name="userId" multiple="false" options="${userb.userListOptions}" defaultOption="${userb.userId}"/>
<input type="submit" value="<fmt:message key='edit'/>">
</form>

<c:choose>

<c:when test="${userb.userId eq '1'}">
<%-- ADMIN --%>
<form method="post">
<input type="hidden" name="action" value="save">

<table class="horizontal">
<tr><th><fmt:message key='authent.login'/></th>       <td><c:out value="${userb.login}"/><input type="hidden" name="login" value="${userb.login}"/></td></tr>
<tr><th><fmt:message key='authent.password'/></th>	 <td><sr:input type="text" name="password" value="${userb.password}"/></td></tr>
<tr><th>Email</th>	 <td><sr:input type="text" name="email" value="${userb.email}"/></td></tr>
</table>
<input type="submit" value="<fmt:message key='save'/>">
</form>
</c:when>

<c:otherwise>
<%-- OTHERS  --%>
<form method="post">
<input type="hidden" name="action" value="save">
<table class="horizontal">
<tr><th><fmt:message key='authent.login'/></th>       <td><sr:input type="text" name="login" value="${userb.login}"/></td></tr>
<tr><th><fmt:message key='authent.password'/></th>	 <td><sr:input type="text" name="password" value="${userb.password}"/></td></tr>
<tr><th>Email</th>	 <td><sr:input type="text" name="email" value="${userb.email}"/></td></tr>
<tr><th><fmt:message key='expiry'/> (<fmt:message key='days'/>)</th> <td><sr:input type="text" name="expiryTime" value="${userb.expiryTime}"/></td></tr>
</table>
<input type="submit" value="<fmt:message key='save'/>">
</form>

<c:if test="${not empty userb.userId}">
<br/>
<form method="post">
<input type="hidden" name="action" value="delete">
<input type="hidden" name="userId" value="${userb.userId}">
<input type="submit" value="<fmt:message key='delete'/>&nbsp;<c:out value='${userb.login}'/>">
</form>
</c:if>
</c:otherwise>
</c:choose>


</div>
<%@ include file="part3.jspf" %>