<%@ include file="import.jspf" %>
<%@ include file="importmoduleid.jspf" %>

<%-- INIT --%>
<jsp:setProperty name="articleapprove" property="userId" value="${usersession.userId}"/>

<%-- ACTION --%>
<c:if test="${not usersession.connected}">
    <jsp:forward page="${FWPREFIX}/cmsbase/needlogin.jsp"/>
</c:if>


<%@ include file="../../part1.jspf" %>
<h1 style="border-top: none; padding-top: 0;"><fmt:message key='article.moderate'/></h1>

<table id="articlemod">
    <tr><th><fmt:message key='title'/></th><th><fmt:message key='category'/></th><th><fmt:message key='status'/></th><th>Action</th></tr>

    <c:set var="art" value="${articleapprove.article}" scope="page"/>
    <c:choose>
        <c:when test="${empty art}"><tr><td><fmt:message key='no.item.found'/></td></tr></c:when>

	<c:otherwise>
	    <c:forEach var="a" items="${art}">
	    <jsp:setProperty name="articleapprove" property="categoryId" value="${a.categoryId}"/>
	    <tr>
	        <td><c:out value="${a.title}"/></td>
		<td><c:out value="${articleapprove.categoryName}"/></td>
		<td><c:choose><c:when test="${a.validate eq '0'}"><fmt:message key='NotValidated'/></c:when><c:otherwise><fmt:message key='Validated'/></c:otherwise></c:choose></td>
		<td><a href="articlemoderate.jsp?articleId=${a.id}"><fmt:message key='edit'/></a></td>
	    </tr>
	    </c:forEach>
	</c:otherwise>
    </c:choose>
</table>

<%@ include file="../../part2.jspf" %>