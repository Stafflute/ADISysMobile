package business.applicationservice;

/**
 * Created with IntelliJ IDEA.
 * User: michelesummo
 * Date: 12/08/13
 * Time: 11:26
 * To change this template use File | Settings | File Templates.
 */

import java.util.List;

public class ApplicationServicePianificazione {

    private static final String DATE_REGEX = "(([0-9])+[-]){2}([0-9])+";
    private static final String TIME_REGEX = "(([0-9])+[_]){2}([0-9])([.](0-9)+)?";
    private static final String FILE_SYNTAX_REGEX = "pianificazione[ ]"+DATE_REGEX+"T"+TIME_REGEX;

    public List<String> getFileList() {
        return null;
    }
}
