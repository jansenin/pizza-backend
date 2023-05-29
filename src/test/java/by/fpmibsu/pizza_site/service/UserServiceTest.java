package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.dao.TransactionFactory;
import by.fpmibsu.pizza_site.entity.User;
import by.fpmibsu.pizza_site.entity.UserRole;
import by.fpmibsu.pizza_site.exception.DaoException;
import by.fpmibsu.pizza_site.exception.TransactionException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    static private final ServiceFactoryImpl serviceFactory;
    static {
        try {
            serviceFactory = new ServiceFactoryImpl(new TransactionFactory());
        } catch (TransactionException e) {
            throw new RuntimeException(e);
        }
    }

    @org.junit.jupiter.api.AfterAll
    static void closeFactory() throws TransactionException {
        serviceFactory.close();
    }

    @Test
    void findAll() throws TransactionException, DaoException {
        UserService service = serviceFactory.getService(UserService.class);
        List<User> users = service.findAll();
        assertEquals(3, users.size());
        assertEquals(List.of(new User(UserRole.ADMIN, "dzen", 1, "secret"),
                             new User(UserRole.STAFF, "parfen", 2, "scrscr"),
                             new User(UserRole.CLIENT, "fpm_student", 72, "fpm")), users);
    }

    @Test
    void findUserByLogin() throws TransactionException, DaoException {
        UserService service = serviceFactory.getService(UserService.class);
        User user = service.findUserByLogin("dzen");
        assertEquals(new User(UserRole.ADMIN, "dzen", 1, "secret"), user);
        user = service.findUserByLogin("fpm_student");
        assertEquals(new User(UserRole.CLIENT, "fpm_student", 72, "fpm"), user);
        user = service.findUserByLogin("mmf_student");
        assertNull(user);
        user = service.findUserByLogin("anon");
        assertNull(user);
    }
    @Test
    void findById() throws TransactionException, DaoException {
        UserService service = serviceFactory.getService(UserService.class);
        User user = service.findById(1);
        assertEquals(new User(UserRole.ADMIN, "dzen", 1, "secret"), user);
        user = service.findById(72);
        assertEquals(new User(UserRole.CLIENT, "fpm_student", 72, "fpm"), user);
        user = service.findById(12);
        assertNull(user);
        user = service.findById(17);
        assertNull(user);
    }

    @Test
    void update() throws TransactionException, DaoException {
        UserService service = serviceFactory.getService(UserService.class);
        User user = service.findById(72);
        user.setPassword("fpm the best");
        user.setRole(UserRole.STAFF);
        user.setLogin("good_fpm_student");
        service.update(user);
        assertEquals(72, user.getId());
        assertEquals(service.findById(72), user);
        assertEquals(new User(UserRole.STAFF, "good_fpm_student", 72, "fpm the best"), user);
        user.setPassword("fpm");
        user.setRole(UserRole.CLIENT);
        user.setLogin("fpm_student");
        service.update(user);
        assertEquals(72, user.getId());
        assertEquals(service.findById(72), user);
        assertEquals(new User(UserRole.CLIENT, "fpm_student", 72, "fpm"), user);

        user = new User(UserRole.CLIENT, "no such user", 4, "abacaba");
        service.update(user);
        assertNull(user.getId());

        user = new User(UserRole.CLIENT, "parfen", 7, "abacaba");
        service.update(user);
        assertNull(user.getId());
    }

    @Test
    void insertDelete() throws TransactionException, DaoException {
        UserService service = serviceFactory.getService(UserService.class);
        User insertUser = new User(UserRole.STAFF, "new_worker", null, "qwerty");
        service.insert(insertUser);
        assertNotEquals(null, insertUser.getId());
        assertEquals(service.findById(insertUser.getId()), insertUser);
        service.deleteById(insertUser.getId());
        assertNull(service.findById(insertUser.getId()));

        insertUser = new User(UserRole.ADMIN, "new_admin", null, "admin");
        service.insert(insertUser);
        assertNotEquals(null, insertUser.getId());
        assertEquals(service.findById(insertUser.getId()), insertUser);
        service.deleteById(insertUser.getId());
        assertNull(service.findById(insertUser.getId()));

        insertUser = new User(UserRole.ADMIN, "fpm_student", null, "");
        service.insert(insertUser);
        assertNull(insertUser.getId());

        insertUser = new User(UserRole.ADMIN, "dzen", null, "sdf");
        service.insert(insertUser);
        assertNull(insertUser.getId());
    }
}