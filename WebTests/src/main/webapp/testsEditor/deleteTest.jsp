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
</head>
<body>
<fmt:message key="subdelete" var="subdelete"/>
<fmt:message key="yes" var="yes"/>
<fmt:message key="no" var="no"/>

${subdelete}:<br/>
<form action="/testsEditor/DeleteTest" method="post">
    <input type="hidden" name="testId" value="<%=request.getAttribute("testId")%>">
    <input type="submit" name="yes" value="${yes}"> <input type="submit" name="no" value="${no}">
</form>
</body>
</html>
