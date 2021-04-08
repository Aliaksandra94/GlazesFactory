<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title><spring:message code="title.changeComposition"/></title>
    <%@include file="/WEB-INF/views/header/header.jsp" %>
</head>
<body>
<c:url value="/catalog/changeGlazeComposition" var="var"/>
<form:form method="POST" action="${var}" modelAttribute="glazesType" cssClass="transparent">
    <input type="hidden" name="id" value="${glazesType.id}">
    <div class="form-inner">
        <h2><spring:message code="title.changeComposition"/></h2>
        <div style="color: red">${msg}</div>
        <br>
        <form:label path="name"><spring:message code="productName.addProduct"/></form:label>
        <form:input path="name"/>
        <form:errors path="name" cssClass="error"/>

        <c:forEach var="raw" items="${glazesType.rawMaterialItems}" varStatus="status">
            <form:hidden path="rawMaterialItems[${status.index}].rawMaterial.rawMaterialID"/>
            <form:label path="">${raw.rawMaterial.name}</form:label>
            <form:input path="rawMaterialItems[${status.index}].quantity"/>
        </c:forEach>
        <spring:message code="title.changeComposition" var="add"/>
        <input type="submit" name="id" value="${add}">
    </div>
</form:form>
</body>
</html>
