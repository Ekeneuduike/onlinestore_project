package com.ekene.config;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Configuration(proxyBeanMethods = false)
public class TestCommandLineRunner {
    Logger log = LoggerFactory.getLogger(TestComponent.class);
    @Bean
    CommandLineRunner commandLineRunner (ObservationRegistry registry, RestTemplate restTemplate){
        Random highCardinalityValue = new Random();
        List<String> lowCardinalityValue = Arrays.asList("userType1","userType2","userType3");
        return args -> {
              String highCardinalityUserId = String.valueOf(highCardinalityValue.nextLong(100_000));
            Observation.createNotStarted("my.observation",registry)
                    .lowCardinalityKeyValue("usertype",randomUserTypePicker(lowCardinalityValue))
                    .highCardinalityKeyValue("userid",highCardinalityUserId)
                    .contextualName("command-line-runner")
                    .observe(()->{
                        log.info("will send a request to the server");
                        String response = restTemplate.getForObject("http://localhost:8081/home",String.class,highCardinalityUserId);
                        log.info("got response[{]]",response);
                    });

        };
    }

    private String randomUserTypePicker(List<String> lowCardinalityValue) {
        Random random = new Random();
        return lowCardinalityValue.get(random.nextInt(2));
    }
}
