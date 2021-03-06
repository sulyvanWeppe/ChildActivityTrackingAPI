package com.sulwep7.childactivitytracking.controllers;

import com.sulwep7.childactivitytracking.model.Child;
import com.sulwep7.childactivitytracking.services.ChildServiceImpl;
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
public class ChildController {

    @Autowired
    ChildServiceImpl childService;

    @GetMapping(value="/child", produces = "application/json")
    public List<Child> getChildren() {
        log.info("Controller - GET - /child");
        return childService.getChildren();
    }

    @GetMapping(value="/child/{id}", produces = "application/json")
    public Child getChildById(@PathVariable int id) {
        try {
            log.info("Controller - GET - /child/{}",id);
            return childService.getChildById(id);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value="/child/parent1id/{parent1Id}", produces = "application/json")
    public List<Child> getChildrenByParent1(@PathVariable int parent1Id) {
        try {
            log.info("Controller - GET - /child/parent1id/{}",parent1Id);
            return childService.getChildrenByParent1Id(parent1Id);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value="/child/parent2id/{parent2Id}", produces = "application/json")
    public List<Child> getChildrenByParent2(@PathVariable int parent2Id) {
        try {
            log.info("Controller - GET - /child/parent2id/{}",parent2Id);
            return childService.getChildrenByParent2Id(parent2Id);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value="/child/parentsid/{parent1Id}/{parent2Id}", produces = "application/json")
    public List<Child> getChildrenByParents(@PathVariable int parent1Id, @PathVariable int parent2Id) {
        try {
            log.info("Controller - GET - /child/parentsid/{}/{}",parent1Id, parent2Id);
            return childService.getChildrenByParentsId(parent1Id, parent2Id);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(value="/child", produces = "application/json")
    public Child createChild(@RequestBody Child child) {
        Child newChild = null;
        try {
            log.info("Controller - POST - /child with input child {}",child);
            newChild = childService.createChild(child.getFirstName(), child.getLastName(), child.getParent1Id(), child.getParent2Id(), child.getBirthDate());
        } catch(InvalidParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return newChild;
    }

    @DeleteMapping(value = "/child/{id}", produces = "application/json")
    public ResponseEntity deleteChildById(@PathVariable int id) {
        try {
            log.info("Controller - DELETE - /child/{}",id);
            childService.deleteChildById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value="/child", produces = "application/json")
    public Child updateChild(@RequestBody Child child) {
        try {
            log.info("Controller - PUT - /child with input child {}",child);
            childService.updateChild(child);
            return childService.getChildById(child.getId());
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch(InvalidParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
