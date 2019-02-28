package com.jack.persistence;

import com.jack.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestMethodOrder(OrderAnnotation.class)
class UserDaoTest {

    UserDao userDao;
    static int currentSize;
    static int createdId;

    @BeforeEach
    void setUp() {
        userDao = new UserDao();
    }

    @Test
    @Order(1)
    void getById() {
        User user = userDao.getById(1);
        assertEquals("billyjohnson", user.getUserName());
    }

    @Test
    @Order(2)
    void saveOrUpdate() {
        User beforeUser = userDao.getById(1);
        beforeUser.setEmail("jeffjohnson@gmail.com");
        userDao.saveOrUpdate(beforeUser);
        User afterUser = userDao.getById(1);
        assertEquals("jeffjohnson@gmail.com", afterUser.getEmail());
    }

    @Test
    @Order(3)
    void insert() {
        User setUser = new User();
        setUser.setUserName("jiffshutske");
        setUser.setEmail("jiffshutske@gmail.com");
        setUser.setFirstName("jiff");
        setUser.setLastName("ekstuhs");
        setUser.setPassword("abc12345");
        createdId = userDao.insert(setUser);
        User afterUser = userDao.getById(createdId);
        assertEquals("jiffshutske@gmail.com", afterUser.getEmail());
        List<User> users = userDao.getAll();
        currentSize = users.size();
    }

    @Test
    @Order(4)
    void delete() {
        User user = userDao.getById(createdId);
        userDao.delete(user);
        assertNull(userDao.getById(createdId));
    }

    @Test
    @Order(5)
    void getAll() {
        List<User> users = userDao.getAll();
        assertEquals(currentSize - 1, users.size());
    }
}