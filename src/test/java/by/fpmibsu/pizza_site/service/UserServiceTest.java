package by.fpmibsu.pizza_site.service;

import by.fpmibsu.pizza_site.dao.TransactionFactory;
import by.fpmibsu.pizza_site.entity.User;
import by.fpmibsu.pizza_site.entity.UserRole;
import by.fpmibsu.pizza_site.exception.DaoException;
import by.fpmibsu.pizza_site.exception.TransactionException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.HashSet;
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

    @AfterAll
    static void closeFactory() throws TransactionException {
        serviceFactory.close();
    }

    @Test
    void findAllCountTest() throws TransactionException, DaoException {
        UserService service = serviceFactory.getService(UserService.class);
        List<User> users = service.findAll();
        assertEquals(3, users.size());

    }

    @Test
    void findAllValueTest() throws TransactionException, DaoException {
        UserService service = serviceFactory.getService(UserService.class);
        List<User> users = service.findAll();
        List<User> checkUsers = new ArrayList<>();
        checkUsers.add(new User (UserRole.ADMIN, "dzen", "secret"));
        checkUsers.get(0).setId(1);
        checkUsers.add(new User (UserRole.STAFF, "parfen", "scrscr"));
        checkUsers.get(1).setId(2);
        checkUsers.add(new User (UserRole.CLIENT, "fpm_student", "fpm"));
        checkUsers.get(2).setId(72);
        assertEquals(new HashSet<>(checkUsers), new HashSet<>(users));
    }

    @ParameterizedTest
    @CsvSource(value = {"ADMIN, dzen, 1, secret",
                        "CLIENT, fpm_student, 72, fpm"})
    void findUserByLoginNotNullTest(UserRole role, String login, Integer id, String password) throws TransactionException, DaoException {
        UserService service = serviceFactory.getService(UserService.class);
        User user = service.findUserByLogin(login);
        User checkUser = new User(role, login, password);
        checkUser.setId(id);
        assertEquals(checkUser, user);
    }

    @ParameterizedTest
    @ValueSource(strings = {"dfadad", "dfsfdgs", "fjiadq"})
    void findUserByLoginNullTest(String login) throws TransactionException, DaoException {
        UserService service = serviceFactory.getService(UserService.class);
        assertNull(service.findUserByLogin(login));
    }
    @ParameterizedTest
    @CsvSource(value = {"ADMIN, dzen, 1, secret",
            "CLIENT, fpm_student, 72, fpm"})
    void findUserByIdNotNullTest(UserRole role, String login, Integer id, String password) throws TransactionException, DaoException {
        UserService service = serviceFactory.getService(UserService.class);
        User user = service.findById(id);
        User checkUser = new User(role, login, password);
        checkUser.setId(id);
        assertEquals(checkUser, user);
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 9, -23, 7})
    void findUserByIdnNullTest(Integer id) throws TransactionException, DaoException {
        UserService service = serviceFactory.getService(UserService.class);
        assertNull(service.findById(id));
    }

    @ParameterizedTest
    @CsvSource(value = {"ADMIN, STAFF, dzen, BIGBOSS, 1, secret, no_secret",
            "CLIENT, STAFF, fpm_student, good_fpm_student, 72, fpm, fpm the best"})
    void allowedUpdateTest(UserRole oldRole, UserRole newRole, String oldLogin, String newLogin, Integer id,
                           String oldPassword, String newPassword) throws TransactionException, DaoException {
        UserService service = serviceFactory.getService(UserService.class);
        User user = service.findById(id);
        user.setPassword(newPassword);
        user.setRole(newRole);
        user.setLogin(newLogin);
        service.update(user);
        assertEquals(id, user.getId());
        assertEquals(service.findById(id), user);
        User checkUser = new User(newRole, newLogin, newPassword);
        checkUser.setId(id);
        assertEquals(checkUser, user);
        user.setPassword(oldPassword);
        user.setRole(oldRole);
        user.setLogin(oldLogin);
        service.update(user);
        assertEquals(id, user.getId());
        assertEquals(service.findById(id), user);
        checkUser = new User(oldRole, oldLogin, oldPassword);
        checkUser.setId(id);
        assertEquals(checkUser, user);
    }

    @ParameterizedTest
    @CsvSource(value = {"CLIENT, no_such_user, 4, secret",
                        "CLIENT, parfen, 1, fpm"})
    void notAllowedUpdateTest(UserRole role, String login, Integer id, String password) throws TransactionException, DaoException {
        UserService service = serviceFactory.getService(UserService.class);
        User user = new User(role, login, password);
        user.setId(id);
        service.update(user);
        assertNull(user.getId());
    }

    @ParameterizedTest
    @CsvSource(value = {"STAFF, new_worker, qwerty",
                        "ADMIN, new_admin, admin"})
    void allowedInsertDeleteTest(UserRole role, String login, String password) throws TransactionException, DaoException {
        UserService service = serviceFactory.getService(UserService.class);
        User insertUser = new User(role, login ,password);
        service.insert(insertUser);
        assertNotNull(insertUser.getId());
        assertEquals(service.findById(insertUser.getId()), insertUser);
        service.deleteById(insertUser.getId());
        assertNull(service.findById(insertUser.getId()));
    }

    @ParameterizedTest
    @CsvSource(value = {"ADMIN, fpm_student, qwerty",
                        "ADMIN, dzen, admin"})
    void notAllowedInsertTest(UserRole role, String login, String password) throws TransactionException, DaoException {
        UserService service = serviceFactory.getService(UserService.class);
        User insertUser = new User(role, login ,password);
        service.insert(insertUser);
        assertNull(insertUser.getId());
    }
}