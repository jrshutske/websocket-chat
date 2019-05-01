package com.jack.controller;

import com.jack.persistence.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import com.jack.entity.User;

import java.util.*;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final Logger logger = LogManager.getLogger(this.getClass());



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/", "/user/new", "/user/create").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login").defaultSuccessUrl("/", true)
            .permitAll()
            .and()
            .logout()
            .permitAll();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {

        GenericDao dao = new GenericDao(User.class);
        List<User> users = dao.getAll();
        logger.info("here are all the users" + users);
        ArrayList<UserDetails> postusers = new ArrayList<>();
        UserDetails userDetails;
        String username = null;
        String password = null;
        for (User user : users) {
            username = user.getUsername();
            password = user.getPassword();
            userDetails =
                    org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
                            .username(username)
                            .password(password)
                            .roles("USER")
                            .build();
            postusers.add(userDetails);
        }
        userDetails =
                org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("admin")
                        .roles("ADMIN")
                        .build();
        postusers.add(userDetails);
        logger.info("here are all the added users" + postusers);

        return new InMemoryUserDetailsManager(postusers);
    }
}

