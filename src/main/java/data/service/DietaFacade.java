package data.service;

import com.dropbox.core.v1.DbxEntry;
import data.entity.Professionista;
import data.entity.Utente;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DietaFacade {

    private DropboxService dropboxService;

    public DietaFacade() {
        this.dropboxService = new DropboxService();
    }

    // Metodo per creare la cartella "Diete" se non esiste
    public void ensureLocalFolderExists(String dieteFolderPath) {
        File dieteFolder = new File(dieteFolderPath);
        if (!dieteFolder.exists()) {
            if (dieteFolder.mkdirs()) {
                System.out.println("Cartella 'Diete' creata con successo: " + dieteFolderPath);
            } else {
                System.out.println("Errore nella creazione della cartella 'Diete': " + dieteFolderPath);
            }
        }
    }

    // Metodo per scaricare il file da Dropbox nella cartella locale
    public void downloadDietaFile(String dropboxFilePath, String dieteFolderPath) {
        dropboxService.download(dropboxFilePath, dieteFolderPath);
    }

    // Metodo per leggere il file Excel e restituire la struttura della tabella
    public String generateHtmlTableFromExcel(File file, Utente u) {
        StringBuilder htmlTable = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(file);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheetAt(0); // Supponiamo di leggere solo il primo foglio

            htmlTable.append("<html><body>");
            htmlTable.append("<form method='post' action='dieta'>");
            htmlTable.append("<table id='tabella'>");

            // Prima riga con i giorni della settimana
            htmlTable.append("<tr>");
            htmlTable.append("<th>Tipo Pasti</th>");
            htmlTable.append("<th>Lunedì</th>");
            htmlTable.append("<th>Martedì</th>");
            htmlTable.append("<th>Mercoledì</th>");
            htmlTable.append("<th>Giovedì</th>");
            htmlTable.append("<th>Venerdì</th>");
            htmlTable.append("<th>Sabato</th>");
            htmlTable.append("<th>Domenica</th>");
            htmlTable.append("</tr>");

            // Itera sulle righe e colonne del foglio Excel
            for (int rowNum = 0; rowNum < 6; rowNum++) {
                XSSFRow row = sheet.getRow(rowNum);
                htmlTable.append("<tr>");

                // Aggiungi l'etichetta tipo pasto
                String tipoPasto = getTipoPasto(rowNum);
                htmlTable.append("<td>").append(tipoPasto).append("</td>");

                // Aggiungi le celle con i valori dal file Excel
                for (int colNum = 0; colNum < 7; colNum++) {
                    XSSFCell cell = row != null ? row.getCell(colNum) : null;
                    String cellValue = cell != null ? cell.toString() : "";
                    htmlTable.append("<td><input type='text' name='cella_").append(rowNum).append("_").append(colNum).append("' id='cella_").append(rowNum).append("_").append(colNum).append("' value='").append(cellValue).append("'></td>");
                }
                if(u instanceof Professionista)// Aggiungi il bottone per svuotare la riga
                    htmlTable.append("<td><button type='button' onclick='svuotaRiga(").append(rowNum).append(")'>-</button></td>");

                htmlTable.append("</tr>");
            }

            htmlTable.append("</table>");
            htmlTable.append("</form>");
            htmlTable.append("</body>");
            htmlTable.append("</html>");

        } catch (IOException e) {
            htmlTable.append("Errore durante la lettura del file Excel");
        }
        return htmlTable.toString();
    }

    // Metodo per restituire il tipo di pasto in base al numero della riga
    private String getTipoPasto(int rowNum) {
        switch (rowNum) {
            case 0: return "Colazione";
            case 1: return "Merenda";
            case 2: return "Pranzo";
            case 3: return "Merenda";
            case 4: return "Cena";
            case 5: return "Merenda";
            default: return "";
        }
    }

    public String getFileName(int idCliente){
        DietaService dietaService = new DietaService();
        return dietaService.getFileName(idCliente);
    }
}
