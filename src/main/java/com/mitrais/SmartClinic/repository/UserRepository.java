package com.mitrais.SmartClinic.repository;

import com.mitrais.SmartClinic.model.ClinicUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<ClinicUser, Long> {

    @Query("SELECT u FROM ClinicUser u JOIN u.roles p WHERE NOT (p.title='PATIENT')")
    List<ClinicUser> findStaffs();

    @Query("SELECT u FROM ClinicUser u JOIN u.roles p WHERE p.title='PATIENT'")
    List<ClinicUser> findPatients();

    @Query("SELECT u FROM ClinicUser u JOIN u.roles p WHERE p.title='DOCTOR'")
    List<ClinicUser> findDoctors();
}
