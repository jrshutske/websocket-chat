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

@Controller
@SpringBootApplication
public class CharacterController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @GetMapping("/character")
    String getAllCharactersPath(Model model) {
        model.addAttribute("characters", getAllCharacters());
        return "character";
    }

    @GetMapping("/character/{id}")
    String getCharacterByIdPath(@PathVariable int id, Model model) throws IOException {
        Character character = getCharacterById(id);
        model.addAttribute("characterModel", getCharacterModel(character));
        model.addAttribute("character", character);
        return "charactershow";
    }

    //Consuming a service by GET method
    @GetMapping("/character/{id}/delete")
    String deletecharacterbyid(@PathVariable int id, Model model) throws IOException {
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

    @PostMapping(value = "/character/create")
    String createcharacter(@RequestParam("charactername") String charactername,
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

    @GetMapping("/character/new")
    String newcharacter(Model model) {
        Character character = new Character();
        model.addAttribute("character", character);
        return "characternew";
    }

    public List<Character> getAllCharacters() {
        GenericDao characterDao = new GenericDao(Character.class);
        List<Character> characters = characterDao.getAll();
        return characters;
    }

    public Character getCharacterById(int id) {
        GenericDao characterDao = new GenericDao(Character.class);
        Character character = (Character)characterDao.getById(id);
        return character;
    }

    public CharacterModel getCharacterModel(Character character) {
        CharacterModelController characterModelController = new CharacterModelController();
        CharacterModel characterModel = characterModelController.getCharacterModel(character);
        return characterModel;
    }
}
