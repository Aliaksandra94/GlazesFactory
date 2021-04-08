<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title><spring:message code="title.managersListPage"/></title>
    <%@include file="/WEB-INF/views/header/header.jsp" %>
</head>
<body>
<h3 align="center"><spring:message code="title.managersListPage"/> </h3>
<table border="1" cellpadding="10" class="table_blur" align="center">
    <tr>
        <th>№</th>
        <th><spring:message code="userName.usersPage"/></th>
        <th><spring:message code="role.managersListPage"/> </th>
        <th><spring:message code="title.usersPage"/></th>
        <th><spring:message code="actionWithProduct.catalogPage"/></th>
    </tr>
    <c:forEach var="manager" items="${managers}" varStatus="status">
        <tr>
            <td> ${status.index+1}</td>
            <td> ${manager.name} </td>
            <td>
                <c:forEach var="role" items="${manager.roles}" varStatus="status">
                ${role.name}<br>
                </c:forEach></td>
            <td>
                    <c:forEach var="user" items="${manager.users}" varStatus="status">
                        <a href="#"> ${user.name}<br><a>
                    </c:forEach> </td>
            <td>
                <a title="Edit manager" href="/managers/edit/${manager.id}">
                    <img src="images/edit_user.png" width="50px" height="50px"></a>

                <a title="Delete manager" href="/managers/delete/${manager.id}">
                    <img src="images/delete_user.png" width="50px" height="50px"></a>
            </td>
        </tr>
    </c:forEach>
</table>
<sec:authorize access="hasRole('ADMIN')">
    <a title="Add new Manager" href="/managers/add"><img src="images/add_users.png" width="50px" height="50px"><spring:message
            code="managerAction.add"/> </a>
</sec:authorize>
</body>
</html>
