package com.mitrais.SmartClinic.controller;

import com.mitrais.SmartClinic.model.Clinic;
import com.mitrais.SmartClinic.model.Schedule;
import com.mitrais.SmartClinic.model.User;
import com.mitrais.SmartClinic.repository.ClinicRepository;
import com.mitrais.SmartClinic.repository.ScheduleRepository;
import com.mitrais.SmartClinic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/schedules")
public class ScheduleController {

    final
    UserRepository userRepository;

    final
    ScheduleRepository scheduleRepository;

    final
    ClinicRepository clinicRepository;

    public ScheduleController(UserRepository userRepository, ScheduleRepository scheduleRepository, ClinicRepository clinicRepository) {
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
        this.clinicRepository = clinicRepository;
    }

    @GetMapping
    public String index(Model model){
        List<Schedule> schedules = scheduleRepository.findAll();
        model.addAttribute("schedules", schedules);
        return "schedules/list-schedule";
    }

    @GetMapping("/add")
    public String showUserForm(Model model)
    {
        List<User> users = userRepository.findStaffs();
        List<Clinic> clinics = clinicRepository.findAll();
        Schedule schedule = new Schedule();
        model.addAttribute("users", users);
        model.addAttribute("clinics", clinics);
        model.addAttribute("schedule",schedule);
        return "schedules/add-schedule";
    }

    @PostMapping("/add")
    public String saveSchedule(Schedule schedule, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "schedules/add-schedule";
        }
        scheduleRepository.save(schedule);
        return "redirect:/schedules";
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model){
        Schedule schedule = scheduleRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        List<User> users = userRepository.findStaffs();
        List<Clinic> clinics = clinicRepository.findAll();
        model.addAttribute("schedule", schedule);
        model.addAttribute("users", users);
        model.addAttribute("clinics", clinics);
        return "schedules/edit-schedule";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Schedule schedule, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            schedule.setId(id);
            return "redirect:/schedules";
        }
        scheduleRepository.save(schedule);
        return "redirect:/schedules";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteSchedule(@PathVariable("id") Long id){
        scheduleRepository.deleteById(id);
        return "redirect:/schedules";
    }
}
