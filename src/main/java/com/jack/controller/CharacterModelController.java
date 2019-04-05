package com.jack.controller;

import com.jack.entity.Character;
import com.jack.entity.CharacterModel;
import com.jack.entity.User;
import com.jack.persistence.GenericDao;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.*;

@Controller
@SpringBootApplication
public class CharacterModelController {

    @Autowired
    RestTemplate restTemplate;

    @Value("https://us.battle.net/oauth/token?client_id=f0315fe57d76491695b77140f61ffda3&client_secret=MFVeGxsAhQyTIxWt0SJMhxaE7c87ioSv&grant_type=client_credentials")
    String serviceURL;

    private final Logger logger = LogManager.getLogger(this.getClass());


    @GetMapping("/user/{id}")
    String getuserbyid(@PathVariable int id, Model model) {
        GenericDao dao = new GenericDao(User.class);
        User user = (User)dao.getById(id);
        logger.info("Getting characters: " + user.getCharacters());

        List<CharacterModel> characterModels = getCharacterModels(user.getCharacters());
        model.addAttribute("user", user);
        model.addAttribute("characterModels", characterModels);
        return "usershow";
    }

    //Consuming a service by GET method
    @GetMapping("/character/{id}")
    String getcharacterbyid(@PathVariable int id, Model model) throws IOException {
        GenericDao characterDao = new GenericDao(Character.class);
        Character character = (Character)characterDao.getById(id);
        CharacterModel characterModel = getCharacterModel(character);
        model.addAttribute("characterModel", characterModel);
        model.addAttribute("character", character);
        return "charactershow";
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
                getCharacterModel(character);
            } catch(Exception e){
                logger.error("error creating character: " + e);
                return "redirect:/character/new?error=404";
            }
            int id = characterDao.insert(character);
            return "redirect:/character/" + id;
        } catch(Exception e){
            logger.error("error creating character: " + e);
            return "redirect:/character/new?error=exists";
        }
    }


    public String getAccessToken() {
        String tokenJson = restTemplate.getForObject(serviceURL, String.class);
        JSONObject tokenobj = new JSONObject(tokenJson);
        String access_token = tokenobj.getString("access_token");
        logger.info("this is the access token:" + tokenobj.getString("access_token"));
        return access_token;
    }

    public String getClassName(Integer id) {
        String classURL = "https://us.api.blizzard.com/wow/data/character/classes?locale=en_US&access_token=" + getAccessToken();
        String classJson = restTemplate.getForObject(classURL, String.class);
        JSONObject classobj = new JSONObject(classJson);
        JSONArray jsonArray = classobj.getJSONArray("classes");
        String classname = "";
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject classjsonobj = jsonArray.getJSONObject(i);
            if (id == classjsonobj.getInt("id")) {
                classname = classjsonobj.getString("name");
            }
        }
        logger.info("this is stims class:" + classname);
        return classname;
    }

    public JSONObject getRaceFactionName(Integer id) {
        String raceURL = "https://us.api.blizzard.com/wow/data/character/races?locale=en_US&access_token=" + getAccessToken();
        String raceJson = restTemplate.getForObject(raceURL, String.class);
        JSONObject racefactionobj = new JSONObject(raceJson);
        JSONArray jsonArray = racefactionobj.getJSONArray("races");
        JSONObject newracefactionobj = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonobj = jsonArray.getJSONObject(i);
            if (id == jsonobj.getInt("id")) {
                newracefactionobj = jsonobj;
            }
        }
        logger.info("returning this race and faction object:" + newracefactionobj);
        return newracefactionobj;
    }

    public List<CharacterModel> getCharacterModels(Set<Character> characters){
        List<CharacterModel> characterModels = new ArrayList<CharacterModel>();
        for(Character character : characters) {
            characterModels.add(getCharacterModel(character));
        }
        return characterModels;
    }

    public CharacterModel getCharacterModel(Character character){
        String characterURL = "https://us.api.blizzard.com/wow/character/" + character.getRealmname() + "/" + character.getCharactername() + "?access_token=" + getAccessToken();
        String characterJson = restTemplate.getForObject(characterURL, String.class);
        JSONObject characterobj = new JSONObject(characterJson);
        CharacterModel characterModel = new CharacterModel();
        characterModel.setName(characterobj.getString("name"));
        characterModel.setRealm(characterobj.getString("realm"));
        characterModel.setBattlegroup(characterobj.getString("battlegroup"));
        characterModel.setClassnumber(characterobj.getInt("class"));
        characterModel.setLevel(characterobj.getInt("level"));
        characterModel.setAchievementpoints(characterobj.getInt("achievementPoints"));
        characterModel.setTotalhonorablekills(characterobj.getInt("totalHonorableKills"));
        characterModel.setId(character.getId());
        characterModel.setClassname(getClassName(characterobj.getInt("class")));
        String thumnail = characterobj.getString("thumbnail");
        String fixedthumbnail = thumnail.replaceAll("avatar","main");
        fixedthumbnail = "http://render-us.worldofwarcraft.com/character/" + fixedthumbnail;
        characterModel.setThumbnail(fixedthumbnail);
        JSONObject rfobject = getRaceFactionName(characterobj.getInt("race"));
        characterModel.setRacename(rfobject.getString("name"));
        String str = rfobject.getString("side");
        characterModel.setFaction(str.substring(0, 1).toUpperCase() + str.substring(1));
        logger.info("this is character from the set:" + characterModel);
        return characterModel;
    }



}