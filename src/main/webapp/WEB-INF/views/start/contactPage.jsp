<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title><spring:message code="title.contactsPage"/></title>
    <%@include file="/WEB-INF/views/header/header.jsp"%>
</head>
<body>
<div class="post-wrap">
    <div class="post-item">
        <div class="item-content">
            <img src="images/ceo.png" width="70px" height="70px">
            <div class="item-body">
                <h3><spring:message code="ceo.contactsPage"/></h3>
                <p><spring:message code="ceo.shotInfo.contactsPage"/></p>
            </div>
            <div class="item-footer">
                <a href="#" class="link"><span>+375 17 222 02 22</span></a>
            </div>
        </div>
    </div>

    <div class="post-item">
        <div class="item-content">
            <img src="images/deputy.png" width="70px" height="70px">
            <div class="item-body">
                <h3><spring:message code="commercialDeputy.contactsPage"/></h3>
                <p><spring:message code="commercialDeputy.shotInfo.contactsPage"/></p>
            </div>
            <div class="item-footer">
                <a href="#" class="link"><span>+375 17 222 02 20</span></a>
            </div>
        </div>
    </div>

    <div class="post-item">
        <div class="item-content">
            <img src="images/deputy_2.png" width="70px" height="70px">
            <div class="item-body">
                <h3><spring:message code = "developmentDeputy.contactsPage"/></h3>
                <p><spring:message code="developmentDeputy.shotInfo.contactsPage"/></p>
            </div>
            <div class="item-footer">
                <a href="#" class="link"><span>+375 17 222 02 23</span></a>
            </div>
        </div>
    </div>

    <div class="post-item">
        <div class="item-content">
            <img src="images/managers.png" width="70px" height="70px">
            <div class="item-body">
                <h3><spring:message code="sales.contactsPage"/></h3>
                <p><spring:message code="sales.shotInfo.contactsPage"/></p>
            </div>
            <div class="item-footer">
                <a href="#" class="link"><span>+375 17 222 02 25</span></a>
            </div>
        </div>
    </div>

    <div class="post-item">
        <div class="item-content">
            <img src="images/managers.png" width="70px" height="70px">
            <div class="item-body">
                <h3><spring:message code="purchasing.contactsPage"/></h3>
                <p><spring:message code="purchasing.shotInfo.contactsPage"/></p>
            </div>
            <div class="item-footer">
                <a href="#" class="link"><span>+375 17 222 02 29</span></a>
            </div>
        </div>
    </div>

    <div class="post-item">
        <div class="item-content">
            <img src="images/managers.png" width="70px" height="70px">
            <div class="item-body">
                <h3><spring:message code="accounting.contactsPage"/></h3>
                <p><spring:message code="accounting.shotInfo.contactsPage"/></p>
            </div>
            <div class="item-footer">
                <a href="#" class="link"><span>+375 17 222 02 30</span></a>
            </div>
        </div>
    </div>
    <div class="post-item">
        <div class="item-content">
            <img src="images/managers.png" width="70px" height="70px">
            <div class="item-body">
                <h3><spring:message code="quality.contactsPage"/></h3>
                <p><spring:message code="quality.shotInfo.contactsPage"/></p>
            </div>
            <div class="item-footer">
                <a href="#" class="link"><span>+375 17 222 02 35</span></a>
            </div>
        </div>
    </div>
    <div class="post-item">
        <div class="item-content">
            <img src="images/managers.png" width="70px" height="70px">
            <div class="item-body">
                <h3><spring:message code="humanRes.contactsPage"/></h3>
                <p><spring:message code="humanRes.shotInfo.contactsPage"/></p>
            </div>
            <div class="item-footer">
                <a href="#" class="link"><span>+375 17 222 02 45</span></a>
            </div>
        </div>
    </div>
    <div class="post-item">
        <div class="item-content">
            <img src="images/support.png" width="70px" height="70px">
            <div class="item-body">
                <h3><spring:message code="coop.contactsPage"/></h3>
                <p><spring:message code="coop.shotInfo.contactsPage"/></p>
            </div>
            <div class="item-footer">
                <a href="#" class="link"><span>+375 17 222 02 40</span></a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
