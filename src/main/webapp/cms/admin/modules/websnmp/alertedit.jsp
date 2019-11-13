<jsp:useBean id="module" scope="session" class="dang.cms.AdminModule" />
<jsp:useBean id="adminalert" scope="session" class="dang.websnmp.AdminAlert" />


<fmt:message key='save' var="i18n_save"/>
<fmt:message key='delete' var="i18n_delete"/>

<c:choose>
    <c:when test="${param.action eq i18n_save}">
        <sr:set bean="${adminalert}" property="gFormulaId|condition|value"/>
	<sr:do bean="${adminalert}" property="save"/>
    </c:when>
    
    <c:when test="${param.action eq i18n_delete}">
        <sr:set bean="${adminalert}" property="gFormulaId"/>
	<sr:do bean="${adminalert}" property="delete"/>
    </c:when>
</c:choose>



<%@ include file="../../part1.jspf" %>
<div id="main-copy">
<%@ include file="../../part2.jspf" %>


<a href="${PREFIX}/modules.jsp?page=modules">Modules</a> > <a href="${PREFIX}/modules.jsp?page=modules&moduleId=${module.id}"><c:out value="${module.name}"/></a> > <a href="${PREFIX}/modules/websnmp/index.jsp?page=modules"><fmt:message key='settings'/></a> > <a href="${PREFIX}/modules/websnmp/alertgen.jsp">Alert</a> > List

<br/>
<br/>


<c:set target="${adminalert}" property="reportId" value="${param.reportId}"/>
<c:set var="alerts" value="${adminalert.alert}"/>

<c:choose>
    <c:when test="${not empty alerts}">
        <table>
	    <tr><th><fmt:message key='status'/></th><th><fmt:message key='formula'/></th><th/><th><fmt:message key='value'/></th><th/></tr>
	    <c:forEach items="${alerts}" var="gf">
	        <tr>
		    <form method="post">
		        <input type="hidden" name="gFormulaId" value="${gf.GFormulaId}"/>
			<td><c:choose><c:when test="${gf.status eq 'ON'}"><span class="info"><fmt:message key='active'/></span></c:when><c:otherwise><span class="error"><fmt:message key='inactive'/></span></c:otherwise></c:choose></td>
			<td><c:out value="${gf.formulaName}"/></td>
			<td><sr:select name="condition" options="${gf.conditionOptions}" defaultOption="${gf.condition}"/></td>
			<td><sr:input type="text" name="value" value="${gf.value}"/></td>
			<td><input type="submit" name="action" value="${i18n_save}"/> <input type="submit" name="action" value="${i18n_delete}"/></td>
		    </form>
		</tr>
	    </c:forEach>
	</table>
    </c:when>

    <c:otherwise>
        <p><span class="info"><fmt:message key='no.alerts'/></span></p>
    </c:otherwise>
</c:choose>



</div>
<%@ include file="../../part3.jspf" %>