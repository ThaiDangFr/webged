<%@ include file="include.jspf" %>
<%@ include file="importmoduleid.jspf" %>


<%-- INIT --%>
<c:set target="${ufile}" property="userId" value="${usersession.userId}"/>

<%-- PROTECTION --%>
<c:if test="${not ufile.modify}">
	<c:redirect url="/cms/user/pc/modules/cmsbase/noaccess.jsp"/>
</c:if>


<%-- ACTION --%>
<c:choose>
<c:when test="${param.action eq 'uploadFile'}">
<c:set target="${ufile}" property="request" value="${pageContext.request}"/>
<sr:do bean="${ufile}" property="upload"/>
<c:redirect url="editfile.jsp"/>
</c:when>

<c:when test="${param.action eq 'save'}">
<c:set target="${ufile}" property="name" value="${param.filename}"/>
<c:set target="${ufile}" property="authorId" value="${usersession.userId}"/>

<c:forEach items="${paramValues.templateItemIds}" var="tiid">
<c:set target="${ufile}" property="templateItemId" value="${tiid}"/>
<c:set var="itemparam" value="item${tiid}"/> <%-- because in form, we must use itemXX, otherwise it does not work with javascript if there is a number as a name value --%>
<c:set target="${ufile}" property="templateItemValue" value="${param[itemparam]}"/>
</c:forEach>

<sr:do bean="${ufile}" property="save" redirectOk="browser.jsp?action=browse&directoryId=${ufile.directoryId}" />
</c:when>
</c:choose>



<%@ include file="../../part1.jspf" %>

<table class="horizontal">
<form method="post" action="editfile.jsp?action=uploadFile" enctype="multipart/form-data">
<caption><fmt:message key='file'/></caption>
<tr><th><fmt:message key='upload.file'/></th><td><input type="file" name="myfile" /><input type="submit" value="<fmt:message key='upload'/>"></td></tr>
</form>

<form method="post" name="description">
<input type="hidden" name="action" value="save">

<tr class="textinput"><th><fmt:message key='filename'/></th><td><sr:input type="text" name="filename" value="${ufile.name}"/></td></tr>

<c:forEach items="${ufile.templateItem}" var="ti">
<input type="hidden" name="templateItemIds" value="${ti.id}"/>
<tr class="textinput">
<th><c:out value="${ti.fieldname}"/></th>

<td>
<c:choose>
<c:when test="${ti.freeType}"><sr:input type="text" name="item${ti.id}" attributesText="maxlength='${ti.size}'" value="${ti.value}"/></c:when>
<c:when test="${ti.dateType}">

<sr:input type="text" name="item${ti.id}" size="${ti.size}" value="${ti.value}"/>
<a href="javascript:cal${ti.id}.popup();"><img src="/openplatform/js/calendar/img/cal.gif" width="16" height="16" border="0" alt="Click Here to Pick up the date"></a><br>
<script language="JavaScript">
var cal${ti.id} = new calendar1(document.forms['description'].elements['item${ti.id}']);
cal${ti.id}.year_scroll = true;
cal${ti.id}.time_comp = true;
document.forms['description'].elements['item${ti.id}'].readOnly = true;
</script>

</c:when>
<c:when test="${ti.enumerationType}"><sr:select name="item${ti.id}" size="0" options="${ti.enumerationOption}" defaultOption="${ti.value}"/></c:when>
</c:choose>
</td>
</tr>
</c:forEach>
</table>

<c:choose>
    <c:when test="${ufile.expired}">
        <input type="submit" value="<fmt:message key='review'/>">
    </c:when>

    <c:otherwise>
        <input type="submit" value="<fmt:message key='save'/>">
    </c:otherwise>
</c:choose>

<fmt:message key='back' var="i18n_back"/>
<sr:a myclass="button" href="browser.jsp?action=browse&directoryId=${ufile.directoryId}"><c:out value="${i18n_back}"/></sr:a>

</form>

<%@ include file="../../part2.jspf" %>