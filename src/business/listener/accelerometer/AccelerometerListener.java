package business.listener.accelerometer;

import android.app.Service;
import android.content.Intent;
import android.hardware.SensorListener;
import android.os.IBinder;

public class AccelerometerListener extends Service implements SensorListener {


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
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {

        return START_STICKY;
    }

    @Override
    public void onSensorChanged(int sensor, float[] values) {

    }

    @Override
    public void onAccuracyChanged(int sensor, int accuracy) {

    }
}
