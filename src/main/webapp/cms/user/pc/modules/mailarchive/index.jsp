<%@ include file="include.jspf" %>

<c:choose>
<c:when test="${param.action eq 'search'}">
    <sr:set bean="${archivelsearch}" property="begin|end|fromMail|toMail|subject|text"/>
    <sr:do bean="${archivelsearch}" property="search"/>
</c:when>

<c:when test="${param.action eq 'reset'}">
    <sr:do bean="${archivelsearch}" property="reset" redirectOk="index.jsp"/>
</c:when>
</c:choose>

<%@ include file="../../part1.jspf" %>


<form method="post" name="form1" >
<input type="hidden" name="action" value="search"/>

<table class="horizontal">

<tr>
<th><fmt:message key='date.begin'/></th>
<td><sr:input type="text" name="begin" value="${archivelsearch.begin}"/><a href="javascript:calb.popup();"><img src="/openplatform/js/calendar/img/cal.gif" width="16" height="16" border="0" alt="Click Here to Pick up the date"></a></td>
</tr>

<tr>
<th><fmt:message key='date.end'/></th>
<td><sr:input type="text" name="end" value="${archivelsearch.end}"/><a href="javascript:cale.popup();"><img src="/openplatform/js/calendar/img/cal.gif" width="16" height="16" border="0" alt="Click Here to Pick up the date"></a></td>
</tr>

<tr>
<th><fmt:message key='from'/></th>
<td><sr:input type="text" name="fromMail" value="${archivelsearch.fromMail}"/></td>
</tr>

<tr>
<th><fmt:message key='to'/></th>
<td><sr:input type="text" name="toMail" value="${archivelsearch.toMail}"/></td>
</tr>

<tr>
<th><fmt:message key='subject'/></th>
<td><sr:input type="text" name="subject" value="${archivelsearch.subject}"/></td>
</tr>

<tr>
<th><fmt:message key='text'/></th>
<td><sr:input type="text" name="text" value="${archivelsearch.text}"/></td>
</tr>

</table>

<input type="submit" value="<fmt:message key='search'/>"/> <a href="index.jsp?action=reset"><fmt:message key='reset'/></a>

</form>

<script language="JavaScript">
var calb = new calendar1(document.forms['form1'].elements['begin']);
calb.year_scroll = true;
calb.time_comp = true;
document.forms['form1'].elements['begin'].readOnly = true;
</script>

<script language="JavaScript">
var cale = new calendar1(document.forms['form1'].elements['end']);
cale.year_scroll = true;
cale.time_comp = true;
document.forms['form1'].elements['end'].readOnly = true;
</script>



<c:if test="${not empty archivelsearch.headers}">
<table class="archivelresults">

<tr>
    <th><fmt:message key='date'/></th>
    <th><fmt:message key='from'/></th>
    <th><fmt:message key='to'/></th>
    <th><fmt:message key='subject'/></th>
    <th/>
</tr>

    <c:forEach var="ma" items="${archivelsearch.headers}">
        <tr>
	    <fmt:parseDate value="${ma.dateStr}" pattern="yyyy-MM-dd HH:mm:ss" var="fdate" parseLocale="en_US"/>
	    <fmt:formatDate type="both" value="${fdate}" dateStyle="short" timeStyle="short" var="fdate"/>
	    <td><c:out value="${fdate}"/></td>
	    <td><c:out value="${ma.fromMail}"/></td>
	    <td><sr:select name="to" options="${ma.toMailOptions}"/></td>
	    <td><c:out value="${ma.subject}"/></td>
	    <td><a href="view.jsp?id=${ma.id}"><fmt:message key='view'/></a></td>
	</tr>
    </c:forEach>

</table>
</c:if>



<%@ include file="../../part2.jspf" %>