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
    public void testInvalidlarghezzagir() throws Exception {

        HttpSession session = mock(HttpSession.class);
        Cliente cliente = mock(Cliente.class);
        when(cliente.getId()).thenReturn(123);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("utente")).thenReturn(cliente);

        when(request.getParameter("peso")).thenReturn("103");
        when(request.getParameter("girovita")).thenReturn("1300");
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
    public void testInvalidlarghezzabrdx() throws Exception {

        HttpSession session = mock(HttpSession.class);
        Cliente cliente = mock(Cliente.class);
        when(cliente.getId()).thenReturn(123);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("utente")).thenReturn(cliente);

        when(request.getParameter("peso")).thenReturn("103");
        when(request.getParameter("girovita")).thenReturn("130");
        when(request.getParameter("braccioDx")).thenReturn("cinquanta");
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
    public void testInvalidlarghezzabrsx() throws Exception {

        HttpSession session = mock(HttpSession.class);
        Cliente cliente = mock(Cliente.class);
        when(cliente.getId()).thenReturn(123);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("utente")).thenReturn(cliente);

        when(request.getParameter("peso")).thenReturn("103");
        when(request.getParameter("girovita")).thenReturn("130");
        when(request.getParameter("braccioDx")).thenReturn("50");
        when(request.getParameter("braccioSx")).thenReturn("cinquanta");
        when(request.getParameter("torace")).thenReturn("103");
        when(request.getParameter("gambaDx")).thenReturn("99");
        when(request.getParameter("gambaSx")).thenReturn("99");
        when(request.getParameter("descrizione")).thenReturn("Procede tutto al meglio.");
        when(request.getParameter("nutrizionista")).thenReturn("PR001");

        // Esegui il test e verifica l'eccezione
        assertThrows(ServletException.class, () -> servlet.doPost(request, response));
    }
    @Test
    public void testInvalidcirctorace() throws Exception {

        HttpSession session = mock(HttpSession.class);
        Cliente cliente = mock(Cliente.class);
        when(cliente.getId()).thenReturn(123);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("utente")).thenReturn(cliente);

        when(request.getParameter("peso")).thenReturn("103");
        when(request.getParameter("girovita")).thenReturn("130");
        when(request.getParameter("braccioDx")).thenReturn("50");
        when(request.getParameter("braccioSx")).thenReturn("50");
        when(request.getParameter("torace")).thenReturn("1033");
        when(request.getParameter("gambaDx")).thenReturn("99");
        when(request.getParameter("gambaSx")).thenReturn("99");
        when(request.getParameter("descrizione")).thenReturn("Procede tutto al meglio.");
        when(request.getParameter("nutrizionista")).thenReturn("PR001");

        // Esegui il test e verifica l'eccezione
        assertThrows(ServletException.class, () -> servlet.doPost(request, response));
    }
    @Test
    public void testInvalidlarghezzagmbdx() throws Exception {

        HttpSession session = mock(HttpSession.class);
        Cliente cliente = mock(Cliente.class);
        when(cliente.getId()).thenReturn(123);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("utente")).thenReturn(cliente);

        when(request.getParameter("peso")).thenReturn("103");
        when(request.getParameter("girovita")).thenReturn("130");
        when(request.getParameter("braccioDx")).thenReturn("50");
        when(request.getParameter("braccioSx")).thenReturn("50");
        when(request.getParameter("torace")).thenReturn("103");
        when(request.getParameter("gambaDx")).thenReturn("9900");
        when(request.getParameter("gambaSx")).thenReturn("99");
        when(request.getParameter("descrizione")).thenReturn("Procede tutto al meglio.");
        when(request.getParameter("nutrizionista")).thenReturn("PR001");

        // Esegui il test e verifica l'eccezione
        assertThrows(ServletException.class, () -> servlet.doPost(request, response));
    }
    @Test
    public void testInvalidlarghezzagmbsx() throws Exception {

        HttpSession session = mock(HttpSession.class);
        Cliente cliente = mock(Cliente.class);
        when(cliente.getId()).thenReturn(123);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("utente")).thenReturn(cliente);

        when(request.getParameter("peso")).thenReturn("103");
        when(request.getParameter("girovita")).thenReturn("130");
        when(request.getParameter("braccioDx")).thenReturn("50");
        when(request.getParameter("braccioSx")).thenReturn("50");
        when(request.getParameter("torace")).thenReturn("103");
        when(request.getParameter("gambaDx")).thenReturn("99");
        when(request.getParameter("gambaSx")).thenReturn("9900");
        when(request.getParameter("descrizione")).thenReturn("Procede tutto al meglio.");
        when(request.getParameter("nutrizionista")).thenReturn("PR001");

        // Esegui il test e verifica l'eccezione
        assertThrows(ServletException.class, () -> servlet.doPost(request, response));
    }

    @Test
    public void testInvaliddescr() throws Exception {

        HttpSession session = mock(HttpSession.class);
        Cliente cliente = mock(Cliente.class);
        when(cliente.getId()).thenReturn(123);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("utente")).thenReturn(cliente);

        when(request.getParameter("peso")).thenReturn("103");
        when(request.getParameter("girovita")).thenReturn("130");
        when(request.getParameter("braccioDx")).thenReturn("50");
        when(request.getParameter("braccioSx")).thenReturn("50");
        when(request.getParameter("torace")).thenReturn("103");
        when(request.getParameter("gambaDx")).thenReturn("9900");
        when(request.getParameter("gambaSx")).thenReturn("99");
        when(request.getParameter("descrizione")).thenReturn("Sono ormai due mesi che ho iniziato questo percorso con il mio personal trainer e devo dire che sto notando dei cambiamenti importanti. All’inizio è stato difficile, soprattutto riprendere un ritmo di allenamento costante dopo anni di sedentarietà, ma con il tempo ho acquisito più forza e resistenza. Ho perso circa 3 kg e sento i muscoli più tonici, soprattutto nelle gambe e nell’addome, che erano i miei punti critici. Anche la mia postura è migliorata, grazie agli esercizi per la schiena e il core.\n" +
                "        \"Mi sento più energico durante la giornata e ho meno dolori alla schiena, che prima mi affliggevano a causa del lavoro sedentario. Ho ancora qualche difficoltà con l’alimentazione, perché non sempre riesco a seguire un regime equilibrato, soprattutto nei momenti di stress, ma sto cercando di migliorare anche su questo aspetto. Il mio personal trainer mi ha aiutato a trovare la giusta motivazione, e ora riesco a spingermi oltre i miei limiti senza arrendermi facilmente.\n" +
                "        \"Nelle ultime settimane gli allenamenti sono diventati più intensi, ma sto imparando a gestire meglio la fatica e a godermi il processo. Il mio obiettivo è continuare su questa strada, migliorando sia fisicamente che mentalmente. Sono soddisfatto dei risultati ottenuti finora e determinato a raggiungere il mio peso forma e una condizione fisica ottimale.\n" +
                "\n");
        when(request.getParameter("nutrizionista")).thenReturn("PR001");

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
        // Inietta il mock nella servlet
        servlet.setProgressiService(ps);
        // Esegui la servlet
        servlet.doPost(request, response);

        // Verifica che il mock venga effettivamente invocato
        Mockito.verify(ps).registraProgressi(any(data.entity.Progressi.class));
        // Verifica che il metodo venga chiamato
        verify(ps, times(1)).registraProgressi(any(Progressi.class));
    }


}