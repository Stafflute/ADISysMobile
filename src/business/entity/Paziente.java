package business.entity;

import org.joda.time.LocalDate;
import utility.SerialClone;

import java.util.List;

public class Paziente implements Entity {
    /**
     *
     */
    private static final long serialVersionUID = 5047761740352062543L;

    private String id;

    private String nome;

    private String cognome;

    private LocalDate data;

    private List<String> numeroCellulare;
    private List<Patologia> patologia;

    public java.lang.String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public java.lang.String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public java.lang.String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public org.joda.time.LocalDate getData() {
        return data;
    }

    public void setData(LocalDate date) {
        this.data = date;
    }

    public java.util.List<String> getNumeroCellulare() {
        return SerialClone.clone(numeroCellulare);
    }

    public void setNumeroCellulare(List<String> numeroCellulare) {
        this.numeroCellulare = SerialClone.clone(numeroCellulare);
    }

    public List<Patologia> getPatologia() {
        return SerialClone.clone(patologia);
    }

    public void setPatologia(List<Patologia> patologia) {
        this.patologia = SerialClone.clone(patologia);
    }
}
