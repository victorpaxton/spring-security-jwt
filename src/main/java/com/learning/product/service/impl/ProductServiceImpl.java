package com.learning.product.service.impl;

import com.learning.product.exception.ProductNotFoundException;
import com.learning.product.exception.ProductServiceBusinessException;
import com.learning.product.model.dto.ProductRequestDTO;
import com.learning.product.model.dto.ProductResponseDTO;
import com.learning.product.model.entity.Product;
import com.learning.product.repository.ProductRepository;
import com.learning.product.service.ProductService;
import com.learning.product.util.ValueMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public ProductResponseDTO createNewProduct(ProductRequestDTO productRequestDTO) throws ProductServiceBusinessException {
        ProductResponseDTO productResponseDTO;

        try {
            log.info("ProductService:createNewProduct execution started.");
            Product product = ValueMapper.convertToEntity(productRequestDTO);
            log.debug("ProductService:createNewProduct request parameters {}", ValueMapper.jsonAsString(productRequestDTO));

            Product productResults = productRepository.save(product);
            productResponseDTO = ValueMapper.convertToDTO(productResults);
            log.debug("ProductService:createNewProduct received response from database {}", ValueMapper.jsonAsString(productResponseDTO));

        } catch (Exception e) {
            log.error("Exception occurred while persisting product to database , Exception message {}", e.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while create a new product");

        }

        log.info("ProductService:createNewProduct execution ended.");
        return productResponseDTO;
    }

    @Override
    @Cacheable(value = "product")
    public List<ProductResponseDTO> getProducts() throws ProductServiceBusinessException {
        List<ProductResponseDTO> productResponseDTOS = null;

        try {
            log.info("ProductService:getProducts execution started.");

            List<Product> productList = productRepository.findAll();

            if (!productList.isEmpty()) {
                productResponseDTOS = productList.stream()
                        .map(ValueMapper::convertToDTO)
                        .collect(Collectors.toList());
            } else {
                productResponseDTOS = Collections.emptyList();
            }

            log.debug("ProductService:getProducts retrieving products from database  {}", ValueMapper.jsonAsString(productResponseDTOS));

        } catch (Exception ex) {
            log.error("Exception occurred while retrieving products from database , Exception message {}", ex.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while fetch all products from Database");
        }

        log.info("ProductService:getProducts execution ended.");
        return productResponseDTOS;
    }

    @Override
    @Cacheable(value = "product")
    public ProductResponseDTO getProductById(long productId) {
        ProductResponseDTO productResponseDTO;

        //String supplierCode="";
        try {
            log.info("ProductService:getProductById execution started.");

//            Product p=productRepository.findBySupplierCode(supplierCode);
//
//            Optional<Product> p1 = Optional.ofNullable(p);
//            if(p1.isPresent()){
//                //do operation
//            }else{
//                throw new Exception();
//            }


            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + productId));
            productResponseDTO = ValueMapper.convertToDTO(product);

            log.debug("ProductService:getProductById retrieving product from database for id {} {}", productId, ValueMapper.jsonAsString(productResponseDTO));

        } catch (ProductNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occurred while retrieving product {} from database , Exception message {}", productId, ex.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while fetch product from Database " + productId);
        }

        log.info("ProductService:getProductById execution ended.");
        return productResponseDTO;
    }

    @Override
    @Cacheable(value = "product")
    public Map<String, List<ProductResponseDTO>> getProductsByType() {
        try {
            log.info("ProductService:getProductsByTypes execution started.");

            Map<String, List<ProductResponseDTO>> productsMap =
                    productRepository.findByProductTypeNotNull().stream()
                            .map(ValueMapper::convertToDTO)
                            .collect(Collectors.groupingBy(ProductResponseDTO::getProductType));

            log.info("ProductService:getProductsByTypes execution ended.");
            return productsMap;

        } catch (Exception ex) {
            log.error("Exception occurred while retrieving product grouping by type from database , Exception message {}", ex.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while fetch product from Database ");
        }
    }
}
