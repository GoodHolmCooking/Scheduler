package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ContactList {
    ObservableList<Contact> contacts = FXCollections.observableArrayList();

    /**
     * Adds a new contact to the list of contacts.
     * @param contact the contact to add.
     */
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

    /**
     *
     * @param id the id of the desired contact.
     * @return the contact that was found.
     */
    public Contact getContact(int id) {
        Contact foundContact = null;
        for (Contact contact : contacts) {
            if (id == contact.getId()) {
                foundContact = contact;
            }
        }

        return foundContact;
    }

    /**
     * Returns the index of a selected contact.
     * @param selectedContact the contact whose index needs to be found.
     * @return the index of the selcected contact.
     */
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

    /**
     *
     * @return a list of contact names as an ObservableList of strings.
     */
    public ObservableList<String> getContactNames() {
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        for (Contact contact : contacts) {
            String name = contact.getName();
            contactNames.add(name);
        }

        return contactNames;
    }

    /**
     *
     * @return the list of contacts
     */
    public ObservableList<Contact> getContacts() {
        return contacts;
    }
}
