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
<div class="page-title"><spring:message code="title.accessDeniedPage"/>!</div>
<h3 align="center" style="color:red;"><spring:message code="message.accessDeniedPage"/>!</h3>
</body>
</html>
