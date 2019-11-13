<jsp:useBean id="module" scope="session" class="dang.cms.AdminModule" />
<jsp:useBean id="adminHost" scope="session" class="dang.websnmp.AdminHostBean" />

<c:choose>

<c:when test="${param.action == null}">
<c:set target="${adminHost}" property="snmpHostId" value="${param.snmpHostId}"/>
</c:when>

<c:when test="${param.action != null}">
    <c:set target="${adminHost}" property="displayName" value="${param.displayName}"/>
    <c:set target="${adminHost}" property="ipAddress" value="${param.ipAddress}"/>
    <c:set target="${adminHost}" property="community" value="${param.community}"/>
    <c:set target="${adminHost}" property="snmpVersion" value="${param.snmpVersion}"/>
    <c:set target="${adminHost}" property="userName" value="${param.userName}"/>
    <c:set target="${adminHost}" property="authentPw" value="${param.authentPw}"/>
    <c:set target="${adminHost}" property="privacyPw" value="${param.privacyPw}"/>
</c:when>

</c:choose>

<c:if test="${param.action=='save'}">
    <sr:do bean="${adminHost}" property="updateHost" redirectOk="hostgen.jsp"/>
</c:if>


<%@ include file="../../part1.jspf" %>
<div id="main-copy">
<%@ include file="../../part2.jspf" %>


<a href="${PREFIX}/modules.jsp?page=modules">Modules</a> > <a href="${PREFIX}/modules.jsp?page=modules&moduleId=${module.id}"><c:out value="${module.name}"/></a> > <a href="${PREFIX}/modules/websnmp/index.jsp?page=modules"><fmt:message key='settings'/></a> > Host

<br/>
<br/>


<form method="post" action="hostedit.jsp" name="hostedit">
    <input type="hidden" name="action" value="save" >
    <input type="hidden" name="snmpHostId" value="${adminHost.snmpHostId}">
 
    
    <table class="horizontal">  
    <caption><fmt:message key='edit.host'/></caption>
    <tr>
    <th><fmt:message key='host.name'/></th>
    <td><input:text name="displayName" default="${adminHost.displayName}" attributesText='size="20" maxlength="20"'/></td>
    </tr>

    <tr>
    <th><fmt:message key='server.ip'/></th>
    <td><input name="ipAddress" value="${adminHost.ipAddress}" type="text" size="20"></td>
    </tr>

    <tr>
    <th><fmt:message key='snmp.version'/></th>
    <td>
    <sr:select name="snmpVersion" options="${adminHost.snmpOptions}" defaultOption="${adminHost.snmpVersion}" attributesText='onChange="document.hostedit.action.value=\'view\';document.hostedit.submit();"'/>
    </td>
    </tr>

<c:if test="${adminHost.snmpVOneTwo}">
    <tr>
    <th><fmt:message key='community'/></th>
    <td><input:text name="community" default="${adminHost.community}" attributesText='size="20"'/></td>
    </tr>
</c:if>

<c:if test="${adminHost.snmpVThree}">
    <tr>
    <th><fmt:message key='username'/></th>
    <td><input:text name="userName" default="${adminHost.userName}" attributesText='size="20"'/></td>
    </tr>

    <tr>
    <th><fmt:message key='authent.password'/></th>
    <td><input:text name="authentPw" default="${adminHost.authentPw}" attributesText='size="20"'/></td>
    </tr>

    <tr>
    <th><fmt:message key='privacy.password'/></th>
    <td><input:text name="privacyPw" default="${adminHost.privacyPw}" attributesText='size="20"'/></td>
    </tr>
</c:if>

    </table>
    
    <input type="submit" value="<fmt:message key='save'/>" class="button">
    <input type="button" value="<fmt:message key='clear'/>" onClick="clearForm(0)" class="button">
    <input type="button" value="<fmt:message key='cancel'/>" onClick="window.location='hostgen.jsp'" class="button">
   
</form>

</div>
<%@ include file="../../part3.jspf" %>