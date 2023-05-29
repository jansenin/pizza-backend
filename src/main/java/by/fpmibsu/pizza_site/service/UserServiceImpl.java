package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.dao.TransactionImpl;
import by.fpmibsu.pizza_site.dao.UserDao;
import by.fpmibsu.pizza_site.entity.User;
import by.fpmibsu.pizza_site.exception.DaoException;
import by.fpmibsu.pizza_site.exception.TransactionException;

import java.util.List;

public class UserServiceImpl extends ServiceImpl implements UserService {
    public UserServiceImpl(TransactionImpl transaction) {
        super(transaction);
    }

    @Override
    public List<User> findAll() throws DaoException, TransactionException {
        UserDao dao = transaction.createDao(UserDao.class);
        return dao.findAll();
    }

    @Override
    public User findById(Integer id) throws DaoException, TransactionException {
        UserDao dao = transaction.createDao(UserDao.class);
        return dao.findById(id);
    }

    @Override
    public User update(User user) throws DaoException, TransactionException {
        UserDao dao = transaction.createDao(UserDao.class);
        return dao.update(user);
    }

    @Override
    public void deleteById(Integer id) throws DaoException, TransactionException {
        UserDao dao = transaction.createDao(UserDao.class);
        dao.deleteById(id);
    }

    @Override
    public void insert(User user) throws DaoException, TransactionException {
        UserDao dao = transaction.createDao(UserDao.class);
        dao.insert(user);
    }

    @Override
    public User findUserByLogin(String login) throws DaoException, TransactionException {
        UserDao dao = transaction.createDao(UserDao.class);
        return dao.findUserByLogin(login);
    }
}
