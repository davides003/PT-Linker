package data;

public class Utente {
    private String nome;
    private String cognome;
    private String email;
    private String indirizzo;
    private int eta,id;
    private String recapitoTelefonico;
    private String citta;

    // Costruttore
    public Utente(String nome, String cognome, String email, String indirizzo, int eta, String recapitoTelefonico, String citta, int id) {
        this.id = id;
        setNome(nome);
        setCognome(cognome);
        setEmail(email);
        setIndirizzo(indirizzo);
        setEta(eta);
        setRecapitoTelefonico(recapitoTelefonico);
        setCitta(citta);
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Il nome non può essere vuoto.");
        }
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        if (cognome == null || cognome.isEmpty()) {
            throw new IllegalArgumentException("Il cognome non può essere vuoto.");
        }
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email non valida.");
        }
        this.email = email;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        if (indirizzo == null || indirizzo.isEmpty()) {
            throw new IllegalArgumentException("Indirizzo non valido.");
        }
        this.indirizzo = indirizzo;
    }

    public int getEta() {
        return eta;
    }

    public void setEta(int eta) {
        if (eta < 0) {
            throw new IllegalArgumentException("L'età non può essere negativa.");
        }
        this.eta = eta;
    }

    public String getRecapitoTelefonico() {
        return recapitoTelefonico;
    }

    public void setRecapitoTelefonico(String recapitoTelefonico) {
        if (recapitoTelefonico == null || recapitoTelefonico.isEmpty()) {
            throw new IllegalArgumentException("Il recapito telefonico non può essere vuoto.");
        }
        this.recapitoTelefonico = recapitoTelefonico;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        if (citta == null || citta.isEmpty()) {
            throw new IllegalArgumentException("La città non può essere vuota.");
        }
        this.citta = citta;
    }

    public int getId() {
        return id;
    }

    public void setIdU(int id) {
        this.id = id;
    }
}