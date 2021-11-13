package com.sulwep7.childactivitytracking.services;

import com.sulwep7.childactivitytracking.model.Child;
import com.sulwep7.childactivitytracking.model.Doctor;

import javax.print.Doc;
import java.rmi.NoSuchObjectException;
import java.security.InvalidParameterException;
import java.util.List;

public interface DoctorService {

    public List<Doctor> getDoctors();

    public Doctor getDoctorById(int id) throws NoSuchObjectException;

    public List<Doctor> getDoctorsByName(String name) throws NoSuchObjectException;

    public List<Doctor> getDoctorsByAddress(String city, String street, String nr, String zipCode, String country) throws NoSuchObjectException;

    public Doctor getDoctorByEmail(String email) throws NoSuchObjectException;

    public List<Doctor> getDoctorsByUserId(int userId) throws NoSuchObjectException;

    public Doctor createDoctor(int userId, String name, String email, String phoneNr, String city, String street, String nr, String zipCode, String country) throws InvalidParameterException;

    public void deleteDoctorById(int id);

    public void deleteDoctorByName(String name);

    public void deleteDoctorByEmail(String email);

    public void deleteDoctorByUserId(int userId);

    public void updateDoctorName(int id, String name) throws NoSuchObjectException, InvalidParameterException;

    public void updateDoctorEmail(int id, String email) throws NoSuchObjectException, InvalidParameterException;

    public void updateDoctorPhone(int id, String phone) throws NoSuchObjectException, InvalidParameterException;

    public void updateDoctorAddress(int id, String country, String city, String zipCode, String street, String nr) throws NoSuchObjectException, InvalidParameterException;

    public void updateDoctor(Doctor doctor) throws NoSuchObjectException, InvalidParameterException;
}
