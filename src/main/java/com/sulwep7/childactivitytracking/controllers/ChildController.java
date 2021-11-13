package com.sulwep7.childactivitytracking.controllers;

import com.sulwep7.childactivitytracking.model.Child;
import com.sulwep7.childactivitytracking.services.ChildServiceImpl;
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
public class ChildController {

    @Autowired
    ChildServiceImpl childService;

    @GetMapping(value="/child", produces = "application/json")
    public List<Child> getChildren() {
        return childService.getChildren();
    }

    @GetMapping(value="/child/{id}", produces = "application/json")
    public Child getChildById(@PathVariable int id) {
        try {
            return childService.getChildById(id);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value="/child/{parent1Id}", produces = "application/json")
    public List<Child> getChildrenByParent1(@PathVariable int parent1Id) {
        try {
            return childService.getChildrenByParent1Id(parent1Id);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value="/child/{parent2Id}", produces = "application/json")
    public List<Child> getChildrenByParent2(@PathVariable int parent2Id) {
        try {
            return childService.getChildrenByParent2Id(parent2Id);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value="/child/{parent1Id}/{parent2Id}", produces = "application/json")
    public List<Child> getChildrenByParents(@PathVariable int parent1Id, @PathVariable int parent2Id) {
        try {
            return childService.getChildrenByParentsId(parent1Id, parent2Id);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(value="/child", produces = "application/json")
    public Child createChild(@RequestBody Child child) {
        Child newChild = null;
        try {
            newChild = childService.createChild(child.getFirstName(), child.getLastName(), child.getParent1Id(), child.getParent2Id(), child.getAge());
        } catch(InvalidParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return newChild;
    }

    @DeleteMapping(value = "/child/{id}", produces = "application/json")
    public ResponseEntity deleteChildById(@PathVariable int id) {
        childService.deleteChildById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value="/child", produces = "application/json")
    public Child updateChild(@RequestBody Child child) {
        try {
            childService.updateChild(child);
            return childService.getChildById(child.getId());
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch(InvalidParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
