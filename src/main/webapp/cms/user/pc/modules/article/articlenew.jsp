<%@ include file="import.jspf" %>
<%@ include file="importmoduleid.jspf" %>


<%-- CONTROL AND INIT --%>
<c:if test="${not usersession.connected}">
<jsp:forward page="${FWPREFIX}/cmsbase/needlogin.jsp"/>
</c:if>

<c:if test="${param.init eq 'true'}">
<c:if test="${articlenew.doInit}"/>
</c:if>



<%-- ACTION --%>
<c:choose>
<c:when test="${param.action eq 'joinfile'}">
    <jsp:setProperty name="articlenew" property="request" value="${pageContext.request}"/>
    <sr:do bean="${articlenew}" property="joinFile"/>
</c:when>

<c:when test="${param.action eq 'removeJoinFile'}">
    <jsp:setProperty name="articlenew" property="doRemoveJoinFile" value="${param.index}"/>
</c:when>

<c:when test="${param.action eq 'joinLink'}">
    <jsp:setProperty name="articlenew" property="doJoinLink" value="${param.joinLink}"/>
</c:when>

<c:when test="${param.action eq 'removeJoinLink'}">
<jsp:setProperty name="articlenew" property="doRemoveLink" value="${param.index}"/>
</c:when>

<c:when test="${param.action eq 'attachImage'}">
    <jsp:setProperty name="articlenew" property="request" value="${pageContext.request}"/>
    <sr:do bean="${articlenew}" property="attachImage"/>
</c:when>

<c:when test="${param.action eq 'removeImage'}">
    <sr:do bean="${articlenew}" property="removeImage"/>
</c:when>

<c:when test="${param.action eq 'save'}">
    <jsp:setProperty name="articlenew" property="authorId" value="${usersession.userId}"/>
    <sr:set bean="${articlenew}" property="imageLegend|title|categoryId|summary|text" />
    <sr:do bean="${articlenew}" property="save" redirectOk="index.jsp"/>
</c:when>

</c:choose>



<%@ include file="../../part1.jspf" %>

<h1 style="border-top: none; padding-top: 0;"><fmt:message key='article.submit'/></h1>

<%-- FILES --%>
<form action="${PREFIX}/article/articlenew.jsp?action=joinfile" enctype="multipart/form-data" method="post">
<input type="hidden" name="action" value="joinfile">
<table class="horizontal">
<caption><fmt:message key='files'/></caption>
<tr>
    <th><fmt:message key='file.path'/></th>
    <td><input type="file" name="myfile"/><input type="submit" value="<fmt:message key='upload'/>">
    <c:forEach var="f" items="${articlenew.joinFileName}" varStatus="vs">
    <br /><a class="noline" href="articlenew.jsp?action=removeJoinFile&index=${vs.count}">[X]</a> <c:out value="${f}"/>
    </c:forEach>
    </td>
</tr>
</table>
</form>

<%-- URL --%>
<form action="articlenew.jsp" method="post">
<input type="hidden" name="action" value="joinLink">
<table class="horizontal">
<caption><fmt:message key='weblink'/></caption>
<tr>
    <th><fmt:message key='weblink'/></th>
    <td><sr:input type="text" name="joinLink"/><input type="submit" value="<fmt:message key='add'/>">
    <c:forEach var="f" items="${articlenew.joinLink}" varStatus="vs">
    <br /><a class="noline" href="articlenew.jsp?action=removeJoinLink&index=${vs.count}">[X]</a> <c:out value="${f}"/>
    </c:forEach>
    </td>
</tr>
</table>
</form>

<%-- IMAGE --%>
<table class="horizontal">
<form action="${PREFIX}/article/articlenew.jsp?action=attachImage" enctype="multipart/form-data" method="post">
<input type="hidden" name="action" value="attachImage">
<caption>Image</caption>
<tr>
    <th><fmt:message key='image.path'/></th>
    <td><input type="file" name="myfile"/><input type="submit" value="<fmt:message key='upload'/>"> 
    <c:if test="${not empty articlenew.imageName}">
    <br />
    <a class="noline" href="articlenew.jsp?action=removeImage">[X]</a> <c:out value="${articlenew.imageName}"/>
    </c:if>
    </td>
</tr>
</form>



<form method="post" action="articlenew.jsp">
<tr>
    <th><fmt:message key='image.legend'/></th>
    <td><sr:input type="text" name="imageLegend" value="${articlenew.imageLegend}"/></td>
</tr>
</table>




<%-- ARTICLE --%>
<input type="hidden" name="action" value="save">
<table class="horizontal">
<caption>Article</caption>

<tr>
    <th><fmt:message key='title'/></th>
    <td><sr:input type="text" name="title" value="${articlenew.title}"/></td>
</tr>
<tr>
    <th><fmt:message key='category'/></th>
    <td><sr:select name="categoryId" multiple="false" options="${articlenew.categoryOptions}" defaultOption="${articlenew.categoryId}"/></td>
</tr>
<tr>
    <th><fmt:message key='summary'/></th>
    <td><sr:input type="textarea" name="summary" value="${articlenew.summary}"/></td>
</tr>
<tr>
    <th><fmt:message key='text'/></th>
    <td><sr:input type="textarea" name="text" value="${articlenew.text}"/></td>
</tr>
</table>

<input type="submit" value="<fmt:message key='save'/>">
<input type="button" value="<fmt:message key='cancel'/>" onClick="self.location.href='index.jsp'">
</form>


<%@ include file="../../part2.jspf" %>