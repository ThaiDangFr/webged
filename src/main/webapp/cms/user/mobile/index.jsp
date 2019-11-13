<c:set target="${usernavig}" property="currentModuleId" value="${null}"/>
<c:set var="URLBASE" value="/cms/user/mobile/modules" scope="page"/>
<c:set var="PREFIX"  value="/openplatform/cms/user/mobile/modules" scope="page"/>


<c:if test="${usernavig.refreshPage}"/>
<sr:xhtml>
    <sr:head title="${usernavig.siteTitle}"/>
    <sr:body title="${usernavig.siteTitle}">
    
    <c:forEach var="b" items="${usernavig.centerBlocks}">
    <c:out value="${b.title}"/> : <jsp:include page="${URLBASE}/${b.jsp}"/>
    </c:forEach>

    <sr:br/>

    <c:forEach var="b" items="${usernavig.leftBlocks}">
    <c:out value="${b.title}"/> : <jsp:include page="${URLBASE}/${b.jsp}"/>
    </c:forEach>

    <sr:br/>

    <c:forEach var="b" items="${usernavig.rightBlocks}">
    <c:out value="${b.title}"/> : <jsp:include page="${URLBASE}/${b.jsp}"/>
    </c:forEach>

    </sr:body>
</sr:xhtml>