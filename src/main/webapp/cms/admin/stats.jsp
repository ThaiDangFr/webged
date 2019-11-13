<jsp:useBean id="maintenance" scope="session" class="dang.applimgt.AdminMaintenance" />


<%@ include file="part1.jspf" %>
<%@ include file="part2.jspf" %>
<div id="main-copy">


<table class="horizontal">
<tr><th><fmt:message key='jvm.stats'/></th>          <td><c:out value="${maintenance.JVMStats}"/></td></tr>
</table>




</div>
<%@ include file="part3.jspf" %>