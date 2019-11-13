<%@ include file="import.jspf" %>
<c:if test="${usersession.logoutUser}"/>
<c:if test="${adminsession.logoutUser}"/>
<c:redirect url="../../index.jsp" />