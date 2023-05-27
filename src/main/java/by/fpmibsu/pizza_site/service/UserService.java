package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.dao.Transaction;
import by.fpmibsu.pizza_site.dao.UserDaoInterface;
import by.fpmibsu.pizza_site.entity.User;
import by.fpmibsu.pizza_site.exception.DaoException;
import by.fpmibsu.pizza_site.exception.TransactionException;

import java.util.List;

public class UserService extends Service implements UserServiceInterface {
    public UserService(Transaction transaction) {
        super(transaction);
    }

    @Override
    public List<User> findAll() throws DaoException, TransactionException {
        UserDaoInterface dao = transaction.createDao(UserDaoInterface.class);
        return dao.findAll();
    }

    @Override
    public User findById(int id) throws DaoException, TransactionException {
        UserDaoInterface dao = transaction.createDao(UserDaoInterface.class);
        return dao.findById(id);
    }

    @Override
    public User update(User user) throws DaoException, TransactionException {
        UserDaoInterface dao = transaction.createDao(UserDaoInterface.class);
        return dao.update(user);
    }

    @Override
    public boolean deleteById(int id) throws DaoException, TransactionException {
        UserDaoInterface dao = transaction.createDao(UserDaoInterface.class);
        return dao.deleteById(id);
    }

    @Override
    public boolean insert(User user) throws DaoException, TransactionException {
        UserDaoInterface dao = transaction.createDao(UserDaoInterface.class);
        return dao.insert(user);
    }

    @Override
    public User findUserByLogin(String login) throws DaoException, TransactionException {
        UserDaoInterface dao = transaction.createDao(UserDaoInterface.class);
        return dao.findUserByLogin(login);
    }
}
