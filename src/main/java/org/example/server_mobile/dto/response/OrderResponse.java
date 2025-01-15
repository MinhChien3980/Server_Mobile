package org.example.server_mobile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class OrderResponse {

    Long id;
    Long userId;
    Long discountId;
    Long cartId;
    Integer status;
    Double totalPrice;
    Double totalDiscount;
    Double grandTotal;
    String address;
    List<CartItemResponse> items;

}
