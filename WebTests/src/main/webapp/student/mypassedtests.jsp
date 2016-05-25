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
<%@ taglib prefix="cr" uri="http://webtests/correctResult"%>
<%@ include file="/index.jsp" %>
<sql:query var="listMyTests" dataSource="jdbc/ProdDB">
    select title, answers, latest_update, test.id, test_date, mark FROM test_result, test WHERE student_id=<%=((model.Person)session.getAttribute("user")).getId()%>;
</sql:query>
<html lang="${language}">
<head>
    <fmt:message key="pastests" var="pastests"/>
    <fmt:message  key="testtitle" var="testtitle"/>
    <fmt:message  key="mark" var="mark"/>
    <fmt:message  key="mes1" var="mes1"/>
    <fmt:message key="passagain" var="passagain"/>

    <title>${pastests}</title>
</head>
<body>
<table border="1" cellpadding="5">
    <caption><h2>${pastests}</h2></caption>
    <tr>
        <th>${testtitle}</th>
        <th>${mark}</th>
    </tr>
        <c:forEach var="mytest" items="${listMyTests.rows}">
            <tr>
                <td>
                    ${mytest.title}
                </td>
                <td>
                    ${mytest.mark}
                </td>
                <c:if test="${!cr:correctResult(mytest.test_date,mytest.latest_update )}" >
                    <td>
                        ${mes1}<br>
                        <form action="/Test" method="get">
                            <input type="hidden" name="id" value="${mytest.id}">
                            <input type="hidden" name="rePass" value="${mytest.answers}">
                            <input type="submit" value="${passagain}">
                        </form>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
</table>
</body>
</html>
