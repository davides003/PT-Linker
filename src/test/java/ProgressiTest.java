import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import business.progressiController;
import data.entity.Cliente;
import data.entity.Progressi;
import data.service.ProgressiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class ProgressiTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ServletContext servletContext;
    @Mock
    private PrintWriter writer;

    private progressiController servlet;

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        servlet = new progressiController();
        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    public void testInvalidPeso() throws Exception {
        // Test caso TC_1.1_1 - Peso non valido (centotre)
        HttpSession session = mock(HttpSession.class);
        Cliente cliente = mock(Cliente.class);
        when(cliente.getId()).thenReturn(123);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("utente")).thenReturn(cliente);

        // Parametri con peso non valido
        when(request.getParameter("peso")).thenReturn("centotre");
        when(request.getParameter("girovita")).thenReturn("120");
        when(request.getParameter("braccioDx")).thenReturn("50");
        when(request.getParameter("braccioSx")).thenReturn("50");
        when(request.getParameter("torace")).thenReturn("103");
        when(request.getParameter("gambaDx")).thenReturn("99");
        when(request.getParameter("gambaSx")).thenReturn("99");
        when(request.getParameter("descrizione")).thenReturn("Procede tutto al meglio.");
        when(request.getParameter("nutrizionista")).thenReturn("PR001");

        // Esegui il test e verifica l'eccezione
        assertThrows(ServletException.class, () -> servlet.doPost(request, response));
    }

    @Test
    public void testInvalidLarghezzaGirovita() throws Exception {
        // Test caso TC_1.1_2 - Larghezza girovita troppo alta
        HttpSession session = mock(HttpSession.class);
        Cliente cliente = mock(Cliente.class);
        when(cliente.getId()).thenReturn(123);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("utente")).thenReturn(cliente);

        // Parametri con larghezza girovita non valido
        when(request.getParameter("peso")).thenReturn("105");
        when(request.getParameter("girovita")).thenReturn("1300"); // Larghezza girovita non valida
        when(request.getParameter("braccioDx")).thenReturn("50");
        when(request.getParameter("braccioSx")).thenReturn("50");
        when(request.getParameter("torace")).thenReturn("103");
        when(request.getParameter("gambaDx")).thenReturn("98");
        when(request.getParameter("gambaSx")).thenReturn("99");
        when(request.getParameter("descrizione")).thenReturn("Tutto bene.");
        when(request.getParameter("nutrizionista")).thenReturn("PR001");

        // Esegui il test e verifica l'eccezione
        assertThrows(ServletException.class, () -> servlet.doPost(request, response));
    }

    @Test
    public void testInvalidCirconferenzaBraccioSx() throws Exception {
        // Test caso TC_1.1_3 - Circonferenza braccio sinistro non numerico
        HttpSession session = mock(HttpSession.class);
        Cliente cliente = mock(Cliente.class);
        when(cliente.getId()).thenReturn(123);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("utente")).thenReturn(cliente);

        // Parametri con circonferenza braccio sinistro non valido
        when(request.getParameter("peso")).thenReturn("101");
        when(request.getParameter("girovita")).thenReturn("120");
        when(request.getParameter("braccioDx")).thenReturn("52");
        when(request.getParameter("braccioSx")).thenReturn("cinquanta");  // Non numerico
        when(request.getParameter("torace")).thenReturn("cento");  // Non numerico
        when(request.getParameter("gambaDx")).thenReturn("99");
        when(request.getParameter("gambaSx")).thenReturn("99");
        when(request.getParameter("descrizione")).thenReturn("Procede tutto al meglio.");
        when(request.getParameter("nutrizionista")).thenReturn("PR001");

        // Esegui il test e verifica l'eccezione
        assertThrows(ServletException.class, () -> servlet.doPost(request, response));
    }

    @Test
    public void testInvalidCirconferenzaGambaDx() throws Exception {
        // Test caso TC_1.1_4 - Circonferenza gamba destra con caratteri speciali
        HttpSession session = mock(HttpSession.class);
        Cliente cliente = mock(Cliente.class);
        when(cliente.getId()).thenReturn(123);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("utente")).thenReturn(cliente);

        // Parametri con circonferenza gamba dx non valido
        when(request.getParameter("peso")).thenReturn("99");
        when(request.getParameter("girovita")).thenReturn("120");
        when(request.getParameter("braccioDx")).thenReturn("50");
        when(request.getParameter("braccioSx")).thenReturn("50");
        when(request.getParameter("torace")).thenReturn("103");
        when(request.getParameter("gambaDx")).thenReturn("80@"); // Carattere speciale
        when(request.getParameter("gambaSx")).thenReturn("8O"); // Carattere speciale
        when(request.getParameter("descrizione")).thenReturn("Procede tutto al meglio.");
        when(request.getParameter("nutrizionista")).thenReturn("PR001");

        // Esegui il test e verifica l'eccezione
        assertThrows(ServletException.class, () -> servlet.doPost(request, response));
    }

    @Test
    public void testInvalidFoto() throws Exception {
        // Test caso TC_1.1_6 - Formato foto non valido (Img.xml)
        HttpSession session = mock(HttpSession.class);
        Cliente cliente = mock(Cliente.class);
        when(cliente.getId()).thenReturn(123);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("utente")).thenReturn(cliente);

        // Parametri con formato foto non valido
        when(request.getParameter("peso")).thenReturn("80");
        when(request.getParameter("girovita")).thenReturn("100");
        when(request.getParameter("braccioDx")).thenReturn("50");
        when(request.getParameter("braccioSx")).thenReturn("50");
        when(request.getParameter("torace")).thenReturn("101");
        when(request.getParameter("gambaDx")).thenReturn("99");
        when(request.getParameter("gambaSx")).thenReturn("99");
        when(request.getParameter("descrizione")).thenReturn("Problema al ginocchio.");

        // Esegui il test e verifica l'eccezione
        assertThrows(ServletException.class, () -> servlet.doPost(request, response));
    }

    @Test
    public void testValidInput() throws Exception {
        ProgressiService ps = mock(ProgressiService.class);
        HttpSession session = mock(HttpSession.class);
        Cliente cliente = mock(Cliente.class);
        when(cliente.getId()).thenReturn(1);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("utente")).thenReturn(cliente);

        // Parametri validi
        when(request.getParameter("peso")).thenReturn("80");
        when(request.getParameter("girovita")).thenReturn("100");
        when(request.getParameter("braccioDx")).thenReturn("50");
        when(request.getParameter("braccioSx")).thenReturn("50");
        when(request.getParameter("torace")).thenReturn("101");
        when(request.getParameter("gambaDx")).thenReturn("99");
        when(request.getParameter("gambaSx")).thenReturn("99");
        when(request.getParameter("descrizione")).thenReturn("Problema al ginocchio.");
        when(request.getParameter("nutrizionista")).thenReturn("2");
        //when(request.getParameter("foto")).thenReturn("Img.jpg");

        // Esegui il test e verifica che l'inserimento vada a buon fine
        servlet.doPost(request, response);

        // Verifica che il mock venga effettivamente invocato
        Mockito.verify(ps).registraProgressi(any(data.entity.Progressi.class));
    }
}