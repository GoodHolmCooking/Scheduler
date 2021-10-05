package Model;

/**
 * sets up the lambda for valdiating the password.
 */
@FunctionalInterface
public interface Validation {
    boolean checkPassword(String x, String y);
}
