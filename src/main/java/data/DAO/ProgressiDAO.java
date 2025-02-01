package data.DAO;

import data.DAO.CollegamentoDB;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

import data.entity.*;
import data.service.DropboxService;

public class ProgressiDAO {
    private Connection database;
    public ProgressiDAO(){
        try {
            database= CollegamentoDB.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean salvaProgressi(Progressi progressi) {

        String sql = "INSERT INTO PROGRESSO (CLIENTE_CODICE, Commento) VALUES (?, ?)";

        try (PreparedStatement stmt = database.prepareStatement(sql)) {
            String reportProgresso=progressi.getIdCliente()+ File.separator;
            reportProgresso += progressi.getDescrizione() + File.separator;
            reportProgresso += progressi.getPeso() + File.separator;
            reportProgresso += progressi.getLarghezzaGirovita() + File.separator;
            reportProgresso += progressi.getCirconferenzaBracciaDx() + File.separator;
            reportProgresso += progressi.getCirconferenzaBracciaSx() + File.separator;
            reportProgresso += progressi.getCirconferenzaTorace() + File.separator;
            reportProgresso += progressi.getCirconferenzaGambaDx() + File.separator;
            reportProgresso += progressi.getCirconferenzaGambaSx();
            System.out.println("report"+reportProgresso);
            stmt.setInt(1, progressi.getIdCliente());
            stmt.setString(2, reportProgresso);


            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean salvaFotoProgressi(ArrayList<String> percorsiFoto) {
        DropboxService dbS= new DropboxService();

        String queryID="SELECT CODICE FROM PROGRESSO ORDER BY CODICE DESC LIMIT 1";
        String queryDocumenti = "INSERT INTO IMMAGINI (PROGRESSO_CODICE, Numero, Percorso_File)  " +
                "VALUES (?, ?, ?)";

        try {
            PreparedStatement codice= database.prepareStatement(queryID);
            // Esegui la query
            ResultSet rs = codice.executeQuery();

            // Memorizza il risultato in una variabile
            if (rs.next()) {
                int ultimoProgresso = rs.getInt("CODICE");
                System.out.println("L'ultimo codice inserito è: " + ultimoProgresso);


                // Inizia una transazione
                database.setAutoCommit(false);

                // Inserire i documenti (certificati) nella tabella DOCUMENTI
                PreparedStatement psDocumenti = database.prepareStatement(queryDocumenti);
                for (int i = 0; i < percorsiFoto.size(); i++) {
                    String percorsoFoto = percorsiFoto.get(i);  // certificato è una stringa che contiene il percorso del file

                    dbS.uploadFile(percorsoFoto,"/PT_LINKER/Progressi_Foto");

                    // Inserisci il percorso del certificato nella tabella DOCUMENTI
                    psDocumenti.setInt(1, ultimoProgresso);  // PROFESSIONISTA_CODICE
                    psDocumenti.setInt(2, i + 1);  // Numero del certificato, inizia da 1
                    psDocumenti.setString(3, percorsoFoto);  // Percorso del certificato (stringa)
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

    public Progressi getProgressi(int idCliente) {
        String sql = "SELECT * FROM PROGRESSO WHERE CLIENTE_CODICE = ? ORDER BY CODICE DESC LIMIT 1";
        try(PreparedStatement stmt = database.prepareStatement(sql)) {
            stmt.setInt(1, idCliente); // Sostituisci idCliente con il valore effettivo
            ResultSet rs = stmt.executeQuery();
            String commento="";
            int codice=0;
            if (rs.next()) {
                 codice = rs.getInt("CODICE");
                 commento = rs.getString("Commento");

                System.out.println("Ultimo progresso - ID: " + codice + ", Commento: " + commento);
            }
            System.out.println("separatore"+File.separator);
            String[] campi = commento.split(File.separator); // Suddivide il commento usando "/" come separatore


            ArrayList<String> percorsiFoto = getPercorsiFoto(codice);
            DropboxService dbs= new DropboxService();
            for (String percorso: percorsiFoto) {
                System.out.println("RITORNO DROPBOXSERVICE "+dbs.download("/PT_LINKER/Progressi_Foto", percorso));
            }
            String descrizione = campi[1];
            int idProgresso = codice;
                    //Integer.parseInt(campi[2]);
            float peso = Float.parseFloat(campi[2]);
            float larghezzaGirovita = Float.parseFloat(campi[3]);
            float circonferenzaBracciaDx = Float.parseFloat(campi[4]);
            float circonferenzaBracciaSx = Float.parseFloat(campi[5]);
            float circonferenzaTorace = Float.parseFloat(campi[6]);
            float circonferenzaGambaDx = Float.parseFloat(campi[7]);
            float circonferenzaGambaSx = Float.parseFloat(campi[8]);

            for(int i=0;i<percorsiFoto.size();i++){
                String percorso = percorsiFoto.get(i);
                percorso = percorso.substring(percorso.indexOf("image"));
                System.out.println(percorso);
                percorsiFoto.set(i,percorso);
            }

            Progressi pr= new Progressi(idCliente, percorsiFoto, descrizione, idProgresso, peso, larghezzaGirovita,
                    circonferenzaBracciaDx, circonferenzaBracciaSx, circonferenzaTorace, circonferenzaGambaDx, circonferenzaGambaSx);

            return pr;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getPercorsiFoto(int codice) throws SQLException {
       ArrayList<String> percorsiFoto = new ArrayList<>();

        // La query SQL per ottenere i percorsi delle foto per un determinato progresso
        String query = "SELECT Percorso_File FROM IMMAGINI WHERE PROGRESSO_CODICE = ?";

        // Prepara la dichiarazione
        try (PreparedStatement stmt = database.prepareStatement(query)) {
            // Imposta il parametro codice
            stmt.setInt(1, codice);

            // Esegui la query
            try (ResultSet rs = stmt.executeQuery()) {
                // Aggiungi ogni percorso nella lista
                while (rs.next()) {
                    percorsiFoto.add(rs.getString("Percorso_File"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Rilancia l'eccezione se necessario
        } finally {
            database.close();
        }

        // Restituisci l'ArrayList con i percorsi delle foto
        return percorsiFoto;
    }
}
