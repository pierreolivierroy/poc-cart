package com.poc.poccart.repository;

import com.poc.poccart.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {

    Iterable<Product> findAll();
    Page<Product> findAll(Pageable pageable);
}
