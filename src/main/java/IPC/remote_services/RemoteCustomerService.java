package IPC.remote_services;

import IPC.TCPClient;
import interfaces.CustomerServiceInterface;
import models.Customer;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RemoteCustomerService implements CustomerServiceInterface {

    private TCPClient tcpClient;

    public RemoteCustomerService(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }


    @Override
    public Customer createCustomer(String username, String email, LocalDate dateofbirth) throws IllegalArgumentException {
        try {
            String response = tcpClient.send("customer;create;" + username + "," + email + "," + dateofbirth);
            return Customer.fromString(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer getCustomerById(long id) {
        return null;
    }

    @Override
    public void updateCustomer(Customer customer) throws IllegalArgumentException {

    }

    @Override
    public void deleteCustomer(long id) throws IllegalArgumentException {

    }

    @Override
    public List<Customer> getAllCustomers() {

        try {
            String response = tcpClient.send("customer;getall;");
            String[] customerString = response.split(";");
            List<Customer> customers = new ArrayList<>();
            for(String s : customerString) {
                customers.add(Customer.fromString(s));
            }
            return customers;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteAllCustomers() {

    }
}
