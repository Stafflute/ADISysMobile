package business.entity;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.simpleframework.xml.convert.Convert;
import util.SerialClone;
import java.util.List;
import org.simpleframework.xml.*;
import util.xml.adapter.XMLDateAdapter;
import util.xml.adapter.XMLTimeAdapter;

@Root
@Order(elements = {"id", "citta", "cap", "indirizzo", "data", "ora", "listaOperazioni", "paziente", "infermiere"})
public class Intervento implements Entity {
	/**
	 * 
	 */
    @Transient
	private static final long serialVersionUID = 3822067935716362954L;
    @Element
	private String id;
    @Element
	private Paziente paziente;
    @Element
	private Infermiere infermiere;
    @Element
	private String citta;
    @Element
	private String cap;
    @Element
	private String indirizzo;
    @Element
    @Convert(XMLDateAdapter.class)
	private LocalDate data;
    @Element
    @Convert(XMLTimeAdapter.class)
	private LocalTime ora;

    @ElementList(name = "listaOperazioni")
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
}
