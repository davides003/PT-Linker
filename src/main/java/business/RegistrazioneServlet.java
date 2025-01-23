package business;

import data.entity.Cliente;
import data.entity.Professionista;
import data.service.RegistrazioneService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.Part;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.IOException;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;


@WebServlet("/RegistrazioneServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB per file
        maxRequestSize = 1024 * 1024 * 50    // 50 MB per richiesta
)
public class RegistrazioneServlet extends HttpServlet {
    // Gestisce la navigazione e l'invio del form
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Se il metodo è GET, l'utente sta cercando di navigare alla pagina di login
        // Quindi mostriamo il form di login
        request.getRequestDispatcher("WEB-INF/registrazione.jsp").forward(request, response);
    }

    // Gestisce la raccolta dei dati dal form e la logica di login
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Leggi i campi principali del form
        int id = 11; // Integer.parseInt(request.getParameter("CODICE"));
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String nome = request.getParameter("name");
        String cognome = request.getParameter("surname");
        String dataNascita = request.getParameter("birthdate");
        String ruolo = request.getParameter("role");
        System.out.println("username"+username+" \nRuolo "+ruolo);

        String hashPass = null;
        CodificaPass cod = new CodificaPass();
        try {
            hashPass = cod.toHash(password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        // Inizializza i campi aggiuntivi
        Float altezza = null;
        Float peso = null;
        Float girovita = null;
        Float circonferenzaBraccioDestro = null;
        Float circonferenzaBraccioSinistro = null;
        Float circonferenzaGambaDestra = null;
        Float circonferenzaGambaSinistra = null;
        Float circonferenzaTorace = null;
        ArrayList<String> certificatiPercorsi = new ArrayList<>(); // Lista per memorizzare i percorsi dei certificati

        // Servizio di registrazione
        RegistrazioneService regService = new RegistrazioneService();

        // Verifica se i dati sono già presenti
        boolean presente = regService.verificaDati(username, email);
        if (!presente) {
            // Salvataggio dei dati del professionista o cliente
            if ("cliente".equals(ruolo)) {
                salvaCliente(request, 0, nome, cognome, username, email, hashPass, dataNascita, altezza, peso, girovita, circonferenzaBraccioDestro, circonferenzaBraccioSinistro, circonferenzaTorace, circonferenzaGambaDestra, circonferenzaGambaSinistra);
            } else if ("personal_trainer".equals(ruolo) || "nutrizionista".equals(ruolo)) {
                // Caricamento dei certificati se ruolo professionista
                System.out.println("prima di chiamare salvaCertificati");
                salvaCertificati(request, certificatiPercorsi);
                if (!certificatiPercorsi.isEmpty()) {
                    System.out.println("if empyt certificati");
                    Professionista pr=new Professionista(nome, cognome, username, email, hashPass, dataNascita, 0,null,false, ruolo);
                    regService.registraProfessionista(pr);
                    regService.registraCertificati(certificatiPercorsi);
                    System.out.println("File PDF caricati con successo!");
                } else {
                    System.out.println("Nessun file PDF caricato.");
                }
            }
        } else {
            System.out.println("GIà REGISTRATO!!");
        }

        // Forward alla pagina di login
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login.jsp");
        dispatcher.forward(request, response);
    }

    private void salvaCliente(HttpServletRequest request, int id, String nome, String cognome, String username, String email, String hashPass, String dataNascita, Float altezza, Float peso, Float girovita, Float circonferenzaBraccioDestro, Float circonferenzaBraccioSinistro, Float circonferenzaTorace, Float circonferenzaGambaDestra, Float circonferenzaGambaSinistra) {
        altezza = Float.parseFloat(request.getParameter("altezza"));
        peso = Float.parseFloat(request.getParameter("peso"));
        girovita = Float.parseFloat(request.getParameter("girovita"));
        circonferenzaBraccioDestro = Float.parseFloat(request.getParameter("circonferenza-braccio-destro"));
        circonferenzaBraccioSinistro = Float.parseFloat(request.getParameter("circonferenza-braccio-sinistro"));
        circonferenzaGambaDestra = Float.parseFloat(request.getParameter("circonferenza-gamba-destra"));
        circonferenzaGambaSinistra = Float.parseFloat(request.getParameter("circonferenza-gamba-sinistra"));
        circonferenzaTorace = Float.parseFloat(request.getParameter("circonferenza-torace"));

        Cliente cliente = new Cliente(nome, cognome, username, email, hashPass, dataNascita, id, altezza, peso, girovita, circonferenzaBraccioDestro, circonferenzaBraccioSinistro, circonferenzaTorace, circonferenzaGambaDestra, circonferenzaGambaSinistra);
        RegistrazioneService regService = new RegistrazioneService();
        boolean esitoReg = regService.registraCliente(cliente);
        if (!esitoReg) {
            System.out.println("FAIL registrazione cliente");
        }
    }

    private void salvaCertificati(HttpServletRequest request, ArrayList<String> certificatiPercors) throws ServletException, IOException {
        String UPLOAD_DIR = "/WEB-INF/certificati";

        // Ottieni il percorso assoluto della directory "certificati"
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;

        // Crea la directory se non esiste
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        try {
            // Itera su tutti i file caricati
            for (Part part : request.getParts()) {
                // Controlla se la parte è un file
                if (part.getName().equals("certificati") && part.getSize() > 0) {
                    // Ottieni il nome del file
                    String fileName = getFileName(part);
                    certificatiPercors.add(uploadPath + File.separator + fileName);

                    // Salva il file nella directory specificata
                    part.write(uploadPath + File.separator + fileName);
                }
            }

            // Messaggio di successo
            System.out.println("File caricati con successo nella directory: " + uploadPath);
        } catch (Exception e) {
            // Gestione degli errori
            e.printStackTrace();
            System.out.println("Errore durante il caricamento dei file: " + e.getMessage());
        }
    }

    // Metodo per estrarre il nome del file dall'intestazione della parte
    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String content : contentDisposition.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return null;
    }
}
