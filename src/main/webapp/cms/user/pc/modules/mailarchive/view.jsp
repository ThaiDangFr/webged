<%@ include file="include.jspf" %>

<c:set target="${archivelview}" property="id" value="${param.id}"/>

<%@ include file="../../part1.jspf" %>


<c:set var="mail" value="${archivelview.mail}"/>
<c:if test="${not empty mail}">
    <table class="horizontal">
        <tr>
	    <th><fmt:message key='date'/></th>
	    <fmt:parseDate value="${mail.dateStr}" pattern="yyyy-MM-dd HH:mm:ss" var="fdate" parseLocale="en_US"/>
	    <fmt:formatDate type="both" value="${fdate}" dateStyle="full" timeStyle="short" var="fdate"/>
	    <td><c:out value="${fdate}"/></td>
	</tr>

        <tr>
	    <th><fmt:message key='from'/></th>
	    <td><c:out value="${mail.fromMail}"/></td>
	</tr>

        <tr>
	    <th><fmt:message key='to'/></th>
	    <td><sr:select name="to" options="${mail.toMailOptions}"/></td>
	</tr>

        <tr>
	    <th><fmt:message key='subject'/></th>
	    <td><c:out value="${mail.subject}"/></td>
	</tr>

	<tr>
	    <th><fmt:message key='attachments'/></th>
	    <td>
	        <c:forEach var="att" items="${mail.attachments}">
		    <sr:a fileBaseId="${att.fileBaseId}">
		    	<c:out value="${att.filename}"/>
		    </sr:a>
		    <br/>
		</c:forEach>
	    </td>
	</tr>
    </table>

    <table>
        <caption><fmt:message key='message'/></caption>
	<tr><td><pre wrap><c:out value="${mail.message}"/></pre></td></tr>
    </table>

    <fmt:message key='download.rfc822' var="i18n_download_rfc822"/>
    <sr:a fileBaseId="${archivelview.rfc822Id}">
    	<c:out value="${i18n_download_rfc822}"/>
    </sr:a>
    <br/><br/>

</c:if>


<a href="index.jsp"><fmt:message key='back'/></a>

<%@ include file="../../part2.jspf" %>