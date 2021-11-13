package com.sulwep7.childactivitytracking.services;

import com.sulwep7.childactivitytracking.consumer.ActivityRepository;
import com.sulwep7.childactivitytracking.consumer.ActivityTrackingRepository;
import com.sulwep7.childactivitytracking.consumer.ChildRepository;
import com.sulwep7.childactivitytracking.model.ActivityTracking;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.rmi.NoSuchObjectException;
import java.security.InvalidParameterException;
import java.sql.Timestamp;
import java.util.List;

@Service
public class ActivityTrackingServiceImpl implements ActivityTrackingService{

    @Autowired
    private ActivityTrackingRepository activityTrackingRepository;
    @Autowired
    private ChildServiceImpl childService;
    @Autowired
    private ActivityServiceImpl activityService;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ChildRepository childRepository;

    @Override
    public List<ActivityTracking> getActivitiesTracking() {
        return activityTrackingRepository.findAll();
    }

    @Override
    public ActivityTracking getActivityTrackingById(int id) throws NoSuchObjectException {
        ActivityTracking activityTracking = activityTrackingRepository.findById(id);
        if(ObjectUtils.isEmpty(activityTracking)) {
            throw new NoSuchObjectException("No activityTracking found with id : "+id);
        }

        return activityTracking;
    }

    @Override
    public List<ActivityTracking> getActivitiesTrackingByChildId(int childId) throws NoSuchObjectException {
        List<ActivityTracking> activityTrackings = activityTrackingRepository.findByChildId(childId);
        if(ObjectUtils.isEmpty(activityTrackings)) {
            throw new NoSuchObjectException("No activityTracking found with childId : "+childId);
        }

        return activityTrackings;
    }

    @Override
    public List<ActivityTracking> getActivitiesTrackingByActivityId(int activityId) throws NoSuchObjectException {
        List<ActivityTracking> activityTrackings = activityTrackingRepository.findByActivityId(activityId);
        if(ObjectUtils.isEmpty(activityTrackings)) {
            throw new NoSuchObjectException("No activityTracking found with activityId : "+activityId);
        }

        return activityTrackings;
    }

    @Override
    public ActivityTracking createActivityTracking(int childId, int activityId, Timestamp timestamp, String remark) throws InvalidParameterException {
        //Data quality checks
        boolean isValidRemark = !StringUtils.isBlank(remark);
        boolean isValidChildId = false;
        try {
            isValidChildId = !ObjectUtils.isEmpty(childService.getChildById(childId));
        } catch(NoSuchObjectException e) {}
        boolean isValidActivityId = false;
        try {
            isValidActivityId = !ObjectUtils.isEmpty(activityService.getActivityById(activityId));
        } catch(NoSuchObjectException e) {}

        if (!isValidChildId || !isValidActivityId || !isValidRemark) {
            throw new InvalidParameterException("Input parameters of service createActivityTracking are not valid :"+childId+", "+activityId+", "+timestamp+" and "+remark);
        }

        ActivityTracking activityTracking = ActivityTracking.builder()
                .childId(childId)
                .activityId(activityId)
                .activityTimestamp(timestamp)
                .activityRemark(remark)
                .build();
        activityTracking = activityTrackingRepository.save(activityTracking);

        return activityTracking;
    }

    @Override
    public void deleteActivityTrackingById(int id) {
        activityTrackingRepository.deleteById(id);
    }

    @Override
    public void deleteActivityTrackingByChildId(int childId) {
        activityTrackingRepository.deleteByChildId(childId);
    }

    @Override
    public void deleteActivityTrackingByActivityId(int activityId) {
        activityTrackingRepository.deleteByActivityId(activityId);
    }

    @Override
    public void updateActivityTrackingChild(int id, int childId) throws InvalidParameterException, NoSuchObjectException {
        //Data quality checks
        boolean isValidChildId = childRepository.existsById(childId);
        if (!isValidChildId) {
            throw new InvalidParameterException("Input parameters for service updateActivityTrackingChild are not valid : "+id+" and "+childId);
        }
        boolean isValidId = activityTrackingRepository.existsById(id);
        if(!isValidId) {
            throw new NoSuchObjectException("No activityTracking found with id : "+id);
        }

        activityTrackingRepository.updateChildId(id, childId);
    }

    @Override
    public void updateActivityTrackingActivity(int id, int activityId) throws InvalidParameterException, NoSuchObjectException {
        //Data quality checks
        boolean isValidActivityId = childRepository.existsById(activityId);
        if (!isValidActivityId) {
            throw new InvalidParameterException("Input parameters for service updateActivityTrackingChild are not valid : "+id+" and "+activityId);
        }
        boolean isValidId = activityTrackingRepository.existsById(id);
        if(!isValidId) {
            throw new NoSuchObjectException("No activityTracking found with id : "+id);
        }

        activityTrackingRepository.updateActivityId(id, activityId);
    }

    @Override
    public void updateActivityTrackingTimestamp(int id, Timestamp timestamp) throws InvalidParameterException, NoSuchObjectException {
        //Data quality checks
        boolean isValidId = activityTrackingRepository.existsById(id);
        if(!isValidId) {
            throw new NoSuchObjectException("No activityTracking found with id : "+id);
        }

        activityTrackingRepository.updateActivityTimestamp(id, timestamp);
    }

    @Override
    public void updateActivityTrackingRemark(int id, String remark) throws InvalidParameterException, NoSuchObjectException {
        //Data quality checks
        boolean isValidRemark = !StringUtils.isBlank(remark);
        if (!isValidRemark) {
            throw new InvalidParameterException("Input parameters for service updateActivityTrackingChild are not valid : "+id+" and "+remark);
        }
        boolean isValidId = activityTrackingRepository.existsById(id);
        if(!isValidId) {
            throw new NoSuchObjectException("No activityTracking found with id : "+id);
        }

        activityTrackingRepository.updateActivityRemark(id, remark);
    }
}
