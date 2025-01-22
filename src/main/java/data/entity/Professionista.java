package data.entity;

import java.util.ArrayList;

public class Professionista extends Utente {
    private ArrayList<String> attestati;
    private String tipo;
    private boolean abilitato;

    // Costruttore
    public Professionista(String nome, String cognome, String username, String email, String password, String dataNascita, int idP, ArrayList<String> attestati, boolean abilitato, String tipo) {
        super(nome, cognome, username, email, password, dataNascita, idP);
        this.attestati = attestati;
        this.abilitato = abilitato;
        this.tipo=tipo;
    }

    public boolean isAbilitato() {
        return abilitato;
    }

    public void setAbilitato(boolean abilitato) {
        this.abilitato = abilitato;
    }

    public ArrayList<String> getAttestati() {
        return attestati;
    }

    public void setAttestati(ArrayList<String> attestati) {
        this.attestati = attestati;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}