package business;

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
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@WebServlet("/dieta")
public class DietaController extends HttpServlet{

    private String dropboxFilePath = "/PT_LINKER/dieteDropbox";  // Percorso nel Dropbox

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        String path = getServletContext().getRealPath("/WEB-INF") + "/tabella.xlsx";

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
        //dietaService.salvaDieta(dropboxFilePath,idP,idC);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

    //STAMPA EXCEL
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DropboxService dropboxService = new DropboxService();

        // Costruisce il percorso completo della cartella "Diete" all'interno di WEB-INF
        String dieteFolderPath = getServletContext().getRealPath("/WEB-INF/Diete");

        // Verifica se la cartella "Diete" esiste, altrimenti la crea
        File dieteFolder = new File(dieteFolderPath);
        if (!dieteFolder.exists()) {
            if (dieteFolder.mkdirs()) {
                System.out.println("Cartella 'Diete' creata con successo: " + dieteFolderPath);
            } else {
                System.out.println("Errore nella creazione della cartella 'Diete': " + dieteFolderPath);
            }
        }

        // Chiama la funzione di download passando il percorso della cartella "Diete"
        dropboxService.downloadFile(dropboxFilePath+"/tabella.xlsx", dieteFolderPath+"/tabella.xlsx");

        String filePath = getServletContext().getRealPath("/WEB-INF/Diete/tabella.xlsx");
        File file = new File(filePath);

        // Imposta il tipo di contenuto come HTML
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if (file.exists()) {
            // Se il file esiste, leggi il file Excel e crea la struttura HTML
            try {
                // Carica il file Excel
                FileInputStream fis = new FileInputStream(file);
                XSSFWorkbook workbook = new XSSFWorkbook(fis);
                XSSFSheet sheet = workbook.getSheetAt(0); // Supponiamo di leggere solo il primo foglio

                out.println("<html><body>");
                out.println("<form method='post' action='dieta'>");
                out.println("<table id='tabella'>");

                // Prima riga con i giorni della settimana
                out.println("<tr>");
                out.println("<th>Tipo Pasti</th>");
                out.println("<th>Lunedì</th>");
                out.println("<th>Martedì</th>");
                out.println("<th>Mercoledì</th>");
                out.println("<th>Giovedì</th>");
                out.println("<th>Venerdì</th>");
                out.println("<th>Sabato</th>");
                out.println("<th>Domenica</th>");
                out.println("</tr>");

                // Itera sulle righe e colonne del foglio Excel
                for (int rowNum = 0; rowNum < 6; rowNum++) {  // Fissiamo a 7 righe (escludendo la prima riga con i giorni)
                    XSSFRow row = sheet.getRow(rowNum);
                    out.println("<tr>");

                    // Aggiungi l'etichetta tipo pasto
                    String tipoPasto = "";
                    switch (rowNum) {
                        case 0: tipoPasto = "Colazione"; break;
                        case 1: tipoPasto = "Merenda"; break;
                        case 2: tipoPasto = "Pranzo"; break;
                        case 3: tipoPasto = "Merenda"; break;
                        case 4: tipoPasto = "Cena"; break;
                        case 5: tipoPasto = "Merenda"; break;
                    }
                    out.println("<td>" + tipoPasto + "</td>");

                    // Aggiungi le celle con i valori dal file Excel
                    for (int colNum = 0; colNum < 7; colNum++) {  // Fissiamo a 7 colonne (giorni della settimana)
                        XSSFCell cell = row != null ? row.getCell(colNum) : null;
                        String cellValue = cell != null ? cell.toString() : "";
                        out.println("<td><input type='text' name='cella_" + rowNum + "_" + colNum + "' id='cella_" + rowNum + "_" + colNum + "' value='" + cellValue + "'></td>");
                    }

                    // Aggiungi il bottone per svuotare la riga
                    out.println("<td><button type='button' onclick='svuotaRiga(" + rowNum + ")'>-</button></td>");

                    out.println("</tr>");
                }

                out.println("</table>");
                out.println("</form>");
                out.println("</body>");
                out.println("</html>");

                workbook.close();
                fis.close();

            } catch (IOException e) {
                out.println("Errore durante la lettura del file Excel");
            }

        } else {
            // Se il file non esiste, restituisci il codice HTML per la creazione della tabella
            response.setContentType("text/html");
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Gestione Tabella</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<form method='post' action='dieta'>");
            out.println("<table id='tabella'>");

            // Prima riga con i giorni della settimana
            out.println("<tr>");
            out.println("<th>Tipo Pasti</th>");
            out.println("<th>Lunedì</th>");
            out.println("<th>Martedì</th>");
            out.println("<th>Mercoledì</th>");
            out.println("<th>Giovedì</th>");
            out.println("<th>Venerdì</th>");
            out.println("<th>Sabato</th>");
            out.println("<th>Domenica</th>");
            out.println("</tr>");

            // Creazione della tabella con 7 righe e 7 colonne, tutte vuote
            String[] tipiPasti = {"Colazione", "Merenda", "Pranzo", "Merenda", "Cena", "Merenda"};
            for (int rowNum = 0; rowNum < 6; rowNum++) {
                out.println("<tr>");

                // Aggiungi l'etichetta tipo pasto
                out.println("<td>" + tipiPasti[rowNum] + "</td>");

                // Creazione delle celle vuote per ciascun giorno
                for (int colNum = 0; colNum < 7; colNum++) {
                    out.println("<td><input type='text' name='cella_" + rowNum + "_" + colNum + "' id='cella_" + rowNum + "_" + colNum + "' value=''></td>");
                }

                // Aggiungi il bottone per svuotare la riga
                out.println("<td><button type='button' onclick='svuotaRiga(" + rowNum + ")'>-</button></td>");

                out.println("</tr>");
            }

            out.println("</table>");
            out.println("<input type='submit' value='Invio'>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
