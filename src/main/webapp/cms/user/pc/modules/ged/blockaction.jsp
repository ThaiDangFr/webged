<%@ include file="include.jspf" %>

<%-- INIT --%>
<c:set var="addFilesRight" value="${ubrowser.addFiles}" scope="page"/>
<c:set var="addDirsRight" value="${ubrowser.addDirs}" scope="page"/>
<c:set var="addTabsRight" value="${ubrowser.addTabs}" scope="page"/>

<fmt:message key='tab.add' var='i18n_tab.add'/>
<fmt:message key='directory.add' var='i18n_directory.add'/>
<fmt:message key='file.add' var='i18n_file.add'/>


<%-- ADD FILE AND DIR --%>
<div class="sideBarText">
    <ul id="filediradd">
        <c:if test="${addTabsRight}">
            <li class="tab"><sr:a href="${PREFIX}/ged/browser.jsp?action=newDir&parentDirId=${ubrowser.parentDirId}" title="${i18n_tab.add}"><fmt:message key='tab.add'/></sr:a></li>
	</c:if>

	<c:if test="${addDirsRight}">
	    <li class="directory"><sr:a href="${PREFIX}/ged/browser.jsp?action=newDir&parentDirId=${ubrowser.directoryId}" title="${i18n_directory.add}"><fmt:message key='directory.add'/></sr:a></li>
	</c:if>
	
	<c:if test="${ubrowser.directoryId ne '1'}">
	<c:if test="${addFilesRight}">
	    <li class="file"><sr:a href="${PREFIX}/ged/browser.jsp?action=addFile" title="${i18n_file.add}"><fmt:message key='file.add'/></sr:a></li>
	</c:if>
	</c:if>

	<li class="trash"><a href="${PREFIX}/ged/trash.jsp"><fmt:message key='trash.view'/></a></li>
    </ul>
</div>