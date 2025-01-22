package data.service;
import data.DAO.LoginDAO;
import data.entity.Utente;

import java.util.HashMap;
import java.util.Map;

public class LoginService {

       public String verifyCredentials(String email,String password){
           LoginDAO log =new LoginDAO();
             String risultato=  log.verificaTipoUtente(email,password);
                 return risultato;
       }

       public Utente verificaUtente(String email,String password){
                LoginDAO log=new LoginDAO();
                Utente risultato= log.verify(email,password);

           return risultato;
       }

    }



