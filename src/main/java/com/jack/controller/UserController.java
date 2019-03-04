package com.jack.controller;

import com.jack.entity.User;
import com.jack.persistence.UserDao;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@SpringBootApplication
public class UserController {
    @GetMapping("/user")
    String users(Model model) {
        UserDao userDao = new UserDao();
        List<User> users = userDao.getAll();
        model.addAttribute("users", users);
        return "user";
    }

    @GetMapping("/user/{id}")
    String getuserbyid(@PathVariable int id, Model model) {
        UserDao userDao = new UserDao();
        User user = userDao.getById(id);
        model.addAttribute("user", user);
        return "usershow";
    }

    @GetMapping("/user/{id}/edit")
    String edituser(@PathVariable int id, Model model) {
        UserDao userDao = new UserDao();
        User user = userDao.getById(id);
        model.addAttribute("user", user);
        return "useredit";
    }

    @PostMapping(value = "/user/{id}/update")
    String updateuser(@PathVariable int id,
                      @RequestParam("userName") String userName,
                      @RequestParam("firstName") String firstName,
                      @RequestParam("lastName") String lastName,
                      @RequestParam("password") String password) {
        UserDao userDao = new UserDao();
        User user = userDao.getById(id);
        user.setUserName(userName);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        userDao.saveOrUpdate(user);
        return "redirect:/user/{id}";
    }

    @GetMapping("/user/new")
    String newuser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "usernew";
    }

    @PostMapping(value = "/user/create")
    String createuser(@RequestParam("userName") String userName,
                      @RequestParam("firstName") String firstName,
                      @RequestParam("lastName") String lastName,
                      @RequestParam("email") String email,
                      @RequestParam("password") String password) {
        UserDao userDao = new UserDao();
        User user = new User();
        user.setUserName(userName);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        int id = userDao.insert(user);
        return "redirect:/user/" + id;

    }
}
