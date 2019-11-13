<jsp:useBean id="module" scope="session" class="dang.cms.AdminModule" />
<jsp:useBean id="aworkflow" scope="session" class="dang.ged.AdminWorkflow"/>


<%-- I18N --%>
<fmt:message key='name' var="i18n_name"/>
<fmt:message key='send.to' var="i18n_sendto"/>
<fmt:message key='timelimit' var="i18n_timelimit"/>
<fmt:message key='day' var="i18n_day"/>
<fmt:message key='backup.to' var="i18n_backupto"/>
<fmt:message key='step' var="i18n_step"/>
<fmt:message key='add.step' var="i18n_addstep"/>


<%-- ACTION --%>
<c:choose>

<c:when test="${param.action eq 'saveWorkflow'}">
<sr:set bean="${aworkflow}" property="workflowName"/>
<sr:do bean="${aworkflow}" property="saveWorkflow" redirectOk="workflow.jsp"/>
</c:when>

<c:when test="${param.action eq 'saveItem'}">
<sr:set bean="${aworkflow}" property="workflowName|itemIndex|name|sendTo1|sendTo2|timelimit1|timelimit2"/>
<sr:do bean="${aworkflow}" property="saveItem"/>
</c:when>

<c:when test="${param.action eq 'upItem'}">
<sr:set bean="${aworkflow}" property="itemIndex"/>
<sr:do bean="${aworkflow}" property="upItem"/>
</c:when>

<c:when test="${param.action eq 'downItem'}">
<sr:set bean="${aworkflow}" property="itemIndex"/>
<sr:do bean="${aworkflow}" property="downItem"/>
</c:when>

<c:when test="${param.action eq 'deleteItem'}">
<sr:set bean="${aworkflow}" property="itemIndex"/>
<sr:do bean="${aworkflow}" property="deleteItem"/>
</c:when>

</c:choose>


<%@ include file="../../part1.jspf" %>
<div id="main-copy">
<%@ include file="../../part2.jspf" %>

<a href="${PREFIX}/modules.jsp?page=modules">Modules</a> > <a href="${PREFIX}/modules.jsp?page=modules&moduleId=${module.id}"><c:out value="${module.name}"/></a> > <a href="${PREFIX}/modules/ged/index.jsp?page=modules"><fmt:message key='settings'/></a> > Workflow

<br><br>

<form method="post" name="workflowForm">
<input type="hidden" name="action" value="saveWorkflow">
<sr:widget title="Workflow" bean="${aworkflow}"
cssClass="horizontal" label="${i18n_name}" property="workflowName"
propType="input" propSize="-1"/>
</form>

<c:forEach items="${aworkflow.itemList}" var="il" varStatus="vs">
<form method="post" name="form${vs.count}">
<input type="hidden" name="itemIndex" value="${vs.count-1}">
<input type="hidden" name="action" value="saveItem">
<input type="hidden" name="workflowName" value="${aworkflow.workflowName}">
<sr:widget title="${i18n_step} ${vs.count}" bean="${il}"
cssClass="horizontal" label="${i18n_name}|${i18n_sendto}|${i18n_timelimit} (${i18n_day})|${i18n_backupto}|${i18n_timelimit} (${i18n_day})"
property="name|sendTo1|timelimit1|sendTo2|timelimit2"
propType="input|select|select|select|select"
propSize="-1|-1|-1|-1|-1">

<tr>
<th/>
<td>
<c:choose>
    <c:when test="${vs.first and vs.last}">
	<input type="button" value="<fmt:message key='delete'/>" onClick="document.form${vs.count}.action.value='deleteItem';document.form${vs.count}.submit();"/>
	<input type="button" value="<fmt:message key='save'/>" onClick="document.form${vs.count}.action.value='saveItem';document.form${vs.count}.submit();"/>    
    </c:when>

    <c:when test="${vs.first}">
	<input type="button" value="<fmt:message key='down'/>" onClick="document.form${vs.count}.action.value='downItem';document.form${vs.count}.submit();"/>
	<input type="button" value="<fmt:message key='delete'/>" onClick="document.form${vs.count}.action.value='deleteItem';document.form${vs.count}.submit();"/>
	<input type="button" value="<fmt:message key='save'/>" onClick="document.form${vs.count}.action.value='saveItem';document.form${vs.count}.submit();"/>
    </c:when>

    <c:when test="${vs.last}">
        <input type="button" value="<fmt:message key='up'/>" onClick="document.form${vs.count}.action.value='upItem';document.form${vs.count}.submit();"/>
	<input type="button" value="<fmt:message key='delete'/>" onClick="document.form${vs.count}.action.value='deleteItem';document.form${vs.count}.submit();"/>
	<input type="button" value="<fmt:message key='save'/>" onClick="document.form${vs.count}.action.value='saveItem';document.form${vs.count}.submit();"/>
    </c:when>

    <c:otherwise>
        <input type="button" value="<fmt:message key='up'/>" onClick="document.form${vs.count}.action.value='upItem';document.form${vs.count}.submit();"/>
	<input type="button" value="<fmt:message key='down'/>" onClick="document.form${vs.count}.action.value='downItem';document.form${vs.count}.submit();"/>
	<input type="button" value="<fmt:message key='delete'/>" onClick="document.form${vs.count}.action.value='deleteItem';document.form${vs.count}.submit();"/>
	<input type="button" value="<fmt:message key='save'/>" onClick="document.form${vs.count}.action.value='saveItem';document.form${vs.count}.submit();"/>
    </c:otherwise>

</c:choose>
</td>
</tr>
</sr:widget>
</form>
</c:forEach>

<form method="post" name="addItemForm">
<input type="hidden" name="itemIndex" value="-1">
<input type="hidden" name="action" value="saveItem">
<input type="hidden" name="workflowName" value="">
<sr:widget title="${i18n_addstep}" bean="${aworkflow}"
cssClass="horizontal" label="${i18n_name}|${i18n_sendto}|${i18n_timelimit} (${i18n_day})|${i18n_backupto}|${i18n_timelimit} (${i18n_day})"
property="name|sendTo1|timelimit1|sendTo2|timelimit2"
propType="input|select|select|select|select"
propSize="-1|-1|-1|-1|-1">
<tr><th/><td><input type="button" value="<fmt:message key='add'/>" onClick="document.addItemForm.workflowName.value=document.workflowForm.workflowName.value;document.addItemForm.submit();"></td></tr>
</sr:widget>
</form>

<input type="button" value="<fmt:message key='save'/>" onClick="document.workflowForm.submit();"/>
<input type="button" value="<fmt:message key='cancel'/>" onClick="window.location='${PREFIX}/modules/ged/workflow.jsp'"/>

</div>
<%@ include file="../../part3.jspf" %>