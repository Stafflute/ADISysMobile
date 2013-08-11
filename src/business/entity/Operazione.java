package business.entity;

import util.SerialClone;
import org.simpleframework.xml.*;

@Root
@Order(elements = {"id", "nome", "nota", "valoreRilevato"})
public class Operazione implements Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3829368754663286748L;
    @Element
	private String id;
    @Element
	private String nome;
    @Element
	private String nota;
    @Element
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
