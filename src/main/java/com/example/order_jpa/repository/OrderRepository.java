package com.example.order_jpa.repository;

import com.example.order_jpa.entity.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Order> findAll() {
        return em.createQuery("select o from Order o").getResultList();
    }

    public Optional<Order> findById(Long orderId) {
        return Optional.ofNullable(em.find(Order.class, orderId));
    }

    public void save(Order order) {
        em.persist(order);
    }

    public void delete(Long orderId) {
        em.remove(orderId);

    }


    public List<Order> findAllByUserId(Long userId) {
        return em.createQuery("select o from Order o where o.user.userId = :userId", Order.class)
              .setParameter("userId", "userId")
              .getResultList();
    }
}
