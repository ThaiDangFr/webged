<%@ include file="import.jspf" %>

<div class="sideBarText">
<ul>
<c:forEach var="m" items="${usernavig.modules}">
<li><a href="${PREFIX}/${m.jsp}" title="${m.name}"><c:out value="${m.name}"/></a></li>
</c:forEach>
</ul>
</div>