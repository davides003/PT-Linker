package data.service;

import data.DAO.AutenticazioneDAO;
import data.entity.Cliente;
import data.entity.Professionista;

import java.util.ArrayList;

public class RegistrazioneService {

    public boolean verificaDati(String username, String email) {
        AutenticazioneDAO operazioni=new AutenticazioneDAO();
        boolean esito=operazioni.isRegistrato(username,email);
        return esito;
    }

    public boolean registraCliente(Cliente cliente){
        AutenticazioneDAO operazioni=new AutenticazioneDAO();
        boolean esito=operazioni.salvaCliente(cliente);
        return esito;
    }

    public boolean registraProfessionista(Professionista p){
        AutenticazioneDAO operazioni=new AutenticazioneDAO();
        boolean esito=operazioni.salvaProfessionista(p);
        return esito;
    }

    public int getIdProfessionista(){
        AutenticazioneDAO operazioni=new AutenticazioneDAO();
        return operazioni.getIdProfessionista();
    }

    public boolean registraCertificati(ArrayList<String> certificati){
        AutenticazioneDAO operazioni=new AutenticazioneDAO();
        boolean esito=operazioni.salvaCertificati(certificati);
        return esito;
    }

    public ArrayList<Professionista> getProfessionista(){
        AutenticazioneDAO operazioni=new AutenticazioneDAO();
        return operazioni.getProfessionista();
    }

    public void abbinaCliente(String pr){
        AutenticazioneDAO operazioni=new AutenticazioneDAO();
        operazioni.abbinaCliente(pr);
    }
}
