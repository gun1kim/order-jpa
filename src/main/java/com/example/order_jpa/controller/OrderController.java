package com.example.order_jpa.controller;

import com.example.order_jpa.dto.OrderDto;
import com.example.order_jpa.entity.Order;
import com.example.order_jpa.exception.NoEnoughStockException;
import com.example.order_jpa.service.OrderService;
import com.example.order_jpa.service.ProductService;
import com.example.order_jpa.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;

    public OrderController(OrderService orderService, UserService userService, ProductService productService) {
        this.orderService = orderService;
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping("/list")
    public String getAllOrders(Model model) {
        List<Order> allOrders = orderService.getAllOrders();
        model.addAttribute("orders", allOrders);
        return "order/orderList";
    }


    @GetMapping("/list/{userId}")
    public String getAllOrdersByUserId(@PathVariable Long userId, Model model) {
        List<Order> allOrdersByUserId = orderService.getAllOrdersByUserId(userId);
        model.addAttribute("orders", allOrdersByUserId);
        return "order/orderList";
    }

    @PostMapping("/list/{orderId}")
    public String cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/order/list";
    }

    @GetMapping("/info/{orderId}")
    public String getOrderInfo(@PathVariable Long orderId, Model model) {
        Order order = orderService.getOrderInfo(orderId);
//        List<OrderProduct> orderProducts = order.getOrderProducts();
//        model.addAttribute("orderProducts", orderProducts);
        model.addAttribute("order", order);
        return "order/orderInfo";
    }

    @GetMapping("/add")
    public String addOrder(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("products", productService.findAll());
        return "order/orderForm";
    }

    @PostMapping("/add")
    public String addOrder(@ModelAttribute OrderDto orderDto) throws NoEnoughStockException {
        orderService.addOrder(orderDto);
        return "redirect:/order/list";
    }

}
