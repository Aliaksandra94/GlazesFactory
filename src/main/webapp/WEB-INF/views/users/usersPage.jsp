<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title><spring:message code="title.usersPage"/></title>
    <%@include file="/WEB-INF/views/header/header.jsp"%>
</head>
<body>
<h2 align="center"><spring:message code="title.usersPage"/></h2>
<table border="1" cellpadding="10" class="table_blur" align="center">
    <tr>
        <th>№</th>
        <th><spring:message code="userName.usersPage"/></th>
        <th><spring:message code="userSolvency.usersPage"/></th>
        <th><spring:message code="userDiscount.usersPage"/></th>
        <th><spring:message code="userOrderHistory"/></th>
        <sec:authorize access="hasRole('ADMIN')">
            <th><spring:message code="userManager.usersPage"/></th>
            <th><spring:message code="userAction"/></th>
        </sec:authorize>
    </tr>
    <c:forEach var="user" items="${users}" varStatus="status">
        <tr>
            <td> ${status.index+1}</td>
            <td> ${user.name} </td>
            <td>${user.solvency.name}</td>
            <td>${user.discount}</td>
            <td><a title="Orders history" href="/users/${user.id}/orders">
                <img src="images/orderHistory.png" width="30px" height="30px" class="round">
                <spring:message code="orderHistory.mainPage"/>
            </a></td>
            <sec:authorize access="hasRole('ADMIN')">
                <td>${user.manager.name}</td>
                <td>
                    <a title="Edit user" href="/users/edit/${user.id}">
                        <img src="images/edit_user.png" width="50px" height="50px"></a>
                    <c:if test="${user.orders.size()<=0}">
                    <a title="Delete user" href="/users/delete/${user.id}">
                        <img src="images/delete_user.png" width="50px" height="50px"></a>
                    </c:if>
                </td>
            </sec:authorize>
        </tr>
    </c:forEach>
</table>
<sec:authorize access="hasRole('ADMIN')">
    <a title="Add new User" href="/users/add"><img src="images/add_users.png" width="50px" height="50px"><spring:message
            code="userAction.add"/> </a>
</sec:authorize>
</body>
</html>
