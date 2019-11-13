<%@ include file="import.jspf" %>
<c:set var="PREFIX"  value="/openplatform/cms/user/mobile/modules" scope="page"/>

<c:forEach var="m" items="${usernavig.modules}">
	<sr:a href="${PREFIX}/${m.jsp}"><c:out value="${m.name}"/></sr:a> |
</c:forEach>