package com.ekene.circuitbreakerconfig;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.net.http.HttpResponse;
import java.time.Duration;

@Configuration
public class CircuitConfig {

    @Bean
    public CircuitBreakerConfig breakerConfig (){
        return CircuitBreakerConfig.custom().failureRateThreshold(50)
                .slidingWindowSize(20)
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .minimumNumberOfCalls(3)
                .slowCallDurationThreshold(Duration.ofSeconds(1))
                .waitDurationInOpenState(Duration.ofSeconds(3))
                .permittedNumberOfCallsInHalfOpenState(4)
                .slowCallRateThreshold(10)
                .build();
    }
    @Bean
    public TimeLimiterConfig timeLimiterConfig(){
        return TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(1))
                .build();
    }
    @Bean
    public TimeLimiterRegistry timeLimiterRegistry(){return TimeLimiterRegistry.of(timeLimiterConfig());}

    @Bean
    public CircuitBreakerRegistry registry(){
        return CircuitBreakerRegistry.of(breakerConfig());
    }
    @Bean
   public RetryConfig retryConfig(){
        return RetryConfig.custom()
                .maxAttempts(2)
                .failAfterMaxAttempts(true)
                .waitDuration(Duration.ofSeconds(2))
                .build();
   }
   @Bean
   public RetryRegistry retryRegistry(){
        return RetryRegistry.of(retryConfig());
   }
}
