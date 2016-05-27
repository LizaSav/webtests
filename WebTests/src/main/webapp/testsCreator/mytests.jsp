<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql" %>

<c:set var="language"
       value="${not empty param.language ? param.language
                                         : not empty language ? language
                                                              : pageContext.request.locale}"
       scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle  basename="localizator.locale" />
<%@ include file="/index.jsp" %>

<sql:query var="listMyTests" dataSource="jdbc/ProdDB">
    select title, id FROM Test WHERE creator_id=<%=((model.Person)session.getAttribute("user")).getId()%>;
</sql:query>

<html lang="${language}">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <fmt:message  key="testslist" var="testslist"/>
    <fmt:message  key="testtitle" var="testtitle"/>
    <fmt:message  key="edit" var="edit"/>
    <fmt:message  key="delete" var="delete"/>
    <fmt:message  key="createtest" var="createtest"/>
    <fmt:message  key="studentsresult" var="studentsresult"/>
    <title>${testslist}</title>
</head>
<body>
<table border="1" cellpadding="5">
    <caption><h2>${testslist}</h2></caption>
    <tr>
        <th>${testtitle}</th>
    </tr>
    <form action="/testsCreator/MyTestsController" method="post">
    <c:forEach var="mytest" items="${listMyTests.rows}">
    <tr>
        <td>
            ${mytest.title}
        </td>
        <td>
            <input type="submit" name="edit${mytest.id}"  value="${edit}">
        </td>
        <td>
            <input type="submit" name="delete${mytest.id}" value="${delete}">
        </td>
        <td>
            <input type="submit" name="studentsresult${mytest.id}" value="${studentsresult}">
        </td>
    </tr>
    </c:forEach>
    <input type="submit" name="createNew" value="${createtest}">
    </form>
    </table>
</body>
</html>
