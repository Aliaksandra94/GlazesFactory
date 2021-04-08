<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title><spring:message code="title.report"/></title>
    <%@include file="/WEB-INF/views/header/header.jsp" %>
</head>
<body>
<h3 align="center"><spring:message code="title.report"/></h3>
<div class="post-wrap">
    <sec:authorize access="hasAnyRole('ADMIN', 'SELLER')">
    <div class="post-item">
        <div class="item-content">
            <img src="/images/report1.png" width="70px" height="70px">
            <div class="item-body">
                <h3><spring:message code="clients.report"/></h3>
            </div>
            <div class="item-footer">
                <a href="/userPage/reports/onClients" class="link"><span><img src="/images/arrow.png" width="30px" height="30px"></span></a>
            </div>
        </div>
    </div>
    </sec:authorize>
    <div class="post-item">
        <div class="item-content">
            <img src="/images/report2.png" width="70px" height="70px">
            <div class="item-body">
                <h3><spring:message code="products.report"/></h3>
            </div>
            <div class="item-footer">
                <a href="/userPage/reports/onProducts" class="link"><span><img src="/images/arrow.png" width="30px" height="30px"></span></a>
            </div>
        </div>
    </div>
<sec:authorize access="hasAnyRole('ADMIN')">
    <div class="post-item">
        <div class="item-content">
            <img src="/images/report4.png" width="70px" height="70px">
            <div class="item-body">
                <h3><spring:message code="managers.report"/></h3>
            </div>
            <div class="item-footer">
                <a href="/userPage/reports/onManagers" class="link"><span><img src="/images/arrow.png" width="30px" height="30px"></span></a>
            </div>
        </div>
    </div>
</sec:authorize>
<sec:authorize access="hasAnyRole('ADMIN', 'SELLER', 'PRODUCER')">
    <div class="post-item">
        <div class="item-content">
            <img src="/images/report5.png" width="70px" height="70px">
            <div class="item-body">
                <h3><spring:message code="orders.report"/></h3>
            </div>
            <div class="item-footer">
                <a href="/userPage/reports/onOrders" class="link"><span><img src="/images/arrow.png" width="30px" height="30px"></span></a>
            </div>
        </div>
    </div>
</sec:authorize>
    <sec:authorize access="hasAnyRole('ADMIN', 'PURCHASER')">
        <div class="post-item">
            <div class="item-content">
                <img src="/images/report6.png" width="70px" height="70px">
                <div class="item-body">
                    <h3><spring:message code="raw.report"/></h3>
                </div>
                <div class="item-footer">
                    <a href="/userPage/reports/onRaw" class="link"><span><img src="/images/arrow.png" width="30px" height="30px"></span></a>
                </div>
            </div>
        </div>
    </sec:authorize>
</div>
</body>
</html>
