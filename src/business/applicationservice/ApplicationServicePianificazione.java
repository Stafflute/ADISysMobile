package business.applicationservice;

/**
 * Created with IntelliJ IDEA.
 * User: michelesummo
 * Date: 12/08/13
 * Time: 11:26
 * To change this template use File | Settings | File Templates.
 */

import android.util.Log;
import business.applicationservice.exception.CommonException;
import business.applicationservice.exception.NotValidatedPianificazioneFormatException;
import business.applicationservice.exception.PianificazioneNotFoundException;
import business.applicationservice.transfer.PianificazioneFile;
import business.entity.Pianificazione;
import integration.xml.parser.PianificazioneParser;
import integration.xml.validator.Validator;
import presentation.controller.ApplicationService;
import util.AndroidPath;
import util.ErrorPrinter;
import util.FolderManager;
import util.Parameter;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApplicationServicePianificazione implements ApplicationService {

    private static final String DATE_REGEX = "(([0-9])+[-]){2}([0-9])+";
    private static final String XML_EXTENSION = "xml";
    private static final String FILE_SYNTAX_REGEX = "pianificazione[ ]" + "([a-zA-Z ])+[ ]" + DATE_REGEX + "[.]" + XML_EXTENSION;

    private static final String ROOT_PATH = "adisysmobile";
    private static final String IMPORTAZIONE_PATH = "importazione";
    private static final String CANONICAL_IMPORTAZIONE_PATH = ROOT_PATH + File.separator + IMPORTAZIONE_PATH;

    private static final String XML_PIANIFICAZIONE_SCHEMA = "schema/XMLPianificazioneSchema.xsd";

    private FileFilter filter = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            String fileName = pathname.getName();

            return fileName.matches(FILE_SYNTAX_REGEX);
        }
    };


    public List<PianificazioneFile> getFileList(Parameter parameter) {
        List<PianificazioneFile> fileListDetailed = null;

        try {
            FolderManager.insertFolderIfNotExists(AndroidPath.SD_PATH + ROOT_PATH);
            FolderManager.insertFolderIfNotExists(AndroidPath.SD_PATH + CANONICAL_IMPORTAZIONE_PATH);

            File folder = new File(AndroidPath.SD_PATH + CANONICAL_IMPORTAZIONE_PATH);

            fileListDetailed = new ArrayList<>();
            File[] fileList = folder.listFiles(filter);

            for (File file : fileList) {
                String fileName = file.getName();
                PianificazioneFile decoratedFileName = new PianificazioneFile(fileName);

                fileListDetailed.add(decoratedFileName);
            }

            Collections.sort(fileListDetailed);
        } catch (Throwable e) {
            Log.e("AndroidRuntime", e.toString() + ": " + e.getLocalizedMessage(), e);
        }

        return fileListDetailed;
    }

    public Pianificazione getPianificazione(Parameter parameter) {
        Pianificazione pianificazione = null;

        PianificazioneFile fileDetailed = (PianificazioneFile) parameter.getValue("pianificazione");
        File file = fileDetailed.getFile();

        try {
            pianificazione = PianificazioneParser.parse(file);
        } catch (Throwable e) {
            ErrorPrinter.print(e);
        }

        return pianificazione;
    }

    public Boolean checkValid(Parameter parameter) throws CommonException {
        PianificazioneFile fileDetailed = (PianificazioneFile) parameter.getValue("pianificazione");

        if (fileDetailed == null) {
            throw new PianificazioneNotFoundException();
        }

        File file = fileDetailed.getFile();

        Boolean valid = Validator.validate(file, XML_PIANIFICAZIONE_SCHEMA);

        if (!valid) {
            throw new NotValidatedPianificazioneFormatException();
        }

        return valid;
    }
}
