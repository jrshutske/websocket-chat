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

class CharacterDaoTest {

    GenericDao characterDao;
    GenericDao userDao;
    Character character;
    User user;
    int characterId;
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

        characterDao = new GenericDao(Character.class);
        Character setCharacter = new Character();
        setCharacter.setCharactername("initialCharacter");
        setCharacter.setCreator(user);
        characterId = characterDao.insert(setCharacter);
        character = (Character)characterDao.getById(characterId);
    }

    @Test
    void getById() {
        Character character = (Character)characterDao.getById(characterId);
        assertEquals("initialCharacter", character.getCharactername());
    }

    @Test
    void saveOrUpdate() {
        Character character = (Character)characterDao.getById(characterId);
        character.setCharactername("newCharacter");
        characterDao.saveOrUpdate(character);
        Character afterCharacter = (Character)characterDao.getById(characterId);
        assertEquals("newCharacter", character.getCharactername());
    }

    @Test
    void insert() {
        characterId = characterDao.insert(character);
        character = (Character)characterDao.getById(characterId);
        assertEquals("initialCharacter", character.getCharactername());
    }

    @Test
    void delete() {
        Character character = (Character)characterDao.getById(characterId);
        characterDao.delete(character);
        assertNull(characterDao.getById(characterId));
    }

    @Test
    void getAll() {
        List<Character> characters = characterDao.getAll();
        assertEquals(false, characters.isEmpty());
    }

    @AfterAll
    public static void AfterAll() {
        CleanDatabase cd = new CleanDatabase();
        cd.runCleaner();
    }
}
