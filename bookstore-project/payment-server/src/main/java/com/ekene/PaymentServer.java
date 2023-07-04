package com.ekene;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentServer {
    public static void main(String[] args) {
        SpringApplication.run(PaymentServer.class,args);
          double[] prices = {20.0,52.78,80.98,56.0,63.78};
          
          Double totalPrice = 0.0;
          for (int i = 0; i < prices.length; i++) {
            totalPrice = totalPrice + prices[i];
        }
        System.out.println("totalPrice = " + totalPrice.floatValue());
    }
}