package rain.quest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServletTest {

    @InjectMocks
    private GameServlet servlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @BeforeEach
    void setUp() {
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void nameStep_success() throws Exception {
        // Given
        when(session.getAttribute("step")).thenReturn(QuestStep.NAME);
        when(request.getParameter("playerName")).thenReturn("Алексей");
        when(request.getParameter("answer")).thenReturn(null);
        // When
        servlet.doPost(request, response);
        // Then
        verify(session).setAttribute("playerName", "Алексей");
        verify(session).setAttribute(eq("startTime"), anyLong());
        verify(session).setAttribute("step", QuestStep.INTRO);
        verify(response).sendRedirect("index.jsp");
    }

    @Test
    void nameStep_emptyName() throws Exception {
        // Given
        when(session.getAttribute("step")).thenReturn(QuestStep.NAME);
        when(request.getParameter("playerName")).thenReturn("");
        when(request.getParameter("answer")).thenReturn(null);
        // When
        servlet.doPost(request, response);
        // Then
        verify(session).setAttribute("message", "Введите имя!");
        verify(session).setAttribute("step", QuestStep.NAME);
        verify(response).sendRedirect("index.jsp");
    }


}

