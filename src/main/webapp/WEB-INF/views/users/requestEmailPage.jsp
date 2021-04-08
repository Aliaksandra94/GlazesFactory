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
<c:url value="/userPage/orderHistory/order${order.id}/enterEmail" var="var"/>
<form:form method="post" action="${var}" modelAttribute="user" cssClass="transparent">
    <div class="form-inner">
        <div align="center" style="color: #fa0404">${msg}</div>
        <br>
        <form:label path="email"><spring:message code="email.registrationPage"/></form:label>
        <form:input path="email" type="email"/>
        <form:errors path="email" cssClass="error"/>
        <spring:message code="submit.emailPage" var="submit"/>
        <input name="id" type="hidden" value="${order.id}">
        <input type="submit" value="${submit}">
    </div>
</form:form>
</body>
</html>