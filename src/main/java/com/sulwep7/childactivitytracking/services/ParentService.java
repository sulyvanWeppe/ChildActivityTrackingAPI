package com.sulwep7.childactivitytracking.services;

import com.sulwep7.childactivitytracking.model.Parent;

import java.rmi.NoSuchObjectException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.NoSuchElementException;

public interface ParentService {

    public List<Parent> getParents();

    public Parent getParentById(int id) throws NoSuchObjectException;

    public List<Parent> getParentsByNames(String firstName, String lastName) throws NoSuchObjectException;

    public List<Parent> getParentsByEmail(String email) throws NoSuchObjectException;

    public List<Parent> getParentsByUserId(int userId) throws NoSuchObjectException;

    public Parent createParent(int userId, String firstName, String lastName, String email) throws InvalidParameterException;

    public void deleteParentById(int id);

    public void deleteParentByName(String firstName, String lastName);

    public void deleteParentByEmail(String email);

    public void deleteParentByUserId(int userId);

    public void updateParentFirstName(int id, String firstName) throws NoSuchObjectException, InvalidParameterException;

    public void updateParentLastName(int id, String lastName) throws NoSuchObjectException, InvalidParameterException;

    public void updateParentEmail(int id, String email) throws NoSuchObjectException, InvalidParameterException;

    public void updateParent(Parent parent) throws NoSuchObjectException, InvalidParameterException;
}
