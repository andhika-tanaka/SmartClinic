package com.mitrais.SmartClinic.controller;

import com.mitrais.SmartClinic.model.ClinicUser;
import com.mitrais.SmartClinic.model.Role;
import com.mitrais.SmartClinic.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PatientController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping
    public String index(Model model) {
        List<ClinicUser> patients = userRepository.findPatients();
        model.addAttribute("patients", patients);
        return "patients/list-patient";
    }

    @GetMapping("/add")
    public String showUserForm(Model model) {
        ClinicUser patient = new ClinicUser();
        model.addAttribute("patient",patient);
        return "patients/add-patient";
    }

    @PostMapping("/add")
    public String saveUser(ClinicUser patient, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "patients/add-patient";
        }
        patient.setActive(1);
        patient.setRole(Role.ROLE_PATIENT);
        patient.setPassword(bCryptPasswordEncoder.encode(patient.getPassword()));
        userRepository.save(patient);
        return "redirect:/patients";
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
        ClinicUser patient = userRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("patient", patient);
        return "patients/edit-patient";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, ClinicUser patient, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            patient.setId(id);
            return "patients/edit-patient";
        }
        patient.setActive(1);
        patient.setRole(Role.ROLE_PATIENT);
        patient.setPassword(bCryptPasswordEncoder.encode(patient.getPassword()));
        userRepository.save(patient);
        return "redirect:/patients";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return "redirect:/patients";
    }
}
