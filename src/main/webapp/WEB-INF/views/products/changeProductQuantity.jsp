<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title><spring:message code="changeAvailableProductQuantity.catalogPage"/></title>
    <%@include file="/WEB-INF/views/header/header.jsp" %>
</head>
<body>
<c:url value="/catalog/changeProductQuantity" var="var"/>
<form:form method="POST" action="${var}" modelAttribute="product" cssClass="transparent">
    <div class="form-inner">
        <h2><spring:message code="changeAvailableProductQuantity.catalogPage"/></h2>
        <input type="hidden" name="id" value="${product.id}">
        <form:label path="name"><spring:message code="productName.addProduct"/></form:label>
        <form:input path="name" readonly="true"/>

        <label><spring:message code="productQuantity.addProduct"/></label>
        <input name="quantityNew" value="${orderedQuantity.get(product.id)}" readonly/>

        <form:label path="quantity"><spring:message code="productQuantity.catalogPage"/></form:label>
        <form:input path="quantity"/>
        <div style="color: red">${fieldProductQuantity}</div>

        <spring:message code="actionWithProduct.editProduct" var="edit"/>
        <input type="submit" value="${edit}">
    </div>
</form:form>
</body>
</html>
