package org.example.server_mobile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.server_mobile.entity.enums.Gender;
import org.example.server_mobile.entity.enums.ShoeSize;

import java.util.List;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ProductResponse {
    Long id;
    String name;
    String description;
    Double price;
    String status;
    Double stock;
    List<ShoeSize> size;
    Gender gender;
//    AgeGroup ageGroup;
    List<ProductMediaResponse> productMedia;
    Set<DiscountResponse> discount;
    String created_at;
    String updated_at;
    CategoryResponse category;
}
