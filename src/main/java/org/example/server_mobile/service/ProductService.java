package org.example.server_mobile.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.server_mobile.dto.request.ProductRequest;
import org.example.server_mobile.dto.response.ProductResponse;
import org.example.server_mobile.entity.Product;
import org.example.server_mobile.entity.ProductMedia;
import org.example.server_mobile.mapper.ProductMapper;
import org.example.server_mobile.mapper.ProductMediaMapper;
import org.example.server_mobile.repository.ProductMediaRepo;
import org.example.server_mobile.repository.ProductRepo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductRepo productRepo;
    ProductMapper productMapper;
    ProductMediaMapper productMediaMapper;
    ProductMediaRepo productMediaRepo;

    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse createProduct(ProductRequest request) {
        // Map ProductRequest to Product
        Product product = productMapper.toProduct(request);
        product.setStatus((byte) ("ACTIVE".equalsIgnoreCase(request.getStatus()) ? 1 : 0));

        // Save Product to generate the ID
        product = productRepo.save(product);

        // Handle ProductMedia
        if (request.getProductMediaUrls() != null && !request.getProductMediaUrls().isEmpty()) {
            Product finalProduct = product;
            List<ProductMedia> mediaList = request.getProductMediaUrls().stream()
                    .map(url -> ProductMedia.builder()
                            .url(url)
                            .product(finalProduct)
                            .mediaType("IMAGE") // Example, set media type based on business logic
                            .build())
                    .collect(Collectors.toList());

            productMediaRepo.saveAll(mediaList); // Save all media entities
            product.setProductMedia(mediaList); // Update product's media list
        }

        // Return response
        return productMapper.toProductResponse(product);
    }

    public List<ProductResponse> allProduct() {
        return productRepo.findAll().stream()
                .map(productMapper::toProductResponse)
                .peek(productResponse -> productResponse.setProductMediaUrls(
                        productMediaRepo.findByProduct_Id(productResponse.getId()).stream()
                                .map(ProductMedia::getUrl)
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }


    public void update(ProductRequest request) {
        Product product = productRepo.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + request.getId()));

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStatus((byte) ("ACTIVE".equalsIgnoreCase(request.getStatus()) ? 1 : 0));

        productRepo.save(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Long id) {
        productRepo.deleteById(id);
    }

    public ProductResponse findById(Long id) {
        return productRepo.findById(id)
                .map(productMapper::toProductResponse)
                .map(productResponse -> {
                    productResponse.setProductMediaUrls(
                            productMediaRepo.findByProduct_Id(productResponse.getId()).stream()
                                    .map(ProductMedia::getUrl)
                                    .collect(Collectors.toList())
                    );
                    return productResponse;
                })
                .orElse(null);
    }
}
