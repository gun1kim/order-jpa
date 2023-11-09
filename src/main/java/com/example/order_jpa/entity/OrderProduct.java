package com.example.order_jpa.entity;

import com.example.order_jpa.exception.NoEnoughStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
    private Long OrderProductId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "order_price")
    private long orderPrice;
    @Column(name = "order_quantity")
    private int orderQuantity;

    public static OrderProduct createOrderProduct(Product product, int orderQuantity) throws NoEnoughStockException {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct(product);
        orderProduct.setOrderPrice(product.getPrice() * orderQuantity);
        orderProduct.setOrderQuantity(orderQuantity);

        product.decreaseQuantity(orderQuantity);
        return orderProduct;
    }

    public void cancelOrderProduct() {
        this.getProduct().increaseQuantity(this.getOrderQuantity());
    }
}
