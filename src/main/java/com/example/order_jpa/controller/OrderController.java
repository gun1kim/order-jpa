package com.example.order_jpa.controller;

import com.example.order_jpa.dto.OrderDto;
import com.example.order_jpa.entity.Order;
import com.example.order_jpa.entity.User;
import com.example.order_jpa.exception.NoEnoughStockException;
import com.example.order_jpa.service.OrderService;
import com.example.order_jpa.service.ProductService;
import com.example.order_jpa.service.UserService;
import com.example.order_jpa.session.SessionConst;
import com.example.order_jpa.session.UserSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
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

    @GetMapping("/cancel/{orderId}")
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
    public String addOrder(Model model, HttpServletResponse response, HttpServletRequest request) {
        // 로그인한 사용자의 정보를 쿠키로부터 얻어오기
        HttpSession session = request.getSession(false);
        UserSession userSession = (UserSession)session.getAttribute(SessionConst.SESSION_NAME);
        Long userId = userSession.getUserId();
        User user = userService.getUserById(userId);

        model.addAttribute("user", user);
        model.addAttribute("products", productService.findAll());
        return "order/orderForm";

    }

    @PostMapping("/add")
    public String addOrder(@ModelAttribute OrderDto orderDto) throws NoEnoughStockException {
        orderService.addOrder(orderDto);
        return "redirect:/order/list";
    }

}
