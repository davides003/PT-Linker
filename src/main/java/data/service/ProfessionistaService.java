package data.service;

import data.DAO.AutenticazioneDAO;

public class ProfessionistaService {
    public String getClienti(int id){
        AutenticazioneDAO aDAO = new AutenticazioneDAO();
        return aDAO.getClienti(id);
    }
}
