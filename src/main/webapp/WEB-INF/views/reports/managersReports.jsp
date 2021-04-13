<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title><spring:message code="managers.report"/></title>
    <%@include file="/WEB-INF/views/header/header.jsp" %>
</head>
<body>
<p align="right">
    <a class="button-2" href="/userPage/reports/onManagers">
        <span><spring:message code="showManagers.report"/></span>
    </a></p>
<c:url value="/userPage/reports/onManagers" var="findByNameOrLogin"/>
<form:form action="${findByNameOrLogin}">
    <input type="hidden" name="form" value="findByNameOrLogin"/>
    <label style="color: #450619"><spring:message code="findManagerByNameOrLogin"/></label>
    <input name="name" required>
    <button type="submit" style="border: none; background: none"><img src="/images/search.png" width="30px"
                                                                      height="30px" alt="Find" border="0px">
    </button>
</form:form>

<c:url value="/userPage/reports/onManagers" var="findByRole"/>
<form:form action="${findByRole}">
    <input type="hidden" name="form" value="findByRole"/>
    <label style="color: #450619"><spring:message code="findByRole"/></label>
    <select required name="roleId">
        <option value=""></option>
        <c:forEach items="${roles}" var="role">
        <option value="${role.id}">${role.name}</option>
        </c:forEach>
    </select>
    <button type="submit" style="border: none; background: none"><img src="/images/search.png" width="30px"
                                                                      height="30px" alt="Find" border="0px">
    </button>
</form:form>
<br><br>
<table border="1" cellpadding="10" class="table_blur" align="center">
    <tr>
        <th>№</th>
        <th><spring:message code="managerName"/></th>
        <th><spring:message code="role.managersListPage"/></th>
        <th><spring:message code="ordersInProgress"/></th>
        <th><spring:message code="closedOrders"/></th>
        <th><spring:message code="title.usersPage"/></th>
    </tr>
    <c:forEach items="${managersOrders}" var="manager" varStatus="status">
        <tr>
            <td>${status.index+1}</td>
            <td>${manager.key.name}</td>
            <td><c:forEach var="role" items="${manager.key.roles}">${role.name}</c:forEach></td>
            <td>${manager.value.get(1)} <spring:message code="ordersForAmount"/>  ${manager.value.get(0)}</td>
            <td>${manager.value.get(3)} <spring:message code="ordersForAmount"/> ${manager.value.get(2)} </td>
            <td><c:forEach var="user" items="${manager.key.users}">${user.name} <br></c:forEach></td>
        </tr>
    </c:forEach>
</table>
<div style="color: #a2010e; font-size: large" align="center">${msg}</div>
</body>
</html>
