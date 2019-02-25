/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jack;

import com.jack.persistence.UserDao;
import com.jack.entity.User;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.*;

@Controller
@SpringBootApplication
public class Main {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Main.class, args);
  }

  @GetMapping("/")
  String index() {
    return "hello";
  }

  @GetMapping("/users")
  String users(Map<String, Object> model) {
    UserDao userDao = new UserDao();
    List<User> rs = userDao.getAll();
    ArrayList<String> output = new ArrayList<String>();
    output.add("User from DB: " + rs);
    model.put("records", output);
    return "users";
  }

  @GetMapping("/users/{id}")
  String userbyid(@PathVariable int id, Map<String, Object> model){
    UserDao userDao = new UserDao();
    User user = userDao.getById(id);
    model.put("record", user);
    return "user";
  }

  @RequestMapping("/hello")
  String hello() {
    return "hello";
  }
}
