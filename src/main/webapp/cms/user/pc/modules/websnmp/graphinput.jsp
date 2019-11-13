<%@ include file="include.jspf" %>


<%@ include file="../../part1.jspf" %>


<form method="post" name="form1" action="graph.jsp">

<table class="horizontal">

    <tr>
        <th><fmt:message key='report'/></th>
	<td><sr:select name="reportId" options="${usnmpgraph.reportOptions}"/></td>
    </tr>
   

    <tr>
        <th><fmt:message key='date.begin'/></th>
	<td><input type="text" name="beginTime"/> <a href="javascript:calb.popup();"><img src="/openplatform/js/calendar/img/cal.gif" width="16" height="16" border="0" alt="Click Here to Pick up the date"></a></td>
    </tr>

    <tr>
        <th><fmt:message key='date.end'/></th>
	<td><input type="text" name="endTime"/> <a href="javascript:cale.popup();"><img src="/openplatform/js/calendar/img/cal.gif" width="16" height="16" border="0" alt="Click Here to Pick up the date"></a></td>
    </tr>

    <tr>
        <th></th>
        <td><input type="submit" name="action" value="<fmt:message key='view'/>"/></td>
    </tr>

</table>

</form>


<script language="JavaScript">
var calb = new calendar1(document.forms['form1'].elements['beginTime']);
calb.year_scroll = true;
calb.time_comp = true;
document.forms['form1'].elements['beginTime'].readOnly = true;
</script>

<script language="JavaScript">
var cale = new calendar1(document.forms['form1'].elements['endTime']);
cale.year_scroll = true;
cale.time_comp = true;
document.forms['form1'].elements['endTime'].readOnly = true;
</script>

<%@ include file="../../part2.jspf" %>