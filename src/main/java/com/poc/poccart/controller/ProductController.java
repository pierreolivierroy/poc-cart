package com.poc.poccart.controller;

import com.poc.poccart.model.Product;
import com.poc.poccart.service.ProductService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/list")
    public Map<Integer, Product> listAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                         @RequestParam(name = "size", defaultValue = "10") Integer size) {

        return productService.listAll(page, size);
    }

    @RequestMapping("{productId}")
    public Product get(@PathVariable("productId") Integer productId) {

        return productService.get(productId);
    }
}
