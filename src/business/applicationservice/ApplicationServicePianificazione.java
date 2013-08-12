package business.applicationservice;

/**
 * Created with IntelliJ IDEA.
 * User: michelesummo
 * Date: 12/08/13
 * Time: 11:26
 * To change this template use File | Settings | File Templates.
 */

import android.util.Log;
import presentation.controller.ApplicationService;
import util.AndroidPath;
import util.FolderManager;
import util.Parameter;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class ApplicationServicePianificazione implements ApplicationService {

    private static final String DATE_REGEX = "(([0-9])+[-]){2}([0-9])+";
    private static final String TIME_REGEX = "(([0-9])+[_]){2}([0-9])+([.]([0-9])+)?";
    private static final String XML_EXTENSION= "xml";
    private static final String FILE_SYNTAX_REGEX = "pianificazione[ ]"+DATE_REGEX+"T"+TIME_REGEX + "[.]" + XML_EXTENSION;

    private static final String ROOT_PATH = "adisysmobile";
    private static final String IMPORTAZIONE_PATH = "importazione";
    private static final String CANONICAL_IMPORTAZIONE_PATH = ROOT_PATH + File.separator + IMPORTAZIONE_PATH ;

    private FileFilter filter = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            String fileName = pathname.getName();

            //Log.d("AndroidRuntime" , fileName);
            return fileName.matches(FILE_SYNTAX_REGEX);
        }
    };


    public List<String> getFileList(Parameter parameter) {
        List<String> fileListString = null;


        FolderManager.insertFolderIfNotExists(AndroidPath.SD_PATH + ROOT_PATH);
        FolderManager.insertFolderIfNotExists(AndroidPath.SD_PATH + CANONICAL_IMPORTAZIONE_PATH);

        File folder = new File(AndroidPath.SD_PATH +CANONICAL_IMPORTAZIONE_PATH);

        fileListString = new ArrayList<>();
        File[] fileList = folder.listFiles(filter);

        for(File file : fileList) {
            String fileName = file.getName();
            fileListString.add(fileName);
        }

        return fileListString;
    }
}
