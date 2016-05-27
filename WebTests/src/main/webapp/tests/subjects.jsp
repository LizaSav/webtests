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
    <fmt:message key="choose" var="choose"/>
    <fmt:message key="GEOGRAPHY" var="GEOGRAPHY"/>
    <fmt:message key="ALGEBRA" var="ALGEBRA"/>
    <fmt:message key="ENGLISH" var="ENGLISH"/>
    <fmt:message key="COMPUTER_SCIENCE" var="COMPUTER_SCIENCE"/>
    <fmt:message key="RUSSIAN" var="RUSSIAN"/>
    <fmt:message key="LITERATURE" var="LITERATURE"/>
    <fmt:message key="GEOMETRY" var="GEOMETRY"/>
    <fmt:message key="OTHER" var="OTHER"/>

    <title>${choose}</title>
</head>
<body>

<a href="/tests/testslist/index.jsp?subject=geography">${GEOGRAPHY}</a><br/>
<a href="/tests/testslist/index.jsp?subject=ALGEBRA">${ALGEBRA}</a><br/>
<a href="/tests/testslist/index.jsp?subject=ENGLISH">${ENGLISH}</a><br/>
<a href="/tests/testslist/index.jsp?subject=COMPUTER_SCIENCE">${COMPUTER_SCIENCE}</a><br/>
<a href="/tests/testslist/index.jsp?subject=RUSSIAN">${RUSSIAN}</a><br/>
<a href="/tests/testslist/index.jsp?subject=LITERATURE">${LITERATURE}</a><br/>
<a href="/tests/testslist/index.jsp?subject=GEOMETRY">${GEOMETRY}</a><br/>
<a href="/tests/testslist/index.jsp?subject=OTHER">${OTHER}</a><br/>

</body>
</html>
