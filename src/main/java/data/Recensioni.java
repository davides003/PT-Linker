package data;

public class Recensioni {
    private int idCliente;
    private String descrizione;
    private int idRecensito,idRecensione;

    // Costruttore con parametri
    public Recensioni(int idCliente, String descrizione, int idRecensito, int idRecensione) {
        this.idRecensione = idRecensione;
        setIdCliente(idCliente);
        setDescrizione(descrizione);
        setIdRecensito(idRecensito);
    }

    // Getters e Setters con controlli
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        if (idCliente <= 0) {
            throw new IllegalArgumentException("L'ID cliente deve essere un numero positivo.");
        }
        this.idCliente = idCliente;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        if (descrizione == null || descrizione.trim().isEmpty()) {
            throw new IllegalArgumentException("La descrizione non può essere vuota.");
        }
        this.descrizione = descrizione;
    }

    public int getIdRecensito() {
        return idRecensito;
    }

    public void setIdRecensito(int idRecensito) {
        if (idRecensito <= 0) {
            throw new IllegalArgumentException("L'ID recensito deve essere un numero positivo.");
        }
        this.idRecensito = idRecensito;
    }

    public int getIdRecensione() {
        return idRecensione;
    }

    public void setIdRecensione(int idRecensione) {
        this.idRecensione = idRecensione;
    }
}
