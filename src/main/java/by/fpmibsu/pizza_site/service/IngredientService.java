package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.dao.Transaction;

public class IngredientService extends Service implements IngredientServiceInterface{
    public IngredientService(Transaction transaction) {
        super(transaction);
    }
}
