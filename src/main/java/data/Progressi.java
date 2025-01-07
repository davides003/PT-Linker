package data;

public class Progressi {
    private int idCliente;
    private String foto;
    private String descrizione;
    private String misureCliente;

    // Costruttore
    public Progressi(int idCliente, String foto, String descrizione, String misureCliente) {
        setIdCliente(idCliente);
        setFoto(foto);
        setDescrizione(descrizione);
        setMisureCliente(misureCliente);
    }

    // Getters e Setters
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        if (idCliente < 0) {
            throw new IllegalArgumentException("L'ID Cliente non può essere negativo.");
        }
        this.idCliente = idCliente;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        if (foto == null || foto.isEmpty()) {
            throw new IllegalArgumentException("La foto non può essere vuota.");
        }
        this.foto = foto;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        if (descrizione == null || descrizione.isEmpty()) {
            throw new IllegalArgumentException("La descrizione non può essere vuota.");
        }
        this.descrizione = descrizione;
    }

    public String getMisureCliente() {
        return misureCliente;
    }

    public void setMisureCliente(String misureCliente) {
        if (misureCliente == null || misureCliente.isEmpty()) {
            throw new IllegalArgumentException("Le misure del cliente non possono essere vuote.");
        }
        this.misureCliente = misureCliente;
    }
}
