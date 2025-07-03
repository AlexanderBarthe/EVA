package com.tickets.spring_controller;

import com.tickets.TicketShop;
import models.Customer;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class SpringCustomerController {

    private TicketShop ticketShop;

    public SpringCustomerController() {
        this.ticketShop = new TicketShop();
    }

    @PostMapping()
    public Customer createCustomer(@RequestBody Customer customer) {
        return ticketShop
                .getCustomerService()
                .createCustomer(customer.getUsername(),
                        customer.getEmail(),
                        customer.getDateofbirth());
    }

    @GetMapping("/id")
    public Customer getCustomer(@PathVariable Long id) {
        return ticketShop.getCustomerService().getCustomerById(id);
    }

    @PutMapping
    public void updateCustomer(@RequestBody Customer customer) {
        ticketShop.getCustomerService().updateCustomer(customer);
    }

    @DeleteMapping("/id")
    public void deleteCustomer(@PathVariable Long id) {
        ticketShop.getCustomerService().deleteCustomer(id);
    }

    @GetMapping("/all")
    public List<Customer> getAllCustomers() {
        return ticketShop.getCustomerService().getAllCustomers();
    }

    @DeleteMapping("/all")
    public void deleteAllCustomers() {
        ticketShop.getCustomerService().deleteAllCustomers();
    }


}
