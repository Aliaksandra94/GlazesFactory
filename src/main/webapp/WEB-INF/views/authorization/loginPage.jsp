<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title><spring:message code="title.loginPage"/></title>
    <%@include file="/WEB-INF/views/header/header_loginPage.jsp"%>
<body>
<c:url value="/login" var="login"/>
<form:form method="post" action="${login}" modelAttribute="user" cssClass="transparent">
    <div class="form-inner">
    <div align="center" style="color: #850000;">${msg}</div>
        <br>
        <form:label path="login"><spring:message code="login.registrationPage"/></form:label>
        <form:input path="login"/>

        <form:label path="password"><spring:message code="password.registrationPage"/></form:label>
        <form:input path="password"/>

    <spring:message code="submit.loginPage" var="submit"/>
        <input type="submit" value="${submit}">
    </div>
</form:form>
</body>
</html>
