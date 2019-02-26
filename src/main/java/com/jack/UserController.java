package com.jack;

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
    @GetMapping("/users")
    String users(Model model) {
        UserDao userDao = new UserDao();
        List<User> rs = userDao.getAll();
        model.addAttribute("users", rs);
        return "users.html";
    }

    @GetMapping("/user/{id}")
    String userbyid(@PathVariable int id, Model model) {
        UserDao userDao = new UserDao();
        User user = userDao.getById(id);
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("edit/{id}")
    String edituserbyid(@PathVariable int id, Model model) {
        UserDao userDao = new UserDao();
        User user = userDao.getById(id);
        model.addAttribute("user", user);
        return "useredit";
    }

    @PostMapping(value = "/update/{id}")
    String submit(@PathVariable int id, @RequestParam("userName") String userName) {
        UserDao userDao = new UserDao();
        User user = userDao.getById(id);
        user.setUserName(userName);
        userDao.saveOrUpdate(user);
        return "redirect:/users";

    }
}
