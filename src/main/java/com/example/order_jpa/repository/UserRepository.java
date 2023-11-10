package com.example.order_jpa.repository;

import com.example.order_jpa.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    @PersistenceContext
    private EntityManager em;


    public void save(User user) {
        em.persist(user);
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u").getResultList();
    }

    public void delete(User user) {
        em.remove(user);
    }

    public User findById(Long userId) {
//        User user = em.find(User.class, userId);
//        em.persist(user);
//        return user;
        return em.find(User.class, userId);
    }

    public Optional<User> findByEmail(String email) {
        return em.createQuery("select u from User u where u.email = :email", User.class)
              .setParameter("email", email)
              .getResultList()
                .stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }
}
