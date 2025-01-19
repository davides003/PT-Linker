package data.entity;

import java.util.ArrayList;

public class Nutrizionista extends Utente {
    private ArrayList<String> certificati;
    private boolean abilitato;

    // Costruttore
    public Nutrizionista(String nome, String cognome, String username, String email, String password, String dataNascita, int idN, ArrayList<String> certificati, boolean abilitato) {
        super(nome, cognome, username, email, password, dataNascita, idN);
        this.certificati = certificati;
        this.abilitato = abilitato;
    }

    public boolean isAbilitato() {
        return abilitato;
    }

    public void setAbilitato(boolean abilitato) {
        this.abilitato = abilitato;
    }

    public ArrayList<String> getCertificati() {
        return certificati;
    }

    public void setCertificati(ArrayList<String> certificati) {
        this.certificati = certificati;
    }
}