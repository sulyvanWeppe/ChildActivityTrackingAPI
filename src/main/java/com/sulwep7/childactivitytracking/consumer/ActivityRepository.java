package com.sulwep7.childactivitytracking.consumer;

import com.sulwep7.childactivitytracking.model.Activity;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

public interface ActivityRepository extends CrudRepository<Activity, Integer> {

    @Query("select a from Activity a order by a.id")
    List<Activity> findAll();

    Activity findById(int id);

    Activity findByName(String name);

    @Transactional
    @Modifying
    @Query("delete from Activity a where name=:name")
    void deleteByName(String name);

    @Transactional
    @Modifying
    @Query("update Activity a set name=:name where id=:id")
    void updateName(int id, String name);

}
