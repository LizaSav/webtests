<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql" %>
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
<sql:query var="listTests" dataSource="jdbc/ProdDB">
    select title, id FROM Test WHERE subject='<%=request.getParameter("subject")%>';
</sql:query>
<html lang="${language}">
<head>
    <fmt:message  key="testslistsubj" var="testslistsubj"/>
    <fmt:message  key="testtitle" var="testtitle"/>
    <title>${testslistsubj}</title>
</head>
<body>

<table border="1" cellpadding="5">
    <caption><h2>${testslistsubj} <%=request.getParameter("subject")%></h2></caption>
    <tr>
        <th>${testtitle}</th>
    </tr>
   <c:forEach var="test" items="${listTests.rows}">
        <tr>
            <td>
                <a href="/Test?id=<c:out value="${test.id}" />"><c:out value="${test.title}" /></a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
