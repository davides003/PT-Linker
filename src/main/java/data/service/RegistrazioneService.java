package data.service;

import data.DAO.AutenticazioneDAO;
import data.entity.Cliente;

import java.io.File;
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

    public boolean registraProfessionista(String nome, String cognome, String username, String email, String password, String dataNascita, int id, String ruolo){
        AutenticazioneDAO operazioni=new AutenticazioneDAO();
        boolean esito=operazioni.salvaProfessionista(nome,cognome,username,email,password,dataNascita,id,ruolo);
        return esito;
    }

    public boolean registraCertificati(int id,ArrayList<String> certificati){
        AutenticazioneDAO operazioni=new AutenticazioneDAO();
        boolean esito=operazioni.salvaCertificati(id,certificati);
        return esito;
    }
}
