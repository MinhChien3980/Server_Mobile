package org.example.server_mobile.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class OrderRequest {
    Long id;
    Long cartId;
    Long orderId;
    Long userId;
    String code;
    double shippingFee;

    int orderStatus;
    String address;
}
