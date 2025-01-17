package data.DAO;

import data.entity.Amministratore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Load {

    public Connection con;


    public Load(){
        try {
            con= CollegamentoDB.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Amministratore> getListAdminDB(){
        ArrayList<Amministratore> listAmm = new ArrayList<>();
        try {
            PreparedStatement ps =
                    con.prepareStatement("SELECT * FROM ADMINN");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Amministratore a = new Amministratore(rs.getString("Nome"),rs.getString("Cognome"), "gg@hhh", "bb", 2, "gggg", "chcchhch", rs.getString("Username") , rs.getInt("CODICE") );
            listAmm.add(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listAmm;
    }
}
