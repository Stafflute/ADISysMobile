package util;

import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: michelesummo
 * Date: 14/08/13
 * Time: 11:37
 * To change this template use File | Settings | File Templates.
 */
public class ErrorPrinter {
    public static void print(Throwable e) {
        Log.e("AndroidRuntime", e.toString() + ": " + e.getLocalizedMessage(), e);
    }
}
