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
        return "Ticket{" +
                "id=" + id +
                ", transactiondate=" + transactiondate +
                ", customer=" + customer +
                ", event=" + event +
                '}';
    }
}
