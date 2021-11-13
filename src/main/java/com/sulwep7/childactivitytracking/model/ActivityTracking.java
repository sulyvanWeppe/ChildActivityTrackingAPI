package com.sulwep7.childactivitytracking.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@Builder
@Entity
public class ActivityTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int childId;
    private int activityId;
    private Timestamp activityTimestamp;
    private String activityRemark;

    public ActivityTracking() {};

    public ActivityTracking(int id, int child_id, int activity_id, Timestamp activity_timestamp, String activity_remark) {
        this.id = id;
        this.childId = child_id;
        this.activityId = activity_id;
        this.activityTimestamp = activity_timestamp;
        this.activityRemark = activity_remark;
    }
}
