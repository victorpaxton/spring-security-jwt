package com.learning.product.controller;

import com.learning.product.model.dto.APIResponse;
import com.learning.product.model.dto.ProductRequestDTO;
import com.learning.product.model.dto.ProductResponseDTO;
import com.learning.product.service.ProductService;
import com.learning.product.util.ValueMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<APIResponse> createNewProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        log.info("ProductController::createNewProduct request body {}", ValueMapper.jsonAsString(productRequestDTO));

        ProductResponseDTO productResponseDTO = productService.createNewProduct(productRequestDTO);
        //Builder Design pattern

        APIResponse<ProductResponseDTO> responseDTO = APIResponse
                .<ProductResponseDTO>builder()
                .status("SUCCESS")
                .results(productResponseDTO)
                .build();

        log.info("ProductController::createNewProduct response {}", ValueMapper.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<APIResponse> getProducts() {

        List<ProductResponseDTO> products = productService.getProducts();
        //Builder Design pattern (to avoid complex object creation headache)
        APIResponse<List<ProductResponseDTO>> responseDTO = APIResponse
                .<List<ProductResponseDTO>>builder()
                .status("SUCCESS")
                .results(products)
                .build();

        log.info("ProductController::getProducts response {}", ValueMapper.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> getProduct(@PathVariable long productId) {

        log.info("ProductController::getProduct by id  {}", productId);

        ProductResponseDTO productResponseDTO = productService.getProductById(productId);
        APIResponse<ProductResponseDTO> responseDTO = APIResponse
                .<ProductResponseDTO>builder()
                .status("SUCCESS")
                .results(productResponseDTO)
                .build();

        log.info("ProductController::getProduct by id  {} response {}", productId,ValueMapper
                .jsonAsString(productResponseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/types")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<APIResponse> getProductsGroupByType() {

        Map<String, List<ProductResponseDTO>> products = productService.getProductsByType();
        APIResponse<Map<String, List<ProductResponseDTO>>> responseDTO = APIResponse
                .<Map<String, List<ProductResponseDTO>>>builder()
                .status("SUCCESS")
                .results(products)
                .build();

        log.info("ProductController::getProductsGroupByType by types  {}", ValueMapper.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
