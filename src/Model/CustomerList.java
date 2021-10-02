package Model;

import Control.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;

public class CustomerList {
    private ObservableList<Customer> customers = FXCollections.observableArrayList();

    public ObservableList<Customer> getCustomers() {
        return customers;
    }

    public int getCustomerId(String desiredName) {
        int desiredId = 0;

        for (Customer customer : customers) {
            if (customer.getName().equals(desiredName)) {
                desiredId = customer.getId();
                System.out.println(String.format("Customer found! ID is %d.", desiredId));
                break;
            }
        }

        return desiredId;
    }

    public ObservableList<String> getCustomerNames() {
        ObservableList<String> customerNames = FXCollections.observableArrayList();

        for (Customer customer : customers) {
            customerNames.add(customer.getName());
        }

        return customerNames;
    }

    public int getIndex(int desiredId) {
        int desiredIndex = 0;
        int loopIndex = 0;
        for (Customer customer : customers) {
            int id = customer.getId();
            if (id == desiredId) {
                desiredIndex = loopIndex;
                break;
            }
            else {
                loopIndex += 1;
            }
        }

        return desiredIndex;
    }

    public int getCurrentId() {
        int highest_id = 0;
        for (Customer customer : customers) {
            int loop_id = customer.getId();
            if (loop_id > highest_id) {
                highest_id = loop_id;
            }
        }

        int current_id = highest_id + 1;
        return current_id;
    }

    public void removeCustomer(Customer selectedCustomer) {
        int desiredIndex = 0;
        int loopIndex = 0;

        for (Customer customer : customers) {
            if (customer.getId() == selectedCustomer.getId()) {
                desiredIndex = loopIndex;
            }

            else {
                loopIndex += 1;
            }
        }

        customers.remove(desiredIndex);
    }

    public void updateCustomer(Customer updatedCustomer, String currentTime) {
        int updateId = updatedCustomer.getId();

        for (Customer customer : customers) {
            if (customer.getId() == updateId) {

                customer.setName(updatedCustomer.getName());

                customer.setAddress(updatedCustomer.getAddress());

                customer.setPostal(updatedCustomer.getPostal());

                customer.setPhone(updatedCustomer.getPhone());

                customer.setUpdater(Main.authenticator.getCurrentUser());

                customer.setUpdated(currentTime);

                customer.setDiv_id(updatedCustomer.getDiv_id());

                customer.setCountry(updatedCustomer.getCountry());

                customer.setCountry_id(updatedCustomer.getCountry_id());

                customer.setDivision(updatedCustomer.getDivision());

                break;
            }
        }
    }

    public Customer getCustomer(int desiredId) {
        Customer desiredCustomer = null;

        for (Customer customer : customers) {
            if (customer.getId() == desiredId) {
                desiredCustomer = customer;
            }
        }

        return desiredCustomer;
    }

    public boolean customerExists(int id) {
        boolean customerExists = false;

        for (Customer customer : customers) {
            if (customer.getId() == id) {
                customerExists = true;
                break;
            }
        }

        return  customerExists;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
}
