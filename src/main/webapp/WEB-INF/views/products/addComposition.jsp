<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <%@include file="/WEB-INF/views/header/header.jsp" %>
</head>
<body>
<c:url value="/userPage/actionWithGlazeType/addNewGlazeType" var="add"/>
<form:form method="POST" action="${add}" modelAttribute="glazeType" cssClass="transparent">
    <div class="form-inner">
        <h2><spring:message code="add.composition"/></h2>
        <br>
        <h5><spring:message code="productName.addProduct"/></h5>
        <form:input id="name" path="name"/>
        <div style="color: red">${msg}</div>
        <form:errors path="name" cssClass="error"/>


        <c:forEach var="category" items="${categories}">
        <h5>${category.name}</h5>
                <c:if test="${category.id == 1}">
                    <spring:message code="productBase.createProduct" var="base"/>
        <select required name="base" id="baseID">
            <option value="">${base}</option>
            <c:forEach items="${category.rawMaterials}" var="raw">
                <option value="${raw.name}">${raw.name}</option>
            </c:forEach>
                </c:if>
            <c:if test="${category.id != 1}">
                <c:forEach items="${category.rawMaterials}" var="raw">
                <label>${raw.name}</label>
                    <input id="quantity" type="number">
                </c:forEach>
            </c:if>
        </c:forEach>
        </select>

<%--        <spring:message code="actionWithGlazeType.check" var="check"/>--%>
<%--        <input type="button" value="${check}" onclick = "getInfo()">--%>
<%--        <textarea id="free_space" rows="10" cols="45"></textarea>--%>
        <spring:message code="actionWithGlazeType.check" var="add"/>
        <input type="submit" value="${add}">
    </div>
</form:form>
<%--<script>--%>
<%--    function getInfo(){--%>
<%--        glazeType={};--%>
<%--        glazeType.name = document.getElementById("name").value;--%>
<%--        glazeType.baseID = document.getElementById("baseID").value;--%>
<%--        glazeType.quantity = document.getElementById("quantity").value;--%>
<%--        localStorage.quantity = JSON.stringify(glazeType);--%>
<%--        var glazeType1 = JSON.parse(localStorage.glazeType);--%>
<%--        var place = document.getElementById("free_space");--%>
<%--        place.value = document.getElementById("name").name + ": "+ glazeType1.name + "\n" +--%>
<%--            document.getElementById("baseID").name + ": " + glazeType1.baseID +"\n" +--%>
<%--            document.getElementById("quantity").name + ": " + glazeType1.quantity;--%>
<%--    };--%>

<%--</script>--%>
</body>
</html>
