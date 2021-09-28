package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ContactList {
    ObservableList<Contact> contacts = FXCollections.observableArrayList();

    public void addContact(Contact contact) { contacts.add(contact); }

    public Contact getContact(String name) {
        Contact foundContact = null;
        for (Contact contact : contacts) {
            if (name.equals(contact.getName())) {
                foundContact = contact;
            }
        }

        return foundContact;
    }

    public Contact getContact(int id) {
        Contact foundContact = null;
        for (Contact contact : contacts) {
            if (id == contact.getId()) {
                foundContact = contact;
            }
        }

        return foundContact;
    }

    public int getIndex(Contact selectedContact) {
        int loopIndex = 0;
        int desiredIndex = 0;

        for (Contact contact : contacts) {
            if (contact.getId() == selectedContact.getId()) {
                desiredIndex = loopIndex;
                break;
            }
            loopIndex += 1;
        }

        return desiredIndex;
    }

    public ObservableList<String> getContacts() {
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        for (Contact contact : contacts) {
            String name = contact.getName();
            contactNames.add(name);
        }

        return contactNames;
    }
}
