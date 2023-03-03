package com.example.demo.DAO;

import com.example.demo.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserDAO {
    @PersistenceContext
    private final EntityManager entityManager;

    public UserDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = entityManager.createQuery("select u from User u join fetch u.roles where u.username=:username ", User.class)
                .setParameter("username", username)
                .getSingleResult();

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with %s not found", username));
        }
        return user;
    }

}
