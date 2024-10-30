package org.example.server_mobile.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.server_mobile.dto.response.DiscountResponse;
import org.example.server_mobile.dto.response.ProductMediaResponse;
import org.example.server_mobile.dto.response.ProductVariantResponse;
import org.example.server_mobile.entity.AgeGroup;
import org.example.server_mobile.entity.Gender;
import org.example.server_mobile.entity.ProductMedia;
import org.example.server_mobile.entity.Size;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    String id;
    String name;
    String description;
    Double price;
    Byte status;
    Double stock;
    Size size;
    Gender gender;
    AgeGroup ageGroup;
    List<ProductVariantRequest> productVariants;
    List<ProductMedia> productMedia;
    Set<DiscountRequest> discount;
}
