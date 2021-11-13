package com.sulwep7.childactivitytracking.consumer;

import com.sulwep7.childactivitytracking.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findAll();

    User findById(int id);

    User findByEmailAddress(String email_address);

    User findByLogin(String login);

    @Transactional
    @Modifying
    @Query("delete from User u where login=:login")
    void deleteByLogin(String login);

    @Transactional
    @Modifying
    @Query("delete from User u where email_address = :email")
    void deleteByEmail(String email);

    @Transactional
    @Modifying
    @Query("update User u set login=:login where id=:id")
    void updateLogin(int id, String login);

    @Transactional
    @Modifying
    @Query("update User u set password=:password where id=:id")
    void updatePassword(int id, String password);

    @Transactional
    @Modifying
    @Query("update User u set email_address=:email where id=:id")
    void updateEmail(int id, String email);

    @Transactional
    @Modifying
    @Query("update User u " +
            "set login=:login, password=:password, email_address=:email " +
            "where id=:id")
    void updateUser(int id, String login, String password, String email);
}
