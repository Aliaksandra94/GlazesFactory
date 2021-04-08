<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<style>
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
    <title><spring:message code="orders.report"/></title>
    <%@include file="/WEB-INF/views/header/header.jsp" %>
</head>
<body>
<p align="right">
<a class="button-2" href="/userPage/reports/onOrders">
    <span><spring:message code="show.report"/></span>
</a></p>
<sec:authorize access="hasAnyRole('ADMIN', 'SELLER')">
<c:url value="/userPage/reports/onOrders" var="find"/>
<form:form action="${find}">
    <input type="hidden" name="form" value="findUser"/>
    <label style="color: #450619"><spring:message code="findByNameOrLogin"/></label>
    <input name="name" required>
    <button type="submit" style="border: none; background: none"><img src="/images/search.png" width="30px"
                                                                      height="30px" alt="Find" border="0px">
    </button>
</form:form>

<c:url value="/userPage/reports/onOrders" var="groupByUserNameOrLogin"/>
<form:form action="${groupByUserNameOrLogin}">
    <input type="hidden" name="form" value="groupByUserNameOrLogin"/>
    <label style="color: #450619"><spring:message code="groupByNameOrLogin"/></label>
    <input name="name" required>
    <button type="submit" style="border: none; background: none"><img src="/images/search.png" width="30px"
                                                                      height="30px" alt="Find" border="0px">
    </button>
</form:form>
</sec:authorize>
<c:url value="/userPage/reports/onOrders" var="findByOrderId"/>
<form:form action="${findByOrderId}">
    <input type="hidden" name="form" value="findByOrderId"/>
    <label style="color: #450619"><spring:message code="findByOrderNumber"/></label>
    <input name="orderId" required>
    <button type="submit" style="border: none; background: none"><img src="/images/search.png" width="30px"
                                                                      height="30px" alt="Find" border="0px">
    </button>
    <span style="color: #fa0404">${error}</span>
</form:form>

<c:url value="/userPage/reports/onOrders" var="findByOrderStage"/>
<form:form action="${findByOrderStage}">
    <input type="hidden" name="form" value="findByOrderStage"/>
    <label style="color: #450619"><spring:message code="showByStage"/></label>
    <select required name="orderStageId">
        <option value=""></option>
        <sec:authorize access="hasAnyRole('ADMIN', 'SELLER')">
        <c:forEach var="stage" items="${orderStages}">
            <option value="${stage.orderStageID}">${stage.name}</option>
        </c:forEach>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('PRODUCER')">
            <c:forEach var="stage" items="${orderStages}">
                <c:if test="${stage.orderStageID == 2 || stage.orderStageID == 3 || stage.orderStageID == 7}">
                <option value="${stage.orderStageID}">${stage.name}</option>
                </c:if>
            </c:forEach>
        </sec:authorize>
    </select>
    <button type="submit" style="border: none; background: none" onclick="show('showInfo')"><img src="/images/search.png" width="30px"
                                                                      height="30px" alt="Find" border="0px">
    </button>
</form:form>

<c:url value="/userPage/reports/onOrders" var="findByPeriod"/>
<form:form action="${findByPeriod}">
    <input type="hidden" name="form" value="findByPeriod"/>
    <label style="color: #450619"><spring:message code="findOrdersInPeriod"/></label>
    <spring:message code="from"/>: <input type="date" id="first" name="fromDate">
    <spring:message code="to"/>: <input type="date" id="second" name="toDate">
    <button type="submit" style="border: none; background: none"><img src="/images/search.png" width="30px"
                                                                      height="30px" alt="Find" border="0px">
    </button>
</form:form>

<c:url value="/userPage/reports/onOrders" var="sortByDate"/>
<form:form action="${sortByDate}">
    <input type="hidden" name="form" value="sortByDate"/>
    <label style="color: #450619"><spring:message code="sortByOrderDate"/></label>
    <select required name="sortById">
        <option value=""></option>
        <option value="1"><spring:message code="ascending"/></option>
        <option value="2"><spring:message code="descending"/></option>
    </select>
    <button type="submit" style="border: none; background: none"><img src="/images/search.png" width="30px"
                                                                      height="30px" alt="Find" border="0px">
    </button>

</form:form>

<c:url value="/userPage/reports/onOrders" var="sortBySum"/>
<form:form action="${sortBySum}">
    <input type="hidden" name="form" value="sortBySum"/>
    <label style="color: #450619"><spring:message code="sortByOrderSum"/></label>
    <select required name="sortById">
        <option value=""></option>
        <option value="1"><spring:message code="ascending"/></option>
        <option value="2"><spring:message code="descending"/></option>
    </select>
    <button type="submit" style="border: none; background: none"><img src="/images/search.png" width="30px"
                                                                      height="30px" alt="Find" border="0px">
    </button>
</form:form>

<table border="1" cellpadding="10" class="table_blur" align="center">
    <h3 align="center"><spring:message code="orders.report"/></h3>
    <tr>
        <th colspan="3"><spring:message code="totalOrders.report"/></th>
        <th colspan="3"><spring:message code="totalAmount.report"/></th>
    </tr>
    <tr>
        <td colspan="3"> ${orders.size()} </td>
        <td colspan="3">${ordersAmounts}</td>
    </tr>
    <tr>
        <th colspan="6"><spring:message code="details.report"/> </th>
    </tr>
    <tr>
        <th>№</th>
        <th><spring:message code="number.ordersPage"/></th>
        <th><spring:message code="date.ordersPage"/></th>
        <th><spring:message code="amount.orderPage"/></th>
        <th><spring:message code="status.ordersPage"/></th>
        <th><spring:message code="user.ordersPage"/></th>
    </tr>
    <c:forEach items="${orders}" var="order" varStatus="status">
        <tr>
            <td>${status.index+1}</td>
            <td>${order.orderNumber}</td>
            <td>${order.orderDate}</td>
            <td>${order.amount}</td>
            <td>${order.orderStage.name}</td>
            <td>${order.user.name}</td>
        </tr>
    </c:forEach>
</table>
<div style="color: #a2010e; font-size: large" align="center">${msg}</div>
<script>
    var el = document.getElementsByTagName("td");
    for (var i = 0; i < el.length; i++) {
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
</body>
</html>
