package com.poc.poccart.service.Impl;

import com.poc.poccart.model.Product;
import com.poc.poccart.repository.ProductRepository;
import com.poc.poccart.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product get(Integer productId) {

        return repository.findOne(productId);
    }

    @Override
    public Map<Integer, Product> listAll(Integer page, Integer size) {

        Page<Product> products = repository.findAll(new PageRequest(page, size));

        return products.getContent()
                .stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));
    }

    @Override
    public Product reduceQuantity(Integer productId) {

        Product product = repository.findOne(productId);
        int quantity = product.getQuantity();

        if (quantity > 0) {
            product.setQuantity(--quantity);
        }

        return repository.save(product);
    }
}
