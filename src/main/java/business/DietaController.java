package business;

import data.entity.Cliente;
import data.entity.Professionista;
import data.entity.Utente;
import data.service.DietaFacade;
import data.service.DietaService;
import data.service.DropboxService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.Enumeration;

import jakarta.servlet.http.HttpSession;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@WebServlet("/dieta")
public class DietaController extends HttpServlet{

    private String dropboxFilePath = "/PT_LINKER/dieteDropbox";  // Percorso nel Dropbox
    private String idcliente;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Professionista professionista = (Professionista) request.getSession().getAttribute("utente");

        // Codice per salvare il file Excel
        System.out.println("Salva Excel");
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Dati Tabella");

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String value = request.getParameter(paramName);

            if (paramName.startsWith("cella_")) {
                String[] indices = paramName.substring(6).split("_");
                int row = Integer.parseInt(indices[0]);
                int col = Integer.parseInt(indices[1]);

                Row excelRow = sheet.getRow(row);
                if (excelRow == null) {
                    excelRow = sheet.createRow(row);
                }
                Cell cell = excelRow.createCell(col);
                cell.setCellValue(value);
            }
        }

        String path = getServletContext().getRealPath("/documenti/Diete") + "/dieta_"+professionista.getId()+"_"+idcliente+".xlsx";

        try (FileOutputStream fos = new FileOutputStream(path)) {
            workbook.write(fos);
        } finally {
            workbook.close();
        }

        DropboxService dropboxService = new DropboxService();
        try {
            dropboxService.uploadFile(path,dropboxFilePath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        DietaService dietaService = new DietaService();
        dietaService.salvaDieta(dropboxFilePath+ "/dieta_"+professionista.getId()+"_"+idcliente+".xlsx",professionista.getId(), Integer.parseInt(idcliente));

        professionista.notificaCliente(idcliente,"aggiunta/modificata dieta");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("pagina") != null) {
            System.out.println("entra in if");
            request.getSession().removeAttribute("pagina"); // Elimina solo la variabile "pagina"
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/gestione_dieta.jsp");
            dispatcher.forward(request, response);
        }else {
            System.out.println("entra in else");
            // Crea il Facade
            DietaFacade dietaFacade = new DietaFacade();
            Utente utente = (Utente) request.getSession().getAttribute("utente");
            if(utente instanceof Professionista)
                idcliente = (String) request.getSession().getAttribute("clienteId");
            else{
                Cliente c= (Cliente) utente;
                idcliente= String.valueOf(c.getId());
            }
            System.out.println("idCliente: "+idcliente);

            // Percorso della cartella "Diete"
            String dieteFolderPath = getServletContext().getRealPath("/documenti/Diete");
            dietaFacade.ensureLocalFolderExists(dieteFolderPath); // Crea la cartella se non esiste

            // Percorso per il file su Dropbox
            String dropboxFilePath = "/PT_LINKER/dieteDropbox";
            String nomeFile=dietaFacade.getFileName(Integer.parseInt(idcliente));
            String localFilePath="";
            if(nomeFile!=null) {
                System.out.println("nomeFile" + nomeFile);
                localFilePath = dieteFolderPath +File.separator +nomeFile;
                System.out.println("localFilePath" + localFilePath);
                System.out.println("Dropbox" + dropboxFilePath);

                // Scarica il file da Dropbox
                dietaFacade.downloadDietaFile(dropboxFilePath, localFilePath);
            }
            // Controlla se il file esiste
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            if (new File(localFilePath).exists()) {
                // Se il file esiste, genera e visualizza la tabella HTML dal file Excel
                String htmlTable = dietaFacade.generateHtmlTableFromExcel(new File(localFilePath),utente);
                out.println(htmlTable);
            } else {
                // Se il file non esiste, visualizza una tabella vuota
                out.println("<html><body>");
                out.println("<form method='post' action='dieta'>");
                out.println("<table id='tabella'>");
                out.println("<tr><th>Tipo Pasti</th><th>Lunedì</th><th>Martedì</th><th>Mercoledì</th><th>Giovedì</th><th>Venerdì</th><th>Sabato</th><th>Domenica</th></tr>");
                String[] tipiPasti = {"Colazione", "Merenda", "Pranzo", "Merenda", "Cena", "Merenda"};
                for (int rowNum = 0; rowNum < 6; rowNum++) {
                    out.println("<tr>");
                    out.println("<td>" + tipiPasti[rowNum] + "</td>");
                    for (int colNum = 0; colNum < 7; colNum++) {
                        out.println("<td><input type='text' name='cella_" + rowNum + "_" + colNum + "' id='cella_" + rowNum + "_" + colNum + "' value='' maxlength='150'></td>");
                    }
                    if(utente instanceof Professionista)
                        out.println("<td><button type='button' onclick='svuotaRiga(" + rowNum + ")'>-</button></td>");
                    out.println("</tr>");
                }
                out.println("</table>");
                out.println("</form>");
                out.println("</body>");
                out.println("</html>");
            }
        }
    }
}
