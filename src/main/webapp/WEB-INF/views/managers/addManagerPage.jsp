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
    <c:url value="/managers/add" var="var"/>
    <form:form method="post" action="${var}" modelAttribute="manager" cssClass="transparent">
    <div class="form-inner">
    <form:label path="name"><spring:message code="name.registrationPage"/></form:label>
    <form:input path="name"/>
    <form:errors path="name" cssClass="error"/>

    <form:label path="login"><spring:message code="login.registrationPage"/></form:label>
    <form:input path="login"/>
    <form:errors path="login" cssClass="error"/>

    <form:label path="password"><spring:message code="password.registrationPage"/></form:label>
    <form:input path="password"/>
    <form:errors path="password" cssClass="error"/>
                    <form:label path="roles">role</form:label>
                    <select required name="roleId" id="roleId" onchange="myFunction()">
                        <option value="">select role</option>
                        <c:forEach var="role" items="${roles}">
                            <option value="${role.id}">${role.name}</option>
                        </c:forEach>
                    </select>
                    <script>
                        document.getElementById("roleId").onchange = function() {
                            localStorage.setItem('roleId', document.getElementById("roleId").value);
                        }

                        if (localStorage.getItem('roleId')) {
                            document.getElementById("roleId").options[localStorage.getItem('roleId')].selected = true;
                        }
                    </script>
        <spring:message code="submit.registrationPage" var="submit"/>
        <input type="submit" value="${submit}">
    </div>
    </form:form>
</body>
</html>
