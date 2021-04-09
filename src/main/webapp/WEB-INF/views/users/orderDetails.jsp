<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title><spring:message code="title.orderDetails"/></title>
    <%@include file="/WEB-INF/views/header/header.jsp" %>
</head>
<body>
<h3 align="center"><spring:message code="title.orderDetails"/></h3>
<sec:authorize access="isAuthenticated() && !hasRole('USER')">
    <h3><spring:message code="userInfo.orderDetails"/></h3>
    <ul>
        <li><spring:message code="userName.usersPage"/>: ${order.user.name}</li>
        <li><spring:message code="login.loginPage"/>: ${order.user.login}</li>
        <li><spring:message code="userDiscount.usersPage"/>: ${order.user.discount}</li>
    </ul>
</sec:authorize>
<h3><spring:message code="orderSum.orderDetails"/>:</h3>
<ul>
    <li><spring:message code="totalCost.basketPage"/>:
        ${order.amount}
    </li>
    <li><spring:message code="status.ordersPage"/>:
        ${order.orderStage.name}
    </li>
    <c:if test="${order.orderStage.orderStageID != 1}">
        <sec:authorize access="isAuthenticated() && !hasRole('USER')">
            <li><spring:message code="changeStatus.orderPage"/>:
                <c:url value="/userPage/orderHistory/orderDetails" var="action"/>
                <form:form action="${action}" modelAttribute="order">
                    <input hidden value="${order.id}" name="orderId">
                    <select required name="orderStagedId" id="orderStagedId" onchange="myFunction()">
                        <option value="">select order stage</option>
                        <c:forEach var="orderStage" items="${orderStages}">
                            <option value="${orderStage.orderStageID}">${orderStage.name}</option>
                        </c:forEach>
                    </select>
                    <script>
                        document.getElementById("orderStagedId").onchange = function () {
                            localStorage.setItem('orderStagedId', document.getElementById("orderStagedId").value);
                        }

                        if (localStorage.getItem('orderStagedId')) {
                            document.getElementById("orderStagedId").options[localStorage.getItem('orderStagedId')].selected = true;
                        }
                    </script>
                    <spring:message code="updateOrderStatus.orderDetailsPage" var="submit"/>
                    <input type="submit" value="${submit}">
                </form:form>
            </li>
        </sec:authorize>
    </c:if>
</ul>
<br><br>
<table border="1" cellpadding="10" class="table_blur" align="center">
    <tr>
        <th>№</th>
        <th><spring:message code="productName.catalogPage"/></th>
        <th><spring:message code="productType.catalogPage"/></th>
        <th><spring:message code="productOderedQuantity.catalogPage"/></th>
        <sec:authorize access="isAuthenticated() && !hasRole('USER')">
            <th><spring:message code="productQuantity.catalogPage"/></th>
        </sec:authorize>
        <th><spring:message code="productPrice.catalogPage"/></th>
        <th><spring:message code="productCost.basketPage"/></th>
    </tr>
    <tr>
        <c:forEach var="item" items="${order.orderItems}" varStatus="status">
        <td>${status.index+1}</td>
            <spring:message code="changeAvailableProductQuantity.catalogPage" var="change"/>
            <c:if test="${item.quantity>=item.product.quantity}">
        <td><a title="${change}" href="/catalog/changeProductQuantity/${item.product.id}"> ${item.product.name} </a></td>
        </c:if>
        <c:if test="${item.quantity<item.product.quantity}">
        <td>${item.product.name}</td>
        </c:if>
        <td>${item.product.glazesType.name}</td>
        <td>${item.quantity}</td>
        <sec:authorize access="isAuthenticated() && !hasRole('USER')">
            <td>${item.product.quantity}</td>
        </sec:authorize>
        <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${item.price}"/></td>
        <c:set value="${item.quantity*item.price}" var="total_amount"/>
        <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${total_amount}"/></td>
    </tr>
    </c:forEach>
</table>
<br><br>
<sec:authorize access="hasAnyRole('PRODUCER', 'PURCHASER')">
<spring:message code="toFulfill.Order.message"/>
<c:if test="${enoughRaw == true}">
    <spring:message code="enoughToFulfill.Order.message"/>
</c:if>
<c:if test="${enoughRaw == false && order.orderStage.orderStageID != 2}">
<sec:authorize access="hasAnyRole('PRODUCER')">
<spring:message code="notEnoughToFulfill.Order.message"/>
</sec:authorize>
<sec:authorize access="hasAnyRole('PURCHASER')">
    <spring:message code="notEnoughRawToFulfill.Order.message"/>
</sec:authorize>
<br>
<table border="1" cellpadding="10" class="table_blur" align="left">
    <tr>
        <th>№</th>
        <th><spring:message code="rawName.addRaw"/></th>
        <th><spring:message code="rawNotEnough"/></th>
    </tr>
        <c:forEach var="raw" items="${notEnoughRaw}" varStatus="status">
    <tr>
        <td>${status.index+1}</td>
        <td><a href="/userPage/actionWithRawMaterial/changeRawMaterialQuantity/${raw.key.rawMaterialID}/${order.id}"> ${raw.key.name} </a></td>
        <td>${raw.value}</td>
    </tr>
        </c:forEach>
        </c:if>
        </sec:authorize>
</body>
</html>