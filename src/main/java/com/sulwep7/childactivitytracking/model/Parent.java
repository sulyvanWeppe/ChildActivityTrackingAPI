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
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int userId;
    private String firstName;
    private String lastName;
    private String emailAddress;

    public Parent() {}

    public Parent(int id, int user_id, String first_name, String last_name, String email_address) {
        this.id = id;
        this.userId = user_id;
        this.firstName = first_name;
        this.lastName = last_name;
        this.emailAddress = email_address;
    }
}
