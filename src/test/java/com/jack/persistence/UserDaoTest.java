package com.jack.persistence;

import com.jack.entity.User;
import com.jack.entity.Room;
import com.jack.utility.CleanDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserDaoTest {

    UserDao userDao;
    RoomDao roomDao;
    User user;
    int userId;

    @BeforeEach
    void setUp() {

        userDao = new UserDao();
        User setUser = new User();
        setUser.setUsername("jackshutske");
        setUser.setEmail("jackshutske@gmail.com");
        setUser.setFirstName("jack");
        setUser.setLastName("shutske");
        setUser.setPassword("abc12345");
        userId = userDao.insert(setUser);
        user = userDao.getById(userId);
    }

    @Test
    void getById() {
        User user = userDao.getById(userId);
        assertEquals("jackshutske", user.getUsername());
    }

    @Test
    void saveOrUpdate() {
        User beforeUser = userDao.getById(userId);
        beforeUser.setEmail("johnshutske@gmail.com");
        userDao.saveOrUpdate(beforeUser);
        User afterUser = userDao.getById(userId);
        assertEquals("johnshutske@gmail.com", afterUser.getEmail());
    }

    @Test
    void insert() {
        userId = userDao.insert(user);
        user = userDao.getById(userId);
        assertEquals("jackshutske@gmail.com", user.getEmail());
    }

    @Test
    void delete() {
        User user = userDao.getById(userId);
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