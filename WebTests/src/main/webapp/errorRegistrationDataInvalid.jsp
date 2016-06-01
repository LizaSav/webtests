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
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <fmt:message key="errorreg" var="errorregdata"/>
    <fmt:message  key="mesdata" var="mesdata"/>
    <title>${errorregdata}</title>
</head>
<body>
<br>
<h3>${mesdata}</h3>
</body>
</html>
