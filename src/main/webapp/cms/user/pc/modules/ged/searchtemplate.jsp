<%@ include file="include.jspf" %>
<%@ include file="importmoduleid.jspf" %>


<%-- INIT --%>
<c:set target="${tsearch}" property="userId" value="${usersession.userId}"/>

<%-- ACTION --%>
<c:choose>
    <c:when test="${param.action eq 'search'}">
        <sr:set bean="${tsearch}" property="templateItemId1|template1|templateItemId2|template2|templateItemId3|template3|templateItemId4|template4|templateItemId5|template5|filename|authorId|reference|directoryId|text"/>
	<sr:do bean="${tsearch}" property="search"/>
    </c:when>

    <c:when test="${param.action eq 'init'}">
        <sr:do bean="${tsearch}" property="init" redirectOk="searchtemplate.jsp"/>
    </c:when>
</c:choose>

<%@ include file="../../part1.jspf" %>


<form method="post">
<input type="hidden" name="action" value="search">
<table class="horizontal">
<caption><fmt:message key='file.properties'/></caption>
    <tr>
        <th><fmt:message key='filename'/></th>
		<td><sr:input type="text" name="filename" value="${tsearch.filename}"/></td>
    </tr>

    <tr class="medium">
        <th><fmt:message key='directory.name'/></th>
		<td><sr:select attributesText='style="width:400px;" class="sortable"' name="directoryId" options="${tsearch.directoryOptions}" defaultOption="${tsearch.directoryId}"/></td>
    </tr>

    <tr>
        <th><fmt:message key='author'/></th>
		<td><sr:select attributesText='class="sortable"' name="authorId" options="${tsearch.authorOptions}" defaultOption="${tsearch.authorId}"/>
    </tr>

    <tr>
        <th><fmt:message key='reference'/></th>
		<td><sr:input type="text" name="reference" value="${tsearch.reference}"/>
    </tr>


    <c:set var="templateItemOption" value="${tsearch.templateItemOption}"/>
    <c:if test="${not empty templateItemOption}">
        <tr>
            <th><fmt:message key='ged.template'/>1</th>
	    	<td>
	    		<sr:select attributesText='class="sortable"' name="templateItemId1" multiple="false" options="${templateItemOption}" defaultOption="${tsearch.templateItemId1}"/>
	    		<sr:input type="text" name="template1" value="${tsearch.template1}"/>
	    	</td>
        </tr>
        <tr>
            <th><fmt:message key='ged.template'/>2</th>
	    	<td>
	    		<sr:select attributesText='class="sortable"' name="templateItemId2" multiple="false" options="${templateItemOption}" defaultOption="${tsearch.templateItemId2}"/>
	    		<sr:input type="text" name="template2" value="${tsearch.template2}"/>
	    	</td>
        </tr>
        <tr>
            <th><fmt:message key='ged.template'/>3</th>
	    	<td>
	    		<sr:select attributesText='class="sortable"' name="templateItemId3" multiple="false" options="${templateItemOption}" defaultOption="${tsearch.templateItemId3}"/>
	    		<sr:input type="text" name="template3" value="${tsearch.template3}"/>
	    	</td>
        </tr>
        <tr>
            <th><fmt:message key='ged.template'/>4</th>
	    	<td>
	    		<sr:select attributesText='class="sortable"' name="templateItemId4" multiple="false" options="${templateItemOption}" defaultOption="${tsearch.templateItemId4}"/>
	    		<sr:input type="text" name="template4" value="${tsearch.template4}"/>
	    	</td>
        </tr>
        <tr>
            <th><fmt:message key='ged.template'/>5</th>
	    	<td>
	    		<sr:select attributesText='class="sortable"' name="templateItemId5" multiple="false" options="${templateItemOption}" defaultOption="${tsearch.templateItemId5}"/>
	    		<sr:input type="text" name="template5" value="${tsearch.template5}"/>
	    	</td>
        </tr>                               
    </c:if>

    <tr>
        <th><fmt:message key='ged.fulltext'/></th>
		<td><sr:input type="text" name="text" value="${tsearch.text}"/></td>
    </tr>
</table>

<input type="submit" value="<fmt:message key='search'/>">
</form>



<br/><br/>




<c:set var="resultVersion" value="${tsearch.resultVersion}"/>
<c:if test="${not empty resultVersion}">
<table class="sortable" id="searchresults">
    <tr><th><fmt:message key='filename'/></th><th><fmt:message key='size'/></th><th><fmt:message key='author'/></th><th>Date</th><th><fmt:message key='reference'/></th></tr>
    <c:forEach items="${resultVersion}" var="version">
        <tr>
	    <td><sr:a href="browser.jsp?action=seeFile&fileId=${version.fileId}"><c:out value="${version.pathName}"/></sr:a></td>
	    <td><sr:out value="${version.size}" unit="o"/></td>
	    <td><c:out value="${version.login}"/></td>

	    <fmt:parseDate value="${version.versionDate}" pattern="yyyy-MM-dd HH:mm:ss" var="fdate" parseLocale="en_US"/>
	    <fmt:formatDate type="both" value="${fdate}" dateStyle="short" timeStyle="medium" var="fdate"/>
	    <td><c:out value="${fdate}"/></td>

	    <td><c:out value="${version.reference}"/></td>   
	</tr>
    </c:forEach>
</table>
</c:if>

<c:if test="${empty resultFile and empty resultVersion and param.action eq 'search'}">
    <span class="info"><fmt:message key='no.result'/></span>
</c:if>


<%@ include file="../../part2.jspf" %>