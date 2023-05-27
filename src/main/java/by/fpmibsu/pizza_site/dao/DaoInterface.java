package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.Entity;
import by.fpmibsu.pizza_site.exception.DaoException;

import java.util.List;

public interface DaoInterface <Type extends Entity> {
     List<Type> findAll() throws DaoException;
     Type findById(int id) throws DaoException;
     Type update(Type entity) throws DaoException;
     boolean deleteById(int id) throws DaoException;
     boolean insert(Type entity) throws  DaoException;
}
