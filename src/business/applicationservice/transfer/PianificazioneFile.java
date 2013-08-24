package business.applicationservice.transfer;

import org.joda.time.LocalDate;
import util.AndroidPath;
import util.DateConverter;

import java.io.File;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PianificazioneFile implements Serializable, Comparable<PianificazioneFile> {
    private static final long serialVersionUID = 6589598624526109234L;

    private final static transient String FILE_HEADER = "Pianificazione ";
    private final static transient String FILE_HEADER_REGEX = "pianificazione[ ]";

    private final static transient String XML_EXTENSION = "xml";
    private final static transient String XML_EXTENSION_REGEX = "[.]" + XML_EXTENSION;

    private final static transient String PIANIFICAZIONE_PATH = AndroidPath.SD_PATH + "adisysmobile/importazione/";
    private static final transient String DATE_REGEX_STRING = "(([0-9])+[-]){2}([0-9])+";

    private static final transient String FOOTER_REGEX = DATE_REGEX_STRING + XML_EXTENSION_REGEX;

    private static final transient Pattern DATE_REGEX = Pattern.compile(DATE_REGEX_STRING);
    private static final transient int FIRST_SUBGROUP = 1;

    private String fileName;
    private LocalDate date;
    private File file;
    private String infermiereName;

    public PianificazioneFile(String fileName) {
        this.fileName = fileName;

        String dateTimeString = null;
        Matcher matcher = DATE_REGEX.matcher(fileName);
        while (matcher.find()) {
            dateTimeString = matcher.group(FIRST_SUBGROUP);
        }

        infermiereName = fileName.replaceFirst(FILE_HEADER_REGEX, "");
        infermiereName = infermiereName.replaceFirst(FOOTER_REGEX, "");

        date = LocalDate.parse(dateTimeString);
        file = new File(PIANIFICAZIONE_PATH + fileName);
    }

    public LocalDate getDateTime() {
        return date;
    }

    public String getFileName() {
        return fileName;
    }

    public String getInfermiereName() {
        return infermiereName;
    }

    public File getFile() {
        return file;
    }

    @Override
    public int compareTo(PianificazioneFile another) {
        LocalDate anotherDateTime = another.getDateTime();
        return date.compareTo(anotherDateTime);

    }

    public String toString() {
        return infermiereName + " " + date.toString(DateConverter.EUROPEAN_DATE_FORMAT);
    }
}
