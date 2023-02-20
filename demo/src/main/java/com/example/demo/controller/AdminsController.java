package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import com.example.demo.util.UserErrorResponse;
import com.example.demo.util.UserCreatingOrUpdatingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin")
@CrossOrigin
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

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAdminPage() {

//        model.addAttribute("admin", userService.getUserByUsername(principal.getName()));
//        model.addAttribute("users", userService.getAllUsers());
//        model.addAttribute("roles", roleService.getRoles());

        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

//    @GetMapping("/new")
//    public String getNewUserForm(@ModelAttribute("user") User user, Model model) {
//        model.addAttribute("roles", roleService.getRoles());
//        return "/new";
//    }

    @PostMapping("/createNew")
    public ResponseEntity<HttpStatus> createUser(@RequestBody @Valid User user,
                                                 BindingResult bindingResult) {
        saveRole(user.getRoles());
        collectErrorMessage(bindingResult);

        userService.saveUser(user);

        return ResponseEntity.ok(HttpStatus.OK);
    }

//    @GetMapping("/{id}/edit")
//    public String editUser(Model model, @PathVariable("id") Long id) {
//        model.addAttribute("user", userService.getUserById(id));
//        model.addAttribute("roles", roleService.getRoles());
//        return "/edit";
//    }

    @PutMapping(value = "/user")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid User user,
                                                 BindingResult bindingResult) {

//        if (user.getPassword().hashCode() != userService.getUserById(id).getPassword().hashCode())
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        saveRole(user.getRoles());
        collectErrorMessage(bindingResult);

        userService.updateUser(user);

        return ResponseEntity.ok(HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public String removeUserById(@PathVariable Long id, Principal principal) throws UserErrorResponse {
//        boolean checkDeletingUserIsCurrent = userService.getUserByUsername(principal.getName()).equals(userService.getUserById(id));

        User user = userService.getUserById(id);
        if (user == null)
            throw new UserErrorResponse("User with id " + id + " not exist", System.currentTimeMillis());

        roleService.removeRoleById(id);
        userService.removeById(id);

        return "user with id " + id + "was deleted";
    }

    @GetMapping("/userPage")
    public User getUserPage(Model model, Principal principal) {
        Long id = userService.getUserByUsername(principal.getName()).getId();
//        model.addAttribute("admin", userService.getUserByUsername(principal.getName()));
//        model.addAttribute("user", userService.getUserById(id));
        return userService.getUserById(id);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserCreatingOrUpdatingException exception) {
        UserErrorResponse response = new UserErrorResponse(
                exception.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private void saveRole(Set<Role> roles) {
        for (Role role : roles) {
            roleService.saveRole(role);
        }
    }

    private void collectErrorMessage(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new UserCreatingOrUpdatingException(errorMsg.toString());
        }
    }
}
