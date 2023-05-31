package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.Entity;
import by.fpmibsu.pizza_site.exception.DaoException;

import java.util.List;

public interface Dao<Type extends Entity> {
     List<Type> findAll() throws DaoException;
     Type findById(Integer id) throws DaoException;
     Type update(Type entity) throws DaoException;
     void deleteById(Integer id) throws DaoException;
     void insert(Type entity) throws DaoException;
}
