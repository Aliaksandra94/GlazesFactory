<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title><spring:message code="raw.report"/></title>
    <%@include file="/WEB-INF/views/header/header.jsp" %>
</head>
<body>
<c:url value="/userPage/reports/onRaw" var="findByProduct"/>
<form:form action="${findByProduct}">
    <input type="hidden" name="form" value="findByProduct"/>
    <label style="color: #450619"><spring:message code="findByProductId"/></label>
    <select required name="productId">
        <option value=""></option>
        <c:forEach items="${allProducts}" var="product">
            <option value="${product.id}">${product.name}</option>
        </c:forEach>
    </select>
    <button type="submit" style="border: none; background: none"><img src="/images/search.png" width="30px"
                                                                      height="30px" alt="Find" border="0px">
    </button>
</form:form>
<table border="1" cellpadding="10" class="table_blur" align="center">
    <h3 align="center"><spring:message code="products.report"/></h3>
    <tr>
        <th>№</th>
        <th><spring:message code="rawName.addRaw"/></th>
        <th><spring:message code="categoryName.addRaw"/></th>
        <th><spring:message code="productPrice.basketPage"/></th>
        <th><spring:message code="freeQuantity.addRaw"/></th>
        <th><spring:message code="quantityInProcess.addRaw"/></th>
    </tr>
    <c:forEach items="${raws}" var="raw" varStatus="status">
        <tr>
            <td>${status.index+1}</td>
            <td>${raw.key.name}</td>
            <td>${raw.key.category.name}</td>
            <td>${raw.key.price}</td>
            <td>${raw.key.quantity-raw.value}</td>
            <td>${raw.value}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
