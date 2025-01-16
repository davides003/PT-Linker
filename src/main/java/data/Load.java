package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Load {

    public Connection con;
    private ArrayList<Amministratore> listAmm = null;


    public Load(){
        try {
            con= CollegamentoDB.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Amministratore> getListAmm() {
        return listAmm;
    }

    public void setListAdminDB(){
        try {
            PreparedStatement ps =
                    con.prepareStatement("SELECT * FROM ADMINN");
            ResultSet rs = ps.executeQuery();
            this.listAmm = new ArrayList<Amministratore>();
            while (rs.next()){
                Amministratore a = new Amministratore(rs.getString("Nome"),rs.getString("Cognome"), "gg@hhh", "bb", 2, "gggg", "chcchhch", rs.getString("Username") , rs.getInt("CODICE") );
            this.listAmm.add(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        Load l = new Load();
        l.setListAdminDB();
        ArrayList<Amministratore> listAdmin = l.getListAmm();
        for(Amministratore amministratore:listAdmin){
            System.out.println(amministratore.getUsername() + ' ' + amministratore.getNome() + ' ' + amministratore.getCognome() +'\n');
        }
    }

}
