package data.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
        String sql = "INSERT INTO DIETA (Percorso_File, PROFESSIONISTA_CODICE, CLIENTE_CODICE) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Imposta i valori nei parametri della query
            stmt.setString(1, percorsoFile);  // Percorso_File
            stmt.setInt(2, professionistaCodice);  // PROFESSIONISTA_CODICE
            stmt.setInt(3, clienteCodice);  // CLIENTE_CODICE

            // Esegui l'inserimento
            stmt.executeUpdate();
            return true;  // Se l'inserimento va a buon fine, ritorna true
        } catch (SQLException e) {
            e.printStackTrace();  // Stampa l'errore
            return false;  // Se si verifica un errore, ritorna false
        }
    }
}
