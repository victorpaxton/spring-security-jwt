package com.learning.product.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String description;

    @Column(name = "product_type")
    private String productType;

    private int quantity;

    private double price;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "supplier_code")
    private String supplierCode;
}
