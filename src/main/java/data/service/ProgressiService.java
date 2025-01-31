package data.service;
import data.DAO.AutenticazioneDAO;
import data.DAO.ProgressiDAO;
import data.entity.Cliente;
import data.entity.Progressi;

import java.util.ArrayList;

public class ProgressiService {

    public boolean registraProgressi(Progressi progressi){
        ProgressiDAO operazioni=new ProgressiDAO();
        boolean esito=operazioni.salvaProgressi(progressi);
        return esito;
    }

    public boolean registraFotoProgressi(ArrayList<String> fotoPaths){
        ProgressiDAO operazioni=new ProgressiDAO();
        return operazioni.salvaFotoProgressi(fotoPaths);
    }

    public Progressi getProgressi(int idCliente){
        ProgressiDAO operazioni=new ProgressiDAO();
        return operazioni.getProgressi(idCliente);
    }
}
