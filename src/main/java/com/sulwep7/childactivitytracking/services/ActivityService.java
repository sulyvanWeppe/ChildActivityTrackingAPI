package com.sulwep7.childactivitytracking.services;

import com.sulwep7.childactivitytracking.model.Activity;

import java.rmi.NoSuchObjectException;
import java.security.InvalidParameterException;
import java.util.List;

public interface ActivityService {

    public List<Activity> getActivities();

    public Activity getActivityById(int id) throws NoSuchObjectException;

    public Activity getActivityByName(String name) throws NoSuchObjectException;

    public Activity createActivity(String name) throws InvalidParameterException;

    public void deleteActivityById(int id);

    public void deleteActivityByName(String name);

    public void updateActivity(int id, String name) throws InvalidParameterException, NoSuchObjectException;
}
