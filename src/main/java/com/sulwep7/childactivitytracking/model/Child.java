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
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private int parent1Id;
    private int parent2Id;

    public Child() {}

    public Child(int id, String first_name, String last_name, int age, int parent_1_id, int parent_2_id) {
        this.id = id;
        this.firstName = first_name;
        this.lastName = last_name;
        this.age = age;
        this.parent1Id = parent_1_id;
        this.parent2Id = parent_2_id;
    }
}
