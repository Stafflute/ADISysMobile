package business.applicationservice.exception;

import business.applicationservice.ErrorToastDialog;

/**
 * Created with IntelliJ IDEA.
 * User: michelesummo
 * Date: 14/08/13
 * Time: 12:36
 * To change this template use File | Settings | File Templates.
 */
public class NotValidatedPianificazioneFormatException extends CommonException {
    public NotValidatedPianificazioneFormatException(String message) {
        super();
    }

    public NotValidatedPianificazioneFormatException() {
    }

    @Override
    public void reportException() {
        ErrorToastDialog.show("File is not a valid Pianificazione file");
    }
}
