package business.applicationservice.exception;

import android.util.Log;
import business.applicationservice.ErrorToastDialog;

/**
 * Created with IntelliJ IDEA.
 * User: michelesummo
 * Date: 14/08/13
 * Time: 12:00
 * To change this template use File | Settings | File Templates.
 */
public class NotStartedGPSServiceException extends CommonException {
    public NotStartedGPSServiceException(String s) {
    }

    @Override
    public void reportException() {
        Log.i("AndroidRuntime", "GPS Service is just stopped");
    }
}
