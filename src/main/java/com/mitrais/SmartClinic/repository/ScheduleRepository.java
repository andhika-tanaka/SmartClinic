package com.mitrais.SmartClinic.repository;

import com.mitrais.SmartClinic.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
