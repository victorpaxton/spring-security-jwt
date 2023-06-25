package com.learning.product.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.product.model.dto.ProductRequestDTO;
import com.learning.product.model.dto.ProductResponseDTO;
import com.learning.product.model.entity.Product;

public class ValueMapper {

    public static Product convertToEntity(ProductRequestDTO productRequestDTO) {
        Product product = new Product();
        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setProductType(productRequestDTO.getProductType());
        product.setQuantity(productRequestDTO.getQuantity());
        product.setPrice(productRequestDTO.getPrice());
        product.setSupplierName(productRequestDTO.getSupplierName());
        product.setSupplierCode(productRequestDTO.getSupplierCode());
        return product;
    }

    public static ProductResponseDTO convertToDTO(Product product) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(product.getId());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setDescription(product.getDescription());
        productResponseDTO.setProductType(product.getProductType());
        productResponseDTO.setQuantity(product.getQuantity());
        productResponseDTO.setPrice(product.getPrice());
        productResponseDTO.setSupplierName(product.getSupplierName());
        productResponseDTO.setSupplierCode(product.getSupplierCode());
        return productResponseDTO;
    }

    public static String jsonAsString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
