package by.fpmibsu.pizza_site.exception;

public class TransactionException extends Exception {
    public TransactionException() {}

    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(Throwable cause) {
        super(cause);
    }
}
