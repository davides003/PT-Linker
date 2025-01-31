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
            int idcliente= Integer.parseInt((String) session.getAttribute("clienteId"));
            Progressi progressi=ps.getProgressi(idcliente);

            System.out.println("SESSIONE APERTA");

            // Usa Gson per serializzare l'oggetto "progressi" in JSON
            Gson gson = new Gson();
            String jsonProgressi = gson.toJson(progressi);

            // Imposta il tipo di contenuto della risposta come JSON
            response.setContentType("application/json");
            response.getWriter().write(jsonProgressi);
        }else{
            request.getRequestDispatcher("WEB-INF/progressi_cliente.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Utente utente = (Utente) request.getSession().getAttribute("utente");
        if(utente instanceof Cliente) {
            try {
                Cliente cliente = (Cliente) utente;
                //Legge i dati numerici e la descrizione
                float peso = Float.parseFloat(request.getParameter("peso"));
                float girovita = Float.parseFloat(request.getParameter("girovita"));
                float braccioDx = Float.parseFloat(request.getParameter("braccioDx"));
                float braccioSx = Float.parseFloat(request.getParameter("braccioSx"));
                float torace = Float.parseFloat(request.getParameter("torace"));
                float gambaDx = Float.parseFloat(request.getParameter("gambaDx"));
                float gambaSx = Float.parseFloat(request.getParameter("gambaSx"));
                String descrizione = request.getParameter("descrizione");
                String idCliente = String.valueOf(cliente.getId()); // Potrebbe essere null

                ArrayList<String> fotoPath = new ArrayList<>();
                salvaFotoProgressi(request, fotoPath, idCliente);

                Progressi pr = new Progressi(Integer.parseInt(idCliente), fotoPath, descrizione, 0, peso, girovita, braccioDx, braccioSx, torace, gambaDx, gambaSx);
                ProgressiService ps = new ProgressiService();
                ps.registraProgressi(pr);
                ps.registraFotoProgressi(fotoPath);
                //Stampa i dati ricevuti nella console del server
                System.out.println("📩 Dati ricevuti:");
                System.out.println("ID Cliente: " + idCliente);
                System.out.println("Peso: " + peso);
                System.out.println("Girovita: " + girovita);
                System.out.println("Braccio DX: " + braccioDx);
                System.out.println("Braccio SX: " + braccioSx);
                System.out.println("Torace: " + torace);
                System.out.println("Gamba DX: " + gambaDx);
                System.out.println("Gamba SX: " + gambaSx);
                System.out.println("Descrizione: " + descrizione);
                //System.out.println("Foto salvata in: " + file.getAbsolutePath());
                request.getRequestDispatcher("/WEB-INF/home_cliente.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Errore durante il salvataggio dei progressi: " + e.getMessage());
                request.getRequestDispatcher("errore.jsp").forward(request, response);
            }
        }else{
            //PROFESSIONISTA FEEDBACK

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
