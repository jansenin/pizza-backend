package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.exception.TransactionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionImpl implements Transaction {
    private static final Map<Class<? extends Dao<?>>, Class<? extends BaseDaoImpl>> classes = new ConcurrentHashMap<>();
    static {
        classes.put(UserDao.class, UserDaoImpl.class);
        classes.put(IngredientDao.class, IngredientDaoImpl.class);
        classes.put(PizzaDao.class, PizzaDaoImpl.class);
        classes.put(OrderDao.class, OrderDaoImpl.class);
    }
    private static final Logger logger = LogManager.getLogger(IngredientDaoImpl.class);
    private final Connection connection;

    public TransactionImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <Type extends Dao<?>> Type createDao(Class<Type> key) throws TransactionException {
        Class<? extends BaseDaoImpl> value = classes.get(key);
        if(value != null) {
            try {
                Constructor<? extends BaseDaoImpl> constructor = value.getConstructor(Connection.class);
                BaseDaoImpl dao = constructor.newInstance(connection);
                dao.setConnection(connection);
                return (Type)dao;
            } catch(InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                logger.error("It is impossible to create data access object", e);
                throw new TransactionException(e);
            }
        }
        return null;
    }

    @Override
    public void commit() throws TransactionException {
        try {
            connection.commit();
        } catch(SQLException e) {
            logger.error("It is impossible to commit transaction", e);
            throw new TransactionException(e);
        }
    }

    @Override
    public void rollback() throws TransactionException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.error("It is impossible to rollback transaction", e);
            throw new TransactionException(e);
        }
    }
}
