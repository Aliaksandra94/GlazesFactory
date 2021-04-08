<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title><spring:message code="title.basketPage"/></title>
    <%@include file="/WEB-INF/views/header/header_basketPage.jsp" %>
</head>
<body>
<h2 align="center"><spring:message code="title.basketPage"/></h2>
<c:if test="${basket == null || basket.basketInfos == null || basket.basketInfos.size()==0}">
    <br><br>
    <h2><spring:message code="noBasketItem.basketPage"/></h2>
</c:if>
<a title="Back to catalog for continue" href="/catalog"><img src="/images/online_shop.png" width="40" height="40">
    <spring:message code="backCatalog.basketPage"/></a>
<c:if test="${basket != null || basket.basketInfos != null || !basket.basketInfos.size()==0}">
    <c:url value="/basket" var="getBasket"/>
    <form:form method="post" action="${getBasket}" modelAttribute="basket">
        <table border="1" cellpadding="10" class="table_blur" align="center">
            <tr>
                <th>№</th>
                <th><spring:message code="productName.basketPage"/></th>
                <th><spring:message code="productPrice.basketPage"/></th>
                <th><spring:message code="productNewQuantity.basketPage"/></th>
                <th><spring:message code="productCost.basketPage"/></th>
                <th><spring:message code="userAction"/></th>
            </tr>
            <c:forEach var="item" items="${basket.basketInfos}" varStatus="status">
            <form:hidden path="basketInfos[${status.index}].product.id"/>
            <tr>
                <td>${status.index+1}</td>
                <td> ${item.product.name}</td>
                <td> ${item.product.price} </td>

                <td>
                    <form:input path="basketInfos[${status.index}].quantity"/>
                <c:set var="amount" value="${item.amount}"/>
                <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${amount}"/></td>
                <td>
                    <a title="Delete product from basket" href="/basket/delete?idProduct=${item.product.id}">
                        <img src="/images/remove_from_basket.png" width="50px" height="50px"></a>
                </td>
            </tr>
                </c:forEach>
            <tr>
                <td colspan="3"></td>
                <td><input type="image" value="Update quantity" src="/images/update.png" width="30px" height="30px"/>
                <spring:message code="updateQuantity.basketPage"/></td>
                <td colspan="2"></td>
            </tr>
            <tr>
                <td colspan="6"></td>
            </tr>
            <tr>
                <td align="right" colspan="4"><spring:message code="totalCost.basketPage"/></td>
                <td align="center">${basket.amountTotal}</td>
                <td><sec:authorize access="isAuthenticated()">
                    <form:hidden path="userInfo.id"/>
                    <c:if test="${basket.amountTotal > 0}">
                    <a title="Confirm order" href="/order"><img src="/images/confirm.png" width="40" height="40">
                        <spring:message code="confirm.basketPage"/></a>
                    </c:if>

                </sec:authorize>
                    <br><br>
                    <sec:authorize access="!isAuthenticated()">
                        <h5><spring:message code="toContinue.basketPage"/></h5>
                    </sec:authorize></td>
            </tr>
        </table>
    </form:form>
</c:if>
</body>
</html>
