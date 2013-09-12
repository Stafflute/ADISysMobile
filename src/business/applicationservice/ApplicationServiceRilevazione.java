package business.applicationservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import business.applicationservice.exception.NotStartedServiceException;
import business.entity.*;
import integration.listener.accelerometer.AccelerometerListener;
import integration.listener.gps.AddressParser;
import integration.listener.gps.GPSListener;
import integration.xml.builder.JournalingBuilder;
import org.joda.time.LocalTime;
import presentation.controller.ApplicationService;
import business.applicationservice.transfer.Parameter;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class ApplicationServiceRilevazione implements ApplicationService {
    private static ComponentName gpsComponent;
    private static ComponentName accelerometerComponent;
    private static InterventoCompleto interventoCompleto;
    private static List<Operazione> operazioneList;
    private static List<GPS> gpsList = new LinkedList<GPS>();
    private static List<Accelerometro> accelerometroList = new LinkedList<Accelerometro>();
    private static File journalingFile;

    public synchronized void startReceiving(Parameter parameter) throws NotStartedServiceException {
        gpsList = new LinkedList<GPS>();
        accelerometroList = new LinkedList<Accelerometro>();

        Intervento intervento = (Intervento) parameter.getValue("intervento");
        interventoCompleto = new InterventoCompleto(intervento);
        operazioneList = interventoCompleto.getOperazione();

        GPS adressGPS = AddressParser.getCoordinates(intervento);
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
        int position = (Integer) parameter.getValue("posizione");
        Operazione operazione = operazioneList.get(position);

        String nota = (String) parameter.getValue("nota");
        operazione.setNota(nota);

        ValoreRilevato valoreRilevato = new ValoreRilevato();
        String misura = (String) parameter.getValue("misura");
        LocalTime tempoOperazione = new LocalTime((Long) parameter.getValue("tempoOperazione"));

        valoreRilevato.setMisura(misura);
        valoreRilevato.setTempoOperazione(tempoOperazione);

        operazione.setValoreRilevato(valoreRilevato);

        //operazioneList.remove(position);
        //operazioneList.add(position, operazione);

        Log.i("AndroidRuntime", "registered values at id " + operazioneList.get(position).getId() + " in " + tempoOperazione + " secs");
    }

    public synchronized void completeIntervento(Parameter parameter) throws NotStartedServiceException {
        InterventoCompleto interventoCompletoResult = interventoCompleto;
        interventoCompletoResult.setGps(gpsList);
        interventoCompletoResult.setAccelerometro(accelerometroList);
        interventoCompletoResult.setOperazione(operazioneList);

        stopReceiving(null);

        Log.d("AndroidRuntime", journalingFile.toString());

        JournalingBuilder.fillInterventoCompleto(journalingFile, interventoCompletoResult);
    }

    static synchronized void setJournalingFile(File journalingFileToInsert) {
        journalingFile = journalingFileToInsert;
    }
}
