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
    <fmt:message key="addtest" var="addtest"/>
    <fmt:message key="subj" var="subj"/>
    <fmt:message key="GEOGRAPHY" var="GEOGRAPHY"/>
    <fmt:message key="ALGEBRA" var="ALGEBRA"/>
    <fmt:message key="ENGLISH" var="ENGLISH"/>
    <fmt:message key="COMPUTER_SCIENCE" var="COMPUTER_SCIENCE"/>
    <fmt:message key="RUSSIAN" var="RUSSIAN"/>
    <fmt:message key="LITERATURE" var="LITERATURE"/>
    <fmt:message key="GEOMETRY" var="GEOMETRY"/>
    <fmt:message key="OTHER" var="OTHER"/>
    <fmt:message key="entertitle" var="entertitle"/>
    <fmt:message key="addq" var="addq"/>
    <fmt:message key="with" var="with"/>
    <fmt:message  key="add" var="add"/>
    <title>${addtest}</title>
</head>
<body>
<form action="/testsCreator/NewTest" method="post">
    ${subj}:<br/>
        <input type="radio" name="subject" value="GEOGRAPHY" CHECKED> ${GEOGRAPHY}<br/>
        <input type="radio" name="subject" value="ALGEBRA">${ALGEBRA}<br/>
        <input type="radio" name="subject" value="ENGLISH">${ENGLISH}<br/>
        <input type="radio" name="subject" value="COMPUTER_SCIENCE">${COMPUTER_SCIENCE}<br/>
        <input type="radio" name="subject" value="RUSSIAN">${RUSSIAN}<br/>
        <input type="radio" name="subject" value="LITERATURE">${LITERATURE}<br/>
        <input type="radio" name="subject" value="GEOMETRY">${GEOMETRY}<br/>
        <input type="radio" name="subject" value="OTHER">${OTHER}<br/>

    ${entertitle}:<br/>
    <input type="text" name="title" required ><br/>
    ${addq} <input type="text" name="numberOfAnswers" required size="3" value="2"> ${with}
    <input type="submit" name="addQuestion" value="${add}">

</form>
</body>
</html>
