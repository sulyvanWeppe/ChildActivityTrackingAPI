package com.sulwep7.childactivitytracking.services;

import com.sulwep7.childactivitytracking.consumer.ActivityRepository;
import com.sulwep7.childactivitytracking.model.Activity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.rmi.NoSuchObjectException;
import java.security.InvalidParameterException;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public List<Activity> getActivities() {
        List<Activity> activities = activityRepository.findAll();

        return activities;
    }

    @Override
    public Activity getActivityById(int id) throws NoSuchObjectException {
        Activity activity = activityRepository.findById(id);

        if (ObjectUtils.isEmpty(activity)) {
            throw new NoSuchObjectException("No activity found with id : "+id);
        }

        return activity;
    }

    @Override
    public Activity getActivityByName(String name) throws NoSuchObjectException {
        Activity activity = activityRepository.findByName(name);

        if (ObjectUtils.isEmpty(activity)) {
            throw new NoSuchObjectException("No activity found with name : "+name);
        }

        return activity;
    }

    @Override
    public Activity createActivity(String name) throws InvalidParameterException {
        //Data quality checks
        boolean isValidName = !StringUtils.isBlank(name);
        if (!isValidName) {
            throw new InvalidParameterException("Input parameters of service createActivity are not valid :"+name);
        }

        Activity activity = Activity.builder()
                .name(name)
                .build();
        activity = activityRepository.save(activity);

        return activity;
    }

    @Override
    public void deleteActivityById(int id) {
        activityRepository.deleteById(id);
    }

    @Override
    public void deleteActivityByName(String name) {
        activityRepository.deleteByName(name);
    }

    @Override
    public void updateActivity(Activity activity) throws InvalidParameterException, NoSuchObjectException {
        int id = activity.getId();
        String name = activity.getName();

        //Data quality checks
        boolean isValidName = !StringUtils.isBlank(name);
        if (!isValidName) {
            throw new InvalidParameterException("Input parameter for service updateActivity are not valid : "+id+" and "+name);
        }
        boolean isValidId = activityRepository.existsById(id);
        if (!isValidId) {
            throw new NoSuchObjectException("No activity found with id : "+id);
        }

        activityRepository.updateName(id, name);
    }
}
