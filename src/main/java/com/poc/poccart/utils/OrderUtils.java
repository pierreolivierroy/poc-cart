package com.poc.poccart.utils;

import com.poc.poccart.model.Cart;
import com.poc.poccart.model.CartItem;
import com.poc.poccart.model.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderUtils {

    private static final BigDecimal TPS = new BigDecimal(0.09975);
    private static final BigDecimal TVQ = new BigDecimal(0.05);

    public BigDecimal calculateOrderTotal(Cart cart) {

        // TODO: 2017-07-16 Apply better strategy to calculate taxes

        BigDecimal subTotal = cart.getItems().stream().map(CartItem::getProduct).map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        return getTotal(subTotal).setScale(2, BigDecimal.ROUND_CEILING);
    }

    private BigDecimal getTotal(BigDecimal subTotal) {

        return subTotal.add(subTotal.multiply(TVQ)).add(subTotal.multiply(TPS));
    }
}
