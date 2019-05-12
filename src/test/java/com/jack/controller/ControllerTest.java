package com.jack.controller;

import com.jack.entity.CharacterModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import com.jack.entity.Character;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The type Character controller test.
 */
public class ControllerTest {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Gets access token test.
     */
    @Test
    void getAccessTokenTest() {
        CharacterModelController characterModelController = new CharacterModelController();
        logger.info("access token: " + characterModelController.getAccessToken());
        assertNotNull(characterModelController.getAccessToken());
    }

    /**
     * Gets race faction name test.
     */
    @Test
    void getRaceFactionNameTest() {
        CharacterModelController characterModelController = new CharacterModelController();
        logger.info("raceFactionObject: " + characterModelController.getRaceFactionName(1));
        JSONObject raceFactionObject = characterModelController.getRaceFactionName(1);
        String faction = raceFactionObject.getString("side");
        String race = raceFactionObject.getString("name");

        assertEquals("Human", race);
        assertEquals("alliance", faction);
    }

    /**
     * Gets class name test.
     */
    @Test
    void getClassNameTest() {
        CharacterModelController characterModelController = new CharacterModelController();
        logger.info("ClassName 1: " + characterModelController.getClassName(1));
        assertEquals("Rogue", characterModelController.getClassName(4));
    }

    /**
     * Gets character model.
     */
    @Test
    void getCharacterModel() {
        Character character = new Character();
        character.setCharactername("Stimulii");
        character.setRealmname("Tichondrius");
        CharacterModelController characterModelController = new CharacterModelController();
        logger.info("ClassName 1: " + characterModelController.getCharacterModel(character));
        CharacterModel characterModel = characterModelController.getCharacterModel(character);
        assertEquals("Stimulii", characterModel.getName());
        assertEquals("Tichondrius", characterModel.getRealm());
        assertEquals("Alliance", characterModel.getFaction());
        assertEquals("Night Elf", characterModel.getRacename());
        assertEquals("Rogue", characterModel.getClassname());
        assertEquals(120, characterModel.getLevel());

    }

    /**
     * Gets character models.
     */
    @Test
    void getCharacterModels() {
        Set<Character> characters = new HashSet<>();
        CharacterModelController characterModelController = new CharacterModelController();
        Character character1 = new Character();
        character1.setCharactername("Stimulii");
        character1.setRealmname("Tichondrius");
        Character character2 = new Character();
        character2.setCharactername("Stimulii");
        character2.setRealmname("Tichondrius");
        characters.add(character1);
        characters.add(character2);
        logger.info("grabbing two models: " + characterModelController.getCharacterModels(characters));
        assertEquals(2, characterModelController.getCharacterModels(characters).size());
    }
}
