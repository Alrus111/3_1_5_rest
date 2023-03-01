package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {
    void saveUser(User user);

    List<User> getAllUsers();

    User getUserById(Integer id);

    User getUserByUsername(String username);

    boolean updateUser(User user);

    void removeById(Integer id);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
