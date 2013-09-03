package business.applicationservice;

import android.app.Activity;
import android.content.Intent;
import presentation.boundary.Boundary;
import presentation.controller.ApplicationService;
import utility.ErrorPrinter;
import utility.Parameter;

public class ApplicationServiceBoundary implements ApplicationService {
    private static final String BOUNDARY_PACKAGE_PATH = "presentation.boundary.";
    private static final String SERVICE_NAME_HEAD = "Mostra";
    private static final int BOUNDARY_NAME_START_POSITION = SERVICE_NAME_HEAD.length();

    private ApplicationServiceBoundary() {

    }

    public static void startBoundary(String serviceName, Parameter parameter) {
        Activity activity = (Activity) parameter.getValue(Boundary.ACTIVITY);
        String boundaryClassString = serviceName.substring(BOUNDARY_NAME_START_POSITION);
        Class<?> boundaryClass = null;
        try {
            boundaryClass = Class.forName(BOUNDARY_PACKAGE_PATH + boundaryClassString);
            Intent intent = new Intent(activity.getApplicationContext(), boundaryClass);
            if (parameter != null) {
                parameter.removeNotSerializable();
            }
            intent.putExtra(Parameter.PARAMETER, parameter);
            activity.startActivityForResult(intent, Boundary.DEFAULT_REQUEST);
        } catch (ClassNotFoundException e) {
            ErrorPrinter.print(e);
        }
    }
}
