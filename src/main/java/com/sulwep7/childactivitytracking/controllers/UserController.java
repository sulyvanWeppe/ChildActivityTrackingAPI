package com.sulwep7.childactivitytracking.controllers;

import com.sulwep7.childactivitytracking.model.User;
import com.sulwep7.childactivitytracking.model.exceptions.AlreadyUsedUserLogin;
import com.sulwep7.childactivitytracking.services.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.rmi.NoSuchObjectException;
import java.security.InvalidParameterException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @GetMapping(value = "/user/{id}", produces = "application/json")
    public User getById(@PathVariable int id) {
        try {
            log.info("Controller - GET - /user/{}",id);
            return userService.getUserById(id);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value="/user/login/{login}", produces="application/json")
    public User getByLogin(@PathVariable String login) {
        try {
            log.info("Controller - GET - /user/login/{}",login);
            return userService.getUserByLogin(login);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value="/user/email/{email}", produces = "application/json")
    public User getUserByEmail(@PathVariable String email) {
        try {
            log.info("Controller - GET - /user/email/{}",email);
            return userService.getUserByEmailAddress(email);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value="/user", produces = "application/json")
    public List<User> getUsers() {
        log.info("Controller - GET - /user");
        return userService.getUsers();
    }

    @PostMapping(value = "/user", produces="application/json")
    public User createUser(@RequestBody User user) {
        User newUser = null;
        try {
            log.info("Controller - POST - /user with input user {}", user);
            newUser =  userService.createUser(user.getEmailAddress(), user.getLogin(), user.getPassword());
            return newUser;
        } catch (AlreadyUsedUserLogin e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (InvalidParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            //Check if user was created in the DB
            //If yes then delete it
            if (newUser != null) {
                userService.deleteUserById(newUser.getId());
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping(value="/user/{id}", produces = "application/json")
    public ResponseEntity deleteUserById(@PathVariable int id) {
        try {
            log.info("Controller - DELETE - /user/{}",id);
            userService.deleteUserById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value="/user/login/{login}", produces = "application/json")
    public ResponseEntity deleteUserByLogin(@PathVariable String login)
    {
        try {
            log.info("Controller - DELETE - /user/login/{}",login);
            userService.deleteUserByLogin(login);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value="/user/email/{email}", produces="application/json")
    public ResponseEntity deleteUserByEmail(@PathVariable String email) {
        try {
            log.info("Controller - DELETE - /user/email/{}",email);
            userService.deleteUserByEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = "/user", produces = "application/json")
    public User updateUser(@RequestBody User user){
        try {
            log.info("Controller - PUT - /user with input user {}",user);
            userService.updateUser(user);
            return userService.getUserById(user.getId());
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (InvalidParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
