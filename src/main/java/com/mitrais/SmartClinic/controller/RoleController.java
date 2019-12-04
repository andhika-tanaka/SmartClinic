package com.mitrais.SmartClinic.controller;

import com.mitrais.SmartClinic.model.Role;
import com.mitrais.SmartClinic.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/roles")
public class RoleController {

    private final
    RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String index(Model model){
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        Role role = new Role();
        model.addAttribute("role",role);
        return "roles/list-role";
    }

    @GetMapping("/add")
    public String showRoleForm(Model model) {
        Role role = new Role();
        model.addAttribute("role",role);
        return "roles/add-role";
    }

    @PostMapping("/add")
    public String saveRole(Role role, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "roles/add-role";
        }
        role.setRole("ROLE_" + role.getTitle());
        roleRepository.save(role);
        model.addAttribute("roles", roleRepository.findAll());
        return "redirect:/roles";
    }

    @GetMapping("/edit/{id}")
    public String showEditRoleForm(@PathVariable("id") Long id, Model model){
        Role role = roleRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("role", role);
        return "roles/edit-role";
    }

    @PostMapping("/edit/{id}")
    public String editMedicine(@PathVariable("id") Long id, Role role, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            role.setId(id);
            return "roles/edit-roles";
        }
        role.setRole("ROLE_" + role.getTitle());
        roleRepository.save(role);
        return "redirect:/roles";
    }

    @GetMapping(value = "/delete/{id}")
    public String deletePosition(@PathVariable("id") Long id){
        roleRepository.deleteById(id);
        return "redirect:/roles";
    }
}
