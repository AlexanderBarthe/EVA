package logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class LogService {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");

    private boolean loggingActive;

    private String logFileName;
    private static final String logFilePath = "logs/";

    private static File logFile;



    public LogService(boolean loggingActive) {
        logFileName = dateTimeFormatter.format(java.time.LocalDateTime.now()) + ".log";
        this.loggingActive = loggingActive;
        initFile();
    }

    /**
     *
     * Crates dir if not existing and new log file.
     *
     */
    public void initFile() {
        File dir = new File(logFilePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(logFilePath + logFileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        logFile = file;
    }

    public void log(LoggableEvent logEvent) {

        log(logEvent, "");

    }

    public void log(LoggableEvent event, String message) {

        if(!loggingActive) return;

        //Write time, origin, log level and description
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            writer.write(
                    "[" + event.getEventTime().format(dateTimeFormatter) + "] "
                            + "[" + event.getEventName() + " - " + event.getIdReference() + "] "
                            + message + " (Thread: "
                            + event.getThreadString() + ")\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
