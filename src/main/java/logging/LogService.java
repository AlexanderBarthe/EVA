package logging;

import java.time.format.DateTimeFormatter;

public class LogService {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");

    public LogService() {

    }

    public void log(LoggableEvent logEvent) {

        log(logEvent, "");

    }

    public void log(LoggableEvent logEvent, String message) {
        String dateTimeString = logEvent.getEventTime().format(dateTimeFormatter);
        String threadString = logEvent.getThreadString();

        System.out.println(dateTimeString + ";" + threadString + ";" + logEvent.getEventName() + ";" + logEvent.getIdReference() +  ";" + message);
    }


}
