package services;

import com.sun.jdi.InternalException;
import interfaces.CustomerServiceInterface;
import logging.CreateCustomerEvent;
import logging.LogService;
import models.Customer;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CustomerService implements CustomerServiceInterface {

    private Map<Long, Customer> customersById = new ConcurrentHashMap<>();
    private final IDService idService;

    private LogService logService;

    public  CustomerService(LogService logService) {
        idService = new IDService();
        this.logService = new LogService();
    }

    public Customer createCustomer(String username, String email, LocalDate dateofbirth) throws IllegalArgumentException, InternalException{
        long generatedId = idService.generateNewId();

        if(generatedId == -1) {
            throw new InternalException("An error occured while creating a new id");
        }

        if(LocalDate.now().minusYears(18).isBefore(dateofbirth.plusDays(1))) {
            throw new IllegalArgumentException("User has to be 18 years old");
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        boolean validMail = email.matches(emailRegex);
        if(!validMail){
            throw new IllegalArgumentException("Invalid email");
        }

        Customer customer = new Customer(generatedId, username, email, dateofbirth);

        if(customersById.containsKey(customer.getId())) {
            throw new IllegalArgumentException("Customer with id " + customer.getId() + " already exists");
        }

        saveCustomer(customer);

        logService.log(new CreateCustomerEvent(customer));

        return customer;
    }

    public Customer getCustomerById (long id) {
        if(!customersById.containsKey(id)) {
            return null;
        }
        return new Customer(customersById.get(id));
    }
    public void updateCustomer (Customer customer) throws IllegalArgumentException{

        if(!customersById.containsKey(customer.getId())) {
            throw new IllegalArgumentException("Customer with id " + customer.getId() + " does not exist.");
        }

        saveCustomer(customer);
    }

    public void deleteCustomer(long id) throws IllegalArgumentException {

        if(!customersById.containsKey(id)) {
            throw new IllegalArgumentException("Customer with id " + id + " does not exist.");
        }

        idService.removeId(id);
        customersById.remove(id);
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customersById.values());
    }

    public void deleteAllCustomers() {
        customersById.clear();
        idService.dropAllIds();
    }

    private void saveCustomer(Customer customer) {

        if(LocalDate.now().minusYears(18).isBefore(customer.getDateofbirth().plusDays(1))) {
            throw new IllegalArgumentException("User has to be 18 years old");
        }

        if (!customer.getEmail().contains("@") || customer.getEmail().indexOf("@") != customer.getEmail().lastIndexOf("@") || !(customer.getEmail().lastIndexOf(".") > customer.getEmail().indexOf("@"))) {
            throw new IllegalArgumentException("Invalid email");
        }

        customersById.put(customer.getId(), customer);
    }
}
