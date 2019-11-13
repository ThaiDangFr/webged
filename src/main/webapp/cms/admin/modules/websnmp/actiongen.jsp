<jsp:useBean id="module" scope="session" class="dang.cms.AdminModule" />
<jsp:useBean id="adminaction" scope="session" class="dang.websnmp.AdminAction" />


<fmt:message key='delete' var="i18n_delete"/>

<c:choose>
    <c:when test="${param.action eq 'New'}">
        <sr:do bean="${adminaction}" property="new" redirectOk="actionedit.jsp" />
    </c:when>

    <c:when test="${param.action eq 'Edit'}">
        <c:set target="${adminaction}" property="id" value="${param.id}"/>
        <sr:do bean="${adminaction}" property="edit" redirectOk="actionedit.jsp" />
    </c:when>

    <c:when test="${param.action eq i18n_delete}">
        <c:set target="${adminaction}" property="actionIds" value="${paramValues.actionIds}"/>
	<sr:do bean="${adminaction}" property="delete" />
    </c:when>
</c:choose>

<%@ include file="../../part1.jspf" %>
<div id="main-copy">
<%@ include file="../../part2.jspf" %>


<a href="${PREFIX}/modules.jsp?page=modules">Modules</a> > <a href="${PREFIX}/modules.jsp?page=modules&moduleId=${module.id}"><c:out value="${module.name}"/></a> > <a href="${PREFIX}/modules/websnmp/index.jsp?page=modules"><fmt:message key='settings'/></a> > Action

<br/>
<br/>


<c:set var="sa" value="${adminaction.snmpAction}"/>


<form method="post" onSubmit="return confirmDelete()">
<table>
<caption>Action</caption>
<tr><th/><th><fmt:message key='name'/></th><th><fmt:message key='host.name'/></th></tr>
<c:choose>
    <c:when test="${not empty sa}">
	<c:forEach items="${sa}" var="i">
	    <tr>
	        <td><input type="checkbox" name="actionIds" value="${i.id}"/></td>
		<td><a href="actiongen.jsp?action=Edit&id=${i.id}"><c:out value="${i.name}"/></a></td>
		<td><c:out value="${i.hostName}"/></td>
	    </tr>
	</c:forEach>
    </c:when>
    <c:otherwise>
        <tr><td colspan="3"><span class="info"><fmt:message key='no.action'/></span></td></tr>
    </c:otherwise>
</c:choose>
</table>

<input type="button" name="action" value="<fmt:message key='add'/>" onClick="window.location='actiongen.jsp?action=New'"/>

<input type="submit" name="action" value="${i18n_delete}"/>
</form>



</div>
<%@ include file="../../part3.jspf" %>