<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title><spring:message code="title.Orders"/></title>
    <%@include file="/WEB-INF/views/header/header.jsp" %>
</head>
<body>
<h2 align="center"><spring:message code="title.Orders"/></h2>
<table align="center" border="1" cellpadding="10" class="table_blur">
    <tr>
        <th>№</th>
        <th><spring:message code="number.ordersPage"/></th>
        <th><spring:message code="status.ordersPage"/></th>
        <th><spring:message code="userName.usersPage"/></th>
        <th><spring:message code="managerName"/></th>
        <th><spring:message code="orderSum.orderDetails"/></th>
        <sec:authorize access="!hasRole('PURCHASER') && !hasRole('PRODUCER')">
        <th><spring:message code="actionWithProduct.catalogPage"/></th>
        </sec:authorize>
    </tr>
    <c:forEach var="order" items="${orders}" varStatus="status">
        <tr>
            <td> ${status.index+1}</td>
            <td> ${order.orderNumber} </td>
            <td>${order.orderStage.name}</td>
            <td>${order.user.name}</td>
            <td>${order.user.manager.name}</td>
            <td>${order.amount}</td>
            <sec:authorize access="!hasRole('PRODUCER') && !hasRole('ADMIN') && !hasRole('PURCHASER')">
            <c:set var="managerID" value="${manager.id}"/>
            <c:set var="usersManagerId" value="${order.user.manager.id}"/>
            <c:if test="${managerID == usersManagerId}">
                <td><a href="/userPage/orderHistory/orderDetails/${order.id}"><img src="/images/orderHistory.png" width="30" height="30"> <spring:message code="title.orderDetails"/></a></td>
            </c:if>
            <c:if test="${managerID != usersManagerId}">
                <td><spring:message code="message.Orders"/></td>
            </c:if>
            </sec:authorize>
            <sec:authorize access="hasRole('ADMIN')">
                    <td><a href="/userPage/orderHistory/orderDetails/${order.id}"><img src="/images/orderHistory.png" width="30" height="30"> <spring:message code="title.orderDetails"/></a></td>
            </sec:authorize>
        </tr>
    </c:forEach>
</table>
</body>
</html>