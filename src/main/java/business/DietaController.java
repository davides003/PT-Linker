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

    private String dropboxFilePath = "/dieteDropbox/tabella.xlsx";  // Percorso nel Dropbox

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Codice per salvare il file Excel
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
        dropboxService.uploadFile(path,dropboxFilePath);

        DietaService dietaService = new DietaService();
        //dietaService.salvaDieta(dropboxFilePath,idP,idC);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

    //STAMPA EXCEL
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DropboxService dropboxService = new DropboxService();
        dropboxService.downloadFile(dropboxFilePath, getServletContext().getRealPath("/WEB-INF"));

        String filePath = getServletContext().getRealPath("/WEB-INF/tabella.xlsx");
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

                // Itera sulle righe e colonne del foglio Excel
                for (int rowNum = 0; rowNum < sheet.getPhysicalNumberOfRows(); rowNum++) {
                    XSSFRow row = sheet.getRow(rowNum);
                    out.println("<tr>");
                    for (int colNum = 0; colNum < row.getPhysicalNumberOfCells(); colNum++) {
                        XSSFCell cell = row.getCell(colNum);
                        String cellValue = cell != null ? cell.toString() : "";

                        // Aggiungi un campo di input per ogni cella
                        out.println("<td><input type='text' name='cella_" + rowNum + "_" + colNum + "' id='cella_" + rowNum + "_" + colNum + "' value='" + cellValue + "'></td>");
                    }
                    out.println("</tr>");
                }

                out.println("</table>");
                out.println("<input type='button' onclick='aggiungiColonna()' value='Add Colonna'>");
                out.println("<input type='button' onclick='aggiungiRiga()' value='Add Riga'><br>");

                out.println("<input type='submit' value='Salva Modifiche'>");
                out.println("</form>");
                out.println("</body></html>");

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
            out.println("<title>JSP - Hello World</title>");
            out.println("<script>");
            out.println("function aggiungiRiga(){");
            out.println("    const tabella = document.getElementById('tabella');");
            out.println("    const riga1 = tabella.rows[0];");
            out.println("    const numColonne = riga1.cells.length;");
            out.println("    const indRiga = tabella.rows.length;");
            out.println("    const nuovaRiga = tabella.insertRow();");
            out.println("    nuovaRiga.id = 'riga' + indRiga;");
            out.println("    for (let i = 0; i < numColonne; i++) {");
            out.println("        const nuovaCella = nuovaRiga.insertCell();");
            out.println("        nuovaCella.id = 'colonna' + i;");
            out.println("        const input = document.createElement('input');");
            out.println("        input.type = 'text';");
            out.println("        input.name = 'cella_' + indRiga + '_' + i;");
            out.println("        input.id = 'cella_' + indRiga + '_' + i;");
            out.println("        input.value = 'cella_' + indRiga + '_' + i;");
            out.println("        nuovaCella.appendChild(input);");
            out.println("    }");
            out.println("}");
            out.println("function aggiungiColonna(){");
            out.println("    const tabella = document.getElementById('tabella');");
            out.println("    for (let i = 0; i < tabella.rows.length; i++) {");
            out.println("        const nuovaCella = tabella.rows[i];");
            out.println("        const numColonna = nuovaCella.cells.length;");
            out.println("        const nCell = nuovaCella.insertCell();");
            out.println("        nCell.id = 'colonna' + numColonna;");
            out.println("        const input = document.createElement('input');");
            out.println("        input.type = 'text';");
            out.println("        input.name = 'cella_' + i + '_' + numColonna;");
            out.println("        input.id = 'cella_' + i + '_' + numColonna;");
            out.println("        input.value = 'cella_' + i + '_' + numColonna;");
            out.println("        nCell.appendChild(input);");
            out.println("    }");
            out.println("}");
            out.println("</script>");
            out.println("</head>");
            out.println("<body>");
            out.println("<form id='formTabella' method='post' action='dieta'>");
            out.println("<table id='tabella'>");
            out.println("<tr id='riga0'>");
            out.println("<td id='colonna0'><input type='text' name='cella_0_0' id='cella_0_0' value='cella_0_0'></td>");
            out.println("<td id='colonna1'><input type='text' name='cella_0_1' id='cella_0_1' value='cella_0_1'></td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("<input type='button' onclick='aggiungiColonna()' value='Add Colonna'>");
            out.println("<input type='button' onclick='aggiungiRiga()' value='Add Riga'>");
            out.println("<input type='submit' value='Invio'>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
