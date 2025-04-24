package models;

import java.time.LocalDate;

public class Customer {

    private long id;
    private String username;
    private String email;
    private LocalDate dateofbirth;

    public Customer(long id, String username, String email, LocalDate birth) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.dateofbirth = birth;
    }

    public Customer(Customer customer) {
        this.id = customer.id;
        this.username = customer.username;
        this.email = customer.email;
        this.dateofbirth = customer.dateofbirth;
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

    @Override
    public String toString(){
        return "Kunde: [id: " + id + ", username: " + username + ", email: " + email + ", dateofbirth: " + dateofbirth + "]";
    }


}
