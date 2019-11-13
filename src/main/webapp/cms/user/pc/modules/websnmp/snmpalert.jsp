<%@ include file="include.jspf" %>

<c:if test="${param.action eq 'chPage'}">
    <sr:set bean="${usnmpalert}" property="offset"/>
</c:if>


<%@ include file="../../part1.jspf" %>

<table id="alert">
<tr class="title"><th>Id</th><th>Date</th><th>Type</th><th><fmt:message key='level'/></th><th>Source</th><th>Description</th></tr>


<c:set var="alerts" value="${usnmpalert.snmpAlert}"/>
<c:choose>

    <c:when test="${not empty alerts}">
        <c:forEach items="${alerts}" var="a">
	    
	        <c:choose>
		    <c:when test="${a.level eq 'Warning'}"><tr class="warning"></c:when>
		    <c:when test="${a.level eq 'Debug'}"><tr></c:when>
		    <c:when test="${a.level eq 'Informational'}"><tr></c:when>
		    <c:when test="${a.level eq 'Notice'}"><tr></c:when>
		    <c:otherwise><tr class="error"></c:otherwise>
		</c:choose>

	        <td><c:out value="${a.id}"/></td>
		<td><c:choose><c:when test="${a.pollingType}"><a href="graph.jsp?reportId=${a.reportId}&beginTime=${a.beginTime}&endTime=${a.endTime}"><c:out value="${a.date}"/></a></c:when><c:otherwise><c:out value="${a.date}"/></c:otherwise></c:choose></td>
		<td><c:out value="${a.type}"/></td>
		<td><c:out value="${a.level}"/></td>
		<td><c:out value="${a.hostName}"/></td>
		<td><c:out value="${a.description}"/></td>
		</span>
	    </tr>
	</c:forEach>
    </c:when>

    <c:otherwise>
        <tr><td colspan="6"><span class="info"><fmt:message key='no.alerts'/></span></td></tr>
    </c:otherwise>

</c:choose>

</table>


<c:set var="po" value="${usnmpalert.pageOptions}"/>
<c:if test="${not empty po}">
    <form method="post" name="pageform">
    <input type="hidden" name="action" value="chPage"/>
       Page N<sr:select name="offset" options="${po}" defaultOption="${usnmpalert.offset}" attributesText="onChange='document.pageform.submit();'"/>
    </form>
</c:if>


<%@ include file="../../part2.jspf" %>