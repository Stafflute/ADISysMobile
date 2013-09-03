package business.applicationservice;

import android.app.Activity;
import android.content.Context;
import presentation.controller.ApplicationService;
import utility.Parameter;

/**
 * Created with IntelliJ IDEA.
 * User: michelesummo
 * Date: 14/08/13
 * Time: 13:01
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationServiceGeneral implements ApplicationService {

    public static Activity activity = null;

    public void start(Parameter parameter) {

        activity = (Activity) parameter.getValue("activity");
        Context context = activity.getApplicationContext();
        ErrorToastDialog.context = context;
    }
}
