package com.sulwep7.childactivitytracking.consumer;

import com.sulwep7.childactivitytracking.model.Parent;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ParentRepository extends CrudRepository<Parent, Integer> {

    List<Parent> findAll();

    Parent findById(int id);

    List<Parent> findByUserId(int userId);

    List<Parent> findByFirstName(String firstName);

    List<Parent> findByLastName(String lastName);

    @Query("select p from Parent p where first_name=:firstName and last_name=:lastName")
    List<Parent> findByName(@Param("firstName") String firstName,@Param("lastName") String lastName);

    List<Parent> findByEmailAddress(String emailAddress);

    @Transactional
    @Modifying
    @Query("delete from Parent p where first_name=:firstName and last_name=:lastName")
    void deleteByName(String firstName, String lastName);

    @Transactional
    @Modifying
    @Query("delete from Parent p where email_address = :email")
    void deleteByEmail(String email);

    @Transactional
    @Modifying
    @Query("delete from Parent p where user_id = :userId")
    void deleteByUserId(int userId);

    @Transactional
    @Modifying
    @Query("update Parent p set first_name=:firstName where id=:id")
    void updateParentFirstName(int id, String firstName);

    @Transactional
    @Modifying
    @Query("update Parent p set last_name=:lastName where id=:id")
    void updateParentLastName(int id, String lastName);

    @Transactional
    @Modifying
    @Query("update Parent p set email_address=:email where id=:id")
    void updateParentEmail(int id, String email);
}
