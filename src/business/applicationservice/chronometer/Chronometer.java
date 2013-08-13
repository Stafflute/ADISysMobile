package business.applicationservice.chronometer;

import org.joda.time.LocalTime;

public class Chronometer extends Thread{
    private static LocalTime elapsed_time = new LocalTime(0, 0, 0);

}
