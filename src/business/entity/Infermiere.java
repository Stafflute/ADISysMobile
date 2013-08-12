package business.entity;

import org.simpleframework.xml.*;

@Root
@Order(elements = {"id", "nome", "cognome"})
public class Infermiere implements Entity {
	/**
	 * 
	 */
    @Transient
	private static final long serialVersionUID = 8182360441844160117L;

    @Element
	private String id;

    @Element
	private String nome;

    @Element
	private String cognome;

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
}
