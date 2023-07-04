package com.ekene;

import com.ekene.module.Product;
import com.ekene.module.dto.ProductResponse;
import com.ekene.repository.ProductRepository;
import com.ekene.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@AllArgsConstructor
public class ProductServer {
    public static void main(String[] args) {
        SpringApplication.run(ProductServer.class,args);
    }

    private final ProductService productService;
   /* @Bean
    CommandLineRunner commandLineRunner(){
        return args -> {
            ProductResponse productResponse = ProductResponse.builder()
                    .productName("wave")
                    .price(955.525)
                    .quantity(50)
                    .description("waves is a physics textbook that aims at get students used to wave functions")
                    .author("sir caleb")
                    .build();
            productService.addproduct(productResponse);
        };
    }*/

}