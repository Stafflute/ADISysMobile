package business.applicationservice.exception;

import business.applicationservice.ErrorToastDialog;

/**
 * Created with IntelliJ IDEA.
 * User: michelesummo
 * Date: 14/08/13
 * Time: 13:54
 * To change this template use File | Settings | File Templates.
 */
public class PianificazioneNotFoundException extends CommonException {

    @Override
    public void reportException() {
        ErrorToastDialog.show("No Pianificazione file found");
    }
}
