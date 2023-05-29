package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.dao.IngredientDaoInterface;
import by.fpmibsu.pizza_site.dao.Transaction;
import by.fpmibsu.pizza_site.entity.Ingredient;
import by.fpmibsu.pizza_site.exception.DaoException;
import by.fpmibsu.pizza_site.exception.TransactionException;

import java.util.List;

public class IngredientService extends Service implements IngredientServiceInterface {
    public IngredientService(Transaction transaction) {
        super(transaction);
    }

    @Override
    public List<Ingredient> findAll() throws DaoException, TransactionException {
        IngredientDaoInterface dao = transaction.createDao(IngredientDaoInterface.class);
        return dao.findAll();
    }

    @Override
    public Ingredient findById(Integer id) throws DaoException, TransactionException {
        IngredientDaoInterface dao = transaction.createDao(IngredientDaoInterface.class);
        return dao.findById(id);
    }

    @Override
    public Ingredient update(Ingredient ingredient) throws DaoException, TransactionException {
        IngredientDaoInterface dao = transaction.createDao(IngredientDaoInterface.class);
        return dao.update(ingredient);
    }

    @Override
    public void deleteById(Integer id) throws DaoException, TransactionException {
        IngredientDaoInterface dao = transaction.createDao(IngredientDaoInterface.class);
        dao.deleteById(id);
    }

    @Override
    public void insert(Ingredient ingredient) throws DaoException, TransactionException {
        IngredientDaoInterface dao = transaction.createDao(IngredientDaoInterface.class);
        dao.insert(ingredient);
    }

    @Override
    public void deleteByIngredient(Ingredient ingredient) throws DaoException, TransactionException {
        IngredientDaoInterface dao = transaction.createDao(IngredientDaoInterface.class);
        dao.deleteByIngredient(ingredient);
    }
}
