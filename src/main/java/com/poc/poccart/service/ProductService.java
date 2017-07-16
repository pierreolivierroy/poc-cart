package com.poc.poccart.service;

import com.poc.poccart.model.Product;

import java.util.Map;

public interface ProductService {

    Product get(Integer productId);
    Map<Integer, Product> listAll(Integer page, Integer size);
    Product reduceQuantity(Integer productId);
}
