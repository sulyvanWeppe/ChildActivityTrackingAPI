package com.sulwep7.childactivitytracking.controllers;

import com.sulwep7.childactivitytracking.model.Activity;
import com.sulwep7.childactivitytracking.services.ActivityServiceImpl;
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
public class ActivityController {

    @Autowired
    ActivityServiceImpl activityService;

    @GetMapping(value = "/activity", produces = "application/json")
    public List<Activity> getActivities() {
        return activityService.getActivities();
    }

    @GetMapping(value = "/activity/{id}", produces = "application/json")
    public Activity getActivityById(@PathVariable int id) {
        try {
            return activityService.getActivityById(id);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(value = "/activity", produces = "application/json")
    public Activity createActivity(@RequestBody Activity activity) {
        Activity newActivity = null;
        try {
            newActivity = activityService.createActivity(activity.getName());
        } catch (InvalidParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return newActivity;
    }

    @PutMapping(value="/activity", produces = "application/json")
    public Activity updateActivity(@RequestBody Activity activity) {
        try {
            activityService.updateActivity(activity);
            return activityService.getActivityById(activity.getId());
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch(InvalidParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping(value="/activity/{id}", produces = "application/json")
    public ResponseEntity deleteActivity(@PathVariable int id) {
        try {
            activityService.deleteActivityById(id);
        }
        catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}