package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.dao.IngredientDao;
import by.fpmibsu.pizza_site.dao.TransactionImpl;
import by.fpmibsu.pizza_site.entity.Ingredient;
import by.fpmibsu.pizza_site.exception.DaoException;
import by.fpmibsu.pizza_site.exception.TransactionException;

import java.util.List;

public class IngredientServiceImpl extends ServiceImpl implements IngredientService {
    public IngredientServiceImpl(TransactionImpl transaction) {
        super(transaction);
    }

    @Override
    public List<Ingredient> findAll() throws DaoException, TransactionException {
        IngredientDao dao = transaction.createDao(IngredientDao.class);
        return dao.findAll();
    }

    @Override
    public Ingredient findById(Integer id) throws DaoException, TransactionException {
        IngredientDao dao = transaction.createDao(IngredientDao.class);
        return dao.findById(id);
    }

    @Override
    public Ingredient update(Ingredient ingredient) throws DaoException, TransactionException {
        IngredientDao dao = transaction.createDao(IngredientDao.class);
        return dao.update(ingredient);
    }

    @Override
    public void deleteById(Integer id) throws DaoException, TransactionException {
        IngredientDao dao = transaction.createDao(IngredientDao.class);
        dao.deleteById(id);
    }

    @Override
    public void insert(Ingredient ingredient) throws DaoException, TransactionException {
        IngredientDao dao = transaction.createDao(IngredientDao.class);
        dao.insert(ingredient);
    }

    @Override
    public void deleteByIngredient(Ingredient ingredient) throws DaoException, TransactionException {
        IngredientDao dao = transaction.createDao(IngredientDao.class);
        dao.deleteByIngredient(ingredient);
    }
}
