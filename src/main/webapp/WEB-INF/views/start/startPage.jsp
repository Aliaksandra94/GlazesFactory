<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title><spring:message code="title.startPage"/></title>
    <%@include file="/WEB-INF/views/header/header.jsp"%>
    <script src="https://js.cx/libs/animate.js"></script>
</head>
<body>
<br><br><br><br>
<p align="center">
    <a title="Information about the company" class="sq-btn btn-one" href="/info">
        <spring:message code="infoAboutCompany.startPage"/></a>
    <a class="sq-btn" title="Go to the catalog" href="/catalog">
        <spring:message code="catalog.startPage"/> </a>
    <a class="sq-btn btn-one" title="Contacts of the company" href="/contacts">
        <spring:message code="contacts.startPage"/> </a>
</p>
<canvas id="myCanvas" width="400" height="400"></canvas>
<%--<div class="stock-wrap">--%>
<%--    <div class="stock-inner">--%>
<%--        <div class="left">--%>
<%--            <p><img align="center" src="images/gift.png"></p>--%>
<%--        </div>--%>
<%--        <div class="right">--%>
<%--            <h3><span>1</span><span class="text"><spring:message code="sale.startPage"/> </span><sup>%</sup>--%>
<%--            </h3>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <p class="stock-text">*<spring:message code="sale.text.startPage"/></p>--%>
<%--    <p class="stock-text">**<spring:message code="sale.exception.startPage"/></p>--%>
<%--</div>--%>
<%--<br><br>--%>
<%--<div class="sale-wrap">--%>
<%--    <div class="sale-inner">--%>
<%--        <div class="sale-info">--%>
<%--            <div class="sale-info-top"><spring:message code="sale.Discount.startPage"/></div>--%>
<%--            <div class="sale-info-bottom"><spring:message code="sale.Company.startPage"/></div>--%>
<%--        </div>--%>
<%--        <div class="sale-text">--%>
<%--            <p><spring:message code="sale.Period.startPage"/></p>--%>
<%--            <h3><spring:message code="sale.Product.startPage"/></h3>--%>
<%--            <p><spring:message code="sale.Client.startPage"/></p>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>


<%--<a title="Basket" href="/userPage/test">--%>
<%--    <img src="images/basket.png" width="30px" height="30px" class="round"><spring:message code="basket.startPage"/>--%>
<%--</a>--%>
</body>
</html>