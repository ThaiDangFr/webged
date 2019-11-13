<%@ include file="include.jspf" %>
<%@ include file="importmoduleid.jspf" %>


<%@ include file="../../part1.jspf" %>

<%--  CONTROL VIEW RIGHTS --%>
<c:if test="${not ufile.versionViewable}">
<c:redirect url="../cmsbase/noaccess.jsp"/>
</c:if>


<c:set var="filesversion" value="${ufile.filesVersion}"/>
<table>
<tr><th>Version</th><th>Date</th><th>Reference</th><th><fmt:message key='filename'/></th><th><fmt:message key='size'/></th><th><fmt:message key='author'/></th><th>Action</th></tr>
<caption>Version(s)</caption>
<c:forEach items="${filesversion}" var="fv" varStatus="vs">

<c:choose><c:when test="${vs.count eq '1'}"><tr class="bglightgrey"></c:when><c:otherwise><tr></c:otherwise></c:choose>
<td><c:out value="${fv.version}"/></td>

<fmt:parseDate value="${fv.versionDate}" pattern="yyyy-MM-dd HH:mm:ss" var="fdate" parseLocale="en_US"/>
<fmt:formatDate type="both" value="${fdate}" dateStyle="short" timeStyle="medium" var="fdate"/>
<td><c:out value="${fdate}"/></td>

<td><c:out value="${fv.reference}"/></td>
<td><c:out value="${fv.name}"/></td>
<td><sr:out value="${fv.size}" unit="o"/></td>
<td><c:out value="${fv.login}"/></td>

<td>
	<sr:a fileBaseId="${fv.fileBaseId}"><fmt:message key='download'/></sr:a>
</td>

</c:forEach>
</table>


<input type="button" value="<fmt:message key='back'/>" onClick="window.location='seefile.jsp'"/>

<%@ include file="../../part2.jspf" %>