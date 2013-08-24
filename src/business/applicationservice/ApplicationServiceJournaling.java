package business.applicationservice;

import android.util.Log;
import business.applicationservice.transfer.PianificazioneFile;
import integration.xml.builder.JournalingBuilder;
import integration.xml.parser.JournalingSimpleParser;
import integration.xml.validator.Validator;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import presentation.controller.ApplicationService;
import util.AndroidPath;
import util.FolderManager;
import util.Parameter;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class ApplicationServiceJournaling implements ApplicationService {

    private final static transient String JOURNALING_PATH = AndroidPath.SD_PATH + "adisysmobile/esportazione/";
    private static final String XML_JOURNALING_SCHEMA = "schema/XMLJournalingSchema.xsd";
    private static final String JOURNALING_HEADER = "Journaling ";
    private static final String XML_EXTENSION = "xml";
    private static final String EXTENSION_START = ".";
    private static final String COMPLETE_EXTENSION = EXTENSION_START + XML_EXTENSION;
    private static final DateTimeFormatter FILE_TIME_FORMAT = DateTimeFormat.forPattern("yyyy'-'MM'-'dd'T'HH'_'mm'_'ss'.'SSS");

    public Set<String> readJournalingFile(Parameter parameter) {
        PianificazioneFile pianificazioneFile = (PianificazioneFile) parameter.getValue("pianificazione");

        boolean toValidate = (boolean) parameter.getValue("validazione");

        FolderManager.insertFolderIfNotExists(JOURNALING_PATH);

        Set<String> idSet = new HashSet<>();

        String dateTimeString = pianificazioneFile.getDateTime().toString(FILE_TIME_FORMAT);

        String journalingFileName = JOURNALING_HEADER + dateTimeString + COMPLETE_EXTENSION;

        File journalingFile = new File(JOURNALING_PATH + journalingFileName);

        boolean fileExists = journalingFile.exists();

        boolean validated = false;
        if (fileExists) {
            if (toValidate) {
                validated = Validator.validate(journalingFile, XML_JOURNALING_SCHEMA);
            } else {
                validated = true;
                Log.i("AndroidRuntime", "file is already validated");
            }
        }

        if (fileExists && validated) {
            idSet = JournalingSimpleParser.parse(journalingFile);
        } else {
            JournalingBuilder.buildEmptyJournaling(journalingFile);
        }

        ApplicationServiceRilevazione.setJournalingFile(journalingFile);

        return idSet;
    }
}
