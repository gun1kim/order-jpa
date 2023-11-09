package com.example.order_jpa.repository;

import com.example.order_jpa.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Product product) {
        em.persist(product);
    }

    public List<Product> findAll() {
        return em.createQuery("select p from Product p").getResultList();
    }

    public Product findById(Long productId) {
        return em.find(Product.class, productId);
    }

    public void delete(Product product) {
        em.remove(product);
    }

    public void deleteById(Long productId) {
        em.remove(em.find(Product.class, productId));
    }


}
