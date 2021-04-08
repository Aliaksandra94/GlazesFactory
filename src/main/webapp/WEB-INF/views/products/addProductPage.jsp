<%@ page import="com.project.moroz.glazes_market.entity.Color" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <script type="text/javascript" src="js/functions.js"></script>
    <title><spring:message code="title.addProduct"/></title>
    <%@include file="/WEB-INF/views/header/header.jsp" %>
    <link href="<c:url value="/js/functions.js"/>" rel="stylesheet">
</head>
<body>
<sec:authorize access="hasRole('USER')"><c:url value="/catalog/create" var="var"/> </sec:authorize>
<sec:authorize access="isAuthenticated() && hasAnyRole('ADMIN', 'PRODUCER')"> <c:url value="/catalog/add"
                                                                                     var="var"/></sec:authorize>
<form:form method="POST" action="${var}" modelAttribute="product" cssClass="transparent">
    <div class="form-inner">
        <sec:authorize access="hasRole('USER')"><h2><spring:message code="head.createProduct"/></h2></sec:authorize><br>
        <sec:authorize access="isAuthenticated() && !hasRole('USER')"><h2><spring:message code="title.addProduct"/></h2>
            <br>
        </sec:authorize>
        <form:label path="name"><spring:message code="productName.addProduct"/></form:label>
        <form:input path="name"/>
        <div style="color: red">${msg}</div>
        <form:errors path="name" cssClass="error"/>

        <sec:authorize access="hasRole('USER')">
            <form:label path="description"><spring:message code="color.createColor"/> </form:label>
            <select required name="colorId" id="colorId" onchange="getValue()">
                <option value=""><spring:message code="color.createColor"/></option>
                <c:forEach var="color" items="<%= Color.values()%>">
                    <option value="${color}">${color}</option>
                </c:forEach>
            </select>
            <script>
                document.getElementById("colorId").onchange = function () {
                    localStorage.setItem('colorId', document.getElementById("colorId").value);
                }

                if (localStorage.getItem('colorId')) {
                    document.getElementById("colorId").options[localStorage.getItem('colorId')].selected = true;
                }
            </script>
        </sec:authorize>
        <br>
        <br>
        <form:label path="description"><spring:message code="selectProductType.addProduct"/> </form:label>
        <select required name="typeID" id="typeID" onchange="myFunction()">
            <option value=""><spring:message code="selectProductType.addProduct"/></option>
            <c:forEach var="type" items="${glazesType}">
                <option value="${type.id}">${type.name}</option>
            </c:forEach>
        </select>
        <p id="price"></p>
        <script>
            document.getElementById("typeID").onchange = function () {
                localStorage.setItem('typeID', document.getElementById("typeID").value);
            }

            if (localStorage.getItem('typeID')) {
                document.getElementById("typeID").options[localStorage.getItem('typeID')].selected = true;
            }
        </script>
        <br>
        <sec:authorize access="isAuthenticated() && hasAnyRole('ADMIN', 'PRODUCER')">
            <form:label path="description"><spring:message code="productDescription.addProduct"/></form:label>
            <form:input path="description"/>
            <form:errors path="description" cssClass="error"/>

            <form:label path="price"><spring:message code="productPrice.addProduct"/></form:label>
            <form:input path="price"/>
            <form:errors path="price" cssClass="error"/>

        </sec:authorize>
        <form:label path="quantity"><spring:message code="productQuantity.addProduct"/></form:label>
        <input name="quantity">
        <form:errors path="quantity" cssClass="error"/>
        <sec:authorize access="isAuthenticated() && hasAnyRole('ADMIN', 'PRODUCER')">
            <form:label path="productionTime"><spring:message code="productTimeProduction.addProduct"/></form:label>
            <form:input path="productionTime"/>
            <form:errors path="productionTime" cssClass="error"/>

            <spring:message code="actionWithProduct.addProduct" var="add"/>
        </sec:authorize>
        <sec:authorize access="hasRole('USER')">
            <spring:message code="actionWithProduct.checkOrder" var="add"/>
        </sec:authorize>
        <input type="submit" value="${add}">
    </div>
</form:form>
</body>
</html>