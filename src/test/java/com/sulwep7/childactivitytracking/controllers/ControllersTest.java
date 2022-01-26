package com.sulwep7.childactivitytracking.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sulwep7.childactivitytracking.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Timestamp;
import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
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
    public void createThenDelete() {
        /**
         * Create
         */
        //User
        User user = User.builder()
                .login("toto")
                .password("totoPassword")
                .emailAddress("toto@email.com")
                .build();
        String resUserPost = null;
        User newUser = null;
        try {
            resUserPost = this.mockMvc.perform(post("/user")
                    .content(objectToJson(user))
                    .contentType("application/json")
                    .accept("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.login").value("toto"))
                    .andReturn().getResponse().getContentAsString();
            newUser = objectMapper.readValue(resUserPost, User.class);
            log.info("createThenDeleteUser : created user {}",user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Parent
        //Parent1
        Parent parent1 = Parent.builder()
                .emailAddress("parent1@email.com")
                .lastName("parent1LastName")
                .firstName("parent1FirstName")
                .userId(newUser.getId())
                .build();
        String resParentPost = null;
        Parent newParent1 = null;
        try {
            resParentPost = this.mockMvc.perform(post("/parent")
                    .contentType("application/json")
                    .accept("application/json")
                    .content(objectToJson(parent1)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andReturn().getResponse().getContentAsString();
            newParent1 = objectMapper.readValue(resParentPost, Parent.class);
            log.info("createThenDeleteUser : created parent {}",newParent1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Parent2
        Parent parent2 = Parent.builder()
                .emailAddress("parent2@email.com")
                .lastName("parent2LastName")
                .firstName("parent2FirstName")
                .userId(newUser.getId())
                .build();
        Parent newParent2 = null;
        try {
            resParentPost = this.mockMvc.perform(post("/parent")
                            .contentType("application/json")
                            .accept("application/json")
                            .content(objectToJson(parent2)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andReturn().getResponse().getContentAsString();
            newParent2 = objectMapper.readValue(resParentPost, Parent.class);
            log.info("createThenDeleteUser : created parent {}",newParent2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Child
        Child child = Child.builder()
                .birthDate(Timestamp.from(Instant.now()))
                .parent1Id(newParent1.getId())
                .parent2Id(newParent2.getId())
                .lastName("parent2LastName")
                .firstName("parent2FirstName")
                .build();
        String resChildPost = null;
        Child newChild = null;
        try {
            resChildPost = this.mockMvc.perform(post("/child")
                    .contentType("application/json")
                    .accept("application/json")
                    .content(objectToJson(child)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andReturn().getResponse().getContentAsString();
            newChild = objectMapper.readValue(resChildPost, Child.class);
            log.info("createThenDeleteUser : created child {}",newChild);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Activity
        Activity activity = Activity.builder()
                .name("activity")
                .measureLabel("measureLabel")
                .build();
        String resActivityPost = null;
        Activity newActivity = null;
        try {
            resActivityPost = this.mockMvc.perform(post("/activity")
                    .content(objectToJson(activity))
                    .contentType("application/json")
                    .accept("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andReturn().getResponse().getContentAsString();
            newActivity = objectMapper.readValue(resActivityPost, Activity.class);
            log.info("createThenDeleteUser : created activity {}",newActivity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Activity Tracking
        ActivityTracking actT = ActivityTracking.builder()
                .activityRemark("remark")
                .activityTimestamp(Timestamp.from(Instant.now()))
                .activityId(newActivity.getId())
                .childId(newChild.getId())
                .build();
        String resActTPost = null;
        ActivityTracking newActT = null;
        try {
            resActTPost = this.mockMvc.perform(post("/activitytracking")
                    .accept("application/json")
                    .contentType("application/json")
                    .content(objectToJson(actT)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andReturn().getResponse().getContentAsString();
            newActT = objectMapper.readValue(resActTPost, ActivityTracking.class);
            log.info("createThenDeleteUser : created activityTracking {}",newActT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Doctor
        Doctor doctor = Doctor.builder()
                .name("doctor")
                .country("country")
                .city("city")
                .street("street")
                .streetNr("nr")
                .phoneNr("phoneNr")
                .userId(newUser.getId())
                .zipCode("zipCode")
                .emailAddress("doctor@email.com")
                .build();
        String resDoctorPost = null;
        Doctor newDoctor = null;
        try {
            resDoctorPost = this.mockMvc.perform(post("/doctor")
                    .content(objectToJson(doctor))
                    .contentType("application/json")
                    .accept("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andReturn().getResponse().getContentAsString();
            newDoctor = objectMapper.readValue(resDoctorPost, Doctor.class);
            log.info("createThenDeleteUser : created doctor {}",newDoctor);
        } catch (Exception e) {
            e.printStackTrace();
        }


        /**
         * Delete
         */
        if (newUser != null
        && newDoctor != null
        && newParent1 != null
        && newParent2 != null
        && newChild != null
        && newActivity != null
        && newActT != null) {
            try {
                //ActivityTracking
                log.info("createThenDeleteUser : delete activityTracking {}",newActT);
                this.mockMvc.perform(MockMvcRequestBuilders.delete("/activitytracking/"+newActT.getId())
                                .accept("application/json"))
                        .andExpect(status().isOk());

                //Activity
                log.info("createThenDeleteUser : delete activity {}",newActivity);
                this.mockMvc.perform(MockMvcRequestBuilders.delete("/activity/"+newActivity.getId())
                                .accept("application/json"))
                        .andExpect(status().isOk());

                //Child
                log.info("createThenDeleteUser : delete child {}",newChild);
                this.mockMvc.perform(MockMvcRequestBuilders.delete("/child/"+newChild.getId())
                                .accept("application/json"))
                        .andExpect(status().isOk());

                //Parent
                log.info("createThenDeleteUser : delete parent {}",newParent1);
                this.mockMvc.perform(MockMvcRequestBuilders.delete("/parent/"+newParent1.getId())
                                .accept("application/json"))
                        .andExpect(status().isOk());
                log.info("createThenDeleteUser : delete parent {}",newParent2);
                this.mockMvc.perform(MockMvcRequestBuilders.delete("/parent/"+newParent2.getId())
                                .accept("application/json"))
                        .andExpect(status().isOk());

                //Doctor
                log.info("createThenDeleteUser : delete doctor {}",newDoctor);
                this.mockMvc.perform(MockMvcRequestBuilders.delete("/doctor/"+newDoctor.getId())
                                .accept("application/json"))
                        .andExpect(status().isOk());

                //User
                log.info("createThenDeleteUser : delete user {}",newUser);
                this.mockMvc.perform(MockMvcRequestBuilders.delete("/user/"+newUser.getId())
                                .accept("application/json"))
                        .andExpect(status().isOk());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            Assertions.assertTrue(false);
        }
    }
}
