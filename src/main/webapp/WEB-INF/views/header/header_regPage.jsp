<link href="<c:url value='/css/style.css'/>" rel="stylesheet">
<link href="<c:url value="/css/form.css"/>" rel="stylesheet">
<link href="<c:url value="/css/icon.css"/>" rel="stylesheet">
<link href="<c:url value="/css/link.css"/>" rel="stylesheet">
<link href="<c:url value="/css/sale.css"/>" rel="stylesheet">
<link href="<c:url value="/css/stock.css"/>" rel="stylesheet">
<link href="<c:url value="/css/table.css"/>" rel="stylesheet">
<link href="<c:url value="/css/blocks.css"/>" rel="stylesheet">
<link href="<c:url value="/css/header.css"/>" rel="stylesheet">
<link href="<c:url value="/css/test.css"/>" rel="stylesheet">
<h2 align="right">
    <a title="English" href="?lang=en">
        <img src="/images/UK.jpg" width="30px" height="30px" class="round">
    </a>
    <a title="Russian" href="?lang=ru">
        <img src="/images/RU.png" width="30px" height="30px" class="round">
    </a> </h2>
<nav class="top-menu">
    <ul class="menu-main">
        <li class="left-item"><a title="Back to the main page" href="/"><img src="/images/logo.png" width="120" height="120"></a>
        </li>
        <sec:authorize access="!isAuthenticated() || hasRole('USER')">
            </li>
            <li class="right-item">
                <a title="Basket" href="/basket">
                    | <img src="/images/basket.png" class="shopping-cart"><spring:message
                        code="basket.startPage"/>
                </a></li></sec:authorize>

            <li class="right-item">
                <a title="Login" href="/login">
                    | <img src="/images/enter.png" width="30px" height="30px"><spring:message
                        code="authorization.startPage"/>
                </a></li>
    </ul>
</nav>