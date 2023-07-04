package com.ekene.service;

import io.micrometer.observation.annotation.Observed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;


@Service
public class TestService {
    private final static Logger log = LoggerFactory.getLogger(TestService.class);
    Random random = new Random();


    @Observed(name = "user.name",
    contextualName = "getting-user-name",
    lowCardinalityKeyValues = {"usertype1","userType2"})
    public String home() throws InterruptedException {
        Thread.sleep(random.nextInt(1000));
        return "you are welcomed here mr";
    }
}
