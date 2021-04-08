<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title><spring:message code="title.accessDeniedPage"/> </title>
    <%@include file="/WEB-INF/views/header/header.jsp"%>
</head>
<body>
<div class="page-title">
    <h2 style="color: #56122c;"> <spring:message code="title.confirmEmail"/>!</div></h2><br>
<h3 align="center" style="color: #56122c;">
    <spring:message code="message1.confirmEmail"/><span style="color: #8a0031"><h2>${order.orderNumber}</h2></span>
    <spring:message code="message2.confirmEmail"/> <span style="color: #8a0031"><h2>${user.name}</h2></span>
    <spring:message code="message3.confirmEmail"/> <span style="color: #8a0031"><h2>${user.email}</h2></span>.
</h3>
<br><br>
</body>
</html>
