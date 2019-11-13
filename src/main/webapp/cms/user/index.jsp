<c:choose>
    <c:when test="${usersession.mobile}">
        <c:redirect url="mobile"/>
    </c:when>
    <c:otherwise>
        <c:redirect url="pc"/>
    </c:otherwise>
</c:choose>