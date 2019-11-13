<jsp:useBean id="admintrace" scope="session" class="dang.cms.AdminTrace"/>

<%@ include file="part1.jspf" %>
<%@ include file="part2.jspf" %>


<c:choose>
<c:when test="${param.action eq 'show'}">
<jsp:setProperty name="admintrace" property="beginDay" value="${param.beginDay}"/>
<jsp:setProperty name="admintrace" property="beginMonth" value="${param.beginMonth}"/>
<jsp:setProperty name="admintrace" property="beginYear" value="${param.beginYear}"/>
<jsp:setProperty name="admintrace" property="endDay" value="${param.endDay}"/>
<jsp:setProperty name="admintrace" property="endMonth" value="${param.endMonth}"/>
<jsp:setProperty name="admintrace" property="endYear" value="${param.endYear}"/>
</c:when>
<c:otherwise><c:if test="${admintrace.doInit}"/></c:otherwise>
</c:choose>


<div id="main-copy">

<form method="post">
<input type="hidden" name="action" value="show">
<table>
<tr><td>
<sr:select name="beginDay" multiple="false" options="${admintrace.dayOption}" defaultOption="${admintrace.beginDay}"/>
<sr:select name="beginMonth" multiple="false" options="${admintrace.monthOption}" defaultOption="${admintrace.beginMonth}"/>
<sr:select name="beginYear" multiple="false" options="${admintrace.yearOption}" defaultOption="${admintrace.beginYear}"/>
</td></tr>
<tr><td>
<sr:select name="endDay" multiple="false" options="${admintrace.dayOption}" defaultOption="${admintrace.endDay}"/>
<sr:select name="endMonth" multiple="false" options="${admintrace.monthOption}" defaultOption="${admintrace.endMonth}"/>
<sr:select name="endYear" multiple="false" options="${admintrace.yearOption}" defaultOption="${admintrace.endYear}"/>
</td></tr>
</table>
<input type="submit" value="<fmt:message key='show'/>">
</form>

<c:if test="${param.action eq 'show'}">
<table id="trace">

<tr>
    <th>Date</th>
    <th>Module</th>
    <th><fmt:message key='object'/></th>
    <th>Action</th>
    <th COLSPAN=3><fmt:message key='status'/></th>
    <th>IP</th>
</tr>

<tr>
    <th/><th/><th/><th/>
    <th><fmt:message key='message'/></th>
    <th><fmt:message key='authent.login'/></th>
    <th><fmt:message key='group'/></th>
    <th/>
</tr>

<c:forEach items="${admintrace.trace}" var="t">
<fmt:parseDate value="${t.date}" pattern="yyyy-MM-dd HH:mm:ss" var="fdate" parseLocale="en_US"/>
<fmt:formatDate type="both" value="${fdate}" dateStyle="full" timeStyle="short" var="fdate"/>
<tr>
<td><c:out value="${fdate}"/></td>
<td><c:out value="${t.moduleName}"/></td>
<td><c:out value="${t.object}"/></td>

<c:choose>
    <c:when test="${not empty t.action}">
        <td><fmt:message key='${t.action}'/></td>
    </c:when>
    <c:otherwise>
        <td/>
    </c:otherwise>
</c:choose>


<c:choose>
    <c:when test="${not empty t.statusMsg}">
        <td><fmt:message key='${t.statusMsg}'/></td>
    </c:when>
    <c:otherwise>
        <td/>
    </c:otherwise>
</c:choose>


<td><c:out value="${t.statusUserName}"/></td>
<td><c:out value="${t.statusGroupName}"/></td>
<td><c:out value="${t.ipAddress}"/></td>
</tr>
</c:forEach>
</table>
</c:if>

</div>
<%@ include file="part3.jspf" %>