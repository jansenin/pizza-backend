package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.dao.Transaction;

public abstract class Service {
    protected Transaction transaction;
    public Service(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
