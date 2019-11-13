<%@ include file="include.jspf" %>
<c:set var="PREFIX"  value="/openplatform/cms/user/mobile/modules" scope="page"/>

<sr:set bean="${usnmpgraph}" property="reportId|beginTime|endTime"/>

<sr:xhtml>
    <sr:head title="WebSNMP"/>
    <sr:body title="WebSNMP">

<c:set var="r" value="${usnmpaction.result}"/>

<c:choose>
    <c:when test="${not empty r}">
        <c:forEach var="v" items="${r}">
	    <p><c:out value="${v}"/></p>
	</c:forEach>
    </c:when>

    <c:otherwise>
        <p>Action executed. No information to display</p>
    </c:otherwise>
</c:choose>


	<sr:a href="snmpaction.jsp">Back</sr:a>
    </sr:body>
</sr:xhtml>