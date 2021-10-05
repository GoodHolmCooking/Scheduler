package Model;

import Control.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CustomerList {
    private ObservableList<Customer> customers = FXCollections.observableArrayList();

    /**
     *
     * @return the list of customers.
     */
    public ObservableList<Customer> getCustomers() {
        return customers;
    }

    /**
     * Finds the customer id based on the customer's name.
     * @param desiredName the customer's name whose id needs to be found.
     * @return the customer's id.
     */
    public int getCustomerId(String desiredName) {
        int desiredId = 0;

        for (Customer customer : customers) {
            if (customer.getName().equals(desiredName)) {
                desiredId = customer.getId();
                break;
            }
        }

        return desiredId;
    }

    /**
     *
     * @return a list of the customer names as strings.
     */
    public ObservableList<String> getCustomerNames() {
        ObservableList<String> customerNames = FXCollections.observableArrayList();

        for (Customer customer : customers) {
            String name = customer.getName();
            int id = customer.getId();
            String nameString = String.format("%d: %s", id, name);
            customerNames.add(nameString);
        }

        return customerNames;
    }

    /**
     * find the index of a customer based on their id.
     * @param desiredId the id of the customer whose index needs to be found.
     * @return the index of the customer.
     */
    public int getIndex(int desiredId) {
        int desiredIndex = 0;
        int loopIndex = 0;

        for (Customer customer : customers) {
            if (customer.getId() == desiredId) {
                desiredIndex = loopIndex;
                break;
            }
            else {
                loopIndex += 1;
            }
        }

        return  desiredIndex;
    }

    /**
     * generates an id for new customers.
     * @return the newly generated id.
     */
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

    /**
     * remove a customer from the list.
     * @param selectedCustomer the customer to remove.
     */
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

    /**
     * Updates an existing customer with new information.
     * @param updatedCustomer the updated version of the customer.
     * @param currentTime the current time.
     */
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

    /**
     * retrieves a customer based on the customer's id.
     * @param desiredId the id of the customer to fetch.
     * @return the customer to return.
     */
    public Customer getCustomer(int desiredId) {
        Customer desiredCustomer = null;

        for (Customer customer : customers) {
            if (customer.getId() == desiredId) {
                desiredCustomer = customer;
            }
        }

        return desiredCustomer;
    }

    /**
     * Checks if a customer of that id already exists.
     * @param id the id to check.
     * @return a boolean stating if the customer already exists or not.
     */
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

    /**
     * adds a new customer to the list.
     * @param customer the customer to add.
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
}
