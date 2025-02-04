package business;

import data.entity.Cliente;
import data.entity.Professionista;
import data.service.DropboxService;
import data.service.RegistrazioneService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;


@WebServlet("/RegistrazioneServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB per file
        maxRequestSize = 1024 * 1024 * 50    // 50 MB per richiesta
)
public class RegistrazioneServlet extends HttpServlet {
    private RegistrazioneService rs=new RegistrazioneService();
    // Gestisce la navigazione e l'invio del form
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //rs = new RegistrazioneService();
        // Ottieni l'elenco dei nutrizionisti
        ArrayList<Professionista> nutrizionisti = rs.getProfessionista();

        // Imposta la lista dei nutrizionisti come attributo della request
        request.setAttribute("nutrizionisti", nutrizionisti);

        // Se il metodo è GET, l'utente sta cercando di navigare alla pagina di login
        // Quindi mostriamo il form di login
        request.getRequestDispatcher("WEB-INF/registrazione.jsp").forward(request, response);
    }

    // Gestisce la raccolta dei dati dal form e la logica di login
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Leggi i campi principali del form
        String regexMail = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        String usernameRegex = "^[a-zA-Z0-9_]+$";
        String nameRegex = "^[a-zA-Z]+$"; // Solo lettere
        String dataNascitaRegex = "^\\d{4}-\\d{2}-\\d{2}$"; // Verifica formato yyyy-MM-dd


        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confPaassword = request.getParameter("confirm-password");
        String nome = request.getParameter("name");
        String cognome = request.getParameter("surname");
        String dataNascita = request.getParameter("birthdate");
        String ruolo = request.getParameter("role");
        System.out.println("username "+username+"\nEmail "+email+" \nRuolo "+ruolo);

        //Controlli
        if(!username.matches(usernameRegex) || username.length() < 3 || username.length() > 30) {
            throw new IllegalArgumentException("Invalid username");
        }
        if(email==null || !email.matches(regexMail) || email.isEmpty()){
            throw new IllegalArgumentException("L'e-mail non rispetta i criteri.");
        }
        if(password==null || password.length()<8 || password.length()>24 || !password.equals(confPaassword)){
            throw new IllegalArgumentException("La password non rispetta i criteri di sicurezza.");
        }
        if(nome==null || nome.isEmpty() || cognome==null || cognome.isEmpty() || !nome.matches(nameRegex) || !cognome.matches(nameRegex)){
            throw new IllegalArgumentException("Nome/Cognome non rispetta i criteri.");
        }
        if(dataNascita==null || dataNascita.isEmpty() || !dataNascita.matches(dataNascitaRegex)){
            throw new IllegalArgumentException("Data nascita non rispetta i criteri.");
        }
        if(ruolo==null || ruolo.isEmpty() || ruolo.isBlank()){
            throw new IllegalArgumentException("Ruolo non rispetta i criteri.");
        }

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
        //rs = new RegistrazioneService();

        // Verifica se i dati sono già presenti
        boolean presente = rs.verificaDati(username, email);
        if (!presente) {
            // Salvataggio dei dati del professionista o cliente
            if ("cliente".equals(ruolo)) {
                salvaCliente(request, 0, nome, cognome, username, email, hashPass, dataNascita, altezza, peso, girovita, circonferenzaBraccioDestro, circonferenzaBraccioSinistro, circonferenzaTorace, circonferenzaGambaDestra, circonferenzaGambaSinistra);
            } else if ("personal_trainer".equals(ruolo) || "nutrizionista".equals(ruolo)) {
                int idUltimoProfessionista= rs.getIdProfessionista();
                // Caricamento dei certificati se ruolo professionista
                System.out.println("prima di chiamare salvaCertificati");
                //salvaCertificati(request, certificatiPercorsi,idUltimoProfessionista+1);
                DropboxService dbS= new DropboxService();
                //if (!certificatiPercorsi.isEmpty()) {
                    System.out.println("if empyt certificati");
                    Professionista pr=new Professionista(nome, cognome, username, email, hashPass, dataNascita, idUltimoProfessionista+1,certificatiPercorsi,false, ruolo);
                    if(!rs.registraProfessionista(pr)){
                        throw new RuntimeException("Registrazione Professionista Fallita");
                    }
                    rs.registraCertificati(certificatiPercorsi);
                    System.out.println("File PDF caricati con successo!");
                /*} else {
                    System.out.println("Nessun file PDF caricato.");
                }*/
            }
        } else {
           throw new RuntimeException("Utente già registrato");
        }

        // Forward alla pagina di login
        //RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login.jsp");
        //dispatcher.forward(request, response);
    }

    public void salvaCliente(HttpServletRequest request, int id, String nome, String cognome, String username, String email, String hashPass, String dataNascita, Float altezza, Float peso, Float girovita, Float circonferenzaBraccioDestro, Float circonferenzaBraccioSinistro, Float circonferenzaTorace, Float circonferenzaGambaDestra, Float circonferenzaGambaSinistra) {
        boolean valid=true;

        try {
            altezza = Float.parseFloat(request.getParameter("altezza"));
            if (altezza <= 0 || altezza > 200) {
                valid = false;
            }
        }catch(NumberFormatException e){
            throw new RuntimeException(e);
        }
        try {
            peso = Float.parseFloat(request.getParameter("peso"));
            if (peso <= 0 || peso > 200) {
                valid = false;
            }
        }catch(NumberFormatException e){
            throw new RuntimeException(e);
        }
        try {
            girovita = Float.parseFloat(request.getParameter("girovita"));
            if (girovita <= 0 || girovita > 200) {
                valid = false;
            }
        }catch(NumberFormatException e){
            throw new RuntimeException(e);
        }
        try {
            circonferenzaBraccioDestro = Float.parseFloat(request.getParameter("circonferenza-braccio-destro"));
            if (circonferenzaBraccioDestro <= 0 || circonferenzaBraccioDestro > 100) {
                valid = false;
            }
        }catch(NumberFormatException e){
            throw new RuntimeException(e);
        }
        try {
            circonferenzaBraccioSinistro = Float.parseFloat(request.getParameter("circonferenza-braccio-sinistro"));
            if (circonferenzaBraccioSinistro <= 0 || circonferenzaBraccioSinistro > 100) {
                valid = false;
            }
            }catch(NumberFormatException e){
            throw new RuntimeException(e);
        }
        try {
            circonferenzaGambaDestra = Float.parseFloat(request.getParameter("circonferenza-gamba-destra"));
            if (circonferenzaGambaDestra <= 0 || circonferenzaGambaDestra > 100) {
                valid = false;
            }
        }catch(NumberFormatException e){
            throw new RuntimeException(e);
        }
        try {
            circonferenzaGambaSinistra = Float.parseFloat(request.getParameter("circonferenza-gamba-sinistra"));
            if (circonferenzaGambaSinistra <= 0 || circonferenzaGambaSinistra > 100) {
                valid = false;
            }
        }catch(NumberFormatException e){
            throw new RuntimeException(e);
        }
        try {
            circonferenzaTorace = Float.parseFloat(request.getParameter("circonferenza-torace"));
            if (circonferenzaTorace <= 0 || circonferenzaTorace > 200) {
                valid = false;
            }
        }catch(NumberFormatException e){
            throw new RuntimeException(e);
        }
        String codicePr = request.getParameter("nutrizionista");
        if (!valid) {
            throw new IllegalArgumentException("Data nutrizionista non rispetta i criteri");
        }

        Cliente cliente = new Cliente(nome, cognome, username, email, hashPass, dataNascita, id, altezza, peso, girovita, circonferenzaBraccioDestro, circonferenzaBraccioSinistro, circonferenzaTorace, circonferenzaGambaDestra, circonferenzaGambaSinistra);

        boolean esitoReg = rs.registraCliente(cliente);
        if (!esitoReg) {
            System.out.println("FAIL registrazione cliente");
            throw new RuntimeException("Registrazione Cliente fallita");
        }else{
            rs.abbinaCliente(codicePr);
        }
    }

    private void salvaCertificati(HttpServletRequest request, ArrayList<String> certificatiPercors, int id) throws ServletException, IOException {
        String UPLOAD_DIR = "documenti/certificati";

        // Ottieni il percorso assoluto della directory "certificati"
        String uploadPath = getServletContext().getRealPath("") +  UPLOAD_DIR;

        // Crea la directory se non esiste
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        int numCert=1;

        try {
            // Itera su tutti i file caricati
            for (Part part : request.getParts()) {
                // Controlla se la parte è un file
                if (part.getName().equals("certificati") && part.getSize() > 0) {
                    // Ottieni il nome del file
                    String fileName = getFileName(part);

                    // Estrai l'estensione del file (es: ".pdf", ".xlsx")
                    String fileExtension = "";
                    int lastDotIndex = fileName.lastIndexOf('.');
                    if (lastDotIndex > 0) {
                        fileExtension = fileName.substring(lastDotIndex);
                    }

                    // Genera un nuovo nome per il file (ad esempio, con un timestamp)
                    String newFileName = "certificato_" + id + "_" + numCert+fileExtension;
                    numCert++;

                    // Crea il percorso completo del file rinominato
                    String newFilePath = uploadPath + File.separator + newFileName;
                    System.out.println("newFilePath: " + newFilePath);

                    certificatiPercors.add(newFilePath);

                    // Salva il file nella directory specificata
                    part.write(newFilePath);
                }
            }
            // Messaggio di successo
            System.out.println("File caricati con successo nella directory: " + uploadPath);
        } catch (Exception e) {
            // Gestione degli errori
            e.printStackTrace();
            System.out.println("Errore durante il caricamento dei file: " + e.getMessage());
            throw new ServletException(e);
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
