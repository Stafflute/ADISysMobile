package business.entity;

import util.SerialClone;

public class Operazione implements Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3829368754663286748L;

    private String id;

    private String nome;

    private String nota;

    private ValoreRilevato valoreRilevato;

	public java.lang.String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public java.lang.String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		if(nota != null) {
			this.nota = nota;
		} else {
			this.nota = "";
		}
		
	}
	public ValoreRilevato getValoreRilevato() {
		return SerialClone.clone(valoreRilevato);
	}
	public void setValoreRilevato(ValoreRilevato valoreRilevato) {
		this.valoreRilevato = SerialClone.clone(valoreRilevato);
	}
	public java.lang.String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
