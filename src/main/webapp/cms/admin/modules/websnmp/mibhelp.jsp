<jsp:useBean id="module" scope="session" class="dang.cms.AdminModule" />
<jsp:useBean id="adminMibHelp" scope="session" class="dang.websnmp.AdminMibHelpBean" />

<c:if test="${param.clear=='true'}">
    <sr:do bean="${adminMibHelp}" property="clear"/>
</c:if>

<c:set target="${adminMibHelp}" property="searchtext" value="${param.searchtext}"/>
<c:set target="${adminMibHelp}" property="searchOnMibname" value="${param.searchOnMibname}"/>
<c:set target="${adminMibHelp}" property="searchOnCaptorName" value="${param.searchOnCaptorName}"/>
<c:set target="${adminMibHelp}" property="searchOnDescription" value="${param.searchOnDescription}"/>
<c:set target="${adminMibHelp}" property="page" value="${param.page}"/>

<c:if test="${param.action=='search'}">
    <sr:do bean="${adminMibHelp}" property="search"/>
</c:if>

<%@ include file="../../part1.jspf" %>
<div id="main-copy">
<%@ include file="../../part2.jspf" %>


<a href="${PREFIX}/modules.jsp?page=modules">Modules</a> > <a href="${PREFIX}/modules.jsp?page=modules&moduleId=${module.id}"><c:out value="${module.name}"/></a> > <a href="${PREFIX}/modules/websnmp/index.jsp?page=modules"><fmt:message key='settings'/></a> > <fmt:message key='mib.search'/>

<br/>
<br/>

<form name="search" method="post" action="mibhelp.jsp">
<input type="hidden" name="action" value="search">

<table class="horizontal">
<tr>
<td class="bold"><fmt:message key='keyword'/></td>
<td><input:text name="searchtext" default="${param.searchtext}" attributesText='size="22"'/></td>
<td><fmt:message key='search.in'/></td>
<td><input:checkbox name="searchOnMibname" value="true"/> <fmt:message key='mibname'/></td>
<td><input:checkbox name="searchOnCaptorName" value="true"/> <fmt:message key='oidname'/></td>
<td><input:checkbox name="searchOnDescription" value="true"/> <fmt:message key='description'/></td>
</tr>
</table>
<input type="submit" value="<fmt:message key='search'/>" class="button">
</form>




<c:if test="${param.action=='search'}">

<c:forEach items="${adminMibHelp.snmpOID}" var="oid">
<table class="mibhelp">
<tr class="title">
<td>
<c:out value="${oid.mibName}"/>_<c:out value="${oid.captorName}"/>
</td>
</tr>

<td>
<c:choose>
<c:when test="${oid.description==null}">
<pre><fmt:message key='no.description'/></pre>
</c:when>

<c:when test="${oid.description!=null}">
<pre><c:out value="${oid.description}"/></pre>
</c:when>

</c:choose>
</td>

</table>
</c:forEach>

</c:if>



<center>
<!-- previous button -->
<c:if test="${adminMibHelp.previous!=0}">
<c:url var="pageurl" scope="page" value="mibhelp.jsp">
<c:param name="searchtext" value="${param.searchtext}"/>
<c:param name="searchOnMibname" value="${param.searchOnMibname}"/>
<c:param name="searchOnCaptorName" value="${param.searchOnCaptorName}"/>
<c:param name="searchOnDescription" value="${param.searchOnDescription}"/>
<c:param name="page" value="${adminMibHelp.previous}"/>
<c:param name="action" value="search"/>
</c:url>
<a href="${pageurl}">[Previous page]</a>
</c:if>

<!-- page numbers -->
<c:forEach begin="${adminMibHelp.beginPage}" end="${adminMibHelp.endPage}" var="pg">
<c:url var="pageurl" scope="page" value="mibhelp.jsp">
<c:param name="searchtext" value="${param.searchtext}"/>
<c:param name="searchOnMibname" value="${param.searchOnMibname}"/>
<c:param name="searchOnCaptorName" value="${param.searchOnCaptorName}"/>
<c:param name="searchOnDescription" value="${param.searchOnDescription}"/>
<c:param name="page" value="${pg}"/>
<c:param name="action" value="search"/>
</c:url>

<c:choose>
<c:when test="${adminMibHelp.page==pg}">
<c:out value="${pg}"/>
</c:when>

<c:when test="${adminMibHelp.page!=pg}">
<a href="${pageurl}"><c:out value="${pg}"/></a>
</c:when>
</c:choose>
</c:forEach>

<!-- next button -->
<c:if test="${adminMibHelp.next!=0}">
<c:url var="pageurl" scope="page" value="mibhelp.jsp">
<c:param name="searchtext" value="${param.searchtext}"/>
<c:param name="searchOnMibname" value="${param.searchOnMibname}"/>
<c:param name="searchOnCaptorName" value="${param.searchOnCaptorName}"/>
<c:param name="searchOnDescription" value="${param.searchOnDescription}"/>
<c:param name="page" value="${adminMibHelp.next}"/>
<c:param name="action" value="search"/>
</c:url>
<a href="${pageurl}">[Next page]</a>
</c:if>
</center>

</div>
<%@ include file="../../part3.jspf" %>