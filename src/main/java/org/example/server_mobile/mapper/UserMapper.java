package org.example.server_mobile.mapper;

import org.example.server_mobile.dto.request.UserCreationRequest;
import org.example.server_mobile.dto.response.UserResponse;
import org.example.server_mobile.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest userRequest);

    UserResponse toUserResponse(User user);

    void updateUser(UserCreationRequest userRequest, @MappingTarget User user);
}
