package business;

import data.DAO.AggiornamentoDAO;
import data.entity.Cliente;
import data.entity.Professionista;
import data.entity.Utente;
import data.service.DropboxService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet("/ProfiloSettingServlet")
public class ProfiloSettingsServlet extends HttpServlet {

    private String dropboxFilePath = "/PT_LINKER/attestatiDropbox";

    // Gestisce la navigazione e l'invio del form
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*Cliente cliente = new Cliente("Davide", "Santillo", "dasan03",
                                "redavidsanti@gmail.com", "123456", "17/11/2003",
                                    11, 1, 1, 1, 1,
                    1, 1, 1, 1);*/

        /*ArrayList<String> list = new ArrayList<>();
        salvaCertificati(request, list);

        Professionista professionista = new Professionista("Davide", "Santillo", "dasan03",
                "redavidsanti@gmail.com", "123456", "17/11/2003", 12, list, false, "Nutrizionista");*/

        //Utente utente = (Utente) request.getSession().getAttribute("utente");
        //Utente utente = Utente.class.cast(professionista);

        //Object utente = cliente;

        //request.setAttribute("utente" , utente);
        request.getRequestDispatcher("/WEB-INF/settings.jsp").forward(request, response);
    }



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupera l'utente dalla sessione
        Utente utente = (Utente) request.getSession().getAttribute("utente");

        if (utente instanceof Cliente) {
            Cliente cliente = (Cliente) utente;

            // Recupera i dati inviati dal form
            String altezza = request.getParameter("Altezza");
            String peso = request.getParameter("Peso");
            String larghezzaGirovita = request.getParameter("larghezzaGirovita");
            String circonferenzaBracciaDx = request.getParameter("circonferenzaBracciaDx");
            String circonferenzaBracciaSx = request.getParameter("circonferenzaBracciaSx");
            String circonferenzaTorace = request.getParameter("circonferenzaTorace");
            String circonferenzaGambaDx = request.getParameter("circonferenzaGambaDx");
            String circonferenzaGambaSx = request.getParameter("circonferenzaGambaSx");

            // Confronta i valori e aggiorna solo quelli che sono stati modificati
            boolean isUpdated = false;

            if (!altezza.equals(cliente.getAltezza())) {
                cliente.setAltezza(Float.parseFloat(altezza) );
                isUpdated = true;
            }
            if (!peso.equals(cliente.getPeso())) {
                cliente.setPeso(Float.parseFloat(peso));
                isUpdated = true;
            }
            if (!larghezzaGirovita.equals(cliente.getLarghezzaGirovita())) {
                cliente.setLarghezzaGirovita(Float.parseFloat(larghezzaGirovita));
                isUpdated = true;
            }
            if (!circonferenzaBracciaDx.equals(cliente.getCirconferenzaBracciaDx())) {
                cliente.setCirconferenzaBracciaDx(Float.parseFloat(circonferenzaBracciaDx));
                isUpdated = true;
            }
            if (!circonferenzaBracciaSx.equals(cliente.getCirconferenzaBracciaSx())) {
                cliente.setCirconferenzaBracciaSx(Float.parseFloat(circonferenzaBracciaSx));
                isUpdated = true;
            }
            if (!circonferenzaTorace.equals(cliente.getCirconferenzaTorace())) {
                cliente.setCirconferenzaTorace(Float.parseFloat(circonferenzaTorace));
                isUpdated = true;
            }
            if (!circonferenzaGambaDx.equals(cliente.getCirconferenzaGambaDx())) {
                cliente.setCirconferenzaGambaDx(Float.parseFloat(circonferenzaGambaDx));
                isUpdated = true;
            }
            if (!circonferenzaGambaSx.equals(cliente.getCirconferenzaGambaSx())) {
                cliente.setCirconferenzaGambaSx(Float.parseFloat(circonferenzaGambaSx));
                isUpdated = true;
            }

            // Aggiorna i dati nel database solo se ci sono stati cambiamenti
            if (isUpdated) {
                // Chiama il DAO o il servizio per aggiornare il cliente
                AggiornamentoDAO clienteDAO = new AggiornamentoDAO();
                boolean success = clienteDAO.updateCliente(cliente);

                if (success) {
                    request.setAttribute("message", "Profilo aggiornato con successo!");
                } else {
                    request.setAttribute("message", "Errore durante l'aggiornamento del profilo.");
                }
            } else {
                request.setAttribute("message", "Nessuna modifica effettuata.");
            }

            // Reindirizza alla pagina del profilo
            request.getRequestDispatcher("profilo.jsp").forward(request, response);

        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accesso non autorizzato.");
        }
    }



    private void salvaCertificati(HttpServletRequest request, ArrayList<String> certificatiPercors) throws ServletException, IOException {
        String UPLOAD_DIR = "WEB-INF"+ File.separator + "certificati";

        /* Ottieni il percorso assoluto della directory "certificati"
        //String uploadPath = getServletContext().getRealPath("") + UPLOAD_DIR;

        // Crea la directory se non esiste
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }*/

        try {/*
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
            }*/

            certificatiPercors.add(UPLOAD_DIR + File.separator + "Attestato_Personal_Trainer.pdf");
            DropboxService dr = new DropboxService();
            dr.uploadFile(certificatiPercors.get(0), "scvc");


            // Messaggio di successo
            System.out.println("File caricati con successo nella directory: " + UPLOAD_DIR);
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
