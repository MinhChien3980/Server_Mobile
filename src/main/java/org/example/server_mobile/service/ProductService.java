package org.example.server_mobile.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.server_mobile.dto.request.ProductRequest;
import org.example.server_mobile.dto.response.ProductResponse;
import org.example.server_mobile.entity.Product;
import org.example.server_mobile.mapper.ProductMapper;
import org.example.server_mobile.repository.ProductRepo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductRepo productRepo;
    ProductMapper productMapper;

    public ProductResponse createProduct(ProductRequest request) {
        Product product = productMapper.toProduct(request);
        productRepo.save(product);
        return productMapper.toProductResponse(product);
    }

    public void delete(String id) {
        productRepo.deleteById(id);
    }

}
