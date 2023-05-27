package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.dao.Transaction;

public class OrderService extends Service implements OrderServiceInterface {
    public OrderService(Transaction transaction) {
        super(transaction);
    }
}
