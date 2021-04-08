<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<style>
    .PREPAYMENT {
        background: #077900;
        color: #eee1af;
    }

    .POSTPAYMENT {
        background: #e3cc01;
        color: #103400;
    }

    .DEBTOR {
        background: #fa0404;
        color: #ffffff;
    }

    .AVAILABLE {
        background: #1dfa04;
        color: #ffffff;
    }

    .IN_PRODUCTION {
        background: #f8af2c;
        color: #ffffff;
    }

    .AWAITING_CONFIRMATION_FROM_PRODUCTION {
        background: #865347;
        color: #ffffff;
    }

    .AWAITING_CONFIRMATION {
        background: #484848;
        color: #ffffff;
    }

    .AVAILABLE_FOR_SELL {
        background: #6fcf64;
        color: #ffffff;
    }

    .AWAITING_CONFIRMATION_FROM_PURCHASES {
        background: #7c3e3e;
        color: #ffffff;
    }

    .AVAILABLE_FOR_PRODUCTION {
        background: #f50d3a;
        color: #ffffff;
    }
</style>
<head>
    <title><spring:message code="title.ordersPage"/></title>
    <%@include file="/WEB-INF/views/header/header.jsp" %>
</head>
<body>
<sec:authorize access="hasAnyRole('ADMIN', 'SELLER', 'USER')">
    <a class="button-2" href="/userPage/orderHistory/activeTasks">
        <span><spring:message code="hide.orderHistory"/></span>
    </a>
    <a class="button-2" href="/userPage/orderHistory">
        <span><spring:message code="show.orderHistory"/></span>
    </a>
    <p style="color: #450619"><spring:message code="showByStage"/></p>
    <c:forEach var="stage" items="${orderStages}">
        <a class="button-2" href="/userPage/orderHistory/stage${stage.orderStageID}">
            <span>${stage.name}</span>
        </a>
    </c:forEach>
</sec:authorize>
<sec:authorize access="hasAnyRole('ADMIN', 'SELLER')">
    <c:url value="/userPage/orderHistory" var="find"/>
    <form:form action="${find}">
        <input type="hidden" name="form" value="findUser"/>
        <label style="color: #450619"><spring:message code="findByNameOrLogin"/></label>
        <input name="name" required>
        <button type="submit" style="border: none; background: none"><img src="/images/search.png" width="30px"
                                                                          height="30px" alt="Find" border="0px">
        </button>
    </form:form>
    <c:url value="/userPage/orderHistory" var="findByOrderId"/>
    <form:form action="${findByOrderId}">
        <input type="hidden" name="form" value="findByOrderId"/>
        <label style="color: #450619"><spring:message code="findByOrderNumber"/></label>
        <input name="orderId" required>
        <button type="submit" style="border: none; background: none"><img src="/images/search.png" width="30px"
                                                                          height="30px" alt="Find" border="0px">
        </button>
        <span style="color: #fa0404">${error}</span>
        <span style="color: #fa0404">${err}</span>
    </form:form>

    <c:url value="/userPage/orderHistory" var="findByProductId"/>
    <form:form action="${findByProductId}">
        <input type="hidden" name="form" value="findByProductId"/>
        <label style="color: #450619"><spring:message code="findByProduct"/></label>
        <select required name="productId">
            <option value=""></option>
            <c:forEach var="product" items="${products}">
                <option value="${product.id}">${product.name}</option>
            </c:forEach>
        </select>
        <button type="submit" style="border: none; background: none"><img src="/images/search.png" width="30px"
                                                                          height="30px" alt="Find" border="0px">
        </button>
    </form:form>

    <c:url value="/userPage/orderHistory" var="findByGlazeType"/>
    <form:form action="${findByGlazeType}">
        <input type="hidden" name="form" value="findByGlazeType"/>
        <label style="color: #450619"><spring:message code="findByGlazeType"/></label>
        <select required name="glazeTypeId">
            <option value=""></option>
            <c:forEach var="type" items="${glazesTypes}">
                <option value="${type.id}">${type.name}</option>
            </c:forEach>
        </select>
        <button type="submit" style="border: none; background: none"><img src="/images/search.png" width="30px"
                                                                          height="30px" alt="Find" border="0px">
        </button>
    </form:form>

    <c:url value="/userPage/orderHistory" var="findByPeriod"/>
    <form:form action="${findByPeriod}">
        <input type="hidden" name="form" value="findByPeriod"/>
        <label style="color: #450619"><spring:message code="findOrdersInPeriod"/></label>
        <spring:message code="from"/>: <input type="date" id="first" name="fromDate">
        <spring:message code="to"/>: <input type="date" id="second" name="toDate">
        <button type="submit" style="border: none; background: none"><img src="/images/search.png" width="30px"
                                                                          height="30px" alt="Find" border="0px">
        </button>
    </form:form>

</sec:authorize>

<h3 align="center"><spring:message code="title.ordersPage"/></h3>
<table border="1" cellpadding="10" class="table_blur" align="center">
    <tr>
        <th>№</th>
        <th><spring:message code="number.ordersPage"/></th>
        <th><spring:message code="date.ordersPage"/></th>
        <sec:authorize access="isAuthenticated() && !(hasAnyRole('PRODUCER', 'PURCHASER'))">
            <th><spring:message code="amount.orderPage"/></th>
        </sec:authorize>
        <th><spring:message code="status.ordersPage"/></th>
        <th><spring:message code="details.ordersPage"/></th>
        <sec:authorize access="isAuthenticated() && (hasRole('ADMIN') || hasRole('SELLER'))">
            <th><spring:message code="user.ordersPage"/></th>
            <th><spring:message code="solvency.ordersPage"/></th>
            <th><spring:message code="actionWithProduct.catalogPage"/></th>
        </sec:authorize>
    </tr>
    <c:forEach items="${orders}" var="order" varStatus="status">
        <tr>
            <td>${status.index+1}</td>
            <td>${order.id}</td>
            <td>${order.orderDate}</td>
            <sec:authorize access="isAuthenticated() && !(hasAnyRole('PRODUCER', 'PURCHASER'))">
                <td>${order.amount}</td>
            </sec:authorize>
            <td>${order.orderStage.name}</td>
            <td><a href="/userPage/orderHistory/orderDetails/${order.id}"/><spring:message
                    code="check.ordersPage"/>
            </td>
            <sec:authorize access="isAuthenticated() && (hasRole('ADMIN') || hasRole('SELLER'))">
                <td>${order.user.name}</td>
                <td>${order.user.solvency.name}</td>
                <c:if test="${order.user.email != null}">
                    <td><c:if test="${order.orderStage.orderStageID == 1}">
                        <a title="Send an email about the readiness of the product."
                           href="/userPage/orderHistory/order${order.id}/enterEmail">
                            <img src="/images/email.png" width="35px" height="35px"></a>
                    </c:if></td>
                </c:if>
            </sec:authorize>
            <sec:authorize access="hasRole('USER')">
                <td><c:if test="${order.orderStage.orderStageID == 1}">
                    <a title="Send an email about the readiness of the product."
                       href="/userPage/orderHistory/order${order.id}/enterEmail">
                        <img src="/images/email.png" width="35px" height="35px"></a>
                </c:if></td>
            </sec:authorize>
        </tr>
    </c:forEach>
</table>
<br>
<div style="color: #a2010e; font-size: large" align="center">${msg}</div>
<script>
    var el = document.getElementsByTagName("td");
    for (var i = 0; i < el.length; i++) {
        if (el[i].innerHTML == "PREPAYMENT") {
            el[i].className += " " + "PREPAYMENT";
        } else if (el[i].innerHTML == "POSTPAYMENT") {
            el[i].className += " " + "POSTPAYMENT";
        } else if (el[i].innerHTML == "DEBTOR") {
            el[i].className += " " + "DEBTOR";
        }
        if (el[i].innerHTML == "AVAILABLE") {
            el[i].className += " " + "AVAILABLE";
        } else if (el[i].innerHTML == "AWAITING CONFIRMATION") {
            el[i].className += " " + "AWAITING_CONFIRMATION";
        } else if (el[i].innerHTML == "IN PRODUCTION") {
            el[i].className += " " + "IN_PRODUCTION";
        } else if (el[i].innerHTML == "AWAITING CONFIRMATION FROM PRODUCTION") {
            el[i].className += " " + "AWAITING_CONFIRMATION_FROM_PRODUCTION";
        } else if (el[i].innerHTML == "AVAILABLE FOR SELL") {
            el[i].className += " " + "AVAILABLE_FOR_SELL";
        } else if (el[i].innerHTML == "AWAITING CONFIRMATION FROM PURCHASES") {
            el[i].className += " " + "AWAITING_CONFIRMATION_FROM_PURCHASES";
        } else if (el[i].innerHTML == "AVAILABLE FOR PRODUCTION") {
            el[i].className += " " + "AVAILABLE_FOR_PRODUCTION";
        }
    }
</script>
</table>
</body>
</html>
