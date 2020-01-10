package com.mitrais.SmartClinic.controller;

import com.mitrais.SmartClinic.model.Registration;
import com.mitrais.SmartClinic.model.ClinicUser;
import com.mitrais.SmartClinic.repository.RegistrationRepository;
import com.mitrais.SmartClinic.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/registrations")
public class RegistrationController {

    private final
    RegistrationRepository registrationRepository;

    private final
    UserRepository userRepository;

    public RegistrationController(RegistrationRepository registrationRepository, UserRepository userRepository) {
        this.registrationRepository = registrationRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String index(Model model) {
        /*Registration List*/
        List<Registration> registrations = registrationRepository.findByOrderByCheckupDateAscDoctorsAscNumberAsc();
        model.addAttribute("registrations", registrations);
        return "/registrations/list-registration";
    }

    @GetMapping("/add")
    public String showRegistrationForm(Model model) {
        /*New Object to Add New Data to Table*/
        Registration registration = new Registration();
        model.addAttribute(registration);

        /*Patient List For Combo Box*/
        List<ClinicUser> patients = userRepository.findPatients();
        model.addAttribute("patients", patients);

        /*Doctor List For Combo Box*/
        List<ClinicUser> doctors = userRepository.findDoctors();
        model.addAttribute("doctors", doctors);

        return "/registrations/add-registration";
    }

    @PostMapping("/add")
    public String saveRegistration(Registration registration, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registrations/list-registration";
        }
        Integer number = 0;

        Registration check = registrationRepository.findTopByCheckupDateAndDoctors_IdOrderByNumberDesc(registration.getCheckupDate(), registration.getDoctors().getId());
        if (check == null) {
            number = 1;
        } else {
            number = check.getNumber() + 1;
        }

        registration.setNumber(number);
        registrationRepository.save(registration);
        return "registrations/data-registration";
    }

    @GetMapping("/data")
    public ModelAndView data() {
        ModelAndView mav = new ModelAndView("registrations/data-registration");
        Registration registration = registrationRepository.findTopByOrderByIdDesc();
        mav.addObject("registration", registration);
        return mav;
    }

    @GetMapping("/delete/{id}")
    public String deleteRegistration(@PathVariable("id") Long id) {
        registrationRepository.deleteById(id);
        return "redirect:/registrations";
    }
}
