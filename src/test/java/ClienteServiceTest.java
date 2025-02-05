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
    public void testSalvaCliente_UsernameLungo_FallimentoPrevisto() throws Exception {

        lenient().when(request.getParameter("username")).thenReturn("FitnessWarrior_Transformation2024");
        lenient().when(request.getParameter("email")).thenReturn("Marco@gmail.com");
        lenient().when(request.getParameter("password")).thenReturn("12345678");
        lenient().when(request.getParameter("confirm-password")).thenReturn("12345678");
        lenient().when(request.getParameter("name")).thenReturn("marco");
        lenient().when(request.getParameter("surname")).thenReturn("rossi");
        lenient().when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        lenient().when(request.getParameter("role")).thenReturn("cliente");
        lenient().when(request.getParameter("altezza")).thenReturn("100");
        lenient().when(request.getParameter("peso")).thenReturn("88");
        lenient().when(request.getParameter("girovita")).thenReturn("55");
        lenient().when(request.getParameter("circonferenza-braccio-destro")).thenReturn("55");
        lenient().when(request.getParameter("circonferenza-braccio-sinistro")).thenReturn("100");
        lenient().when(request.getParameter("circonferenza-torace")).thenReturn("2");
        lenient().when(request.getParameter("circonferenza-gamba-destra")).thenReturn("90");
        lenient().when(request.getParameter("circonferenza-gamba-sinistra")).thenReturn("90");


        // Verifica che venga lanciata un'eccezione per username troppo lungo
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> clienteService.doPost(request, response));

        // Controlla che il messaggio dell'eccezione sia quello atteso
        assertEquals("Invalid username", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraCliente(any(Cliente.class));
    }
    @Test
    public void testSalvaCliente_email_FallimentoPrevisto() throws Exception {

        lenient().when(request.getParameter("username")).thenReturn("FitnessWarrior");
        lenient().when(request.getParameter("email")).thenReturn("Marco.gmail.com");
        lenient().when(request.getParameter("password")).thenReturn("12345678");
        lenient().when(request.getParameter("confirm-password")).thenReturn("12345678");
        lenient().when(request.getParameter("name")).thenReturn("marco");
        lenient().when(request.getParameter("surname")).thenReturn("rossi");
        lenient().when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        lenient().when(request.getParameter("role")).thenReturn("cliente");
        lenient().when(request.getParameter("altezza")).thenReturn("100");
        lenient().when(request.getParameter("peso")).thenReturn("88");
        lenient().when(request.getParameter("girovita")).thenReturn("55");
        lenient().when(request.getParameter("circonferenza-braccio-destro")).thenReturn("55");
        lenient().when(request.getParameter("circonferenza-braccio-sinistro")).thenReturn("100");
        lenient().when(request.getParameter("circonferenza-torace")).thenReturn("2");
        lenient().when(request.getParameter("circonferenza-gamba-destra")).thenReturn("90");
        lenient().when(request.getParameter("circonferenza-gamba-sinistra")).thenReturn("90");


        // Verifica che venga lanciata un'eccezione per username troppo lungo
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> clienteService.doPost(request, response));

        // Controlla che il messaggio dell'eccezione sia quello atteso
        assertEquals("L'e-mail non rispetta i criteri.", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraCliente(any(Cliente.class));
    }
    @Test
    public void testSalvaCliente_pass_FallimentoPrevisto() throws Exception {

        lenient().when(request.getParameter("username")).thenReturn("FitnessWarrior");
        lenient().when(request.getParameter("email")).thenReturn("Marco@gmail.com");
        lenient().when(request.getParameter("password")).thenReturn("123456");
        lenient().when(request.getParameter("confirm-password")).thenReturn("12345678");
        lenient().when(request.getParameter("name")).thenReturn("marco");
        lenient().when(request.getParameter("surname")).thenReturn("rossi");
        lenient().when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        lenient().when(request.getParameter("role")).thenReturn("cliente");
        lenient().when(request.getParameter("altezza")).thenReturn("100");
        lenient().when(request.getParameter("peso")).thenReturn("88");
        lenient().when(request.getParameter("girovita")).thenReturn("55");
        lenient().when(request.getParameter("circonferenza-braccio-destro")).thenReturn("55");
        lenient().when(request.getParameter("circonferenza-braccio-sinistro")).thenReturn("100");
        lenient().when(request.getParameter("circonferenza-torace")).thenReturn("2");
        lenient().when(request.getParameter("circonferenza-gamba-destra")).thenReturn("90");
        lenient().when(request.getParameter("circonferenza-gamba-sinistra")).thenReturn("90");


        // Verifica che venga lanciata un'eccezione per username troppo lungo
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> clienteService.doPost(request, response));

        // Controlla che il messaggio dell'eccezione sia quello atteso
        assertEquals("La password non rispetta i criteri di sicurezza.", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraCliente(any(Cliente.class));
    } @Test
    public void testSalvaCliente_passconf_FallimentoPrevisto() throws Exception {

        lenient().when(request.getParameter("username")).thenReturn("FitnessWarrior");
        lenient().when(request.getParameter("email")).thenReturn("Marco@gmail.com");
        lenient().when(request.getParameter("password")).thenReturn("12345678");
        lenient().when(request.getParameter("confirm-password")).thenReturn("123456");
        lenient().when(request.getParameter("name")).thenReturn("marco");
        lenient().when(request.getParameter("surname")).thenReturn("rossi");
        lenient().when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        lenient().when(request.getParameter("role")).thenReturn("cliente");
        lenient().when(request.getParameter("altezza")).thenReturn("100");
        lenient().when(request.getParameter("peso")).thenReturn("88");
        lenient().when(request.getParameter("girovita")).thenReturn("55");
        lenient().when(request.getParameter("circonferenza-braccio-destro")).thenReturn("55");
        lenient().when(request.getParameter("circonferenza-braccio-sinistro")).thenReturn("100");
        lenient().when(request.getParameter("circonferenza-torace")).thenReturn("2");
        lenient().when(request.getParameter("circonferenza-gamba-destra")).thenReturn("90");
        lenient().when(request.getParameter("circonferenza-gamba-sinistra")).thenReturn("90");


        // Verifica che venga lanciata un'eccezione per username troppo lungo
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> clienteService.doPost(request, response));

        // Controlla che il messaggio dell'eccezione sia quello atteso
        assertEquals("La password non rispetta i criteri di sicurezza.", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraCliente(any(Cliente.class));
    } @Test
    public void testSalvaCliente_nome_FallimentoPrevisto() throws Exception {

        lenient().when(request.getParameter("username")).thenReturn("FitnessWarrior");
        lenient().when(request.getParameter("email")).thenReturn("Marco@gmail.com");
        lenient().when(request.getParameter("password")).thenReturn("12345678");
        lenient().when(request.getParameter("confirm-password")).thenReturn("12345678");
        lenient().when(request.getParameter("name")).thenReturn("marco@@°");
        lenient().when(request.getParameter("surname")).thenReturn("rossi");
        lenient().when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        lenient().when(request.getParameter("role")).thenReturn("cliente");
        lenient().when(request.getParameter("altezza")).thenReturn("100");
        lenient().when(request.getParameter("peso")).thenReturn("88");
        lenient().when(request.getParameter("girovita")).thenReturn("55");
        lenient().when(request.getParameter("circonferenza-braccio-destro")).thenReturn("55");
        lenient().when(request.getParameter("circonferenza-braccio-sinistro")).thenReturn("100");
        lenient().when(request.getParameter("circonferenza-torace")).thenReturn("2");
        lenient().when(request.getParameter("circonferenza-gamba-destra")).thenReturn("90");
        lenient().when(request.getParameter("circonferenza-gamba-sinistra")).thenReturn("90");


        // Verifica che venga lanciata un'eccezione per username troppo lungo
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> clienteService.doPost(request, response));

        // Controlla che il messaggio dell'eccezione sia quello atteso
        assertEquals("Nome/Cognome non rispetta i criteri.", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraCliente(any(Cliente.class));
    } @Test
    public void testSalvaCliente_cogn_FallimentoPrevisto() throws Exception {

        lenient().when(request.getParameter("username")).thenReturn("FitnessWarrior");
        lenient().when(request.getParameter("email")).thenReturn("Marco@gmail.com");
        lenient().when(request.getParameter("password")).thenReturn("12345678");
        lenient().when(request.getParameter("confirm-password")).thenReturn("12345678");
        lenient().when(request.getParameter("name")).thenReturn("marco");
        lenient().when(request.getParameter("surname")).thenReturn("rossi@@#");
        lenient().when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        lenient().when(request.getParameter("role")).thenReturn("cliente");
        lenient().when(request.getParameter("altezza")).thenReturn("100");
        lenient().when(request.getParameter("peso")).thenReturn("88");
        lenient().when(request.getParameter("girovita")).thenReturn("55");
        lenient().when(request.getParameter("circonferenza-braccio-destro")).thenReturn("55");
        lenient().when(request.getParameter("circonferenza-braccio-sinistro")).thenReturn("100");
        lenient().when(request.getParameter("circonferenza-torace")).thenReturn("2");
        lenient().when(request.getParameter("circonferenza-gamba-destra")).thenReturn("90");
        lenient().when(request.getParameter("circonferenza-gamba-sinistra")).thenReturn("90");


        // Verifica che venga lanciata un'eccezione per username troppo lungo
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> clienteService.doPost(request, response));

        // Controlla che il messaggio dell'eccezione sia quello atteso
        assertEquals("Nome/Cognome non rispetta i criteri.", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraCliente(any(Cliente.class));
    } @Test
    public void testSalvaCliente_data_FallimentoPrevisto() throws Exception {

        lenient().when(request.getParameter("username")).thenReturn("FitnessWarrior");
        lenient().when(request.getParameter("email")).thenReturn("Marco@gmail.com");
        lenient().when(request.getParameter("password")).thenReturn("12345678");
        lenient().when(request.getParameter("confirm-password")).thenReturn("12345678");
        lenient().when(request.getParameter("name")).thenReturn("marco");
        lenient().when(request.getParameter("surname")).thenReturn("rossi");
        lenient().when(request.getParameter("birthdate")).thenReturn("2000/01/01");
        lenient().when(request.getParameter("role")).thenReturn("cliente");
        lenient().when(request.getParameter("altezza")).thenReturn("100");
        lenient().when(request.getParameter("peso")).thenReturn("88");
        lenient().when(request.getParameter("girovita")).thenReturn("55");
        lenient().when(request.getParameter("circonferenza-braccio-destro")).thenReturn("55");
        lenient().when(request.getParameter("circonferenza-braccio-sinistro")).thenReturn("100");
        lenient().when(request.getParameter("circonferenza-torace")).thenReturn("2");
        lenient().when(request.getParameter("circonferenza-gamba-destra")).thenReturn("90");
        lenient().when(request.getParameter("circonferenza-gamba-sinistra")).thenReturn("90");


        // Verifica che venga lanciata un'eccezione per username troppo lungo
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> clienteService.doPost(request, response));

        // Controlla che il messaggio dell'eccezione sia quello atteso
        assertEquals("Data nascita non rispetta i criteri.", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraCliente(any(Cliente.class));
    } @Test
    public void testSalvaCliente_peso_FallimentoPrevisto() throws Exception {

        lenient().when(request.getParameter("username")).thenReturn("FitnessWarrior");
        lenient().when(request.getParameter("email")).thenReturn("Marco@gmail.com");
        lenient().when(request.getParameter("password")).thenReturn("12345678");
        lenient().when(request.getParameter("confirm-password")).thenReturn("12345678");
        lenient().when(request.getParameter("name")).thenReturn("marco");
        lenient().when(request.getParameter("surname")).thenReturn("rossi");
        lenient().when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        lenient().when(request.getParameter("role")).thenReturn("cliente");
        lenient().when(request.getParameter("altezza")).thenReturn("100");
        lenient().when(request.getParameter("peso")).thenReturn("cento");
        lenient().when(request.getParameter("girovita")).thenReturn("55");
        lenient().when(request.getParameter("circonferenza-braccio-destro")).thenReturn("55");
        lenient().when(request.getParameter("circonferenza-braccio-sinistro")).thenReturn("100");
        lenient().when(request.getParameter("circonferenza-torace")).thenReturn("2");
        lenient().when(request.getParameter("circonferenza-gamba-destra")).thenReturn("90");
        lenient().when(request.getParameter("circonferenza-gamba-sinistra")).thenReturn("90");


        // Verifica che venga lanciata un'eccezione per username troppo lungo
        // Verifica che venga lanciata un'eccezione per username troppo lungo
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> clienteService.doPost(request, response));

        // Controlla che il messaggio dell'eccezione sia quello atteso
        assertEquals("Formato non valido per peso", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraCliente(any(Cliente.class));
    } @Test
    public void testSalvaCliente_girov_FallimentoPrevisto() throws Exception {

        lenient().when(request.getParameter("username")).thenReturn("FitnessWarrior");
        lenient().when(request.getParameter("email")).thenReturn("Marco@gmail.com");
        lenient().when(request.getParameter("password")).thenReturn("12345678");
        lenient().when(request.getParameter("confirm-password")).thenReturn("12345678");
        lenient().when(request.getParameter("name")).thenReturn("marco");
        lenient().when(request.getParameter("surname")).thenReturn("rossi");
        lenient().when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        lenient().when(request.getParameter("role")).thenReturn("cliente");
        lenient().when(request.getParameter("altezza")).thenReturn("100");
        lenient().when(request.getParameter("peso")).thenReturn("88");
        lenient().when(request.getParameter("girovita")).thenReturn("8800");
        lenient().when(request.getParameter("circonferenza-braccio-destro")).thenReturn("55");
        lenient().when(request.getParameter("circonferenza-braccio-sinistro")).thenReturn("100");
        lenient().when(request.getParameter("circonferenza-torace")).thenReturn("2");
        lenient().when(request.getParameter("circonferenza-gamba-destra")).thenReturn("90");
        lenient().when(request.getParameter("circonferenza-gamba-sinistra")).thenReturn("90");


        // Verifica che venga lanciata un'eccezione per username troppo lungo
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> clienteService.doPost(request, response));

        // Controlla che il messaggio dell'eccezione sia quello atteso
        assertEquals("Valore girovita non valido", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraCliente(any(Cliente.class));
    } @Test
    public void testSalvaCliente_brdx_FallimentoPrevisto() throws Exception {

        lenient().when(request.getParameter("username")).thenReturn("FitnessWarrior");
        lenient().when(request.getParameter("email")).thenReturn("Marco@gmail.com");
        lenient().when(request.getParameter("password")).thenReturn("12345678");
        lenient().when(request.getParameter("confirm-password")).thenReturn("12345678");
        lenient().when(request.getParameter("name")).thenReturn("marco");
        lenient().when(request.getParameter("surname")).thenReturn("rossi");
        lenient().when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        lenient().when(request.getParameter("role")).thenReturn("cliente");
        lenient().when(request.getParameter("altezza")).thenReturn("100");
        lenient().when(request.getParameter("peso")).thenReturn("88");
        lenient().when(request.getParameter("girovita")).thenReturn("55");
        lenient().when(request.getParameter("circonferenza-braccio-destro")).thenReturn("550");
        lenient().when(request.getParameter("circonferenza-braccio-sinistro")).thenReturn("55");
        lenient().when(request.getParameter("circonferenza-torace")).thenReturn("2");
        lenient().when(request.getParameter("circonferenza-gamba-destra")).thenReturn("90");
        lenient().when(request.getParameter("circonferenza-gamba-sinistra")).thenReturn("90");


        // Verifica che venga lanciata un'eccezione per username troppo lungo
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> clienteService.doPost(request, response));

        // Controlla che il messaggio dell'eccezione sia quello atteso
        assertEquals("Valore circonferenza braccio destro non valido", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraCliente(any(Cliente.class));
    } @Test
    public void testSalvaCliente_brsx_FallimentoPrevisto() throws Exception {

        lenient().when(request.getParameter("username")).thenReturn("FitnessWarrior");
        lenient().when(request.getParameter("email")).thenReturn("Marco@gmail.com");
        lenient().when(request.getParameter("password")).thenReturn("12345678");
        lenient().when(request.getParameter("confirm-password")).thenReturn("12345678");
        lenient().when(request.getParameter("name")).thenReturn("marco");
        lenient().when(request.getParameter("surname")).thenReturn("rossi");
        lenient().when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        lenient().when(request.getParameter("role")).thenReturn("cliente");
        lenient().when(request.getParameter("altezza")).thenReturn("100");
        lenient().when(request.getParameter("peso")).thenReturn("88");
        lenient().when(request.getParameter("girovita")).thenReturn("55");
        lenient().when(request.getParameter("circonferenza-braccio-destro")).thenReturn("55");
        lenient().when(request.getParameter("circonferenza-braccio-sinistro")).thenReturn("5500");
        lenient().when(request.getParameter("circonferenza-torace")).thenReturn("2");
        lenient().when(request.getParameter("circonferenza-gamba-destra")).thenReturn("90");
        lenient().when(request.getParameter("circonferenza-gamba-sinistra")).thenReturn("90");


        // Verifica che venga lanciata un'eccezione per username troppo lungo
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> clienteService.doPost(request, response));

        // Controlla che il messaggio dell'eccezione sia quello atteso
        assertEquals("Valore circonferenza braccio sinistro non valido", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraCliente(any(Cliente.class));
    } @Test
    public void testSalvaCliente_circtor_FallimentoPrevisto() throws Exception {

        lenient().when(request.getParameter("username")).thenReturn("FitnessWarrior");
        lenient().when(request.getParameter("email")).thenReturn("Marco@gmail.com");
        lenient().when(request.getParameter("password")).thenReturn("12345678");
        lenient().when(request.getParameter("confirm-password")).thenReturn("12345678");
        lenient().when(request.getParameter("name")).thenReturn("marco");
        lenient().when(request.getParameter("surname")).thenReturn("rossi");
        lenient().when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        lenient().when(request.getParameter("role")).thenReturn("cliente");
        lenient().when(request.getParameter("altezza")).thenReturn("100");
        lenient().when(request.getParameter("peso")).thenReturn("88");
        lenient().when(request.getParameter("girovita")).thenReturn("55");
        lenient().when(request.getParameter("circonferenza-braccio-destro")).thenReturn("55");
        lenient().when(request.getParameter("circonferenza-braccio-sinistro")).thenReturn("100");
        lenient().when(request.getParameter("circonferenza-torace")).thenReturn("1000");
        lenient().when(request.getParameter("circonferenza-gamba-destra")).thenReturn("90");
        lenient().when(request.getParameter("circonferenza-gamba-sinistra")).thenReturn("90");


        // Verifica che venga lanciata un'eccezione per username troppo lungo
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> clienteService.doPost(request, response));

        // Controlla che il messaggio dell'eccezione sia quello atteso
        assertEquals("Valore circonferenza torace non valido", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraCliente(any(Cliente.class));
    } @Test
    public void testSalvaCliente_gbdx_FallimentoPrevisto() throws Exception {

        lenient().when(request.getParameter("username")).thenReturn("FitnessWarrior");
        lenient().when(request.getParameter("email")).thenReturn("Marco@gmail.com");
        lenient().when(request.getParameter("password")).thenReturn("12345678");
        lenient().when(request.getParameter("confirm-password")).thenReturn("12345678");
        lenient().when(request.getParameter("name")).thenReturn("marco");
        lenient().when(request.getParameter("surname")).thenReturn("rossi");
        lenient().when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        lenient().when(request.getParameter("role")).thenReturn("cliente");
        lenient().when(request.getParameter("altezza")).thenReturn("100");
        lenient().when(request.getParameter("peso")).thenReturn("88");
        lenient().when(request.getParameter("girovita")).thenReturn("55");
        lenient().when(request.getParameter("circonferenza-braccio-destro")).thenReturn("55");
        lenient().when(request.getParameter("circonferenza-braccio-sinistro")).thenReturn("100");
        lenient().when(request.getParameter("circonferenza-torace")).thenReturn("90");
        lenient().when(request.getParameter("circonferenza-gamba-destra")).thenReturn("900");
        lenient().when(request.getParameter("circonferenza-gamba-sinistra")).thenReturn("90");


        // Verifica che venga lanciata un'eccezione per username troppo lungo
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> clienteService.doPost(request, response));

        // Controlla che il messaggio dell'eccezione sia quello atteso
        assertEquals("Valore circonferenza gamba destra non valido", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraCliente(any(Cliente.class));
    } @Test
    public void testSalvaCliente_gbsx_FallimentoPrevisto() throws Exception {

        lenient().when(request.getParameter("username")).thenReturn("FitnessWarrior");
        lenient().when(request.getParameter("email")).thenReturn("Marco@gmail.com");
        lenient().when(request.getParameter("password")).thenReturn("12345678");
        lenient().when(request.getParameter("confirm-password")).thenReturn("12345678");
        lenient().when(request.getParameter("name")).thenReturn("marco");
        lenient().when(request.getParameter("surname")).thenReturn("rossi");
        lenient().when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        lenient().when(request.getParameter("role")).thenReturn("cliente");
        lenient().when(request.getParameter("altezza")).thenReturn("100");
        lenient().when(request.getParameter("peso")).thenReturn("88");
        lenient().when(request.getParameter("girovita")).thenReturn("55");
        lenient().when(request.getParameter("circonferenza-braccio-destro")).thenReturn("55");
        lenient().when(request.getParameter("circonferenza-braccio-sinistro")).thenReturn("100");
        lenient().when(request.getParameter("circonferenza-torace")).thenReturn("90");
        lenient().when(request.getParameter("circonferenza-gamba-destra")).thenReturn("90");
        lenient().when(request.getParameter("circonferenza-gamba-sinistra")).thenReturn("900");


        // Verifica che venga lanciata un'eccezione per username troppo lungo
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> clienteService.doPost(request, response));

        // Controlla che il messaggio dell'eccezione sia quello atteso
        assertEquals("Valore circonferenza gamba sinistra non valido", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraCliente(any(Cliente.class));
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