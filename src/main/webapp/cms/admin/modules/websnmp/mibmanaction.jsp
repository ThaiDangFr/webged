
<c:choose>
    <c:when test="${param.action eq 'upload'}">
        <c:set target="${adminMib}" property="request" value="${pageContext.request}"/>
	<sr:do bean="${adminMib}" property="upload" redirectOk="mibman.jsp"/>
    </c:when>

    <c:when test="${param.action eq 'delete'}">
        <sr:set bean="${adminMib}" property="mibId"/>
	<sr:do bean="${adminMib}" property="delete" redirectOk="mibman.jsp"/>
    </c:when>
</c:choose>