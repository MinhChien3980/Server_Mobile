package org.example.server_mobile.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.example.server_mobile.dto.request.AuthRequest;
import org.example.server_mobile.dto.request.IntrospectRequest;
import org.example.server_mobile.dto.response.AuthResponse;
import org.example.server_mobile.dto.response.IntrospectResponse;
import org.example.server_mobile.entity.User;
import org.example.server_mobile.exception.AppException;
import org.example.server_mobile.exception.ErrorCode;
import org.example.server_mobile.repository.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {
    UserRepo userRepo;

    @NonFinal
    @Value("${jwt.secret}")
    protected String key;

    public AuthResponse authenticateService(AuthRequest authRequest) {
        var user = userRepo.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        boolean authenticated = passwordEncoder.matches(authRequest.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generateToken(user);

        return AuthResponse.builder()
                .authenticated(true)
                .token(token)
                .build();
    }

    private String generateToken(User user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("hello-spring-boot")
                .issueTime(new Date())
                .claim("scope", buildScope(user))
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .build();
        Payload payload = new Payload(claimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        try {
            jwsObject.sign(new MACSigner(key.getBytes(StandardCharsets.UTF_8)));
            return jwsObject.serialize();

        } catch (Exception e) {
            log.error("cannot sign token", e);
            throw new RuntimeException(e);
        }
    }

    public IntrospectResponse introspectResponse(IntrospectRequest introspectRequest) throws ParseException, JOSEException {
        var token = introspectRequest.getToken();

            JWSVerifier verifier = new MACVerifier(key.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            var verify = signedJWT.verify(verifier);

            return IntrospectResponse.builder()
                    .valid(verify && expirationTime.after(new Date()))
                    .build();
    }

    private String buildScope(User user){
        StringJoiner joiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRole())) {
            user.getRole().forEach(role -> joiner.add(role.getName()));
        }
        return joiner.toString();
    }
}
