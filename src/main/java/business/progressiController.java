package business;
import com.google.gson.Gson;
import data.entity.Cliente;
import data.entity.Professionista;
import data.entity.Progressi;
import data.entity.Utente;
import data.service.ProgressiService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


@WebServlet("/progressiController")

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB soglia per scrivere su disco
        maxFileSize = 5 * 1024 * 1024,   // 5MB max per singolo file
        maxRequestSize = 10 * 1024 * 1024 // 10MB max per l'intera richiesta
)

public class progressiController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Percorso dove salvare le immagini
    private static final String UPLOAD_DIRECTORY = "foto_clienti";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente u= (Utente) request.getSession().getAttribute("utente");
        System.out.println("CIAO "+u.getNome());
        if(u instanceof Professionista) {
            System.out.println("IF PROFESSIONISTA CONTROLLER");
            ProgressiService ps = new ProgressiService();
            HttpSession session = request.getSession();
            System.out.println(session.getAttribute("clienteId"));
            try {
                int idcliente = Integer.parseInt((String) session.getAttribute("clienteId"));
                Progressi progressi = ps.getProgressi(idcliente);

                System.out.println("SESSIONE APERTA");

                // Usa Gson per serializzare l'oggetto "progressi" in JSON
                Gson gson = new Gson();
                String jsonProgressi = gson.toJson(progressi);

                // Imposta il tipo di contenuto della risposta come JSON
                response.setContentType("application/json");
                response.getWriter().write(jsonProgressi);
            }catch(Exception e) {
                throw new ServletException(e);
            }
        }else{
            request.getRequestDispatcher("WEB-INF/progressi_cliente.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Utente utente = (Utente) request.getSession().getAttribute("utente");
        if(utente instanceof Cliente) {
            Float altezza = null;
            Float peso = null;
            Float girovita = null;
            Float circonferenzaBraccioDestro = null;
            Float circonferenzaBraccioSinistro = null;
            Float circonferenzaGambaDestra = null;
            Float circonferenzaGambaSinistra = null;
            Float circonferenzaTorace = null;
            boolean valid=true;
            try {
                Cliente cliente = (Cliente) utente;
                //Legge i dati numerici e la descrizione
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
                    circonferenzaBraccioDestro = Float.parseFloat(request.getParameter("braccioDx"));
                    if (circonferenzaBraccioDestro <= 0 || circonferenzaBraccioDestro > 100) {
                        valid = false;
                    }
                }catch(NumberFormatException e){
                    throw new RuntimeException(e);
                }
                try {
                    circonferenzaBraccioSinistro = Float.parseFloat(request.getParameter("braccioSx"));
                    if (circonferenzaBraccioSinistro <= 0 || circonferenzaBraccioSinistro > 100) {
                        valid = false;
                    }
                }catch(NumberFormatException e){
                    throw new RuntimeException(e);
                }
                try {
                    circonferenzaGambaDestra = Float.parseFloat(request.getParameter("gambaDx"));
                    if (circonferenzaGambaDestra <= 0 || circonferenzaGambaDestra > 100) {
                        valid = false;
                    }
                }catch(NumberFormatException e){
                    throw new RuntimeException(e);
                }
                try {
                    circonferenzaGambaSinistra = Float.parseFloat(request.getParameter("gambaSx"));
                    if (circonferenzaGambaSinistra <= 0 || circonferenzaGambaSinistra > 100) {
                        valid = false;
                    }
                }catch(NumberFormatException e){
                    throw new RuntimeException(e);
                }
                try {
                    circonferenzaTorace = Float.parseFloat(request.getParameter("torace"));
                    if (circonferenzaTorace <= 0 || circonferenzaTorace > 200) {
                        valid = false;
                    }
                }catch(NumberFormatException e){
                    throw new RuntimeException(e);
                }
                String codicePr = request.getParameter("nutrizionista");

                if (!valid) {
                    throw new IllegalArgumentException("Dati nutrizionista non rispettano i criteri");
                }

                String descrizione = request.getParameter("descrizione");
                if(descrizione.length()<0 || descrizione.length() > 550){
                    throw new IllegalArgumentException("Descrizione non rispetta i criteri");
                }

                String idCliente = String.valueOf(cliente.getId()); // Potrebbe essere null
                if(idCliente == null || idCliente.isEmpty()) {
                    throw new IllegalArgumentException("idCliente null");
                }

                ArrayList<String> fotoPath = new ArrayList<>();
                salvaFotoProgressi(request, fotoPath, idCliente);

                Progressi pr = new Progressi(Integer.parseInt(idCliente), fotoPath, descrizione, 0, peso, girovita, circonferenzaBraccioDestro, circonferenzaBraccioSinistro, circonferenzaTorace, circonferenzaGambaDestra, circonferenzaGambaSinistra);
                ProgressiService ps = new ProgressiService();
                ps.registraProgressi(pr);
                ps.registraFotoProgressi(fotoPath);
                //Stampa i dati ricevuti nella console del server
                System.out.println("📩 Dati ricevuti:");
                System.out.println("ID Cliente: " + idCliente);
                System.out.println("Peso: " + peso);
                System.out.println("Girovita: " + girovita);
                System.out.println("Braccio DX: " + circonferenzaBraccioDestro);
                System.out.println("Braccio SX: " + circonferenzaBraccioSinistro);
                System.out.println("Torace: " + circonferenzaTorace);
                System.out.println("Gamba DX: " + circonferenzaGambaDestra);
                System.out.println("Gamba SX: " + circonferenzaGambaSinistra);
                System.out.println("Descrizione: " + descrizione);
                //System.out.println("Foto salvata in: " + file.getAbsolutePath());
                request.getRequestDispatcher("/WEB-INF/home_cliente.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServletException(e);
            }
        }else{
            //PROFESSIONISTA FEEDBACK
            throw new IllegalArgumentException("Data nutrizionista non rispetta i criteri");
        }
    }

    private void salvaFotoProgressi(HttpServletRequest request, ArrayList<String> fotoPercors, String id) throws ServletException, IOException {
        String UPLOAD_DIR = "image/Foto_Progressi";

        // Ottieni il percorso assoluto della directory "certificati"
        String uploadPath = getServletContext().getRealPath("") + UPLOAD_DIR;

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
                if (part.getName().equals("foto") && part.getSize() > 0) {
                    // Ottieni il nome del file
                    String fileName = getFileName(part);

                    // Estrai l'estensione del file (es: ".pdf", ".xlsx")
                    String fileExtension = "";
                    int lastDotIndex = fileName.lastIndexOf('.');
                    if (lastDotIndex > 0) {
                        fileExtension = fileName.substring(lastDotIndex);
                    }

                    // Genera un nuovo nome per il file (ad esempio, con un timestamp)
                    String newFileName = "progressiFoto_" + id + "_" + numCert+fileExtension;
                    numCert++;

                    // Crea il percorso completo del file rinominato
                    String newFilePath = uploadPath + File.separator + newFileName;
                    System.out.println("newFilePath: " + newFilePath);

                    fotoPercors.add(newFilePath);

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
