<jsp:useBean id="dl" scope="page" class="openplatform.http.DownloadConvert" />
<%@ page contentType="text/plain; charset=UTF-8"%>

<c:set target="${dl}" property="fileBaseId" value="${param.fileBaseId}" />
<c:set target="${dl}" property="convert" value="${param.convert}" />
<c:set target="${dl}" property="disposition" value="${param.disposition}" />
<c:set target="${dl}" property="extension" value="${param.extension}" />
<c:set target="${dl}" property="param" value="${param.param}" />
<c:set target="${dl}" property="response" value="${pageContext.response}" />


<sr:do bean="${dl}" property="download" />


<%
out.clear();
out = pageContext.pushBody(); 
%>