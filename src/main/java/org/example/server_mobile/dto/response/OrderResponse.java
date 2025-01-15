package org.example.server_mobile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
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
    String userName;
    int status;
    Double totalPrice;
    Double totalDiscount;
    Double grandTotal;
    double shippingFee;
    String address;
    Date createdAt;
    List<CartItemResponse> cartItems;


}
