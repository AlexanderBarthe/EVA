package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Customer {

    private long id;
    private String username;
    private String email;
    private LocalDate dateofbirth;
    private List<Ticket> tickets;
    private Map<Long, Integer> ticketPerEvent;

    public Customer(long id, String username, String email, LocalDate birth) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.dateofbirth = birth;
        this.tickets = new ArrayList<>();
        this.ticketPerEvent = new ConcurrentHashMap<>();
    }

    public Customer(Customer customer) {
        this.id = customer.id;
        this.username = customer.username;
        this.email = customer.email;
        this.dateofbirth = customer.dateofbirth;
        this.tickets = customer.tickets;
        this.ticketPerEvent = customer.getTicketPerEvent();
    }

    public long getId(){
        return this.id;
    }
    public void setId(long id){
        this.id = id;
    }
    public String getUsername(){
        return this.username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public LocalDate getDateofbirth(){
        return this.dateofbirth;
    }
    public void setDateofbirth(LocalDate birth){
        this.dateofbirth = birth;
    }
    public void addTicket(Ticket ticket){tickets.add(ticket);}
    public List<Ticket> getTickets() {
        return tickets;
    }

    public Map<Long, Integer> getTicketPerEvent() {
        return ticketPerEvent;
    }

    public void setTicketPerEvent(Long eventId, Integer ticketcount) {
        ticketPerEvent.put(eventId, ticketcount);
    }


    @Override
    public String toString() {
        return "Customer [id=" + id
                + ",username=" + username
                + ", email=" + email
                + ", dateofbirth=" + dateofbirth
                + "]";
    }

    public static Customer fromString(String string) {
        if (string == null) {
            throw new IllegalArgumentException("Input string is null");
        }
        String prefix = "Customer [";
        String suffix = "]";
        if (!string.startsWith(prefix) || !string.endsWith(suffix)) {
            throw new IllegalArgumentException(
                    "String does not start with \"" + prefix + "\" or end with \"" + suffix + "\"");
        }
        String content = string.substring(prefix.length(), string.length() - suffix.length());
        try {
            // Schlüssel für die Teile im toString
            String keyId = "id=";
            String keyUsername = ",username=";
            String keyEmail = ", email=";
            String keyDob = ", dateofbirth=";

            // id
            if (!content.startsWith(keyId)) {
                throw new IllegalArgumentException("Cannot find \"" + keyId + "\" at start");
            }
            int idxUsername = content.indexOf(keyUsername);
            if (idxUsername < 0) {
                throw new IllegalArgumentException("Cannot find username delimiter");
            }
            String idStr = content.substring(keyId.length(), idxUsername).trim();

            // username
            int idxUsernameStart = idxUsername + keyUsername.length();
            int idxEmail = content.indexOf(keyEmail, idxUsernameStart);
            if (idxEmail < 0) {
                throw new IllegalArgumentException("Cannot find email delimiter");
            }
            String usernameStr = content.substring(idxUsernameStart, idxEmail).trim();

            // email
            int idxEmailStart = idxEmail + keyEmail.length();
            int idxDob = content.indexOf(keyDob, idxEmailStart);
            if (idxDob < 0) {
                throw new IllegalArgumentException("Cannot find dateofbirth delimiter");
            }
            String emailStr = content.substring(idxEmailStart, idxDob).trim();

            // dateofbirth
            int idxDobStart = idxDob + keyDob.length();
            String dobStr = content.substring(idxDobStart).trim();

            // Parsen
            long id = Long.parseLong(idStr);
            String username = usernameStr;
            String email = emailStr;
            java.time.LocalDate dateofbirth = java.time.LocalDate.parse(dobStr);

            // Neues Customer-Objekt mit leerer Ticket-Liste und leerer Map
            return new Customer(id, username, email, dateofbirth);
        } catch (Exception e) {
            throw new IllegalArgumentException("Fehler beim Parsen des Customer-Strings: " + e.getMessage(), e);
        }
    }



}
