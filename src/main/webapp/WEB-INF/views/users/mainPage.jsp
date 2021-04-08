<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title><spring:message code="title.mainPage"/></title>
    <%@include file="/WEB-INF/views/header/headerMainPage.jsp" %>
</head>
<body>
<div class="post-wrap">
    <sec:authorize access="isAuthenticated()">
    <div class="post-item">
        <div class="item-content">
            <img src="/images/edit_users_data.png" width="70px" height="70px">
            <div class="item-body">
                <h3><spring:message code="changePersonalData.mainPage"/></h3>
                <sec:authorize access="hasRole('USER')">
                    <p><spring:message code="text.changePersonalDataUser.mainPage"/></p>
                </sec:authorize>
                <sec:authorize access="!hasRole('USER')">
                    <p><spring:message code="text.changePersonalDataOther.mainPage"/></p>
                </sec:authorize>
            </div>
            <div class="item-footer">
                <sec:authorize access="hasRole('USER')">
                <a href="/userPage/edit/${user.id}" class="link"><span><img src="images/arrow.png" width="30px"
                                                               height="30px"></span></a></sec:authorize>
                <sec:authorize access="!hasRole('USER')"><a href="/userPage/editManager/${user.id}" class="link"><span><img src="images/arrow.png" width="30px"
                                                                                                                     height="30px"></span></a></sec:authorize>
            </div>
        </div>
    </div>
    </sec:authorize>
    <div class="post-item">
        <div class="item-content">
            <img src="/images/online_shop.png" width="70px" height="70px">
            <div class="item-body">
                <h3><spring:message code="catalog.mainPage"/></h3>
                <p><spring:message code="text.catalog.mainPage"/></p>
            </div>
            <div class="item-footer">
                <a href="/catalog" class="link"><span><img src="images/arrow.png" width="30px" height="30px"></span></a>
            </div>
        </div>
    </div>
    <sec:authorize access="isAuthenticated() && !(hasAnyRole('USER'))">
    <div class="post-item">
        <div class="item-content">
            <img src="/images/task.png" width="70px" height="70px">
            <div class="item-body">
                <h3><spring:message code="task.mainPage"/></h3>
                <p><spring:message code="message.task.mainPage"/></p>
            </div>
            <div class="item-footer">
                <a href="/userPage/orderHistory" class="link"><span><img src="images/arrow.png" width="30px" height="30px"></span></a>
            </div>
        </div>
    </div>
        <div class="post-item">
            <div class="item-content">
                <img src="/images/reports.png" width="70px" height="70px">
                <div class="item-body">
                    <h3><spring:message code="title.report"/></h3>
                    <p><spring:message code="message.report"/></p>
                </div>
                <div class="item-footer">
                    <a href="/userPage/reports" class="link"><span><img src="images/arrow.png" width="30px" height="30px"></span></a>
                </div>
            </div>
        </div>
    </sec:authorize>
    <sec:authorize access="hasAnyRole('ADMIN', 'PRODUCER')">
    <div class="post-item">
        <div class="item-content">
            <img src="/images/create.png" width="70px" height="70px">
            <div class="item-body">
                <h3><spring:message code="product.mainPage"/></h3>
                <p><spring:message code="message.product.mainPage"/></p>
            </div>
            <div class="item-footer">
                <a href="/userPage/actionWithProduct" class="link"><span><img src="images/arrow.png" width="30px" height="30px"></span></a>
            </div>
        </div>
    </div>
<%--        <div class="post-item">--%>
<%--            <div class="item-content">--%>
<%--                <img src="/images/edit_product.png" width="70px" height="70px">--%>
<%--                <div class="item-body">--%>
<%--                    <h3><spring:message code="glazeType.mainPage"/></h3>--%>
<%--                    <p><spring:message code="message.glazeType.mainPage"/></p>--%>
<%--                </div>--%>
<%--                <div class="item-footer">--%>
<%--                    <a href="/userPage/actionWithGlazeType" class="link"><span><img src="/images/arrow.png" width="30px" height="30px"></span></a>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
    </sec:authorize>
<%--    <sec:authorize access="hasAnyRole('ADMIN', 'PURCHASER')">--%>
<%--    <div class="post-item">--%>
<%--        <div class="item-content">--%>
<%--            <img src="/images/create.png" width="70px" height="70px">--%>
<%--            <div class="item-body">--%>
<%--                <h3><spring:message code="raw.mainPage"/></h3>--%>
<%--                <p><spring:message code="message.raw.mainPage"/></p>--%>
<%--            </div>--%>
<%--            <div class="item-footer">--%>
<%--                <a href="/userPage/actionWithRawMaterial" class="link"><span><img src="/images/arrow.png" width="30px" height="30px"></span></a>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    </sec:authorize>--%>
    <div class="post-item">
        <div class="item-content">
            <img src="/images/orderHistory.png" width="70px" height="70px">
            <div class="item-body">
                <h3><spring:message code="orderHistory.mainPage"/></h3>
                <p><spring:message code="text.orderHistory.mainPage"/></p>
            </div>
            <div class="item-footer">
                <a href="/userPage/orderHistory" class="link"><span><img src="/images/arrow.png" width="30px"
                                                                         height="30px"></span></a>
            </div>
        </div>
    </div>
    <sec:authorize access="hasRole('USER')">
        <div class="post-item">
            <div class="item-content">
                <img src="/images/contacts.png" width="70px" height="70px">
                <div class="item-body">
                    <h3><spring:message code="contacts.mainPage"/></h3>
                    <p><spring:message code="text.contacts.mainPage"/></p>
                </div>
                <div class="item-footer">
                    <a href="/contacts" class="link"><span><img src="/images/arrow.png" width="30px"
                                                                height="30px"></span></a>
                </div>
            </div>
        </div>
    </sec:authorize>

    <sec:authorize access="hasRole('ADMIN') || hasRole('SELLER')">
        <div class="post-item">
            <div class="item-content">
                <img src="/images/managers.png" width="70px" height="70px">
                <div class="item-body">
                    <h3><spring:message code="users.mainPage"/></h3>
                    <p><spring:message code="text.users.mainPage"/></p>
                </div>
                <div class="item-footer">
                   <a href="/users" class="link"><span><img
                            src="/images/arrow.png" width="30px"
                            height="30px"></span></a>
                </div>
            </div>
        </div>
    </sec:authorize>
    <sec:authorize access="hasRole('ADMIN')">
        <div class="post-item">
            <div class="item-content">
                <img src="/images/managers.png" width="70px" height="70px">
                <div class="item-body">
                    <h3><spring:message code="managers.mainPage"/></h3>
                    <p><spring:message code="text.managers.mainPage"/></p>
                </div>
                <div class="item-footer">
                    <a href="/managers" class="link"><span><img src="/images/arrow.png" width="30px"
                                                                height="30px"></span></a>
                </div>
            </div>
        </div>
    </sec:authorize>
</div>

</body>
</html>