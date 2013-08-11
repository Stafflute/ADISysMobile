package business.entity;

import org.joda.time.LocalDate;
import util.SerialClone;
import java.util.List;
import org.simpleframework.xml.*;

@Root
@Order(elements = {"id", "nome", "cognome", "data", "numeroCellulare"})
public class Paziente implements Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5047761740352062543L;
    @Element
	private String id;
    @Element
	private String nome;
    @Element
	private String cognome;
    @Element
	private LocalDate data;
    @Path("rubrica")
    @Element(name = "numero")
	private List<String> numeroCellulare;

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
}