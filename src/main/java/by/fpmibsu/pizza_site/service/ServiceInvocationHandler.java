package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.dao.IngredientDaoImpl;
import by.fpmibsu.pizza_site.exception.TransactionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ServiceInvocationHandler implements java.lang.reflect.InvocationHandler {
    private final ServiceImpl service;
    private static final Logger logger = LogManager.getLogger(IngredientDaoImpl.class);
    public ServiceInvocationHandler(ServiceImpl service) {
        this.service = service;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
        try {
            Object result = method.invoke(service, arguments);
            service.transaction.commit();
            return result;
        } catch (TransactionException e) {
            rollback(method);
            throw e;
        } catch(InvocationTargetException e) {
            rollback(method);
            throw e.getCause();
        }
    }

    private void rollback(Method method) {
        try {
            service.transaction.rollback();
        } catch(TransactionException e) {
            logger.warn("It is impossible to rollback transaction", e);
        }
    }
}
