<jsp:useBean id="alic" scope="session" class="openplatform.license.AdminLicense" />


<%-- ACTION --%>
<c:if test="${param.action eq 'upload'}">
    <c:set target="${alic}" property="request" value="${pageContext.request}"/>
    <sr:do bean="${alic}" property="upload" redirectOk="/openplatform/cms/admin/"/>
</c:if>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en-AU">

<head>
    <meta http-equiv="CACHE-CONTROL" content="NO-CACHE"/>
    <meta http-equiv="PRAGMA" content="NO-CACHE"/>
    <meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8" />
    <link rel="stylesheet" type="text/css" href="/openplatform/css/gila-screen.css" media="screen" title="Gila (screen)" />
    <style type="text/css">@import url(/openplatform/cms/admin/cms.css);</style>
    <style type="text/css">@import url(local.css);</style>
    <link rel="SHORTCUT ICON" href="/openplatform/favicon.ico"/>
    <script language="JavaScript" src="/openplatform/js/common.js" type="text/JavaScript"></script>
    <script language="JavaScript" src="/openplatform/js/calendar/calendar1.js"></script>
    <script language="JavaScript">
    function confirmDelete() 
    {
        if(!confirm("<fmt:message key='confirm.delete'/>"))
	{
            return false;
	}
	else
	{
            return true;
	}
    }
    </script>

    <title>CMS ADMINISTRATION</title>
</head>

<body>

<div id="main-copy">
<table class="horizontal">
	<tr><th><fmt:message key='version'/></th><td><c:out value="${alic.version}"/></td></tr>

    <tr><th><fmt:message key='license.id'/></th><td><c:out value="${alic.licenseId}"/></td></tr>

    <c:if test="${not empty alic.maxHost}">
        <tr><th><fmt:message key='websnmp.max.host'/></th><td><c:out value="${alic.currentHost}"/> / <c:out value="${alic.maxHost}"/></td></tr>
    </c:if>

    <c:if test="${not empty alic.maxUser}">
        <tr><th><fmt:message key='max.user.account'/></th><td><c:out value="${alic.currentUser}"/> / <c:out value="${alic.maxUser}"/></td></tr>
    </c:if>

    <c:choose>
        <c:when test="${alic.licensed}">
	    <tr><th><fmt:message key='expiration.date'/></th>    <td><c:out value="${alic.expiryDate}"/></td></tr>
	    <tr><th><fmt:message key='license.grant.to'/></th>   <td><c:out value="${alic.companyName}"/></td></tr>
	</c:when>

	<c:otherwise>
	    <tr><th><fmt:message key='update.license'/></th><td>Unlicensed product</td></tr>
	</c:otherwise>
    </c:choose>


</table>

<form action="license.jsp?action=upload" enctype="multipart/form-data" method="post">
    <table class="horizontal">
        <tr><th><fmt:message key='update.license'/></th><td><input type="file" name="myfile"><input type="submit" value="<fmt:message key='upload'/>"/></td></tr>
    </table>
</form>
</div>

</body>
</html>
