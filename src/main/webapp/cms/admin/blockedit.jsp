<jsp:useBean id="adminblockedit" scope="session" class="dang.cms.AdminBlockEdit" />

<c:if test="${param.action eq 'save'}">
    <jsp:setProperty name="adminblockedit" property="title" value="${param.title}"/>
    <jsp:setProperty name="adminblockedit" property="weight" value="${param.weight}"/>
    <jsp:setProperty name="adminblockedit" property="visibleIn" value="${paramValues.visibleIn}"/>
    <jsp:setProperty name="adminblockedit" property="visible" value="${param.visible}"/>
    <jsp:setProperty name="adminblockedit" property="position" value="${param.position}"/>
    
    <sr:do bean="${adminblockedit}" property="store" />
</c:if>


<%@ include file="part1.jspf" %>

<div id="main-copy">
<%@ include file="part2.jspf" %>

<jsp:setProperty name="adminblockedit" property="blockId" value="${param.blockId}"/>

<form method="post">
<input type="hidden" name="action" value="save">

<table class="horizontal">
<caption><fmt:message key='block.edit'/></caption>
<tr><th><fmt:message key='title'/></th><td><sr:input type="text" name="title" value="${adminblockedit.title}"/></td></tr>
<tr><th><fmt:message key='weight'/></th><td><sr:input type="text" name="weight" value="${adminblockedit.weight}" size="2"/></td></tr>
<tr><th><fmt:message key='visible.in'/></th><td><sr:select name="visibleIn" multiple="true" options="${adminblockedit.visibleInOption}" defaultOptionArray="${adminblockedit.visibleIn}" size="5"/></td></tr>
<tr><th><fmt:message key='visibility'/></th><td><sr:select name="visible" multiple="false" options="${adminblockedit.visibleOption}" defaultOption="${adminblockedit.visible}"/></td></tr>
<tr><th>Position</th><td><sr:select name="position" multiple="false" options="${adminblockedit.positionOption}" defaultOption="${adminblockedit.position}"/></td></tr>
</table>

<input type="submit" value="<fmt:message key='save'/>">
<input type="button" value="<fmt:message key='cancel'/>" onClick="self.location.href='blocks.jsp?page=blocks'">

</form>
</div>
<%@ include file="part3.jspf" %>