package data.DAO;

import data.entity.Cliente;

import java.sql.Connection;
import java.sql.SQLException;

public class AggiornamentoDAO {
    private Connection database;

    public AggiornamentoDAO() {
        try {
            this.database=CollegamentoDB.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean updateCliente(Cliente cliente) {
        return true;
    }

    public boolean updatePT(Cliente cliente) {
        return true;
    }
    public boolean updateNutrizionista(Cliente cliente) {
        return true;
    }
}
