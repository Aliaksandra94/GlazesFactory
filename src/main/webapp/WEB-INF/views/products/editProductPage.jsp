<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <script type="text/javascript" src="js/functions.js"></script>
    <title><spring:message code="title.editProduct"/></title>
    <%@include file="/WEB-INF/views/header/header.jsp"%>
</head>
<body>
<c:url value="/catalog/edit" var="var"/>
<form:form method="POST" action="${var}" modelAttribute="product" cssClass="transparent">
    <div class="form-inner">
        <h2><spring:message code="title.editProduct"/></h2>
        <input type="hidden" name="id" value="${product.id}">
        <form:label path="name"><spring:message code="productName.addProduct"/></form:label>
        <form:input path="name"/>
        <form:errors path="name" cssClass="error"/>
        <br><br>
        <sec:authorize access="hasRole('SELLER')">
            <form:label path="glazesType"><spring:message code="productType.addProduct"/></form:label>
            <input name="glazesType" readonly value="${product.glazesType.name}"/>
            <input type="hidden" name="typeID" value="${product.glazesType.id}">
        </sec:authorize>
        <sec:authorize access="hasRole('ADMIN') || hasRole('PRODUCER')">
        <form:label path="glazesType"><spring:message code="productType.addProduct"/></form:label>
        <select required name="typeID" id="typeID" onchange="myFunction()">
            <option value=""><spring:message code="selectProductType.addProduct"/></option>
            <c:forEach var="type" items="${glazesType}">
                <option value="${type.id}">${type.name}</option>
            </c:forEach>
        </select>
        <script>
            document.getElementById("typeID").onchange = function() {
                localStorage.setItem('typeID', document.getElementById("typeID").value);
            }

            if (localStorage.getItem('typeID')) {
                document.getElementById("typeID").options[localStorage.getItem('typeID')].selected = true;
            }
        </script>
        </sec:authorize>

        <form:label path="description"><spring:message code="productDescription.addProduct"/></form:label>
        <form:input path="description"/>
        <form:errors path="description" cssClass="error"/>

        <form:label path="price"><spring:message code="productPrice.addProduct"/></form:label>
        <form:input path="price"/>
        <form:errors path="price" cssClass="error"/>
        <sec:authorize access="hasRole('SELLER')">
        <form:label path="glazesType.cost"><spring:message code="productPrice.addProduct"/></form:label>
        <form:input path="glazesType.cost" readonly="true"/>
        <form:errors path="glazesType.cost" cssClass="error"/>
        </sec:authorize>

<sec:authorize access="hasRole('SELLER')">
        <form:label path="quantity"><spring:message code="productQuantity.addProduct"/></form:label>
        <form:input path="quantity" readonly="true"/>
        <form:errors path="quantity" cssClass="error"/>
        <form:label path="productionTime"><spring:message code="productTimeProduction.addProduct"/></form:label>
        <form:input path="productionTime" readonly="true"/>
        <form:errors path="productionTime" cssClass="error"/>
</sec:authorize>
        <sec:authorize access="hasRole('ADMIN') || hasRole('PRODUCER')">
            <form:label path="quantity"><spring:message code="productQuantity.addProduct"/></form:label>
            <form:input path="quantity" readonly="true"/>
            <form:errors path="quantity" cssClass="error"/>
            <form:label path="productionTime"><spring:message code="productTimeProduction.addProduct"/></form:label>
            <form:input path="productionTime"/>
            <form:errors path="productionTime" cssClass="error"/>
        </sec:authorize>
        <spring:message code="actionWithProduct.editProduct" var="edit"/>
        <input type="submit" value="${edit}">
    </div>
</form:form>
</body>
</html>