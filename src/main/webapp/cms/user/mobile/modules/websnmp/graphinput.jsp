<%@ include file="include.jspf" %>
<c:set var="PREFIX"  value="/openplatform/cms/user/mobile/modules" scope="page"/>

<sr:xhtml>
    <sr:head title="WebSNMP"/>
    <sr:body title="WebSNMP">

    <sr:form method="post" action="graph.jsp" submitstring="View">
        <sr:table columns="2">
	    <tr><td>Report</td><td><sr:select name="reportId" options="${usnmpgraph.reportOptions}"/></td></tr>
	    <tr><td>Begin date</td><td><sr:input type="text" name="beginTime"/>dd/mm/yyyy hh:mm:ss</td></tr>
	    <tr><td>End date</td><td><sr:input type="text" name="endTime"/>dd/mm/yyyy hh:mm:ss</td></tr>
	</sr:table>
    </sr:form>


	<sr:a href="index.jsp">Back</sr:a>
    </sr:body>
</sr:xhtml>