package com.mitrais.SmartClinic.repository;

import com.mitrais.SmartClinic.model.ClinicUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<ClinicUser, Long> {

    @Query("SELECT u FROM ClinicUser u WHERE NOT u.role = 3")
    List<ClinicUser> findStaffs();

    @Query("SELECT u FROM ClinicUser u WHERE u.role = 3")
    List<ClinicUser> findPatients();

    @Query("SELECT u FROM ClinicUser u WHERE u.role = 2")
    List<ClinicUser> findDoctors();
}
