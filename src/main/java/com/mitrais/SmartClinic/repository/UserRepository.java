package com.mitrais.SmartClinic.repository;

import com.mitrais.SmartClinic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u JOIN u.roles p WHERE NOT (p.title='PATIENT')")
    List<User> findStaffs();

    @Query("SELECT u FROM User u JOIN u.roles p WHERE p.title='PATIENT'")
    List<User> findPatients();

    @Query("SELECT u FROM User u JOIN u.roles p WHERE p.title='DOCTOR'")
    List<User> findDoctors();
}
