package data.DAO;

import data.entity.Cliente;
import data.entity.Professionista;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AutenticazioneDAO {
    private Connection database;

    public AutenticazioneDAO() {
        try {
            database=CollegamentoDB.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isRegistrato(String username, String email) {
        try {
            // Modifica la query per verificare se esiste un record con lo stesso Username o E_Mail in CLIENTE o PROFESSIONISTA
            PreparedStatement ps = database.prepareStatement(
                    "SELECT 1 FROM CLIENTE WHERE Username = ? OR E_Mail = ? " +
                            "UNION " +
                            "SELECT 1 FROM PROFESSIONISTA WHERE Username = ? OR E_Mail = ?"
            );

            // Imposta i parametri della query
            ps.setString(1, username);  // Imposta il primo parametro (Username per CLIENTE)
            ps.setString(2, email);     // Imposta il secondo parametro (Email per CLIENTE)
            ps.setString(3, username);  // Imposta il terzo parametro (Username per PROFESSIONISTA)
            ps.setString(4, email);     // Imposta il quarto parametro (Email per PROFESSIONISTA)

            // Esegui la query
            ResultSet rs = ps.executeQuery();

            // Restituisce true se esiste almeno una corrispondenza, altrimenti false
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean salvaCliente(Cliente cliente) {
        String query = "INSERT INTO CLIENTE ( Nome, Cognome, Data_di_Nascita, Username, E_Mail, Password, " +
                "Altezza, Peso, Circonferenza_Giro_Vita, Circonferenza_Braccio_Sx, Circonferenza_Braccio_Dx, " +
                "Circonferenza_Torace, Circonferenza_Gamba_Sx, Circonferenza_Gamba_Dx) " +
                "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = database.prepareStatement(query)) {
            // Impostare i valori dei parametri nel PreparedStatement
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCognome());
            ps.setDate(3, java.sql.Date.valueOf(cliente.getDataDiNascita())); // Assicurati che dataDiNascita sia di tipo LocalDate
            ps.setString(4, cliente.getUsername());
            ps.setString(5, cliente.getEmail());
            ps.setString(6, cliente.getPassword());
            ps.setBigDecimal(7, BigDecimal.valueOf(cliente.getAltezza()));  // Cambia per usare BigDecimal per DECIMAL
            ps.setBigDecimal(8, BigDecimal.valueOf(cliente.getPeso()));  // Cambia per usare BigDecimal per DECIMAL
            ps.setBigDecimal(9, BigDecimal.valueOf(cliente.getLarghezzaGirovita()));  // Cambia per usare BigDecimal per DECIMAL
            ps.setBigDecimal(10, BigDecimal.valueOf(cliente.getCirconferenzaBracciaSx()));  // Cambia per usare BigDecimal per DECIMAL
            ps.setBigDecimal(11, BigDecimal.valueOf(cliente.getCirconferenzaBracciaDx()));  // Cambia per usare BigDecimal per DECIMAL
            ps.setBigDecimal(12, BigDecimal.valueOf(cliente.getCirconferenzaTorace()));  // Cambia per usare BigDecimal per DECIMAL
            ps.setBigDecimal(13, BigDecimal.valueOf(cliente.getCirconferenzaGambaSx()));  // Cambia per usare BigDecimal per DECIMAL
            ps.setBigDecimal(14, BigDecimal.valueOf(cliente.getCirconferenzaGambaDx()));  // Cambia per usare BigDecimal per DECIMAL

            // Esegui l'insert
            int rowsAffected = ps.executeUpdate();

            // Se il numero di righe modificate è maggiore di 0, significa che l'inserimento è riuscito
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Se c'è un errore, restituisce false
        }
    }

    public boolean salvaProfessionista(Professionista p) {
        String queryProfessionista = "INSERT INTO PROFESSIONISTA (Nome, Cognome, Data_di_Nascita, Username, E_Mail, Password, Tipo, ADMIN_CODICE) " +
                "VALUES ( ?, ?, ?, ?, ?, ?, ?, null)";

        try {
            // Inizia una transazione
            database.setAutoCommit(false);

            // Inserire il professionista nella tabella PROFESSIONISTA
            try (PreparedStatement psProfessionista = database.prepareStatement(queryProfessionista)) {
                //psProfessionista.setInt(1, id);
                psProfessionista.setString(1, p.getNome());
                psProfessionista.setString(2, p.getCognome());
                psProfessionista.setDate(3, java.sql.Date.valueOf(p.getDataDiNascita()));
                psProfessionista.setString(4, p.getUsername());
                psProfessionista.setString(5, p.getEmail());
                psProfessionista.setString(6, p.getPassword());
                psProfessionista.setString(7, p.getTipo());  // `ruolo` è un campo tipo, che deve essere passato
                //psProfessionista.set(9,null);  // Assumendo che `certificati` contenga anche il `ADMIN_CODICE` come primo valore
                System.out.println(queryProfessionista);
                int rowsAffected = psProfessionista.executeUpdate();

                if (rowsAffected <= 0) {
                    // Se l'inserimento del professionista non va a buon fine, annulla la transazione
                    database.rollback();
                    return false;
                }
            }

            // Se il professionista è stato inserito correttamente, ritorna true
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                // Se c'è un errore, annulla la transazione
                database.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                // Ripristina la modalità auto commit
                database.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean salvaCertificati( ArrayList<String> certificati) {
        String queryID="SELECT CODICE FROM PROFESSIONISTA ORDER BY CODICE DESC LIMIT 1";
        String queryDocumenti = "INSERT INTO DOCUMENTI (PROFESSIONISTA_CODICE, Numero, Percorso_Documenti) " +
                "VALUES (?, ?, ?)";

        try {
            PreparedStatement id= database.prepareStatement(queryID);
            // Esegui la query
            ResultSet rs = id.executeQuery();

            // Memorizza il risultato in una variabile
            if (rs.next()) {
                int ultimoId = rs.getInt("CODICE");
                System.out.println("L'ultimo codice inserito è: " + ultimoId);


                // Inizia una transazione
            database.setAutoCommit(false);

            // Inserire i documenti (certificati) nella tabella DOCUMENTI
            PreparedStatement psDocumenti = database.prepareStatement(queryDocumenti);
                for (int i = 0; i < certificati.size(); i++) {
                    String certificato = certificati.get(i);  // certificato è una stringa che contiene il percorso del file

                    // Inserisci il percorso del certificato nella tabella DOCUMENTI
                    psDocumenti.setInt(1, ultimoId);  // PROFESSIONISTA_CODICE
                    psDocumenti.setInt(2, i + 1);  // Numero del certificato, inizia da 1
                    psDocumenti.setString(3, certificato);  // Percorso del certificato (stringa)
                    System.out.println(queryDocumenti);
                    psDocumenti.addBatch();  // Aggiungi alla batch
                }

                // Esegui la batch per inserire tutti i certificati
                int[] rowsAffected = psDocumenti.executeBatch();

                // Se almeno uno dei certificati non è stato inserito, annulla la transazione
                for (int row : rowsAffected) {
                    if (row <= 0) {
                        database.rollback();
                        return false;
                    }
                }
            }

            // Se tutto è andato a buon fine, commit della transazione
            database.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                // Se c'è un errore, annulla la transazione
                database.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;  // Se c'è un errore, restituisce false
        } finally {
            try {
                // Ripristina la modalità auto commit
                database.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
