package com.poc.poccart.service;

import com.poc.poccart.model.Cart;
import com.poc.poccart.model.Product;
import javassist.NotFoundException;

import java.math.BigDecimal;

public interface CartService {

    Cart get(Integer cartId);
    Cart create(Cart cart);
    Cart addItem(Integer cartId, Product product, Integer quantity) throws NotFoundException;
    Cart removeItem(Integer cartId, Integer cartItemId);
    Cart updateItem(Integer cartId, Integer productId, Integer quantity) throws NotFoundException;
    BigDecimal getTotal(Integer cartId) throws NotFoundException;
    void purchase();
}
