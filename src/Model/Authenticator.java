package Model;

import java.util.HashMap;

public class Authenticator {
    private final HashMap<String, String> passwords = new HashMap<String, String>();
    private final HashMap<String, Integer> ids = new HashMap<String, Integer>();
    private String currentUser = null;

    public boolean authenticate(String suppliedUser, String suppliedPassword) {
        boolean passwordCorrect = false;
        if (passwords.containsKey(suppliedUser)) {
            String correctPassword = passwords.get(suppliedUser);
            if (suppliedPassword.equals(correctPassword)) {
                passwordCorrect = true;
                this.currentUser = suppliedUser;
            }
        }

        return passwordCorrect;
    }

    public void addUser(String user, String password) {
        passwords.put(user, password);
    }
    public void addId(String user, int id) { ids.put(user, id); }

    public String getCurrentUser() {
        return currentUser;
    }
    public int getId(String user) { return ids.get(user); }
}
