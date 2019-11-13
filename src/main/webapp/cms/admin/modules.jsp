<jsp:useBean id="module" scope="session" class="dang.cms.AdminModule" />

<%-- ACTION BEG --%>
<c:if test="${param.action eq 'save'}">
	<jsp:setProperty name="module" property="CSS" value="${param.CSS}"/>
	<jsp:setProperty name="module" property="visible" value="${param.visible}"/>
	<jsp:setProperty name="module" property="auth" value="${paramValues.auth}"/>
	<jsp:setProperty name="module" property="name" value="${param.name}"/>
	<sr:do bean="${module}" property="save"/>
</c:if>
<%-- ACTION END --%>

<%@ include file="part1.jspf" %>

<div id="main-copy">

<%@ include file="part2.jspf" %>

<%-- MENU BEG --%>
<c:set var="allmodules" value="${module.listModule}"/>

<c:choose>
<c:when test="${empty param.moduleId and not empty allmodules}">
<ul>
<c:forEach var="m" items="${allmodules}">
<li><a href="${PREFIX}/modules.jsp?page=modules&amp;moduleId=${m.id}"><c:out value="${m.name}"/></a></li>
</c:forEach>
</ul>
</c:when>

<c:when test="${empty allmodules}">
No modules<br><br><br><br><br><br><br><br><br><br><br><br><br>
</c:when>

<c:when test="${not empty param.moduleId}">
<jsp:setProperty name="module" property="moduleId" value="${param.moduleId}"/>
<a href="${PREFIX}/modules.jsp?page=modules">Modules</a> > <c:out value="${module.name}"/>
</c:when>
</c:choose>
<%-- MENU END --%>


<%-- CONFIG BEG --%>
<c:if test="${not empty param.moduleId}">

<form method="post">
<input type="hidden" name="action" value="save">

<table class="horizontal">
<caption><c:out value="${module.name}"/></caption>
<tr><th><fmt:message key="name"/></th><td><sr:input type="text" name="name" value="${module.name}"/></td></tr>
<tr><th><fmt:message key="edit"/> CSS</th><td><textarea name="CSS"><c:out value="${module.CSS}"/></textarea></td></tr>
<tr><th><fmt:message key="visibility"/></th><td><sr:select name="visible" multiple="false" options="${module.visibleOption}" defaultOption="${module.visible}"/></td></tr>
<tr><th><fmt:message key="authorization"/></th><td><sr:select name="auth" multiple="true" options="${module.authOption}" defaultOptionList="${module.authList}" size="5"/></td></tr>
</table>

<input type="submit" value="<fmt:message key='save'/>">
</form>

<c:if test="${module.advancedConf}"><a href="${PREFIX}/modules/${module.advancedConfURL}?page=modules"><fmt:message key="settings"/></a></c:if>
</c:if>
<%-- CONFIG END --%>
</div>
<%@ include file="part3.jspf" %>