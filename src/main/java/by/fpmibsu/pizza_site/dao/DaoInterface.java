package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.entity.Entity;

import java.util.List;

public interface DaoInterface <Type extends Entity> {
     List<Type> findAll();
     Type findById(int id);
     Type update(Type entity);
     boolean deleteById(int id);
     boolean insert(Type entity);
}
