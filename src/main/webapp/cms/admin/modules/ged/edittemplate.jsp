<jsp:useBean id="module" scope="session" class="dang.cms.AdminModule" />
<jsp:useBean id="atemplate" scope="session" class="dang.ged.AdminTemplate"/>


<%-- I18N --%>
<fmt:message key='name' var="i18n_name"/>
<fmt:message key='size' var="i18n_size"/>
<fmt:message key='default.value' var="i18n_defaultvalue"/>
<fmt:message key='add.item' var="i18n_additem"/>
<fmt:message key='ged.template' var="i18n_template"/>
<fmt:message key='item' var="i18n_item"/>

<%-- ACTION --%>
<c:choose>

<c:when test="${param.action eq 'saveTemplate'}">
<sr:set bean="${atemplate}" property="templateName"/>
<sr:do bean="${atemplate}" property="saveTemplate" redirectOk="template.jsp"/>
</c:when>

<c:when test="${param.action eq 'saveItem'}">
<sr:set bean="${atemplate}" property="templateName|itemIndex|fieldname|size|type|enumeration|defaultValue"/>
<sr:do bean="${atemplate}" property="saveItem"/>
</c:when>

<c:when test="${param.action eq 'upItem'}">
<sr:set bean="${atemplate}" property="itemIndex"/>
<sr:do bean="${atemplate}" property="upItem"/>
</c:when>

<c:when test="${param.action eq 'downItem'}">
<sr:set bean="${atemplate}" property="itemIndex"/>
<sr:do bean="${atemplate}" property="downItem"/>
</c:when>

<c:when test="${param.action eq 'deleteItem'}">
<sr:set bean="${atemplate}" property="itemIndex"/>
<sr:do bean="${atemplate}" property="deleteItem"/>
</c:when>

</c:choose>


<%@ include file="../../part1.jspf" %>
<div id="main-copy">
<%@ include file="../../part2.jspf" %>

<a href="${PREFIX}/modules.jsp?page=modules">Modules</a> > <a href="${PREFIX}/modules.jsp?page=modules&moduleId=${module.id}"><c:out value="${module.name}"/></a> > <a href="${PREFIX}/modules/ged/index.jsp?page=modules"><fmt:message key='settings'/></a> > <fmt:message key='ged.template'/>

<br><br>

<form method="post" name="templateForm">
<input type="hidden" name="action" value="saveTemplate">
<sr:widget title="${i18n_template}" bean="${atemplate}"
cssClass="horizontal" label="${i18n_name}" property="templateName"
propType="input" propSize="-1"/>
</form>

<c:forEach items="${atemplate.itemList}" var="il" varStatus="vs">
<form method="post" name="form${vs.count}">
<input type="hidden" name="itemIndex" value="${vs.count-1}">
<input type="hidden" name="action" value="saveItem">
<input type="hidden" name="templateName" value="${atemplate.templateName}">
<sr:widget title="${i18n_item} ${vs.count}" bean="${il}"
cssClass="horizontal" label="${i18n_name}|Type|${i18n_size}|Enumeration|${i18n_defaultvalue}"
property="fieldname|type|size|enumeration|defaultValue"
propType="input|select|select|input|input"
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
<input type="hidden" name="templateName" value="">
<sr:widget title="${i18n_additem}" bean="${atemplate}"
cssClass="horizontal" label="${i18n_name}|Type|${i18n_size}|Enumeration|${i18n_defaultvalue}"
property="fieldname|type|size|enumeration|defaultValue"
propType="input|select|select|input|input"
propSize="-1|-1|-1|-1|-1">
<tr>
<th/>
<td>
<input type="button" value="<fmt:message key='add'/>" onClick="document.addItemForm.templateName.value=document.templateForm.templateName.value;document.addItemForm.submit();"/>
</td></tr>
</sr:widget>
</form>

<input type="button" value="<fmt:message key='save'/>" onClick="document.templateForm.submit();"/>
<input type="button" value="<fmt:message key='cancel'/>" onClick="window.location='${PREFIX}/modules/ged/template.jsp'"/>

</div>
<%@ include file="../../part3.jspf" %>