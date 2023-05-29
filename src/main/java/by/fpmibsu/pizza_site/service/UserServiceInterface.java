package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.entity.User;
import by.fpmibsu.pizza_site.exception.DaoException;
import by.fpmibsu.pizza_site.exception.TransactionException;

import java.util.List;

public interface UserServiceInterface extends ServiceInterface {
    List<User> findAll() throws DaoException, TransactionException;
    User findById(Integer id) throws DaoException, TransactionException;
    User update(User user) throws DaoException, TransactionException;
    void deleteById(Integer id) throws DaoException, TransactionException;
    void insert(User user) throws DaoException, TransactionException;
    User findUserByLogin(String login) throws DaoException, TransactionException;
}
