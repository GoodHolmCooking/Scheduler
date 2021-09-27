package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ContactList {
    ObservableList<Contact> contacts = FXCollections.observableArrayList();

    public void addContact(Contact contact) { contacts.add(contact); }
    public ObservableList<String> getContacts() {
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        for (Contact contact : contacts) {
            String name = contact.getName();
            contactNames.add(name);
        }

        return contactNames;
    }
}
