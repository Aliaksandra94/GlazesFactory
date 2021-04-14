<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title><spring:message code="userAction.add"/></title>
    <%@include file="/WEB-INF/views/header/header.jsp" %>
</head>
<body>
<c:url value="/users/add" var="var"/>
<form:form method="post" action="${var}" modelAttribute="user" cssClass="transparent">
    <div class="form-inner">
        <form:label path="name"><spring:message code="name.registrationPage"/></form:label>
        <form:input path="name"/>
        <div align="center" style="color: #fa0404">${fieldNameError}</div>

        <form:label path="login"><spring:message code="login.registrationPage"/></form:label>
        <form:input path="login"/>
        <form:errors path="login" cssClass="error"/>
        <div align="center" style="color: #fa0404">${fieldLoginError}</div>

        <form:label path="password"><spring:message code="password.registrationPage"/></form:label>
        <form:input path="password"/>
        <div align="center" style="color: #fa0404">${fieldPassError}</div>

        <form:label path="discount">discount</form:label>
        <form:input path="discount"/>
        <div align="center" style="color: #fa0404">${fieldDiscError}</div>

        <form:label path="manager">manager</form:label>
        <select required name="managerId" id="managerId" onchange="myFunction()">
            <option value="">select manager</option>
            <c:forEach var="manager" items="${managers}">
                <option value="${manager.id}">${manager.name}</option>
            </c:forEach>
        </select>
        <script>
            document.getElementById("managerId").onchange = function () {
                localStorage.setItem('managerId', document.getElementById("managerId").value);
            }

            if (localStorage.getItem('managerId')) {
                document.getElementById("managerId").options[localStorage.getItem('managerId')].selected = true;
            }
        </script>
        <form:errors path="manager" cssClass="error"/>

        <form:label path="solvency">solvency</form:label>
        <select required name="solvencyId" id="solvencyId" onchange="myFunction()">
            <option value="">select solvency</option>
            <c:forEach var="solvency" items="${solvencies}">
                <option value="${solvency.id}">${solvency.name}</option>
            </c:forEach>
        </select>
        <script>
            document.getElementById("solvencyId").onchange = function () {
                localStorage.setItem('solvencyId', document.getElementById("solvencyId").value);
            }

            if (localStorage.getItem('solvencyId')) {
                document.getElementById("solvencyId").options[localStorage.getItem('solvencyId')].selected = true;
            }
        </script>

        <spring:message code="submit.registrationPage" var="submit"/>
        <input type="submit" value="${submit}">
    </div>
</form:form>
</body>
</html>
