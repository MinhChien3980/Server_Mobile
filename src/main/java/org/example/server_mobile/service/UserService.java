package org.example.server_mobile.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.server_mobile.constant.PredefinedRole;
import org.example.server_mobile.dto.request.UserCreationRequest;
import org.example.server_mobile.dto.response.UserResponse;
import org.example.server_mobile.entity.Role;
import org.example.server_mobile.entity.User;
import org.example.server_mobile.exception.AppException;
import org.example.server_mobile.exception.ErrorCode;
import org.example.server_mobile.mapper.UserMapper;
import org.example.server_mobile.repository.RoleRepo;
import org.example.server_mobile.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    UserRepo userRepo;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepo roleRepo;

    public UserResponse createUser(UserCreationRequest userRequest) {

        if (userRepo.existsByEmail(userRequest.getEmail()))
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);

        User newUser = userMapper.toUser(userRequest);

        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roleRepo.findById(PredefinedRole.USER_ROLE).ifPresent(roles :: add);
        newUser.setRole(roles);
        try {
            newUser = userRepo.save(newUser);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        newUser.setCreated_at(String.valueOf(new Date()));
        newUser.setUpdated_at(String.valueOf(new Date()));

        return userMapper.toUserResponse(userRepo.save(newUser));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @PostAuthorize("returnObject.email == authentication.name")
    public UserResponse getUserById(String id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("User entity: " + user);

        return userMapper.toUserResponse(user);
    }

    @PostAuthorize("returnObject.email == authentication.name")
    public UserResponse updateUser(String id, UserCreationRequest user) {
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setUpdated_at(String.valueOf(new Date()));
        userMapper.updateUser(user, existingUser);
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));

//        var roles = roleRepo.findAllById(user.getRole());
//        existingUser.setRole(new HashSet<>(roles));
        return userMapper.toUserResponse(userRepo.save(existingUser));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String id) {
        userRepo.deleteById(id);
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String authented = context.getAuthentication().getName();

        User byUserName = userRepo.findByEmail(authented)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toUserResponse(byUserName);
    }
}
