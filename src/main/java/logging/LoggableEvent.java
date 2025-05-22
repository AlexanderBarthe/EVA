package logging;

import java.time.LocalDateTime;

public interface LoggableEvent {

    long getIdReference();

    String getEventName();

    LocalDateTime getEventTime();

    String getThreadString();

}
