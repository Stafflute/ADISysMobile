package business.applicationservice.exception;

import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: michelesummo
 * Date: 14/08/13
 * Time: 12:00
 * To change this template use File | Settings | File Templates.
 */
public class NotStartedServiceException extends CommonException {
    @Override
    public void reportException() {
        Log.i("AndroidRuntime", "GPS and Accelerometer Service is just stopped");
    }
}
