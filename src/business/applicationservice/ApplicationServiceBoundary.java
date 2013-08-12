package business.applicationservice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import presentation.boundary.Boundary;
import util.Parameter;

public class ApplicationServiceBoundary {
	private static final String BOUNDARY_PACKAGE_PATH = "presentation.boundary.";
	private static final String SERVICE_NAME_HEAD = "Mostra";
	private static final int BOUNDARY_NAME_START_POSITION = SERVICE_NAME_HEAD.length();

	private ApplicationServiceBoundary() {
		
	}
	
	public static void startBoundary(String serviceName, Parameter parameter) {
        Activity activity = (Activity) parameter.getValue("activity");
        String boundaryClassString = serviceName.substring(BOUNDARY_NAME_START_POSITION);
        Class<?> boundaryClass = null;
        try {
            boundaryClass = Class.forName(BOUNDARY_PACKAGE_PATH + boundaryClassString);
            Intent intent = new Intent(activity.getApplicationContext(), boundaryClass);
            parameter.removeNotSerializable();
            intent.putExtra(Parameter.PARAMETER, parameter);
            activity.startActivity(intent);
        } catch (ClassNotFoundException e) {
            Log.e("AndroidRuntime", e.toString() + ": " + e.getLocalizedMessage(), e);
        }
	}
}
