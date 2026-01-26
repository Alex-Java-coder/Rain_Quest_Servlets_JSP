package rain.quest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/game")
public class GameServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();

        QuestStep step = (QuestStep) session.getAttribute("step");
        if (step == null) step = QuestStep.NAME;

        String answer = req.getParameter("answer");

        session.removeAttribute("message");

        switch (step) {

            case NAME:
                String playerName = req.getParameter("playerName");

                if (playerName != null && !playerName.trim().isEmpty()) {
                    session.setAttribute("playerName", playerName.trim());
                    session.setAttribute("startTime", System.currentTimeMillis());
                    step = QuestStep.INTRO;
                } else {
                    session.setAttribute("message", "Введите имя!");
                }
                break;

            case INTRO:
                if ("accept".equals(answer)) {
                    step = QuestStep.PATH_CHOICE;
                } else if ("decline".equals(answer)) {
                    session.setAttribute("playTime", getPlayTime(session));
                    session.setAttribute("success", false);
                    session.setAttribute("result", "Вы отказались помочь. Дождь не пришёл.");
                    resp.sendRedirect("result.jsp");
                    return;
                } else {
                    session.setAttribute("message", "Выберите один из вариантов!");
                }
                break;

            case PATH_CHOICE:
                if ("buben".equals(answer)) {
                    step = QuestStep.DANCE;
                } else if ("spell".equals(answer)) {
                    step = QuestStep.SPELL;
                } else if ("internet".equals(answer)) {
                    step = QuestStep.INTERNET;
                } else {
                    session.setAttribute("message", "Выберите путь!");
                }
                break;

            case DANCE:
                if ("dance".equals(answer)) {
                    session.setAttribute("playTime", getPlayTime(session));
                    step = QuestStep.FINAL;
                } else if ("hit".equals(answer)) {
                    session.setAttribute("playTime", getPlayTime(session));
                    session.setAttribute("success", false);
                    session.setAttribute("result", "Вы слишком сильно ударили бубен. Он треснул. Дождя не будет.");
                    resp.sendRedirect("result.jsp");
                    return;
                } else {
                    session.setAttribute("message", "Выберите действие!");
                }
                break;

            case SPELL:
                if ("read".equals(answer)) {
                    session.setAttribute("playTime", getPlayTime(session));
                    session.setAttribute("success", false);
                    session.setAttribute("result", "Вы прочитали заклинание вслух. Но с неба падает град! Дождь не тот...");
                    resp.sendRedirect("result.jsp");
                    return;
                } else if ("improvise".equals(answer)) {
                    session.setAttribute("playTime", getPlayTime(session));
                    step = QuestStep.FINAL;
                } else {
                    session.setAttribute("message", "Выберите действие!");
                }
                break;

            case INTERNET:
                if ("followVideo".equals(answer)) {
                    session.setAttribute("playTime", getPlayTime(session));
                    step = QuestStep.FINAL;
                } else if ("ignore".equals(answer)) {
                    session.setAttribute("playTime", getPlayTime(session));
                    session.setAttribute("success", false);
                    session.setAttribute("result", "Вы проигнорировали видео. Дождь не пришёл.");
                    resp.sendRedirect("result.jsp");
                    return;
                } else {
                    session.setAttribute("message", "Выберите действие!");
                }
                break;

            case FINAL:
                if ("shout".equals(answer)) {
                    session.setAttribute("playTime", getPlayTime(session));
                    session.setAttribute("success", true);
                    session.setAttribute("result", "Вы закричали: «ДОЖДЬ, ПРИХОДИ!»\nИ дождь полил деревню! Миссия выполнена!");
                } else if ("whisper".equals(answer)) {
                    session.setAttribute("playTime", getPlayTime(session));
                    session.setAttribute("success", false);
                    session.setAttribute("result", "Вы прошептали заклинание… Но дождь стесняется приходить. Почти получилось.");
                } else {
                    session.setAttribute("message", "Выберите действие!");
                    break;
                }

                resp.sendRedirect("result.jsp");
                return;
        }

        session.setAttribute("step", step);
        resp.sendRedirect("index.jsp");
    }

    // Подсчет времени
    private String getPlayTime(HttpSession session) {
        long startTime = (long) session.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long durationMillis = endTime - startTime;

        // Переводим в секунды
        long seconds = durationMillis / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;

        return minutes + " мин " + seconds + " сек";
    }

}