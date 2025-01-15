package org.example.server_mobile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.server_mobile.entity.CartPrice;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class CartsResponse {
    Long id;
    List<CartItemResponse> cartItems;
    Address address;
    CartPrice cartPrice;
    String errorMessage;
}
