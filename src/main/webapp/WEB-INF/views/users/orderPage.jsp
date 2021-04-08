<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="title.orderPage"/></title>
    <%@include file="/WEB-INF/views/header/header.jsp" %>
</head>
<body>
<div class="post-wrap">
<div class="post-item">
<div class="item-content">
<img src="/images/unchecked.png" width="70px" height="70px">
<div class="item-body">
    <h3><spring:message code="confirmation.orderPage"/></h3>
    <h3><spring:message code="userInfo.orderPage"/>:</h3>
    <li><spring:message code="name.registrationPage"/>: <span>${user.name}</span></li>
    <li><spring:message code="userDiscount.usersPage"/>: <span> ${user.discount}</span></li>
    <br>
    <h3><spring:message code="basketSummary.orderPage"/>:</h3>
    <li><spring:message code="total.orderPage"/> ${basketModel.basketInfos.size()} <spring:message
            code="amount.orderPage"/>
        <c:set var="amount" value="${basketModel.amountTotal-basketModel.amountTotal*(user.discount/100)}"/>
        <fmt:formatNumber type="number" maxFractionDigits="2" value="${amount}"/>
        <c:if test="${user.discount>0}"> <spring:message code="includeDiscount.orderPage"/> ${user.discount}
        </c:if>
        .
    </li>
</div>
<div class="item-footer">
<a href="/basket" class="link"><spring:message code="editBasket"/></a>
    </div>
    <div class="item-footer">
    <form:form method="post" action="/order" modelAttribute="user">
<input type="image" value="send" src="/images/confirm.png">
    </form:form>
        </div>
    </div>
    </div>
</div>
    </body>
    </html>