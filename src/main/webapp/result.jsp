<%@ page import="rain.quest.QuestStep" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="main.css" rel="stylesheet" type="text/css">
    <title>Результат</title>
</head>
<body>

<%
    Boolean success = (Boolean) session.getAttribute("success");
%>

<%
    if (Boolean.TRUE.equals(success)) {
%>
<img src="${pageContext.request.contextPath}/resources/Image2.png"
     alt="Идёт дождь" height="300">
<%
} else {
%>
<img src="${pageContext.request.contextPath}/resources/Image.png"
     alt="Засуха" height="300">
<%
    }
%>

<%--Отображаем результат--%>
<h1>${sessionScope.result}</h1>

<%--Кнопка для начала заново--%>
<form action="hello" method="get">
    <input type="submit" value="Начать заново">
</form>

<hr>
<h3>Статистика сессии:</h3>
<p>Странник:
    <p2>${sessionScope.playerName}</p2>
<p>Время в игре: ${sessionScope.playTime}</p>

<p>ID сессии: ${pageContext.session.id}</p>

</body>
</html>
