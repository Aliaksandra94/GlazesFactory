<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="title.orderReportPage"/></title>
    <%@include file="/WEB-INF/views/header/header.jsp"%>
</head>
<body>
<div class="post-wrap">
    <div class="post-item">
        <div class="item-content">
            <img src="/images/checked.png" width="70px" height="70px">
            <div class="item-body">
                <h3><spring:message code="title.orderReportPage"/></h3>
                <h3><spring:message code="thanks.orderReportPage"/>! </h3>
                <h3><spring:message code="info.orderReportPage"/> ${lastOrderedCart.orderNum} </h3>
                <p><spring:message code="message.orderReportPage"/></p>
            </div>
        </div>
    </div>
</div>
</body>
</html>
