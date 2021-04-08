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
    <title><spring:message code="clients.report"/></title>
    <%@include file="/WEB-INF/views/header/header.jsp" %>
</head>
<body>
<c:url value="/userPage/reports/onClients" var="find"/>
<form:form action="${find}">
    <input type="hidden" name="form" value="findUser"/>
    <label style="color: #450619"><spring:message code="findByNameOrLogin"/></label>
    <input name="name" required>
    <button type="submit" style="border: none; background: none"><img src="/images/search.png" width="30px"
    height="30px" alt="Find" border="0px">
    </button>
</form:form>
<h3 align="center"><spring:message code="title.ordersPage"/></h3>
<table border="1" cellpadding="10" class="table_blur" align="center">
    <tr>
        <th>№</th>
        <th><spring:message code="name.registrationPage"/></th>
        <th colspan="7"><spring:message code="information.report"/></th>
    </tr>
    <c:forEach items="${allOrdersSum}" var="ordersSum">
        <tr>
            <td rowspan="3">1</td>
            <td rowspan="3">${ordersSum.key.name}</td>
            <td colspan="7">
                Total ${ordersSum.key.orders.size()} orders, total amount ${ordersSum.value.get(0)}</td>

        <tr>
            <c:forEach items="${stages}" var="stage">
                <td>${stage.name}</td>
            </c:forEach></tr>
    <tr>
        <c:forEach items="${stages}" var="stage" varStatus="status">
            <td>Amount: ${ordersSum.value.get(status.index+1)}</td>
        </c:forEach></tr>
    </c:forEach>
</tr>
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
