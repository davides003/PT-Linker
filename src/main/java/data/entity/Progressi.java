package data.entity;
import data.entity.*;

import java.util.ArrayList;


public class Progressi {
    private int idCliente,idProgresso;
    private ArrayList<String> percorsiFoto;
    private String descrizione;
    private float peso;
    private float larghezzaGirovita;
    private float circonferenzaBracciaDx;
    private float circonferenzaBracciaSx;
    private float circonferenzaTorace;
    private float circonferenzaGambaDx;
    private float circonferenzaGambaSx;

    // Costruttore
    public Progressi(int idCliente, ArrayList<String> percorsiFoto, String descrizione, int idProgresso, float peso, float larghezzaGirovita, float circonferenzaBracciaDx, float circonferenzaBracciaSx, float circonferenzaTorace, float circonferenzaGambaDx, float circonferenzaGambaSx) {
        this.idProgresso = idProgresso;
        this.percorsiFoto = percorsiFoto;
        setIdCliente(idCliente);
        setDescrizione(descrizione);
        setPeso(peso);
        setLarghezzaGirovita(larghezzaGirovita);
        setCirconferenzaBracciaDx(circonferenzaBracciaDx);
        setCirconferenzaBracciaSx(circonferenzaBracciaSx);
        setCirconferenzaTorace(circonferenzaTorace);
        setCirconferenzaGambaDx(circonferenzaGambaDx);
        setCirconferenzaGambaSx(circonferenzaGambaSx);
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

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        if (descrizione == null || descrizione.isEmpty()) {
            throw new IllegalArgumentException("La descrizione non può essere vuota.");
        }
        this.descrizione = descrizione;
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

    public int getIdProgresso() {
        return idProgresso;
    }

    public void setIdProgresso(int idProgresso) {
        this.idProgresso = idProgresso;
    }

    public ArrayList<String> getPercorsiFoto() {
        return percorsiFoto;
    }

    public void setPercorsiFoto(ArrayList<String> percorsiFoto) {
        this.percorsiFoto = percorsiFoto;
    }
}
