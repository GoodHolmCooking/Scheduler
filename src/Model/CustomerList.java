package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CustomerList {
    private ObservableList<Customer> customers = FXCollections.observableArrayList();

    public ObservableList<Customer> getCustomers() {
        return customers;
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

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
}
