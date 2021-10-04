package Model;

@FunctionalInterface
public interface Validation {
    boolean checkPassword(String x, String y);
}
