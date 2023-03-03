package com.example.demo.service;

import com.example.demo.DAO.UserDAO;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserServiceImpl( UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, RoleService roleService, UserDAO userDAO) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.userDAO = userDAO;
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        saveAndSetRole(user.getRoles(), user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) {
        Optional<User> userById = userRepository.findById(id);
        if (userById.isPresent())
            return userById.get();
        else
            throw new UsernameNotFoundException(String.format("User with %s not found", id));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Transactional
    @Override
    public boolean updateUser(User updatedUser) {
        User userFromDB = userRepository.findById(updatedUser.getId()).get();
        if (updatedUser.getPassword().hashCode()!=userFromDB.getPassword().hashCode()) {
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        boolean checkUpdateUsername = false;
        if (userRepository.getUserByUsername(updatedUser.getUsername())==null)
            checkUpdateUsername = true;
        userRepository.save(updatedUser);
        return checkUpdateUsername;
    }

    @Transactional
    @Override
    public void removeById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDAO.loadUserByUsername(username);
    }


    private void saveAndSetRole(Set<Role> roles, User user) {
        for (Role role : roles) {
            roleService.saveRole(role);
            user.setRoles(Set.of(role));
        }
    }
}
