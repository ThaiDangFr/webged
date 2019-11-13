<%@ include file="include.jspf" %>
<%@ include file="importmoduleid.jspf" %>


<%-- INIT --%>
<c:set target="${utrash}" property="userId" value="${usersession.userId}"/>


<%-- ACTION --%>
<c:if test="${param.action eq 'restore'}">
    <c:set target="${utrash}" property="fileIds" value="${paramValues.fileId}"/>
    <sr:set bean="${utrash}" property="directoryId"/>
    <sr:do bean="${utrash}" property="restore"/>
</c:if>


<%@ include file="../../part1.jspf" %>

<c:set var="t" value="${utrash.trash}"/>


<c:choose>

    <c:when test="${empty t}">
        <span><fmt:message key='nothing.trash'/></span>
    </c:when>


    <c:otherwise>
        <form method="post">
	<input type="hidden" name="action" value="restore">

	<table>
	<caption><fmt:message key='restore.files'/></caption>
	<tr><th></th><th><fmt:message key='name'/></th><th><fmt:message key='author'/></th><th>Date</th><th><fmt:message key='size'/></th></tr>
	    <c:forEach var="f" items="${t}">
	        <tr>
		    <td><input type="checkbox" name="fileId" value="${f.id}" /></td>
		    <td><c:out value="${f.name}"/></td>
		    <td><c:out value="${f.login}"/></td>
		    
		    <fmt:parseDate value="${f.versionDate}" pattern="yyyy-MM-dd HH:mm:ss" var="fdate" parseLocale="en_US"/>
		    <fmt:formatDate type="both" value="${fdate}" dateStyle="short" timeStyle="medium" var="fdate"/>
		    <td><c:out value="${fdate}"/></td>

		    <td><sr:out value="${f.size}" unit="o"/></td>
		</tr>
	    </c:forEach>
	</table>


	<fmt:message key='move.to'/>
	<sr:select name="directoryId" multiple="false"
	options="${ubrowser.writableDirectoryOption}"
	defaultOption="${ufile.directoryId}"/>

	<input type="submit" value="<fmt:message key='save'/>"/>
	</form>
    </c:otherwise>
</c:choose>


<p><input type="button" value="<fmt:message key='back'/>" onClick="window.location='browser.jsp'"/></p>

<%@ include file="../../part2.jspf" %>