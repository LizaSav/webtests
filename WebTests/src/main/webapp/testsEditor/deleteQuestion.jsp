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
</head>
<body>
<fmt:message key="subdeleteq" var="subdeleteq"/>
<fmt:message  key="yes" var="yes"/>
<fmt:message key="no" var="no"/>
${subdeleteq}:<br/>
<%=request.getAttribute("question")%><br/>
<form action="/testsEditor/DeleteQuestion" method="post">
    <input type="hidden" name="questionNumber" value="<%=request.getAttribute("questionNumber")%>">
    <input type="hidden" name="testId" value="<%=request.getAttribute("testId")%>">
    <input type="submit" name="yes" value="${yes}"> <input type="submit" name="no" value="${no}">
</form>
</body>
</html>
