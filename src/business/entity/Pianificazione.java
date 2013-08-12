package business.entity;

import util.SerialClone;
import org.simpleframework.xml.*;
import java.util.List;

@Root
public class Pianificazione implements Entity {
	
	/**
	 * 
	 */
    @Transient
	private static final long serialVersionUID = 919250330191402749L;

    @Element
	private List<Intervento> intervento;

	public java.util.List<Intervento> getIntervento() {
		return SerialClone.clone(intervento);
	}

	public void setIntervento(List<Intervento> intervento) {
		this.intervento = SerialClone.clone(intervento);
	}
}
