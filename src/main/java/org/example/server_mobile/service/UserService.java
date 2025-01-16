package org.example.server_mobile.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.example.server_mobile.constant.PredefinedRole;
import org.example.server_mobile.dto.request.ResetPassRequest;
import org.example.server_mobile.dto.request.UserCreationRequest;
import org.example.server_mobile.dto.response.UserResponse;
import org.example.server_mobile.entity.Role;
import org.example.server_mobile.entity.User;
import org.example.server_mobile.exception.AppException;
import org.example.server_mobile.exception.ErrorCode;
import org.example.server_mobile.mapper.UserMapper;
import org.example.server_mobile.repository.RoleRepo;
import org.example.server_mobile.repository.UserRepo;
import org.example.server_mobile.util.RandomGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    UserRepo userRepo;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepo roleRepo;
    JavaMailSender mailSender;

    @NonFinal
    @Value("${spring.mail.username}")
    protected String mailFrom;

    public UserResponse createUser(UserCreationRequest userRequest) {

        if (userRepo.existsByEmail(userRequest.getEmail()))
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);

        User newUser = userMapper.toUser(userRequest);

        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        newUser.setStatus((byte) ("ACTIVE".equals(userRequest.getStatus()) ? 1 : 0));
        HashSet<Role> roles = new HashSet<>();
        roleRepo.findByName(PredefinedRole.USER_ROLE).ifPresent(roles :: add);
        newUser.setRole(roles);
        newUser.setFullName(userRequest.getFullName());
        newUser.setPhoneNumber(userRequest.getPhoneNumber());
        newUser.setDateOfBirth(userRequest.getDateOfBirth());
        newUser.setAddresses(userRequest.getAddresses());
        newUser.setCreatedAt(java.time.LocalDateTime.now());
        newUser.setUpdatedAt(java.time.LocalDateTime.now());
        try {
            newUser = userRepo.save(newUser);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        return userMapper.toUserResponse(userRepo.save(newUser));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUserById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        System.out.println("User entity: " + user);

        return userMapper.toUserResponse(user);
    }

    @PostAuthorize("returnObject.email == authentication.name")
    public UserResponse updateUser(Long id, UserCreationRequest user) {
        log.info("Updating user with id: {}", id);
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        log.info("Existing user: {}", existingUser.toString());
        userMapper.updateUser(user, existingUser);
        existingUser.setDateOfBirth(user.getDateOfBirth());
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("Updated user: {}", existingUser.getPassword());

        log.info("Updated user: {}", existingUser.getRole());
        return userMapper.toUserResponse(userRepo.save(existingUser));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String authented = context.getAuthentication().getName();

        User byUserName = userRepo.findByEmail(authented)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toUserResponse(byUserName);
    }

    public void resetPass(ResetPassRequest mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        log.info("email: {}", mail);
        try {
            Optional<User> optionalUser = userRepo.findByEmail(mail.getEmail());
            log.info("User found 1: {}", optionalUser);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                log.info("User found 2: {}", user);

                String newPassword = RandomGenerator.generateRandomPassword();

                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
                String encodedPassword = passwordEncoder.encode(newPassword);
                user.setPassword(encodedPassword);

                userRepo.save(user);

                mailMessage.setFrom(mailFrom);
                mailMessage.setTo(mail.getEmail());
                mailMessage.setSubject("New Password");
                mailMessage.setText("Your new password is: " + newPassword);
                mailSender.send(mailMessage);
            } else {
                throw new AppException(ErrorCode.USER_NOT_EXISTED);
            }
        } catch (AppException e) {
            log.error("Error during password reset: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during password reset: {}", e.getMessage(), e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
