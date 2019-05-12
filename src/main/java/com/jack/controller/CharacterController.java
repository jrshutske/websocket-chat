package com.jack.controller;

import com.jack.entity.Character;
import com.jack.entity.CharacterModel;
import com.jack.entity.User;
import com.jack.persistence.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;
import java.util.List;

/**
 * The type Character controller.
 */
@Controller
@SpringBootApplication
public class CharacterController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Gets all characters path.
     *
     * @param model the model
     * @return the all characters path
     */
    @GetMapping("/character")
    String getAllCharactersPath(Model model) {
        model.addAttribute("characters", getAllCharacters());
        return "character";
    }

    /**
     * Gets character by id path.
     *
     * @param id    the id
     * @param model the model
     * @return the character by id path
     * @throws IOException the io exception
     */
    @GetMapping("/character/{id}")
    String getCharacterByIdPath(@PathVariable int id, Model model) throws IOException {
        Character character = getCharacterById(id);
        model.addAttribute("characterModel", getCharacterModel(character));
        model.addAttribute("character", character);
        return "charactershow";
    }

    /**
     * Delete character path string.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     * @throws IOException the io exception
     */
//Consuming a service by GET method
    @GetMapping("/character/{id}/delete")
    String deleteCharacterPath(@PathVariable int id, Model model) throws IOException {
        GenericDao characterDao = new GenericDao(Character.class);
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Character character = getCharacterById(id);
        User creator = character.getCreator();
        int creatorId = creator.getId();
        logger.info("currentUSER: " + currentUserName);
        logger.info("attempting to delete character of: " + creator.getUsername());
        if (creator.getUsername().equals(currentUserName)) {
            characterDao.delete(character);
            return "redirect:/user/"+creatorId+"/?notice=success";
        }
        return "redirect:/user/"+creatorId+"/?notice=failure";
    }

    /**
     * Create character path string.
     *
     * @param charactername the charactername
     * @param creatorname   the creatorname
     * @param realmname     the realmname
     * @param model         the model
     * @return the string
     */
    @PostMapping(value = "/character/create")
    String createCharacterPath(@RequestParam("charactername") String charactername,
                           @RequestParam("creatorname") String creatorname,
                           @RequestParam("realmname") String realmname,
                           Model model) {
        try {
            GenericDao characterDao = new GenericDao(Character.class);
            GenericDao userDao = new GenericDao(User.class);
            Character character = new Character();
            List<User> users = userDao.getAll();
            User user1 = null;
            for (User user : users) {
                if (creatorname.equalsIgnoreCase(user.getUsername())) {
                    user1 = user;
                    logger.info("This is the creator: " + user);
                }
            }
            character.setCreator(user1);
            character.setCharactername(charactername);
            character.setRealmname(realmname);
            try{
                CharacterModelController characterModelController = new CharacterModelController();
                CharacterModel characterModel = characterModelController.getCharacterModel(character);
                character.setCharactername(characterModel.getName());
                character.setRealmname(characterModel.getRealm());
            } catch(Exception e){
                logger.error("error creating character: " + e);
                return "redirect:/character/new?notice=404";
            }
            int id = characterDao.insert(character);
            return "redirect:/character/" + id;
        } catch(Exception e){
            logger.error("error creating character: " + e);
            return "redirect:/character/new?notice=exists";
        }
    }

    /**
     * New character path string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/character/new")
    String newCharacterPath(Model model) {
        Character character = new Character();
        model.addAttribute("character", character);
        return "characternew";
    }

    /**
     * Gets all characters.
     *
     * @return the all characters
     */
    public List<Character> getAllCharacters() {
        GenericDao characterDao = new GenericDao(Character.class);
        List<Character> characters = characterDao.getAll();
        return characters;
    }

    /**
     * Gets character by id.
     *
     * @param id the id
     * @return the character by id
     */
    public Character getCharacterById(int id) {
        GenericDao characterDao = new GenericDao(Character.class);
        Character character = (Character)characterDao.getById(id);
        return character;
    }

    /**
     * Gets character model.
     *
     * @param character the character
     * @return the character model
     */
    public CharacterModel getCharacterModel(Character character) {
        CharacterModelController characterModelController = new CharacterModelController();
        CharacterModel characterModel = characterModelController.getCharacterModel(character);
        return characterModel;
    }
}
