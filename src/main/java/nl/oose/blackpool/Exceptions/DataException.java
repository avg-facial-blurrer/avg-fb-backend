package nl.oose.blackpool.Exceptions;

public class DataException extends Exception {
    public DataException() {
        super("Something with wrong with getting the data you requested");
    }

    public DataException(String message) {
        super(message);
    }
}
