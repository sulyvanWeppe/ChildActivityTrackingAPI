package com.sulwep7.childactivitytracking.services;

import com.sulwep7.childactivitytracking.model.User;
import com.sulwep7.childactivitytracking.model.exceptions.AlreadyUsedUserLogin;
import java.rmi.NoSuchObjectException;
import java.security.InvalidParameterException;
import java.util.List;

public interface UserService {

    public User createUser(String emailAddress, String login, String password) throws AlreadyUsedUserLogin, InvalidParameterException;

    public User getUserById(int userId) throws NoSuchObjectException;

    public User getUserByEmailAddress(String emailAddress) throws NoSuchObjectException;

    public User getUserByLogin(String login) throws NoSuchObjectException;

    public List<User> getUsers();

    public void deleteUserById(int id);

    public void deleteUserByLogin(String login);

    public void deleteUserByEmail(String email);

    public void updateUserLogin(int id, String login) throws AlreadyUsedUserLogin, InvalidParameterException, NoSuchObjectException;

    public void updateUserPassword(int id, String password) throws InvalidParameterException, NoSuchObjectException;

    public void updateUserEmail(int id, String email) throws InvalidParameterException, NoSuchObjectException;

    public void updateUser(User user) throws NoSuchObjectException, InvalidParameterException;
}
