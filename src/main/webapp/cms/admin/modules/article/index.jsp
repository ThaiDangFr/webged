<jsp:useBean id="module" scope="session" class="dang.cms.AdminModule" />
<jsp:useBean id="articlepref" scope="session" class="dang.article.AdminPref" />
<jsp:useBean id="articlecat" scope="session" class="dang.article.AdminCategory" />
<jsp:useBean id="articlemod" scope="session" class="dang.article.AdminModerator" />

<c:choose>
<c:when test="${param.action eq 'savepref'}">
	<jsp:setProperty name="articlepref" property="articlePerPage" value="${param.articlePerPage}"/>
	<jsp:setProperty name="articlepref" property="commentsActivated" value="${param.commentsActivated}"/>
	<sr:do bean="${articlepref}" property="save"/>
</c:when>

<c:when test="${param.action eq 'addcat'}">
	<jsp:setProperty name="articlecat" property="addCategory" value="${param.category}"/>
</c:when>

<c:when test="${param.action eq 'removecat'}">
	<jsp:setProperty name="articlecat" property="removeCategory" value="${paramValues.category}"/>
	<sr:do bean="${articlecat}" property="removeCategory"/>
</c:when>

<c:when test="${param.action eq 'renamecat'}">
	<jsp:setProperty name="articlecat" property="renameCategoryId" value="${param.category}"/>
	<jsp:setProperty name="articlecat" property="renameTo" value="${param.renameTo}"/>
	<sr:do bean="${articlecat}" property="rename"/>
</c:when>

<c:when test="${param.action eq 'moderator'}">
	<c:forEach var="c" items="${articlemod.category}">
		<jsp:setProperty name="articlemod" property="categoryId" value="${c.id}"/>
		<jsp:setProperty name="articlemod" property="userId" value="${param[c.id]}"/>
		<sr:do bean="${articlemod}" property="saveModerator"/>
	</c:forEach>
</c:when>

</c:choose>





<%@ include file="../../part1.jspf" %>
<div id="main-copy">
<%@ include file="../../part2.jspf" %>

<a href="${PREFIX}/modules.jsp?page=modules">Modules</a> > <a href="${PREFIX}/modules.jsp?page=modules&moduleId=${module.id}"><c:out value="${module.name}"/></a> > <fmt:message key='settings'/>


<%-- ADMIN PREF --%>
<div>
<form method="post">
<input type="hidden" name="action" value="savepref">
<table class="horizontal">
<caption>preferences</caption>
<tr><th><fmt:message key='article.per.page'/></th><td><sr:select name="articlePerPage" options="${articlepref.articlePerPageOption}" defaultOption="${articlepref.articlePerPage}"/></td></tr>
<tr><th><fmt:message key='activate.comments'/></th><td><sr:select name="commentsActivated" multiple="false" options="${articlepref.yesNoOpt}" defaultOption="${articlepref.commentsActivated}"/></td></tr>
</table>
<input type="submit" value="<fmt:message key='save'/>">
</form>
</div>



<%-- ADMIN CAT --%>
<div>
<table class="horizontal">
<form method="post">
<input type="hidden" name="action" value="addcat">
<caption><fmt:message key='category'/></caption>
<tr><th><fmt:message key='new.category'/></th><td><input type="text" name="category"></td><td class="button"><input type="submit" value="<fmt:message key='add'/>"></td></tr>
</form>

<form method="post">
<input type="hidden" name="action" value="removecat">
<tr><th><fmt:message key='delete.category'/></th><td><sr:select name="category" multiple="false" options="${articlecat.categoryOption}"/> <fmt:message key='article.delete.carefull'/></td><td class="button"><input type="submit" value="<fmt:message key='delete'/>"></td></tr>
</form>

<form method="post">
<input type="hidden" name="action" value="renamecat">
<tr><th><fmt:message key='rename.category'/></th><td><sr:select name="category" multiple="false" options="${articlecat.categoryOption}"/> <input type="text" name="renameTo"></td><td class="button"<input type="submit" value="<fmt:message key='rename'/>"></td></tr>
</form>
</table>
</div>


<%-- ADMIN MODERATOR --%>
<div>
<form method="post">
<input type="hidden" name="action" value="moderator">
<table class="vertical">
<caption><fmt:message key='moderator'/></caption>
<tr><th><fmt:message key='category'/></th><th><fmt:message key='moderator'/></th></tr>
<c:forEach var="c" items="${articlemod.category}">
<jsp:setProperty name="articlemod" property="categoryId" value="${c.id}"/>
<tr><td><c:out value="${c.name}"/></td><td><sr:select name="${c.id}" multiple="false" options="${articlemod.userOption}" defaultOption="${articlemod.userIdFromCategoryId}"/></td></tr>
</c:forEach>
</table>

<input type="submit" value="<fmt:message key='save'/>">
</form>
</div>



</div>
<%@ include file="../../part3.jspf" %>