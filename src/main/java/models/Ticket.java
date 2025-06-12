package models;

import java.time.LocalDate;

public class Ticket {

    private long id;
    private LocalDate transactiondate;
    private Customer customer;
    private Event event;

    public Ticket(long id, LocalDate transactiondate, Customer customer, Event event) {
        this.id = id;
        this.transactiondate = transactiondate;
        this.customer = customer;
        this.event = event;
    }

    public Ticket(Ticket ticket) {
        this.id = ticket.id;
        this.transactiondate = ticket.transactiondate;
        this.customer = ticket.customer;
        this.event = ticket.event;
    }

    public long getId() {
        return id;
    }

    public LocalDate getTransactiondate() {
        return transactiondate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Event getEvent() {
        return event;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTransactiondate(LocalDate transactiondate) {
        this.transactiondate = transactiondate;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Ticket[" +
                "id=" + id +
                ", transactiondate=" + transactiondate +
                ", customer=" + customer +
                ", event=" + event +
                ']';
    }

    public static Ticket fromString(String string) {
        if (string == null) {
            throw new IllegalArgumentException("Input string is null");
        }
        String prefix = "Ticket[";
        String suffix = "]";
        if (!string.startsWith(prefix) || !string.endsWith(suffix)) {
            throw new IllegalArgumentException("String does not start with \"" + prefix + "\" or end with \"" + suffix + "\"");
        }
        // Inneren Teil extrahieren: id=..., transactiondate=..., customer=..., event=...
        String content = string.substring(prefix.length(), string.length() - suffix.length());
        try {
            // Schlüsselwörter
            String keyId = "id=";
            String keyTransaction = ", transactiondate=";
            String keyCustomer = ", customer=";
            String keyEvent = ", event=";

            // id
            if (!content.startsWith(keyId)) {
                throw new IllegalArgumentException("Cannot find \"" + keyId + "\" at start");
            }
            int idxTransactionKey = content.indexOf(keyTransaction);
            if (idxTransactionKey < 0) {
                throw new IllegalArgumentException("Cannot find transactiondate delimiter");
            }
            String idStr = content.substring(keyId.length(), idxTransactionKey).trim();

            // transactiondate
            int idxTransactionStart = idxTransactionKey + keyTransaction.length();
            int idxCustomerKey = content.indexOf(keyCustomer, idxTransactionStart);
            if (idxCustomerKey < 0) {
                throw new IllegalArgumentException("Cannot find customer delimiter");
            }
            String transactionStr = content.substring(idxTransactionStart, idxCustomerKey).trim();

            // customer
            int idxCustomerStart = idxCustomerKey + keyCustomer.length();
            int idxEventKey = content.indexOf(keyEvent, idxCustomerStart);
            if (idxEventKey < 0) {
                throw new IllegalArgumentException("Cannot find event delimiter");
            }
            String customerStr = content.substring(idxCustomerStart, idxEventKey).trim();

            // event
            int idxEventStart = idxEventKey + keyEvent.length();
            String eventStr = content.substring(idxEventStart).trim();

            // Parsen der Werte
            long id = Long.parseLong(idStr);
            java.time.LocalDate transactiondate = java.time.LocalDate.parse(transactionStr);

            // Customer.fromString und Event.fromString müssen existieren und exakt die toString-Formate parsen
            Customer customer = Customer.fromString(customerStr);
            Event event = Event.fromString(eventStr);

            return new Ticket(id, transactiondate, customer, event);
        } catch (Exception e) {
            throw new IllegalArgumentException("Fehler beim Parsen des Ticket-Strings: " + e.getMessage(), e);
        }
    }

}
