package com.mitrais.SmartClinic.controller;

import com.mitrais.SmartClinic.model.ClinicUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/system")
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
        model.addAttribute("patient",patient);
        return "login";
    }

    @GetMapping("/403")
    public String error403() {
        return "error/403";
    }

}
