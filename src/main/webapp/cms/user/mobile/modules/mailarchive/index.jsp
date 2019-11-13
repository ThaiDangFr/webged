<%@ include file="../../../pc/modules/mailarchive/include.jspf" %>


<c:choose>
<c:when test="${param.action eq 'search'}">
    <sr:set bean="${archivelsearch}" property="begin|end|fromMail|toMail|subject|text"/>
    <sr:do bean="${archivelsearch}" property="search"/>
</c:when>

<c:when test="${param.action eq 'reset'}">
    <sr:do bean="${archivelsearch}" property="reset" redirectOk="index.jsp"/>
</c:when>
</c:choose>



<sr:xhtml>
    <sr:head title="Archivel"/>
    <sr:body title="Archivel">
    
    <sr:form method="post" submitstring="Ok" action="index.jsp">
    <sr:input type="hidden" name="action" value="search"/>
        <sr:table columns="2">
	    <tr>
	        <td><fmt:message key='from'/></td>
		<td><sr:input type="text" name="fromMail" value="${archivelsearch.fromMail}"/></td>
	    </tr>

	    <tr>
	        <td><fmt:message key='to'/></td>
		<td><sr:input type="text" name="toMail" value="${archivelsearch.toMail}"/></td>
	    </tr>

	    <tr>
	        <td><fmt:message key='subject'/></td>
		<td><sr:input type="text" name="subject" value="${archivelsearch.subject}"/></td>
	    </tr>

	    <tr>
	        <td><fmt:message key='text'/></td>
		<td><sr:input type="text" name="text" value="${archivelsearch.text}"/></td>
	    </tr>
	</sr:table>
	</sr:form>


	<c:if test="${not empty archivelsearch.headers}">
	     <sr:table columns="5">
	         <tr>
		 <td><fmt:message key='date'/></td>
		 <td><fmt:message key='from'/></td>
		 <td><fmt:message key='to'/></td>
		 <td><fmt:message key='subject'/></td>
		 <td/>
		 </tr> 
 
                 <c:forEach var="ma" items="${archivelsearch.headers}">
		 <tr>
		 <fmt:parseDate value="${ma.date}" pattern="yyyy-MM-dd HH:mm:ss" var="fdate" parseLocale="en_US"/>
		 <fmt:formatDate type="both" value="${fdate}" dateStyle="short" timeStyle="short" var="fdate"/>
		 <td><c:out value="${fdate}"/></td>
		 <td><c:out value="${ma.fromMail}"/></td>
		 <td><sr:select name="to" options="${mail.toMailOptions}"/></td>
		 <td><c:out value="${ma.subject}"/></td>
		 <td><a href="view.jsp?id=${ma.id}"><fmt:message key='view'/></a></td>
		 </tr>
		 </c:forEach>

	     </sr:table>
	</c:if>


	<sr:a href="../../index.jsp">Back</sr:a>
    </sr:body>
</sr:xhtml>