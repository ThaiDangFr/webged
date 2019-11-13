<%@ include file="import.jspf" %>
<%@ include file="importmoduleid.jspf" %>


<c:if test="${param.action eq 'save'}">
    <sr:set bean="${useredit}" property="password|firstName|lastName|mail|officePhone|mobilePhone|icq|jabber|msn"/>

    <c:set target="${useredit}" property="fwMsgToMail" value="${false}"/>
    <c:set target="${useredit}" property="cpMsgToSMS" value="${false}"/>

    <c:if test="${not empty param.fwMsgToMail}">
        <c:set target="${useredit}" property="fwMsgToMail" value="${true}"/>
    </c:if>

    <c:if test="${not empty param.cpMsgToSMS}">
        <c:set target="${useredit}" property="cpMsgToSMS" value="${true}"/>
    </c:if>

    <sr:do bean="${useredit}" property="save"/>
</c:if>


<%-- Loading --%>
<c:set target="${useredit}" property="userId" value="${usersession.userId}"/>


<%@ include file="../../part1.jspf" %>
<h1 style="border-top: none; padding-top: 0;"><fmt:message key='edit'/>&nbsp;<c:out value="${useredit.login}"/></h1>
<form method="post">
<input type="hidden" name="action" value="save">
<table class="horizontal">
<caption>Informations</caption>
<tr class="textinput"> <th><fmt:message key='authent.password'/></th><td><sr:input type="text" name="password" size="25" /> </td></tr>
<tr class="textinput"> <th><fmt:message key='firstname'/></th><td><sr:input type="text" name="firstName" size="25" value="${useredit.firstName}" /> </td></tr>
<tr class="textinput"> <th><fmt:message key='lastname'/></th><td><sr:input type="text" name="lastName" size="25" value="${useredit.lastName}" /> </td></tr>
<tr class="textinput"> <th>Email</th><td><sr:input type="text" name="mail" size="25" value="${useredit.mail}" /> </td></tr>
<tr class="textinput"> <th><fmt:message key='phone.office'/></th><td><sr:input type="text" name="officePhone" size="25" value="${useredit.officePhone}" /> ex. France 33123456789</td></tr>
<tr class="textinput"> <th><fmt:message key='phone.mobile'/></th><td><sr:input type="text" name="mobilePhone" size="25" value="${useredit.mobilePhone}" /> ex. France 33623456789</td></tr>
<tr class="textinput"> <th>Icq number</th><td><sr:input type="text" name="icq" size="25" value="${useredit.icq}" /> </td></tr>
<tr class="textinput"> <th>Jabber address</th><td><sr:input type="text" name="jabber" size="25" value="${useredit.jabber}" /> </td></tr>
<tr class="textinput"> <th>Msn address</th><td><sr:input type="text" name="msn" size="25" value="${useredit.msn}" /> </td></tr>
</table>

<table class="horizontal">
    <caption>Options</caption>
    <tr>
        <td><sr:input type="checkbox" name="fwMsgToMail" checked="${useredit.fwMsgToMail}"/><fmt:message key='forward.msg.mail'/></td>
    </tr>

    <tr>
        <td><sr:input type="checkbox" name="cpMsgToSMS" checked="${useredit.cpMsgToSMS}"/><fmt:message key='copy.important.msg.sms'/></td>
    </tr>
</table>


<input type="submit" value="<fmt:message key='save'/>">
<input type="reset" value="<fmt:message key='clear'/>">
<input type="button" value="<fmt:message key='cancel'/>" onClick="self.location.href='index.jsp'">

</form>
<%@ include file="../../part2.jspf" %>