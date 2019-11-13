<jsp:useBean id="module" scope="session" class="dang.cms.AdminModule" />
<jsp:useBean id="adminaction" scope="session" class="dang.websnmp.AdminAction" />

<c:choose>
<c:when test="${param.action eq 'Save'}">
    <sr:set bean="${adminaction}" property="name|hostId|type|oid"/>
    <sr:do bean="${adminaction}" property="save" redirectOk="actiongen.jsp"/>
</c:when>

<c:when test="${param.action eq 'Select'}">
    <c:set target="${adminaction}" property="mibName" value="${param.mibName}"/>
</c:when>
</c:choose>

<%@ include file="../../part1.jspf" %>
<div id="main-copy">
<%@ include file="../../part2.jspf" %>


<a href="${PREFIX}/modules.jsp?page=modules">Modules</a> > <a href="${PREFIX}/modules.jsp?page=modules&moduleId=${module.id}"><c:out value="${module.name}"/></a> > <a href="${PREFIX}/modules/websnmp/index.jsp?page=modules"><fmt:message key='settings'/></a> > Action

<br/>
<br/>


<form method="post" name="myform">
<input type="hidden" name="action" value="Select"/>
<table class="horizontal">
    <tr>
        <th><fmt:message key='name'/></th>
	<td><sr:input type="text" name="name" value="${adminaction.name}"/></td>
    </tr>

    <tr>
        <th><fmt:message key='host'/></th>
	<td><sr:select name="hostId" options="${adminaction.hostOptions}" defaultOption="${adminaction.hostId}"/></td>
    </tr>

    <tr>
        <th>Type</th>
	<td><sr:select name="type" options="${adminaction.typeOptions}" defaultOption="${adminaction.type}"/></td>
    </tr>

    <tr>
        <th>OID</th>
	<td><sr:select name="mibName" options="${adminaction.mibNameOptions}" defaultOption="${adminaction.mibName}" attributesText="onChange='document.myform.submit()'"/>
	<sr:select name="oid" options="${adminaction.oidStringOptions}" defaultOption="${adminaction.oid}"/></td>
    </tr>
</table>

<input type="button" value="<fmt:message key='save'/>" onClick="document.myform.action.value='Save';document.myform.submit()"/>
</form>

<input type="button" value="<fmt:message key='back'/>" onClick="window.location='actiongen.jsp'"/>

</div>
<%@ include file="../../part3.jspf" %>