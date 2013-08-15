package business.applicationservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import business.applicationservice.exception.NotStartedServiceException;
import business.entity.Accelerometro;
import business.entity.GPS;
import business.entity.Intervento;
import business.entity.InterventoCompleto;
import business.listener.accelerometer.AccelerometerListener;
import business.listener.gps.AdressParser;
import business.listener.gps.GPSListener;
import presentation.controller.ApplicationService;
import util.Parameter;

import java.util.LinkedList;
import java.util.List;

public class ApplicationServiceRilevazione implements ApplicationService {
    private static ComponentName gpsComponent;
    private static ComponentName accelerometerComponent;
    private static InterventoCompleto interventoCompleto;
    private static List<GPS> gpsList = new LinkedList<>();
    private static List<Accelerometro> accelerometroList = new LinkedList<>();

    public void startReceiving(Parameter parameter) {
        Intervento intervento = (Intervento) parameter.getValue("intervento");
        interventoCompleto = new InterventoCompleto(intervento);

        GPS adressGPS = AdressParser.getCoordinates(intervento);
        gpsList.add(adressGPS);

        Log.i("AndroidRuntime", "Parsered GPS: " + ((adressGPS != null) ? adressGPS : "null"));

        Activity activity = ApplicationServiceGeneral.activity;
        Intent intentGPS = new Intent(activity.getApplicationContext(), GPSListener.class);
        Intent intentAccelerometer = new Intent(activity.getApplicationContext(), AccelerometerListener.class);
        gpsComponent = activity.startService(intentGPS);
        accelerometerComponent = activity.startService(intentAccelerometer);

    }

    public void stopReceiving(Parameter parameter) throws NotStartedServiceException {
        if ((gpsComponent == null) || (accelerometerComponent == null)) {
            throw new NotStartedServiceException();
        }
        Activity activity = ApplicationServiceGeneral.activity;
        Intent intentGPS = new Intent(activity.getApplicationContext(), GPSListener.class);
        Intent intentAccelerometer = new Intent(activity.getApplicationContext(), AccelerometerListener.class);
        activity.stopService(intentGPS);
        activity.stopService(intentAccelerometer);
        gpsComponent = null;
        accelerometerComponent = null;
        gpsList = new LinkedList<>();
        accelerometroList = new LinkedList<>();
    }

    public static synchronized void addGPS(GPS gps) {
        gpsList.add(gps);
    }

    public static synchronized void addAccelerometro(Accelerometro accelerometro) {
        accelerometroList.add(accelerometro);
    }
}
