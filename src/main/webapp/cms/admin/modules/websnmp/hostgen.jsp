<jsp:useBean id="module" scope="session" class="dang.cms.AdminModule" />
<jsp:useBean id="adminHost" scope="session" class="dang.websnmp.AdminHostBean" />

<c:if test="${param.action=='delete'}">
    <c:set target="${adminHost}" property="hostToDelete" value="${paramValues.snmphostid}"/>
    <sr:do bean="${adminHost}" property="deleteHost"/>
</c:if>

<sr:do bean="${adminHost}" property="clear"/>


<%@ include file="../../part1.jspf" %>
<div id="main-copy">
<%@ include file="../../part2.jspf" %>



<a href="${PREFIX}/modules.jsp?page=modules">Modules</a> > <a href="${PREFIX}/modules.jsp?page=modules&moduleId=${module.id}"><c:out value="${module.name}"/></a> > <a href="${PREFIX}/modules/websnmp/index.jsp?page=modules"><fmt:message key='settings'/></a> > <fmt:message key='host'/>


<br/>
<br/>

<a href="discovery.jsp"><fmt:message key='net.discovery'/></a>

<br/>
<br/>


<form action="hostgen.jsp" method="post" name="gen" onSubmit="return confirmDelete()">
    <input type="hidden" name="action" value="delete">
    
    <table>
    <tr>
    <th></th>
    <th><fmt:message key='host.name'/></th>
    <th><fmt:message key='server.ip'/></th>
    <th><fmt:message key='snmp.version'/></th>
    </tr>
 
   <c:set var="dbhosts" value="${adminHost.allHost}" scope="page" />
   <c:choose>

   <c:when test="${dbhosts==null}">
       <tr>
            <td colspan="4"><span class="info"><fmt:message key='no.hosts'/></span></td>
       </tr>
   </c:when>

   <c:when test="${dbhosts!=null}">
       <c:forEach items="${dbhosts}" var="h">
            
           <tr>
           <td width="5%"><input type="checkbox" name="snmphostid" value="${h.snmpHostId}"></td>
	   <td><a href="hostedit.jsp?snmpHostId=${h.snmpHostId}"><c:out value="${h.displayName}"/></a></td>
	   <td><c:out value="${h.ipAddress}"/></td>
	   <td><sr:select name="snmpVersion" options="${adminHost.snmpOptions}" defaultOption="${h.snmpVersion}" attributesText="disabled"/></td>


           </tr>
           
       </c:forEach>
   </c:when>

   </c:choose>
    </table> 
    
    <input type="button" value="<fmt:message key='add'/>" onClick="window.location='hostadd.jsp'" class="button">
    <input type="submit" value="<fmt:message key='delete'/>" class="button">
 
</form>

</div>
<%@ include file="../../part3.jspf" %>