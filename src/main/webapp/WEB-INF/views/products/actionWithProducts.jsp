<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Title</title>
    <%@include file="/WEB-INF/views/header/header.jsp" %>
</head>
<body>
<div class="post-wrap">
    <div class="post-item">
        <div class="item-content">
            <img src="/images/create.png" width="70px" height="70px">
            <div class="item-body">
                <h3><spring:message code="title.createProduct"/></h3>
            </div>
            <div class="item-footer">
                <a href="/userPage/actionWithProduct/addNewProduct" class="link"><span><img src="/images/arrow.png" width="30px"
                                                                            height="30px"></span></a>
            </div>
        </div>
    </div>
    <div class="post-item">
        <div class="item-content">
            <img src="/images/edit_product.png" width="70px" height="70px">
            <div class="item-body">
                <h3><spring:message code="title.editProduct"/></h3>
            </div>
            <div class="item-footer">
                <a href="/userPage/actionWithProduct/updateProduct" class="link"><span><img src="/images/arrow.png" width="30px"
                                                                                            height="30px"></span></a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
