package business.entity;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import util.SerialClone;
import java.util.List;

public class Intervento implements Entity {
	/**
	 * 
	 */

	private static final long serialVersionUID = 3822067935716362954L;

	private String id;

	private Paziente paziente;

	private Infermiere infermiere;

	private String citta;

	private String cap;

	private String indirizzo;

	private LocalDate data;

	private LocalTime ora;

	private List<Operazione> operazione;

	public java.lang.String getCitta() {
		return citta;
	}
	public void setCitta(String citta) {
		this.citta = citta;
	}
	public java.lang.String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public java.lang.String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public org.joda.time.LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}

	public org.joda.time.LocalTime getOra() {
		return ora;
	}
	public void setOra(LocalTime ora) {
		this.ora = ora;
	}

	public java.lang.String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public java.util.List<Operazione> getOperazione() {
		return SerialClone.clone(operazione);
	}
	public void setOperazione(List<Operazione> operazione) {
		this.operazione = SerialClone.clone(operazione);
	}

	public business.entity.Paziente getPaziente() {
		return SerialClone.clone(paziente);
	}
	public void setPaziente(Paziente paziente) {
		this.paziente = SerialClone.clone(paziente);
	}

	public business.entity.Infermiere getInfermiere() {
		return SerialClone.clone(infermiere);
	}
	public void setInfermiere(Infermiere infermiere) {
		this.infermiere = SerialClone.clone(infermiere);
	}

    public String toString() {
        return id + " paziente: " + paziente.getNome() + " " + paziente.getCognome();
    }
}
