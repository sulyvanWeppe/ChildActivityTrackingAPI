package com.sulwep7.childactivitytracking.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String emailAddress;
    private String login;
    private String password;

    public User() {}

    public User(int id, String email_address, String login, String password){
        this.id = id;
        this.emailAddress = email_address;
        this.login = login;
        this.password = password;
    }
}