package data;

public class Amministratore extends Utente {
    private String username;

    // Costruttore
    public Amministratore(String nome, String cognome, String email, String indirizzo, int eta, String recapitoTelefonico, String citta, String username) {
        super(nome, cognome, email, indirizzo, eta, recapitoTelefonico, citta);
        setUsername(username);
    }

    // Getters e Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Il nome utente non può essere vuoto.");
        }
        this.username = username;
    }
}
