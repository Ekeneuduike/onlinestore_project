package com.ekene.controller;

import com.ekene.module.Order;
import com.ekene.module.dto.OrderItemRequest;
import com.ekene.module.dto.OrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderItemControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
     public void placeOrder() throws Exception {
        OrderRequest orderRequest = getOrder();
        String valueAsString = objectMapper.writeValueAsString(orderRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8085/api/order/placeorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString))
                 .andExpect(status().isOk());
     }

    private OrderRequest getOrder() {
         return OrderRequest.builder()
                 .orderItemRequests(List.of(
                         OrderItemRequest.builder()
                                 .author("sir caleb")
                                 .description("waves is a physics textbook that aims at get students used to wave fun…")
                                 .price(955.55F)
                                 .productName("wave")
                                 .quantity(1)
                                 .build(),
                         OrderItemRequest.builder()
                                 .author("uchenna emma")
                                 .description("a book about helen the princess")
                                 .price(50.33F)
                                 .productName("hello")
                                 .quantity(1)
                                 .build()
                 )).build();
    }
}