package com.jack.persistence;

import com.jack.entity.User;
import com.jack.entity.Character;
import com.jack.utility.CleanDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserDaoTest {

    GenericDao userDao;
    GenericDao characterDao;
    User user;
    int userId;

    @BeforeEach
    void setUp() {

        userDao = new GenericDao(User.class);
        User setUser = new User();
        setUser.setUsername("jackshutske");
        setUser.setContact("jackshutske@gmail.com");
        setUser.setFirstname("jack");
        setUser.setLastname("shutske");
        setUser.setPassword("abc12345");
        userId = userDao.insert(setUser);
        user = (User)userDao.getById(userId);
    }

    @Test
    void getById() {
        User user = (User)userDao.getById(userId);
        assertEquals("jackshutske", user.getUsername());
    }

    @Test
    void saveOrUpdate() {
        User beforeUser = (User)userDao.getById(userId);
        beforeUser.setContact("johnshutske@gmail.com");
        userDao.saveOrUpdate(beforeUser);
        User afterUser = (User)userDao.getById(userId);
        assertEquals("johnshutske@gmail.com", afterUser.getContact());
    }

    @Test
    void insert() {
        userId = userDao.insert(user);
        user = (User)userDao.getById(userId);
        assertEquals("jackshutske@gmail.com", user.getContact());
    }

    @Test
    void delete() {
        User user = (User)userDao.getById(userId);
        userDao.delete(user);
        assertNull(userDao.getById(userId));
    }

    @Test
    void getAll() {
        List<User> users = userDao.getAll();
        assertEquals(false, users.isEmpty());
    }

    @AfterAll
    public static void AfterAll() {
        CleanDatabase cd = new CleanDatabase();
        cd.runCleaner();
    }
}