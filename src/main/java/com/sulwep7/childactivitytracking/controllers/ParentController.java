package com.sulwep7.childactivitytracking.controllers;

import com.sulwep7.childactivitytracking.model.Parent;
import com.sulwep7.childactivitytracking.services.ParentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.rmi.NoSuchObjectException;
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

    @GetMapping(value="/parent/{firstName}/{lastName}", produces = "application/json")
    public List<Parent> getParentsByNames(@PathVariable String firstName, @PathVariable String lastName) {
        try {
            return parentService.getParentsByNames(firstName, lastName);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value="/parent/{userId}", produces = "application/json")
    public List<Parent> getParentsByUser(@PathVariable int userId) {
        try {
            return parentService.getParentsByUserId(userId);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value = "/parent/{email}", produces = "application/json")
    public List<Parent> getParentByEmail(@PathVariable String email) {
        try {
            return parentService.getParentsByEmail(email);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping(value = "/parent/{id}", produces = "application/json")
    public ResponseEntity deleteParentById(@PathVariable int id) {
        parentService.deleteParentById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value="/parent", produces = "application/json")
    public Parent updateParent(@RequestBody Parent parent) {
        try {
            parentService.updateParent(parent);
        } catch (NoSuchObjectException e) {
            //ICIIIIIIIIIIIIIIIIIIIIIIII
        }
    }
}
