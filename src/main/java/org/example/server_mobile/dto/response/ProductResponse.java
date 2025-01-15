package org.example.server_mobile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.server_mobile.entity.enums.Gender;
import org.example.server_mobile.entity.enums.ShoeSize;

import java.util.Date;
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
    String categoryName;
    String description;
    Double price;
    List<ShoeSize> size;
    List<String> productMediaUrls;
    Set<String> discountNames;
    Set<String> wishlistUsernames;
    String status;
    Double stock;
    Date createdAt;
    Date updatedAt;
}
