<c:set target="${ubrowser}" property="userId" value="${usersession.userId}"/>

<%-- DISPLAY GENEALOGY --%>
<div id="genealogy">
<ul>
<c:forEach items="${ubrowser.genea}" var="g" varStatus="vs">

    <c:choose>
        <c:when test="${vs.last}">
	    <li id="active">
	        <span><c:out value="${g.name}"/></span>
	</c:when>

	<c:otherwise>
	    <li>
	        <sr:a href="${PREFIX}/ged/browser.jsp?action=browse&directoryId=${g.id}">
	  	    <span><c:out value="${g.name}"/></span>
			</sr:a>
	</c:otherwise>
    </c:choose>
	    
   <c:if test="${g.writeRight}">
       <sr:a href="${PREFIX}/ged/browser.jsp?action=editDir&id=${g.id}">(<fmt:message key='edit'/>)</sr:a>
   </c:if>

   </li>

</c:forEach>
</ul>
</div>