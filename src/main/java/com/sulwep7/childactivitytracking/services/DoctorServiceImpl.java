package com.sulwep7.childactivitytracking.services;

import com.sulwep7.childactivitytracking.consumer.DoctorRepository;
import com.sulwep7.childactivitytracking.model.Doctor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.rmi.NoSuchObjectException;
import java.security.InvalidParameterException;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService{

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private UserServiceImpl userService;

    @Override
    public List<Doctor> getDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();

        return doctors;
    }

    @Override
    public Doctor getDoctorById(int id) throws NoSuchObjectException {
        Doctor doctor = doctorRepository.findById(id);

        if (ObjectUtils.isEmpty(doctor)) {
            throw new NoSuchObjectException("No doctor found with id : "+id);
        }

        return doctor;
    }

    @Override
    public List<Doctor> getDoctorsByName(String name) throws NoSuchObjectException {
        List<Doctor> doctors = doctorRepository.findByName(name);

        if(ObjectUtils.isEmpty(doctors)) {
            throw new NoSuchObjectException("No doctor found with name : "+name);
        }

        return doctors;
    }

    @Override
    public List<Doctor> getDoctorsByAddress(String city, String street, String nr, String zipCode, String country) throws NoSuchObjectException {
        List<Doctor> doctors = doctorRepository.findByAddress(city, street, nr, zipCode, country);

        if (ObjectUtils.isEmpty(doctors)) {
            throw new NoSuchObjectException("No doctor found with address : "+nr+" "+street+", "+city+" "+zipCode+", "+country);
        }

        return doctors;
    }

    @Override
    public Doctor getDoctorByEmail(String email) throws NoSuchObjectException {
        Doctor doctor = doctorRepository.findByEmailAddress(email);

        if(ObjectUtils.isEmpty(doctor)) {
            throw new NoSuchObjectException("No doctor found with email : "+email);
        }

        return doctor;
    }

    @Override
    public List<Doctor> getDoctorsByUserId(int userId) throws NoSuchObjectException {
        List<Doctor> doctors = doctorRepository.findByUserId(userId);

        if (ObjectUtils.isEmpty(doctors)) {
            throw new NoSuchObjectException("No doctor found with userId : "+userId);
        }

        return doctors;
    }

    @Override
    public Doctor createDoctor(int userId, String name, String email, String phoneNr, String city, String street, String nr, String zipCode, String country) throws InvalidParameterException {
        //Data quality checks
        boolean isValidName = !StringUtils.isBlank(name);
        boolean isValidAddress = !StringUtils.isBlank(city) && !StringUtils.isBlank(street) && !StringUtils.isBlank(nr) && !StringUtils.isBlank(zipCode) && !StringUtils.isBlank(country);
        boolean isValidEmail = !StringUtils.isBlank(email) && EmailValidator.getInstance().isValid(email);
        boolean isValidPhone = !StringUtils.isBlank(phoneNr);
        boolean isValidUserId = false;
        try {
            isValidUserId = !ObjectUtils.isEmpty(userService.getUserById(userId));
        } catch(NoSuchObjectException e) {}

        if (!isValidName || !isValidAddress || !isValidEmail || !isValidPhone || !isValidUserId) {
            throw new InvalidParameterException("Input parameters of service createDoctor are not valid : "+userId+", "+name+", "+email+", "+phoneNr+", "+nr+" "+street+" "+city+" "+zipCode+" "+country);
        }

        Doctor doctor = Doctor.builder()
                .userId(userId)
                .name(name)
                .phoneNumber(phoneNr)
                .emailAddress(email)
                .streetNr(nr)
                .street(street)
                .zipCode(zipCode)
                .city(city)
                .country(country)
                .build();
        doctor = doctorRepository.save(doctor);

        return doctor;
    }

    @Override
    public void deleteDoctorById(int id) {
        doctorRepository.deleteById(id);
    }

    @Override
    public void deleteDoctorByName(String name) {
        doctorRepository.deleteByName(name);
    }

    @Override
    public void deleteDoctorByEmail(String email) {
        doctorRepository.deleteByEmail(email);
    }

    @Override
    public void deleteDoctorByUserId(int userId) {
        doctorRepository.deleteByUserId(userId);
    }

    @Override
    public void updateDoctorName(int id, String name) throws NoSuchObjectException, InvalidParameterException {
        //Data quality checks
        boolean isValidName = !StringUtils.isBlank(name);
        if(!isValidName) {
            throw new InvalidParameterException("Input parameters for service updateDoctorName are not valid : "+id+" and "+name);
        }
        boolean isValidId = doctorRepository.existsById(id);
        if (!isValidId) {
            throw new NoSuchObjectException("No doctor found with id : "+id);
        }

        doctorRepository.updateName(id, name);
    }

    @Override
    public void updateDoctorEmail(int id, String email) throws NoSuchObjectException, InvalidParameterException {
        //Data quality checks
        boolean isValidEmail = !StringUtils.isBlank(email);
        if(!isValidEmail) {
            throw new InvalidParameterException("Input parameters for service updateDoctorName are not valid : "+id+" and "+email);
        }
        boolean isValidId = doctorRepository.existsById(id);
        if (!isValidId) {
            throw new NoSuchObjectException("No doctor found with id : "+id);
        }

        doctorRepository.updateEmail(id, email);
    }

    @Override
    public void updateDoctorPhone(int id, String phone) throws NoSuchObjectException, InvalidParameterException {
        //Data quality checks
        boolean isValidPhone = !StringUtils.isBlank(phone);
        if(!isValidPhone) {
            throw new InvalidParameterException("Input parameters for service updateDoctorName are not valid : "+id+" and "+phone);
        }
        boolean isValidId = doctorRepository.existsById(id);
        if (!isValidId) {
            throw new NoSuchObjectException("No doctor found with id : "+id);
        }

        doctorRepository.updatePhone(id, phone);
    }

    @Override
    public void updateDoctorAddress(int id, String country, String city, String zipCode, String street, String nr) throws NoSuchObjectException, InvalidParameterException {
        //Data quality checks
        boolean isValidAddress = !StringUtils.isBlank(country)
                                && !StringUtils.isBlank(city)
                                && !StringUtils.isBlank(zipCode)
                                && !StringUtils.isBlank(street)
                                && !StringUtils.isBlank(nr);
        if(!isValidAddress) {
            throw new InvalidParameterException("Input parameters for service updateDoctorName are not valid : "+id+" and "+nr+" "+street+", "+zipCode+" "+city+", "+country);
        }
        boolean isValidId = doctorRepository.existsById(id);
        if (!isValidId) {
            throw new NoSuchObjectException("No doctor found with id : "+id);
        }

        doctorRepository.updateAddress(id, country, city, zipCode, street, nr);
    }
}
