import business.RegistrazioneServlet;
import data.entity.Cliente;
import data.service.RegistrazioneService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private HttpServletRequest request;  // Mock del HttpServletRequest

    @Mock
    private RegistrazioneService regServiceMock;  // Mock del RegistrazioneService

    @InjectMocks
    private RegistrazioneServlet clienteService;  // La classe da testare

    @Test
    public void testSalvaCliente_RegistraConSuccesso() {
        // Configura il comportamento del mock HttpServletRequest
        when(request.getParameter("altezza")).thenReturn("1.75");
        when(request.getParameter("peso")).thenReturn("70");
        when(request.getParameter("girovita")).thenReturn("80");
        when(request.getParameter("circonferenza-braccio-destro")).thenReturn("30");
        when(request.getParameter("circonferenza-braccio-sinistro")).thenReturn("30");
        when(request.getParameter("circonferenza-gamba-destra")).thenReturn("40");
        when(request.getParameter("circonferenza-gamba-sinistra")).thenReturn("40");
        when(request.getParameter("circonferenza-torace")).thenReturn("90");
        when(request.getParameter("nutrizionista")).thenReturn("2");

        // Crea l'oggetto Cliente che si vuole salvare
        Cliente cliente = new Cliente("Gianmarco", "Rossi", "gmrossi", "gmrossi@example.com", "hashedPass", "1990-01-01", 1, 1.75f, 70f, 80f, 30f, 30f, 90f, 40f, 40f);

        // Configura il comportamento del servizio di registrazione per restituire true (successo)
        // Usa doReturn().when() per evitare problemi con la corrispondenza degli oggetti
        doReturn(true).when(regServiceMock).registraCliente(any(Cliente.class));

        // Esegui la funzione salvaCliente
        clienteService.salvaCliente(request, 1, "Gianmarco", "Rossi", "gmrossi", "gmrossi@example.com", "hashedPass", "1990-01-01",
                1.75f, 70f, 80f, 30f, 30f, 90f, 40f, 40f);

        // Verifica che il metodo registraCliente sia stato chiamato correttamente
        verify(regServiceMock).registraCliente(any(Cliente.class));  // Usa any(Cliente.class) per ignorare la corrispondenza esatta
        verify(regServiceMock).abbinaCliente("2");
    }
}