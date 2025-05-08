package controllers;

import com.sun.jdi.InternalException;
import models.Customer;
import services.CustomerService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CustomerController {

    private CustomerService customerService;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public CustomerController() {
        this.customerService = new CustomerService();
    }


    public void executeString(String input) {

        String[] args = input.split(" ");

        if(args.length == 0) {
            System.out.println("Invalid command");
            return;
        }

        if(args[0].equals("create")) {

            if(args.length < 4) {
                System.out.println("Please specify: Username, Email and Date of birth");
                return;
            }

            try {
                Customer newCustomer = customerService.createCustomer(args[1], args[2], LocalDate.parse(args[3], dateFormatter));
                System.out.println(newCustomer);
            } catch (DateTimeParseException e) {
                System.err.println("The date is wrongly formatted. The right format is dd.MM.yyyy");
            } catch (IllegalArgumentException | InternalException e) {
                System.err.println(e.getMessage());
            }

        }
        else if(args[0].equals("get")) {

            if(args.length >= 2) {
                try {
                    long id = Long.parseLong(args[1]);
                    Customer customer = customerService.getCustomerById(id);
                    System.out.println(customer);
                }
                catch(NumberFormatException nfe) {
                    System.err.println("Please give a valid id.");
                } catch (IllegalArgumentException iae) {
                    System.err.println(iae.getMessage());
                }
            }
            else {
                System.err.println("Please give a valid id.");
            }

        }
        else if(args[0].equals("update")) {

            if(args.length < 4) {
                System.out.println("Please specify: Customer Id, key (username/email/birthdate, value");
            }

            Customer customer = null;

            try {
                customer = customerService.getCustomerById(Long.parseLong(args[1]));
            } catch (NumberFormatException nfe) {
                System.err.println("Please give a valid id.");
                return;
            } catch (IllegalArgumentException iae) {
                System.err.println(iae.getMessage());
                return;
            }

            if(customer == null) {
                System.err.println("An unknown error occurred.");
                return;
            }
            try {
                switch (args[2]) {
                    case "username":
                        customer.setUsername(args[3]);
                        customerService.updateCustomer(customer);
                        break;
                    case "email":
                        customer.setEmail(args[3]);
                        customerService.updateCustomer(customer);
                        break;
                    case "birthdate":
                        customer.setDateofbirth(LocalDate.parse(args[3], dateFormatter));
                        customerService.updateCustomer(customer);
                        break;
                    default:
                        System.err.println("Invalid key. Please give username, email or birthdate");
                        return;
                }
            } catch (DateTimeParseException dpe) {
                System.err.println("The date is wrongly formatted. The right format is dd.MM.yyyy");
            } catch (NumberFormatException nfe) {
                System.err.println("The number you gave is invalid");
            } catch (IllegalArgumentException iae) {
                System.err.println(iae.getMessage());
            }

            System.out.println("User updated.");

        }
        else if(args[0].equals("delete")) {

            if(args.length >= 2) {
                try {
                    long id = Long.parseLong(args[1]);
                    customerService.deleteCustomer(id);
                    System.out.println("Customer deleted.");
                }
                catch(NumberFormatException nfe) {
                    System.err.println("Please give a valid id.");
                } catch (IllegalArgumentException iae) {
                    System.err.println(iae.getMessage());
                }
            }
            else {
                System.err.println("Please give a valid id.");
            }

        }

        else if(args[0].equals("listAll")) {

            customerService.getAllCustomers().forEach(System.out::println);

        }
        else if(args[0].equals("deleteAll")) {

            customerService.deleteAllCustomers();
            System.out.println("Deleted all the users.");

        }
        else {
            System.err.println("Invalid command");
        }




    }

}
