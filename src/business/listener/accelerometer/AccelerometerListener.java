package business.listener.accelerometer;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import business.applicationservice.ApplicationServiceGeneral;
import business.applicationservice.ApplicationServiceRilevazione;
import business.entity.Accelerometro;

public class AccelerometerListener extends Service implements SensorEventListener {
    private static final int X = 0;
    private static final int Y = 1;
    private static final int Z = 2;

    private static final int SENSOR_DELAY_VERY_HIGH = 15 * 1000000;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    public AccelerometerListener() {

    }

    public void destroy() {
        this.stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Activity activity = ApplicationServiceGeneral.activity;
        sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {

        return START_STICKY;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Accelerometro accelerometro = new Accelerometro();

        double x = event.values[X];
        double y = event.values[Y];
        double z = event.values[Z];

        accelerometro.setX(x);
        accelerometro.setY(y);
        accelerometro.setZ(z);

        ApplicationServiceRilevazione.addAccelerometro(accelerometro);
        Log.d("AndroidRuntime", "Accelerometer coords: " + accelerometro.toString());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
