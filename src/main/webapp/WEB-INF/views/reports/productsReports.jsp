<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<head>
    <title><spring:message code="products.report"/></title>
    <%@include file="/WEB-INF/views/header/header.jsp" %>
</head>
<body>
<p align="right">
    <a class="button-2" href="/userPage/reports/onProducts">
        <span><spring:message code="showProduct.report"/></span>
    </a></p>

<c:url value="/userPage/reports/onProducts" var="find"/>
<form:form action="${find}">
    <input type="hidden" name="form" value="findProduct"/>
    <label style="color: #450619"><spring:message code="findProduct"/></label>
    <select required name="productId">
        <option value=""></option>
        <c:forEach var="product" items="${products}">
            <option value="${product.id}">${product.name}</option>
        </c:forEach>
    </select>
    <button type="submit" style="border: none; background: none"><img src="/images/search.png" width="30px"
                                                                      height="30px" alt="Find" border="0px">
    </button>
</form:form>
<c:url value="/userPage/reports/onProducts" var="findByType"/>
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
<%--<c:url value="/userPage/reports/onProducts" var="sortReadyQuantity"/>--%>
<%--<form:form action="${sortReadyQuantity}">--%>
<%--    <input type="hidden" name="form" value="sortReadyQuantity"/>--%>
<%--    <label style="color: #450619"><spring:message code="sortReadyQuantity"/></label>--%>
<%--    <select required name="sortById">--%>
<%--        <option value=""></option>--%>
<%--        <option value="1"><spring:message code="ascending"/></option>--%>
<%--        <option value="2"><spring:message code="descending"/></option>--%>
<%--    </select>--%>
<%--    <button type="submit" style="border: none; background: none"><img src="/images/search.png" width="30px"--%>
<%--                                                                      height="30px" alt="Find" border="0px">--%>
<%--    </button>--%>
<%--</form:form>--%>
<%--<c:url value="/userPage/reports/onProducts" var="sortOrderedQuantity"/>--%>
<%--<form:form action="${sortOrderedQuantity}">--%>
<%--    <input type="hidden" name="form" value="sortOrderedQuantity"/>--%>
<%--    <label style="color: #450619"><spring:message code="sortOrderedQuantity"/></label>--%>
<%--    <select required name="sortById">--%>
<%--        <option value=""></option>--%>
<%--        <option value="1"><spring:message code="ascending"/></option>--%>
<%--        <option value="2"><spring:message code="descending"/></option>--%>
<%--    </select>--%>
<%--    <button type="submit" style="border: none; background: none"><img src="/images/search.png" width="30px"--%>
<%--                                                                      height="30px" alt="Find" border="0px">--%>
<%--    </button>--%>
<%--</form:form>--%>

<%--<c:url value="/userPage/reports/onProducts" var="sortOrderedAmount"/>--%>
<%--<form:form action="${sortOrderedAmount}">--%>
<%--    <input type="hidden" name="form" value="sortOrderedAmount"/>--%>
<%--    <label style="color: #450619"><spring:message code="sortOrderedAmount"/></label>--%>
<%--    <select required name="sortById">--%>
<%--        <option value=""></option>--%>
<%--        <option value="1"><spring:message code="ascending"/></option>--%>
<%--        <option value="2"><spring:message code="descending"/></option>--%>
<%--    </select>--%>
<%--    <button type="submit" style="border: none; background: none"><img src="/images/search.png" width="30px"--%>
<%--                                                                      height="30px" alt="Find" border="0px">--%>
<%--    </button>--%>
<%--</form:form>--%>

<%--<c:url value="/userPage/reports/onProducts" var="sortReadyAmount"/>--%>
<%--<form:form action="${sortReadyAmount}">--%>
<%--    <input type="hidden" name="form" value="sortReadyAmount"/>--%>
<%--    <label style="color: #450619"><spring:message code="sortReadyAmount"/></label>--%>
<%--    <select required name="sortById">--%>
<%--        <option value=""></option>--%>
<%--        <option value="1"><spring:message code="ascending"/></option>--%>
<%--        <option value="2"><spring:message code="descending"/></option>--%>
<%--    </select>--%>
<%--    <button type="submit" style="border: none; background: none"><img src="/images/search.png" width="30px"--%>
<%--                                                                      height="30px" alt="Find" border="0px">--%>
<%--    </button>--%>
<%--</form:form>--%>


<table border="1" cellpadding="10" class="table_blur" align="center">
    <h3 align="center"><spring:message code="products.report"/></h3>
    <thead>
    <tr>
        <th>№</th>
        <th><spring:message code="productName.catalogPage"/></th>
        <th><spring:message code="productType.addProduct"/></th>
        <th><spring:message code="productPrice.addProduct"/></th>
        <th><spring:message code="freeQuantity.report"/></th>
        <th></th>
        <th><spring:message code="productQuantity.basketPage"/></th>
        <th><spring:message code="amountWithUserDiscount.addProduct"/></th>
        <th><spring:message code="productMargin.catalogPage"/></th>
        <th><spring:message code="income"/></th>
    </tr>
    </thead>
    <c:forEach items="${productsWithAmountAndQuantities}" var="product" varStatus="status">
        <tr>
            <td rowspan="2">${status.index+1}</td>
            <td rowspan="2">${product.key.name}</td>
            <td rowspan="2">${product.key.glazesType.name}</td>
            <td rowspan="2">${product.key.price}</td>
            <td rowspan="2">${product.key.quantity}</td>
            <td>Ready</td>
            <td>${product.value.get(0)}</td>
            <td>${product.value.get(1)}</td>
            <c:set var="marginAvail" value="${((product.value.get(1) / product.key.cost)-1)*100}"/>
            <c:if test="${marginAvail == -100}">
                <td>0</td>
            </c:if>
            <c:if test="${marginAvail != -100}">
                <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${marginAvail}"/></td>
            </c:if>
            <c:set var="incomeAvail" value="${product.value.get(0)*(product.value.get(1)/product.key.cost/100)}"/>
            <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${incomeAvail}"/></td>
    </tr>
        <tr>
            <td>In progress</td>
            <td>${product.value.get(2)}</td>
            <td>${product.value.get(3)}</td>
            <c:set var="marginOrdered" value="${((product.value.get(3)/product.key.cost)-1)*100}"/>
            <c:if test="${marginOrdered == -100}">
                <td>0</td>
            </c:if>
            <c:if test="${marginOrdered != -100}">
                <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${marginOrdered}"/></td>
            </c:if>
            <c:set var="incomeOrdered" value="${product.value.get(2)*(marginOrdered/100)}"/>
            <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${incomeOrdered}"/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
