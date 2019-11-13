<%@ include file="include.jspf" %>
<c:set var="PREFIX"  value="/openplatform/cms/user/mobile/modules" scope="page"/>

<sr:xhtml>
    <sr:head title="WebSNMP"/>
    <sr:body title="WebSNMP">

<c:set var="hl" value="${usnmpaction.hostList}"/>

<c:choose>
    <c:when test="${not empty hl}">
        <p>Host list</p>
        <ul>
            <c:forEach var="h" items="${hl}">
	        <li>
	        	<sr:a href="snmpaction.jsp?hostId=${h.snmpHostId}">
	        		<c:out value="${h.displayName}"/>
	        	</sr:a>
	        </li>
	    </c:forEach>
	</ul>
    </c:when>

    <c:otherwise>
        <p>No hosts found</p>
    </c:otherwise>
</c:choose>


	<sr:a href="index.jsp">Back</sr:a>
    </sr:body>
</sr:xhtml>