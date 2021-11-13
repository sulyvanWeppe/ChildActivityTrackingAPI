package com.sulwep7.childactivitytracking.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sulwep7.childactivitytracking.model.User;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllersTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserController userController;
    @Autowired
    private ObjectMapper objectMapper;

    private String objectToJson(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void shouldReturnTrue() {
        User user = User.builder()
                .login("toto")
                .password("totoPassword")
                .emailAddress("toto@email.com")
                .build();
        try {
            this.mockMvc.perform(MockMvcRequestBuilders.post("/user")
                    .content(objectToJson(user))
                    .contentType("application/json")
                    .accept("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.login").value("toto"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
