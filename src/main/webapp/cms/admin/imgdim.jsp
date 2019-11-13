<jsp:useBean id="adminimgdim" scope="session" class="dang.applimgt.AdminImgDim" />

<sr:do bean="${adminimgdim}" property="init"/>

<%-- ACTION BEGIN --%>                
<c:choose>
	<c:when test="${param.action=='delete'}">
	        <jsp:setProperty name="adminimgdim" property="imageId" value="${param.imageId}"/>
		<sr:do bean="${adminimgdim}" property="delete"/>
	</c:when>
	<c:when test="${param.action=='update'}" >
		<sr:do bean="${adminimgdim}" property="createImage"/>
		<sr:set bean="${adminimgdim}" property="imageId|width|height|extension"/>
		<sr:do bean="${adminimgdim}" property="update"/>
	</c:when>
	<c:when test="${param.action=='add'}" >
		<sr:do bean="${adminimgdim}" property="createImage"/>
		<sr:set bean="${adminimgdim}" property="width|height|extension"/>
		<sr:do bean="${adminimgdim}" property="add"/>
	</c:when>
</c:choose>
<%-- ACTION END --%> 


<%@ include file="part1.jspf" %>
<%@ include file="part2.jspf" %>
<div id="main-copy">

<table>
<caption><fmt:message key='images'/></caption>
<tr> <th><fmt:message key='width'/></th> <th><fmt:message key='height'/></th> <th><fmt:message key='extension'/></th> <th/></tr>
<c:forEach var="image" items="${adminimgdim.imageItems}">
	<c:if test="${image.imageId!=null && image.imageId!='1'}">
	<form action="imgdim.jsp?page=imgdim" name="form${image.imageId}" method="post">
	<input type="hidden" name="action" value="" />
	<input type="hidden" name="imageId" value="${image.imageId}" />

                <tr>
		<td>
		<input type="text" name="width" value="${image.width}" />
		</td>
		<td>
		<input type="text" name="height" value="${image.height}" />
		</td>
		<td>
		<sr:select name="extension" options="${adminimgdim.extensionList}" defaultOption="${image.extension}"/>
		</td>
		<td>
		<input type="button" value="<fmt:message key='update'/>" onClick="document.form${image.imageId}.action.value='update';document.form${image.imageId}.submit();"/>
		<input type="button" value="<fmt:message key='delete'/>" onClick="if(confirmDelete()){document.form${image.imageId}.action.value='delete';document.form${image.imageId}.submit();}"/>
		</td>
		</tr>

	</form>
	</c:if>
</c:forEach>

</table>




<!--ADD AN IMAGE DIM-->
<form action="imgdim.jsp?page=imgdim" method="post">
<input type="hidden" name="action" value="add" />
<table>
<caption><fmt:message key='new'/></caption>
<tr> <th><fmt:message key='width'/></th> <th><fmt:message key='height'/></th> <th><fmt:message key='extension'/></th> <th/></tr>
<tr>
    <td><input type="text" name="width"></td>
    <td><input type="text" name="height"></td>
    <td><sr:select name="extension" options="${adminimgdim.extensionList}"/></td>
    <td>
	<input type="submit" value="<fmt:message key='add'/>"/>
	<input type="reset" value="<fmt:message key='clear'/>"/>
    </td>
</tr>
</table>
</form>




</div>
<%@ include file="part3.jspf" %>