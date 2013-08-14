package business.applicationservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import business.applicationservice.exception.NotStartedGPSServiceException;
import business.listener.gps.GPSListener;
import presentation.controller.ApplicationService;
import util.Parameter;

public class ApplicationServiceGPS implements ApplicationService {
   private static ComponentName gpsComponent;

    public void startReceiving(Parameter parameter) {
        Activity activity = (Activity) parameter.getValue("activity");
        Intent intent = new Intent(activity.getApplicationContext(), GPSListener.class);
        gpsComponent = activity.startService(intent);
    }

    public void stopReceiving(Parameter parameter) throws NotStartedGPSServiceException {
        if(gpsComponent == null) {
             throw new NotStartedGPSServiceException("Trying to stop a stopped GPS Service");
        }
        Activity activity = (Activity) parameter.getValue("activity");
        Intent intent = new Intent(activity.getApplicationContext(), GPSListener.class);
        activity.stopService(intent);
        gpsComponent = null;
    }
}
