package data.entity;

public class Scheda {
    private String descrizione;
    private String note;
    private int idScheda;

    // Costruttore
    public Scheda(String descrizione, String note, int idScheda) {
        this.idScheda = idScheda;
        setDescrizione(descrizione);
        setNote(note);
    }

    // Getters e Setters
    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        if (descrizione == null || descrizione.isEmpty()) {
            throw new IllegalArgumentException("La descrizione non può essere vuota.");
        }
        this.descrizione = descrizione;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        if (note == null || note.isEmpty()) {
            throw new IllegalArgumentException("Le note non possono essere vuote.");
        }
        this.note = note;
    }

    public int getIdScheda() {
        return idScheda;
    }

    public void setIdScheda(int idScheda) {
        this.idScheda = idScheda;
    }
}
