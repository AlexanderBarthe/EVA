package IPC.remote_services;

import IPC.TCPClient;
import interfaces.CustomerServiceInterface;
import models.Customer;

import java.time.LocalDate;
import java.util.List;

public class RemoteCustomerService implements CustomerServiceInterface {

    private TCPClient tcpClient;

    public RemoteCustomerService(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }


    @Override
    public Customer createCustomer(String username, String email, LocalDate dateofbirth) throws IllegalArgumentException {
        return null;
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
        return List.of();
    }

    @Override
    public void deleteAllCustomers() {

    }
}
