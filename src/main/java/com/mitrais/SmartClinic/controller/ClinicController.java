package com.mitrais.SmartClinic.controller;

import com.mitrais.SmartClinic.model.Clinic;
import com.mitrais.SmartClinic.repository.ClinicRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clinics")
public class ClinicController {

    private final
    ClinicRepository clinicRepository;

    public ClinicController(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    @GetMapping
    public String index(Model model){
        List<Clinic> clinics = clinicRepository.findAll();
        model.addAttribute("clinics", clinics);
        Clinic clinic = new Clinic();
        model.addAttribute("clinic",clinic);
        return "clinics/list-clinic";
    }

    @GetMapping("/add")
    public String showClinicForm(Model model) {
        Clinic clinic = new Clinic();
        model.addAttribute("clinic",clinic);
        return "clinics/add-clinic";
    }

    @PostMapping("/add")
    public String saveClinic(Clinic clinic, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "clinics/add-clinic";
        }
        clinicRepository.save(clinic);
        model.addAttribute("clinics", clinicRepository.findAll());
        return "redirect:/clinics";
    }

    @GetMapping("/edit/{id}")
    public String showEditClinicForm(@PathVariable("id") Long id, Model model){
        Clinic clinic = clinicRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("clinic", clinic);
        return "clinics/edit-clinic";
    }

    @PostMapping("/edit/{id}")
    public String editClinic(@PathVariable("id") Long id, Clinic clinic, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            clinic.setId(id);
            return "clinics/edit-clinic";
        }
        clinicRepository.save(clinic);
        return "redirect:/clinics";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteClinics(@PathVariable("id") Long id){
        clinicRepository.deleteById(id);
        return "redirect:/clinics";
    }
}
