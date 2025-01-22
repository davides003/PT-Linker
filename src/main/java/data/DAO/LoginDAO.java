package data.DAO;
import data.entity.*;
import java.sql.*;


    public class LoginDAO {

        private Connection database;

        public LoginDAO(){
            try {
                database=CollegamentoDB.getConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        // Metodo per convalidare le credenziali dell'utente
        public String verificaTipoUtente(String email, String password) {
            // Variabile per memorizzare il tipo di utente
            String tipoUtente = "Non registrato";  // Default: "Non registrato"

            String query = "SELECT 'cliente' AS utente FROM CLIENTE WHERE E_Mail = ? AND Password = ? " +
                    "UNION " +
                    "SELECT 'professionista' AS utente FROM PROFESSIONISTA WHERE E_Mail = ? AND Password = ? ";

            try (PreparedStatement ps = database.prepareStatement(query)) {
                ps.setString(1, email);  // Imposta email e password per la tabella CLIENTE
                ps.setString(2, password);
                ps.setString(3, email);  // Imposta email e password per la tabella PROFESSIONISTA
                ps.setString(4, password);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // Se trovi un risultato, restituisci il tipo di utente (Cliente o Professionista)
                        tipoUtente = rs.getString("utente");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();  // Log dell'errore
            }

            // Restituisce il tipo di utente o "Non registrato" se l'utente non è trovato
            return tipoUtente;
        }

        public Utente verify(String email,String password){
            String tipoUtente=verificaTipoUtente(email,password);

            if(!tipoUtente.equals("")){
               String query1 ="SELECT * FROM " + tipoUtente + " WHERE E_mail='"+email+"' AND Password='"+password+"' ";
               System.out.println(query1);
                try (PreparedStatement ps = database.prepareStatement(query1)) {

                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            // Se trovi un risultato, restituisci il tipo di utente (Cliente o Professionista)
                            if(tipoUtente.equals("cliente")){

                         return new Cliente( rs.getString("Nome"), rs.getString("Cognome"),
                                rs.getString("Username"), rs.getString("E_Mail"),
                                 rs.getString("Password"),rs.getString("Data_di_Nascita"),rs.getInt("CODICE"),
                                 rs.getFloat("Altezza"),rs.getFloat("Peso"),rs.getFloat("Circonferenza_Giro_Vita"),
                                 rs.getFloat("Circonferenza_Braccio_Dx"),rs.getFloat("Circonferenza_Braccio_Sx"),rs.getFloat("Circonferenza_Torace"),
                                 rs.getFloat("Circonferenza_Gamba_Dx"),rs.getFloat("Circonferenza_Gamba_Sx")
                                    );
                            }else{
                                return new Professionista(rs.getString("Nome"), rs.getString("Cognome"),
                                        rs.getString("Username"), rs.getString("E_Mail"),
                                        rs.getString("Password"),rs.getString("Data_di_Nascita"),rs.getInt("CODICE"),null,
                                        false,rs.getString("Tipo")  );
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();  // Log dell'errore
                }

            }else{
                System.out.println("vuoto");
            }

            return null;
        }

    }




