package business.applicationservice;

import android.app.Activity;
import android.content.Context;
import presentation.controller.ApplicationService;
import util.Parameter;

/**
 * Created with IntelliJ IDEA.
 * User: michelesummo
 * Date: 14/08/13
 * Time: 13:01
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationServiceToast implements ApplicationService {

    public void startToast(Parameter parameter) {
        Context context = (Context) parameter.getValue("context");
        ErrorToastDialog.context = context;
    }
}
