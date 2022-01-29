package com.sulwep7.childactivitytracking.controllers;

import com.sulwep7.childactivitytracking.model.Doctor;
import com.sulwep7.childactivitytracking.services.DoctorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.rmi.NoSuchObjectException;
import java.security.InvalidParameterException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/")
public class DoctorController {

    @Autowired
    DoctorServiceImpl doctorService;

    @GetMapping(value = "/doctor", produces = "application/json")
    public List<Doctor> getDoctors() {
        log.info("Controller - GET - /doctor");
        return doctorService.getDoctors();
    }

    @GetMapping(value = "/doctor/{id}", produces = "application/json")
    public Doctor getDoctorById(@PathVariable int id) {
        try {
            log.info("Controller - GET - /doctor/{id}", id);
            return doctorService.getDoctorById(id);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value = "/doctor/userid/{userid}", produces = "application/json")
    public List<Doctor> getDoctorsByUserId(@PathVariable int userid) {
        try {
            log.info("Controller - GET - /doctor/userid/{}",userid);
            return doctorService.getDoctorsByUserId(userid);
        } catch (NoSuchObjectException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(value="/doctor", produces = "application/json")
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        Doctor newDoctor = null;
        try {
            log.info("Controller - POST - /doctor with input doctor {}",doctor);
            newDoctor = doctorService.createDoctor(doctor.getUserId(),doctor.getName(), doctor.getEmailAddress(),doctor.getPhoneNr(), doctor.getCity(), doctor.getStreet(), doctor.getStreetNr(), doctor.getZipCode(), doctor.getCountry());
        } catch(InvalidParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return newDoctor;
    }

    @PutMapping(value="/doctor", produces = "application/json")
    public Doctor updateDoctor(@RequestBody Doctor doctor) {
        try {
            log.info("Controller - PUT - /doctor with input doctor {}",doctor);
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
        try {
            log.info("Controller - DELETE - /doctor/{}",id);
            doctorService.deleteDoctorById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value="/doctor/userid/{userid}", produces = "application/json")
    public ResponseEntity deleteDoctorByUser(@PathVariable int userid) {
        try {
            log.info("Controller - DELETE - /doctor/userid/{}",userid);
            doctorService.deleteDoctorByUserId(userid);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
