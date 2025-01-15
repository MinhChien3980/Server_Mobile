package org.example.server_mobile.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.server_mobile.entity.ProductMedia;
import org.example.server_mobile.entity.enums.Gender;
import org.example.server_mobile.entity.enums.ShoeSize;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ProductRequest {
    String name;
    String description;
    Double price;
    String status;
    Double stock;
    List<ShoeSize> size;
    Gender gender;
    CategoryRequest category;
//    AgeGroup ageGroup;
    List<ProductMedia> productMedia;
}
