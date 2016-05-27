<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <fmt:message key="user" var="user"/>
    <fmt:message key="adress" var="adress"/>
    <fmt:message  key="enter" var="enter"/>
    <fmt:message  key="regdone" var="regdone"/>
    <title>${regdone}</title>
</head>
<body>

<h1>${regdone}</h1>
<jsp:useBean id="user" class="model.Person" scope="session"/>
${user}: <%= user.getFirstName()%> <%= user.getLastName()%><br>
Email: <%= user.getEmail()%><br>
${adress}: <%= user.getAddress()%><br>
<a href="login.jsp">${enter}</a>
</body>
</html>
