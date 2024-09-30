package org.example.server_mobile.controller;

import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.server_mobile.dto.request.ApiResponse;
import org.example.server_mobile.dto.request.AuthRequest;
import org.example.server_mobile.dto.request.IntrospectRequest;
import org.example.server_mobile.dto.response.AuthResponse;
import org.example.server_mobile.dto.response.IntrospectResponse;
import org.example.server_mobile.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class AuthController {
    AuthService authService;

    @PostMapping("/token")
    ApiResponse<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        var re = authService.authenticateService(authRequest);
        return ApiResponse.<AuthResponse>builder()
                .data(re)
                .build();
    }
    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest introspectRequest) throws ParseException, JOSEException {
        var re = authService.introspectResponse(introspectRequest);
        return ApiResponse.<IntrospectResponse>builder()
                .data(re)
                .build();
    }
}
