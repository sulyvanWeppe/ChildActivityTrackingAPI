package com.sulwep7.childactivitytracking.services;

import com.sulwep7.childactivitytracking.consumer.UserRepository;
import com.sulwep7.childactivitytracking.model.User;
import com.sulwep7.childactivitytracking.model.exceptions.AlreadyUsedUserLogin;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.rmi.NoSuchObjectException;
import java.security.InvalidParameterException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Override
    public User createUser(String emailAddress, String login, String password) throws InvalidParameterException, AlreadyUsedUserLogin {
        //Data quality checks
        boolean isValidEmailAddress = !StringUtils.isBlank(emailAddress) && EmailValidator.getInstance().isValid(emailAddress);
        boolean isValidLogin = !StringUtils.isBlank(login);
        boolean isValidPassword = !StringUtils.isBlank(password);
        if (!isValidEmailAddress || !isValidLogin || !isValidPassword)
        {
            throw new InvalidParameterException("Input parameters of service createUser are not valid : "+emailAddress+", "+login+" and "+password);
        }
        boolean alreadyUsedLogin = !ObjectUtils.isEmpty(userRepository.findByLogin(login));
        if (alreadyUsedLogin)
        {
            throw new AlreadyUsedUserLogin();
        }

        //Create new User POJO
        User newUser = User.builder()
                .login(login)
                .password(password)
                .emailAddress(emailAddress)
                .build();
        //Save it in the DB
        newUser = userRepository.save(newUser);

        return newUser;
    }

    @Override
    public User getUserById(int userId) throws NoSuchObjectException{
        User user = userRepository.findById(userId);

        if (ObjectUtils.isEmpty(user)) {
            throw new NoSuchObjectException("No user found with id : " + userId);
        }

        return user;
    }

    @Override
    public User getUserByEmailAddress(String emailAddress) throws NoSuchObjectException{
        User user = userRepository.findByEmailAddress(emailAddress);

        if (ObjectUtils.isEmpty(user)) {
            throw new NoSuchObjectException("No user found with emailAddress : " + emailAddress);
        }

        return user;
    }

    @Override
    public User getUserByLogin(String login) throws NoSuchObjectException{
        User user = userRepository.findByLogin(login);

        if (ObjectUtils.isEmpty(user)) {
            throw new NoSuchObjectException("No user found with login : " + login);
        }

        return user;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = userRepository.findAll();

        return users;
    }

    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteUserByLogin(String login) {
        userRepository.deleteByLogin(login);
    }

    @Override
    public void deleteUserByEmail(String email)
    {
        userRepository.deleteByEmail(email);
    }

    @Override
    public void updateUserLogin(int id, String login) throws AlreadyUsedUserLogin, InvalidParameterException, NoSuchObjectException {
        //Data quality checks
        boolean isValidLogin = !StringUtils.isBlank(login);
        if (!isValidLogin) {
            throw new InvalidParameterException("Input parameters of service updateUserLogin are not valid : "+id+" and "+login);
        }
        boolean isValidId = userRepository.existsById(id);
        if (!isValidId) {
            throw new NoSuchObjectException("No user found with id : "+id);
        }
        boolean alreadyUsedLogin = !ObjectUtils.isEmpty(userRepository.findByLogin(login));
        if (alreadyUsedLogin) {
            throw new AlreadyUsedUserLogin();
        }

        userRepository.updateLogin(id, login);
    }

    @Override
    public void updateUserPassword(int id, String password) throws InvalidParameterException, NoSuchObjectException {
        //Data quality checks
        boolean isValidPassword = !StringUtils.isBlank(password);
        if (!isValidPassword) {
            throw new InvalidParameterException("Input parameters of service updateUserLogin are not valid : "+id+" and "+password);
        }
        boolean isValidId = userRepository.existsById(id);
        if (!isValidId) {
            throw new NoSuchObjectException("No user found with id : "+id);
        }
        userRepository.updateLogin(id, password);
    }

    @Override
    public void updateUserEmail(int id, String email) throws InvalidParameterException, NoSuchObjectException {
        //Data quality checks
        boolean isValidEmail = !StringUtils.isBlank(email) && EmailValidator.getInstance().isValid(email);
        if (!isValidEmail) {
            throw new InvalidParameterException("Input parameters of service updateUserLogin are not valid : "+id+" and "+email);
        }
        boolean isValidId = userRepository.existsById(id);
        if (!isValidId) {
            throw new NoSuchObjectException("No user found with id : "+id);
        }

        userRepository.updateLogin(id, email);
    }

    @Override
    public void updateUser(User user) throws NoSuchObjectException, InvalidParameterException {
        //Data quality checks
        int id = user.getId();
        String email = user.getEmailAddress();
        String login = user.getLogin();
        String password = user.getPassword();

        boolean isValidLogin = !StringUtils.isBlank(login);
        if (!isValidLogin) {
            throw new InvalidParameterException("Input parameters of service updateUserLogin are not valid : "+id+" and "+login);
        }
        boolean isValidPassword = !StringUtils.isBlank(password);
        if (!isValidPassword) {
            throw new InvalidParameterException("Input parameters of service updateUserLogin are not valid : "+id+" and "+password);
        }
        boolean isValidEmail = !StringUtils.isBlank(email) && EmailValidator.getInstance().isValid(email);
        if (!isValidEmail) {
            throw new InvalidParameterException("Input parameters of service updateUserLogin are not valid : "+id+" and "+email);
        }
        boolean isValidId = userRepository.existsById(id);
        if (!isValidId) {
            throw new NoSuchObjectException("No user found with id : "+id);
        }

        userRepository.updateUser(id, login, password, email);
    }

}