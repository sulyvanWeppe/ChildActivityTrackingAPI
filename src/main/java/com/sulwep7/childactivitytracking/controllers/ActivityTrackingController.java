package com.sulwep7.childactivitytracking.controllers;

import com.sulwep7.childactivitytracking.model.ActivityTracking;
import com.sulwep7.childactivitytracking.services.ActivityTrackingServiceImpl;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ActivityTrackingController {

    @Autowired
    ActivityTrackingServiceImpl activityTrackingService;

    @GetMapping(value="/activitytracking", produces = "application/json")
    public List<ActivityTracking> getActivitiesTracking() {
        log.info("Controller - GET - /activitytracking");
        return activityTrackingService.getActivitiesTracking();
    }

    @GetMapping(value = "/activitytracking/{id}", produces = "application/json")
    public ActivityTracking getActivityTrackingById(@PathVariable int id) {
        try {
            log.info("Controller - GET - /activitytracking/{}",id);
            return activityTrackingService.getActivityTrackingById(id);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value="/activitytracking/activityid/{activityid}", produces = "application/json")
    public List<ActivityTracking> getActivitiesTrackingByActivity(@PathVariable int activityid) {
        try {
            log.info("Controller - GET - /activitytracking/activityid/{}",activityid);
            return activityTrackingService.getActivitiesTrackingByActivityId(activityid);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value="/activitytracking/childid/{childid}", produces = "application/json")
    public List<ActivityTracking> getActivitiesTrackingByChild(@PathVariable int childid) {
        try {
            log.info("Controller - GET - /activitytracking/childid/{}",childid);
            return activityTrackingService.getActivitiesTrackingByChildId(childid);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping(value = "/activitytracking/{id}", produces = "application/json")
    public ResponseEntity deleteActivityTrackingById(@PathVariable int id) {
        try {
            log.info("Controller - DELETE - /activitytracking/{}",id);
            activityTrackingService.deleteActivityTrackingById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);}

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/activitytracking/childid/{childid}", produces = "application/json")
    public ResponseEntity deleteActivityTrackingByChild(@PathVariable int childid) {
        try {
            log.info("Controller - DELETE - /activitytracking/childid/{}",childid);
            activityTrackingService.deleteActivityTrackingByChildId(childid);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/activitytracking/activityid/{activityid}", produces = "application/json")
    public ResponseEntity deleteActivityTrackingByActivity(@PathVariable int activityid) {
        try {
            log.info("Controller - DELETE - /activitytracking/activityid/{}",activityid);
            activityTrackingService.deleteActivityTrackingByActivityId(activityid);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/activitytracking", produces = "application/json")
    public ActivityTracking createActivityTracking(@RequestBody ActivityTracking activityTracking) {
        ActivityTracking newActivityTracking = null;
        try {
            log.info("Controller - POST - /activitytracking with input activityTracking {}",activityTracking);
            newActivityTracking = activityTrackingService.createActivityTracking(activityTracking.getChildId(), activityTracking.getActivityId(), activityTracking.getActivityTimestamp(), activityTracking.getActivityRemark());
        } catch (InvalidParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return newActivityTracking;
    }

    @PutMapping(value="/activitytracking", produces = "application/json")
    public ActivityTracking updateActivityTracking(@RequestBody ActivityTracking activityTracking) {
        try {
            log.info("Controller - PUT - /activitytracking with input activityTracking {}",activityTracking);
            activityTrackingService.updateActivityTracking(activityTracking);
            return activityTrackingService.getActivityTrackingById(activityTracking.getId());
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (InvalidParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
