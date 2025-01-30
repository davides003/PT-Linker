package data.DAO;

import data.DAO.CollegamentoDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import data.entity.*;

public class ProgressiDAO {
    private Connection conn;
    public ProgressiDAO(){
        try {
            conn= CollegamentoDB.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean salvaProgressi(Progressi progressi) {

        String sql = "INSERT INTO progressi (idCliente, foto, descrizione, idProgresso, peso, larghezzaGirovita, circonferenzaBracciaDx, circonferenzaBracciaSx, circonferenzaTorace, circonferenzaGambaDx, circonferenzaGambaSx) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, progressi.getIdCliente());
            stmt.setString(2, progressi.getFoto());
            stmt.setString(3, progressi.getDescrizione());
            stmt.setInt(4, progressi.getIdProgresso());
            stmt.setFloat(5, progressi.getPeso());
            stmt.setFloat(6, progressi.getLarghezzaGirovita());
            stmt.setFloat(7, progressi.getCirconferenzaBracciaDx());
            stmt.setFloat(8, progressi.getCirconferenzaBracciaSx());
            stmt.setFloat(9, progressi.getCirconferenzaTorace());
            stmt.setFloat(10, progressi.getCirconferenzaGambaDx());
            stmt.setFloat(11, progressi.getCirconferenzaGambaSx());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
