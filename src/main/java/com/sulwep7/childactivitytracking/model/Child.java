package com.sulwep7.childactivitytracking.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Builder
@Entity
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String firstName;
    private String lastName;
    private Timestamp birthDate;
    @Column(name = "PARENT_1_ID")
    private int parent1Id;
    @Column(name = "PARENT_2_ID")
    private int parent2Id;

    public Child() {}

    public Child(int id, String first_name, String last_name, Timestamp birthDate, int parent_1_id, int parent_2_id) {
        this.id = id;
        this.firstName = first_name;
        this.lastName = last_name;
        this.birthDate = birthDate;
        this.parent1Id = parent_1_id;
        this.parent2Id = parent_2_id;
    }
}
