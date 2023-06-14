package com.ahmetcan7.userservice.filter;

import com.ahmetcan7.userservice.exception.TokenNotValidException;
import com.ahmetcan7.userservice.util.JWTTokenProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ahmetcan7.userservice.constant.SecurityConstant.*;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JWTTokenProvider jwtTokenProvider;
    @Value("${jwt.secret}")
    private String secret;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try{
            List<String> publicUrls = Arrays.stream(PUBLIC_URLS)
                    .filter((path)->request.getServletPath().equals(path))
                    .collect(Collectors.toList());

            String[] parts = request.getServletPath().split("/");

            StringBuilder removedPath = new StringBuilder();
            for (int i = 0; i < parts.length - 1; i++) {
                removedPath.append(parts[i]);
                removedPath.append("/");
            }

            if (removedPath.length() > 0) {
                removedPath.deleteCharAt(removedPath.length() - 1);
            }

            List<String> publicUrlsWithOnlyMain = Arrays.stream(PUBLIC_URLS_WITH_ONLY_MAIN)
                    .filter((path)->removedPath.toString().equals(path))
                    .collect(Collectors.toList());

            if (request.getMethod().equalsIgnoreCase(OPTIONS_HTTP_METHOD)) {
                response.setStatus(OK.value());
            } else {
                String authorizationHeader = request.getHeader(AUTHORIZATION);
                if (!publicUrls.isEmpty() || !publicUrlsWithOnlyMain.isEmpty()) {
                    filterChain.doFilter(request, response);
                    return;
                }
                if(authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX)){
                    throw new TokenNotValidException("There is no token or not start with Bearer");
                }
                    String token = authorizationHeader.substring(TOKEN_PREFIX.length());
                    DecodedJWT decodedJWT = jwtTokenProvider.decodeToken(token);
                    Preconditions.checkArgument(decodedJWT.getType().equals("JWT"));
                    Algorithm algorithm = HMAC512(secret);
                    JWTVerifier verifier = JWT.require(algorithm).withIssuer(Company_LLC).build();
                    verifier.verify(decodedJWT);
                    String email = decodedJWT.getSubject();

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        List<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(decodedJWT);
                        Authentication authentication = jwtTokenProvider.getAuthentication(email, authorities, request);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        SecurityContextHolder.clearContext();
                    }
            }
        }catch (Exception exception){
            response.setHeader("error", exception.getMessage());
            response.setStatus(FORBIDDEN.value());
            Map<String, String> error = new HashMap<>();
            error.put("error_message", exception.getMessage());
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }

        filterChain.doFilter(request, response);
    }
}
