package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.User;

public interface UserDaoInterface extends DaoInterface<User> {
    User findUserByLogin(String login);
    boolean checkUserPassword(User user, String password);
    boolean deleteByLogin(String login);
}
