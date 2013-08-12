package presentation.controller;

import business.applicationservice.factory.ApplicationServiceFactory;
import business.applicationservice.factory.ApplicationServiceMethod;
import business.applicationservice.factory.ApplicationServiceMethodFactory;
import presentation.boundary.Boundary;
import business.applicationservice.ApplicationServiceBoundary;
import util.Parameter;

class ADISysApplicationController implements ApplicationController {
	
	//TODO decidere quali comandi utilizzare
	private static String SHOW_SYNTAX = "Mostra[a-zA-Z]+";

	public Object handleRequest(String serviceName, Parameter parameter) {
		Object result = null;
		
		if (serviceName.matches(SHOW_SYNTAX)) {
            dispatchGUI(serviceName, parameter);
		} else {
			result = execute(serviceName, parameter);
		}
		
		return result;
	}

	private Object execute(String serviceName, Parameter parameter) {
		ApplicationService asClass = ApplicationServiceFactory.buildInstance(serviceName);
		ApplicationServiceMethod asMethod = ApplicationServiceMethodFactory.buildInstance(asClass);
		return asMethod.invoke(serviceName, parameter);
	}

    private static void dispatchGUI(String serviceName, Parameter parameter) {
        ApplicationServiceBoundary.startBoundary(serviceName, parameter);
    }


}
