package com.mitrais.SmartClinic.repository;

import com.mitrais.SmartClinic.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT p FROM Role p WHERE NOT p.title='PATIENT'")
    List<Role> findStaffs();

    @Query("SELECT p FROM Role p WHERE p.title='PATIENT'")
    List<Role> findPatients();
}
