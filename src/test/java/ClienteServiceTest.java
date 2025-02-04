import business.RegistrazioneServlet;
import data.entity.Cliente;
import data.service.RegistrazioneService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private HttpServletRequest request;  // Mock del HttpServletRequest

    @Mock
    private HttpServletResponse response;  // Mock del HttpServletRequest

    @Mock
    private RegistrazioneService regServiceMock;  // Mock del RegistrazioneService

    @InjectMocks
    private RegistrazioneServlet clienteService;  // La classe da testare

    @Test
    public void testSalvaCliente_UsernameLungo() throws ServletException, IOException {
        // Configura i parametri corretti per il test valido
        when(request.getParameter("username")).thenReturn("FitnessWarrior_Transformation2024");
        when(request.getParameter("email")).thenReturn("marco@gmail.com");
        when(request.getParameter("password")).thenReturn("12345678");
        when(request.getParameter("confirm-password")).thenReturn("12345678");
        when(request.getParameter("name")).thenReturn("marco");
        when(request.getParameter("surname")).thenReturn("rossi");
        when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        when(request.getParameter("role")).thenReturn("cliente");
        when(request.getParameter("altezza")).thenReturn("100");
        when(request.getParameter("peso")).thenReturn("88");
        when(request.getParameter("girovita")).thenReturn("55");
        when(request.getParameter("circonferenza-braccio-destro")).thenReturn("55");
        when(request.getParameter("circonferenza-braccio-sinistro")).thenReturn("100");
        when(request.getParameter("circonferenza-gamba-destra")).thenReturn("90");
        when(request.getParameter("circonferenza-gamba-sinistra")).thenReturn("90");
        when(request.getParameter("circonferenza-torace")).thenReturn("2");
        when(request.getParameter("nutrizionista")).thenReturn("nutrizionista");

        // Crea oggetto Cliente
        Cliente cliente = new Cliente("marco", "rossi", "FitnessWarrior_Transformation2024", "marco@gmail.com", "12345678", "01/01/2000", 1,
                Float.parseFloat("100"), Float.parseFloat("88"), Float.parseFloat("55"), Float.parseFloat("55"), Float.parseFloat("100"),
                Float.parseFloat("90"), Float.parseFloat("90"), Float.parseFloat("2"));

        // Configura il comportamento del servizio
        doReturn(false).when(regServiceMock).registraCliente(any(Cliente.class));

        // Esegui il metodo
        clienteService.doPost(request, response);

        // Verifica
        verify(regServiceMock).registraCliente(any(Cliente.class));
    }

    @Test
    public void testSalvaCliente_EmailErrataPasswordCorta() throws ServletException, IOException {
        // Configura i parametri con email errata e password corta
        when(request.getParameter("username")).thenReturn("FitnessWarrior");
        when(request.getParameter("email")).thenReturn("Marco.gmail.com");
        when(request.getParameter("password")).thenReturn("123456");
        when(request.getParameter("confirm-password")).thenReturn("123456");
        when(request.getParameter("name")).thenReturn("marco");
        when(request.getParameter("surname")).thenReturn("rossi");
        when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        when(request.getParameter("role")).thenReturn("cliente");
        when(request.getParameter("altezza")).thenReturn("100");
        when(request.getParameter("peso")).thenReturn("88");
        when(request.getParameter("girovita")).thenReturn("55");
        when(request.getParameter("circonferenza-braccio-destro")).thenReturn("55");
        when(request.getParameter("circonferenza-braccio-sinistro")).thenReturn("100");
        when(request.getParameter("circonferenza-gamba-destra")).thenReturn("90");
        when(request.getParameter("circonferenza-gamba-sinistra")).thenReturn("90");
        when(request.getParameter("circonferenza-torace")).thenReturn("2");
        when(request.getParameter("nutrizionista")).thenReturn("nutrizionista");

        // Configura il comportamento del servizio
        doReturn(false).when(regServiceMock).registraCliente(any(Cliente.class));

        // Esegui il metodo
        clienteService.doPost(request, response);

        // Verifica
        verify(regServiceMock, times(1)).registraCliente(any(Cliente.class));
    }

    @Test
    public void testSalvaCliente_PasswordDifferenteNomeSpeciale() throws ServletException, IOException {
        // Configura i parametri con password diversa dalla conferma e nome con caratteri speciali
        when(request.getParameter("username")).thenReturn("FitnessWarrior");
        when(request.getParameter("email")).thenReturn("marco@gmail.com");
        when(request.getParameter("password")).thenReturn("12345678");
        when(request.getParameter("confirm-password")).thenReturn("123456");
        when(request.getParameter("name")).thenReturn("marco@#");
        when(request.getParameter("surname")).thenReturn("rossi");
        when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        when(request.getParameter("role")).thenReturn("cliente");
        when(request.getParameter("altezza")).thenReturn("100");
        when(request.getParameter("peso")).thenReturn("88");
        when(request.getParameter("girovita")).thenReturn("55");
        when(request.getParameter("circonferenza-braccio-destro")).thenReturn("55");
        when(request.getParameter("circonferenza-braccio-sinistro")).thenReturn("100");
        when(request.getParameter("circonferenza-gamba-destra")).thenReturn("90");
        when(request.getParameter("circonferenza-gamba-sinistra")).thenReturn("90");
        when(request.getParameter("circonferenza-torace")).thenReturn("2");
        when(request.getParameter("nutrizionista")).thenReturn("nutrizionista");

        // Configura il comportamento del servizio
        doReturn(false).when(regServiceMock).registraCliente(any(Cliente.class));

        // Esegui il metodo
        clienteService.doPost(request, response);

        // Verifica
        verify(regServiceMock, times(0)).registraCliente(any(Cliente.class));
    }

    @Test
    public void testSalvaCliente_CognomeSpecialeFormatoDataErrato() throws ServletException, IOException {
        // Configura i parametri con cognome con caratteri speciali e formato data errato
        when(request.getParameter("username")).thenReturn("FitnessWarrior");
        when(request.getParameter("email")).thenReturn("marco@gmail.com");
        when(request.getParameter("password")).thenReturn("12345678");
        when(request.getParameter("confirm-password")).thenReturn("12345678");
        when(request.getParameter("name")).thenReturn("marco");
        when(request.getParameter("surname")).thenReturn("Rossi##@");
        when(request.getParameter("birthdate")).thenReturn("01-01-2000");  // Formato errato
        when(request.getParameter("role")).thenReturn("cliente");
        when(request.getParameter("altezza")).thenReturn("100");
        when(request.getParameter("peso")).thenReturn("88");
        when(request.getParameter("girovita")).thenReturn("55");
        when(request.getParameter("circonferenza-braccio-destro")).thenReturn("55");
        when(request.getParameter("circonferenza-braccio-sinistro")).thenReturn("100");
        when(request.getParameter("circonferenza-gamba-destra")).thenReturn("90");
        when(request.getParameter("circonferenza-gamba-sinistra")).thenReturn("90");
        when(request.getParameter("circonferenza-torace")).thenReturn("2");
        when(request.getParameter("nutrizionista")).thenReturn("nutrizionista");

        // Configura il comportamento del servizio
        doReturn(false).when(regServiceMock).registraCliente(any(Cliente.class));

        // Esegui il metodo
        clienteService.doPost(request, response);

        // Verifica
        verify(regServiceMock, times(0)).registraCliente(any(Cliente.class));
    }

    @Test
    public void testSalvaCliente_PesoNonNumericoOutOfRange() throws ServletException, IOException {
        // Configura i parametri con peso non numerico e valori fuori range
        when(request.getParameter("username")).thenReturn("FitnessWarrior");
        when(request.getParameter("email")).thenReturn("marco@gmail.com");
        when(request.getParameter("password")).thenReturn("12345678");
        when(request.getParameter("confirm-password")).thenReturn("12345678");
        when(request.getParameter("name")).thenReturn("marco");
        when(request.getParameter("surname")).thenReturn("rossi");
        when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        when(request.getParameter("role")).thenReturn("cliente");
        when(request.getParameter("altezza")).thenReturn("100");
        when(request.getParameter("peso")).thenReturn("cento");
        when(request.getParameter("girovita")).thenReturn("8899");
        when(request.getParameter("circonferenza-braccio-destro")).thenReturn("550");
        when(request.getParameter("circonferenza-braccio-sinistro")).thenReturn("100");
        when(request.getParameter("circonferenza-gamba-destra")).thenReturn("90");
        when(request.getParameter("circonferenza-gamba-sinistra")).thenReturn("90");
        when(request.getParameter("circonferenza-torace")).thenReturn("2");
        when(request.getParameter("nutrizionista")).thenReturn("nutrizionista");

        // Configura il comportamento del servizio
        doReturn(false).when(regServiceMock).registraCliente(any(Cliente.class));

        // Esegui il metodo
        clienteService.doPost(request, response);

        // Verifica
        verify(regServiceMock, times(0)).registraCliente(any(Cliente.class));
    }

    @Test
    public void testSalvaCliente_CirconferenzeLettere() throws ServletException, IOException {
        // Configura i parametri con circonferenze espresse in lettere
        when(request.getParameter("username")).thenReturn("FitnessWarrior");
        when(request.getParameter("email")).thenReturn("marco@gmail.com");
        when(request.getParameter("password")).thenReturn("12345678");
        when(request.getParameter("confirm-password")).thenReturn("12345678");
        when(request.getParameter("name")).thenReturn("marco");
        when(request.getParameter("surname")).thenReturn("rossi");
        when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        when(request.getParameter("role")).thenReturn("cliente");
        when(request.getParameter("altezza")).thenReturn("100");
        when(request.getParameter("peso")).thenReturn("88");
        when(request.getParameter("girovita")).thenReturn("55");
        when(request.getParameter("circonferenza-braccio-destro")).thenReturn("cento");
        when(request.getParameter("circonferenza-braccio-sinistro")).thenReturn("novanta");
        when(request.getParameter("circonferenza-gamba-destra")).thenReturn("90");
        when(request.getParameter("circonferenza-gamba-sinistra")).thenReturn("90");
        when(request.getParameter("circonferenza-torace")).thenReturn("2");
        when(request.getParameter("nutrizionista")).thenReturn("nutrizionista");

        // Configura il comportamento del servizio
        doReturn(false).when(regServiceMock).registraCliente(any(Cliente.class));

        // Esegui il metodo
        clienteService.doPost(request, response);

        // Verifica
        verify(regServiceMock, times(0)).registraCliente(any(Cliente.class));
    }

    @Test
    public void testSalvaCliente_RegistrazioneValida() throws ServletException, IOException {
        // Configura i parametri con valori validi
        when(request.getParameter("username")).thenReturn("FitnessWarrior_2025");
        when(request.getParameter("email")).thenReturn("giovanni@example.com");
        when(request.getParameter("password")).thenReturn("password123");
        when(request.getParameter("confirm-password")).thenReturn("password123");
        when(request.getParameter("name")).thenReturn("Giovanni");
        when(request.getParameter("surname")).thenReturn("Bianchi");
        when(request.getParameter("birthdate")).thenReturn("1995-01-01");
        when(request.getParameter("role")).thenReturn("cliente");
        when(request.getParameter("altezza")).thenReturn("180");
        when(request.getParameter("peso")).thenReturn("75");
        when(request.getParameter("girovita")).thenReturn("85");
        when(request.getParameter("circonferenza-braccio-destro")).thenReturn("35");
        when(request.getParameter("circonferenza-braccio-sinistro")).thenReturn("35");
        when(request.getParameter("circonferenza-gamba-destra")).thenReturn("55");
        when(request.getParameter("circonferenza-gamba-sinistra")).thenReturn("55");
        when(request.getParameter("circonferenza-torace")).thenReturn("95");
        when(request.getParameter("nutrizionista")).thenReturn("Giovanni_Nutri");

        // Crea oggetto Cliente con i parametri validi
        Cliente cliente = new Cliente("Giovanni", "Bianchi", "FitnessWarrior_2025", "giovanni@example.com", "password123", "01/01/1995", 1,
                Float.parseFloat("180"), Float.parseFloat("75"), Float.parseFloat("85"), Float.parseFloat("35"), Float.parseFloat("35"),
                Float.parseFloat("55"), Float.parseFloat("55"), Float.parseFloat("95"));

        // Configura il comportamento del servizio
        doReturn(true).when(regServiceMock).registraCliente(any(Cliente.class));

        // Esegui il metodo
        clienteService.doPost(request, response);

        // Verifica che il metodo registaCliente sia stato chiamato
        verify(regServiceMock).registraCliente(any(Cliente.class));
    }
}