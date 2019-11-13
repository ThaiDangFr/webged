<%@ include file="include.jspf" %>



<%@ include file="../../part1.jspf" %>

<c:set var="r" value="${usnmpaction.result}"/>

<div id="actionresult">
<c:choose>
    <c:when test="${not empty r}">
        <c:forEach var="v" items="${r}">
	    <pre wrap><c:out value="${v}"/></pre>
	</c:forEach>
    </c:when>

    <c:otherwise>
        <p><span class="info">Action executed. No information to display</span></p>
    </c:otherwise>
</c:choose>
</div>

<input type="button" value="<fmt:message key='back'/>" onClick="window.location='snmpaction.jsp'" />

<%@ include file="../../part2.jspf" %>