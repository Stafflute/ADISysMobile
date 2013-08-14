package business.applicationservice;

import android.content.Context;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: michelesummo
 * Date: 14/08/13
 * Time: 12:41
 * To change this template use File | Settings | File Templates.
 */
public class ErrorToastDialog {
    static Context context;

    public static void show(String text) {
        if(context != null) {
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
