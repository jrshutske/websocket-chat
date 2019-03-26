package com.jack.controller;

import com.jack.entity.User;
import com.jack.persistence.GenericDao;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
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

@Controller
@SpringBootApplication
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
public class UserController {

    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @Autowired
    public UserController(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
    }
    private final Logger logger = LogManager.getLogger(this.getClass());


    @GetMapping("/user")
    String users(Model model) {
        GenericDao dao = new GenericDao(User.class);
        List<User> users = dao.getAll();
        model.addAttribute("users", users);
        return "user";
    }

    @GetMapping("/user/{id}")
    String getuserbyid(@PathVariable int id, Model model) {
        GenericDao dao = new GenericDao(User.class);
        User user = (User)dao.getById(id);
        model.addAttribute("user", user);
        logger.info("Getting user: " + user);
        return "usershow";
    }

    @GetMapping("/user/{id}/edit")
    String edituser(@PathVariable int id, Model model) {
        GenericDao dao = new GenericDao(User.class);
        User user = (User)dao.getById(id);
        model.addAttribute("user", user);
        return "useredit";
    }

    @PostMapping(value = "/user/{id}/update")
    String updateuser(@PathVariable int id,
                      @RequestParam("username") String username,
                      @RequestParam("firstName") String firstName,
                      @RequestParam("lastName") String lastName,
                      @RequestParam("password") String password) {
        GenericDao dao = new GenericDao(User.class);
        User user = (User)dao.getById(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        dao.saveOrUpdate(user);
        return "redirect:/user/{id}";
    }

    @GetMapping("/user/new")
    String newuser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "usernew";
    }

    @PostMapping(value = "/user/create")
    String createuser(@RequestParam("username") String username,
                      @RequestParam("firstName") String firstName,
                      @RequestParam("lastName") String lastName,
                      @RequestParam("email") String email,
                      @RequestParam("password") String password) {
        GenericDao dao = new GenericDao(User.class);
        User user = new User();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        UserDetails userDetails =
                org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
                        .username(username)
                        .password(password)
                        .roles("USER")
                        .build();
        if(!inMemoryUserDetailsManager.userExists(username)) {
            int id = dao.insert(user);
            inMemoryUserDetailsManager.createUser(userDetails);
            logger.info("the user that is created: " + username);
            return "redirect:/user/" + id;
        }
        else {
            return "redirect:/user/new?error";
        }
    }
}
