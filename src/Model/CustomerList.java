package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CustomerList {
    private ObservableList<Customer> customers = FXCollections.observableArrayList();

    public ObservableList<Customer> getCustomers() {
        return customers;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
}
