package com.sulwep7.childactivitytracking.services;

import com.sulwep7.childactivitytracking.model.*;
import com.sulwep7.childactivitytracking.model.exceptions.AlreadyUsedUserLogin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ObjectUtils;

import java.rmi.NoSuchObjectException;
import java.security.InvalidParameterException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

@SpringBootTest
public class ServicesTest {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    ParentServiceImpl parentService;
    @Autowired
    ChildServiceImpl childService;
    @Autowired
    ActivityServiceImpl activityService;
    @Autowired
    ActivityTrackingServiceImpl activityTrackingService;
    @Autowired
    DoctorServiceImpl doctorService;


    @Test
    public void createThenDelete(){
        /**
         * Creation Step
         * **/
        //User
        String userEmail = "user@email.com";
        String userLogin = "userLogin";
        String userPassword = "userPassword";
        User user = null;
        try {
            user = userService.createUser(userEmail, userLogin, userPassword);
            Assertions.assertTrue(!ObjectUtils.isEmpty(user));
        } catch (AlreadyUsedUserLogin e) {
            e.printStackTrace();
            Assertions.assertTrue(!ObjectUtils.isEmpty(user));
        }

        //Parents
        //Parent1
        int userId = user.getId();
        String parent1FirstName = "parent1FirstName";
        String parent1LastName = "parent1LastName";
        String parent1Email = "parent1@email.com";
        Parent parent1 = null;
        try {
            parent1 = parentService.createParent(userId, parent1FirstName, parent1LastName, parent1Email);
            Assertions.assertTrue(!ObjectUtils.isEmpty(parent1));
        } catch (InvalidParameterException e) {
            Assertions.assertTrue(!ObjectUtils.isEmpty(parent1));
        }
        //Parent2
        String parent2FirstName = "parent2FirstName";
        String parent2LastName = "parent2LastName";
        String parent2Email = "parent2@email.com";
        Parent parent2 = null;
        try {
            parent2 = parentService.createParent(userId, parent2FirstName, parent2LastName, parent2Email);
            Assertions.assertTrue(!ObjectUtils.isEmpty(parent2));
        } catch (InvalidParameterException e) {
            Assertions.assertTrue(!ObjectUtils.isEmpty(parent2));
        }

        //Child
        int parent1Id = parent1.getId();
        int parent2Id = parent2.getId();
        String childFirstName = "childFirstName";
        String childLastName = "childLastName";
        Timestamp childBirthDate = Timestamp.from(Instant.now());
        Child child = null;
        try {
            child = childService.createChild(childFirstName, childLastName, parent1Id, parent2Id, childBirthDate);
            Assertions.assertTrue(!ObjectUtils.isEmpty(child));
        } catch (InvalidParameterException e) {
            Assertions.assertTrue(!ObjectUtils.isEmpty(child));
        }

        //Activity
        String activityName = "activityName";
        Activity activity = null;
        try {
            activity = activityService.createActivity(activityName);
            Assertions.assertTrue(!ObjectUtils.isEmpty(activity));
        }catch (InvalidParameterException e) {
            Assertions.assertTrue(!ObjectUtils.isEmpty(activity));
        }

        //ActivityTracking
        int activityId = activity.getId();
        int childId = child.getId();
        String activityRemark = "activityRemark";
        Timestamp activityTimestamp = Timestamp.from(Instant.now());
        ActivityTracking activityTracking = null;
        try {
            activityTracking = activityTrackingService.createActivityTracking(childId,activityId,activityTimestamp,activityRemark);
            Assertions.assertTrue(!ObjectUtils.isEmpty(activityTracking));
        }catch (InvalidParameterException e) {
            Assertions.assertTrue(!ObjectUtils.isEmpty(activityTracking));
        }

        //Doctor
        String doctorName = "doctorName";
        String doctorEmail = "doctor@email.com";
        String doctorPhone = "+123456789";
        String doctorCity = "Luxembourg";
        String doctorStreet = "Rue de la liberté";
        String doctorNr = "10";
        String doctorZipCode = "12345";
        String doctorCountry = "France";
        Doctor doctor = null;
        try {
            doctor = doctorService.createDoctor(userId,doctorName,doctorEmail,doctorPhone,doctorCity,doctorStreet,doctorNr,doctorZipCode,doctorCountry);
            Assertions.assertTrue(!ObjectUtils.isEmpty(doctor));
        }catch (InvalidParameterException e) {
            Assertions.assertTrue(!ObjectUtils.isEmpty(doctor));
        }

        /**
         * Deletion Step
         * **/
        doctorService.deleteDoctorById(doctor.getId());
        activityTrackingService.deleteActivityTrackingById(activityTracking.getId());
        activityService.deleteActivityById(activityId);
        childService.deleteChildById(childId);
        parentService.deleteParentById(parent1Id);
        parentService.deleteParentById(parent2Id);
        userService.deleteUserById(userId);

        ActivityTracking deletedActivityTracking = null;
        try {
            deletedActivityTracking = activityTrackingService.getActivityTrackingById(activityTracking.getId());
            Assertions.assertTrue(ObjectUtils.isEmpty(deletedActivityTracking));
        } catch (NoSuchObjectException e) {
            Assertions.assertTrue(true);

        }
        Activity deletedActivity = null;
        try {
            deletedActivity = activityService.getActivityById(activityId);
            Assertions.assertTrue(ObjectUtils.isEmpty(deletedActivity));
        } catch (NoSuchObjectException e) {
            Assertions.assertTrue(true);
        }
        Child deletedChild;
        try {
            deletedChild = childService.getChildById(childId);
            Assertions.assertTrue(ObjectUtils.isEmpty(deletedChild));
        } catch (NoSuchObjectException e) {
            Assertions.assertTrue(true);
        }
        Parent deletedParent1;
        try {
            deletedParent1 = parentService.getParentById(parent1Id);
            Assertions.assertTrue(ObjectUtils.isEmpty(deletedParent1));
        } catch (NoSuchObjectException e) {
            Assertions.assertTrue(true);
        }
        Parent deletedParent2;
        try {
            deletedParent2 = parentService.getParentById(parent2Id);
            Assertions.assertTrue(ObjectUtils.isEmpty(deletedParent2));
        } catch (NoSuchObjectException e) {
            Assertions.assertTrue(true);
        }
        Doctor deletedDoctor;
        try {
            deletedDoctor = doctorService.getDoctorById(doctor.getId());
            Assertions.assertTrue(ObjectUtils.isEmpty(deletedDoctor));
        } catch (NoSuchObjectException e) {
            Assertions.assertTrue(true);
        }
        User deletedUser;
        try {
            deletedUser = userService.getUserById(userId);
            Assertions.assertTrue(ObjectUtils.isEmpty(deletedUser));
        } catch (NoSuchObjectException e) {
            Assertions.assertTrue(true);
        }
    }
}
