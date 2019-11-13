<%@ include file="include.jspf" %>



<%@ include file="../../part1.jspf" %>

<c:set var="hl" value="${usnmpaction.hostList}"/>

<c:choose>
    <c:when test="${not empty hl}">
        <h2><fmt:message key='host.list'/></h2>
        <ul id="hostlist">
            <c:forEach var="h" items="${hl}">
	        <li><a href="snmpaction.jsp?hostId=${h.snmpHostId}"><c:out value="${h.displayName}"/></a></li>
	    </c:forEach>
	</ul>
    </c:when>

    <c:otherwise>
        <p><span class="info"><fmt:message key='no.hosts'/></span></p>
    </c:otherwise>
</c:choose>

<%@ include file="../../part2.jspf" %>