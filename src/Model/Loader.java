package Model;

/**
 * this interface sets up the lambda to load the database data.
 */
@FunctionalInterface
public interface Loader {
    void load();
}
