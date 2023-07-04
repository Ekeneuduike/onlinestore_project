package com.ekene.service;

import com.ekene.module.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
@Autowired
UserService userService;

    @Test
    public void registerues (){
        User user = new  User();
                        user.setEmail("ekeneuduike@gmail.com");
                        user.setFirstname("ekene");
                        user.setLastname("uduike") ;
                        user.setPassword("password");

        userService.signUp(user);
    }

}