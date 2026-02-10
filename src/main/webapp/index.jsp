<%@ page import="rain.quest.QuestStep" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Получаем шаг квеста из сессии
    QuestStep step = (QuestStep) session.getAttribute("step");

    // Если первый заход — пролог
    if (step == null) {
        step = QuestStep.NAME;
        session.setAttribute("step", step);
    }

    // Сообщение об ошибке
    String message = (String) session.getAttribute("message");
%>

<html>
<head>
    <title>Миссия: Намочить землю</title>
    <link href="main.css" rel="stylesheet" type="text/css">
</head>

<body>
<img height="300" src="${pageContext.request.contextPath}/resources/Image.png"
     alt="Лого">
<h1>Миссия: Намочить землю.</h1>

<form action="game" method="post">

    <%--     Ввод имени. Начало --%>
    <%
        if (step == QuestStep.NAME) {
    %>
    <p>Странник, назови своё имя.</p>

    <p1><input type="text" name="playerName" placeholder="Ваше имя"></p1>

    <%--     Пролог --%>
    <%
    } else if (step == QuestStep.INTRO) {
    %>

    <p>Вождь деревни смотрит на высохший колодец.</p>
    <p>— Нам нужен дождь.
        <p2><%= session.getAttribute("playerName") %>,</p2>
        поможешь?
    </p>


    <p1><input type="radio" name="answer" value="accept"> Да, я помогу<br><br></p1>
    <p1><input type="radio" name="answer" value="decline"> Нет, это не моё дело<br></p1>

    <%--     Выбор пути --%>
    <%
    } else if (step == QuestStep.PATH_CHOICE) {
    %>
    <p>Шаман говорит:</p>
    <p>— Бубен в деревне старый. Куда пойдёшь искать магические ингредиенты?</p>

    <p1><input type="radio" name="answer" value="buben"> Найти бубен<br><br></p1>
    <p1><input type="radio" name="answer" value="spell"> Прочитать заклинание<br><br></p1>
    <p1><input type="radio" name="answer" value="internet"> Погуглить «как вызвать дождь»<br></p1>

    <%--     Ритуал с бубном --%>
    <%
    } else if (step == QuestStep.DANCE) {
    %>
    <p>Ты держишь бубен в руках и готов к ритуалу.</p>
    <p>Что сделаешь?</p>

    <p1><input type="radio" name="answer" value="dance"> Танцевать с бубном<br><br></p1>
    <p1><input type="radio" name="answer" value="hit"> Сильно ударить по бубну<br></p1>

    <%--        Чтение заклинаний--%>
    <%
    } else if (step == QuestStep.SPELL) {
    %>
    <p>Ты открываешь древний свиток с заклинанием.</p>
    <p>Читаешь вслух или импровизируешь?</p>

    <p1><input type="radio" name="answer" value="read"> Читаю как написано<br><br></p1>
    <p1><input type="radio" name="answer" value="improvise"> Импровизирую<br></p1>

    <%--    Интернет --%>
    <%
    } else if (step == QuestStep.INTERNET) {
    %>
    <p>Ты достаёшь телефон и включаешь видео «Танцы с бубном за 5 минут».</p>
    <p>Что сделаешь?</p>

    <p1><input type="radio" name="answer" value="followVideo"> Повторяю движения<br><br></p1>
    <p1><input type="radio" name="answer" value="ignore"> Игнорирую видео<br></p1>

    <%--    Финал --%>
    <%
    } else if (step == QuestStep.FINAL) {
    %>
    <p>Ты готов завершить ритуал. Как поступишь?</p>

    <p1><input type="radio" name="answer" value="shout"> Кричу: «ДОЖДЬ, ПРИХОДИ!»<br><br></p1>
    <p1><input type="radio" name="answer" value="whisper"> Тихо прошептать заклинание<br></p1>

    <%
        }
    %>

    <br><br>
    <input type="submit" value="Продолжить">

</form>

<%-- Сообщение об ошибке --%>
<%
    if (message != null) {
%>
<p class="error"><%= message %>
</p>
<%
        session.removeAttribute("message");
    }
%>

</body>
</html>