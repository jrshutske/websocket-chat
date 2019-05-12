package com.jack.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Entity test.
 */
class EntityTest {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Create user.
     */
    @Test
    void createUser() {
        Set<Character> characters = new HashSet();

        User user = new User();
        user.setId(1);
        user.setPassword("password");
        user.setContact("jrshutske@madisoncollege.edu");
        user.setFirstname("Jack");
        user.setLastname("Shutske");
        user.setUsername("jackshutske");

        Character character = new Character();
        character.setRealmname("Tichondrius");
        character.setCharactername("Stimulii");
        character.setCreator(user);
        character.setId(1);
        characters.add(character);

        user.setCharacters(characters);

        assertEquals(1, user.getId());
        assertEquals("password", user.getPassword());
        assertEquals("jrshutske@madisoncollege.edu", user.getContact());
        assertEquals("Jack", user.getFirstname());
        assertEquals("Shutske", user.getLastname());
        assertEquals("jackshutske", user.getUsername());
        assertEquals(characters, user.getCharacters());
    }

    /**
     * Create character.
     */
    @Test
    void createCharacter() {
        User user = new User();
        user.setId(1);
        user.setPassword("password");
        user.setContact("jrshutske@madisoncollege.edu");
        user.setFirstname("Jack");
        user.setLastname("Shutske");
        user.setUsername("jackshutske");

        Character character = new Character();
        character.setRealmname("Tichondrius");
        character.setCharactername("Stimulii");
        character.setCreator(user);
        character.setId(1);

        assertEquals("Tichondrius", character.getRealmname());
        assertEquals("Stimulii", character.getCharactername());
        assertEquals(user, character.getCreator());
        assertEquals(1, character.getId());

    }

    /**
     * Create character model.
     */
    @Test
    void createCharacterModel() {
        CharacterModel characterModel = new CharacterModel();
        characterModel.setFaction("horde");
        characterModel.setAchievementpoints(1000);
        characterModel.setBattlegroup("battlegroup");
        characterModel.setClassname("Warrior");
        characterModel.setClassnumber(1);
        characterModel.setId(1);
        characterModel.setLevel(60);
        characterModel.setRacename("Human");
        characterModel.setRacenumber(1);
        characterModel.setRealm("Antoidas");
        characterModel.setTotalhonorablekills(9999);

        assertEquals("horde", characterModel.getFaction());
        assertEquals(1000, characterModel.getAchievementpoints());
        assertEquals("battlegroup", characterModel.getBattlegroup());
        assertEquals("Warrior", characterModel.getClassname());
        assertEquals(1, characterModel.getClassnumber());
        assertEquals(1, characterModel.getId());
        assertEquals(60, characterModel.getLevel());
        assertEquals("Human", characterModel.getRacename());
        assertEquals(1, characterModel.getRacenumber());
        assertEquals("Antoidas", characterModel.getRealm());
        assertEquals(9999, characterModel.getTotalhonorablekills());



    }

}