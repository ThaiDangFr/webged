<%@ include file="import.jspf" %>
<%@ include file="importmoduleid.jspf" %>


<%-- LOADING --%>
<c:if test="${param.init eq 'true'}"><c:if test="${articlelist.doInit}"/></c:if>
<c:if test="${not empty param.categoryId}"><jsp:setProperty name="articlelist" property="categoryId" value="${param.categoryId}"/></c:if>
<c:if test="${not empty param.searchText}"><jsp:setProperty name="articlelist" property="searchText" value="${param.searchText}"/></c:if>
<c:if test="${not empty param.page}"><jsp:setProperty name="articlelist" property="page" value="${param.page}"/></c:if>


<%@ include file="../../part1.jspf" %>



<c:forEach var="art" items="${articlelist.article}">
    <jsp:setProperty name="articleview" property="articleId" value="${art.id}"/>

    <div class="article">
    <fmt:parseDate value="${art.date}" pattern="yyyy-MM-dd HH:mm:ss" var="fdate" parseLocale="en_US"/>
    <fmt:formatDate type="both" value="${fdate}" dateStyle="full" timeStyle="short" var="fdate"/>


    <fmt:message key="submittedByThe" var="submittedByThe">
        <fmt:param value="${articleview.authorLogin}"/>
	<fmt:param value="${fdate}"/>
    </fmt:message>

    <fmt:message key="validatedBy" var="validatedBy">
	<fmt:param value="${articleview.moderatorLogin}"/>
    </fmt:message>

    
    <div class="header">
        <h2><span class="category"><c:out value="${articleview.categoryName}"/> : </span><span class="title"><c:out value="${art.title}"/></span></h2>
	
	<p><span class="author"><c:out value="${submittedByThe}"/>. <c:out value="${validatedBy}"/>.</span></p>
    </div>


    <div class="content">
    	<sr:img fileBaseId="${articleview.imageId}" extension="jpg" param="80x60"/>
       
	<%-- <span><pre wrap><c:out value="${art.summary}"/></pre></span> --%>	
	<span><c:out value="${art.summary}" escapeXml="false" /></span>

        <ul>
            <c:forEach var="l" items="${articleview.joinLink}">
                <li class="link">
                    <a href="${l}" target="_blank"><c:out value="${l}"/></a>
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
	
	<p>
	    <sr:a href="${PREFIX}/article/articleview.jsp?articleId=${art.id}"><fmt:message key='article.read'/></sr:a>
	    <c:if test="${articlelist.commentsActivated}">(<c:out value="${articleview.numberOfComments}"/>&nbsp;<fmt:message key='comments'/>)</c:if>
	</p>
    </div>

    </div>
</c:forEach>





<c:set var="len" value="${articlelist.numberOfPage}"/>
<c:if test="${len ne 1}">
<c:forEach var="i" begin="1" end="${len}">
<c:choose>
<c:when test="${articlelist.page eq i}">${i}</c:when>
<c:otherwise><sr:a href="${PREFIX}/article/articlelist.jsp?page=${i}">${i}</sr:a></c:otherwise>
</c:choose>
</c:forEach>
</c:if>

<%@ include file="../../part2.jspf" %>