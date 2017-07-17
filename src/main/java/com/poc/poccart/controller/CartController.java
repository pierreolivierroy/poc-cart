package com.poc.poccart.controller;

import com.poc.poccart.model.Cart;
import com.poc.poccart.model.Product;
import com.poc.poccart.service.CartService;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/carts")
public class CartController {

    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @RequestMapping("/{cartId}")
    public Cart get(@PathVariable("cartId") Integer cartId) {
        return cartService.get(cartId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Cart create(@RequestBody Cart cart) {

        return cartService.create(cart);
    }

    @RequestMapping(value = "/{cartId}/addItem", method = RequestMethod.POST)
    public Cart addItem(@PathVariable("cartId") Integer cartId,
                        @RequestParam(value = "quantity", defaultValue = "1") Integer quantity,
                        @RequestBody Product product) {

        try {
            return cartService.addItem(cartId, product, quantity);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        return new Cart();
    }

    @RequestMapping(value = "/{cartId}/removeItem", method = RequestMethod.POST)
    public Cart removeItem(@PathVariable("cartId") Integer cartId, @RequestParam("cartItemId") Integer cartItemId) {

        return cartService.removeItem(cartId, cartItemId);
    }

    @RequestMapping(value = "/{cartId}", method = RequestMethod.PATCH)
    public Cart updateItem(@PathVariable("cartId") Integer cartId,
                           @RequestParam("productId") Integer productId,
                           @RequestParam("quantity") Integer quantity) {

        try {
            return cartService.updateItem(cartId, productId, quantity);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        return new Cart();
    }

    @RequestMapping(value = "/{cartId}/total")
    public BigDecimal getTotal(@PathVariable("cartId") Integer cartId) {

        try {
            return cartService.getTotal(cartId);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        return new BigDecimal(0.00);
    }
}
