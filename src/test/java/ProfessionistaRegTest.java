import business.RegistrazioneServlet;
import business.progressiController;
import data.entity.Cliente;
import data.entity.Professionista;
import data.service.RegistrazioneService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.http.HttpServletRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProfessionistaRegTest{
    @Mock
    private HttpServletRequest request;  // Mock del HttpServletRequest

    @Mock
    private HttpServletResponse response;  // Mock del HttpServletRequest

    @Mock
    private RegistrazioneService regServiceMock;  // Mock del RegistrazioneService

    @InjectMocks
    private RegistrazioneServlet profService;  // La classe da testare
    @Mock
    private PrintWriter writer;

    private RegistrazioneServlet servlet;

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        servlet = new RegistrazioneServlet();

    }

    @Test
    public void testRegistrazionePersonalTrainerEmailNonValida() throws Exception {
        // Imposta i parametri invalidi per la registrazione del personal trainer
        when(request.getParameter("username")).thenReturn("Personal_Trainer_2025_Giovanni_____");
        when(request.getParameter("email")).thenReturn("Personal.email.com");  // Email non valida
        when(request.getParameter("password")).thenReturn("12345678");
        when(request.getParameter("confirm-password")).thenReturn("12345678");
        when(request.getParameter("name")).thenReturn("Giovanni");
        when(request.getParameter("surname")).thenReturn("rossi");
        when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        when(request.getParameter("role")).thenReturn("nutrizionista");

        // Controlla che venga lanciata un'IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> servlet.doPost(request, response));
        assertEquals("Invalid username", exception.getMessage());
        // Verifica che il messaggio dell'eccezione sia quello atteso (personalizzalo con quello che la tua servlet lancia)
       // assertEquals("L'e-mail non rispetta i criteri.", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraProfessionista(any(Professionista.class));
    }


    @Test
    public void testRegistrazionePersonalTrainerPasswordNonCoincidenti() throws Exception {
        // Imposta i parametri invalidi per la registrazione del personal trainer
        when(request.getParameter("username")).thenReturn("Personal_Trainer_2025");
        when(request.getParameter("email")).thenReturn("Personal@email.com");
        when(request.getParameter("password")).thenReturn("123456");
        when(request.getParameter("confirm-password")).thenReturn("12345");  // Password non coincidenti
        when(request.getParameter("name")).thenReturn("Giovanni");
        when(request.getParameter("surname")).thenReturn("rossi");
        when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        when(request.getParameter("role")).thenReturn("nutrizionista");

        // Controlla che venga lanciata un'IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> servlet.doPost(request, response));

        // Verifica che il messaggio dell'eccezione sia quello atteso (personalizzalo con quello che la tua servlet lancia)
        assertEquals("La password non rispetta i criteri di sicurezza.", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraProfessionista(any(Professionista.class));
    }

    @Test
    public void testRegistrazionePersonalTrainerCaratteriNonValidi() throws Exception {
        // Imposta i parametri invalidi per la registrazione del personal trainer
        when(request.getParameter("username")).thenReturn("Personal_Trainer_2025");
        when(request.getParameter("email")).thenReturn("Personal@email.com");
        when(request.getParameter("password")).thenReturn("12345678");
        when(request.getParameter("confirm-password")).thenReturn("12345678");
        when(request.getParameter("name")).thenReturn("Giovanni@@?");  // Caratteri non validi
        when(request.getParameter("surname")).thenReturn("Rossi??");     // Caratteri non validi
        when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        when(request.getParameter("role")).thenReturn("nutrizionista");

        // Controlla che venga lanciata un'IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> servlet.doPost(request, response));

        // Verifica che il messaggio dell'eccezione sia quello atteso (personalizzalo con quello che la tua servlet lancia)
        assertEquals("Nome/Cognome non rispetta i criteri.", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraProfessionista(any(Professionista.class));
    }

    @Test
    public void testRegistrazionePersonalTrainerDataNascitaNonValida() throws Exception {
        // Imposta i parametri invalidi per la registrazione del personal trainer
        when(request.getParameter("username")).thenReturn("Personal_Trainer_2025");
        when(request.getParameter("email")).thenReturn("Personal@email.com");
        when(request.getParameter("password")).thenReturn("12345678");
        when(request.getParameter("confirm-password")).thenReturn("12345678");
        when(request.getParameter("name")).thenReturn("Giovanni");
        when(request.getParameter("surname")).thenReturn("rossi");
        when(request.getParameter("birthdate")).thenReturn("2000/01/01");  // Formato non valido
        when(request.getParameter("role")).thenReturn("nutrizionista");

        // Controlla che venga lanciata un'IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> servlet.doPost(request, response));

        // Verifica che il messaggio dell'eccezione sia quello atteso (personalizzalo con quello che la tua servlet lancia)
        assertEquals("Data nascita non rispetta i criteri.", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraProfessionista(any(Professionista.class));
    }

    @Test
    public void testRegistrazioneProfessionistaSuccess() throws Exception {
        // Imposta i parametri validi per la registrazione del professionista
        when(request.getParameter("username")).thenReturn("prof123");
        when(request.getParameter("email")).thenReturn("prof@esempio.com");
        when(request.getParameter("password")).thenReturn("password123");
        when(request.getParameter("confirm-password")).thenReturn("password123");
        when(request.getParameter("name")).thenReturn("Mario");
        when(request.getParameter("surname")).thenReturn("Rossi");
        when(request.getParameter("birthdate")).thenReturn("1985-05-20");
        when(request.getParameter("role")).thenReturn("nutrizionista");

        // Simula la registrazione del professionista
        doReturn(true).when(regServiceMock).registraProfessionista(any(Professionista.class));

        // Esegui la registrazione (doPost)
        profService.doPost(request, response);

        // Verifica che la registrazione del professionista sia stata eseguita correttamente
        verify(regServiceMock, times(1)).registraProfessionista(any(Professionista.class));  // Verifica una sola volta
    }

    //NUTRIZIONISTA TEST
    @Test
    public void testRegistrazioneNutrizionistaEmailNonValida() throws Exception {
        // Imposta i parametri invalidi per la registrazione del nutrizionista
        when(request.getParameter("username")).thenReturn("Nutrizionista_2025_Giovanni_Rossi_123");
        when(request.getParameter("email")).thenReturn("Nutrizionista.email.com");  // Email non valida
        when(request.getParameter("password")).thenReturn("12345678");
        when(request.getParameter("confirm-password")).thenReturn("12345678");
        when(request.getParameter("name")).thenReturn("Giovanni");
        when(request.getParameter("surname")).thenReturn("rossi");
        when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        when(request.getParameter("role")).thenReturn("nutrizionista");

        // Simula la registrazione del nutrizionista
        doReturn(false).when(regServiceMock).registraProfessionista(any(Professionista.class));

        // Esegui la registrazione
        profService.doPost(request, response);

        // Verifica che la registrazione non sia andata a buon fine
        verify(regServiceMock, times(0)).registraProfessionista(any(Professionista.class));  // Verifica che non sia mai stato invocato
    }

    @Test
    public void testRegistrazioneNutrizionistaPasswordNonConcordata() throws Exception {
        // Imposta i parametri con password non concordate
        when(request.getParameter("username")).thenReturn("Nutrizionista_2025");
        when(request.getParameter("email")).thenReturn("Nutrizionista@email.com");
        when(request.getParameter("password")).thenReturn("123456");
        when(request.getParameter("confirm-password")).thenReturn("12345");  // Password non concordata
        when(request.getParameter("name")).thenReturn("Giovanni");
        when(request.getParameter("surname")).thenReturn("rossi");
        when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        when(request.getParameter("role")).thenReturn("nutrizionista");

        // Simula la registrazione del nutrizionista
        doReturn(false).when(regServiceMock).registraProfessionista(any(Professionista.class));

        // Esegui la registrazione
        profService.doPost(request, response);

        // Verifica che la registrazione non sia andata a buon fine
        verify(regServiceMock, times(0)).registraProfessionista(any(Professionista.class));  // Verifica che non sia mai stato invocato
    }

    @Test
    public void testRegistrazioneNutrizionistaCaratteriSpeciali() throws Exception {
        // Imposta i parametri con caratteri speciali nel nome
        when(request.getParameter("username")).thenReturn("Nutrizionisa_2025");
        when(request.getParameter("email")).thenReturn("Nutrizionista@email.com");
        when(request.getParameter("password")).thenReturn("12345678");
        when(request.getParameter("confirm-password")).thenReturn("12345678");
        when(request.getParameter("name")).thenReturn("Giovanni@@?");
        when(request.getParameter("surname")).thenReturn("Rossi??");
        when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        when(request.getParameter("role")).thenReturn("nutrizionista");

        // Simula la registrazione del nutrizionista
        doReturn(false).when(regServiceMock).registraProfessionista(any(Professionista.class));

        // Esegui la registrazione
        profService.doPost(request, response);

        // Verifica che la registrazione non sia andata a buon fine
        verify(regServiceMock, times(0)).registraProfessionista(any(Professionista.class));  // Verifica che non sia mai stato invocato
    }

    @Test
    public void testRegistrazioneNutrizionistaFormatoDataErrato() throws Exception {
        // Imposta i parametri con formato data errato
        when(request.getParameter("username")).thenReturn("Nutrizionista_2025");
        when(request.getParameter("email")).thenReturn("Nutrizinista@email.com");
        when(request.getParameter("password")).thenReturn("12345678");
        when(request.getParameter("confirm-password")).thenReturn("12345678");
        when(request.getParameter("name")).thenReturn("Giovanni");
        when(request.getParameter("surname")).thenReturn("rossi");
        when(request.getParameter("birthdate")).thenReturn("2000/01/01");  // Formato errato
        when(request.getParameter("role")).thenReturn("nutrizionista");

        // Simula la registrazione del nutrizionista
        doReturn(false).when(regServiceMock).registraProfessionista(any(Professionista.class));

        // Esegui la registrazione
        profService.doPost(request, response);

        // Verifica che la registrazione non sia andata a buon fine
        verify(regServiceMock, times(0)).registraProfessionista(any(Professionista.class));  // Verifica che non sia mai stato invocato
    }

    @Test
    public void testRegistrazioneNutrizionistaValida() throws Exception {
        // Imposta i parametri corretti per la registrazione
        when(request.getParameter("username")).thenReturn("Nutrizionista_2025");
        when(request.getParameter("email")).thenReturn("Nutrizionista@email.com");
        when(request.getParameter("password")).thenReturn("12345678");
        when(request.getParameter("confirm-password")).thenReturn("12345678");
        when(request.getParameter("name")).thenReturn("Giovanni");
        when(request.getParameter("surname")).thenReturn("rossi");
        when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        when(request.getParameter("role")).thenReturn("nutrizionista");

        // Simula la registrazione del nutrizionista
        doReturn(true).when(regServiceMock).registraProfessionista(any(Professionista.class));

        // Esegui la registrazione
        profService.doPost(request, response);

        // Verifica che la registrazione sia andata a buon fine
        verify(regServiceMock, times(1)).registraProfessionista(any(Professionista.class));  // Verifica che sia stato invocato una volta
    }
}
