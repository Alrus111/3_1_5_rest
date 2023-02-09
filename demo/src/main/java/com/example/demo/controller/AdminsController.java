package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminsController {

    private final UserService userService;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminsController(UserService userService, RoleService roleService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public String getAdminPage(@ModelAttribute("user") User user,
                               Model model, Principal principal) {

        model.addAttribute("admin", userService.getUserByUsername(principal.getName()));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getRoles());
        return "/admin";
    }

//    @GetMapping("/new")
//    public String getNewUserForm(@ModelAttribute("user") User user, Model model) {
//        model.addAttribute("roles", roleService.getRoles());
//        return "/new";
//    }

    @PostMapping("/createNew")
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "nameRole") String nameRole) {

        Role role = new Role(nameRole);
        roleService.saveRole(role);
        user.setRoles(Set.of(role));
        userService.saveUser(user);
        return "redirect:/admin";
    }

//    @GetMapping("/{id}/edit")
//    public String editUser(Model model, @PathVariable("id") Long id) {
//        model.addAttribute("user", userService.getUserById(id));
//        model.addAttribute("roles", roleService.getRoles());
//        return "/edit";
//    }

    @PatchMapping(value = "/{id}/edit")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Long id,
                             @RequestParam(value = "nameRole") String nameRole) {
        if (user.getPassword().hashCode() != userService.getUserById(id).getPassword().hashCode())
            user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = new Role(nameRole);
        roleService.saveRole(role);
        user.setRoles(Set.of(role));

        if (userService.updateUser(user))
            return "redirect:/login";
        else
            return "redirect:/admin";
    }

    @DeleteMapping("/{id}/delete")
    public String removeUserById(@PathVariable("id") Long id, Principal principal) {
        boolean checkDeletingUserIsCurrent = userService.getUserByUsername(principal.getName()).equals(userService.getUserById(id));

        roleService.removeRoleById(id);
        userService.removeById(id);

        if (checkDeletingUserIsCurrent)
            return "redirect:/login";
        else
            return "redirect:/admin";
    }

    @GetMapping("/user")
    public String getUserPage(Model model, Principal principal) {
        Long id = userService.getUserByUsername(principal.getName()).getId();
        model.addAttribute("admin", userService.getUserByUsername(principal.getName()));
        model.addAttribute("user", userService.getUserById(id));
        return "admin_show_user";
    }
}
