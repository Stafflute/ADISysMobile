package util.xml.parser;

import business.applicationservice.exception.CommonException;

/**
 * Created with IntelliJ IDEA.
 * User: michelesummo
 * Date: 14/08/13
 * Time: 12:36
 * To change this template use File | Settings | File Templates.
 */
public class NotValidatedPianificazioneFormatException extends CommonException {
    @Override
    public void reportException() {
        String fileName = getMessage();

    }
}
