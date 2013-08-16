package integration.listener.accelerometer;

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

    private int x = 0;
    private int y = 0;
    private int z = 0;

    private int count = 0;
    private final static int MAX_COUNT = 40;

    private static final int SENSOR_DELAY_VERY_HIGH = 15 * 1000000;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private boolean startSignal = true;

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
        synchronized (this) {
            x += event.values[X];
            y += event.values[Y];
            z += event.values[Z];

            count++;

            if ((count >= MAX_COUNT) || startSignal) {
                Accelerometro accelerometro = new Accelerometro();

                accelerometro.setX(x / count);
                accelerometro.setY(y / count);
                accelerometro.setZ(z / count);

                x = 0;
                y = 0;
                z = 0;

                count = 0;

                ApplicationServiceRilevazione.addAccelerometro(accelerometro);
                Log.d("AndroidRuntime", "Accelerometer coords: " + accelerometro.toString());

                startSignal = false;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
