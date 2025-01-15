package org.example.server_mobile.mapper;

import org.example.server_mobile.dto.request.AddressesRequest;
import org.example.server_mobile.dto.response.AddressResponse;
import org.example.server_mobile.entity.Addresses;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressesMapper {

    Addresses toAddress(AddressesRequest request);

    AddressResponse toAddressResponse(Addresses addresses);
}
