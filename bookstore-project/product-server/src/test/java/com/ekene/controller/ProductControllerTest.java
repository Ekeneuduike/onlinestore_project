package com.ekene.controller;

import com.ekene.module.dto.ProductResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void delete() throws Exception {
        ProductResponse productResponse = getProduct();
        String valueAsString = objectMapper.writeValueAsString(productResponse);
        mockMvc.perform(MockMvcRequestBuilders
                .post("http://localhost:8081/api/product/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
                ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    private ProductResponse getProduct() {
        return new ProductResponse("hello",
                "a book about helen the princess", "uchenna emma",
                20,50.33);

    }


}