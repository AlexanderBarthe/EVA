package services;

import com.sun.jdi.InternalException;
import models.Customer;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

public class CustomerService {

    private HashMap<Long, Customer> customersById;
    private IDService idService;

    public  CustomerService() {
        this.customersById = new HashMap<>();
        idService = new IDService();
    }

    public Customer createCustomer(String username, String email, LocalDate dateofbirth) throws IllegalArgumentException{
        long generatedId = idService.generateNewId();

        if(generatedId == -1) {
            throw new InternalException("An error occured while creating a new id");
        }

        if(LocalDate.now().minusYears(18).isBefore(dateofbirth.plusDays(1))) {
            throw new IllegalArgumentException("User has to be 18 years old");
        }

        if(!email.contains("@") || email.indexOf("@") != email.lastIndexOf("@") || !(email.lastIndexOf(".") > email.indexOf("@"))){
            throw new IllegalArgumentException("Invalid email");
        }

        Customer customer = new Customer(generatedId, username, email, dateofbirth);

        if(customersById.containsKey(customer.getId())) {
            throw new IllegalArgumentException("Customer with id " + customer.getId() + " already exists");
        }

        saveCustomer(customer);
        return customer;
    }

    public Customer getCustomerById (long id) {
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

    public Collection<Customer> getAllCustomers() {
        return customersById.values();
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
