package business.entity;

public class Infermiere implements Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8182360441844160117L;

	private String id;

	private String nome;

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
