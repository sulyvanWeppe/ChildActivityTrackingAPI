package com.sulwep7.childactivitytracking.consumer;

import com.sulwep7.childactivitytracking.model.Doctor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface DoctorRepository extends CrudRepository<Doctor, Integer> {

    List<Doctor> findAll();

    Doctor findById(int id);

    List<Doctor> findByUserId(int userId);

    List<Doctor> findByName(String name);

    Doctor findByEmailAddress(String emailAddress);

    @Query("select d from Doctor d where phone_nr=:phoneNumber")
    Doctor findByPhoneNr(String phoneNumber);

    List<Doctor> findByCity(String city);

    List<Doctor> findByStreet(String street);

    List<Doctor> findByStreetNr(String streetNr);

    List<Doctor> findByZipCode(String zipCode);

    List<Doctor> findByCountry(String country);

    @Query("select d from Doctor d where city = :city and street = :street and street_nr = :streetNr and zip_code = :zipCode and country = :country")
    List<Doctor> findByAddress(@Param("city") String city, @Param("street") String street, @Param("streetNr") String streetNr, @Param("zipCode") String zipCode, @Param("country") String country);

    @Transactional
    @Modifying
    @Query("delete from Doctor d where name=:name")
    void deleteByName(String name);

    @Transactional
    @Modifying
    @Query("delete from Doctor d where email_address=:email")
    void deleteByEmail(String email);

    @Transactional
    @Modifying
    @Query("delete from Doctor d where user_id=:userId")
    void deleteByUserId(int userId);

    @Transactional
    @Modifying
    @Query("update Doctor d set name=:name where id=:id")
    void updateName(int id, String name);

    @Transactional
    @Modifying
    @Query("update Doctor d set email_address=:email where id=:id")
    void updateEmail(int id, String email);

    @Transactional
    @Modifying
    @Query("update Doctor d set phone_nr=:phone where id=:id")
    void updatePhone(int id, String phone);

    @Transactional
    @Modifying
    @Query("update Doctor d set country=:country, city=:city, zip_code=:zipCode, street=:street, street_nr=:nr where id=:id")
    void updateAddress(int id, String country, String city, String zipCode, String street, String nr);

    @Transactional
    @Modifying
    @Query("update Doctor d set user_id=:userId, name=:name, email_address=:email, phone_nr=:phone, country=:country, zip_code=:zipCode, city=:city, street=:street, street_nr=:nr where id=:id")
    void updateDoctor(int id, int userId, String name, String email, String phone, String country, String zipCode, String city, String street, String nr);
}
