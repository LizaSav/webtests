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
<head >
    <fmt:message key="name" var="name"/>
    <fmt:message  key="adress" var="adress"/>
    <fmt:message  key="password" var="pas"/>
    <fmt:message  key="lastname" var="lastname"/>
    <fmt:message  key="uselike" var="uselike"/>
    <fmt:message  key="save" var="save"/>
    <fmt:message  key="reg1" var="reg1"/>
    <fmt:message  key="cancel" var="cancel"/>
    <title>${reg1}</title>
</head>
<body>
<h1>Регистрация посетителей</h1>
<form action="AddUser" method="post">
    ${name}: <input type="text" name="firstName" size="10"><br>
    ${lastname}: <input type="text" name="lastName" size="10"><br>
    Email (${uselike}): <input type="text" name="user"><br>
    ${pas}: <input type="password" name="password" size="10"><br>
    ${adress}: <input type="text" name="address"><br>
    <p>
    <table>
        <tr>
            <th><small>
                <input type="submit" name="save" value="${save}">
            </small>
            <th><small>
                <input type="submit" name="cancel" value="${cancel}">
            </small>
    </table>
</form>
</body>
</html>
