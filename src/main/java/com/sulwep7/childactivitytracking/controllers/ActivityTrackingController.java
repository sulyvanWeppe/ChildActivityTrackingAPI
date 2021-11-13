package com.sulwep7.childactivitytracking.controllers;

import com.sulwep7.childactivitytracking.model.ActivityTracking;
import com.sulwep7.childactivitytracking.services.ActivityServiceImpl;
import com.sulwep7.childactivitytracking.services.ActivityTrackingService;
import com.sulwep7.childactivitytracking.services.ActivityTrackingServiceImpl;
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
public class ActivityTrackingController {

    @Autowired
    ActivityTrackingServiceImpl activityTrackingService;

    @GetMapping(value="/activitytracking", produces = "application/json")
    public List<ActivityTracking> getActivitiesTracking() {
        return activityTrackingService.getActivitiesTracking();
    }

    @GetMapping(value = "/activitytracking/{id}", produces = "application/json")
    public ActivityTracking getActivityTrackingById(@PathVariable int id) {
        try {
            return activityTrackingService.getActivityTrackingById(id);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value="/activitytracking/{activityid}", produces = "application/json")
    public List<ActivityTracking> getActivitiesTrackingByActivity(@PathVariable int activityid) {
        try {
            return activityTrackingService.getActivitiesTrackingByActivityId(activityid);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value="/activitytracking/{childid}", produces = "application/json")
    public List<ActivityTracking> getActivitiesTrackingByChild(@PathVariable int childid) {
        try {
            return activityTrackingService.getActivitiesTrackingByChildId(childid);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping(value = "/activitytracking/{id}", produces = "application/json")
    public ResponseEntity deleteActivityTrackingById(@PathVariable int id) {
        activityTrackingService.deleteActivityTrackingById(id);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/activitytracking/{childid}", produces = "application/json")
    public ResponseEntity deleteActivityTrackingByChild(@PathVariable int childid) {
        activityTrackingService.deleteActivityTrackingByChildId(childid);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/activitytracking/{activityid}", produces = "application/json")
    public ResponseEntity deleteActivityTrackingByActivity(@PathVariable int activityid) {
        activityTrackingService.deleteActivityTrackingByActivityId(activityid);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/activitytracking", produces = "application/json")
    public ActivityTracking createActivityTracking(@RequestBody ActivityTracking activityTracking) {
        ActivityTracking newActivityTracking = null;
        try {
            newActivityTracking = activityTrackingService.createActivityTracking(activityTracking.getChildId(), activityTracking.getActivityId(), activityTracking.getActivityTimestamp(), activityTracking.getActivityRemark());
        } catch (InvalidParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return newActivityTracking;
    }

    @PutMapping(value="/activitytracking", produces = "application/json")
    public ActivityTracking updateActivityTracking(@RequestBody ActivityTracking activityTracking) {
        try {
            activityTrackingService.updateActivityTracking(activityTracking);
            return activityTrackingService.getActivityTrackingById(activityTracking.getId());
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (InvalidParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
