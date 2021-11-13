package com.sulwep7.childactivitytracking.services;

import com.sulwep7.childactivitytracking.consumer.ParentRepository;
import com.sulwep7.childactivitytracking.consumer.UserRepository;
import com.sulwep7.childactivitytracking.model.Parent;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.rmi.NoSuchObjectException;
import java.security.InvalidParameterException;
import java.util.List;

@Service
public class ParentServiceImpl implements ParentService{

    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Parent> getParents() {
        return parentRepository.findAll();
    }

    @Override
    public Parent getParentById(int id) throws NoSuchObjectException {
        Parent parent = parentRepository.findById(id);

        if(ObjectUtils.isEmpty(parent))
        {
            throw new NoSuchObjectException("No parent found with id : "+id);
        }

        return  parent;
    }

    @Override
    public List<Parent> getParentsByNames(String firstName, String lastName) throws NoSuchObjectException {
        List<Parent> parents = parentRepository.findByName(firstName, lastName);

        if (ObjectUtils.isEmpty(parents)) {
            throw new NoSuchObjectException("No parent found with firstName : "+firstName+" and lastName : "+lastName);
        }

        return parents;
    }

    @Override
    public List<Parent> getParentsByEmail(String email) throws NoSuchObjectException {
        List<Parent> parents = parentRepository.findByEmailAddress(email);

        if (ObjectUtils.isEmpty(parents)) {
            throw new NoSuchObjectException("No parent found with email : "+email);
        }

        return parents;
    }

    @Override
    public List<Parent> getParentsByUserId(int userId) throws NoSuchObjectException {
        List<Parent> parents = parentRepository.findByUserId(userId);

        if (ObjectUtils.isEmpty(parents)) {
            throw new NoSuchObjectException("No parent found with userId : "+userId);
        }

        return parents;
    }

    @Override
    public Parent createParent(int userId, String firstName, String lastName, String email) throws InvalidParameterException{
        //Data quality checks
        boolean isValidFirstName = !StringUtils.isBlank(firstName);
        boolean isValidLastName = !StringUtils.isBlank(lastName);
        boolean isValidEmail = !StringUtils.isBlank(email) && EmailValidator.getInstance().isValid(email);
        boolean isValidUserId = userRepository.existsById(userId);

        if (!isValidFirstName || !isValidLastName ||!isValidEmail)
        {
            throw new InvalidParameterException("Input parameters of service createParent are not valid : "+firstName+", "+lastName+", "+email+" and "+userId);
        }

        Parent parent = Parent.builder()
                .userId(userId)
                .firstName(firstName)
                .lastName(lastName)
                .emailAddress(email)
                .build();

        parent = parentRepository.save(parent);

        return parent;
    }

    @Override
    public void deleteParentById(int id) {
        parentRepository.deleteById(id);
    }

    @Override
    public void deleteParentByName(String firstName, String lastName) {
        parentRepository.deleteByName(firstName, lastName);
    }

    @Override
    public void deleteParentByEmail(String email) {
        parentRepository.deleteByEmail(email);
    }

    @Override
    public void deleteParentByUserId(int userId) {
        parentRepository.deleteByUserId(userId);
    }

    @Override
    public void updateParentFirstName(int id, String firstName) throws NoSuchObjectException, InvalidParameterException {
        //Data quality checks
        boolean isValidFirstName = !StringUtils.isBlank(firstName);
        if(!isValidFirstName) {
            throw new InvalidParameterException("Input parameters of service updateParentFirstName are not valid : "+id+" and "+firstName);
        }
        boolean isValidId = parentRepository.existsById(id);
        if (!isValidId) {
            throw new NoSuchObjectException("No Parent found with id : "+id);
        }

        parentRepository.updateParentFirstName(id, firstName);
    }

    @Override
    public void updateParentLastName(int id, String lastName) throws NoSuchObjectException, InvalidParameterException {
        //Data quality checks
        boolean isValidLastName = !StringUtils.isBlank(lastName);
        if(!isValidLastName) {
            throw new InvalidParameterException("Input parameters of service updateParentFirstName are not valid : "+id+" and "+lastName);
        }
        boolean isValidId = parentRepository.existsById(id);
        if (!isValidId) {
            throw new NoSuchObjectException("No Parent found with id : "+id);
        }

        parentRepository.updateParentLastName(id, lastName);
    }

    @Override
    public void updateParentEmail(int id, String email) throws NoSuchObjectException, InvalidParameterException {
        //Data quality checks
        boolean isValidEmail = !StringUtils.isBlank(email);
        if(!isValidEmail) {
            throw new InvalidParameterException("Input parameters of service updateParentFirstName are not valid : "+id+" and "+email);
        }
        boolean isValidId = parentRepository.existsById(id);
        if (!isValidId) {
            throw new NoSuchObjectException("No Parent found with id : "+id);
        }

        parentRepository.updateParentEmail(id, email);
    }
}
