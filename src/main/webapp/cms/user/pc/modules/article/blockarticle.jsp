<%@ include file="import.jspf" %>


<%-- INIT --%>
<jsp:setProperty name="articleapprove" property="userId" value="${usersession.userId}"/>

<div class="sideBarText">
   <ul>
       <li><a href="${PREFIX}/article/articlenew.jsp?init=true"><fmt:message key='article.submit'/></a></li>
       
       <c:if test="${articleapprove.moderator}">
	   <li>
       <a href="${PREFIX}/article/articleapprove.jsp"><fmt:message key='article.moderate'/>
       <c:set var="count" value="${articleapprove.countArticleToModerate}"/>
       <c:if test="${count != 0}"><span class="error">(<c:out value="${count}"/>)</span></c:if>
       </a>
       </li>
       </c:if>
   </ul>
</div>