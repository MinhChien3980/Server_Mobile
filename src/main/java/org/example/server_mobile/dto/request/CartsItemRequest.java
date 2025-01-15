package org.example.server_mobile.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.server_mobile.entity.enums.TypeItem;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class CartsItemRequest {
    Long id;
    Long cartId;
    Long orderId;
    Long productId;
    String code;
    int quantity;
    double grandTotal;
    double discount;
    double productPrice;
    TypeItem typeItem;
}
