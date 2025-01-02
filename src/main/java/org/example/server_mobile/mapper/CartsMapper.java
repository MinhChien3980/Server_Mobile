package org.example.server_mobile.mapper;

import org.example.server_mobile.dto.response.CartsResponse;
import org.example.server_mobile.entity.Carts;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartsMapper {
    
    CartsResponse toCartResponse(Carts carts);
}
