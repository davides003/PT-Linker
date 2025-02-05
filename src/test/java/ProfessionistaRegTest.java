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
    public void testRegistrazionePersonalTrainerusernameNonValido() throws Exception {
        // Imposta i parametri invalidi per la registrazione del personal trainer
        when(request.getParameter("username")).thenReturn("Personal_Trainer_2025_Giovanni_____");
        when(request.getParameter("email")).thenReturn("Personal@email.com");  // Email non valida
        when(request.getParameter("password")).thenReturn("12345678");
        when(request.getParameter("confirm-password")).thenReturn("12345678");
        when(request.getParameter("name")).thenReturn("Giovanni");
        when(request.getParameter("surname")).thenReturn("rossi");
        when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        when(request.getParameter("role")).thenReturn("personal_trainer");

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
    public void testRegistrazionePersonalTrainerEmailNonValida() throws Exception {
        // Imposta i parametri invalidi per la registrazione del personal trainer
        when(request.getParameter("username")).thenReturn("Personal_Trainer_2025");
        when(request.getParameter("email")).thenReturn("Personal.email.com");  // Email non valida
        when(request.getParameter("password")).thenReturn("12345678");
        when(request.getParameter("confirm-password")).thenReturn("12345678");
        when(request.getParameter("name")).thenReturn("Giovanni");
        when(request.getParameter("surname")).thenReturn("rossi");
        when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        when(request.getParameter("role")).thenReturn("personal_trainer");

        // Controlla che venga lanciata un'IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> servlet.doPost(request, response));
        assertEquals("L'e-mail non rispetta i criteri.", exception.getMessage());
        // Verifica che il messaggio dell'eccezione sia quello atteso (personalizzalo con quello che la tua servlet lancia)
        // assertEquals("L'e-mail non rispetta i criteri.", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraProfessionista(any(Professionista.class));
    }

    @Test
    public void testRegistrazionePersonalTrainerpassNonValida() throws Exception {
        // Imposta i parametri invalidi per la registrazione del personal trainer
        when(request.getParameter("username")).thenReturn("Personal_Trainer_2025");
        when(request.getParameter("email")).thenReturn("Personal@email.com");  // Email non valida
        when(request.getParameter("password")).thenReturn("123456");
        when(request.getParameter("confirm-password")).thenReturn("12345678");
        when(request.getParameter("name")).thenReturn("Giovanni");
        when(request.getParameter("surname")).thenReturn("rossi");
        when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        when(request.getParameter("role")).thenReturn("personal_trainer");

        // Controlla che venga lanciata un'IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> servlet.doPost(request, response));
        assertEquals("La password non rispetta i criteri di sicurezza.", exception.getMessage());
        // Verifica che il messaggio dell'eccezione sia quello atteso (personalizzalo con quello che la tua servlet lancia)
        // assertEquals("L'e-mail non rispetta i criteri.", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraProfessionista(any(Professionista.class));
    }

    @Test
    public void testRegistrazionePersonalTrainerpassconNonValida() throws Exception {
        // Imposta i parametri invalidi per la registrazione del personal trainer
        when(request.getParameter("username")).thenReturn("Personal_Trainer_2025");
        when(request.getParameter("email")).thenReturn("Personal@email.com");  // Email non valida
        when(request.getParameter("password")).thenReturn("12345678");
        when(request.getParameter("confirm-password")).thenReturn("123456");
        when(request.getParameter("name")).thenReturn("Giovanni");
        when(request.getParameter("surname")).thenReturn("rossi");
        when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        when(request.getParameter("role")).thenReturn("personal_trainer");

        // Controlla che venga lanciata un'IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> servlet.doPost(request, response));
        assertEquals("La password non rispetta i criteri di sicurezza.", exception.getMessage());
        // Verifica che il messaggio dell'eccezione sia quello atteso (personalizzalo con quello che la tua servlet lancia)
        // assertEquals("L'e-mail non rispetta i criteri.", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraProfessionista(any(Professionista.class));
    }
    @Test
    public void testRegistrazionePersonalTrainernomeNonValida() throws Exception {
        // Imposta i parametri invalidi per la registrazione del personal trainer
        when(request.getParameter("username")).thenReturn("Personal_Trainer_2025");
        when(request.getParameter("email")).thenReturn("Personal@email.com");  // Email non valida
        when(request.getParameter("password")).thenReturn("12345678");
        when(request.getParameter("confirm-password")).thenReturn("12345678");
        when(request.getParameter("name")).thenReturn("Giovanni@@@");
        when(request.getParameter("surname")).thenReturn("rossi");
        when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        when(request.getParameter("role")).thenReturn("personal_trainer");

        // Controlla che venga lanciata un'IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> servlet.doPost(request, response));
        assertEquals("Nome/Cognome non rispetta i criteri.", exception.getMessage());
        // Verifica che il messaggio dell'eccezione sia quello atteso (personalizzalo con quello che la tua servlet lancia)
        // assertEquals("L'e-mail non rispetta i criteri.", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraProfessionista(any(Professionista.class));
    }
    @Test
    public void testRegistrazionePersonalTrainercognomeNonValida() throws Exception {
        // Imposta i parametri invalidi per la registrazione del personal trainer
        when(request.getParameter("username")).thenReturn("Personal_Trainer_2025");
        when(request.getParameter("email")).thenReturn("Personal@email.com");  // Email non valida
        when(request.getParameter("password")).thenReturn("12345678");
        when(request.getParameter("confirm-password")).thenReturn("12345678");
        when(request.getParameter("name")).thenReturn("Giovanni");
        when(request.getParameter("surname")).thenReturn("rossi@@@");
        when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        when(request.getParameter("role")).thenReturn("personal_trainer");

        // Controlla che venga lanciata un'IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> servlet.doPost(request, response));
        assertEquals("Nome/Cognome non rispetta i criteri.", exception.getMessage());
        // Verifica che il messaggio dell'eccezione sia quello atteso (personalizzalo con quello che la tua servlet lancia)
        // assertEquals("L'e-mail non rispetta i criteri.", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraProfessionista(any(Professionista.class));
    }
    @Test
    public void testRegistrazionePersonalTrainerdataNonValida() throws Exception {
        // Imposta i parametri invalidi per la registrazione del personal trainer
        when(request.getParameter("username")).thenReturn("Personal_Trainer_2025");
        when(request.getParameter("email")).thenReturn("Personal@email.com");  // Email non valida
        when(request.getParameter("password")).thenReturn("12345678");
        when(request.getParameter("confirm-password")).thenReturn("12345678");
        when(request.getParameter("name")).thenReturn("Giovanni");
        when(request.getParameter("surname")).thenReturn("rossi");
        when(request.getParameter("birthdate")).thenReturn("2000/01/01");
        when(request.getParameter("role")).thenReturn("personal_trainer");

        // Controlla che venga lanciata un'IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> servlet.doPost(request, response));
        assertEquals("Data nascita non rispetta i criteri.", exception.getMessage());
        // Verifica che il messaggio dell'eccezione sia quello atteso (personalizzalo con quello che la tua servlet lancia)
        // assertEquals("L'e-mail non rispetta i criteri.", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraProfessionista(any(Professionista.class));
    }
    @Test
    public void testRegistrazionePersonalTrainerValida() throws Exception {
        // Imposta i parametri corretti per la registrazione
        when(request.getParameter("username")).thenReturn("Personal_Trainer_2025");
        when(request.getParameter("email")).thenReturn("Nutrizionista@email.com");
        when(request.getParameter("password")).thenReturn("12345678");
        when(request.getParameter("confirm-password")).thenReturn("12345678");
        when(request.getParameter("name")).thenReturn("Giovanni");
        when(request.getParameter("surname")).thenReturn("rossi");
        when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        when(request.getParameter("role")).thenReturn("personal_trainer");

        // Simula la registrazione del nutrizionista
        doReturn(true).when(regServiceMock).registraProfessionista(any(Professionista.class));

        // Esegui la registrazione
        profService.doPost(request, response);

        // Verifica che la registrazione sia andata a buon fine
        verify(regServiceMock, times(1)).registraProfessionista(any(Professionista.class));  // Verifica che sia stato invocato una volta
    }

    @Test
    public void testRegistrazioneNutrizionistausernameNonValido() throws Exception {
        // Imposta i parametri invalidi per la registrazione del personal trainer
        when(request.getParameter("username")).thenReturn("Nutrizionista_2025_Giovanni_____");
        when(request.getParameter("email")).thenReturn("Nutrizionista@email.com");  // Email non valida
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
    public void testRegistrazionenutrEmailNonValida() throws Exception {
        // Imposta i parametri invalidi per la registrazione del personal trainer
        when(request.getParameter("username")).thenReturn("Nutrizionista_2025");
        when(request.getParameter("email")).thenReturn("Nutrizionista.email.com");  // Email non valida
        when(request.getParameter("password")).thenReturn("12345678");
        when(request.getParameter("confirm-password")).thenReturn("12345678");
        when(request.getParameter("name")).thenReturn("Giovanni");
        when(request.getParameter("surname")).thenReturn("rossi");
        when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        when(request.getParameter("role")).thenReturn("nutrizionista");

        // Controlla che venga lanciata un'IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> servlet.doPost(request, response));
        assertEquals("L'e-mail non rispetta i criteri.", exception.getMessage());
        // Verifica che il messaggio dell'eccezione sia quello atteso (personalizzalo con quello che la tua servlet lancia)
        // assertEquals("L'e-mail non rispetta i criteri.", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraProfessionista(any(Professionista.class));
    }

    @Test
    public void testRegistrazionenutrpassNonValida() throws Exception {
        // Imposta i parametri invalidi per la registrazione del personal trainer
        when(request.getParameter("username")).thenReturn("Nutrizionista_2025");
        when(request.getParameter("email")).thenReturn("Nutrizionista@email.com");  // Email non valida
        when(request.getParameter("password")).thenReturn("123456");
        when(request.getParameter("confirm-password")).thenReturn("12345678");
        when(request.getParameter("name")).thenReturn("Giovanni");
        when(request.getParameter("surname")).thenReturn("rossi");
        when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        when(request.getParameter("role")).thenReturn("nutrizionista");

        // Controlla che venga lanciata un'IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> servlet.doPost(request, response));
        assertEquals("La password non rispetta i criteri di sicurezza.", exception.getMessage());
        // Verifica che il messaggio dell'eccezione sia quello atteso (personalizzalo con quello che la tua servlet lancia)
        // assertEquals("L'e-mail non rispetta i criteri.", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraProfessionista(any(Professionista.class));
    }

    @Test
    public void testRegistrazionenutrpassconNonValida() throws Exception {
        // Imposta i parametri invalidi per la registrazione del personal trainer
        when(request.getParameter("username")).thenReturn("Nutrizionista_2025");
        when(request.getParameter("email")).thenReturn("Nutrizionista@email.com");  // Email non valida
        when(request.getParameter("password")).thenReturn("12345678");
        when(request.getParameter("confirm-password")).thenReturn("123456");
        when(request.getParameter("name")).thenReturn("Giovanni");
        when(request.getParameter("surname")).thenReturn("rossi");
        when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        when(request.getParameter("role")).thenReturn("nutrizionista");

        // Controlla che venga lanciata un'IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> servlet.doPost(request, response));
        assertEquals("La password non rispetta i criteri di sicurezza.", exception.getMessage());
        // Verifica che il messaggio dell'eccezione sia quello atteso (personalizzalo con quello che la tua servlet lancia)
        // assertEquals("L'e-mail non rispetta i criteri.", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraProfessionista(any(Professionista.class));
    }
    @Test
    public void testRegistrazionenutrnomeNonValida() throws Exception {
        // Imposta i parametri invalidi per la registrazione del personal trainer
        when(request.getParameter("username")).thenReturn("Nutrizionistar_2025");
        when(request.getParameter("email")).thenReturn("Nutrizionista@email.com");  // Email non valida
        when(request.getParameter("password")).thenReturn("12345678");
        when(request.getParameter("confirm-password")).thenReturn("12345678");
        when(request.getParameter("name")).thenReturn("Giovanni@@@");
        when(request.getParameter("surname")).thenReturn("rossi");
        when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        when(request.getParameter("role")).thenReturn("nutrizionista");

        // Controlla che venga lanciata un'IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> servlet.doPost(request, response));
        assertEquals("Nome/Cognome non rispetta i criteri.", exception.getMessage());
        // Verifica che il messaggio dell'eccezione sia quello atteso (personalizzalo con quello che la tua servlet lancia)
        // assertEquals("L'e-mail non rispetta i criteri.", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraProfessionista(any(Professionista.class));
    }
    @Test
    public void testRegistrazionenutrcognomeNonValida() throws Exception {
        // Imposta i parametri invalidi per la registrazione del personal trainer
        when(request.getParameter("username")).thenReturn("Nutrizionista_2025");
        when(request.getParameter("email")).thenReturn("Nutrizionista@email.com");  // Email non valida
        when(request.getParameter("password")).thenReturn("12345678");
        when(request.getParameter("confirm-password")).thenReturn("12345678");
        when(request.getParameter("name")).thenReturn("Giovanni");
        when(request.getParameter("surname")).thenReturn("rossi@@@");
        when(request.getParameter("birthdate")).thenReturn("2000-01-01");
        when(request.getParameter("role")).thenReturn("nutrizionista");

        // Controlla che venga lanciata un'IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> servlet.doPost(request, response));
        assertEquals("Nome/Cognome non rispetta i criteri.", exception.getMessage());
        // Verifica che il messaggio dell'eccezione sia quello atteso (personalizzalo con quello che la tua servlet lancia)
        // assertEquals("L'e-mail non rispetta i criteri.", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraProfessionista(any(Professionista.class));
    }
    @Test
    public void testRegistrazionenutrdataNonValida() throws Exception {
        // Imposta i parametri invalidi per la registrazione del personal trainer
        when(request.getParameter("username")).thenReturn("Nutrizionista_2025");
        when(request.getParameter("email")).thenReturn("Nutrizionista@email.com");  // Email non valida
        when(request.getParameter("password")).thenReturn("12345678");
        when(request.getParameter("confirm-password")).thenReturn("12345678");
        when(request.getParameter("name")).thenReturn("Giovanni");
        when(request.getParameter("surname")).thenReturn("rossi");
        when(request.getParameter("birthdate")).thenReturn("2000/01/01");
        when(request.getParameter("role")).thenReturn("nutrizionista");

        // Controlla che venga lanciata un'IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> servlet.doPost(request, response));
        assertEquals("Data nascita non rispetta i criteri.", exception.getMessage());
        // Verifica che il messaggio dell'eccezione sia quello atteso (personalizzalo con quello che la tua servlet lancia)
        // assertEquals("L'e-mail non rispetta i criteri.", exception.getMessage());

        // Verifica che il servizio di registrazione NON venga mai chiamato (dato che l'input è errato)
        verify(regServiceMock, never()).registraProfessionista(any(Professionista.class));
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
