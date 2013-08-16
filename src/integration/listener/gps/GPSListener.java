package integration.listener.gps;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import business.applicationservice.ApplicationServiceRilevazione;
import business.entity.GPS;

public class GPSListener extends Service implements LocationListener {

    private static final int MIN_TIME = 60000;
    private LocationManager locationManager;

    private static final String START_LISTENING = "startListening";
    private static final String STOP_LISTENING = "stopListening";

    public GPSListener() {

    }

    @Override
    public void onLocationChanged(Location location) {
        GPS gps = new GPS();

        gps.setLatitudine(location.getLatitude());
        gps.setLongitudine(location.getLongitude());

        ApplicationServiceRilevazione.addGPS(gps);
        Log.i("AndroidRuntime", "Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    public void destroy() {
        this.stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(this);
        locationManager = null;
    }

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, 0, this);

        return START_STICKY;

    }
}
