package ru.kata.spring.boot_security.demo.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.Optional;

@Controller
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/admin/users")
    public String getUsersAddForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("userList", userService.getAllUsers());
        return "/admin/users";
    }

    @PostMapping(value = "admin/addUser")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        Optional<User> userWithSameUsername = userService.findByUsername(user.getUsername());
        if (userWithSameUsername.isPresent()) {
            bindingResult.rejectValue("username", "error.username", "Username is already taken");
            return "/admin/users";
        }
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping(value = "admin/deleteUser/{id}")
    public String removeUserById(@PathVariable(value = "id") Long id) {
        userService.removeUserById(id);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "admin/updateUser/{id}")
    public String getUserUpdateForm(ModelMap model, @PathVariable(value = "id") Long id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "/admin/updateUser";
    }

    @PutMapping(value = "/updateUser")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        Optional<User> userWithSameUsername = userService.findByUsername(user.getUsername());
        User updatingUser = userService.getUserById(user.getId());
        if (userWithSameUsername.isPresent() && !user.getUsername().equals(updatingUser.getUsername())) {
            bindingResult.rejectValue("username", "error.username", "Username is already taken");
            return "/admin/updateUser";
        }
        userService.updateUser(user);
        return "redirect:/admin/users";
    }
}
