package com.sulwep7.childactivitytracking.services;

import com.sulwep7.childactivitytracking.consumer.ChildRepository;
import com.sulwep7.childactivitytracking.consumer.ParentRepository;
import com.sulwep7.childactivitytracking.model.Child;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.rmi.NoSuchObjectException;
import java.security.InvalidParameterException;
import java.util.List;

@Service
public class ChildServiceImpl implements ChildService{

    @Autowired
    private ChildRepository childRepository;
    @Autowired
    private ParentServiceImpl parentService;
    @Autowired
    private ParentRepository parentRepository;

    @Override
    public List<Child> getChildren() {
        List<Child> children = childRepository.findAll();

        return children;
    }

    @Override
    public Child getChildById(int id) throws NoSuchObjectException {
        Child child = childRepository.findById(id);

        if (ObjectUtils.isEmpty(child)){
            throw new NoSuchObjectException("No child found with id : "+id);
        }

        return child;
    }

    @Override
    public List<Child> getChildrenByNames(String firstName, String lastName) throws NoSuchObjectException{
        List<Child> children = childRepository.findByNames(firstName, lastName);

        if (ObjectUtils.isEmpty(children)) {
            throw new NoSuchObjectException("No child found with firstName : "+firstName+" and lastName : "+lastName);
        }

        return children;
    }

    @Override
    public List<Child> getChildrenByParent1Id(int parent1Id) throws NoSuchObjectException {
        List<Child> children = childRepository.findByParent1Id(parent1Id);

        if (ObjectUtils.isEmpty(children)) {
            throw new NoSuchObjectException("No child found with parent1Id : "+parent1Id);
        }

        return children;
    }

    @Override
    public List<Child> getChildrenByParent2Id(int parent2Id) throws NoSuchObjectException {
        List<Child> children = childRepository.findByParent2Id(parent2Id);

        if(ObjectUtils.isEmpty(children)) {
            throw new NoSuchObjectException("No child found with parent2Id : "+parent2Id);
        }

        return children;
    }

    @Override
    public List<Child> getChildrenByParentsId(int parent1Id, int parent2Id) throws NoSuchObjectException{
        List<Child> children = childRepository.findByParentsId(parent1Id, parent2Id);

        if(ObjectUtils.isEmpty(children)) {
            throw new NoSuchObjectException("No child found with parent1Id : "+parent1Id+" and parent2Id : "+parent2Id);
        }

        return children;
    }

    @Override
    public Child createChild(String firstName, String lastName, int parent1Id, int parent2Id, int age) throws InvalidParameterException {
        //Data quality checks
        boolean isValidFirstName = !StringUtils.isBlank(firstName);
        boolean isValidLastName = !StringUtils.isBlank(lastName);
        boolean isValidAge = age >= 0 && age <= (18*12); //Application is designed for children => under 18
        boolean areValidParentsId = parentRepository.existsById(parent1Id) && parentRepository.existsById(parent2Id);

        if (!isValidAge || !isValidFirstName || !isValidLastName || !areValidParentsId) {
            throw new InvalidParameterException("Input parameters of service createChild are not valid : "+firstName+", "+lastName+", "+age+", "+parent1Id+" and "+parent2Id);
        }

        Child child = Child.builder()
                .firstName(firstName)
                .lastName(lastName)
                .parent1Id(parent1Id)
                .parent2Id(parent2Id)
                .age(age)
                .build();
        child = childRepository.save(child);

        return child;
    }

    @Override
    public void deleteChildById(int id) {
        childRepository.deleteById(id);
    }

    @Override
    public void deleteChildByNames(String firstName, String lastName) {
        childRepository.deleteByNames(firstName, lastName);
    }

    @Override
    public void deleteChildByParentsId(int parent1Id, int parent2Id) {
        childRepository.deleteByParents(parent1Id, parent2Id);
    }

    @Override
    public void updateChildFirstName(int id, String firstName) throws NoSuchObjectException, InvalidParameterException {
        //Data quality checks
        boolean isValidFirstName = !StringUtils.isBlank(firstName);
        if (!isValidFirstName) {
            throw new InvalidParameterException("Input parameters for service updateChildFirstName are not valid : "+id+" and "+firstName);
        }
        boolean isValidId = childRepository.existsById(id);
        if (!isValidId) {
            throw new NoSuchObjectException("No child found with id : "+id);
        }

        childRepository.updateFirstName(id, firstName);
    }

    @Override
    public void updateChildLastName(int id, String lastName) throws NoSuchObjectException, InvalidParameterException {
        //Data quality checks
        boolean isValidLastName = !StringUtils.isBlank(lastName);
        if (!isValidLastName) {
            throw new InvalidParameterException("Input parameters for service updateChildFirstName are not valid : "+id+" and "+lastName);
        }
        boolean isValidId = childRepository.existsById(id);
        if (!isValidId) {
            throw new NoSuchObjectException("No child found with id : "+id);
        }

        childRepository.updateLastName(id, lastName);
    }

    @Override
    public void updateChildParent1(int id, int parent1Id) throws NoSuchObjectException, InvalidParameterException {
        //Data quality checks
        boolean isValidParent1Id = parentRepository.existsById(parent1Id);
        if (!isValidParent1Id) {
            throw new InvalidParameterException("Input parameters for service updateChildFirstName are not valid : "+id+" and "+parent1Id);
        }
        boolean isValidId = childRepository.existsById(id);
        if (!isValidId) {
            throw new NoSuchObjectException("No child found with id : "+id);
        }

        childRepository.updateParent1(id, parent1Id);
    }

    @Override
    public void updateChildParent2(int id, int parent2Id) throws NoSuchObjectException, InvalidParameterException {
        //Data quality checks
        boolean isValidParent2Id = parentRepository.existsById(parent2Id);
        if (!isValidParent2Id) {
            throw new InvalidParameterException("Input parameters for service updateChildFirstName are not valid : "+id+" and "+parent2Id);
        }
        boolean isValidId = childRepository.existsById(id);
        if (!isValidId) {
            throw new NoSuchObjectException("No child found with id : "+id);
        }

        childRepository.updateParent2(id, parent2Id);
    }

    @Override
    public void updateChild(Child child) throws NoSuchObjectException, InvalidParameterException {
        int id = child.getId();
        String firstName = child.getFirstName();
        String lastName = child.getLastName();
        int parent1Id = child.getParent1Id();
        int parent2Id = child.getParent2Id();
        int age = child.getAge();

        //Data quality checks
        boolean isValidFirstName = !StringUtils.isBlank(firstName);
        boolean isValidLastName = !StringUtils.isBlank(lastName);
        boolean isValidParent1Id = parentRepository.existsById(parent1Id);
        boolean isValidParent2Id = parentRepository.existsById(parent2Id);
        boolean isValidAge = age >= 0 && age <= (18*12); //Application is designed for children => under 18
        if (!isValidFirstName || !isValidLastName ||!isValidParent1Id || !isValidParent2Id || !isValidAge) {
            throw new InvalidParameterException("Input parameters for service updateChildFirstName are not valid : "+id+", "+firstName+", "+lastName+", "+parent1Id+", "+parent2Id+" and "+age);
        }
        boolean isValidId = childRepository.existsById(id);
        if (!isValidId) {
            throw new NoSuchObjectException("No child found with id : "+id);
        }

        childRepository.updateChild(id, firstName, lastName, parent1Id, parent2Id, age);
    }
}
