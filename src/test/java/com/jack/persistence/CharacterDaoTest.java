package com.jack.persistence;

import com.jack.entity.User;
import com.jack.entity.Character;
import com.jack.utility.CleanDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * The type Character dao test.
 */
class CharacterDaoTest {

    /**
     * The Character dao.
     */
    GenericDao characterDao;
    /**
     * The User dao.
     */
    GenericDao userDao;
    /**
     * The Character.
     */
    Character character;
    /**
     * The User.
     */
    User user;
    /**
     * The Character id.
     */
    int characterId;
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

        characterDao = new GenericDao(Character.class);
        Character setCharacter = new Character();
        setCharacter.setCharactername("initialCharacter");
        setCharacter.setCreator(user);
        characterId = characterDao.insert(setCharacter);
        character = (Character)characterDao.getById(characterId);
    }

    /**
     * Gets by id.
     */
    @Test
    void getById() {
        Character character = (Character)characterDao.getById(characterId);
        assertEquals("initialCharacter", character.getCharactername());
    }

    /**
     * Save or update.
     */
    @Test
    void saveOrUpdate() {
        Character character = (Character)characterDao.getById(characterId);
        character.setCharactername("newCharacter");
        characterDao.saveOrUpdate(character);
        Character afterCharacter = (Character)characterDao.getById(characterId);
        assertEquals("newCharacter", character.getCharactername());
    }

    /**
     * Insert.
     */
    @Test
    void insert() {
        characterId = characterDao.insert(character);
        character = (Character)characterDao.getById(characterId);
        assertEquals("initialCharacter", character.getCharactername());
    }

    /**
     * Delete.
     */
    @Test
    void delete() {
        Character character = (Character)characterDao.getById(characterId);
        characterDao.delete(character);
        assertNull(characterDao.getById(characterId));
    }

    /**
     * Gets all.
     */
    @Test
    void getAll() {
        List<Character> characters = characterDao.getAll();
        assertEquals(false, characters.isEmpty());
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
