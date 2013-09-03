package business.entity;

import utility.SerialClone;
import java.util.List;

public class Pianificazione implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 919250330191402749L;

	private List<Intervento> intervento;

	public java.util.List<Intervento> getIntervento() {
		return SerialClone.clone(intervento);
	}

	public void setIntervento(List<Intervento> intervento) {
		this.intervento = SerialClone.clone(intervento);
	}
}
