package business.applicationservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import business.applicationservice.exception.NotStartedServiceException;
import business.entity.*;
import business.listener.accelerometer.AccelerometerListener;
import business.listener.gps.AdressParser;
import business.listener.gps.GPSListener;
import org.joda.time.LocalTime;
import presentation.controller.ApplicationService;
import util.Parameter;

import java.util.LinkedList;
import java.util.List;

public class ApplicationServiceRilevazione implements ApplicationService {
    private static ComponentName gpsComponent;
    private static ComponentName accelerometerComponent;
    private static InterventoCompleto interventoCompleto;
    private static List<Operazione> operazioneList;
    private static List<GPS> gpsList = new LinkedList<>();
    private static List<Accelerometro> accelerometroList = new LinkedList<>();

    public synchronized void startReceiving(Parameter parameter) throws NotStartedServiceException {
        gpsList = new LinkedList<>();
        accelerometroList = new LinkedList<>();

        Intervento intervento = (Intervento) parameter.getValue("intervento");
        interventoCompleto = new InterventoCompleto(intervento);
        operazioneList = interventoCompleto.getOperazione();

        GPS adressGPS = AdressParser.getCoordinates(intervento);
        gpsList.add(adressGPS);

        //Check if the signal listening is active
        if ((gpsComponent != null) || (accelerometerComponent != null)) {
            stopReceiving(parameter);
        }

        Log.i("AndroidRuntime", "Parsered GPS geocoding: " + ((adressGPS != null) ? adressGPS : "null"));

        Activity activity = ApplicationServiceGeneral.activity;
        Intent intentGPS = new Intent(activity.getApplicationContext(), GPSListener.class);
        Intent intentAccelerometer = new Intent(activity.getApplicationContext(), AccelerometerListener.class);
        gpsComponent = activity.startService(intentGPS);
        accelerometerComponent = activity.startService(intentAccelerometer);

    }

    public synchronized void stopReceiving(Parameter parameter) throws NotStartedServiceException {
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
        gpsList = null;
        accelerometroList = null;
    }

    public static synchronized void addGPS(GPS gps) {
        if (gpsList != null) {
            gpsList.add(gps);
        }
    }

    public static synchronized void addAccelerometro(Accelerometro accelerometro) {
        if (accelerometroList != null) {
            accelerometroList.add(accelerometro);
        }
    }

    public synchronized void registerValues(Parameter parameter) {
        int position = (int) parameter.getValue("posizione");
        Operazione operazione = operazioneList.get(position);

        String nota = (String) parameter.getValue("nota");
        operazione.setNota(nota);

        ValoreRilevato valoreRilevato = new ValoreRilevato();
        String misura = (String) parameter.getValue("misura");
        LocalTime tempoOperazione = new LocalTime((long) parameter.getValue("tempoOperazione"));

        valoreRilevato.setMisura(misura);
        valoreRilevato.setTempoOperazione(tempoOperazione);

        operazione.setValoreRilevato(valoreRilevato);
        Log.i("AndroidRuntime", "registered values at id " + operazioneList.get(position).getId() + " in " + tempoOperazione + " secs");
    }


}
