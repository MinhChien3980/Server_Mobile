package org.example.server_mobile.mapper;

import org.example.server_mobile.dto.request.ProductRequest;
import org.example.server_mobile.dto.response.ProductResponse;
import org.example.server_mobile.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface ProductMapper {
    Product toProduct(ProductRequest request);

    ProductResponse toProductResponse(Product product);
}
