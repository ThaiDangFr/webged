<%@ include file="import.jspf" %>
<%@ include file="importmoduleid.jspf" %>


<c:choose>
<c:when test="${param.action eq 'reply'}">
<c:if test="${inboxcompose.doInit}"/>
<jsp:setProperty name="inboxcompose" property="subject" value="Re : ${inbox.subject}"/>
<jsp:setProperty name="inboxcompose" property="priority" value="${inbox.priority}"/>
<jsp:setProperty name="inboxcompose" property="text" value=""/>
<jsp:setProperty name="inboxcompose" property="toUserId" value="${inbox.fromUserId}"/>
<c:set target="${inboxcompose}" property="inboxAttachment" value="${null}"/>
<c:redirect url="inboxcompose.jsp"/>
</c:when>

<c:when test="${param.action eq 'forward'}">
<c:if test="${inboxcompose.doInit}"/>
<jsp:setProperty name="inboxcompose" property="subject" value="Fw : ${inbox.subject}"/>
<jsp:setProperty name="inboxcompose" property="priority" value="${inbox.priority}"/>
<jsp:setProperty name="inboxcompose" property="text" value="${inbox.text}"/>
<c:set target="${inboxcompose}" property="toUserId" value="${null}"/>
<jsp:setProperty name="inboxcompose" property="inboxAttachment" value="${inbox.receivedAttachments}"/>
<c:redirect url="inboxcompose.jsp"/>
</c:when>
</c:choose>


<%@ include file="../../part1.jspf" %>
<h1 style="border-top: none; padding-top: 0;">Message</h1>
<jsp:setProperty name="inbox" property="inboxId" value="${param.inboxId}"/>
<jsp:setProperty name="inbox" property="fromUserId" value="${inbox.fromUserId}"/>

<table class="horizontal">
    <tr><th>Date</th><td><c:out value="${inbox.date}"/></td></tr>
    <tr><th><fmt:message key='from'/></th><td><c:out value="${inbox.fromUserLogin}"/></td></tr>
    <tr><th><fmt:message key='subject'/></th><td><c:out value="${inbox.subject}"/></td></tr>
    <tr><th><fmt:message key='priority'/></th><td><fmt:message key="${inbox.priority}"/></td></tr>

    <c:set value="${inbox.receivedAttachments}" var="ra"/>
    <c:if test="${not empty ra}">
    <tr>
        <th><fmt:message key='attachments'/></th>
	    <td>
	    <c:forEach var="a" items="${ra}">
	        <jsp:setProperty name="filebasebean" property="fileBaseId" value="${a.fileBaseId}"/>
		<sr:a fileBaseId="${a.fileBaseId}">
			<c:out value="${filebasebean.fileName}"/>
		</sr:a>
		&nbsp;
	    </c:forEach>
	    </td>
    </tr>
    </c:if>
</table>

<div id="message">
    <pre wrap><sr:out value="${inbox.text}" specialtag="true"/></pre>
</div>


<p>
    <input type="button" value="<fmt:message key='back'/>" onClick="self.location.href='${PREFIX}/cmsbase/inbox.jsp'"/>
    <input type="button" value="<fmt:message key='reply'/>" onClick="self.location.href='${PREFIX}/cmsbase/inboxview.jsp?action=reply'"/>
    <input type="button" value="<fmt:message key='forward'/>" onClick="self.location.href='${PREFIX}/cmsbase/inboxview.jsp?action=forward'"/>
</p>

<p>
    <input type="button" value="<fmt:message key='delete'/>" onClick="if(confirmDelete()) { self.location.href='${PREFIX}/cmsbase/inbox.jsp?action=delete&todel=${inbox.inboxId}'; }"/>
</p>

<%@ include file="../../part2.jspf" %>