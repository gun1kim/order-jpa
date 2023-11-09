package com.example.order_jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Column(name = "order_date", length = 10)
    private LocalDateTime orderDate;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "order_status", length = 10)
    private OrderStatus status;

    @Column(name = "total_price")
    private long totalPrice;
    @Column(name = "total_quantity")
    private int totalQuantity;

    public void addOrderProduct(OrderProduct orderProduct) {
        orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
    }

    public void cancel() {
        this.setStatus(OrderStatus.CANCELED);
        for (OrderProduct orderProduct : this.getOrderProducts()) {
            orderProduct.cancelOrderProduct();
        }
//        productRepository.save(this.getOrderProducts().get(0).getProduct());
//        orderRepository.save(this);
    }

    public static Order createOrder(User user, OrderProduct... orderProducts) {
        long totalPrice = 0L;
        int totalQuantity = 0;
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);
        for (OrderProduct orderProduct : orderProducts) {
            totalPrice += orderProduct.getOrderPrice();
            totalQuantity += orderProduct.getOrderQuantity();
            order.addOrderProduct(orderProduct);
        }
        order.setTotalPrice(totalPrice);
        order.setTotalQuantity(totalQuantity);
        return order;

    }
//    Order order = new Order();
//        order.setUser(user);
//        order.setOrderDate(LocalDateTime.now());
//        order.setStatus(OrderStatus.CREATED);
//        order.setTotalPrice(product.getPrice() * orderDto.getOrderQuantity());
//        order.setTotalQuantity(orderDto.getOrderQuantity());
//        orderRepository.save(order);


//    List<OrderProduct> orderProducts = order.getOrderProducts();
//        orderProducts.add(orderProduct);
//        order.setOrderProducts(orderProducts);

}
