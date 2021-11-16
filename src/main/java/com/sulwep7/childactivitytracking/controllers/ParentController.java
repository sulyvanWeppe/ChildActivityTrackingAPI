package com.sulwep7.childactivitytracking.controllers;

import com.sulwep7.childactivitytracking.model.Parent;
import com.sulwep7.childactivitytracking.services.ParentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.rmi.NoSuchObjectException;
import java.security.InvalidParameterException;
import java.util.List;

@RestController
@RequestMapping("/")
public class ParentController {
    @Autowired
    ParentServiceImpl parentService;

    @GetMapping(value = "/parent", produces = "application/json")
    public List<Parent> getParents() {
        return parentService.getParents();
    }

    @GetMapping(value = "/parent/{id}", produces = "application/json")
    public Parent getParentById(@PathVariable int id) {
        try {
            return parentService.getParentById(id);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value="/parent/names/{firstName}/{lastName}", produces = "application/json")
    public List<Parent> getParentsByNames(@PathVariable String firstName, @PathVariable String lastName) {
        try {
            return parentService.getParentsByNames(firstName, lastName);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value="/parent/userid/{userId}", produces = "application/json")
    public List<Parent> getParentsByUser(@PathVariable int userId) {
        try {
            return parentService.getParentsByUserId(userId);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value = "/parent/email/{email}", produces = "application/json")
    public List<Parent> getParentByEmail(@PathVariable String email) {
        try {
            return parentService.getParentsByEmail(email);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping(value = "/parent/{id}", produces = "application/json")
    public ResponseEntity deleteParentById(@PathVariable int id) {
        try {
            parentService.deleteParentById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value="/parent", produces = "application/json")
    public Parent updateParent(@RequestBody Parent parent) {
        try {
            parentService.updateParent(parent);
            return parentService.getParentById(parent.getId());
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (InvalidParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping(value = "/parent", produces = "application/json")
    public Parent createParent(@RequestBody Parent parent) {
        Parent newParent = null;
        try {
            newParent = parentService.createParent(parent.getUserId(), parent.getFirstName(), parent.getLastName(), parent.getEmailAddress());
        } catch (InvalidParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch(Exception e) {
            //Check if parent was created in the DB
            //if yes then delete it
            if (newParent != null) {
                parentService.deleteParentById(newParent.getId());
            }
        }

        return newParent;
    }
}
