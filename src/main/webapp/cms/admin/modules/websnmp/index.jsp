<jsp:useBean id="module" scope="session" class="dang.cms.AdminModule" />

<%@ include file="../../part1.jspf" %>
<div id="main-copy">
<%@ include file="../../part2.jspf" %>

<a href="${PREFIX}/modules.jsp?page=modules">Modules</a> > <a href="${PREFIX}/modules.jsp?page=modules&moduleId=${module.id}"><c:out value="${module.name}"/></a> > <fmt:message key='settings'/>

<br/><br/>


<ul>
<li><a href="${PREFIX}/modules/websnmp/hostgen.jsp"><fmt:message key='host'/></a></li>
<li><a href="${PREFIX}/modules/websnmp/formulagen.jsp"><fmt:message key='formula'/></a></li>
<li><a href="${PREFIX}/modules/websnmp/mibman.jsp"><fmt:message key='mib.manager'/></a></li>
<li><a href="${PREFIX}/modules/websnmp/mibhelp.jsp?searchOnMibname=true&searchOnCaptorName=true&searchOnDescription=true&clear=true"><fmt:message key='mib.search'/></a></li>
<li><a href="${PREFIX}/modules/websnmp/mibtest.jsp"><fmt:message key='mib.test'/></a></li>
<li><a href="${PREFIX}/modules/websnmp/reportgen.jsp"><fmt:message key='report'/></a></li>
<li><a href="${PREFIX}/modules/websnmp/alertgen.jsp"><fmt:message key='alert'/></a></li>
<li><a href="${PREFIX}/modules/websnmp/actiongen.jsp">Action</a></li>
</ul>

</div>
<%@ include file="../../part3.jspf" %>