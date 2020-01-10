package com.mitrais.SmartClinic.controller;

import com.mitrais.SmartClinic.model.ClinicUser;
import com.mitrais.SmartClinic.model.Role;
import com.mitrais.SmartClinic.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public HomeController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/home")
    public String systemHome() {
        return "home";
    }

    @GetMapping("/welcome")
    public String admin() {
        return "welcome";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/login")
    public String login(Model model) {
        ClinicUser patient = new ClinicUser();
        model.addAttribute("patient", patient);
        return "login";
    }

    @PostMapping("/signup")
    public String saveUser(ClinicUser patient, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "patients/add-patient";
        }
        patient.setActive(1);
        patient.setRole(Role.ROLE_PATIENT);
        patient.setPassword(bCryptPasswordEncoder.encode(patient.getPassword()));
        userRepository.save(patient);
        return "redirect:/login";
    }

    @GetMapping("/403")
    public String error403() {
        return "errors/page_403";
    }

}
