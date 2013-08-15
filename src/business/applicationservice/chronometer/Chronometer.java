package business.applicationservice.chronometer;

import org.joda.time.LocalTime;

public class Chronometer {
    private Long begin = null;
    private Long end = null;
    private LocalTime elapsed;

    public void start() {
        end = null;
        elapsed = null;
        begin = System.currentTimeMillis();
    }

    public void stop() {
        if (begin != null) {
            end = System.currentTimeMillis();
            elapsed = new LocalTime(end - begin);
        }
    }

    public LocalTime getElapsed() {
        return elapsed;
    }
}
