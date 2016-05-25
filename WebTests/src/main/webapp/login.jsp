<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
       value="${not empty param.language ? param.language
                                         : not empty language ? language
                                                              : pageContext.request.locale}"
       scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle  basename="localizator.locale" />
<%@ include file="/index.jsp" %>
<html lang="${language}">
<head>
    <fmt:message  key="user" var="user"/>
    <fmt:message  key="password" var="password"/>
    <fmt:message  key="enter" var="enter"/>
    <fmt:message  key="reg" var="reg"/>
    <title>${enter}</title>
</head>
<body>

<br>
<form action="Registration" method="post">
    ${user}: <input type="text" name="user" size="10"><br>
    ${password}: <input type="password" name="password" size="10"><br>
    <p>
    <table>
        <tr>
            <th><small>
                <input type="submit" name="login" value="${enter}">
            </small>
            <th><small>
                <input type="submit" name="registration" value="${reg}">
            </small>
    </table>
</form>
<br>
</body>
</html>
