package business.applicationservice.factory;

import android.util.Log;
import presentation.controller.ApplicationService;

public class ApplicationServiceFactory {

    private ApplicationServiceFactory() {

    }
    
    public static ApplicationService buildInstance(String serviceName) {
	ApplicationService as = null;
	
	try {
	    String canonicalClassName = ApplicationServiceSelector.getApplicationService(serviceName);
	    Class<?> asClass = Class.forName(canonicalClassName);
	    as = (ApplicationService) asClass.newInstance();
	} catch (ClassNotFoundException e) {
	    Log.e("AndroidRuntime", e.toString() + ": " + e.getLocalizedMessage(), e);
	} catch (InstantiationException e) {
        Log.e("AndroidRuntime", e.toString() + ": " + e.getLocalizedMessage(), e);
	} catch (IllegalAccessException e) {
        Log.e("AndroidRuntime", e.toString() + ": " + e.getLocalizedMessage(), e);
	}
	
	return as;
    }
    
}
