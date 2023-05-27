package by.fpmibsu.pizza_site.dao;

import by.fpmibsu.pizza_site.exception.TransactionException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Transaction implements TransactionInterface {
    private static final Map<Class<? extends DaoInterface<?>>, Class<? extends BaseDao>> classes = new ConcurrentHashMap<>();
    static {
        classes.put(UserDaoInterface.class, UserDao.class);
        classes.put(IngredientDaoInterface.class, IngredientDao.class);
        classes.put(PizzaDaoInterface.class, PizzaDao.class);
        classes.put(OrderDaoInterface.class, OrderDao.class);
    }
    private static final Logger logger = Logger.getLogger(IngredientDao.class);
    private final Connection connection;

    public Transaction(Connection connection) {
        this.connection = connection;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <Type extends DaoInterface<?>> Type createDao(Class<Type> key) throws TransactionException {
        Class<? extends BaseDao> value = classes.get(key);
        if(value != null) {
            try {
                BaseDao dao = value.newInstance();
                dao.setConnection(connection);
                return (Type)dao;
            } catch(InstantiationException | IllegalAccessException e) {
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