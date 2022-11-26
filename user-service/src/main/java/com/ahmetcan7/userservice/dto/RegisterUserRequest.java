package com.ahmetcan7.userservice.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class RegisterUserRequest {
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String password;
}



