package business;

public class Dieta {
    private String descrizione;
    private String note;

    // Costruttore
    public Dieta(String descrizione, String note) {
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
}
