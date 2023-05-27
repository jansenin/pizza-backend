package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.dao.Transaction;

public class PizzaService extends Service implements PizzaServiceInterface {
    public PizzaService(Transaction transaction) {
        super(transaction);
    }
}
