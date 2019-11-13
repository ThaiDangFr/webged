<%@ include file="include.jspf" %>

<c:if test="${empty param.reportId or empty param.beginTime or empty param.endTime}">
    <c:redirect url="graphinput.jsp"/>
</c:if>

<sr:set bean="${usnmpgraph}" property="reportId|beginTime|endTime"/>

<%@ include file="../../part1.jspf" %>

<div id="graph">
<table>
<c:forEach items="${usnmpgraph.snmpGraphicFormula}" var="gf">
    <c:set target="${usnmpgraph}" property="GFormulaId" value="${gf.GFormulaId}"/>
    <sr:do bean="${usnmpgraph}" property="generateGraph"/>

    <tr>
       <td>
           <sr:img fileBaseId="${usnmpgraph.fileBaseId}" extension="jpg" param="80x60" />
       </td>
       <td>
           <sr:a fileBaseId="${usnmpgraph.excelFileBaseId}">Data.txt</sr:a>
       </td>
    </tr>
</c:forEach>
</table>
</div>


<%@ include file="../../part2.jspf" %>