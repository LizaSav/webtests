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
<!DOCTYPE html>
<html lang="${language}">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style>
      ul.hr {
        margin: 0; /* Обнуляем значение отступов */
        padding: 4px; /* Значение полей */
      }
      ul.hr li {
        display: inline; /* Отображать как строчный элемент */
        margin-right: 5px; /* Отступ слева */
        border: 1px solid #000; /* Рамка вокруг текста */
        padding: 3px; /* Поля вокруг текста */
      }
    </style>
  </head>
  <body>

  <fmt:message  key="login" var="login"/>
  <fmt:message  key="logout" var="logout"/>
  <fmt:message key="pastests" var="pastests"/>
  <fmt:message key="tests" var="tests"/>
  <fmt:message key="mytests" var="mytests"/>
  <fmt:message   key="main" var="main"/>
  <ul class="hr">
    <li><a href="/main.jsp">${main}</a></li>
    <li><a href="/tests/subjects.jsp">${tests}</a></li>
    <c:choose>
      <c:when test="${empty sessionScope.isLogged }">
        <li><a href="login.jsp">${login}</a></li>
      </c:when>
      <c:otherwise>
        <c:if test="${sessionScope.creator==true}">
          <li><a href="/testsCreator/mytests.jsp">${mytests}</a></li>
        </c:if>
        <li><a href="/student/mypassedtests.jsp">${pastests}</a> </li>
        <li><a href="/logout" >${logout}</a></li>

      </c:otherwise>
    </c:choose>

  </ul>
  </body>
</html>
