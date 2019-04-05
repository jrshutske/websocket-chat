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


    @GetMapping("/character/new")
    String newcharacter(Model model) {
        Character character = new Character();
        model.addAttribute("character", character);
        return "characternew";
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
            int id = characterDao.insert(character);
            return "redirect:/character/" + id;
        } catch(Exception e){
            logger.error("error creating character: " + e);
            return "redirect:/character/new?error=exists";
        }
    }

    @Autowired
    RestTemplate restTemplate;


    @Value("https://us.battle.net/oauth/token?client_id=f0315fe57d76491695b77140f61ffda3&client_secret=MFVeGxsAhQyTIxWt0SJMhxaE7c87ioSv&grant_type=client_credentials")
    String serviceURL;


    //Consuming a service by GET method
    @GetMapping("/character/{id}")
    String getcharacterbyid(@PathVariable int id, Model model) throws IOException {
        GenericDao characterDao = new GenericDao(Character.class);
        Character character = (Character)characterDao.getById(id);
        String charactername = character.getCharactername();
        String realmname = character.getRealmname();


        String tokenJson = restTemplate.getForObject(serviceURL, String.class);
        JSONObject tokenobj = new JSONObject(tokenJson);
        String access_token = tokenobj.getString("access_token");

        logger.info("this is the access token:" + tokenobj.getString("access_token"));

        String characterURL = "https://us.api.blizzard.com/wow/character/"+realmname+"/"+charactername+"?access_token=" + access_token;
        String characterJson = restTemplate.getForObject(characterURL, String.class);
        JSONObject characterobj = new JSONObject(characterJson);
        CharacterModel characterModel = new CharacterModel();
        characterModel.setName(characterobj.getString("name"));
        characterModel.setRealm(characterobj.getString("realm"));
        characterModel.setBattlegroup(characterobj.getString("battlegroup"));
        characterModel.setClassnumber(characterobj.getInt("class"));
        characterModel.setRace(characterobj.getInt("race"));
        characterModel.setLevel(characterobj.getInt("level"));
        characterModel.setAchievementpoints(characterobj.getInt("achievementPoints"));
        characterModel.setThumbnail(characterobj.getString("thumbnail"));
        characterModel.setTotalhorablekills(characterobj.getInt("totalHonorableKills"));

        logger.info("this is the character stimulii:"+ characterJson);


        model.addAttribute("characterModel", characterModel);
        model.addAttribute("character", character);
        return "charactershow";
    }


    @Bean
    public RestTemplate rest() {
        return new RestTemplate();
    }
}
