package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.dao.TransactionImpl;
import by.fpmibsu.pizza_site.dao.TransactionFactory;
import by.fpmibsu.pizza_site.exception.TransactionException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceFactoryImpl implements ServiceFactory {
    private static final Map<Class<? extends Service>, Class<? extends ServiceImpl>> services = new ConcurrentHashMap<>();
    static {
        services.put(UserService.class, UserServiceImpl.class);
        services.put(PizzaService.class, PizzaServiceImpl.class);
        services.put(IngredientService.class, IngredientServiceImpl.class);
        services.put(OrderService.class, OrderServiceImpl.class);
    }

    private final TransactionFactory factory;

    public ServiceFactoryImpl(TransactionFactory factory) {
        this.factory = factory;
    }

    @Override
    public <Type extends Service> Type getService(Class<Type> key) {
        Class<? extends ServiceImpl> value = services.get(key);
        if (value != null) {
            try {
                ClassLoader classLoader = value.getClassLoader();
                Class<?>[] interfaces = {key};
                TransactionImpl transaction = factory.createTransaction();
                Constructor<? extends ServiceImpl> constructor = value.getDeclaredConstructor(TransactionImpl.class);
                ServiceImpl service = constructor.newInstance(transaction);
                InvocationHandler handler = new ServiceInvocationHandler(service);
                return (Type)Proxy.newProxyInstance(classLoader, interfaces, handler);
            } catch (NoSuchMethodException  | InstantiationException | IllegalAccessException | InvocationTargetException
                    e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    public void close() throws TransactionException {
        factory.close();
    }
}
