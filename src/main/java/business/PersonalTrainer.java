package business;

public class PersonalTrainer extends Utente {
    private int idP;
    private String attestati;

    // Costruttore
    public PersonalTrainer(String nome, String cognome, String email, String indirizzo, int eta, String recapitoTelefonico, String citta, int idP, String attestati) {
        super(nome, cognome, email, indirizzo, eta, recapitoTelefonico, citta);
        setIdP(idP);
        setAttestati(attestati);
    }

    // Getters e Setters
    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        if (idP < 0) {
            throw new IllegalArgumentException("ID Personal Trainer non può essere negativo.");
        }
        this.idP = idP;
    }

    public String getAttestati() {
        return attestati;
    }

    public void setAttestati(String attestati) {
        if (attestati == null || attestati.isEmpty()) {
            throw new IllegalArgumentException("Gli attestati non possono essere vuoti.");
        }
        this.attestati = attestati;
    }
}