package data.service;
import data.DAO.AutenticazioneDAO;
import data.DAO.ProgressiDAO;
import data.entity.Cliente;
import data.entity.Progressi;

public class ProgressiService {

    public boolean registraProgressi(Progressi progressi){
        ProgressiDAO operazioni=new ProgressiDAO();
        boolean esito=operazioni.salvaProgressi(progressi);
        return esito;
    }
}
