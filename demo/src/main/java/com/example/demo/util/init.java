package com.example.demo.util;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class init {
    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public init(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostConstruct
    public void initialization() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");

        roleService.saveRole(roleAdmin);
        roleService.saveRole(roleUser);

        User admin = new User("adminName", "adminLastName", "admin", "admin", Set.of(roleAdmin));
        userService.saveUser(admin);

        User user = new User("userName", "userLastname", "user", "user", Set.of(roleUser));
        userService.saveUser(user);
    }
}
