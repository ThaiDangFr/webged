<%@ page contentType="application/soap; charset=utf-8" %>

<jsp:useBean id="asoap" scope="session" class="dang.mailarchive.ArchivelSoap"/>

<c:set target="${asoap}" property="request" value="${pageContext.request}"/>
<c:out value="${asoap.response}" escapeXml="false"/>