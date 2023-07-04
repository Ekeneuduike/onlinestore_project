package com.ekene.controller;

import com.ekene.module.User;
import com.ekene.repository.UserRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    @GetMapping
    @CircuitBreaker(name = "UserServer",fallbackMethod = "UserServerFallBack")
    public User getUser(String email){

        return userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("user not found"));
    }
    public User UserServerFallBack(){
        return new
                User("ekene","uduike",
                "ekeneudike@gmail.com","password",
                new Date(1999, Calendar.DECEMBER,29));
    }
}
