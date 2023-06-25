package com.learning.product.repository;

import com.learning.product.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//    Product findBySupplierCode(String supplierCode);
    List<Product> findByProductTypeNotNull();
}
