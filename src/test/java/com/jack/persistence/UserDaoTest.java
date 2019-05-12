package com.jack.persistence;

import com.jack.entity.User;
import com.jack.entity.Character;
import com.jack.utility.CleanDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import com.jack.controller.CharacterModelController;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * The type User dao test.
 */
class UserDaoTest {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * The User dao.
     */
    GenericDao userDao;
    /**
     * The Character dao.
     */
    GenericDao characterDao;
    /**
     * The User.
     */
    User user;
    /**
     * The User id.
     */
    int userId;

    /**
     * Sets up.
     */
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

    /**
     * Gets by id.
     */
    @Test
    void getById() {
        User user = (User)userDao.getById(userId);
        assertEquals("jackshutske", user.getUsername());
    }

    /**
     * Save or update.
     */
    @Test
    void saveOrUpdate() {
        User beforeUser = (User)userDao.getById(userId);
        beforeUser.setContact("johnshutske@gmail.com");
        userDao.saveOrUpdate(beforeUser);
        User afterUser = (User)userDao.getById(userId);
        assertEquals("johnshutske@gmail.com", afterUser.getContact());
    }

    /**
     * Insert.
     */
    @Test
    void insert() {
        userId = userDao.insert(user);
        user = (User)userDao.getById(userId);
        assertEquals("jackshutske@gmail.com", user.getContact());
    }

    /**
     * Delete.
     */
    @Test
    void delete() {
        User user = (User)userDao.getById(userId);
        userDao.delete(user);
        assertNull(userDao.getById(userId));
    }

    /**
     * Gets all.
     */
    @Test
    void getAll() {
        List<User> users = userDao.getAll();
        assertEquals(false, users.isEmpty());
    }

    /**
     * After all.
     */
    @AfterAll
    public static void AfterAll() {
        CleanDatabase cd = new CleanDatabase();
        cd.runCleaner();
    }
}