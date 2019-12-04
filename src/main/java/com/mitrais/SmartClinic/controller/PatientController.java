package com.mitrais.SmartClinic.controller;

import com.mitrais.SmartClinic.model.Role;
import com.mitrais.SmartClinic.model.User;
import com.mitrais.SmartClinic.repository.RoleRepository;
import com.mitrais.SmartClinic.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final
    UserRepository userRepository;
    RoleRepository roleRepository;

    public PatientController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String index(Model model) {
        List<User> patients = userRepository.findPatients();
        model.addAttribute("patients", patients);
        return "patients/list-patient";
    }

    @GetMapping("/add")
    public String showUserForm(Model model) {
        User patient = new User();
        model.addAttribute("patient",patient);

        List<Role> roles = roleRepository.findPatients();
        model.addAttribute("roles", roles);
        return "patients/add-patient";
    }

    @PostMapping("/add")
    public String saveUser(User patient, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "patients/add-patient";
        }
        userRepository.save(patient);
        return "redirect:/patients";
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
        User patient = userRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("patient", patient);

        List<Role> roles = roleRepository.findPatients();
        model.addAttribute("roles", roles);
        return "patients/edit-patient";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, User patient, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            patient.setId(id);
            return "patients/edit-patient";
        }
        userRepository.save(patient);
        return "redirect:/patients";
    }

    @GetMapping("/detail/{id}")
    public String showDetailForm(@PathVariable("id") Long id, Model model) {
        User patient = userRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("patient", patient);
        return "patients/detail-patient";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return "redirect:/patients";
    }
}
