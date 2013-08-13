package util;

import org.joda.time.LocalDateTime;

import java.io.File;
import java.io.Serializable;

public class PianificazioneFile implements Serializable, Comparable<PianificazioneFile> {
    private static final long serialVersionUID = 6589598624526109234L;

    private final static transient String FILE_HEADER = "Pianificazione ";
    private final static transient int HEADER_LENGTH = FILE_HEADER.length();
    private final static transient char HOUR_SEPARATOR = '_';
    private final static transient char NORMAL_HOUR_SEPARATOR = ':';
    private final static transient String XML_EXTENSION = ".xml";
    private final static transient int XML_EXTENSION_LENGTH =  XML_EXTENSION.length();
    private final static transient String PIANIFICAZIONE_PATH = AndroidPath.SD_PATH + "adisysmobile/importazione/";

    private String fileName;
    private LocalDateTime dateTime;
    private File file;

    public PianificazioneFile(String fileName) {
        this.fileName = fileName;
        int dateTimeEndPosition = fileName.length() - XML_EXTENSION_LENGTH;
        String dateTimeString = fileName.substring(HEADER_LENGTH, dateTimeEndPosition);
        dateTimeString =  dateTimeString.replace(HOUR_SEPARATOR, NORMAL_HOUR_SEPARATOR);
        dateTime = LocalDateTime.parse(dateTimeString);
        file = new File(PIANIFICAZIONE_PATH + fileName);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getFileName() {
        return fileName;
    }

    public File getFile() {
        return file;
    }

    @Override
    public int compareTo(PianificazioneFile another) {
        LocalDateTime anotherDateTime =  another.getDateTime();
        return anotherDateTime.compareTo(dateTime);

    }

    public String toString() {
        return dateTime.toString(DateConverter.EUROPEAN_DATE_TIME_FORMAT);
    }
}
