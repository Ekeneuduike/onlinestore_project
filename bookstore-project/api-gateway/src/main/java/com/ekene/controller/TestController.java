package com.ekene.controller;

import com.ekene.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);
    private final TestService testService;
    TestController(TestService testService){
        this.testService = testService;
    }

    @GetMapping("/home")
    public String Home() throws InterruptedException {
        log.info("request received");
        return  testService.home();
    }
}
