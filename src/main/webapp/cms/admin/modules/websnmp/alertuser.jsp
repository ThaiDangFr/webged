<jsp:useBean id="module" scope="session" class="dang.cms.AdminModule" />
<jsp:useBean id="adminalertuser" scope="session" class="dang.websnmp.AdminAlertUser" />

<fmt:message key='add' var="i18n_add"/>


<c:choose>

    <c:when test="${param.action eq 'remove'}">
        <sr:set bean="${adminalertuser}" property="groupId"/>
	<sr:do bean="${adminalertuser}" property="remove"/>
	<c:redirect url="alertuser.jsp"/>
    </c:when>

    <c:when test="${param.action eq 'add'}">
        <sr:set bean="${adminalertuser}" property="groupId"/>
	<sr:do bean="${adminalertuser}" property="add"/>
	<c:redirect url="alertuser.jsp"/>
    </c:when>

</c:choose>


<%@ include file="../../part1.jspf" %>
<div id="main-copy">
<%@ include file="../../part2.jspf" %>


<a href="${PREFIX}/modules.jsp?page=modules">Modules</a> > <a href="${PREFIX}/modules.jsp?page=modules&moduleId=${module.id}"><c:out value="${module.name}"/></a> > <a href="${PREFIX}/modules/websnmp/index.jsp?page=modules"><fmt:message key='settings'/></a> > <a href="${PREFIX}/modules/websnmp/alertgen.jsp"><fmt:message key='alert'/></a> > <fmt:message key='configure.alert.mailing'/>

<br/>
<br/>

<table class="horizontal">

    <tr>
        <th><fmt:message key="groups"/></th>

	<c:set var="groups" value="${adminalertuser.groups}"/>
	
	<c:choose>

	    <c:when test="${empty groups}">
	        <td><span class="info"><fmt:message key='no.groups'/></span></td>
	    </c:when>

	    <c:otherwise>
	
	        <td>
		    <c:forEach items="${groups}" var="g">
		        <c:out value="${g.name}"/><a href="alertuser.jsp?action=remove&groupId=${g.groupId}">[x]</a><br/>
		    </c:forEach>
		</td>

	    </c:otherwise>
	</c:choose>

    </tr>

</table>


<form method="post">
    <input type="hidden" name="action" value="add"/>
    <sr:select name="groupId" options="${adminalertuser.groupsOption}"/>
    <input type="submit" value="<fmt:message key='add'/>"/>
</form>



</div>
<%@ include file="../../part3.jspf" %>