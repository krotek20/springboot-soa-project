package com.soaproject.ProductService.service;

import com.soaproject.ProductService.model.ProductRequest;
import com.soaproject.ProductService.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long productId, long quantity);
}
