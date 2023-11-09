package com.example.order_jpa.service;

import com.example.order_jpa.dto.ProductUpdateDto;
import com.example.order_jpa.entity.Product;
import com.example.order_jpa.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }


    @Transactional(readOnly = true)
    public Product findById(Long productId) {
        return productRepository.findById(productId);
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }

    public void deleteById(Long productId) {
        productRepository.deleteById(productId);
    }

    public void update(ProductUpdateDto productUpdateDto) {
        Product product = productRepository.findById(productUpdateDto.getProductId());
        product.setName(productUpdateDto.getName());
        product.setPrice(productUpdateDto.getPrice());
        //product.setQuantity(productUpdateDto.getQuantity());
        productRepository.save(product);
    }

}
