package business.applicationservice.factory;

import android.util.Log;
import business.applicationservice.exception.CommonException;
import presentation.controller.ApplicationService;
import util.ErrorPrinter;
import util.Parameter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ADISysApplicationServiceMethod implements ApplicationServiceMethod {

	private final ApplicationService as;
	
	public ADISysApplicationServiceMethod(ApplicationService as) {
		this.as = as;
	}

	public Object invoke(String serviceName, Parameter parameter) throws CommonException {
		Object result = null;
		try {
			
			//TODO codice temporaneo. necessario restyling
			Class<?> asClass = as.getClass();
			String asMethodInString = ApplicationServiceSelector.getServiceMethod(serviceName);
			Method asMethod = asClass.getMethod(asMethodInString, Parameter.class);
			result = asMethod.invoke(as, parameter);
			
		} catch (IllegalAccessException e) {
            ErrorPrinter.print(e);
		} catch (IllegalArgumentException e) {
            ErrorPrinter.print(e);
		} catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            Class<?> causeClass = cause.getClass();
            if (CommonException.class.isAssignableFrom(causeClass)) {
                   CommonException commonCause = (CommonException) cause;
                   throw commonCause;
            } else {
                ErrorPrinter.print(e);
            }
   		} catch (NoSuchMethodException e) {
            ErrorPrinter.print(e);
		} catch (SecurityException e) {
            ErrorPrinter.print(e);
		}
		return result;
	}
}
