<%@ include file="import.jspf" %>
<%@ include file="importmoduleid.jspf" %>


<c:if test="${not empty param.toUserId}">
<jsp:setProperty name="inboxcompose" property="toUserId" value="${param.toUserId}"/>
</c:if>

<c:if test="${param.init eq 'true'}"><c:if test="${inboxcompose.doInit}"/></c:if>

<c:choose>
<c:when test="${param.action eq 'attach'}">
<jsp:setProperty name="inboxcompose" property="request" value="${pageContext.request}"/>
<c:if test="${inboxcompose.doUploadAttachment}"/>
</c:when>

<c:when test="${param.action eq 'remattach'}">
<jsp:setProperty name="inboxcompose" property="doDeleteInboxAttachment" value="${param.index}"/>
</c:when>

<c:when test="${param.action eq 'send'}">
<jsp:setProperty name="inboxcompose" property="fromUserId" value="${usersession.userId}"/>
<jsp:setProperty name="inboxcompose" property="subject" value="${param.subject}"/>
<jsp:setProperty name="inboxcompose" property="priority" value="${param.priority}"/>
<jsp:setProperty name="inboxcompose" property="text" value="${param.text}"/>

<sr:do bean="${inboxcompose}" property="send" redirectOk="inbox.jsp"/>
</c:when>
</c:choose>



<%@ include file="../../part1.jspf" %>
<h1 style="border-top: none; padding-top: 0;"><fmt:message key='compose'/></h1>

<table class="horizontal">
<caption><fmt:message key='join.files'/></caption>
<tr><th><fmt:message key='attachments'/></th><td>
<c:forEach var="name" items="${inboxcompose.inboxAttachmentName}" varStatus="vs">
<c:out value="${name}"/><a class="noline" href="${PREFIX}/cmsbase/inboxcompose.jsp?action=remattach&index=${vs.count}"> [X]</a>&nbsp;
</c:forEach>
</td></tr>

<tr><th><fmt:message key='file.attach'/></th><td><form action="${PREFIX}/cmsbase/inboxcompose.jsp?action=attach" enctype="multipart/form-data" method="post">
<input type="hidden" name="action" value="attach"><input type="file" name="myfile"/><input type="submit" value="<fmt:message key='file.attach'/>">
</form></td></tr>
</table>

<form method="post" action="${PREFIX}/cmsbase/inboxcompose.jsp">
<input type="hidden" name="action" value="send">
<table class="horizontal">
<caption><fmt:message key='compose'/></caption>
<tr>
<th><fmt:message key='to'/></th>
<c:choose>
<c:when test="${empty inboxcompose.toUserId}"><td><a href="${PREFIX}/cmsbase/inboxcontact.jsp"><fmt:message key='click.here'/></a></td></c:when>
<c:otherwise>
<td><c:out value="${inboxcompose.toUserLogin}"/></td>
</c:otherwise>
</c:choose>
</tr>

<tr class="textinput"><th><fmt:message key='subject'/></th><td><sr:input type="text" name="subject" value="${inboxcompose.subject}"/></td></tr>

<tr><th><fmt:message key='priority'/></th><td><sr:select name="priority" multiple="false" options="${inboxcompose.priorityOptions}" defaultOption="${inboxcompose.priority}"/></td></tr>

<tr><th>Message</th><td><sr:input type="textarea" name="text" value="${inboxcompose.text}"/> </td></tr>

</table>


<fmt:message key='send' var="i18n_send"/>
<fmt:message key='back' var="i18n_back"/>


<input type="submit" value="${i18n_send}">
<input type="button" value="${i18n_back}" onClick="self.location.href='${PREFIX}/cmsbase/inbox.jsp'">


</form>


<%@ include file="../../part2.jspf" %>