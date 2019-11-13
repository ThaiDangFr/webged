<jsp:useBean id="module" scope="session" class="dang.cms.AdminModule" />
<jsp:useBean id="adminFormula" scope="session" class="dang.websnmp.AdminFormulaBean" />


<c:if test="${param.action=='delete'}">
<c:set target="${adminFormula}" property="formulaToDelete" value="${paramValues.formulaId}"/>
<sr:do bean="${adminFormula}" property="deleteFormula"/>
</c:if>


<%@ include file="../../part1.jspf" %>
<div id="main-copy">
<%@ include file="../../part2.jspf" %>

<a href="${PREFIX}/modules.jsp?page=modules">Modules</a> > <a href="${PREFIX}/modules.jsp?page=modules&moduleId=${module.id}"><c:out value="${module.name}"/></a> > <a href="${PREFIX}/modules/websnmp/index.jsp?page=modules"><fmt:message key='settings'/></a> > <fmt:message key='formula'/>

<br/>
<br/>

<form action="formulagen.jsp" method="POST" name="gen" onSubmit="return confirmDelete()">
<input type="hidden" name="action" value="delete">

<table class="vertical">
<tr>
<th></th>
<th><fmt:message key='formula.name'/></th>
<th>Description</th>
</tr>

<c:set var="allF" value="${adminFormula.allFormula}" scope="page"/>
<c:choose>
<c:when test="${allF==null}">
<tr><td width="5%"></td><td><span class="notice">No formula found</span></td></tr>
</c:when>

<c:when test="${allF!=null}">
<c:forEach items="${allF}" var="f">
<tr>
<td width="5%"><input type="checkbox" name="formulaId" value="${f.formulaId}"></td>
<td><a href="formulaedit.jsp?init=true&formulaId=${f.formulaId}"><c:out value="${f.displayName}"/></a></td>
<td><c:out value="${fn:substring(f.description,0, 45)}"/></td>
</tr>
</c:forEach>
</c:when>
</c:choose>

</table>

<input type="button" value="<fmt:message key='add'/>" onClick="window.location='formulaedit.jsp?init=true'" class="button">
<input type="submit" value="<fmt:message key='delete'/>" class="button">

</form>

</div>
<%@ include file="../../part3.jspf" %>