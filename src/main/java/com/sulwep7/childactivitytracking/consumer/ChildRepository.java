package com.sulwep7.childactivitytracking.consumer;

import com.sulwep7.childactivitytracking.model.Child;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

public interface ChildRepository extends CrudRepository<Child, Integer> {

    List<Child> findAll();

    Child findById(int id);

    List<Child> findByLastName(String lastName);

    List<Child> findByFirstName(String firstName);

    @Query("select c from Child c where first_name=:firstName and last_name=:lastName")
    List<Child> findByNames(String firstName, String lastName);

    List<Child> findByBirthDate(Timestamp birthDate);

    List<Child> findByParent1Id(int parent1Id);

    @Query("select c from Child c where parent_2_id=:parent2Id")
    List<Child> findByParent2Id(int parent2Id);

    @Query("select c from Child c where parent_1_id = :parent1Id and parent_2_id = :parent2Id")
    List<Child> findByParentsId(int parent1Id, int parent2Id);

    @Transactional
    @Modifying
    @Query("delete from Child c where first_name=:firstName and last_name=:lastName")
    void deleteByNames(String firstName, String lastName);

    @Transactional
    @Modifying
    @Query("delete from Child c where parent_1_id=:parent1Id and parent_2_id=:parent2Id")
    void deleteByParents(int parent1Id, int parent2Id);

    @Transactional
    @Modifying
    @Query("update Child c set first_name=:firstName where id=:id")
    void updateFirstName(int id, String firstName);

    @Transactional
    @Modifying
    @Query("update Child c set last_name=:lastName where id=:id")
    void updateLastName(int id, String lastName);

    @Transactional
    @Modifying
    @Query("update Child c set parent_1_id=:parent1Id where id=:id")
    void updateParent1(int id, int parent1Id);

    @Transactional
    @Modifying
    @Query("update Child c set parent_2_id=:parent2Id where id=:id")
    void updateParent2(int id, int parent2Id);

    @Transactional
    @Modifying
    @Query("update Child c set first_name=:firstName, last_name=:lastName, parent_1_id=:parent1Id, parent_2_id=:parent2Id, birthDate=:birthDate where id=:id")
    void updateChild(int id, String firstName, String lastName, int parent1Id, int parent2Id, Timestamp birthDate);
}
