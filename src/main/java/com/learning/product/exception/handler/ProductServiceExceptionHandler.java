package com.learning.product.exception.handler;

import com.learning.product.exception.ProductNotFoundException;
import com.learning.product.exception.ProductServiceBusinessException;
import com.learning.product.model.dto.APIResponse;
import com.learning.product.model.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ProductServiceExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIResponse<?> handleMethodArgumentException(MethodArgumentNotValidException exception) {
        APIResponse<?> serviceResponse = new APIResponse<>();
        List<ErrorDTO> errors = new ArrayList<>();

        exception.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    ErrorDTO errorDTO = new ErrorDTO(error.getField(), error.getDefaultMessage());
                    errors.add(errorDTO);
                });

        serviceResponse.setStatus("FAILED");
        serviceResponse.setErrors(errors);

        return serviceResponse;
    }

    @ExceptionHandler(ProductServiceBusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public APIResponse<?> handleProductServiceBusinessException(ProductServiceBusinessException exception) {
        APIResponse<?> serviceResponse = new APIResponse<>();
        serviceResponse.setStatus("FAILED");
        serviceResponse.setErrors(Collections.singletonList(new ErrorDTO("", exception.getMessage())));
        return serviceResponse;
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public APIResponse<?> handleProductNotFoundException(ProductNotFoundException exception) {
        APIResponse<?> serviceResponse = new APIResponse<>();
        serviceResponse.setStatus("FAILED");
        serviceResponse.setErrors(Collections.singletonList(new ErrorDTO("", exception.getMessage())));
        return serviceResponse;
    }
}
