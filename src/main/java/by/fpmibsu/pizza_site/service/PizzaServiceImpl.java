package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.dao.IngredientDao;
import by.fpmibsu.pizza_site.dao.PizzaDao;
import by.fpmibsu.pizza_site.dao.TransactionImpl;
import by.fpmibsu.pizza_site.entity.Ingredient;
import by.fpmibsu.pizza_site.entity.Pizza;
import by.fpmibsu.pizza_site.exception.DaoException;
import by.fpmibsu.pizza_site.exception.TransactionException;

import java.util.List;

public class PizzaServiceImpl extends ServiceImpl implements PizzaService {
    public PizzaServiceImpl(TransactionImpl transaction) {
        super(transaction);
    }


    private void LoadData(Pizza pizza) throws TransactionException, DaoException {
        IngredientDao ingredientDao = transaction.createDao(IngredientDao.class);
        for (Ingredient ingredient : pizza.getIngredients()) {
            ingredient.setName(ingredientDao.findById(ingredient.getId()).getName());
        }
    }
    @Override
    public List<Pizza> findAll() throws DaoException, TransactionException {
        PizzaDao pizzaDao = transaction.createDao(PizzaDao.class);
        List<Pizza> pizzas = pizzaDao.findAll();
        for (Pizza pizza : pizzas) {
            LoadData(pizza);
        }
        return pizzas;
    }

    @Override
    public Pizza findById(Integer id) throws DaoException, TransactionException {
        PizzaDao pizzaDao = transaction.createDao(PizzaDao.class);
        Pizza pizza = pizzaDao.findById(id);
        LoadData(pizza);
        return pizza;
    }

    @Override
    public Pizza update(Pizza pizza) throws DaoException, TransactionException {
        PizzaDao dao = transaction.createDao(PizzaDao.class);
        return dao.update(pizza);
    }

    @Override
    public void deleteById(Integer id) throws DaoException, TransactionException {
        PizzaDao dao = transaction.createDao(PizzaDao.class);
        dao.deleteById(id);
    }

    @Override
    public void insert(Pizza pizza) throws DaoException, TransactionException {
        PizzaDao dao = transaction.createDao(PizzaDao.class);
        dao.insert(pizza);
    }

    @Override
    public void addIngredientInPizza(Pizza pizza, Integer ingredientId) throws DaoException, TransactionException {
        PizzaDao pizzaDao = transaction.createDao(PizzaDao.class);
        IngredientDao ingredientDao = transaction.createDao(IngredientDao.class);
        pizzaDao.addIngredientInPizza(pizza, ingredientId);
        pizza.getIngredients().get(pizza.getIngredients().size() - 1).setName(ingredientDao.findById(ingredientId).getName());
    }

    @Override
    public void removeIngredientFromPizza(Pizza pizza, Integer ingredientId) throws DaoException, TransactionException {
        PizzaDao dao = transaction.createDao(PizzaDao.class);
        dao.removeIngredientFromPizza(pizza, ingredientId);
    }
}
