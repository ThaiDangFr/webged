<%@ include file="include.jspf" %>
<c:set var="PREFIX"  value="/openplatform/cms/user/mobile/modules" scope="page"/>


<c:if test="${not empty param.hostId}">
    <c:set target="${usnmpaction}" property="hostId" value="${param.hostId}"/>
</c:if>

<c:if test="${param.action eq 'Request'}">
    <sr:set bean="${usnmpaction}" property="id|value"/>
    <sr:do bean="${usnmpaction}" property="action" redirectOk="snmpactionresult.jsp"/>
</c:if>


<sr:xhtml>
    <sr:head title="WebSNMP"/>
    <sr:body title="WebSNMP">


<c:set var="al" value="${usnmpaction.snmpActionList}"/>
<c:choose>
    <c:when test="${not empty al}">
        <c:forEach var="a" items="${al}">
	    <ul>
	    <c:choose>
	        <c:when test="${a.getType}">
		    <li>
		    <sr:form method="post" action="snmpaction.jsp" submitstring="${a.name}">
		    <sr:input type="hidden" name="action" value="Request"/>
		    <sr:input type="hidden" name="id" value="${a.id}"/>
		    </sr:form>
		    </li>
		</c:when>
		<c:otherwise>
		    <li>
		    <sr:form method="post" action="snmpaction.jsp" submitstring="${a.name}">
		    <sr:input type="hidden" name="action" value="Request"/>
		    <sr:input type="hidden" name="id" value="${a.id}"/>
		    <sr:input type="text" name="value" size="25"/>
		    </sr:form>
		    </li>
		</c:otherwise>
	    </c:choose>
	    </ul>
	</c:forEach>
    </c:when>

    <c:otherwise>
        <p>No actions found</p>
    </c:otherwise>
</c:choose>


	<sr:a href="index.jsp">Back</sr:a>
    </sr:body>
</sr:xhtml>