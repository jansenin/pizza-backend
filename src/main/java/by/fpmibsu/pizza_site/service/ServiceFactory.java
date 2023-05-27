package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.dao.Transaction;
import by.fpmibsu.pizza_site.dao.TransactionFactory;
import by.fpmibsu.pizza_site.exception.TransactionException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceFactory implements ServiceFactoryInterface {
    private static final Map<Class<? extends ServiceInterface>, Class<? extends Service>> services = new ConcurrentHashMap<>();
    static {
        services.put(UserServiceInterface.class, UserService.class);
        services.put(PizzaServiceInterface.class, PizzaService.class);
        services.put(IngredientServiceInterface.class, IngredientService.class);
        services.put(OrderServiceInterface.class, OrderService.class);
    }

    private final TransactionFactory factory;

    public ServiceFactory(TransactionFactory factory) {
        this.factory = factory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <Type extends Service> Type getService(Class<Type> key) {
        Class<? extends Service> value = services.get(key);
        if (value != null) {
            try {
                ClassLoader classLoader = value.getClassLoader();
                Class<?>[] interfaces = {key};
                Transaction transaction = factory.createTransaction();
                Service service = value.newInstance();
                service.setTransaction(transaction);
                InvocationHandler handler = new ServiceInvocationHandler(service);
                return (Type) Proxy.newProxyInstance(classLoader, interfaces, handler);
            } catch (InstantiationException | IllegalAccessException e) {
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
