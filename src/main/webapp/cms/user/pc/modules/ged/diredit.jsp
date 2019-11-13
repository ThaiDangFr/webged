<%@ include file="include.jspf" %>
<%@ include file="importmoduleid.jspf" %>

<%-- ACTION --%>
<c:choose>

<c:when test="${param.action eq 'next'}">
    <sr:set bean="${udir}" property="name|reference|storeType"/>

    <c:choose><c:when test="${not empty param.template}"><sr:set bean="${udir}" property="templateId"/></c:when><c:otherwise><c:set target="${udir}" property="templateId" value="${null}"/></c:otherwise></c:choose>

    <c:choose><c:when test="${not empty param.expiration}"><sr:set bean="${udir}" property="fileExpiryTime|fileExpiryGroup"/></c:when><c:otherwise><c:set target="${udir}" property="fileExpiryTime" value="${null}"/><c:set target="${udir}" property="fileExpiryGroup" value="${null}"/></c:otherwise></c:choose>

    <c:choose><c:when test="${not empty param.writeWorkflow}"><sr:set bean="${udir}" property="fileAddModifyWorkflow"/></c:when><c:otherwise><c:set target="${udir}" property="fileAddModifyWorkflow" value="${null}"/></c:otherwise></c:choose>

    <c:redirect url="diredit2.jsp"/>
</c:when>


<c:when test="${param.action eq 'finish'}">
    <sr:set bean="${udir}" property="name|reference|storeType"/>

    <c:choose><c:when test="${not empty param.template}"><sr:set bean="${udir}" property="templateId"/></c:when><c:otherwise><c:set target="${udir}" property="templateId" value="${null}"/></c:otherwise></c:choose>

    <c:choose><c:when test="${not empty param.expiration}"><sr:set bean="${udir}" property="fileExpiryTime|fileExpiryGroup"/></c:when><c:otherwise><c:set target="${udir}" property="fileExpiryTime" value="${null}"/><c:set target="${udir}" property="fileExpiryGroup" value="${null}"/></c:otherwise></c:choose>

    <c:choose><c:when test="${not empty param.writeWorkflow}"><sr:set bean="${udir}" property="fileAddModifyWorkflow"/></c:when><c:otherwise><c:set target="${udir}" property="fileAddModifyWorkflow" value="${null}"/></c:otherwise></c:choose>

    <sr:do bean="${udir}" property="saveDir" redirectOk="browser.jsp"/>
</c:when>

</c:choose>




<%@ include file="../../part1.jspf" %>


<ul id="step">
    <li class="active"><span>General</span></li>
    <li class="inactive"><span><fmt:message key='rights'/></span></li>
    <li class="inactive"><span>Notification</span></li>
</ul>



<c:set var="templateOption" value="${udir.templateOption}"/>
<c:set var="workflowOption" value="${udir.workflowOption}"/>
<c:set var="groupOption" value="${udir.groupOption}"/>

<form method="post" name="dirForm">
    <input type="hidden" name="action" value="next">
        <table class="horizontal">
	    <caption><fmt:message key='general.information'/></caption>

	    <tr class="textinput"><th><fmt:message key='name'/></th><td><sr:input type="text" name="name" value="${udir.name}"/></td></tr>

	    <c:if test="${not empty templateOption}">
	        <tr><th><sr:input type="checkbox" name="template" checked="${udir.template}"/> <fmt:message key='ged.template' /></th><td><sr:select name="templateId" multiple="false" options="${templateOption}" defaultOption="${udir.templateId}"/></td></tr>
	    </c:if>

	    <c:if test="${not empty groupOption}">
	        <tr><th><sr:input type="checkbox" name="expiration" checked="${udir.expiration}"/> <fmt:message key='ged.file.expiry'/></th><td><sr:select name="fileExpiryTime" multiple="false" options="${udir.daysOption}" defaultOption="${udir.fileExpiryTime}"/><fmt:message key='year(s)'/>. <fmt:message key='alert.group'/> <sr:select name="fileExpiryGroup" multiple="false" options="${groupOption}" defaultOption="${udir.fileExpiryGroup}"/></td></tr>
	    </c:if>

	    <tr class="textinput"><th>Reference</th><td><sr:input type="text" name="reference" value="${udir.reference}"/></td></tr>

		<tr><th><fmt:message key='office.store.type'/></th><td><sr:select name="storeType" options="${udir.storeTypeOption}" defaultOption="${udir.storeType}"/></td></tr>

	    <c:if test="${not empty workflowOption}">
	        <tr><th><sr:input type="checkbox" name="writeWorkflow" checked="${udir.writeWorkflow}"/> <fmt:message key='ged.write.follow.workflow'/></th><td><sr:select name="fileAddModifyWorkflow" multiple="false" options="${workflowOption}" defaultOption="${udir.fileAddModifyWorkflow}"/></td></tr>
	    </c:if>
    </table>
</form>




<p>
<input type="button" value="<fmt:message key='next'/>" onClick="document.dirForm.submit();">
</p>

<p>
<input type="button" value="<fmt:message key='save'/>" onClick="document.dirForm.action.value='finish';document.dirForm.submit();">
<input type="button" value="<fmt:message key='cancel'/>" onClick="window.location='${PREFIX}/ged/browser.jsp'">
</p>

<%@ include file="../../part2.jspf" %>