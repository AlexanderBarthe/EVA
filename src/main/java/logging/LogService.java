package logging;

import java.time.format.DateTimeFormatter;

public class LogService {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");

    private boolean loggingActive;

    public LogService(boolean loggingActive) {
        this.loggingActive = loggingActive;
    }

    public void log(LoggableEvent logEvent) {

        log(logEvent, "");

    }

    public void log(LoggableEvent logEvent, String message) {

        if(!loggingActive) return;

        String dateTimeString = logEvent.getEventTime().format(dateTimeFormatter);
        String threadString = logEvent.getThreadString();

        System.out.println(dateTimeString + ";" + threadString + ";" + logEvent.getEventName() + ";" + logEvent.getIdReference() +  ";" + message);
    }


}
