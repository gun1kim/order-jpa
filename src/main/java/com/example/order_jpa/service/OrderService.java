package com.example.order_jpa.service;

import com.example.order_jpa.dto.OrderDto;
import com.example.order_jpa.entity.*;
import com.example.order_jpa.exception.NoEnoughStockException;
import com.example.order_jpa.repository.OrderRepository;
import com.example.order_jpa.repository.ProductRepository;
import com.example.order_jpa.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getAllOrdersByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    public void addOrder(OrderDto orderDto) throws NoEnoughStockException { // userId, productId, orderQuantity
        User user = userRepository.findById(orderDto.getUserId());
        Product product = productRepository.findById(orderDto.getProductId());
        // 주문상품 생성
        OrderProduct orderProduct =  OrderProduct.createOrderProduct(product, orderDto.getOrderQuantity());
        // 주문 생성
        Order order = Order.createOrder(user, orderProduct);
        orderRepository.save(order);
        productRepository.save(product);
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.cancel();

    }

    public Order getOrderInfo(Long orderId) {
        return orderRepository.findById(orderId).get();
    }

    public void updateOrder(Order order) {
        orderRepository.save(order);
    }
}
