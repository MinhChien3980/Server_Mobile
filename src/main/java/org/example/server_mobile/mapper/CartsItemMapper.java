package org.example.server_mobile.mapper;

import org.example.server_mobile.dto.request.CartsItemRequest;
import org.example.server_mobile.dto.request.CartsRequest;
import org.example.server_mobile.dto.response.CartItemResponse;
import org.example.server_mobile.entity.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartsItemMapper {
    CartItem toCartItem(CartsItemRequest cartsItemRequest);

    CartItemResponse toCartItemResponse(CartItem cartItem);
}