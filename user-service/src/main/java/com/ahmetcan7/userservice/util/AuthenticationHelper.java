package com.ahmetcan7.userservice.util;

import com.ahmetcan7.userservice.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import static com.ahmetcan7.userservice.constant.SecurityConstant.JWT_TOKEN_HEADER;

@Component
@RequiredArgsConstructor
public class AuthenticationHelper {
    private final JWTTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    public HttpHeaders getJwtHeader(UserPrincipal user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(user));
        return headers;
    }

    public void authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
