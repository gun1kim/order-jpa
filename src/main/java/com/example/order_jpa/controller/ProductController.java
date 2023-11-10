package com.example.order_jpa.controller;

import com.example.order_jpa.dto.ProductUpdateDto;
import com.example.order_jpa.entity.Product;
import com.example.order_jpa.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/add")
    public String addProduct() {
        return "product/productForm";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/product/list";
    }

    @GetMapping("/update/{productId}")
    public String updateProduct(@PathVariable Long productId, Model model) {
        Product findProduct = productService.findById(productId);
        model.addAttribute("product", findProduct);
        return "product/productUpdate";
    }

    @PostMapping("/update/{productId}")
    public String updateProduct(@Validated @ModelAttribute ProductUpdateDto productUpdateDto) {
        productService.update(productUpdateDto);
        return "redirect:/product/list";
    }

    @GetMapping("/list")
    public String getAllProducts(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "product/productList";
    }

    @GetMapping("/info/{productId}")
    public String getProductInfo(@PathVariable Long productId, Model model) {
        Product findProduct = productService.findById(productId);
        model.addAttribute("product", findProduct);
        return "product/productInfo";
    }

    @GetMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable Long productId) {
        productService.deleteById(productId);
        return "redirect:/product/list";
    }
}
