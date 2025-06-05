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

    private BufferedWriter writer;

    public LogService(boolean loggingActive) {
        logFileName = dateTimeFormatter.format(java.time.LocalDateTime.now()) + ".log";
        this.loggingActive = loggingActive;
        init();

    }

    /**
     *
     * Crates dir if not existing and new log file.
     *
     */
    public void init() {
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

        try {
            writer = new BufferedWriter(new FileWriter(logFile, true));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void log(LoggableEvent logEvent) {

        log(logEvent, "");

    }

    public void log(LoggableEvent event, String message) {

        if(!loggingActive) return;

        try {
            writer.write(
                        "[" + event.getEventTime().format(dateTimeFormatter) + "] "
                                + "[" + event.getEventName() + " - " + event.getIdReference() + "] "
                                + message + " (Thread: "
                                + event.getThreadString() + ")\n");
        } catch (IOException ignored) {

        }


    }

    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
