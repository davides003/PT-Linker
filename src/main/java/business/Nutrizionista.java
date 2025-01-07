package business;

public class Nutrizionista extends Utente {
    private int idN;
    private String certificati;

    // Costruttore
    public Nutrizionista(String nome, String cognome, String email, String indirizzo, int eta, String recapitoTelefonico, String citta, int idN, String certificati) {
        super(nome, cognome, email, indirizzo, eta, recapitoTelefonico, citta);
        setIdN(idN);
        setCertificati(certificati);
    }

    // Getters e Setters
    public int getIdN() {
        return idN;
    }

    public void setIdN(int idN) {
        if (idN < 0) {
            throw new IllegalArgumentException("ID Nutrizionista non può essere negativo.");
        }
        this.idN = idN;
    }

    public String getCertificati() {
        return certificati;
    }

    public void setCertificati(String certificati) {
        if (certificati == null || certificati.isEmpty()) {
            throw new IllegalArgumentException("I certificati non possono essere vuoti.");
        }
        this.certificati = certificati;
    }
}