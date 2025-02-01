package data.service;


import data.DAO.DietaDAO;

public class DietaService {
    public boolean salvaDieta(String percorsoFile, int professionistaCodice, int clienteCodice){
        DietaDAO dao = new DietaDAO();
        return dao.salvaDietaInDatabase(percorsoFile, professionistaCodice, clienteCodice);
    }

    public String getFileName(int idCliente){
        DietaDAO dao = new DietaDAO();
        return dao.getFileName(idCliente);
    }
}
