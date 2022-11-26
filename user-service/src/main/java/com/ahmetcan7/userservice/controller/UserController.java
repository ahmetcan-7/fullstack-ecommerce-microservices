package com.ahmetcan7.userservice.controller;

import com.ahmetcan7.userservice.dto.LoginUserRequest;
import com.ahmetcan7.userservice.dto.RegisterUserRequest;
import com.ahmetcan7.userservice.model.User;
import com.ahmetcan7.userservice.model.UserPrincipal;
import com.ahmetcan7.userservice.service.UserService;
import com.ahmetcan7.userservice.util.AuthenticationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterUserRequest user)  {
        userService.register(user);
        return ResponseEntity.ok("User is registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginUserRequest user) {
        authenticationHelper.authenticate(user.getUsername(), user.getPassword());
        User loginUser = userService.findUserByUsername(user.getUsername());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = authenticationHelper.getJwtHeader(userPrincipal);
        return new ResponseEntity<>(loginUser, jwtHeader, OK);
    }

}
