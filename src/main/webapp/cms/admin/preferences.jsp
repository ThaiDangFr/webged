<jsp:useBean id="adminpref" scope="session" class="dang.cms.AdminPref" />

<%-- ACTION BEG --%>
<c:choose>
<c:when test="${param.action eq 'save'}">
	<sr:set bean="${adminpref}" property="css|smtpHost|adminMail|adminMail|firstPageId|header|footer|siteTitle|ipAddress|cmsLang|ldapAuthent|ldapServer|ldapDN|debugOn"/>
	<sr:do bean="${adminpref}" property="store"/>
</c:when>

<c:when test="${param.action eq 'uploadlogo'}">
<jsp:setProperty name="adminpref" property="request" value="${pageContext.request}"/>
<c:if test="${adminpref.uploadLogo}"><jsp:setProperty name="message" property="text" value="Logo uploaded !"/></c:if>
<c:redirect url="preferences.jsp"/>
</c:when>

<c:when test="${param.action eq 'deleteLogo'}"><c:if test="${adminpref.deleteLogo}"><jsp:setProperty name="message" property="text" value="Logo deleted !"/></c:if></c:when>
</c:choose>

<c:if test="${adminpref.load}"/>
<%-- ACTION END --%>




<%@ include file="part1.jspf" %>

<div id="main-copy">
<%@ include file="part2.jspf" %>

<%-- CONFIG BEG --%>
<form method="post">
<input type="hidden" name="action" value="save">

<table class="horizontal">
<caption>preferences</caption>
<tr><th>CSS</th><td><textarea name="css"><c:out value="${adminpref.css}"/></textarea></td></tr>
<tr><th><fmt:message key='smtp.host'/></th><td><sr:input type="text" name="smtpHost" value="${adminpref.smtpHost}"/></td></tr>
<tr><th><fmt:message key='admin.email'/></th><td><sr:input type="text" name="adminMail" value="${adminpref.adminMail}"/></td></tr>
<tr><th><fmt:message key='first.page'/></th><td><sr:select name="firstPageId" multiple="false" options="${adminpref.firstPageOption}" defaultOption="${adminpref.firstPageId}"/></td></tr>
<tr><th><fmt:message key='header'/></th><td><textarea name="header"><c:out value="${adminpref.header}"/></textarea></td></tr>
<tr><th><fmt:message key='footer'/></th><td><textarea name="footer"><c:out value="${adminpref.footer}"/></textarea></td></tr>
<tr><th><fmt:message key='site.title'/></th><td><sr:input type="text" name="siteTitle" value="${adminpref.siteTitle}"/></td></tr>
<tr><th><fmt:message key='print.debug'/></th><td><sr:select name="debugOn" options="${adminpref.trueFalse}" defaultOption="${adminpref.debugOn}"/></td></tr>
<tr><th><fmt:message key='external.link'/></th><td><sr:input type="text" name="ipAddress" value="${adminpref.ipAddress}"/></td></tr>
<tr><th><fmt:message key='language'/></th><td><sr:select name="cmsLang" options="${adminpref.cmsLangOpt}" defaultOption="${adminpref.cmsLang}"/></td></tr>

<tr>
	<th><fmt:message key='ldap.authent'/></th>
	<td>
	
		<!--  mask/unmask options -->
		<script language="javascript">
		function rendVisible(valeur)
		{
			if(valeur == 1)
			{
	      		document.getElementById('ldapoptions').style.display = "block";
	   		}
		   	else
		   	{
    	   		document.getElementById('ldapoptions').style.display = "none";
		   	}
		}
		</script>
	
		<select id="ldapAuthent" name="ldapAuthent" onChange="rendVisible(this.value);">
			<c:choose>
				<c:when test="${adminpref.ldapAuthent eq 0}">
					<option value="0" selected><fmt:message key='no'/></option>
					<option value="1"><fmt:message key='yes'/></option>
				</c:when>
				<c:otherwise>
					<option value="0"><fmt:message key='no'/></option>
					<option value="1" selected><fmt:message key='yes'/></option>
				</c:otherwise>
			</c:choose>
		</select>
		
		<div id="ldapoptions" style="display:block;">
			<fmt:message key='ldap.server'/> <sr:input type="text" name="ldapServer" value="${adminpref.ldapServer}"/>
			<fmt:message key='ldap.dnmask'/> <sr:input type="text" name="ldapDN" value="${adminpref.ldapDN}"/>
		</div>
		
		<!--  mask/unmask according to default value -->
		<script language="javascript">rendVisible(document.getElementById('ldapAuthent').value);</script>
	</td>
</tr>

</table>

<input type="submit" value="<fmt:message key='save'/>"/>
<input type="button" value="<fmt:message key='cancel'/>" onClick="self.location.href='index.jsp'"/>
</form>


<form action="preferences.jsp?action=uploadlogo" method="post" enctype="multipart/form-data">
<table class="horizontal">
<caption>logo</caption>
<tr><th>Logo</th><td><sr:img fileBaseId="${adminpref.logoId}"/><br><a href="preferences.jsp?action=deleteLogo"><fmt:message key='delete'/> logo</a></td></tr>
<tr><th><fmt:message key='upload'/> logo</th><td><input type="file" name="myfile"><input type="submit" value="<fmt:message key='upload'/>"></td></tr>
</table>
</form>





<%-- CONFIG END --%>
</div>
<%@ include file="part3.jspf" %>