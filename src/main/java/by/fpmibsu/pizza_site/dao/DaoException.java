package by.fpmibsu.pizza_site.dao;

public class DaoException extends Exception {
    public DaoException() {

    }
    public DaoException(String message) {
        super(message);
    }
    public DaoException(String massage, Throwable cause) {
        super(massage,cause);
    }
    public DaoException(Throwable cause) {
        super(cause);
    }
}
