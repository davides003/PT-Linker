package data;

public class Cliente extends Utente {
    private int idC;
    private float altezza;
    private float peso;
    private float larghezzaGirovita;
    private float circonferenzaBracciaDx;
    private float circonferenzaBracciaSx;
    private float circonferenzaTorace;
    private float circonferenzaGambaDx;
    private float circonferenzaGambaSx;

    // Costruttore
    public Cliente(String nome, String cognome, String email, String indirizzo, int eta, String recapitoTelefonico, String citta, int idC, float altezza, float peso, float larghezzaGirovita, float circonferenzaBracciaDx, float circonferenzaBracciaSx, float circonferenzaTorace, float circonferenzaGambaDx, float circonferenzaGambaSx) {
        super(nome, cognome, email, indirizzo, eta, recapitoTelefonico, citta);
        setIdC(idC);
        setAltezza(altezza);
        setPeso(peso);
        setLarghezzaGirovita(larghezzaGirovita);
        setCirconferenzaBracciaDx(circonferenzaBracciaDx);
        setCirconferenzaBracciaSx(circonferenzaBracciaSx);
        setCirconferenzaTorace(circonferenzaTorace);
        setCirconferenzaGambaDx(circonferenzaGambaDx);
        setCirconferenzaGambaSx(circonferenzaGambaSx);
    }

    // Getters e Setters
    public int getIdC() {
        return idC;
    }

    public void setIdC(int idC) {
        if (idC < 0) {
            throw new IllegalArgumentException("ID Cliente non può essere negativo.");
        }
        this.idC = idC;
    }

    public float getAltezza() {
        return altezza;
    }

    public void setAltezza(float altezza) {
        if (altezza <= 0) {
            throw new IllegalArgumentException("L'altezza deve essere maggiore di 0.");
        }
        this.altezza = altezza;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        if (peso <= 0) {
            throw new IllegalArgumentException("Il peso deve essere maggiore di 0.");
        }
        this.peso = peso;
    }

    public float getLarghezzaGirovita() {
        return larghezzaGirovita;
    }

    public void setLarghezzaGirovita(float larghezzaGirovita) {
        if (larghezzaGirovita < 0) {
            throw new IllegalArgumentException("La larghezza girovita non può essere negativa.");
        }
        this.larghezzaGirovita = larghezzaGirovita;
    }

    public float getCirconferenzaBracciaDx() {
        return circonferenzaBracciaDx;
    }

    public void setCirconferenzaBracciaDx(float circonferenzaBracciaDx) {
        if (circonferenzaBracciaDx < 0) {
            throw new IllegalArgumentException("La circonferenza del braccio destro non può essere negativa.");
        }
        this.circonferenzaBracciaDx = circonferenzaBracciaDx;
    }

    public float getCirconferenzaBracciaSx() {
        return circonferenzaBracciaSx;
    }

    public void setCirconferenzaBracciaSx(float circonferenzaBracciaSx) {
        if (circonferenzaBracciaSx < 0) {
            throw new IllegalArgumentException("La circonferenza del braccio sinistro non può essere negativa.");
        }
        this.circonferenzaBracciaSx = circonferenzaBracciaSx;
    }

    public float getCirconferenzaTorace() {
        return circonferenzaTorace;
    }

    public void setCirconferenzaTorace(float circonferenzaTorace) {
        if (circonferenzaTorace < 0) {
            throw new IllegalArgumentException("La circonferenza del torace non può essere negativa.");
        }
        this.circonferenzaTorace = circonferenzaTorace;
    }

    public float getCirconferenzaGambaDx() {
        return circonferenzaGambaDx;
    }

    public void setCirconferenzaGambaDx(float circonferenzaGambaDx) {
        if (circonferenzaGambaDx < 0) {
            throw new IllegalArgumentException("La circonferenza della gamba destra non può essere negativa.");
        }
        this.circonferenzaGambaDx = circonferenzaGambaDx;
    }

    public float getCirconferenzaGambaSx() {
        return circonferenzaGambaSx;
    }

    public void setCirconferenzaGambaSx(float circonferenzaGambaSx) {
        if (circonferenzaGambaSx < 0) {
            throw new IllegalArgumentException("La circonferenza della gamba sinistra non può essere negativa.");
        }
        this.circonferenzaGambaSx = circonferenzaGambaSx;
    }
}
