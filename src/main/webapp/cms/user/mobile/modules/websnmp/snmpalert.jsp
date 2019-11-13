<%@ include file="include.jspf" %>
<c:set var="PREFIX"  value="/openplatform/cms/user/mobile/modules" scope="page"/>


<c:if test="${param.action eq 'chPage'}">
    <sr:set bean="${usnmpalert}" property="offset"/>
</c:if>

<sr:xhtml>
    <sr:head title="WebSNMP"/>
    <sr:body title="WebSNMP">



<c:set var="alerts" value="${usnmpalert.snmpAlert}"/>
<c:choose>

    <c:when test="${not empty alerts}">
        <sr:table columns="6">
	<tr><td>Id</td><td>Date</td><td>Type</td><td>Level</td><td>Source</td><td>Description</td></tr>
        <c:forEach items="${alerts}" var="a">
	    <tr>
	        <td><c:out value="${a.id}"/></td>		
		<td><c:choose><c:when test="${a.pollingType}">
		<sr:a href="graph.jsp?reportId=${a.reportId}&amp;beginTime=${a.beginTime}&amp;endTime=${a.endTime}">
			<c:out value="${a.date}"/>
		</sr:a>
		</c:when><c:otherwise><c:out value="${a.date}"/></c:otherwise></c:choose></td>

		<td><c:out value="${a.type}"/></td>
		<td><c:out value="${a.level}"/></td>
		<td><c:out value="${a.hostName}"/></td>
		<td><c:out value="${a.description}"/></td>
	    </tr>
	</c:forEach>
	</sr:table>
    </c:when>

    <c:otherwise>
        No alerts found<br/>
    </c:otherwise>

</c:choose>




<c:set var="po" value="${usnmpalert.pageOptions}"/>
<c:if test="${not empty po}">
    <sr:form method="post" submitstring="Ok" action="snmpalert.jsp">
    <sr:input type="hidden" name="action" value="chPage"/>
    <sr:select name="offset" options="${po}" defaultOption="${usnmpalert.offset}"/>
    </sr:form>
</c:if>






	<sr:a href="index.jsp">Back</sr:a>
    </sr:body>
</sr:xhtml>