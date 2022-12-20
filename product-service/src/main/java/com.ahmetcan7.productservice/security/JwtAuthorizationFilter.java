package com.ahmetcan7.productservice.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class JwtAuthorizationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader("Authorization");
        if(!validString(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        logHeaders(httpServletRequest);
        String userId=httpServletRequest.getHeader("userId");
        String authoritiesStr = httpServletRequest.getHeader("authorities");
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
        if(validString(authoritiesStr)) {
            simpleGrantedAuthorities=Arrays.stream(authoritiesStr.split(",")).distinct()
                    .filter(this::validString).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());;
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null, simpleGrantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

    private void logHeaders(HttpServletRequest httpServletRequest) {
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String header=headerNames.nextElement();
            logger.info(String.format("Header: %s --- Value: %s", header, httpServletRequest.getHeader(header)));

        }
    }

    private boolean validString(String input) {
        return input!=null && !input.isEmpty();
    }
}