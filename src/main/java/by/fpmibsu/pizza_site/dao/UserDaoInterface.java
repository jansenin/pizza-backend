package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.User;

import java.util.List;

public interface UserDaoInterface extends DaoInterface {
    List<User> findAll() throws DaoException;
    User findUserById(int id) throws DaoException;
    User findUserByLogin(String login) throws DaoException;

    boolean checkUserPassword(User user, String password) throws DaoException;
    boolean deleteByUser(User user) throws DaoException;
    boolean deleteById(int id) throws DaoException;
    boolean insert(User user) throws DaoException;
}
