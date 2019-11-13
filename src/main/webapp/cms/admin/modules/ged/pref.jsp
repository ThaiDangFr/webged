<jsp:useBean id="module" scope="session" class="dang.cms.AdminModule" />
<jsp:useBean id="gedpref" scope="session" class="dang.ged.AdminPref"/>

<c:choose>
    <c:when test="${param.action eq 'save'}">
        <sr:set bean="${gedpref}" property="trashExpiry"/>
		<sr:do bean="${gedpref}" property="save"/>
    </c:when>

    <c:when test="${param.action eq 'reindex'}">
        <sr:do bean="${gedpref}" property="reIndex"/>
    </c:when>
</c:choose>


<%@ include file="../../part1.jspf" %>
<div id="main-copy">
<%@ include file="../../part2.jspf" %>


<a href="${PREFIX}/modules.jsp?page=modules">Modules</a> > <a href="${PREFIX}/modules.jsp?page=modules&moduleId=${module.id}"><c:out value="${module.name}"/></a> > <fmt:message key='settings'/>

<ul>
<li><a href="${PREFIX}/modules/ged/template.jsp"><fmt:message key='ged.template'/></a></li>
<li><a href="${PREFIX}/modules/ged/workflow.jsp">Workflow</a></li>
<li><fmt:message key='preferences'/></li>
<li><a href="${PREFIX}/modules/ged/gedstate.jsp"><fmt:message key='ged.state'/></a></li>
</ul>

<br/><br/>


<form method="post">
    <input type="hidden" name="action" value="reindex"/>
    <table class="horizontal">
        <caption>Indexation</caption>
	<tr>
	    <th><fmt:message key='files'/></th>
	    <td><fmt:message key='reindex.database'/><input type="submit" value="<fmt:message key='go'/>"/></td>
	</tr>
    </table>
</form>


<form method="post">
<input type="hidden" name="action" value="save"/>
    <table class="horizontal">
        <tr>
	    <th><fmt:message key='trash.expiry.time'/></th>
	    <td><sr:select name="trashExpiry" options="${gedpref.trashExpiryOption}" defaultOption="${gedpref.trashExpiry}" /></td>
		</tr>
    </table>
    
    <input type="submit" value="<fmt:message key='save'/>"/>
</form>




</div>
<%@ include file="../../part3.jspf" %>