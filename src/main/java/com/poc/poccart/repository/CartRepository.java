package com.poc.poccart.repository;

import com.poc.poccart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findById(Integer cartId);
    Optional<Cart> findByIdAndItemsId(Integer cartId, Integer cartItemId);
}
