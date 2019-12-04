package com.mitrais.SmartClinic.controller;

import com.mitrais.SmartClinic.model.Role;
import com.mitrais.SmartClinic.model.User;
import com.mitrais.SmartClinic.repository.RoleRepository;
import com.mitrais.SmartClinic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final
    UserRepository userRepository;
    RoleRepository roleRepository;

    public UserController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String index(Model model) {
        List<User> users = userRepository.findStaffs();
        model.addAttribute("users", users);
        return "users/list-user";
    }

    @GetMapping("/add")
    public String showUserForm(Model model) {
        User user = new User();
        model.addAttribute(user);

        List<Role> roles = roleRepository.findStaffs();
        model.addAttribute("roles", roles);
        return "users/add-user";
    }

    @PostMapping("/add")
    public String saveUser(User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/add-user";
        }
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);

        List<Role> roles = roleRepository.findStaffs();
        model.addAttribute("roles", roles);
        return "users/edit-user";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            user.setId(id);
            return "users/edit-user";
        }
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/detail/{id}")
    public String showDetailForm(@PathVariable("id") Long id, Model model) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "users/detail-user";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }
}
