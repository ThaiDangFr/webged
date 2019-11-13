<%@ include file="include.jspf" %>

<c:set target="${ubrowser}" property="directoryId" value="1"/>
<sr:do bean="${ubrowser}" property="browse" redirectOk="browser.jsp"/>
