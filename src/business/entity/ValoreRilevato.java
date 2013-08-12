package business.entity;

import org.joda.time.LocalTime;
import org.simpleframework.xml.*;

@Root
@Order(elements = {"misura", "tempoOperazione"})
public class ValoreRilevato implements Entity {
	/**
	 * 
	 */
    @Transient
	private static final long serialVersionUID = 2796107324385873233L;

    @Element
	private String misura;
    @Element
	private LocalTime tempoOperazione;
	
	public String getMisura() {
		return misura;
	}
	public void setMisura(String misura) {
		this.misura = misura;
	}
	public LocalTime getTempoOperazione() {
		return tempoOperazione;
	}
	public void setTempoOperazione(LocalTime tempoOperazione) {
		this.tempoOperazione = tempoOperazione;
	}
	
}
