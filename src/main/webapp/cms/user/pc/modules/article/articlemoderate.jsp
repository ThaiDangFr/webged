<%@ include file="import.jspf" %>
<%@ include file="importmoduleid.jspf" %>


<%--  CONTROL --%>
<c:if test="${not usersession.connected}">
    <jsp:forward page="${FWPREFIX}/cmsbase/needlogin.jsp"/>
</c:if>


<%-- LOADING --%>
<c:if test="${not empty param.articleId}"><jsp:setProperty name="articlemoderate" property="articleId" value="${param.articleId}"/></c:if>

<jsp:setProperty name="articlemoderate" property="moderatorId" value="${usersession.userId}"/>
<c:if test="${not articlemoderate.isModerator}"><jsp:forward page="${FWPREFIX}/cmsbase/noaccess.jsp"/></c:if>

<c:if test="${not empty param.articleId}"><c:if test="${articlemoderate.loadArticle}"/></c:if>


<%-- ACTION --%>
<c:choose>

<c:when test="${param.action eq 'deleteArticle'}">
    <sr:cmstrace moduleId="${MODULEID}" object="${articlemoderate.title}" action="delete" statusMsg="ok" statusUserId="${usersession.userId}" ipAddress="${pageContext.request.remoteAddr}"/>
    <sr:do bean="${articlemoderate}" property="delete" redirectOk="articleapprove.jsp"/>
</c:when>

<c:when test="${param.action eq 'joinfile'}">
    <jsp:setProperty name="articlemoderate" property="request" value="${pageContext.request}"/>
    <sr:do bean="${articlemoderate}" property="joinFile"/>
</c:when>

<c:when test="${param.action eq 'removeJoinFile'}">
    <jsp:setProperty name="articlemoderate" property="doRemoveJoinFile" value="${param.index}"/>
</c:when>

<c:when test="${param.action eq 'joinLink'}">
    <jsp:setProperty name="articlemoderate" property="doJoinLink" value="${param.joinLink}"/>
</c:when>

<c:when test="${param.action eq 'removeJoinLink'}">
    <jsp:setProperty name="articlemoderate" property="doRemoveLink" value="${param.index}"/>
</c:when>

<c:when test="${param.action eq 'attachImage'}">
    <jsp:setProperty name="articlemoderate" property="request" value="${pageContext.request}"/>
    <sr:do bean="${articlemoderate}" property="attachImage"/>
</c:when>

<c:when test="${param.action eq 'removeImage'}">
    <sr:do bean="${articlemoderate}" property="removeImage"/>
</c:when>

<c:when test="${param.action eq 'save'}">
    <jsp:setProperty name="articlemoderate" property="moderatorId" value="${usersession.userId}"/>
    <sr:set bean="${articlemoderate}" property="imageLegend|title|categoryId|summary|text|validate"/>
    <sr:do bean="${articlemoderate}" property="save" redirectOk="articleapprove.jsp"/>

    <c:choose><c:when test="${param.validate eq '0'}"><sr:cmstrace moduleId="${MODULEID}" object="${param.title}" action="disapprove" statusMsg="ok" statusUserId="${usersession.userId}" ipAddress="${pageContext.request.remoteAddr}"/></c:when>
<c:otherwise><sr:cmstrace moduleId="${MODULEID}" object="${param.title}" action="approve" statusMsg="ok" statusUserId="${usersession.userId}" ipAddress="${pageContext.request.remoteAddr}"/></c:otherwise></c:choose>

</c:when>
</c:choose>





<%@ include file="../../part1.jspf" %>

<%-- DATE --%>
<table class="horizontal">
<caption>date</caption>
<tr>
    <th>Date</th>
    <td>
    	<fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss.S" var="parsedDateTime" value="${articlemoderate.date}"/>
    	<fmt:formatDate value="${parsedDateTime}" type="both" dateStyle="short" timeStyle="short"/>
    	<%-- <c:out value="${articlemoderate.date}"/> --%>
    </td>
</tr>
</table>    


<%-- FILES --%>
<form action="${PREFIX}/article/articlemoderate.jsp?action=joinfile" enctype="multipart/form-data" method="post">
<input type="hidden" name="action" value="joinfile">
<table class="horizontal">
<caption><fmt:message key='files'/></caption>
<tr>
    <th><fmt:message key='file.path'/></th>
    <td><input type="file" name="myfile"/><input type="submit" value="<fmt:message key='upload'/>">
    <c:forEach var="f" items="${articlemoderate.joinFileName}" varStatus="vs">
    <br /><a class="noline" href="articlemoderate.jsp?action=removeJoinFile&index=${vs.count}">[X]</a> <c:out value="${f}"/>
    </c:forEach>
    </td>    
</tr>
</table>
</form>

<%-- URL --%>
<form action="articlemoderate.jsp" method="post">
<input type="hidden" name="action" value="joinLink">
<table class="horizontal">
<caption><fmt:message key='weblink'/></caption>
<tr>
    <th><fmt:message key='weblink'/></th>
    <td><sr:input type="text" name="joinLink"/><input type="submit" value="<fmt:message key='add'/>">
    <c:forEach var="f" items="${articlemoderate.joinLink}" varStatus="vs">
    <br /><a class="noline" href="articlemoderate.jsp?action=removeJoinLink&index=${vs.count}">[X]</a> <c:out value="${f}"/>
    </c:forEach>
    </td>
</tr>
</table>
</form>

<%-- IMAGE --%>
<table class="horizontal">
<form action="${PREFIX}/article/articlemoderate.jsp?action=attachImage" enctype="multipart/form-data" method="post">
<input type="hidden" name="action" value="attachImage">
<caption>image</caption>
<tr>
    <th><fmt:message key='image.path'/></th>
    <td><input type="file" name="myfile"/><input type="submit" value="<fmt:message key='upload'/>">
    <br /><c:if test="${not empty articlemoderate.imageName}"><a class="noline" href="articlemoderate.jsp?action=removeImage">[X]</a> <c:out value="${articlemoderate.imageName}"/></c:if>
    </td>
</tr>
</form>


<form method="post" action="articlemoderate.jsp">
<tr>
    <th><fmt:message key='image.legend'/></th>
    <td><sr:input type="text" name="imageLegend" value="${articlemoderate.imageLegend}"/></td>
</tr>
</table>



<%-- ARTICLE --%>
<input type="hidden" name="action" value="save">
<table class="horizontal">
<caption>article</caption>
<tr>
    <th><fmt:message key='title'/></th>
    <td><sr:input type="text" name="title" value="${articlemoderate.title}"/></td>
</tr>
<tr>
    <th><fmt:message key='category'/></th>
    <td><sr:select name="categoryId" multiple="false" options="${articlemoderate.categoryOptions}" defaultOption="${articlemoderate.categoryId}"/></td>
</tr>
<tr>
    <th><fmt:message key='summary'/></th>
    <td><sr:input type="textarea" name="summary" value="${articlemoderate.summary}"/></td>
</tr>
<tr>
    <th><fmt:message key='text'/></th>
    <td><sr:input type="textarea" name="text" value="${articlemoderate.text}"/></td>
</tr> 
<tr>
    <th><fmt:message key='status'/></th>
    <td><sr:select name="validate" multiple="false" options="${articlemoderate.validateOption}" defaultOption="${articlemoderate.validate}"/></td>
</tr>
</table>


<input type="submit" value="<fmt:message key='save'/>">
<input type="button" value="<fmt:message key='cancel'/>" onClick="self.location.href='articleapprove.jsp'">
<input type="button" value="<fmt:message key='delete'/>" onClick="if(confirmDelete()) { self.location.href='articlemoderate.jsp?action=deleteArticle'; }">
<input type="button" value="<fmt:message key='preview'/>" onClick="window.open('articleview.jsp?articleId=${articlemoderate.articleId}','<fmt:message key='preview'/>','width=800,height=600')">
</form>

<%@ include file="../../part2.jspf" %>