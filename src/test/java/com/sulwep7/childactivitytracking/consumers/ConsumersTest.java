package com.sulwep7.childactivitytracking.consumers;

import com.sulwep7.childactivitytracking.consumer.*;
import com.sulwep7.childactivitytracking.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@SpringBootTest
@EntityScan
public class ConsumersTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private ChildRepository childRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ActivityTrackingRepository activityTrackingRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    public void createThenUpdateThenDelete()
    {
        /**
         * Create
         */

        //User
        User newUser = User.builder()
                .login("newUser")
                .password("newUserPassword")
                .emailAddress("newUserEmail@email.com")
                .build();
        newUser = userRepository.save(newUser);
        int newUserId = newUser.getId();

        //Parent
        //Parent 1
        Parent newParent1 = Parent.builder()
                .firstName("newParent1FirstName")
                .lastName("newParent1LastName")
                .emailAddress("newParent1@email.com")
                .userId(newUserId)
                .build();
        newParent1 = parentRepository.save(newParent1);
        int newParent1Id = newParent1.getId();
        //Parent 2
        Parent newParent2 = Parent.builder()
                .firstName("newParent2FirstName")
                .lastName("newParent2LastName")
                .emailAddress("newParent2@email.com")
                .userId(newUserId)
                .build();
        newParent2 = parentRepository.save(newParent2);
        int newParent2Id = newParent2.getId();

        //Child
        Child newChild = Child.builder()
                .firstName("newChildFirstName")
                .lastName("newChildLastName")
                .parent1Id(newParent1Id)
                .parent2Id(newParent2Id)
                .age(11)
                .build();
        newChild = childRepository.save(newChild);
        int newChildId = newChild.getId();

        //Activity
        Activity newActivity = Activity.builder()
                .name("eating")
                .build();
        newActivity = activityRepository.save(newActivity);
        int newActivityId = newActivity.getId();

        //ActivityTracking
        ActivityTracking newActivityTracking = ActivityTracking.builder()
                .activityId(newActivityId)
                .childId(newChildId)
                .activityTimestamp(Timestamp.from(Instant.now()))
                .activityRemark("All good")
                .build();
        newActivityTracking = activityTrackingRepository.save(newActivityTracking);
        int newActivityTrackingId = newActivityTracking.getId();

        //Doctor
        Doctor newDoctor = Doctor.builder()
                .name("newDoctorName")
                .city("Luxembourg")
                .street("Rue Rousseau")
                .streetNr("237")
                .zipCode("L-1234")
                .country("Luxembourg")
                .emailAddress("newDoctor@email.com")
                .phoneNumber("+352123123123")
                .userId(newUserId)
                .build();
        newDoctor = doctorRepository.save(newDoctor);
        int newDoctorId = newDoctor.getId();

        Assertions.assertTrue(!ObjectUtils.isEmpty(newUser));
        Assertions.assertTrue(!ObjectUtils.isEmpty(newParent1));
        Assertions.assertTrue(!ObjectUtils.isEmpty(newParent2));
        Assertions.assertTrue(!ObjectUtils.isEmpty(newChild));
        Assertions.assertTrue(!ObjectUtils.isEmpty(newActivity));
        Assertions.assertTrue(!ObjectUtils.isEmpty(newActivityTracking));
        Assertions.assertTrue(!ObjectUtils.isEmpty(newDoctor));

        /**
         * Update
         */
        userRepository.updateLogin(newUserId,"newUser2");
        userRepository.updateEmail(newUserId, "user@email2.com");
        userRepository.updatePassword(newUserId, "password2");

        doctorRepository.updateAddress(newDoctorId, "country2", "city2", "zipCode2", "street2", "2");
        doctorRepository.updatePhone(newDoctorId,"+352321321321");
        doctorRepository.updateEmail(newDoctorId, "doctor@email2.com");
        doctorRepository.updateName(newDoctorId,"doctor2");

        parentRepository.updateParentEmail(newParent1Id, "parent@email2.com");
        parentRepository.updateParentFirstName(newParent1Id, "firstName2");
        parentRepository.updateParentLastName(newParent1Id, "LastName2");

        childRepository.updateParent1(newChildId,newParent2Id);
        childRepository.updateFirstName(newChildId,"firstName2");
        childRepository.updateLastName(newChildId,"lastName2");

        activityTrackingRepository.updateActivityRemark(newActivityTrackingId,"remark2");
        activityTrackingRepository.updateActivityTimestamp(newActivityTrackingId, Timestamp.valueOf(LocalDateTime.now()));

        activityRepository.updateName(newActivityId, "name2");

        /**
         * Delete
         */

        activityTrackingRepository.delete(newActivityTracking);
        activityRepository.delete(newActivity);
        childRepository.delete(newChild);
        parentRepository.delete(newParent1);
        parentRepository.delete(newParent2);
        doctorRepository.delete(newDoctor);
        userRepository.delete(newUser);

        Assertions.assertTrue(ObjectUtils.isEmpty(activityTrackingRepository.findById(newActivityTrackingId)));
        Assertions.assertTrue(ObjectUtils.isEmpty(activityRepository.findById(newActivityId)));
        Assertions.assertTrue(ObjectUtils.isEmpty(childRepository.findById(newChildId)));
        Assertions.assertTrue(ObjectUtils.isEmpty(parentRepository.findById(newParent1Id)));
        Assertions.assertTrue(ObjectUtils.isEmpty(parentRepository.findById(newParent2Id)));
        Assertions.assertTrue(ObjectUtils.isEmpty(doctorRepository.findById(newDoctorId)));
        Assertions.assertTrue(ObjectUtils.isEmpty(userRepository.findById(newUserId)));
    }
}
