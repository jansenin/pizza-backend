package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.User;
import by.fpmibsu.pizza_site.exception.DaoException;

public interface UserDaoInterface extends DaoInterface<User> {
    User findUserByLogin(String login) throws DaoException;
    boolean checkUserPassword(User user, String password) throws DaoException;
    void deleteByLogin(String login) throws DaoException;
}
