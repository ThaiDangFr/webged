<jsp:useBean id="module" scope="session" class="dang.cms.AdminModule" />
<jsp:useBean id="adminFormula" scope="session" class="dang.websnmp.AdminFormulaBean"/>

<c:set target="${adminFormula}" property="mibName" value="${param.mibName}"/>
<c:set target="${adminFormula}" property="formula" value="${param.formula}"/>
<c:set target="${adminFormula}" property="name" value="${param.name}"/>
<c:set target="${adminFormula}" property="description" value="${param.description}"/>
<c:set target="${adminFormula}" property="legendMibName" value="${param.legendMibName}"/>
<c:set target="${adminFormula}" property="legendOidName" value="${param.legendOidName}"/>

<c:if test="${param.init=='true'}">
<c:set target="${adminFormula}" property="formulaId" value="${param.formulaId}"/>
</c:if>

<c:if test="${param.action=='save'}">
    <sr:do bean="${adminFormula}" property="save" redirectOk="formulagen.jsp"/>
</c:if>

<%@ include file="../../part1.jspf" %>
<div id="main-copy">
<%@ include file="../../part2.jspf" %>

<a href="${PREFIX}/modules.jsp?page=modules">Modules</a> > <a href="${PREFIX}/modules.jsp?page=modules&moduleId=${module.id}"><c:out value="${module.name}"/></a> > <a href="${PREFIX}/modules/websnmp/index.jsp?page=modules"><fmt:message key='settings'/></a> > <fmt:message key='formula'/>

<br/>
<br/>

<form name="formulaire" action="formulaedit.jsp" method="POST">
<input type="hidden" name="action" value="nosave">

<c:if test="${param.formulaId!=null}">
<input type="hidden" name="formulaId" value="${param.formulaId}">
</c:if>




    <table id="formula" class="horizontal">
    <caption><fmt:message key='formula'/></caption>
    <tr>
    <th><fmt:message key='mib.name'/></th>
    <td><input:select name="mibName" options="${adminFormula.mibNameOptions}" default="${adminFormula.mibName}" attributesText='onChange="formulaire.submit()"' /></td>
    </tr>    

    <tr>
    <th><fmt:message key='oid.name'/></th>
    <td><input:select name="oidName" optionLabels="${adminFormula.oidNameList}" attributesText='size="13" id="oidName" ondblclick="Data.value += mibName.options[mibName.selectedIndex].text + \'_\' + oidName.options[oidName.selectedIndex].text"'/></td>
    </tr>
       
    <tr>
    <th><fmt:message key='operators'/></th>
    <td><input:select name="operator" optionLabels="${adminFormula.operators}" attributesText='size="13" id="operator" ondblclick="Data.value += operator.options[operator.selectedIndex].text"'/></td>   
    </tr>
    
    <tr>
    <th><fmt:message key='formula'/></th>
    <td><sr:input type="text" name="formula" value="${adminFormula.formula}" attributesText="id=Data style='width:45em;'"/></td>
    </tr>

    </table>



    <table class="horizontal">
    <caption><fmt:message key='properties'/></caption>
    <tr>
    <th valign="top"><fmt:message key='name'/></th>
    <td>
    <input:text name="name" default="${adminFormula.name}" attributesText='size="45" maxlength="45"'/>
    </td>
    </tr>

    <tr>
    <th valign="top">Description</th>
    <td><textarea cols="60" name="description" rows="5"><c:out value="${adminFormula.description}"/></textarea>
    </td>
    </tr>

    <tr>
    <th valign="top"><fmt:message key='legend'/></th>
    <td>
    <input:select name="legendMibName" options="${adminFormula.mibNameOptions}" default="${adminFormula.legendMibName}" attributesText='onChange="document.formulaire.submit();"'/>
    <input:select name="legendOidName" options="${adminFormula.legendOidNameOptions}" default="${adminFormula.legendOidName}"/>
    </td>
    </tr>

    </table>

<input type="button" value="<fmt:message key='save'/>" onClick="document.formulaire.action.value='save';document.formulaire.submit();" class="button">
<input type="button" value="<fmt:message key='cancel'/>" onClick="window.location='formulagen.jsp'" class="button">

</form>


</div>
<%@ include file="../../part3.jspf" %>