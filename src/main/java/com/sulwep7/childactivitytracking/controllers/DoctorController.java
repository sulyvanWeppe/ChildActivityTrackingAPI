package com.sulwep7.childactivitytracking.controllers;

import com.sulwep7.childactivitytracking.model.Doctor;
import com.sulwep7.childactivitytracking.services.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.print.Doc;
import java.rmi.NoSuchObjectException;
import java.security.InvalidParameterException;
import java.util.List;

@RestController
@RequestMapping("/")
public class DoctorController {

    @Autowired
    DoctorServiceImpl doctorService;

    @GetMapping(value = "/doctor", produces = "application/json")
    public List<Doctor> getDoctors() {
        return doctorService.getDoctors();
    }

    @GetMapping(value = "/doctor/{id}", produces = "application/json")
    public Doctor getDoctorById(@PathVariable int id) {
        try {
            return doctorService.getDoctorById(id);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value = "/doctor/{userid}", produces = "application/json")
    public List<Doctor> getDoctorsByUserId(@PathVariable int userid) {
        try {
            return doctorService.getDoctorsByUserId(userid);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(value="/doctor", produces = "application/json")
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        Doctor newDoctor = null;
        try {
            newDoctor = doctorService.createDoctor(doctor.getUserId(),doctor.getName(), doctor.getEmailAddress(),doctor.getPhoneNr(), doctor.getCity(), doctor.getStreet(), doctor.getStreetNr(), doctor.getZipCode(), doctor.getCountry());
        } catch(InvalidParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return newDoctor;
    }

    @PutMapping(value="/doctor", produces = "application/json")
    public Doctor updateDoctor(@RequestBody Doctor doctor) {
        try {
            doctorService.updateDoctor(doctor);
            return doctorService.getDoctorById(doctor.getId());
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch(InvalidParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping(value="/doctor/{id}", produces = "application/json")
    public ResponseEntity deleteDoctorById(@PathVariable int id) {
        doctorService.deleteDoctorById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value="/doctor/{userid}", produces = "application/json")
    public ResponseEntity deleteDoctorByUser(@PathVariable int userid) {
        doctorService.deleteDoctorByUserId(userid);
        return new ResponseEntity(HttpStatus.OK);
    }
}
