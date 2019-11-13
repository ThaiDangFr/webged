<%@ include file="import.jspf" %>


<div class="sideBarText">
    <ul>
        <form method="post" action="${PREFIX}/article/articlelist.jsp">
	    <input type="hidden" name="init" value="true">
            <li><sr:select name="categoryId" multiple="false" options="${articlecat.categoryOption}"/><input type="submit" value="<fmt:message key='filter'/>"></li>
	</form>


	<form method="post" action="${PREFIX}/article/articlelist.jsp">
	    <input type="hidden" name="init" value="true">
	    <li><input type="submit" value="<fmt:message key='showall'/>"></li>
	</form>


	<form method="post" action="${PREFIX}/article/articlelist.jsp">
	    <li><input type="text" name="searchText" size="5"><input type="submit" value="<fmt:message key='search'/>"></li>
	</form>
    </ul>
</div>


