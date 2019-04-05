package com.jack.controller;

import com.jack.entity.Character;
import com.jack.entity.CharacterModel;
import com.jack.entity.User;
import com.jack.persistence.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.List;

@Controller
@SpringBootApplication
public class CharacterController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @GetMapping("/character")
    String characters(Model model) {
        GenericDao characterDao = new GenericDao(Character.class);
        List<Character> characters = characterDao.getAll();
        model.addAttribute("characters", characters);
        return "character";
    }

//    @GetMapping("/character/{id}")
//    String getcharacterbyid(@PathVariable int id, Model model) throws IOException {
//        GenericDao characterDao = new GenericDao(Character.class);
//        Character character = (Character)characterDao.getById(id);
//        CharacterModelController cmc = new CharacterModelController();
//        CharacterModel characterModel = cmc.getCharacterModel(character);
//        model.addAttribute("characterModel", characterModel);
//        model.addAttribute("character", character);
//        return "charactershow";
//    }

    @GetMapping("/character/new")
    String newcharacter(Model model) {
        Character character = new Character();
        model.addAttribute("character", character);
        return "characternew";
    }
}
