package com.sulwep7.childactivitytracking.services;

import com.sulwep7.childactivitytracking.model.Child;

import java.rmi.NoSuchObjectException;
import java.security.InvalidParameterException;
import java.sql.Timestamp;
import java.util.List;
import java.util.prefs.NodeChangeEvent;

public interface ChildService {

    public List<Child> getChildren();

    public Child getChildById(int id) throws NoSuchObjectException;

    public List<Child> getChildrenByNames(String firstName, String lastName) throws NoSuchObjectException;

    public List<Child> getChildrenByParent1Id(int parent1Id) throws NoSuchObjectException;

    public List<Child> getChildrenByParent2Id(int parent2Id) throws NoSuchObjectException;

    public List<Child> getChildrenByParentsId(int parent1Id, int parent2Id) throws NoSuchObjectException;

    public Child createChild(String firstName, String lastName, int parent1Id, int parent2Id, Timestamp birthDate) throws InvalidParameterException;

    public void deleteChildById(int id);

    public void deleteChildByNames(String firstName, String lastName);

    public void deleteChildByParentsId(int parent1Id, int parent2Id);

    public void updateChildFirstName(int id, String firstName) throws NoSuchObjectException, InvalidParameterException;

    public void updateChildLastName(int id, String lastName) throws NoSuchObjectException, InvalidParameterException;

    public void updateChildParent1(int id, int parent1Id) throws NoSuchObjectException, InvalidParameterException;

    public void updateChildParent2(int id, int parent2Id) throws NoSuchObjectException, InvalidParameterException;

    public void updateChild(Child child) throws NoSuchObjectException, InvalidParameterException;
}
