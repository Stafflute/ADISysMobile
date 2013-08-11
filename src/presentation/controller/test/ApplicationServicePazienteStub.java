package presentation.controller.test;

import business.applicationservice.CRUG;
import business.entity.Paziente;
import mockit.Mock;
import presentation.controller.ApplicationService;
import util.Parameter;

public class ApplicationServicePazienteStub implements CRUG<Paziente>, ApplicationService {

	@Mock
	public void create(Parameter parameter) {
		System.out.println("OK Inserisci paziente");

	}

	@Mock
	public void update(Parameter parameter) {
		System.out.println("OK Modifica paziente");

	}

	@Mock
	public Paziente read(Parameter parameter) {
		System.out.println("OK");
		return null;
	}

	@Mock
	public java.util.List<Paziente> getAll(Parameter parameter) {
		System.out.println("OK");
		return null;
	}

}
