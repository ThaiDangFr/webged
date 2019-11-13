<%@ include file="import.jspf" %>
<%@ include file="importmoduleid.jspf" %>


<c:if test="${param.action eq 'delete'}">
<jsp:setProperty name="inbox" property="doDeleteInbox" value="${paramValues.todel}"/>
</c:if>



<%@ include file="../../part1.jspf" %>
<h1 style="border-top: none; padding-top: 0;"><fmt:message key='inbox'/></h1>

<form method="post" onSubmit="return confirmDelete()" name="msgform">
    <input type="hidden" name="action" value="delete">

    <ul id="bar">
        <li><a href="#" onClick="if(confirmDelete()){document.msgform.submit();}"><img src="images/trash.gif" title="<fmt:message key='delete'/>"/></a></li>
	<li><a href="${PREFIX}/cmsbase/inboxcompose.jsp?init=true"><img src="images/compose.gif" title="<fmt:message key='compose'/>"/></a></li>
    </ul>


    <table id="inbox">
        <tr class="title">
	    <th><input type="checkbox" name="checkall" onClick="selectAll(msgform, document.msgform.checkall.checked)"></th>
	    <th><fmt:message key='from'/></th>
	    <th><fmt:message key='subject'/></th>
	    <th>Date</th>
        </tr>

	<c:set var="messages" value="${inbox.inbox}" scope="page"/>
	    <c:forEach var="ib" items="${messages}">
	    
	    <jsp:setProperty name="inbox" property="fromUserId" value="${ib.fromUserId}"/>
	        <tr class="message">
		    <td><input type="checkbox" name="todel" value="${ib.id}"></td>
		    <td><c:out value="${inbox.fromUserLogin}"/></td>
		    <td>
		        <c:if test="${ib.isRead eq '0'}"><img src="images/email.gif"></c:if>
			    <c:choose>
			        <c:when test="${ib.priority eq 'normal'}">
			            <a href="${PREFIX}/cmsbase/inboxview.jsp?inboxId=${ib.id}">
				        <c:out value="${ib.subject}"/>
				    </a>
			        </c:when>
				<c:otherwise>
				    <a class="highpriority" href="${PREFIX}/cmsbase/inboxview.jsp?inboxId=${ib.id}">
				        <c:out value="${ib.subject}"/>
				    </a>
			        </c:otherwise>
			    </c:choose>
		    </td>

		    <fmt:parseDate value="${ib.date}" pattern="yyyy-MM-dd HH:mm:ss" var="fdate" parseLocale="en_US"/>
		    <fmt:formatDate type="both" value="${fdate}" dateStyle="full" timeStyle="short" var="fdate"/>
		    <td><c:out value="${fdate}"/></td>
	        </tr>
	</c:forEach>

	<c:if test="${empty messages}"><tr><td colspan="100%"><fmt:message key='no.message'/></td></tr></c:if>

    </table>
</form>

<%@ include file="../../part2.jspf" %>