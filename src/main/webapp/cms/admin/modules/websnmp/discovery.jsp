<jsp:useBean id="module" scope="session" class="dang.cms.AdminModule" />
<jsp:useBean id="adminDiscover" scope="session" class="dang.websnmp.AdminSnmpDiscover"/>

<fmt:message key='launch' var="i18n_launch"/>
<fmt:message key='stop' var="i18n_stop"/>

<c:choose>
    <c:when test="${param.action eq i18n_launch}">
        <sr:set bean="${adminDiscover}" property="beginIP|endIP"/>
	<sr:do bean="${adminDiscover}" property="discover" redirectOk="discovery.jsp"/>
    </c:when>

    <c:when test="${param.action eq i18n_stop}">
        <sr:do bean="${adminDiscover}" property="stop"/>
    </c:when>
</c:choose>


<%@ include file="../../part1.jspf" %>
<div id="main-copy">
<%@ include file="../../part2.jspf" %>


<a href="${PREFIX}/modules.jsp?page=modules">Modules</a> > <a href="${PREFIX}/modules.jsp?page=modules&moduleId=${module.id}"><c:out value="${module.name}"/></a> > <a href="${PREFIX}/modules/websnmp/index.jsp?page=modules"><fmt:message key='settings'/></a> > <a href="hostgen.jsp">Host</a> > Auto Discovery

<br/>
<br/>


<c:choose>

    <c:when test="${not adminDiscover.running}">

<form method="post">
<table class="horizontal">
    <tr>
        <th><fmt:message key='begin.ip'/></th>
	<td><sr:input type="text" name="beginIP" value="${adminDiscover.beginIP}"/></td>
    </tr>

    <tr>
        <th><fmt:message key='end.ip'/></th>
	<td><sr:input type="text" name="endIP" value="${adminDiscover.endIP}"/></td>
    </tr>
</table>

<input type="submit" name="action" value="${i18n_launch}"/>
</form>

    </c:when>


    <c:otherwise>

        <script language="JavaScript" type="text/JavaScript">
	setTimeout('window.location="discovery.jsp"',2000);
	</script>

        <table class="horizontal">
	<tr>
            <th><fmt:message key='begin.ip'/></th>
	    <td><c:out value="${adminDiscover.beginIP}"/></td>
	</tr>

	<tr>
            <th><fmt:message key='current.ip'/></th>
	    <td><span class="info"><c:out value="${adminDiscover.currentIP}"/></span></td>
	</tr>	
	
	<tr>
            <th><fmt:message key='end.ip'/></th>
	    <td><c:out value="${adminDiscover.endIP}"/></td>
	</tr>
</table>

        <form method="post">
	    <input type="submit" name="action" value="${i18n_stop}"/>
	</form>
    </c:otherwise>

</c:choose>


</div>
<%@ include file="../../part3.jspf" %>