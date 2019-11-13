<%@ include file="include.jspf" %>
<%@ include file="importmoduleid.jspf" %>


<%-- INIT --%>
<c:set target="${ufile}" property="userId" value="${usersession.userId}"/>
<c:set var="displayApproveDisapprove" value="${ufile.displayApproveDisapprove}"/>
<c:set target="${ubrowser}" property="directoryId" value="${ufile.directoryId}"/>


<%-- CONTROL VIEW RIGHTS --%>
<c:if test="${not ufile.viewable}">
<c:redirect url="../cmsbase/noaccess.jsp"/>
</c:if>


<%-- ACTION --%>

<c:if test="${param.action eq 'recreateCache'}">
	<sr:do bean="${ufile}" property="recreateCache" />
</c:if>

<c:if test="${displayApproveDisapprove}">
<c:choose>

<c:when test="${param.action eq 'approve'}">
<sr:cmstrace moduleId="${MODULEID}" object="Workflow:${ufile.pathName}" action="approve" statusMsg="ok" statusUserId="${usersession.userId}" ipAddress="${pageContext.request.remoteAddr}"/>
<sr:do bean="${ufile}" property="approve" redirectOk="browser.jsp?action=browse&directoryId=${ufile.directoryId}"/>
</c:when>

<c:when test="${param.action eq 'disapprove'}">
<sr:cmstrace moduleId="${MODULEID}" object="Workflow:${ufile.pathName}" action="disapprove" statusMsg="ok" statusUserId="${usersession.userId}" ipAddress="${pageContext.request.remoteAddr}"/>
<sr:do bean="${ufile}" property="disapprove" redirectOk="browser.jsp?action=browse&directoryId=${ufile.directoryId}"/>
</c:when>

<c:when test="${param.action eq 'addComment'}">
<sr:set bean="${ufile}" property="commentSubject|commentText"/>
<sr:do bean="${ufile}" property="addComment"/>
</c:when>

</c:choose>
</c:if>


<%@ include file="../../part1.jspf" %>

<table class="horizontal">
    <caption>Description</caption>
    <tr><th><fmt:message key='filename'/></th><td><c:out value="${ufile.name}"/></td></tr>
    <tr><th><fmt:message key='size'/></th><td><sr:out value="${ufile.size}" unit="o"/></td></tr>
    <tr><th><fmt:message key='author'/></th><td><c:out value="${ufile.login}"/></td></tr>
        <fmt:parseDate value="${ufile.versionDate}" pattern="yyyy-MM-dd HH:mm:ss" var="fdate" parseLocale="en_US"/>
	<fmt:formatDate type="both" value="${fdate}" dateStyle="full" timeStyle="medium" var="fdate"/>
    <tr><th>Date</th><td><c:out value="${fdate}"/></td></tr>

    <tr><th><fmt:message key='reference'/></th><td><c:out value="${ufile.reference}"/></td></tr>

    <tr><th><fmt:message key='workflow.status'/></th><td><fmt:message key="${ufile.workflowStatus}"/></td></tr>

    <c:if test="${ufile.inWorkflow}">
        <c:set var="wdetail" value="${ufile.workflowStepExt}"/>
	<tr><th><fmt:message key='workflow.name'/></th><td><c:out value="${wdetail.workflowName}"/></td></tr>
	<tr><th><fmt:message key='workflow.step'/></th><td><c:out value="${wdetail.name}"/></td></tr>
	<tr><th><fmt:message key='workflow.user'/></th><td><c:out value="${wdetail.currentSendToLogin}"/></td></tr>
    </c:if>

    <tr><th/><td>


    	<c:choose>
    		<c:when test="${not ufile.writable and ufile.convert2pdf}">
    			<sr:a fileBaseId="${ufile.fileBaseId}" extension="pdf"><fmt:message key='download'/></sr:a>
    		</c:when>
    		<c:otherwise>
    			<sr:a fileBaseId="${ufile.fileBaseId}"><fmt:message key='download'/></sr:a>
    		</c:otherwise>
    	</c:choose>
    
    
	    <c:if test="${displayApproveDisapprove}">
	        | <a href="seefile.jsp?action=approve"><fmt:message key='approve'/></a>
		| <a href="seefile.jsp?action=disapprove"><fmt:message key='disapprove'/></a>
	    </c:if>
    </td></tr> 

</table>



<c:set var="tis" value="${ufile.templateItem}"/>
<c:if test="${not empty tis}">

<table class="horizontal">
<caption><fmt:message key='ged.template'/></caption>
<c:forEach items="${tis}" var="ti">
<tr>
<th><c:out value="${ti.fieldname}"/></th>
<td><c:out value="${ti.value}"/></td>
</tr>
</c:forEach>
</table>

</c:if>


<c:if test="${ufile.versionViewable}">
<p>
<a href="seeversion.jsp"><fmt:message key='ged.see.version'/></a>
</p>
</c:if>

<c:if test="${ufile.writable}">
	<p><sr:a href="seefile.jsp?action=recreateCache"><fmt:message key='reload'/></sr:a></p>
</c:if>


<%-- COMMENTS --%>
<c:if test="${displayApproveDisapprove}">
<form method="post">
<input type="hidden" name="action" value="addComment">
<table class="horizontal">
<caption><fmt:message key='comments'/></caption>
<tr><th><fmt:message key='subject'/></th><td><input type="text" name="commentSubject"></td></tr>
<tr><th><fmt:message key='text'/></th><td><textarea name="commentText"></textarea></td></tr>
<tr><th></th><td><input type="submit" value="<fmt:message key='save'/>"></td></tr>
</table>
</form>

<c:set var="comment" value="${ufile.comments}"/>
<c:if test="${not empty comment}">
<table class="horizontal">
<caption>Comments</caption>
<c:forEach var="c" items="${comment}">
<tr>
<th><c:out value="${c.login}"/></th>
<td><c:out value="${c.subject}"/><br/><c:out value="${c.text}"/></td>
</tr>
</c:forEach>
</table>
</c:if>
</c:if>


<fmt:message key='back' var="i18n_back"/>
<sr:a myclass="button" href="browser.jsp?action=browse&directoryId=${ufile.directoryId}"><c:out value="${i18n_back}"/></sr:a>


<%@ include file="../../part2.jspf" %>