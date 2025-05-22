package logging;

import models.Customer;

import java.time.LocalDateTime;

public class CreateCustomerEvent implements LoggableEvent {

    private Customer customer;
    private LocalDateTime eventTime;
    private String threadString;

    public CreateCustomerEvent(Customer customer) {
        this.customer = customer;
        this.eventTime = LocalDateTime.now();
        this.threadString = Thread.currentThread().getId() + ":" + Thread.currentThread().getName();
    }

    @Override
    public long getIdReference() {
        return customer.getId();
    }

    @Override
    public String getEventName() {
        return "Create-Customer";
    }

    @Override
    public LocalDateTime getEventTime() {
        return eventTime;
    }

    @Override
    public String getThreadString() {
        return "";
    }


}
