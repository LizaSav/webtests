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
<sql:query var="listResults" dataSource="jdbc/ProdDB">
    select first_name, last_name, mark FROM test_result, person WHERE test_id=<%=Integer.parseInt((String)request.getAttribute("testId"))%> AND student_id=person.id;
</sql:query>
<html lang="${language}">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <fmt:message key="studentsresult" var="studentsresult"/>
    <fmt:message  key="mark" var="mark"/>
    <fmt:message  key="name" var="name"/>
    <fmt:message  key="lastname" var="lastname"/>

    <title>${studentsresult}</title>
</head>
<body>
<table border="1" cellpadding="5">
    <caption><h2>${studentsresult}</h2></caption>
    <tr>
        <th>${name}</th>
        <th>${lastname}</th>
        <th>${mark}</th>
    </tr>
    <c:forEach var="result" items="${listResults.rows}">
        <tr>
            <td>
                    ${result.first_name}
            </td>
            <td>
                    ${result.last_name}
            </td>
            <td>
                    ${result.mark}
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

