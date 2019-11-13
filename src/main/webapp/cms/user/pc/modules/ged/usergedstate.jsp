<%@ page import="java.util.HashMap" %>
<%@ include file="include.jspf" %>

<%-- INIT --%>
<c:set target="${ugstate}" property="userId" value="${usersession.userId}"/>
<c:set var="gedStateList" value="${ugstate.gedStateList}" />


<%-- CONTROL VIEW RIGHTS --%>
<c:if test="${usersession.userId eq null}">
<c:redirect url="../cmsbase/noaccess.jsp"/>
</c:if>


<%-- ACTION --%>
<%-- 
<c:choose>
	<c:when test="${param.action eq 'search'}">
	
			<%
				HashMap map = new HashMap();
				pageContext.setAttribute("map",map);
			%>
			
			<c:forEach items="${gedStateList}" var="gsl">
					<c:set target="${map}" property="${gsl.gsId}" value="${param[gsl.gsId]}"/>
			</c:forEach>
			
		<c:set target="${ugstate}" property="filter" value="${map}"/>
		
	</c:when>
</c:choose>
--%>

<c:set var="gedStateFilesRes" value="${ugstate.searchResult}"/>

<%@ include file="../../part1_print.jspf" %>


<c:if test="${gedStateFilesRes != null}">

<%-- <table class="sortable" id="usergedstate" table-layout="auto"> --%>
<table id="usergedstate">
<%-- <caption><fmt:message key='state.display'/></caption> --%>
	<tr>
		<th/>
			<c:forEach items="${gedStateList}" var="gsl">
				<c:choose>
					<c:when test="${gsl.gsType eq '0'}">
						<th><fmt:message key="${gsl.gsName}"/></th>
					</c:when>
					<c:otherwise>
						<th><c:out value="${gsl.gsName}"/></th>
					</c:otherwise>
				</c:choose>
			</c:forEach>
	</tr>


	
	<c:forEach items="${gedStateFilesRes}" var="gsf">
		<c:set var="idValueMap" value="${gsf.idValueMap}"/>
		
		<tr>
			<td><sr:a href="browser.jsp?action=seeFile&fileId=${gsf.id}"><fmt:message key='view'/></sr:a></td>
			<c:forEach items="${gedStateList}" var="gsl">
			
				<c:choose>
					<c:when test="${gsl.gsName eq 'procstatus' and not empty idValueMap[gsl.gsId]}">
						<td><fmt:message key="${idValueMap[gsl.gsId]}"/></td>
					</c:when>
					<c:when test="${gsl.gsName eq 'date' or gsl.gsName eq 'expirydate'}">
						<fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss.S" var="parsedDateTime" value="${idValueMap[gsl.gsId]}"/>
						<td><fmt:formatDate value="${parsedDateTime}" type="both" dateStyle="short" timeStyle="short"/></td>
					</c:when>
					<c:otherwise>
						<td><c:out value="${idValueMap[gsl.gsId]}"/></td>
					</c:otherwise>
				</c:choose>
				
			</c:forEach>
		</tr>
	</c:forEach>
</table>

<script language="javascript" type="text/javascript">
	var props = {
		rows_counter: true,
		rows_counter_text: "<fmt:message key='displayed.rows'/> : ",
		btn_reset: true,
		btn_reset_text: "<fmt:message key='clear'/>",
		btn_text: " > ",
		loader: true,
		loader_text: "<fmt:message key='loading'/>...",
		alternate_rows: true
	}
	setFilterGrid("usergedstate",props);
</script>
</c:if>



<%--
<form method="post">
<input type="hidden" name="action" value="search">

<table class="horizontal">
	<c:forEach items="${gedStateList}" var="gsl">
		<tr>
				<c:choose>
					<c:when test="${gsl.gsType eq '0'}">
						<th><fmt:message key="${gsl.gsName}"/></th>
					</c:when>
					<c:otherwise>
						<th><c:out value="${gsl.gsName}"/></th>
					</c:otherwise>
				</c:choose>
				
				<td><sr:input type="text" name="${gsl.gsId}" value="${param[gsl.gsId]}"/></td>			
		</tr>
	</c:forEach>
</table>

<input type="submit" value="<fmt:message key='filter'/>">
</form>
--%>

<%@ include file="../../part2_print.jspf" %>