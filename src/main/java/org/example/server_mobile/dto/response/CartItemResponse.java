package org.example.server_mobile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.server_mobile.entity.Product;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class CartItemResponse {
    Long id;
    Product product;
    String productName;
    Integer quantity;
    int grandTotal;
    int discount;
    int productPrice;
    String errorMessage;
}
