package com.poc.poccart.service.Impl;

import com.poc.poccart.model.Cart;
import com.poc.poccart.model.CartItem;
import com.poc.poccart.model.Product;
import com.poc.poccart.repository.CartItemRepository;
import com.poc.poccart.repository.CartRepository;
import com.poc.poccart.repository.ProductRepository;
import com.poc.poccart.service.CartService;
import com.poc.poccart.utils.OrderUtils;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;
    private ProductRepository productRepository;
    private OrderUtils orderUtils;

    public CartServiceImpl(CartRepository cartRepository,
                           CartItemRepository cartItemRepository,
                           ProductRepository productRepository,
                           OrderUtils orderUtils) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.orderUtils = orderUtils;
    }

    @Override
    @Transactional(readOnly = true)
    public Cart get(Integer cartId) {
        return cartRepository.findOne(cartId);
    }

    @Override
    @Transactional
    public Cart create(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart addItem(Integer cartId, Product product, Integer quantity) throws NotFoundException {

        Optional<Cart> cart = Optional.ofNullable(cartRepository.findOne(cartId));

        if (cart.isPresent()) {

            int i = 0;
            List<CartItem> cartItems = new ArrayList<>(quantity);
            while (i < quantity) {
                CartItem cartItem = new CartItem();
                cartItem.setProduct(product);

                cart.get().addItem(cartItem);
                cartItems.add(cartItem);
                i++;
            }

            cartItemRepository.save(cartItems);

            return cart.get();
        }

        throw new NotFoundException(String.format("Cart [%s] cannot be found", cartId));
    }

    @Override
    @Transactional
    public Cart removeItem(Integer cartId, Integer cartItemId) {

        Optional<Cart> cart = cartRepository.findByIdAndItemsId(cartId, cartItemId);

        if (cart.isPresent()) {
            Optional<CartItem> cartItem = cart.get().getItems()
                    .stream()
                    .filter(item -> item.getId().equals(cartItemId))
                    .findFirst();

            if (cartItem.isPresent()) {
                cart.get().removeItem(cartItem.get());
                return cartRepository.save(cart.get());
            }
        }

        return cartRepository.findOne(cartId);
    }

    @Override
    @Transactional
    public Cart updateItem(Integer cartId, Integer productId, Integer quantity) throws NotFoundException {

        Optional<Cart> cart = cartRepository.findById(cartId);

        if (cart.isPresent()) {

            int count = cart.get().getItems()
                    .stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .collect(Collectors.toList())
                    .size();

            if (count > 0) {

                if (count < quantity) {
                    return incrementQuantityOfItems(cart.get(), productId, count, quantity);
                } else if (count > quantity) {
                    return decrementQuantityOfItems(cart.get(), productId, count, quantity);
                }
            }

            return cart.get();
        }

        throw new NotFoundException(String.format("Cart [%s] cannot be found", cartId));
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotal(Integer cartId) throws NotFoundException {

        Optional<Cart> cart = cartRepository.findById(cartId);

        if (cart.isPresent()) {
            return orderUtils.calculateOrderTotal(cart.get());
        }

        throw new NotFoundException(String.format("Cart [%s] cannot be found", cartId));
    }

    @Override
    public void purchase() {
        // TODO: 2017-07-16 Purchase items in cart (reduce quantity of products, place order, etc)
    }

    private Cart incrementQuantityOfItems(Cart cart, Integer productId, int initialSize, Integer quantity) {

        Product product = productRepository.findOne(productId);

        while (initialSize < quantity) {

            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);

            cart.addItem(cartItem);

            initialSize++;
        }

        return cartRepository.save(cart);
    }

    private Cart decrementQuantityOfItems(Cart cart, Integer productId, int initialSize, Integer quantity) {

        while (initialSize > quantity) {

            CartItem cartItem = cart.getItems()
                    .stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst()
                    .get();

            cart.removeItem(cartItem);

            initialSize--;
        }

        return cartRepository.save(cart);
    }
}
