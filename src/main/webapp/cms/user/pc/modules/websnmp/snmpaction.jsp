<%@ include file="include.jspf" %>

<c:if test="${not empty param.hostId}">
    <c:set target="${usnmpaction}" property="hostId" value="${param.hostId}"/>
</c:if>

<c:if test="${param.action eq 'Request'}">
    <sr:set bean="${usnmpaction}" property="id|value"/>
    <sr:do bean="${usnmpaction}" property="action" redirectOk="snmpactionresult.jsp"/>
</c:if>


<%@ include file="../../part1.jspf" %>

<c:set var="al" value="${usnmpaction.snmpActionList}"/>
<c:choose>
    <c:when test="${not empty al}">
        <ul id="action">
        <c:forEach var="a" items="${al}">
	    <c:choose>
	        <c:when test="${a.getType}">
		    <li>
		    <form method="post">
		    <input type="hidden" name="action" value="Request"/>
		    <input type="hidden" name="id" value="${a.id}"/>
		    <input type="submit" value="<c:out value='${a.name}'/>"/>
		    </form>
		    </li>
		</c:when>
		<c:otherwise>
		    <li>
		    <form method="post">
		    <input type="hidden" name="action" value="Request"/>
		    <input type="hidden" name="id" value="${a.id}"/>
		    <input type="text" name="value" size="25"/>
		    <input type="submit" value="<c:out value='${a.name}'/>"/>
		    </form>
		    </li>
		</c:otherwise>
	    </c:choose>
	</c:forEach>
	</ul>
    </c:when>

    <c:otherwise>
        <p><span class="info">No actions found</span></p>
    </c:otherwise>
</c:choose>

<%@ include file="../../part2.jspf" %>