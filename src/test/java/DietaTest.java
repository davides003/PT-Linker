import business.DietaController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GestioneDietaServletTest {

    @InjectMocks
    private DietaController servlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Test
    void testCreazioneDietaLunga_Fallimento() throws ServletException, IOException {
        // Simula una richiesta con input lungo
        when(request.getParameter("dieta")).thenReturn(
                "Colazione equilibrata: una tazza di latte parzialmente scremato o una bevanda vegetale senza zuccheri aggiunti, " +
                        "accompagnata da 40 g di fiocchi d’avena con un cucchiaino di miele e una manciata di frutta secca (noci, mandorle o nocciole). " +
                        "Aggiungere un frutto fresco di stagione, come una mela o una banana, per un apporto di fibre e vitamine. " +
                        "Per chi preferisce il salato, una fetta di pane integrale con avocado e un uovo sodo rappresenta un’ottima alternativa ricca di proteine e grassi sani."
        );

        // Simuliamo la risposta HTTP
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // CHIAMATA ESPLICITA AL METODO doPost
        servlet.doPost(request, response);

        // Verifica la risposta attesa
        writer.flush();
        String servletResponse = stringWriter.toString();
        assertTrue(servletResponse.contains("Inserimento non valido"), "Messaggio di errore atteso non trovato");
    }

    @Test
    void testCreazioneDietaCorta_Successo() throws ServletException, IOException {
        // Simula una richiesta con input breve
        when(request.getParameter("dieta")).thenReturn("Yogurt greco con miele e noci.");

        // Simuliamo la risposta HTTP
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // CHIAMATA ESPLICITA AL METODO doPost
        servlet.doPost(request, response);

        // Verifica la risposta attesa
        writer.flush();
        String servletResponse = stringWriter.toString();
        assertTrue(servletResponse.contains("Inserimento avvenuto con successo"), "Messaggio di successo atteso non trovato");
    }
}