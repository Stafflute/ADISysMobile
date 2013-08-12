package business.applicationservice.factory;

import android.util.Log;
import presentation.controller.ApplicationService;
import util.Parameter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ADISysApplicationServiceMethod implements ApplicationServiceMethod {

	private final ApplicationService as;
	
	public ADISysApplicationServiceMethod(ApplicationService as) {
		this.as = as;
	}

	public Object invoke(String serviceName, Parameter parameter) {
		Object result = null;
		try {
			
			//TODO codice temporaneo. necessario restyling
			Class<?> asClass = as.getClass();
			String asMethodInString = ApplicationServiceSelector.getServiceMethod(serviceName);
			Method asMethod = asClass.getMethod(asMethodInString, Parameter.class);
			result = asMethod.invoke(as, parameter);
			
		} catch (IllegalAccessException e) {
            Log.e("AndroidRuntime", e.toString() + ": " + e.getLocalizedMessage(), e);
		} catch (IllegalArgumentException e) {
            Log.e("AndroidRuntime", e.toString() + ": " + e.getLocalizedMessage(), e);
		} catch (InvocationTargetException e) {
            Log.e("AndroidRuntime", e.toString() + ": " + e.getLocalizedMessage(), e);
		} catch (NoSuchMethodException e) {
            Log.e("AndroidRuntime", e.toString() + ": " + e.getLocalizedMessage(), e);
		} catch (SecurityException e) {
            Log.e("AndroidRuntime", e.toString() + ": " + e.getLocalizedMessage(), e);
		}
		return result;
	}
}
