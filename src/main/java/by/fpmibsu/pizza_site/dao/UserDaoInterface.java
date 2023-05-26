package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.User;

import java.util.List;

public interface UserDaoInterface {
    List<User> findAll();
    User findUserById(int id);
    User findUserByLogin(String login);
    User updateUser(User user, String password);
    boolean checkUserPassword(User user, String password);
    boolean deleteById(int id);
    boolean deleteByLogin(String login);
    boolean insert(User user, String password);
}
