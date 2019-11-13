<jsp:useBean id="pdf" scope="session" class="openplatform.http.UserPdfBean" />

<c:if test="${not empty param.currentPage}">
	<c:set target="${pdf}" property="currentPage" value="${param.currentPage}" />
</c:if>

<c:if test="${not empty param.fileBaseId}">
	<c:set target="${pdf}" property="currentPage" value="0" />
	<c:set target="${pdf}" property="fileBaseId" value="${param.fileBaseId}" />
</c:if>


<html>
<head>
<style type="text/css">

body 
{
	background-color: #bbb;
}

#pdf-header
{
	text-align:center;
	background-color:#000;
	color:#fff;
	padding-top:10px;
	padding-bottom:10px;
}

#pdf-header a
{
	text-decoration:none;
	color:#fff;
	padding-left: 10px;
	padding-right: 10px;
	margin-left: 20px;
	margin-right: 20px;
	background-color: #aaa;
}

#pdf-header a:hover
{
	background-color: #ccc;
}

#pdf-content
{

	margin-top: 10px;
	text-align:center;
}

#pdf-content img
{
	background-color: #fff;
	border: 2px solid #000;
}

</style>
</head>

<body>


<div id="pdf">

<div id="pdf-header">
	<c:set var="nxt" value="${pdf.nextPage}"/>
	<c:set var="prev" value="${pdf.previousPage}"/>
	<c:set var="total" value="${pdf.totalPage-1}"/>



	<c:if test="${not empty prev}">
		<sr:a href="pdf.jsp?currentPage=${prev}"><fmt:message key="previous"/></sr:a>
	</c:if>
		
	<c:choose>
		<c:when test="${total >= 0}">
					
			<select id="currentPage" name="currentPage" onchange="location.href=document.getElementById('currentPage').options[document.getElementById('currentPage').selectedIndex].value">
				<c:forEach var="i" begin="0" end="${total}">
					<option value="pdf.jsp?currentPage=${i}" <c:if test="${pdf.currentPage==i}">selected</c:if>><c:out value="${i+1}/${total+1}"/></option>
				</c:forEach>
			</select>
			
		</c:when>
		<c:otherwise>
			<fmt:message key="loading"/>...
		</c:otherwise>
	</c:choose>
		
	<c:if test="${not empty nxt}">
		<sr:a href="pdf.jsp?currentPage=${nxt}"><fmt:message key="next"/></sr:a>
	</c:if>

	<div style="position:absolute;right:20px;display:inline;">
		<input type="button" value="<fmt:message key='print'/>" onclick="window.print();" />
		<input type="button" value="<fmt:message key='close'/>" onclick="window.close();" />
	</div>
</div>



<div id="pdf-content">
	<img src="${pdf.imgLink}" />
</div>

</div>




</body>
</html>

