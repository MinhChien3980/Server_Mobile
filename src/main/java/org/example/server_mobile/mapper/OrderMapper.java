package org.example.server_mobile.mapper;

import org.example.server_mobile.dto.request.OrderRequest;
import org.example.server_mobile.dto.response.OrderResponse;
import org.example.server_mobile.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(OrderRequest orderRequest);

    OrderResponse toOrderResponse(Order order);
}