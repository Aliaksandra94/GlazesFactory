<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title><spring:message code="userAction.add"/></title>
    <%@include file="/WEB-INF/views/header/header.jsp" %>
</head>
<body>
<c:url value="/catalog/create/checkPrice" var="var"/>
<form:form method="post" action="${var}" modelAttribute="product" cssClass="transparent">
    <div class="form-inner">
        <br>
        <form:label path="name"><spring:message code="productName.addProduct"/></form:label>
        <form:input path="name" readonly="true"/>

        <form:label path="glazesType"><spring:message code="productType.addProduct"/></form:label>
        <form:input path="glazesType.name" readonly="true"/>
        <input type="hidden" name="typeID" value="${product.glazesType.id}">

        <form:label path="description"><spring:message code="productDescription.addProduct"/></form:label>
        <form:input path="description" readonly="true"/>

        <form:label path="price"><spring:message code="productPrice.addProduct"/></form:label>
        <form:input path="price" readonly="true"/>

        <label><spring:message code="productPriceWithUserDiscount.addProduct"/></label>
        <input name="priceDiscount" readonly value="${product.price-product.price*user.discount/100}">

        <form:label path="quantity"><spring:message code="productOderedQuantity.catalogPage"/></form:label>
        <form:input path="quantity" type="number" readonly="true"/>

        <label><spring:message code="productPriceWithUserDiscount.addProduct"/></label>
        <input readonly value="${product.quantity*(product.price-product.price*user.discount/100)}">

        <form:label path="productionTime"><spring:message code="productTimeProduction.addProduct"/></form:label>
        <form:input path="productionTime" type="number" readonly="true"/>
        <a href="/catalog/create" class="sq-btn btn-one"><spring:message code="backAndTryAgain"/></a>
            <spring:message code="actionWithProduct.createProduct" var="add"/>
        <input type="submit" value="${add}">
    </div>
</form:form>
</body>
</html>