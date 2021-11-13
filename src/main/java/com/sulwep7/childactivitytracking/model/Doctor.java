package com.sulwep7.childactivitytracking.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int userId;
    private String name;
    private String emailAddress;
    private String phoneNr;
    private String city;
    private String street;
    private String streetNr;
    private String zipCode;
    private String country;

    public Doctor() {};

    public Doctor(int id, int user_id, String name, String email_address, String phone_number, String city, String street, String street_nr, String zip_code, String country) {
        this.id = id;
        this.userId = user_id;
        this.name = name;
        this.emailAddress = email_address;
        this.phoneNr = phone_number;
        this.city = city;
        this.street = street;
        this.streetNr = street_nr;
        this.zipCode = zip_code;
        this.country = country;
    }
}
