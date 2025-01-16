package org.example.server_mobile.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.server_mobile.entity.enums.ShoeSize;

import java.util.List;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ProductRequest {
    Long id;
    String name;
    Long categoryId;
    String description;
    Double price;
    List<ShoeSize> size;
    List<String> productMediaUrls;
    Set<Long> discountIds;
    Set<Long> wishlistIds;
    String status;
    Double stock;
}
