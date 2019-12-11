package com.mitrais.SmartClinic.controller;

import com.mitrais.SmartClinic.model.ClinicUser;
import com.mitrais.SmartClinic.model.Role;
import com.mitrais.SmartClinic.repository.RoleRepository;
import com.mitrais.SmartClinic.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping
    public String index(Model model) {
        List<ClinicUser> clinicUsers = userRepository.findStaffs();
        model.addAttribute("users", clinicUsers);
        return "users/list-user";
    }

    @GetMapping("/add")
    public String showUserForm(Model model) {
        ClinicUser clinicUser = new ClinicUser();
        model.addAttribute("user",clinicUser);

        List<Role> roles = roleRepository.findStaffs();
        model.addAttribute("roles", roles);
        return "users/add-user";
    }

    @PostMapping("/add")
    public String saveUser(ClinicUser user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/add-user";
        }
        user.setActive(1);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
        ClinicUser clinicUser = userRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", clinicUser);

        List<Role> roles = roleRepository.findStaffs();
        model.addAttribute("roles", roles);
        return "users/edit-user";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, ClinicUser user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            user.setId(id);
            return "users/edit-user";
        }
        user.setActive(1);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }
}
