<%@ include file="include.jspf" %>
<c:set var="PREFIX"  value="/openplatform/cms/user/mobile/modules" scope="page"/>

<sr:set bean="${usnmpgraph}" property="reportId|beginTime|endTime"/>

<sr:xhtml>
    <sr:head title="WebSNMP"/>
    <sr:body title="WebSNMP">

    <sr:table columns="2">

    <c:forEach items="${usnmpgraph.snmpGraphicFormula}" var="gf">
        <c:set target="${usnmpgraph}" property="GFormulaId" value="${gf.GFormulaId}"/>
	<sr:do bean="${usnmpgraph}" property="generateGraph"/>

	<tr>
	    <td>
	        <sr:img fileBaseId="${usnmpgraph.fileBaseId}" extension="jpg" param="80x60"/>
	    </td>
	    <td>
	        <sr:a fileBaseId="${usnmpgraph.excelFileBaseId}">
	        	Data.txt
	        </sr:a>
	    </td>
	</tr>
    </c:forEach>

    </sr:table>


	<sr:a href="graphinput.jsp">Back</sr:a>
    </sr:body>
</sr:xhtml>