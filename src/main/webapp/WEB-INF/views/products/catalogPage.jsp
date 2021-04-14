<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title><spring:message code="title.catalogPage"/></title>
    <%@include file="/WEB-INF/views/header/header.jsp" %>
    <link href="<c:url value="/js/functions.js"/>" rel="stylesheet">
</head>
<body>
<p align="right">
    <a class="button-2" href="/catalog">
        <span><spring:message code="showProduct.report"/></span>
    </a></p>
<c:url value="/catalog" var="findByType"/>
<form:form action="${findByType}">
    <input type="hidden" name="form" value="findByType"/>
    <label style="color: #450619"><spring:message code="findProductByGlazeType"/></label>
    <select required name="glazeTypeId">
        <option value=""></option>
        <c:forEach var="glazeType" items="${glazeTypes}">
            <option value="${glazeType.id}">${glazeType.name}</option>
        </c:forEach>
    </select>
    <button type="submit" style="border: none; background: none"><img src="/images/search.png" width="30px"
                                                                      height="30px" alt="Find" border="0px">
    </button>
</form:form>
<h2 align="center"><spring:message code="title.catalogPage"/></h2>
<sec:authorize access="hasRole('USER')">
    <a title="create recipe" href="/catalog/create"><img src="/images/create.png" width="40" height="40"><spring:message
            code="head.createProduct"/> </a>
</sec:authorize>
<sec:authorize access="hasRole('PRODUCER') || hasAnyRole('ADMIN', 'PRODUCER')">
    <a title="add new product" href="/catalog/add"><img src="/images/create.png" width="40" height="40"><spring:message
            code="title.addProduct"/> </a>
</sec:authorize>

<table align="center" border="1" cellpadding="10" class="table_blur">
    <tr>
        <th>№</th>
        <th><spring:message code="productName.catalogPage"/></th>
        <sec:authorize access="isAuthenticated() && !(hasAnyRole('PRODUCER', 'ADMIN'))">
            <th><spring:message code="productType.catalogPage"/></th>
        </sec:authorize>
        <sec:authorize access="isAuthenticated() && (hasAnyRole('PRODUCER', 'ADMIN'))">
            <th><spring:message code="changeRawMaterialsForProductType.catalogPage"/></th>
        </sec:authorize>
        <sec:authorize access="!hasAnyRole('PRODUCER', 'PURCHASER')">
            <th><spring:message code="productDescription.catalogPage"/></th>
            <th><spring:message code="productPrice.catalogPage"/></th>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ADMIN', 'SELLER')">
            <th><spring:message code="productCost.basketPage"/></th>
            <th><spring:message code="productMargin.catalogPage"/></th>
        </sec:authorize>
        <sec:authorize access="isAuthenticated() && !hasRole('USER')">
            <th><spring:message code="productQuantity.catalogPage"/></th>
            <th><spring:message code="productQuantityOrdered.catalogPage"/></th>
            <sec:authorize access="isAuthenticated() && (hasAnyRole('PRODUCER', 'ADMIN'))">
                <th><spring:message code="changeAvailableProductQuantity.catalogPage"/></th>
            </sec:authorize>
            <th><spring:message code="orderedList.catalogPage"/></th>
        </sec:authorize>
        <sec:authorize access="!hasAnyRole('PURCHASER')">
            <th><spring:message code="actionWithProduct.catalogPage"/></th>
        </sec:authorize>
    </tr>
    <input type="hidden" value="${user}">
    <c:forEach var="product" items="${products}" varStatus="status">
        <tr>
        <td> ${status.index+1}</td>
        <td> ${product.name} </td>
        <sec:authorize access="isAuthenticated() && !hasAnyRole('PRODUCER', 'ADMIN')">
            <td>${product.glazesType.name}</td>
        </sec:authorize>
        <sec:authorize access="isAuthenticated() && hasAnyRole('PRODUCER', 'ADMIN')">
                <td><a href="/catalog/changeGlazeComposition/${product.glazesType.id}"
                       title="Change composition for glaze">${product.glazesType.name}</a></td>
        </sec:authorize>
        <sec:authorize access="!hasAnyRole('PRODUCER', 'PURCHASER')">
            <td>${product.description}</td>
            <td>${product.price}</td>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ADMIN', 'SELLER')">
            <td>${product.glazesType.cost}</td>
            <c:set var="margin" value="${((product.price / product.glazesType.cost)-1)*100}"/>
            <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${margin}"/></td>
        </sec:authorize>
        <sec:authorize access="isAuthenticated() && !hasRole('USER')">
            <c:if test="${product.quantity > orderedQuantity.get(product.id)}">
                <td style="background-color:#83e57d">${product.quantity}</td>
            </c:if>
            <c:if test="${product.quantity == orderedQuantity.get(product.id)}">
                <td style="background-color:#e5e37d">${product.quantity}</td>
            </c:if>
            <c:if test="${product.quantity < orderedQuantity.get(product.id)}">
                <td style="background-color:#f14a6b">${product.quantity}</td>
            </c:if>
            <td>${orderedQuantity.get(product.id)}</td>
            <sec:authorize access="isAuthenticated() && (hasAnyRole('PRODUCER', 'ADMIN'))">
                <c:if test="${product.quantity <= orderedQuantity.get(product.id)}">
                <td><a href="/catalog/changeProductQuantity/${product.id}"><spring:message code="changeAvailableProductQuantity.catalogPage"/></a></td>
                </c:if>
                <c:if test="${product.quantity > orderedQuantity.get(product.id)}">
                    <td></td>
                </c:if>
            </sec:authorize>
            <td>
                <c:if test="${orderedQuantity.get(product.id) > 0 || hasOrders==true}">
                    <a title="List of orders" href="/catalog/getOrders/${product.id}"><img
                            src="/images/orderHistory.png"><spring:message code="orderedList.catalogPage"/></a>
                </c:if>
                <c:if test="${orderedQuantity.get(product.id) <= 0 && hasOrders.equals(false)}">
                    <spring:message code="noOrders.catalogPage"></spring:message>
                </c:if>
            </td>
        </sec:authorize>
        <sec:authorize access="!hasAnyRole('PURCHASER')">
            <td>
                <sec:authorize access="hasRole('USER') || !isAuthenticated()">
                    <a title="add to basket" href="/catalog/addToBasket?idProduct=${product.id}">
                        <img id="${status.index+1}" src="/images/add_to_basket.png"
                             width="40px" height="40px"></a>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ADMIN', 'PRODUCER', 'SELLER')">
                    <c:if test="${((product.price/product.glazesType.cost)<=1.1) || (orderedQuantity.get(product.id) <= 0 || hasOrders==false)}">
                        <a title="update" href="/catalog/edit/${product.id}"><img
                                src="/images/edit_product.png" width="40px" height="40px" class="table_blur"></a>
                    </c:if>
                    <c:if test="${(orderedQuantity.get(product.id) > 0 && hasOrders==true) && (product.price/product.glazesType.cost)>1.1}">
                        <spring:message code="messageChangerProduct.catalogPage"/>
                    </c:if>
                </sec:authorize>

                <sec:authorize access="hasRole('ADMIN')">
                    <c:if test="${orderedQuantity.get(product.id) <= 0 || hasOrders==false}">
                        <a title="delete" href="/catalog/delete/${product.id}"><img src="/images/delete_product.png"
                                                                                    width="40px" height="40px"
                                                                                    class="table_blur"></a>
                    </c:if>
                    <c:if test="${orderedQuantity.get(product.id) > 0 && hasOrders==true}">
                        <spring:message code="messageDeleteProduct.catalogPage"/>
                    </c:if>
                </sec:authorize>
            </td>
        </sec:authorize>
        </tr>
    </c:forEach>
</table>
<br>
<div style="color: #a2010e; font-size: large" align="center">${msg}</div>
</body>
</html>

