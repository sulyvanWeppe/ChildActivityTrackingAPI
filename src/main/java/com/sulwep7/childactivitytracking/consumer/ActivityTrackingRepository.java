package com.sulwep7.childactivitytracking.consumer;

import com.sulwep7.childactivitytracking.model.Activity;
import com.sulwep7.childactivitytracking.model.ActivityTracking;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

public interface ActivityTrackingRepository extends CrudRepository<ActivityTracking, Integer> {

    List<ActivityTracking> findAll();

    ActivityTracking findById(int id);

    @Query("select at from ActivityTracking at where child_id=:childId")
    List<ActivityTracking> findByChildId(int childId);

    List<ActivityTracking> findByActivityId(int activityId);

    @Transactional
    @Modifying
    void deleteByChildId(int childId);

    @Transactional
    @Modifying
    void deleteByActivityId(int activityId);

    @Transactional
    @Modifying
    @Query("update ActivityTracking at set child_id=:childId where id=:id")
    void updateChildId(int id, int childId);

    @Transactional
    @Modifying
    @Query("update ActivityTracking at set activity_id=:activityId where id=:id")
    void updateActivityId(int id, int activityId);

    @Transactional
    @Modifying
    @Query("update ActivityTracking at set activity_timestamp=:tstmp where id=:id")
    void updateActivityTimestamp(int id, Timestamp tstmp);

    @Transactional
    @Modifying
    @Query("update ActivityTracking at set activity_remark=:remark where id=:id")
    void updateActivityRemark(int id, String remark);

    @Transactional
    @Modifying
    @Query("update ActivityTracking at set child_id=:childId, activity_id=:activityId, activity_timestamp=:activityTimestamp, activity_remark=:activityRemark where id=:id")
    void updateActivityTracking(int id, int childId, int activityId, Timestamp activityTimestamp, String activityRemark);

}
