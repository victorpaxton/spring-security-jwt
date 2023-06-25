package com.learning.product.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProductRequestDTO {

    @NotBlank(message = "Product name should not be NULL or EMPTY")
    private String name;

    private String description;

    @NotBlank(message = "Product type should not be NULL or EMPTY")
    private String productType;

    @Min(value = 1, message = "Quantity is not defined!")
    private int quantity;

    @Min(value = 200, message = "Product price can not be less than 200")
    @Max(value = 500000, message = "Product price can not be more than 500000")
    private double price;

    private String supplierName;

    @NotBlank(message = "Supplier code should not be NULL or EMPTY")
    private String supplierCode;
}
