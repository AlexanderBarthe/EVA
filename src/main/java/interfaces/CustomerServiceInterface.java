package interfaces;

import models.Customer;

import java.time.LocalDate;

public interface CustomerServiceInterface {
    Customer createCustomer(String username, String email, LocalDate dateofbirth) throws IllegalArgumentException;
    Customer getCustomerById(long id);
    void updateCustomer(Customer customer) throws IllegalArgumentException;
    void deleteCustomer(long id) throws IllegalArgumentException;
    Iterable<Customer> getAllCustomers();
    void deleteAllCustomers();
}
