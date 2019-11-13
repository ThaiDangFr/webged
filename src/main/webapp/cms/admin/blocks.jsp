<jsp:useBean id="adminblock" scope="session" class="dang.cms.AdminBlock" />

<%@ include file="part1.jspf" %>
<%@ include file="part2.jspf" %>
<div id="main-copy">
<c:if test="${param.action eq 'filter'}">
    <c:set target="${adminblock}" property="visibleIn" value="${param.visibleIn}"/>
    <c:set target="${adminblock}" property="visible" value="${param.visible}"/>
</c:if>


<form method="post">
<input type="hidden" name="action" value="filter">
<fmt:message key='only.block.in'/> <sr:select name="visibleIn" multiple="false" options="${adminblock.visibleInOption}" defaultOption="${adminblock.visibleIn}"/>
<fmt:message key='and'/> <sr:select name="visible" multiple="false" options="${adminblock.visibleOption}" defaultOption="${adminblock.visible}"/>
<input type="submit" value="<fmt:message key='filter'/>">
</form>

<form method="post">
<input type="hidden" name="action" value="filter">
<input type="submit" value="<fmt:message key='showall'/>">
</form>



<table class="vertical">

<caption><fmt:message key='blocks'/></caption>

<tr>
<th>Module</th><th><fmt:message key='title'/></th><th>Position</th><th><fmt:message key='weight'/></th><th>Visible</th><th>Action</th>
</tr>

<c:set var="listblocks" value="${adminblock.blockList}"/>
<c:if test="${empty listblocks}"><tr><td colspan="6"><fmt:message key="no.block.found"/></td></tr></c:if>

<c:forEach var="b" items="${listblocks}">
<jsp:setProperty name="adminblock" property="moduleId" value="${b.moduleId}"/>
<tr>
<td><c:out value="${adminblock.moduleName}"/></td>
<td><c:out value="${b.title}"/></td>
<td><fmt:message key='${b.position}'/></td>
<td><c:out value="${b.weight}"/></td>

<td><c:choose><c:when test="${b.visible eq 1}"><fmt:message key='yes'/></c:when><c:otherwise><fmt:message key='no'/></c:otherwise></c:choose></td>

<td><a href="blockedit.jsp?page=blocks&amp;blockId=${b.id}"><fmt:message key='edit'/></a></td>
</tr>
</c:forEach>
</table>
</div>
<%@ include file="part3.jspf" %>