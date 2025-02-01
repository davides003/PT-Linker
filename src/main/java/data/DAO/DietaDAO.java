package data.DAO;

import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;

public class DietaDAO {
    private Connection conn;

    public DietaDAO(){
        try {
            conn=CollegamentoDB.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Funzione per salvare solo il percorso del file Excel nel database
    public boolean salvaDietaInDatabase(String percorsoFile, int professionistaCodice, int clienteCodice) {
        // Prepara la query SQL per l'inserimento del percorso del file
        String sql = "INSERT INTO DIETA (Data_Scheda, Percorso_File, PROFESSIONISTA_CODICE, CLIENTE_CODICE) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Imposta i valori nei parametri della query
            stmt.setDate(1, Date.valueOf(LocalDate.now())); // Imposta la data di oggi
            stmt.setString(2, percorsoFile);  // Percorso_File
            stmt.setInt(3, professionistaCodice);  // PROFESSIONISTA_CODICE
            stmt.setInt(4, clienteCodice);  // CLIENTE_CODICE

            // Esegui l'inserimento
            stmt.executeUpdate();
            return true;  // Se l'inserimento va a buon fine, ritorna true
        } catch (SQLException e) {
            e.printStackTrace();  // Stampa l'errore
            return false;  // Se si verifica un errore, ritorna false
        }
    }

    public String getFileName(int idCliente){
        String nomeFile = null;
        String sql = "SELECT Percorso_File FROM DIETA WHERE CLIENTE_CODICE = ? ORDER BY Data_Scheda DESC LIMIT 1";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1,idCliente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String percorsoFile = rs.getString("Percorso_File");
                // Estrai solo il nome del file dal percorso
                if (percorsoFile != null) {
                    nomeFile = Paths.get(percorsoFile).getFileName().toString();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nomeFile;
    }
}
