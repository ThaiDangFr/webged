<jsp:useBean id="module" scope="session" class="dang.cms.AdminModule" />

<%@ include file="../../part1.jspf" %>
<div id="main-copy">
<%@ include file="../../part2.jspf" %>

<a href="${PREFIX}/modules.jsp?page=modules">Modules</a> > <a href="${PREFIX}/modules.jsp?page=modules&moduleId=${module.id}"><c:out value="${module.name}"/></a> > <fmt:message key="settings"/>

<ul>
<li><a href="${PREFIX}/modules/ged/template.jsp"><fmt:message key='ged.template'/></a></li>
<li><a href="${PREFIX}/modules/ged/workflow.jsp">Workflow</a></li>
<li><a href="${PREFIX}/modules/ged/pref.jsp"><fmt:message key='preferences'/></a></li>
<li><a href="${PREFIX}/modules/ged/gedstate.jsp"><fmt:message key='ged.state'/></a></li>
</ul>

</div>
<%@ include file="../../part3.jspf" %>