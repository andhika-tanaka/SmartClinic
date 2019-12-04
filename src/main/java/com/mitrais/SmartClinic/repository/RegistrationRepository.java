package com.mitrais.SmartClinic.repository;

import com.mitrais.SmartClinic.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByOrderByCheckupDateAscDoctorsAscNumberAsc();

    Registration findTopByOrderByIdDesc();

    @Query("SELECT MAX(r.number) FROM Registration r JOIN r.doctors d " +
            "WHERE r.checkupDate = ?1 " +
            "AND d.id = ?2 " +
            "ORDER BY r.number " +
            "DESC")
    Integer last_number(String date, Long doctor);
}
