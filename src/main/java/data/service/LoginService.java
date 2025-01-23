package data.service;
import data.DAO.AutenticazioneDAO;
import data.entity.Utente;

import java.util.HashMap;
import java.util.Map;

public class LoginService {

       public Utente verificaUtente(String email,String password){
                AutenticazioneDAO log=new AutenticazioneDAO();
                Utente risultato= log.verify(email,password);

           return risultato;
       }

    }



