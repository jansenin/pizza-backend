package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.dao.Transaction;

public class UserService extends Service implements UserServiceInterface {
    public UserService(Transaction transaction) {
        super(transaction);
    }
}
