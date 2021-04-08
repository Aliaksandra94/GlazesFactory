<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title><spring:message code="title.loginPage"/></title>
    <link href="css/style.css" rel="stylesheet" type="text/css">
    <%@include file="/WEB-INF/views/header/header_regPage.jsp"%>
</head>
<body>
<c:url value="/registration" var="var"/>
<form:form method="post" action="${var}" modelAttribute="user" cssClass="transparent">
    <div class="form-inner">
        <form:label path="name"><spring:message code="name.registrationPage"/></form:label>
        <form:input path="name"/>
        <form:errors path="name" cssClass="error"/>

        <form:label path="login"><spring:message code="login.registrationPage"/></form:label>
        <form:input path="login"/>
        <form:errors path="login" cssClass="error"/>

        <form:label path="password"><spring:message code="password.registrationPage"/></form:label>
        <form:input path="password"/>
        <form:errors path="password" cssClass="error"/>

        <spring:message code="submit.registrationPage" var="submit"/>
        <input type="submit" value="${submit}">
    </div>
</form:form>
</body>
</html>
