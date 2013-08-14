package business.applicationservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import business.applicationservice.exception.NotStartedGPSServiceException;
import business.listener.gps.GPSListener;
import presentation.controller.ApplicationService;
import util.Parameter;

public class ApplicationServiceRilevazione implements ApplicationService {
    private static ComponentName gpsComponent;

    public void startReceiving(Parameter parameter) {
        Activity activity = ApplicationServiceGeneral.activity;
        Intent intent = new Intent(activity.getApplicationContext(), GPSListener.class);
        gpsComponent = activity.startService(intent);
    }

    public void stopReceiving(Parameter parameter) throws NotStartedGPSServiceException {
        if (gpsComponent == null) {
            throw new NotStartedGPSServiceException();
        }
        Activity activity = ApplicationServiceGeneral.activity;
        Intent intent = new Intent(activity.getApplicationContext(), GPSListener.class);
        activity.stopService(intent);
        gpsComponent = null;
    }
}
