<%@ include file="include.jspf" %>
<%@ include file="importmoduleid.jspf" %>

<%-- INIT --%>
<c:set target="${ubrowser}" property="userId" value="${usersession.userId}"/>
<c:set target="${ufile}" property="userId" value="${usersession.userId}"/>
<c:set target="${udir}" property="userId" value="${usersession.userId}"/>

<fmt:message key='see' 		var='i18n_see'/>
<fmt:message key='modify' 	var='i18n_modify'/>
<fmt:message key='move' 	var='i18n_move'/>
<fmt:message key='lock' 	var='i18n_lock'/>
<fmt:message key='delete' 	var='i18n_delete'/>
<fmt:message key='unlock' 	var='i18n_unlock'/>




<%-- ACTION --%>
<c:choose>

<c:when test="${param.action eq 'browse'}">
    <c:choose>
        <c:when test="${param.directoryId eq '2'}"><c:redirect url="trash.jsp"/></c:when>
	<c:otherwise>
	    <sr:set bean="${ubrowser}" property="directoryId"/>
	    <sr:do bean="${ubrowser}" property="browse" redirectError="../cmsbase/noaccess.jsp"/>
	</c:otherwise>
    </c:choose>
</c:when>

<c:when test="${param.action eq 'seeFile'}">
<c:set target="${ufile}" property="id" value="${param.fileId}"/>
<sr:do bean="${ufile}" property="edit" redirectOk="seefile.jsp"/>
</c:when>

<c:when test="${param.action eq 'newDir'}">
<c:set target="${udir}" property="parentDirId" value="${param.parentDirId}"/>
<sr:do bean="${udir}" property="newDir" redirectOk="diredit.jsp" />
</c:when>

<c:when test="${param.action eq 'editDir'}">
<sr:set bean="${udir}" property="id"/>
<sr:do bean="${udir}" property="editDir" redirectOk="diredit.jsp"/>
</c:when>

<c:when test="${param.action eq 'addFile'}">
<c:set target="${ufile}" property="directoryId" value="${ubrowser.directoryId}"/>
<sr:do bean="${ufile}" property="new" redirectOk="editfile.jsp"/>
</c:when>

<c:when test="${param.action eq 'modifyFile'}">
<c:set target="${ufile}" property="id" value="${param.fileId}"/>
<sr:do bean="${ufile}" property="edit" redirectOk="editfile.jsp"/>
</c:when>

<c:when test="${param.action eq 'moveFile'}">
<c:set target="${ufile}" property="id" value="${param.fileId}"/>
<c:redirect url="movefile.jsp"/>
</c:when>

<c:when test="${param.action eq 'lockFile'}">
<c:set target="${ufile}" property="id" value="${param.fileId}"/>
<sr:do bean="${ufile}" property="lock"/>
</c:when>

<c:when test="${param.action eq 'deleteFile'}">
<c:set target="${ufile}" property="id" value="${param.fileId}"/>
<sr:cmstrace moduleId="${MODULEID}" object="${ufile.pathName}" action="delete" statusMsg="ok" statusUserId="${usersession.userId}" ipAddress="${pageContext.request.remoteAddr}"/>
<sr:do bean="${ufile}" property="moveInTrash"/>
</c:when>

<c:when test="${param.action eq 'unlockFile'}">
<c:set target="${ufile}" property="id" value="${param.fileId}"/>
<sr:do bean="${ufile}" property="unlock"/>
</c:when>

<c:when test="${param.action eq 'deleteDir'}">
    <c:set target="${ubrowser}" property="directoryId" value="${param.id}"/>
    <sr:do bean="${ubrowser}" property="browse"/>
    <c:set target="${udir}" property="id" value="${param.id}"/>
    <sr:cmstrace moduleId="${MODULEID}" object="${udir.path}" action="delete" statusMsg="ok" statusUserId="${usersession.userId}" ipAddress="${pageContext.request.remoteAddr}"/>
    <sr:do bean="${udir}" property="deleteDir" redirectOk="browser.jsp?action=browse&directoryId=${ubrowser.parentDirId}"/>
</c:when>

</c:choose>


<%-- INIT --%>
<c:set var="addFilesRight" value="${ubrowser.addFiles}" scope="page"/>



<%@ include file="../../part1.jspf" %>


<%-- DISPLAY TAB --%>
<c:set var="tab" value="${ubrowser.brotherDirectories}" scope="page"/>
<div id="directory">
    <ul>
    
       <li class="special"><sr:a href="browser.jsp?action=browse&directoryId=1"><img src="images/home.gif"/></sr:a></li>

        <c:if test="${not empty ubrowser.parentDirId}">
	    <li class="special"><sr:a href="browser.jsp?action=browse&directoryId=${ubrowser.parentDirId}"><img src="images/up.gif"/></sr:a></li>
		</c:if>


        <c:forEach items="${tab}" var="s">
	    <c:choose>
	        <c:when test="${s.id eq ubrowser.directoryId}">
		    <li class="active"><span><c:out value="${s.name}"/></span></li>
		</c:when>
		<c:otherwise>
		    <li><sr:a href="browser.jsp?action=browse&directoryId=${s.id}"><c:out value="${s.name}"/></sr:a></li>		    
		</c:otherwise>
	    </c:choose>	
	</c:forEach>
	
	<c:if test="${ubrowser.directoryId eq '1'}">
	    <sr:do bean="${ubrowser}" property="browse"/>
	    <li class="active"><span><c:out value="${ubrowser.name}"/></span></li>
	</c:if>
    </ul>  
</div>


<%-- DISPLAY FILES AND SUB DIRECTORY --%>
<c:set var="files" value="${ubrowser.files}" scope="page"/>
<c:set var="sub" value="${ubrowser.subdirectories}" scope="page"/>


<div id="file">
    <table class="sortable" id="browser">
    <tr class="title"><th/><th/><th><fmt:message key='name'/></th><th><fmt:message key='author'/></th><th>Date</th><th><fmt:message key='workflow.status'/></th><th/></tr>


    <c:forEach items="${files}" var="f">
        <c:set var="wstatus" value="${f.workflowStatus}"/>
    
	<c:choose>
		<c:when test="${wstatus eq 'Validated'}">
			<tr>
			<td>
				<img src="images/file.gif"/>
				<c:if test="${f.expired}"><img src="images/review.gif"/></c:if>
			</td>
		</c:when>
		<c:otherwise>
			<tr class="filebusy">
			<td><img src="images/file-busy.gif"/></td>
		</c:otherwise>
	</c:choose>
	    

	<td>
		<sr:mediaplayer fileBaseId="${f.fileBaseId}"/>
	</td>
	    
	    <td>
	    	<sr:a myclass="filelink" href="browser.jsp?action=seeFile&fileId=${f.id}" title="${i18n_see}">
	    		<c:out value="${f.name}"/>
	    	</sr:a>
	    </td>
	    
	    <td><c:out value="${f.login}"/></td>

	    <fmt:parseDate value="${f.versionDate}" pattern="yyyy-MM-dd HH:mm:ss" var="fdate" parseLocale="en_US"/>
	    <fmt:formatDate type="both" value="${fdate}" dateStyle="short" timeStyle="medium" var="fdate"/>
	    <td><c:out value="${fdate}"/></td>

	    <td><fmt:message key='${wstatus}'/></td>
	    <td class="command">
	    	<%--
			<a href="#" onclick="openMenu('command_file_${f.id}')">E</a>        	        
	        <div id="command_file_${f.id}" style="display:none">
	        --%>
		    <c:if test="${addFilesRight}">
		    	<c:choose>
		    		<c:when test="${f.modify}">
		    			<sr:a href="browser.jsp?action=modifyFile&fileId=${f.id}" title="${i18n_modify}"><img src="images/edit.png"/></sr:a>
		    		</c:when>
		    		<c:otherwise>
		    			<fmt:message key="lock.by"/> <c:out value="${f.lockUser}"/>
		    		</c:otherwise>
		    	</c:choose>
		    	
				<c:if test="${f.move}"><sr:a href="browser.jsp?action=moveFile&fileId=${f.id}" title="${i18n_move}"><img src="images/move.png"/></sr:a></c:if>
				<c:if test="${not f.lock}"><sr:a href="browser.jsp?action=lockFile&fileId=${f.id}" title="${i18n_lock}"><img src="images/lock.png"/></sr:a></c:if>			
				<c:if test="${f.delete}"><sr:a href="browser.jsp?action=deleteFile&fileId=${f.id}" title="${i18n_delete}" onclick="return confirmDelete();"><img src="images/delete.png"/></sr:a></c:if>
				<c:if test="${f.showUnlockButton}"><sr:a href="browser.jsp?action=unlockFile&fileId=${f.id}" title="${i18n_unlock}"><img src="images/unlock.png"/></sr:a></c:if>
		    </c:if>
		    <%--	  
		    </div>
		    --%> 
		</td>
    </tr>
    </c:forEach>


<c:forEach var="ss" items="${sub}">
    <tr>
        <td><img src="images/directory.gif"/></td>
        <td/>
        <td>
	    	<sr:a myclass="dirlink" href="browser.jsp?action=browse&directoryId=${ss.id}"><c:out value="${ss.name}"/></sr:a>
		</td>
		<td/>
		<td/>
		<td/>
		<td class="command">
	    	<c:if test="${ss.writeRight}">
	        	<sr:a href="browser.jsp?action=editDir&id=${ss.id}" title="${i18n_modify}"><img src="images/edit.png"/></sr:a>
		    	<c:if test="${ss.deletable}">
		        	<sr:a href="browser.jsp?action=deleteDir&id=${ss.id}" onclick="return confirmDelete();" title="${i18n_delete}"><img src="images/delete.png"/></sr:a>
		    	</c:if>
	    	</c:if>
		</td>
    </tr>
</c:forEach>


</table>
</div>






<%@ include file="../../part2.jspf" %>