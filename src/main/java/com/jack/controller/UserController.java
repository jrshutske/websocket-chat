package com.jack.controller;
import com.jack.entity.CharacterModel;
import com.jack.entity.User;
import com.jack.persistence.GenericDao;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;

/**
 * The type User controller.
 */
@Controller
@SpringBootApplication
public class UserController {

    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;

    /**
     * Instantiates a new User controller.
     *
     * @param inMemoryUserDetailsManager the in memory user details manager
     */
    @Autowired
    public UserController(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
    }

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * All users path string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/user")
    String allUsersPath(Model model) {
        GenericDao dao = new GenericDao(User.class);
        List<User> users = dao.getAll();
        model.addAttribute("users", users);
        return "user";
    }

    /**
     * Gets user by id path.
     *
     * @param id    the id
     * @param model the model
     * @return the user by id path
     */
    @GetMapping("/user/{id}")
    String getUserByIdPath(@PathVariable int id, Model model) {
        GenericDao dao = new GenericDao(User.class);
        User user = (User)dao.getById(id);
        logger.info("Getting characters: " + user.getCharacters());
        CharacterModelController characterModelController = new CharacterModelController();
        List<CharacterModel> characterModels = characterModelController.getCharacterModels(user.getCharacters());
        model.addAttribute("user", user);
        model.addAttribute("characterModels", characterModels);
        return "usershow";
    }

    /**
     * Show user by username path string.
     *
     * @param username the username
     * @param model    the model
     * @return the string
     */
    @GetMapping("/user/name/{username}")
    String showUserByUsernamePath(@PathVariable String username, Model model) {
        GenericDao dao = new GenericDao(User.class);
        User user = dao.getByUsername(username);
        logger.info("Getting characters: " + user.getCharacters());
        model.addAttribute("user", user);
        model.addAttribute("characterModels", getUsersCharacterModels(user));
        return "usershow";
    }

    /**
     * Gets user by username path.
     *
     * @param username the username
     * @param model    the model
     * @return the user by username path
     */
    @GetMapping("/search")
    String getUserByUsernamePath(@RequestParam("username") String username, Model model) {
        GenericDao dao = new GenericDao(User.class);
        User user = (User)dao.getByUsername(username);
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("characterModels", getUsersCharacterModels(user));
            return "usershow";
        } else {
            return "redirect:/?notice=searchfailure";
        }
    }

    /**
     * Api user id path response entity.
     *
     * @param id    the id
     * @param model the model
     * @return the response entity
     */
    @GetMapping(value = "/api/user/{id}")
    public ResponseEntity<?> apiUserIdPath(@PathVariable int id, Model model) {
        GenericDao dao = new GenericDao(User.class);
        User user = (User)dao.getById(id);
        List<CharacterModel> characterModels = getUsersCharacterModels(user);
        if (characterModels.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(characterModels, HttpStatus.OK);
        }
    }


    /**
     * Edit user path string.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
    @GetMapping("/user/{id}/edit")
    String editUserPath(@PathVariable int id, Model model) {
        GenericDao dao = new GenericDao(User.class);
        User user = (User) dao.getById(id);
        model.addAttribute("user", user);
        return "useredit";
    }

    /**
     * Update user path string.
     *
     * @param id        the id
     * @param contact   the contact
     * @param firstname the firstname
     * @param lastname  the lastname
     * @param password  the password
     * @return the string
     */
    @PostMapping(value = "/user/{id}/update")
    String updateUserPath(@PathVariable int id,
                      @RequestParam("contact") String contact,
                      @RequestParam("firstname") String firstname,
                      @RequestParam("lastname") String lastname,
                      @RequestParam("password") String password) {
        GenericDao dao = new GenericDao(User.class);
        User user = (User) dao.getById(id);
        UserDetails userDetails =
                org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
                        .username(user.getUsername())
                        .password(password)
                        .roles("USER")
                        .build();
        inMemoryUserDetailsManager.updateUser(userDetails);
        user.setContact(contact);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setPassword(password);
        dao.saveOrUpdate(user);
        return "redirect:/user/{id}";
    }

    /**
     * New user path string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/user/new")
    String newUserPath(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "usernew";
    }

    /**
     * Create user path string.
     *
     * @param username  the username
     * @param firstname the firstname
     * @param lastname  the lastname
     * @param contact   the contact
     * @param password  the password
     * @return the string
     */
    @PostMapping(value = "/user/create")
    String createUserPath(@RequestParam("username") String username,
                      @RequestParam("firstname") String firstname,
                      @RequestParam("lastname") String lastname,
                      @RequestParam("contact") String contact,
                      @RequestParam("password") String password) {
        GenericDao userDao = new GenericDao(User.class);
        User user = new User();
        user.setUsername(username);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setContact(contact);
        user.setPassword(password);
        UserDetails userDetails =
                org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
                        .username(username)
                        .password(password)
                        .roles("USER")
                        .build();
        if (username.length() == 0) {
            return "redirect:/user/new?notice=failure";
        }
        if (!inMemoryUserDetailsManager.userExists(username)) {
            int id = userDao.insert(user);
            inMemoryUserDetailsManager.createUser(userDetails);
            logger.info("the user that is created: " + username);
            return "redirect:/login/?notice=success";
        } else {
            return "redirect:/user/new?notice=failure";
        }
    }

    /**
     * Gets users character models.
     *
     * @param user the user
     * @return the users character models
     */
    public List<CharacterModel> getUsersCharacterModels(User user) {
        CharacterModelController characterModelController = new CharacterModelController();
        List<CharacterModel> characterModels = characterModelController.getCharacterModels(user.getCharacters());
        return characterModels;
    }
}