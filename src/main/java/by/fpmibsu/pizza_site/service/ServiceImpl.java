package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.dao.Transaction;

public abstract class ServiceImpl {
    protected Transaction transaction;
    public ServiceImpl(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
