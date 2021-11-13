package com.sulwep7.childactivitytracking.services;

import com.sulwep7.childactivitytracking.model.ActivityTracking;

import java.rmi.NoSuchObjectException;
import java.security.InvalidParameterException;
import java.sql.Timestamp;
import java.util.List;

public interface ActivityTrackingService {

    public List<ActivityTracking> getActivitiesTracking();

    public ActivityTracking getActivityTrackingById(int id) throws NoSuchObjectException;

    public List<ActivityTracking> getActivitiesTrackingByChildId(int childId) throws NoSuchObjectException;

    public List<ActivityTracking> getActivitiesTrackingByActivityId(int activityId) throws NoSuchObjectException;

    public ActivityTracking createActivityTracking(int childId, int activityId, Timestamp timestamp, String remark) throws InvalidParameterException;

    public void deleteActivityTrackingById(int id);

    public void deleteActivityTrackingByChildId(int childId);

    public void deleteActivityTrackingByActivityId(int activityId);

    public void updateActivityTrackingChild(int id, int childId) throws InvalidParameterException, NoSuchObjectException;

    public void updateActivityTrackingActivity(int id, int activityId) throws InvalidParameterException, NoSuchObjectException;

    public void updateActivityTrackingTimestamp(int id, Timestamp timestamp) throws InvalidParameterException, NoSuchObjectException;

    public void updateActivityTrackingRemark(int id, String remark) throws InvalidParameterException, NoSuchObjectException;
}
