package com.ekene.repository;

import com.ekene.controller.UserController;
import com.ekene.module.Role;
import com.ekene.registration.RegistrationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserRepositoryTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testUser() throws Exception {
        RegistrationRequest request = getRegistartionRequest();
        String requestToString = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("http://user-server/api/user/register")
                .content(requestToString)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    private RegistrationRequest getRegistartionRequest() {
        return RegistrationRequest.builder()
                .email("ekeneuduike@Outlook.com")
                .firstname("ekene")
                .lastname("uduike")
                .password("password")
                .dob(new Date(2002,02,25))
                .build();
    }

}