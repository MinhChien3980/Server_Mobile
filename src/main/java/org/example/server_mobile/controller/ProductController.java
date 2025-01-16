package org.example.server_mobile.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.server_mobile.dto.request.ApiResponse;
import org.example.server_mobile.dto.request.ProductRequest;
import org.example.server_mobile.dto.response.ProductResponse;
import org.example.server_mobile.entity.Product;
import org.example.server_mobile.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService productService;

    @PostMapping
    ApiResponse<ProductResponse> createProduct(@RequestBody ProductRequest product) {
        log.info("Request to create product: {}", product);
        return ApiResponse.<ProductResponse>builder()
                .data(productService.createProduct(product))
                .build();
    }

    @GetMapping
    ApiResponse<List<Product>> allProduct() {
        return ApiResponse.<List<Product>>builder()
                .data(productService.allProduct())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<ProductResponse> getProductById(@PathVariable Long id) {
        return ApiResponse.<ProductResponse>builder()
                .data(productService.getById(id))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ApiResponse.<String>builder()
                .data("Product deleted successfully")
                .build();
    }
    @PutMapping("/{id}")
    ApiResponse<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest product) {
        return ApiResponse.<ProductResponse>builder()
                .data(productService.updateProduct(id, product))
                .build();
    }

}
