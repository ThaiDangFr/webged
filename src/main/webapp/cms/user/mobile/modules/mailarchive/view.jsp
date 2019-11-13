<%@ include file="../../../pc/modules/mailarchive/include.jspf" %>

<c:set target="${archivelview}" property="id" value="${param.id}"/>


<sr:xhtml>
    <sr:head title="Archivel"/>
    <sr:body title="Archivel">

<c:set var="mail" value="${archivelview.mail}"/>
<c:if test="${not empty mail}">
    <sr:table columns="2">
        <tr>
	    <td><fmt:message key='date'/></td>
	    <fmt:parseDate value="${mail.dateStr}" pattern="yyyy-MM-dd HH:mm:ss" var="fdate" parseLocale="en_US"/>
	    <fmt:formatDate type="both" value="${fdate}" dateStyle="full" timeStyle="short" var="fdate"/>
	    <td><c:out value="${fdate}"/></td>
	</tr>

        <tr>
	    <td><fmt:message key='from'/></td>
	    <td><c:out value="${mail.fromMail}"/></td>
	</tr>

        <tr>
	    <td><fmt:message key='to'/></td>
	    <td><sr:select name="to" options="${mail.toMailOptions}"/></td>
	</tr>

        <tr>
	    <td><fmt:message key='subject'/></td>
	    <td><c:out value="${mail.subject}"/></td>
	</tr>

	<tr>
	    <td><fmt:message key='attachments'/></td>
	    <td>
	        <c:forEach var="att" items="${mail.attachments}">
		    <sr:a fileBaseId="${att.fileBaseId}">
		    	<c:out value="${att.filename}"/>
		    </sr:a><sr:br/>
		</c:forEach>
	    </td>
	</tr>

	<tr>
	    <td><fmt:message key='message'/></td>
	    <td><c:out value="${mail.message}"/></td>
	</tr>
    </sr:table>


</c:if>


<a href="index.jsp"><fmt:message key='back'/></a>

    </sr:body>
</sr:xhtml>