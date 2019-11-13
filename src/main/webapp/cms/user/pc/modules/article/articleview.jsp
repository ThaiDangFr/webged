<%@ include file="import.jspf" %>
<%@ include file="importmoduleid.jspf" %>


<%-- ACTION --%>
<c:if test="${param.action eq 'savecomment'}">
    <c:set target="${articlecom}" property="title" value="${param.title}"/>
    <c:set target="${articlecom}" property="comment" value="${param.comment}"/>
    <c:set target="${articlecom}" property="authorId" value="${usersession.userId}"/>
    <sr:do bean="${articlecom}" property="save"/>
</c:if>


<%-- LOADING --%>
<c:if test="${not empty param.articleId}">
<jsp:setProperty name="articleview" property="articleId" value="${param.articleId}"/>
</c:if>

<%@ include file="../../part1.jspf" %>

<div class="article">

    <fmt:parseDate value="${articleview.date}" pattern="yyyy-MM-dd HH:mm:ss" var="fdate" parseLocale="en_US"/>
    <fmt:formatDate type="both" value="${fdate}" dateStyle="full" timeStyle="short" var="fdate"/>


    <fmt:message key="submittedByThe" var="submittedByThe">
        <fmt:param value="${articleview.authorLogin}"/>
	<fmt:param value="${fdate}"/>
    </fmt:message>

    <fmt:message key="validatedBy" var="validatedBy">
	<fmt:param value="${articleview.moderatorLogin}"/>
    </fmt:message>

    
    <div class="header">
        <h2><span class="category"><c:out value="${articleview.categoryName}"/> : </span><span class="title"><c:out value="${articleview.title}"/></span></h2>
	
	<p><span class="author"><c:out value="${submittedByThe}"/>. <c:out value="${validatedBy}"/>.</span></p>
    </div>


    <div class="content">
        <sr:img fileBaseId="${articleview.imageId}" title="${articleview.imageLegend}" extension="jpg" param="80x60" />
	<span><c:out value="${articleview.summary}" escapeXml="false" /></span>

        <ul>
            <c:forEach var="l" items="${articleview.joinLink}">
                <li class="link">
                    <sr:a href="${l}"><c:out value="${l}"/></sr:a>
                </li>
            </c:forEach>
            <c:forEach var="f" items="${articleview.joinFile}">
                <li class="file">
                    <sr:a fileBaseId="${f.fileBaseId}">
                    	<c:out value="${f.filename}"/>
                    </sr:a>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>


<c:if test="${not empty articleview.text}">
    <div class="articledetail">
        <span><c:out value="${articleview.text}" escapeXml="false"/></span>
    </div>
</c:if>


<p><a href="${PREFIX}/article/index.jsp"><fmt:message key='back'/></a></p>


<c:if test="${articlelist.commentsActivated}">
<div id="comment">
    <c:choose>
    <c:when test="${usersession.connected}">
        <form method="post" action="articleview.jsp">
	<input type="hidden" name="action" value="savecomment">
	<input type="hidden" name="articleId" value="${articleview.articleId}">

        <table class="horizontal">
	    <caption><fmt:message key='comment.post'/></caption>
	    <tr class="textinput"><th><fmt:message key='title'/></th><td><input type="text" name="title"/></td></tr>
	    <tr><th><fmt:message key='message'/></th><td><textarea name="comment"></textarea></td></tr>
	</table>
	<input type="submit" value="<fmt:message key='send'/>"/>
	</form>
    </c:when>

    <c:otherwise>
        <fmt:message key='comment.login'/>
    </c:otherwise>
    </c:choose>

    <jsp:setProperty name="articlecom" property="articleId" value="${param.articleId}"/>
    <c:forEach var="c" items="${articlecom.comments}">
	<jsp:setProperty name="articlecom" property="authorId" value="${c.authorId}"/>

	<div id="articlecom">

            <fmt:parseDate value="${c.date}" pattern="yyyy-MM-dd HH:mm:ss" var="fdate" parseLocale="en_US"/>
	    <fmt:formatDate type="both" value="${fdate}" dateStyle="full" timeStyle="short" var="fdate"/>

	    <fmt:message key="submittedByThe" var="submittedByThe">
	        <fmt:param value="${articlecom.authorLogin}"/>
		<fmt:param value="${fdate}"/>
	    </fmt:message>

	    <h4><c:out value="${c.title}"/></h4>
	    <p><span class="submitby"><c:out value="${submittedByThe}"/><span></p>
	    <p><span><c:out value="${c.comment}"/></span></p>
        </div>

    </c:forEach>
</div>
</c:if>

<%@ include file="../../part2.jspf" %>