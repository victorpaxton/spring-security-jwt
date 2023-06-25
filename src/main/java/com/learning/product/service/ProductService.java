package com.learning.product.service;

import com.learning.product.model.dto.ProductRequestDTO;
import com.learning.product.model.dto.ProductResponseDTO;

import java.util.List;
import java.util.Map;

public interface ProductService {
    public ProductResponseDTO createNewProduct(ProductRequestDTO productRequestDTO);

    public List<ProductResponseDTO> getProducts();

    public ProductResponseDTO getProductById(long productId);

    public Map<String, List<ProductResponseDTO>> getProductsByType();
}
